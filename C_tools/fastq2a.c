
#include <stdio.h>


/*****************************************************************************/
#define  TRUE		1
#define  FALSE		0

#define  BLOCK_SIZE	60	/* output block size */

#define  MAX_ASCII      256     /* maximum ASCII characters */

#define  MAX_BASES      1000000  /* maximum DNA sequence length */

#define  MAX_LINE	2012	/* maximum line length */

#define  MAX_SEQ_BLOCK  MAX_BASES + 200000	/* maximum DNA sequence block length */

#define  MIN_SEQ	12	/* minimum sequence length */



/*****************************************************************************/
/* sequence. */
typedef  struct {
  long  total;			/* sequence length */
  char  name [ MAX_LINE ];	/* file name */
  char  base [ MAX_BASES ];	/* DNA bases */
  char  qual [ MAX_BASES ];	/* DNA bases quality codes */
} t_seq;


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


/*****************************************************************************/
static  int  base_map [ 123 ] = { 0,

/* 1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/*26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/* 51  52  53  54  55  56  57  58  59  60  61  62  63  64 */
   64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,

/* A   B  C   D   E   F  G   H   I   J   K   L   M */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* N   O   P   Q   R   S  T  U   V   W   X   Y   Z */
  64, 64, 64, 64, 64, 64, 0, 0, 64, 64, 64, 64, 64,

  64, 64, 64, 64, 64, 64,

/* a   b  c   d   e   f  g   h   i   j   k   l   m */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* n   o   p   q   r   s  t  u   v   w   x   y   z */
  64, 64, 64, 64, 64, 64, 0, 0, 64, 64, 64, 64, 64
};


static  int  base_map_3 [ 123 ] = { 0,

/* 1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/*26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/* 51  52  53  54  55  56  57  58  59  60  61  62  63  64 */
   64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,

/* A   B  C   D   E   F  G   H   I   J   K   L   M */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* N   O   P   Q   R   S  T  U   V   W   X   Y   Z */
  64, 64, 64, 64,  2, 64, 0, 0, 64, 64, 64,  0, 64,

  64, 64, 64, 64, 64, 64,

/* a   b  c   d   e   f  g   h   i   j   k   l   m */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* n   o   p   q   r   s  t  u   v   w   x   y   z */
  64, 64, 64, 64,  2, 64, 0, 0, 64, 64, 64,  0, 64
};


/* Genetic code */
static  char  codon_map [ 64 ] = {
  'F', 'F', 'L', 'L',     'S', 'S', 'S', 'S',  
  'Y', 'Y', '*', '*',     'C', 'C', '*', 'W',

  'L', 'L', 'L', 'L',     'P', 'P', 'P', 'P',
  'H', 'H', 'Q', 'Q',     'R', 'R', 'R', 'R',

  'I', 'I', 'I', 'M',     'T', 'T', 'T', 'T',
  'N', 'N', 'K', 'K',     'S', 'S', 'R', 'R',

  'V', 'V', 'V', 'V',     'A', 'A', 'A', 'A',
  'D', 'D', 'E', 'E',     'G', 'G', 'G', 'G'  };


static  char  complement [ 123 ] = {

/*    1    2    3    4    5    6    7    8    9   10 */
' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 20 */
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 30 */
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 40 */
     ' ', ' ', ' ', ' ', '-', '.', ' ', ' ', ' ', ' ',  /* 50 */
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 60 */
     ' ', ' ', ' ', ' ',                                /* 64 */

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
   'T', 'V', 'G', 'H', ' ', ' ', 'C', 'D', ' ', ' ', 'M', ' ', 'K',

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
   'N', ' ', ' ', ' ', 'Y', 'S', 'A', 'A', 'B', 'W', 'N', 'R', ' ',

   ' ', ' ', ' ', ' ', ' ', ' ',

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
   't', 'v', 'g', 'h', ' ', ' ', 'c', 'd', ' ', ' ', 'm', ' ', 'k',

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
   'n', ' ', ' ', ' ', 'y', 's', 'a', 'a', 'b', 'w', 'n', 'r', ' '
};


static  int  dna_mask [ 123 ] = { 0,

/*    1    2    3    4    5    6    7    8    9   10 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 20 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 30 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 40 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 50 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 60 */
      0,   0,   0,   0,   

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
    1, 0xE,   4, 0xB,   0,   0,   2, 0xD,   0,   0, 0xA,   0,   5,

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
  0xF,   0,   0,   0,   3,   6,   8,   8,   7,   9, 0xF, 0xC,   0,

    0,   0,   0,   0,   0,   0,

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
    1, 0xE,   4, 0xB,   0,   0,   2, 0xD,   0,   0, 0xA,   0,   5,

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
  0xF,   0,   0,   0,   3,   6,   8,   8,   7,   9, 0xF, 0xC,   0 };




