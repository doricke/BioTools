
#include <stdio.h>


#define  TRUE		1
#define  FALSE		0

#define  MAX_LINE	10032	/* maximum line length */


/* Text file. */
typedef struct {
  char  name [ MAX_LINE ];	/* file name */
  char  next;			/* current character */
  char  token [ MAX_LINE ];	/* current token */
  char  line  [ MAX_LINE ];	/* current line */
  int   line_index;		/* line index */
  FILE  *data;			/* data file */
  short eof;			/* end of file flag */
} t_text;


main ( argc, argv )
int  argc;		/* number of command line arguments */
char *argv [];		/* command line arguments */
{
  int     line_index;   /* line index */
  t_text  sts;		/* STS sequence file */
  t_text  sequence;	/* STS sequence */
  FILE    *fopen ();	/* file open function */


  printf ( "This is the File to Files program.\n\n" );

  /* Check for a command line argument. */
  if ( argc > 1 )
  {
    d_strcpy ( sts.name, argv [ 1 ] );
    sts.data = fopen ( sts.name, "r" );
  }  /* if */
  else

    /* Prompt for the input file name. */
    prompt_file ( &sts, "What is the file name?" );

  /* Process the sequences. */
  while ( sts.eof != TRUE )
  {
    /* Find the STS name line. */
    while ( ( sts.line [ 0 ] != '>' ) &&
            ( sts.eof != TRUE ) )
    
      get_line ( &sts );

    d_strcpy ( sequence.name, &(sts.line [ 1 ]) );

    if ( sequence.name [ d_stridx ( sequence.name, "\t" ) ] == '\t' )
      sequence.name [ d_stridx ( sequence.name, "\t" ) ] = '\0';

    while ( sequence.name [ 0 ] == ' ' )
      d_strcpy ( sequence.name, &(sequence.name [ 1 ]) );

    if ( sequence.name [ d_stridx ( sequence.name, " " ) ] == ' ' )
      sequence.name [ d_stridx ( sequence.name, " " ) ] = '\0';

    line_index = d_stridx ( sequence.name, "gi|" );
    if ( sequence.name [ line_index ] == 'g' )
      d_strcpy ( sequence.name, &(sequence.name [ line_index + 3 ]) );

    if ( sequence.name [ d_stridx ( sequence.name, "|" ) ] == '|' )
      sequence.name [ d_stridx ( sequence.name, "|" ) ] = '\0';

    if ( sequence.name [ d_stridx ( sequence.name, "," ) ] == ',' )
      sequence.name [ d_stridx ( sequence.name, "," ) ] = '\0';

    if ( sequence.name [ d_strlen ( sequence.name ) - 1 ] == '\n' )
      sequence.name [ d_strlen ( sequence.name ) - 1 ] = '\0';

    if ( sequence.name [ d_stridx ( sequence.name, ";" ) ] == ';' )
      sequence.name [ d_stridx ( sequence.name, ";" ) ] = '\0';

    if ( sequence.name [ d_stridx ( sequence.name, "| " ) ] == '|' )
      sequence.name [ d_stridx ( sequence.name, "| " ) ] = '\0';

    if ( sequence.name [ d_strlen ( sequence.name ) - 1 ] == '|' )
      sequence.name [ d_strlen ( sequence.name ) - 1 ] = '\0';

    while ( sequence.name [ d_stridx ( sequence.name, "|" ) ] == '|' )
      sequence.name [ d_stridx ( sequence.name, "|" ) ] = '_';

    for ( line_index = 0; line_index < d_strlen ( sequence.name ); line_index++ )
    {
      /* Replace spaces with underscores. */
      if ( sequence.name [ line_index ] == ' ' )
        sequence.name [ line_index ] = '_';
    }  /* for */

    /* Remove trailing underscores. */
    while ( sequence.name [ d_strlen ( sequence.name ) - 1 ] == '_' )

      sequence.name [ d_strlen ( sequence.name ) - 1 ] = '\0';


    /* Get the first sequence line. */
    get_line ( &sts );

    printf ( "name '%s'\n", sequence.name );

    sequence.data = fopen ( sequence.name, "w" );

    /* Copy the sequence file. */
    while ( ( sequence.data  != NULL ) &&
            ( sts.eof        != TRUE ) &&
            ( sts.line [ 0 ] != '>'  ) )
    {
      fprintf ( sequence.data, "%s", sts.line );

      get_line ( &sts );
    }  /* while */

    fclose ( sequence.data );
  }  /* while */

  fclose ( sts.data );

  printf ( "\nEnd of File->Files program.\n" );
}  /* main */


