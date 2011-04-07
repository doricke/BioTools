
// import Base;
// import DnaWord;


/******************************************************************************/

public class CommonDnaWord extends Object
{


/******************************************************************************/

  private   String  dna_word = "";	// DNA bases word

  private   int  count = 0;		// count of this common word

  private   int [] positions = null;	// DNA bases word start position

  private   int  previous = -1;		// index of previous common word

  private   int  previous_count = 0;	// count of previous ordered words

  private   int  word_size = 4;		// DNA bases per word

  private   int  total = 0;		// number of sequence positions

  private   int  word = 0;		// DNA bases word


/******************************************************************************/
  // Constructor CommonDnaWord
  public CommonDnaWord ()
  {
    initialize ();
  }  // constructor CommonDnaWord


/******************************************************************************/
  // Constructor CommonDnaWord
  public CommonDnaWord 
      ( int word_value
      , String peptide
      , byte size 
      , int total_value
      )
  {
    initialize ();

    dna_word = peptide;
    count = 1;
    word_size = size;
    total = total_value;
    word = word_value;

    positions = new int [ total ];
    for ( int i = 0; i < total; i++ )

      positions [ i ] = 0;
  }  // constructor CommonDnaWord


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    dna_word = "";
    count = 0;
    positions = null;
    previous = -1;
    previous_count = 0;
    word_size = 4;
    total = 0;
    word = 0;
  }  // method initialize 


/******************************************************************************/
  public int getWord ()
  {
    return word;
  }  // method getWord


/******************************************************************************/
  public String getDnaBases ()
  {
    return dna_word;
  }  // method getDnaBases


/******************************************************************************/
  public int getCount ()
  {
    return count;
  }  // method getCount


/******************************************************************************/
  public int getPosition ( int i )
  {
    if ( ( i >= 0 ) && ( i < total ) )  return positions [ i ];
    return 0;
  }  // method getPosition


/******************************************************************************/
  public int [] getPositions ()
  {
    return positions;
  }  // method getPosition


/******************************************************************************/
  public int getPrevious ()
  {
    return previous;
  }  // method getPrevious


/******************************************************************************/
  public int getPreviousCount ()
  {
    return previous_count;
  }  // method getPreviousCount


/******************************************************************************/
  public int getWordSize ()
  {
    return word_size;
  }  // method getWordSize


/******************************************************************************/
  public void setBases ( String value )
  {
    dna_word = value;
  }  // method setBases


/******************************************************************************/
  public void setCount ( int value )
  {
    count = value;
  }  // method setCount


/******************************************************************************/
  public void setPosition ( int value, int index )
  { 
    if ( ( index >= 0 ) && ( index < total ) )

      positions [ index ] = value;
  }  // method setPosition


/******************************************************************************/
  public void setPrevious ( int value )
  {
    previous = value;
  }  // method setPrevious


/******************************************************************************/
  public void setPreviousCount ( int value )
  {
    previous_count = value;
  }  // method setPreviousCount


/******************************************************************************/
  public void setWordSize ( byte value )
  {
    word_size = value;
  }  // method setWordSize


/******************************************************************************/
  public void addDnaWord ( DnaWord word_dna )
  {
    // Check if this common word is blank.
    if ( count == 0 )
    {
      dna_word = word_dna.getDnaWord ();
      word_size = word_dna.getWordSize ();
      word = word_dna.getWord ();
    }  // if
  }  // method addDnaWord


/******************************************************************************/
  // This method increases the current word 5' by one base
  public void grow5Prime ()
  {
    word_size++;
    for ( int i = 0; i < positions.length; i++ )

      positions [ i ]--;
  }  // method grow5Prime


/******************************************************************************/
  public String toString ()
  {
    StringBuffer str = new StringBuffer ( 240 );
    str.append ( dna_word + "\t" + word + "\tcount = " + count + "\t" );

    str.append ( "[" );
    if ( positions != null )

      for ( int i = 0; i < positions.length; i++ )
      {
        str.append ( positions [ i ] );

        if ( i + 1 < positions.length )

          str.append ( ", " );
      }  // for

    str.append ( "]" );
    str.append ( "\tw.s. " + word_size );
    str.append ( "\tprev. " + previous + "\tp.c. " + previous_count );

    return str.toString ();
  }  // method toString


/******************************************************************************/
  public static void main ( String [] args )
  {
    CommonDnaWord dna_word = new CommonDnaWord ();
    dna_word.setWordSize ( (byte) 3 );
    dna_word.setBases ( "AAA" );
    System.out.println ( dna_word.toString () );
    dna_word.setBases ( "ACD" );
    System.out.println ( dna_word.toString () );
    dna_word.setBases ( "ILM" );
    System.out.println ( dna_word.toString () );
    dna_word.setBases ( "k.z" );
    System.out.println ( dna_word.toString () );
  }  // method main


/******************************************************************************/

}  // class CommonDnaWord