/*****************************************************************************/
main ()
{
  t_text  fasta;		/* library of DNA sequences */
  t_text  n_seqs;		/* file of DNA sequences to review */
  t_text  source;		/* FASTQ library file of sequences */
  t_seq   seq;         		/* DNA sequence */
  FILE    *fopen ();		/* file open function */

  char    description [ MAX_LINE ];			/* description line */
  static  char    seq_block [ MAX_SEQ_BLOCK ];		/* DNA sequence lines */
  static  char    qual_block [ MAX_SEQ_BLOCK ];		/* DNA sequence quality lines */


  printf ( "This is the FASTQ --> FASTA format program.\n\n" );

  /* Prompt for the input file name. */
  prompt_file ( &source, "What is the FASTQ file name?" );

  /* Open the FASTA library ".fasta" output file. */
  d_strcpy ( fasta.name, source.name );
  fasta.name [ d_stridx ( fasta.name, "\n" ) ] = '\0';
  fasta.name [ d_stridx ( fasta.name, "." ) ] = '\0';
  d_strcpy ( n_seqs.name, fasta.name );
  d_strcat ( fasta.name, ".fa" );
  fasta.data = fopen ( fasta.name, "w" );

  d_strcat ( n_seqs.name, ".fa_ns" );
  n_seqs.data = fopen ( n_seqs.name, "w" );

  /* Process the sequences. */
  while ( source.eof != TRUE )
  {
    while ( ( source.eof != TRUE ) && ( source.line [ 0 ] != '@' ) )
    {
      printf ( "unknown: %s\n", source.line );
      fprintf ( n_seqs.data, "%s", source.line );
      get_line ( &source );
    }  /* while */

    /* Save the description line. */
    d_strcpy ( description, source.line );
    printf ("seq header: %s", source.line );

    /* Read in the DNA sequence. */
    seq_block [ 0 ] = '\0';
    seq.total = 0;

    if ( source.eof != TRUE )
    {
      get_next_DNA_seq ( &source, &seq, seq_block );

      d_strcpy ( seq.name, &(description [ 1 ]) );
      seq.name [ d_stridx ( seq.name, " " ) ] = '\0';
      /* printf ( "%s\n", seq.name ); */

      /* Read in the sequence quality information. */
      get_line ( &source );
      printf ("qual header: %s\n", source.line );
      get_next_DNA_qual ( &source, &seq, qual_block );
    }  /* if */

    if ( seq.total > 0 )
    {
      /* Write out the description line. */
      fprintf ( fasta.data, "%s", description );

      /* Write out the DNA sequence. */
      write_DNA_seq ( &seq, 0, seq.total - 1, &fasta );
    }  /* if */
    else
    {
      fprintf ( n_seqs.data, "%s", description );
      fprintf ( n_seqs.data, "%s", seq_block );
    }  /* else */
  }  /* while */

  fclose ( fasta.data );
  fclose ( source.data );
  fclose ( n_seqs.data );

  printf ( "\nEnd of FASTQ to FASTA library reformating program.\n" );
}  /* main */


/*****************************************************************************/
/* This function writes out a Fasta format name line. */
write_fasta_name ( out, name, first, length, size )
t_text  *out;		/* output file */
char    name [];	/* sequence name */
int	first;		/* First base of sequence segment */
int	length;		/* Length of sequence */
int	size;		/* Size of sequence segment */
{
  /* Check for NULL file. */
  if ( (*out).data == NULL )  return;

  /* Pass useful information on the name line. */
  fprintf ( (*out).data, ">%s\n", name );
}  /* write_fasta_name */


/*****************************************************************************/
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


/*****************************************************************************/
/* This function gets the next text character. */
get_char ( text )
t_text  *text;		/* ASCII text file */
{
  if ( (*text).next == (char) EOF )  return; 
  (*text).next = ' ';

  /* Get the next sequence character. */
  if ( ( (*text).line [ (*text).line_index ] != '\n' ) &&
       ( (*text).line [ (*text).line_index ] != '\0' ) )

    (*text).next = (*text).line [ (*text).line_index++ ];

  /* skip white space */
  while ( ( ( (*text).next == ' '  ) ||
            ( (*text).next == '\n' ) ||
            ( (*text).next == '\t' ) ) && ( (*text).next != (char) EOF ) )

    if ( ( (*text).next == '\n' ) || ( (*text).next == '\0' ) )
    {
      if ( (*text).eof == TRUE )  (*text).next = (char) EOF;
      else  get_line ( text );
    }
    else
      (*text).next = (*text).line [ (*text).line_index++ ];

}  /* get_char */