/* This function capitalizes a string. */
capitalize ( s )
char s [];		/* string */
{
  int  i = 0;

  while ( ( s [ i ] != '\0' ) && ( i < MAX_LINE ) )
  {
    if ( ( s [ i ] >= 'a' ) && ( s [ i ] <= 'z' ) )

      s [ i ] = s [ i ] - 'a' + 'A';

    i++;
  }  /* while */
}  /* capitalize */


/* This function gets the next text character. */
get_char ( text )
t_text  *text;		/* ASCII text file */
{
  /* Get the next sequence character. */
  (*text).next = (*text).line [ (*text).line_index++ ];

  /* skip white space */
  while ( ( (*text).next == ' '  ) ||
          ( (*text).next == '\n' ) ||
          ( (*text).next == '\t' ) )

    if ( (*text).next == '\n' )  get_line ( text );
    else
      (*text).next = (*text).line [ (*text).line_index++ ];

}  /* get_char */


/* This function gets the next integer from the current line. */
int  get_int ( text )
t_text  *text;		/* ASCII text file */
{
  char  c;
  int   i, sign = 1;


  /* Get the first character. */
  get_char ( text );

  /* Check for a sign. */
  if ( ( (*text).next == '+' ) || ( (*text).next == '-' ) )
  {
    sign = ( (*text).next == '+' ) ? 1 : -1;

    (*text).next = (*text).line [ (*text).line_index++ ];
  }  /* if */

  /* Traverse the integer. */
  for ( i = 0; ( (*text).next >= '0' && (*text).next <= '9' );
      ( (*text).next = (*text).line [ (*text).line_index++ ] ) )

    i = i * 10 + (*text).next - '0';

  /* Set the integer sign. */
  i *= sign;

  return ( i );  /* return the integer */
}  /* get_int */


/* This function gets the next text line. */
get_line ( text )
t_text  *text;		/* ASCII text file */
{
  int  c = 0, i = 0;


  (*text).line_index = 0;

  /* Get the text line. */
  while ( ( i < MAX_LINE ) && ( ( c = getc ( (*text).data ) ) != EOF ) &&
          ( c != '\n' ) )

    (*text).line [ i++ ] = c;

  /* Properly terminate the text line. */
  (*text).line [ i++ ] = '\n';
  (*text).line [ i   ] = '\0';

  /* Check for end of file. */
  if ( c == EOF )  (*text).eof = TRUE;

  /* Get the first character. */
  (*text).next = (*text).line [ (*text).line_index++ ];

  /* Get the first text token. */
  get_token ( text );

}  /* get_line */


/* This function gets the next text token. */
get_token ( text )
t_text  *text;		/* ASCII text file */
{
  int  i = 0;


  /* skip white space */
  while ( ( (*text).next == ' '  ) ||
          ( (*text).next == '\n' ) ||
          ( (*text).next == '\t' ) )
  {
    (*text).next = (*text).line [ (*text).line_index++ ];

    if ( (*text).next == '\n' )  get_line ( text );
  }  /* while */

  /* Copy the token. */
  while ( ( ( (*text).next >= '0' ) && ( (*text).next <= '9' ) ) ||
          ( ( (*text).next >= 'a' ) && ( (*text).next <= 'z' ) ) ||
          ( ( (*text).next >= 'A' ) && ( (*text).next <= 'Z' ) ) ||
            ( (*text).next == '.' ) ) 
  {
    (*text).token [ i++ ] = (*text).next;

    (*text).next = (*text).line [ (*text).line_index++ ];
  }  /* while */

  /* Check for non-alphanumeric. */
  if ( i == 0 )
  {
    (*text).token [ i++ ] = (*text).next;

    (*text).next = (*text).line [ (*text).line_index++ ];
  }  /* if */

  (*text).token [ i ] = '\0';
}  /* get_token */


