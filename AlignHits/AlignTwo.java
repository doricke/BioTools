

/******************************************************************************/
/**
  This class aligns two protein sequences.
  
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

public class AlignTwo extends Object
{


/******************************************************************************/

  public  final static byte ANCHOR_5 = 1;	// 5' sequence end anchor

  public  final static byte ANCHOR_3 = 2;	// 3' sequence end anchor

  public  final static byte INTERNAL = 3;	// internal sequence 

  public  final static byte FULL_LENGTH = 4;	// full length sequence


/******************************************************************************/

  // alignment for first sequence
  private   StringBuffer alignment1 = new StringBuffer ( 3000 );

  // alignment for second sequence
  private   StringBuffer alignment2 = new StringBuffer ( 3000 );

  private   byte  anchor = 0;			// alignment anchor location

  private   String  sequence1 = "";		// first sequence to align

  private   String  sequence2 = "";		// second sequence to align

  private   byte  word_size = 3;		// comparison word size 


/******************************************************************************/
  // Constructor AlignTwo
  public AlignTwo ()
  {
    initialize ();
  }  // constructor AlignTwo


/******************************************************************************/
  // Constructor AlignTwo
  public AlignTwo ( String seq1, String seq2, byte w_size, byte anchor_loc )
  {
    initialize ();
    setWordSize ( w_size );
    setAnchor ( anchor_loc );
    align ( seq1, seq2 );
  }  // constructor AlignTwo


/******************************************************************************/
  public void reset ()
  {
    alignment1.setLength ( 0 );
    alignment2.setLength ( 0 );
    sequence1 = "";
    sequence2 = "";
  }  // method rest


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    reset ();
    anchor = 0;
    word_size = 4;
  }  // method initialize 


/******************************************************************************/
  public String getAlignment1 ()
  {
    return alignment1.toString ();
  }  // method getAlignment1


/******************************************************************************/
  public String getAlignment2 ()
  {
    return alignment2.toString ();
  }  // method getAlignment2


/******************************************************************************/
  public byte getAnchor ()
  {
    return anchor;
  }  // method getAnchor


/******************************************************************************/
  public String getSequence1 ()
  {
    return sequence1;
  }  // method getSequence1


/******************************************************************************/
  public String getSequence2 ()
  {
    return sequence2;
  }  // method getSequence2


/******************************************************************************/
  public byte getWordSize ()
  {
    return word_size;
  }  // method getWordSize


/******************************************************************************/
  public void setAnchor ( byte value )
  {
    anchor = value;
  }  // method setAnchor


/******************************************************************************/
  public void setSequence1 ( String value )
  {
    sequence1 = value;
  }  // method setSequence1


/******************************************************************************/
  public void setSequence2 ( String value )
  {
    sequence2 = value;
  }  // method setSequence2


/******************************************************************************/
  public void setWordSize ( byte value )
  {
    word_size = value;
  }  // method setWordSize


/******************************************************************************/
  private void copyWord ( int start1, int end1, int start2, int end2 )
  {
    for ( int i = start1; i <= end1 + word_size - 1; i++ )
      alignment1.append ( sequence1.charAt ( i - 1 ) );

    for ( int i = start2; i <= end2 + word_size - 1; i++ )
      alignment2.append ( sequence2.charAt ( i - 1 ) );
  }  // method copyWord


/******************************************************************************/
  private void evenAlignments
      ( int   len1
      , int   len2
      )
  {
    // Even out the sequence lengths with gaps.
    if ( len1 > len2 )
    {
      for ( int i = len2 + 1; i <= len1; i++ )
        alignment2.append ( "-" );
    }  // if
    else
      if ( len1 < len2 )
      {
        for ( int i = len1 + 1; i <= len2; i++ )
          alignment1.append ( "-" );
      }  // if
  }  // method evenAlignments


/******************************************************************************/
  private void subAlign 
      ( int   start1
      , int   end1
      , int   start2
      , int   end2
      , byte  new_anchor 
      )
  {
    int len1 = end1 - start1;
    int len2 = end2 - start2;

    // Minimize gaps rule.
    if ( ( len1 == len2 ) && ( len1 <= 10 ) && ( len1 > 0 ) )
    {
      alignment1.append ( sequence1.substring ( start1 - 1, end1 - 1 ) );
      alignment2.append ( sequence2.substring ( start2 - 1, end2 - 1 ) );
      return;
    }  // if

    // Check for zero length subsequences.
    if ( len1 <= 0 )
    {
      // Fill in the missing letters with gaps.
      for ( int i = 0; i < len2; i++ )
      {
        alignment1.append ( "-" );
        alignment2.append ( sequence2.charAt ( start2 - 1 + i ) );
      }  // for

      return;
    }  // if

    if ( len2 <= 0 )
    {
      // Fill in the missing letters with gaps.
      for ( int i = 0; i < len1; i++ )
      {
        alignment2.append ( "-" );
        alignment1.append ( sequence1.charAt ( start1 - 1 + i ) );
      }  // for

      return;
    }  // if

    // Check for single amino acid mismatch.
    if ( ( len1 == 1 ) && ( len2 == 1 ) )
    {
      alignment1.append ( sequence1.charAt ( start1 - 1 ) );
      alignment2.append ( sequence2.charAt ( start2 - 1 ) );

      return;
    }  // if

    if ( ( word_size <= 3 ) && ( new_anchor == ANCHOR_3 ) )
    {
      // Just copy the sequence.
      alignment1.append ( sequence1.substring ( start1 - 1, end1 - 1 ) );
      alignment2.append ( sequence2.substring ( start2 - 1, end2 - 1 ) );

      // Even out the sequence lengths with gaps.
      evenAlignments ( len1, len2 );

      return;
    }  // if

    if ( ( word_size <= 3 ) && ( new_anchor == ANCHOR_5 ) )
    {
      // Even out the sequence lengths with gaps.
      evenAlignments ( len1, len2 );

      // Just copy the sequence.
      alignment1.append ( sequence1.substring ( start1 - 1, end1 - 1 ) );
      alignment2.append ( sequence2.substring ( start2 - 1, end2 - 1 ) );

      return;
    }  // if

    // Check for smallest word size.
    // if ( word_size <= 2 )
    {
      // Just copy the sequence.
      alignment1.append ( sequence1.substring ( start1 - 1, end1 - 1 ) );
      alignment2.append ( sequence2.substring ( start2 - 1, end2 - 1 ) );

      // Even out the sequence lengths with gaps.
      evenAlignments ( len1, len2 );

      return;
    }  // if

    // Use a smaller word size.

    // Decrease the word size if the subsequences are short.
/*
    int w_size = word_size - 1;
    if ( w_size > len1 )  w_size = len1;
    if ( w_size > len2 )  w_size = len2;

    AlignTwo smaller = new AlignTwo ( sequence1.substring ( start1 - 1, end1 - 1 )
                                    , sequence2.substring ( start2 - 1, end2 - 1 )
                                    , (byte) ( w_size )
                                    , new_anchor
                                    );

    alignment1.append ( smaller.getAlignment1 () );
    alignment2.append ( smaller.getAlignment2 () );

    smaller.initialize ();
    smaller = null;
*/
  }  // method subAlign


