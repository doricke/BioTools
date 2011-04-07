

/******************************************************************************/

public class Base extends Object
{


/******************************************************************************/

  private   char  dna_base = ' ';		// DNA base 

  private   boolean  is_dna_base = false;	// is DNA base flag

  private   boolean  is_gap = false;		// is gap flag

  private   byte  ordinal = 0;			// amino acid ordinal number


/******************************************************************************/
  // Constructor Base
  public Base ()
  {
    initialize ();
  }  // constructor Base


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    dna_base = ' ';
    is_dna_base = false;
    is_gap = false;
    ordinal = 0;
  }  // method initialize 


/******************************************************************************/
  public char getDnaBase ()
  {
    return dna_base;
  }  // method getDnaBase


/******************************************************************************/
  public boolean getIsDnaBase ()
  {
    return is_dna_base;
  }  // method getIsDnaBase


/******************************************************************************/
  public boolean getIsGap ()
  {
    return is_gap;
  }  // method getIsGap


/******************************************************************************/
  public byte getOrdinal ()
  {
    return ordinal;
  }  // method getOrdinal


/******************************************************************************/
  public boolean isDnaBase ()
  {
    return is_dna_base;
  }  // method isDnaBase


/******************************************************************************/
  public boolean isGap ()
  {
    return is_gap;
  }  // method isGap


/******************************************************************************/
  public void setDnaBase ( char value )
  {
    dna_base = value;

    setOrdinal ( dna_base );
  }  // method setDnaBase


/******************************************************************************/
  private void setOrdinal ( char base )
  {
    is_dna_base = true;
    is_gap = false;
    ordinal = 0;		// defaults to A

    switch ( base )
    {
      case 'A':
      case 'a':
        break;

      case 'C':
      case 'c':
        ordinal = 1;
        break;

      case 'G':
      case 'g':
        ordinal = 2;
        break;

      case 'T':
      case 't':
      case 'U':
      case 'u':
        ordinal = 3;
        break;

      case '-':
      case '.':
        is_dna_base = false;
        is_gap = true;
        break;

      // For DNA base hashing, IUB codes are not treated as individual bases.
      default:
        is_dna_base = false;
    }  // switch
  }  // method setOrdinal


/******************************************************************************/
  public static void main ( String [] args )
  {
    Base base = new Base ();
    char bases [] = { 'A', 'C', 'G', 'T', 'a', 'c', 'g', 't', 'r', 'y', 'u', '-', '.' };

    for ( byte i = 0; i < bases.length; i++ )
    {
      base.setDnaBase ( bases [ i ] );
      System.out.println ( "Base " + bases [ i ] + ", ordinal = " + base.getOrdinal () );

      if ( base.isGap () == true )
        System.out.println ( "\tis gap!" );

      if ( base.isDnaBase () == false )
        System.out.println ( "\tis not [ACGT]" );

      base.initialize ();
    }  // for

  }  // method main


/******************************************************************************/

}  // class Base