/* Convert c to lower case; ASCII only */
lower (c)
int c;
{
  if (c >= 'A' && c <= 'Z')

    return ( c + 'a' - 'A' );

  else

    return (c);
}  /* lower */


/* This function opens a file. */
open_text_file ( text )
t_text  *text;		/* ASCII text file */
{
  FILE  *fopen ();


  /* Initialization. */
  (*text).next = ' ';
  (*text).token [ 0 ] = '\0';
  (*text).line  [ 0 ] = '\0';
  (*text).eof         = TRUE;
  (*text).data        = NULL;

  /* Check for a valid file name. */
  if ( (*text).name [ 0 ] == '\0' )  return;

  /* Open the text file in read mode. */
  if ( ( (*text).data = fopen ( (*text).name, "r" ) ) == NULL )

    printf ( "Could not open '%s'\n", (*text).name );
  else
    (*text).eof = FALSE;

  /* Get the first text line. */
  get_line ( text );

}  /* open_text_file */


/* This function promts for a file name. */
prompt_file ( text, prompt )
t_text  *text;		/* ASCII text file */
char    prompt [];	/* request prompt */
{
  (*text).name [ 0 ] = '\0';
  (*text).eof = TRUE;

  /* Prompt for a valid file name. */
  while ( ( (*text).eof == TRUE ) && ( d_strcmp ( (*text).name, "exit" ) != 0 ) )
  {
    printf ( "%s or 'exit' ", prompt );

    scanf ( "%s", (*text).name );

    if ( d_strcmp ( (*text).name, "exit" ) != 0 )

      open_text_file ( text );
  }  /* while */

  printf ( "\n" );
}  /* promp_file */


/* Reverse string s in place. */
reverse(s)
char s[];
{
  int  c, i, j;

  for (i = 0, j = d_strlen(s)-1; i < j; i++, j--)
  {
    c    = s[i];
    s[i] = s[j];
    s[j] = c;
  }  /* for */
}  /* reverse */


/* This function concatenates t to the end of s. */
d_strcat (s, t)
char  s[], t[];		/* s must be big enough */
{
  int  i = 0, j = 0;


  /* Find the end of s. */
  while ( s [ i ] != '\0' )  i++;

  /* Copy t. */
  while ( ( s [ i++ ] = t [ j++ ] ) != '\0' )  
    ;
}  /* d_strcat */


/* Return <0 if s<t, 0 if s==t, >0 if s>t */
d_strcmp (s, t)
char *s, *t;
{
  for ( ; *s == *t; s++, t++)

    if (*s == '\0')  return (0);

  return (*s - *t);
}  /* d_strcmp */


/* Copy t to s */
d_strcpy (s, t)
char *s, *t;
{
  while (*s++ = *t++)  ;
}  /* d_strcpy */


/* This function returns the index of t in s or index of '\0'. */
d_stridx (s, t)
char s[], t[];
{
  int  i, j, k;

  for (i = 0; s [ i ] != '\0'; i++ )
  {
    for ( j = i, k = 0; t [ k ] != '\0' && s [ j ] == t [ k ]; j++, k++ )  ;

    if ( t [ k ] == '\0' )  return ( i );
  }  /* for */

  return ( i );
}  /* d_stridx */


/* Return length of string s */
d_strlen (s)
char *s;
{
  char *p = s;

  while (*p != '\0')  p++;

  return (p-s);
}  /* d_strlen */


