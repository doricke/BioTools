
// import Amino;

/******************************************************************************/
/**
  This class models a hashed word of amino acids.
  
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

public class AminoWord extends Object
{


/******************************************************************************/

  private   String  aminos = "";    	// amino acids

  private   AminoWord  common = null;	// matched common word

  private   int  count = 0;		        // count of ordered previous common words

  private   int  position = 0;	 	    // amino acid word start position

  private   int  previous = -1;	        // index of previous common word

  private   byte  size = 4;		        // amino acids per word

  private   int  word = 0;		        // amino acids word

  private   int  word_node = -1;	    // WordNode array index


/******************************************************************************/
  // Constructor AminoWord
  public AminoWord ()
  {
    initialize ();
  }  // constructor AminoWord


/******************************************************************************/
  // Constructor AminoWord
  public AminoWord ( int word_value, String peptide, int pos, byte word_size )
  {
    initialize ();

    aminos = peptide;
    position = pos;
    size = word_size;
    word = word_value;
  }  // constructor AminoWord


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    aminos = "";
    common = null;
    count = 0;
    position = 0;
    previous = -1;
    size = 4;
    word = 0;
    word_node = -1;
  }  // method initialize 


/******************************************************************************/
  public int getWord ()
  {
    return word;
  }  // method getWord


/******************************************************************************/
  public String getAminos ()
  {
    return aminos;
  }  // method getAminos


/******************************************************************************/
  public AminoWord getCommon ()
  {
    return common;
  }  // method getCommon


/******************************************************************************/
  public AminoWord getCopy ()
  {
    AminoWord copy = new AminoWord ();

    copy.setSize ( size );
    copy.setAminos ( aminos );
    copy.setCommon ( common );
    copy.setCount ( count );
    copy.setPosition ( position );
    copy.setPrevious ( previous );
    copy.setWordNode ( word_node );

    return copy;
  }  // method getCopy


/******************************************************************************/
  public int getCount ()
  {
    return count;
  }  // method getCount


/******************************************************************************/
  public int getPosition ()
  {
    return position;
  }  // method getPosition


/******************************************************************************/
  public int getPrevious ()
  {
    return previous;
  }  // method getPrevious


/******************************************************************************/
  public byte getSize ()
  {
    return size;
  }  // method getSize


/******************************************************************************/
  public int getWordNode ()
  {
    return word_node;
  }  // method getWordNode


/******************************************************************************/
  // This method checks for 3 common amino acids in a word.
  public boolean isGoodWord ( String word )
  {
    for ( int i = 0; i < size - 2; i++ )

      for ( int j = i + 1; j < size - 1; j++ )

        for ( int k = j + 1; k < size; k++ )

          if ( ( word.charAt ( i ) == word.charAt ( j ) ) &&
               ( word.charAt ( j ) == word.charAt ( k ) ) )
          {
            // System.out.println ( "isGood! " + word + " i= " + i + " j= " + j + " k= " + k );
            return false;
          }  // if
    return true;
  }  // method isGoodWord


/******************************************************************************/
  public void setAminos ( String value )
  {
    aminos = value;

    computeWord ();
  }  // method setAminos


/******************************************************************************/
  public void setCommon ( AminoWord value )
  {
    // System.out.println ( "setCommon: " + aminos );

    common = value;
  }  // method setCommon


/******************************************************************************/
  public void setCount ( int value )
  {
    count = value;
  }  // method setCount


/******************************************************************************/
  public void setPosition ( int value )
  {
    position = value;
  }  // method setPosition


/******************************************************************************/
  public void setPrevious ( int value )
  {
    previous = value;
  }  // method setPrevious


/******************************************************************************/
  public void setSize ( byte value )
  {
    size = value;
  }  // method setSize


/******************************************************************************/
  public void setWordNode ( int value )
  {
    word_node = value;
  }  // method setWordNode


/******************************************************************************/
  private void computeWord ()
  {
    Amino amino = new Amino ();

    if ( aminos.length () != size )
    {
      System.out.println ( "Strange aminos length: '" + aminos + "', len = " + aminos.length () );
    }  // if

    // Hash the current amino acids into an integer word.
    word = 0;
    for ( int i = 0; i < size; i++ )

      if ( i + 1 <= aminos.length () )
      {
        amino.setAminoAcid ( aminos.charAt ( i ) );
        word += amino.getOrdinal ();
        if ( i + 1 < size )  word *= 32;		// shift word for next amino acid
      }  // for
  }  // method computeWord


/******************************************************************************/
  public String toString ()
  {
    return aminos + "\t" + word + "\t" + position + "\tc " + count + "\tp " + previous;
  }  // method toString


/******************************************************************************/
  public static void main ( String [] args )
  {
    AminoWord amino_word = new AminoWord ();
    amino_word.setSize ( (byte) 3 );
    amino_word.setAminos ( "AAA" );
    System.out.println ( amino_word.toString () );
    amino_word.setAminos ( "aaa" );
    System.out.println ( amino_word.toString () );
    amino_word.setAminos ( "ACD" );
    System.out.println ( amino_word.toString () );
    amino_word.setAminos ( "ILM" );
    System.out.println ( amino_word.toString () );
    amino_word.setAminos ( "k.z" );
    System.out.println ( amino_word.toString () );
  }  // method main


/******************************************************************************/

}  // class AminoWord