/*****************************************************************************/
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


/*****************************************************************************/
/* This function gets the next text line. */
get_line ( text )
t_text  *text;		/* ASCII text file */
{
  int  c = 0, i = 0;


  if ( (*text).next == (char) EOF )  return;

  (*text).line_index = 0;

  /* Get the text line. */
  while ( ( i < MAX_LINE ) && ( ( c = getc ( (*text).data ) ) != EOF ) &&
          ( c != '\n' ) )
  {
    (*text).line [ i++ ] = c;
  }  /* while */

  /* Properly terminate the text line. */
  (*text).line [ i++ ] = '\n';
  (*text).line [ i   ] = '\0';

  /* Check for end of file. */
  if ( c == EOF )  (*text).eof = TRUE;

  /* Get the first character. */
  (*text).next = (*text).line [ 0 ];
  (*text).line_index++;
}  /* get_line */


/*****************************************************************************/
/* This function gets the next text token. */
get_token ( text )
t_text  *text;		/* ASCII text file */
{
  int  i = 0;


  /* skip white space */
  while ( ( ( (*text).next == ' '  ) ||
            ( (*text).next == '\n' ) ||
            ( (*text).next == '\t' ) ) && ( (*text).eof != TRUE ) )
  {
    if ( (*text).next == '\n' )  get_line ( text );
    else
      (*text).next = (*text).line [ (*text).line_index++ ];
  }  /* while */

  d_strcpy ( (*text).token, "" );
  if ( (*text).eof == TRUE )  return;

  /* Copy the token. */
  while ( ( ( (*text).next >= '0' ) && ( (*text).next <= '9' ) ) ||
          ( ( (*text).next >= 'a' ) && ( (*text).next <= 'z' ) ) ||
          ( ( (*text).next >= 'A' ) && ( (*text).next <= 'Z' ) ) ) 
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


/*****************************************************************************/
/* Convert n to characters in s. */
itoa (n, s)
char s[];
int n;
{
  int i, sign;

  if ((sign = n) < 0)  /* record sign */
    n = -n;

  /* Generate the digits in reverse order. */
  i = 0;
  do 
  {
    s[i++] = n % 10 + '0';	/* get next digit */
  }
  while ((n /= 10) > 0);	/* delete it */

  if (sign < 0)  s[i++] = '-';

  s[i] ='\0';

  reverse (s);
}  /* itoa */


/*****************************************************************************/
/* Convert c to lower case; ASCII only */
lower (c)
int c;
{
  if (c >= 'A' && c <= 'Z')

    return ( c + 'a' - 'A' );

  else

    return (c);
}  /* lower */


/*****************************************************************************/
/* This function capitalizes a string. */
strlower ( s )
char s [];		/* string */
{
  int  i = 0;


  while ( ( s [ i ] != '\0' ) && ( i < MAX_LINE ) )
  {
    s [ i ] = lower ( s [ i ] );
    i++;
  }  /* while */
}  /* strlower */


/*****************************************************************************/
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


/*****************************************************************************/
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
}  /* prompt_file */


