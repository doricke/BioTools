

/******************************************************************************/
/**
  This class models an amino acid.
  
  @author       Darrell O. Ricke, Ph.D.  (mailto: d_ricke@yahoo.com)
  Copyright:    Copyright (c) 1998 Paragon Software
  License:      GNU GPL license (http://www.gnu.org/licenses/gpl.html)  
  Contact:      Paragon Software, 1314 Viking Blvd., Cedar, MN 55011
 
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/******************************************************************************/

public class Amino extends Object
{


/******************************************************************************/

  private   char  amino_acid = ' ';	        // amino acid

  private   boolean  is_amino_acid = false;	// is amino acid flag

  private   boolean  is_gap = false;		// is gap flag

  private   byte  ordinal = 0;			    // amino acid ordinal number


/******************************************************************************/
  // Constructor Amino
  public Amino ()
  {
    initialize ();
  }  // constructor Amino


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    amino_acid = ' ';
    is_amino_acid = false;
    is_gap = false;
    ordinal = 0;
  }  // method initialize 


/******************************************************************************/
  public char getAminoAcid ()
  {
    return amino_acid;
  }  // method getAminoAcid


/******************************************************************************/
  public boolean getIsAminoAcid ()
  {
    return is_amino_acid;
  }  // method getIsAminoAcid


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
  public boolean isAminoAcid ()
  {
    return is_amino_acid;
  }  // method isAminoAcid


/******************************************************************************/
  public boolean isGap ()
  {
    return is_gap;
  }  // method isGap


/******************************************************************************/
  public void setAminoAcid ( char value )
  {
    amino_acid = value;

    setOrdinal ( amino_acid );
  }  // method setAminoAcid


/******************************************************************************/
  private void setOrdinal ( char amino )
  {
    is_amino_acid = true;
    is_gap = false;

    if ( ( amino >= 'A' ) && ( amino <= 'Z' ) )
    {
      ordinal = (byte) ( amino - 'A' + 1 );
    }  // if
    else
      if ( ( amino >= 'a' ) && ( amino <= 'z' ) )
      {
        ordinal = (byte) ( amino - 'a' + 1 );
      }  // if
      else
        // Check for a gap character.
        if ( ( amino == '.' ) || ( amino == '-' ) || ( amino == ' ' ) )
        {
          is_gap = true;
          is_amino_acid = false;
          ordinal = 27;
        }  // if
        else
        {
          System.out.println ( "Amino.setOrdinal: *Warning* unknown amino acid '" + amino + "'" );
          is_amino_acid = false;
        }  // else
  }  // method setOrdinal


/******************************************************************************/
  public static void main ( String [] args )
  {
    Amino amino = new Amino ();

    for ( char c = 'A'; c <= 'Z'; c++ )
    {
      amino.setAminoAcid ( c );
      System.out.println ( "Amino " + c + ", ordinal = " + amino.getOrdinal () );
    }  // for

    System.out.println ();
    for ( char c = 'a'; c <= 'z'; c++ )
    {
      amino.setAminoAcid ( c );
      System.out.println ( "Amino " + c + ", ordinal = " + amino.getOrdinal () );
    }  // for

    System.out.println ();
    amino.setAminoAcid ( '.' );
    System.out.println ( "Amino ., ordinal = " + amino.getOrdinal () );
    amino.setAminoAcid ( '-' );
    System.out.println ( "Amino -, ordinal = " + amino.getOrdinal () );

  }  // method main


/******************************************************************************/

}  // class Amino