/******************************************************************************/
  private byte getNewAnchor ( byte anchor_end )
  {
    if ( anchor == FULL_LENGTH )  return anchor_end;

    return anchor;
  }  // method getNewAnchor


/******************************************************************************/
  public void align ()
  {
    // Align sequences using hash words.
    AminoWords words1 = new AminoWords ( sequence1, word_size );
    AminoWords words2 = new AminoWords ( sequence2, word_size );

    // Match the common words between the two sequences.
    words1.matchCommonWords ( words2 );
    AminoWord [] sorted_words = words1.getSortedCommonWords ();

    // Anchor the start of full length sequences to 5'.
    byte new_anchor = getNewAnchor ( ANCHOR_5 );

    int start1 = 1;
    int start2 = 1;
    int end1;
    int end2;

    if ( sorted_words != null )
    {
      // Check for enough of an alignment to make it worth while.
      if ( sorted_words.length > 2 )
      { 
        for ( int i = 0; i < sorted_words.length; i++ )
        {
          end1 = sorted_words [ i ].getPosition ();
          end2 = sorted_words [ i ].getCommon ().getPosition ();
    
          // Check for letters before the common word.
          if ( ( start1 < end1 ) || ( start2 < end2 ) )
          {
            subAlign ( start1, end1, start2, end2, new_anchor );
            start1 = end1;
            start2 = end2;
          }  // if
    
          // Copy this word.
          copyWord ( start1, end1, start2, end2 );
    
          start1 = end1 + word_size;
          start2 = end2 + word_size;
    
          new_anchor = INTERNAL;	// internal sequence fragment
        }  // for
      }  // if
      else
      {
      }  // else
    }  // if

    // Anchor the start of full length sequences to 3'.
    new_anchor = getNewAnchor ( ANCHOR_3 );

    // Check for tails at the end of the sequences.
    end1 = sequence1.length () + 1;
    end2 = sequence2.length () + 1;

    if ( ( start1 <= end1 ) || ( start2 <= end2 ) )
      subAlign ( start1, end1, start2, end2, new_anchor );
  }  // method align


/******************************************************************************/
  public void align ( String seq1, String seq2 )
  {
    setSequence1 ( seq1 );
    setSequence2 ( seq2 );

    // Check for short sequences.
    if ( ( seq1.length () == seq2.length () ) &&
         ( seq1.length () < 50 ) )
    {
      alignment1.append ( seq1 );
      alignment2.append ( seq2 );
      return;
    }  // if

    // align ();
    subAlign ( 1, seq1.length () + 1, 1, seq2.length () + 1, anchor );

/*
    if ( seq1.length () != seq2.length () ) 
    {
      System.out.println ( "AlignTwo.align: seq1 = '" + seq1 + "' & seq2 = '" + seq2 + "'" );
      writeAlignment ();
    }  // if
*/
  }  // method align


/******************************************************************************/
  public void writeAlignment ()
  {
    for ( int i = 0; i < alignment1.length (); i += 50 )
    {
      System.out.println ();

      // Print out the first sequence.
      System.out.print ( (i+1) + "\t" );
      int end = i + 50;
      if ( end > alignment1.length () )  end = alignment1.length ();
      System.out.println ( alignment1.substring ( i, end ) + "\t" + end );

      // Print out the identity markers.
      System.out.print ( "\t" );
      for ( int j = i; j < end; j++ )
        if ( j < alignment2.length () )
        {
          if ( alignment1.charAt ( j ) == alignment2.charAt ( j ) )
            System.out.print ( "|" );
          else
            System.out.print ( " " );
        }  // if
      System.out.println ();

      // Print out the second sequence.
      System.out.print ( (i+1) + "\t" );
      if ( end > alignment2.length () )
        System.out.println ( alignment2.substring ( i ) + "\t" + end );
      else
        System.out.println ( alignment2.substring ( i, end ) + "\t" + end );
    }  // for
  }  // method writeAlignment


/******************************************************************************/
  public static void main ( String [] args )
  {
    AlignTwo app = new AlignTwo ( "AIGKRMLSTGVEQ", "AIGKMLTGVE", (byte) 3, FULL_LENGTH );
    app.writeAlignment ();
  }  // main


/******************************************************************************/

}  // class AlignTwo