/*****************************************************************************/
/* This function reads in a DNA sequence. */
get_next_DNA_seq ( text, seq, seq_block )
t_text	*text;		/* ASCII text file */
t_seq	*seq;		/* DNA sequence */
char    seq_block [];	/* block of sequence lines read */
{
  int  index;
  int  unknown = FALSE;			/* flag for unknown characters */


  /* Read in the DNA sequence. */
  (*seq).total = 0;

  /* Advance past the description line. */
  (*text).line [ 0 ] = '\0';

    /* Get the next line of the FASTA library sequence file. */
    get_line ( text );

    /* Check for a Perl script at the end of the file. */
    if ( ( (*text).line [ 0 ] == '#' ) && ( (*text).line [ 1 ] == '!' ) )
      return;

    /* Add the line to the sequence block. */
    if ( ( (*text).line [ 0 ] != '\0' ) && 
         ( (*text).line [ 0 ] != '\n' ) &&
         ( (*text).line [ 0 ] != '+' ) &&
         ( (*text).line [ 0 ] != '@'  ) )
      d_strcat ( seq_block, (*text).line );

    /* Add the current line to the sequence. */
    index = 0;
    while ( ( (*text).line [ index ] != '\0' ) && 
            ( (*text).line [ index ] != '\n' ) )
    {
      if ( ( ( (*text).line [ index ] >= 'a' ) && ( (*text).line [ index ] <= 'z' ) ) ||
           ( ( (*text).line [ index ] >= 'A' ) && ( (*text).line [ index ] <= 'Z' ) ) )
      {
        if ( dna_mask [ (*text).line [ index ] ] != 0 )
          (*seq).base [ (*seq).total++ ] = (*text).line [ index ]; 
        else
        {
          unknown = TRUE;
          printf ( "Unknown DNA base '%c'\n", (*text).line [ index ] );
        }  /* else */

        if ( (*seq).total == MAX_BASES - 1 )
        {
          printf ( "Maximum number of sequence bases reached.\n" );
          return;
        }  /* if */
      }  /* if */
      else
      {
        if ( ( (*text).line [ index ] != ' ' ) ||
             ( (*text).line [ index ] != '\t' ) ||
             ( (*text).line [ index ] != '\n' ) ||
             ( (*text).line [ index ] != '\0' ) )
        {
          unknown = TRUE;
          printf ( "Unknown DNA base '%c'\n", (*text).line [ index ] );
        }  /* if */
      }  /* else */
      index++;
    }  /* while */

  /* if invalid sequence, don't return it. */
  if ( unknown == TRUE )  (*seq).total = 0;
}  /* get_next_DNA_seq */


/*****************************************************************************/
/* This function reads in a DNA sequence quality codes. */
get_next_DNA_qual ( text, seq, seq_block )
t_text	*text;		/* ASCII text file */
t_seq	*seq;		/* DNA sequence */
char    seq_block [];	/* block of sequence lines read */
{
  int  index;


  /* Read in the DNA sequence. */
  (*seq).total = 0;

  /* Advance past the description line. */
  (*text).line [ 0 ] = '\0';

  /* Get the next line of the FASTA library sequence file. */
  get_line ( text );

  /* Add the line to the sequence block. */
  if ( ( (*text).line [ 0 ] != '\0' ) && 
       ( (*text).line [ 0 ] != '\n' ) )
    d_strcat ( seq_block, (*text).line );

  /* Add the current line to the sequence. */
  index = 0;
  while ( ( (*text).line [ index ] != '\0' ) && 
          ( (*text).line [ index ] != '\n' ) )
  {
    (*seq).qual [ (*seq).total++ ] = (*text).line [ index ]; 

    if ( (*seq).total == MAX_BASES - 1 )
    {
      printf ( "Maximum number of sequence base qualities reached.\n" );
      return;
    }  /* if */
    index++;
  }  /* while */


  /* Get the next line of the FASTA library sequence file. */
  get_line ( text );

}  /* get_next_DNA_qual */


/*****************************************************************************/
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


/*****************************************************************************/
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


/*****************************************************************************/
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


/*****************************************************************************/
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


/*****************************************************************************/
/* Return length of string s */
d_strlen (s)
char *s;
{
  char *p = s;

  while (*p != '\0')  p++;

  return (p-s);
}  /* d_strlen */


/*****************************************************************************/
/* This function writes out a DNA sequence block. */
write_DNA_seq ( seq, first, last, text )
t_seq	*seq;		/* DNA sequence */
long	first;		/* first base of block */
long	last;		/* last base of block */
t_text  *text;		/* output text file */
{
  int   flag;
  long  index;		/* sequence index */


  /* Check for NULL output file. */
  if ( (*text).data == NULL )  return;
  
  /* Print out the DNA block. */
  for ( index = first; index <= last; index++ )
  {
    fprintf ( (*text).data, "%c", (*seq).base [ index ] );
    flag = 1;

    if ( ( ( index - first + 1 ) % 50 ) == 0 )
    {
      fprintf ( (*text).data, "\n" );

      flag = 0;
    }  /* if */
  }  /* for */

  if ( flag == 1 )

    fprintf ( (*text).data, "\n" );
}  /* write_DNA_seq */


/*****************************************************************************/
/* This function reverses and complements a DNA sequence. */
reverse_seq ( seq, rev )
t_seq	*seq;		/* DNA sequence */
t_seq	*rev;		/* reversed & complemented DNA sequence */
{
  int  index;		/* base index */


  (*rev).total = (*seq).total;

  d_strcpy ( (*rev).name, (*seq).name );

  for ( index = 0; index < (*seq).total; index++ )

    (*rev).base [ (*rev).total - index - 1 ] = 
      complement [ (*seq).base [ index ] ];
}  /* reverse_seq */


