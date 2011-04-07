

/******************************************************************************/
/**
  This class uses the zipper algorithm to align two protein sequences. 
  
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

public class Zipper extends Object
{


/******************************************************************************/

  final static int MAX_SIZE = 10000;


/******************************************************************************/

  // alignment for first sequence
  private   StringBuffer alignment1 = new StringBuffer ( MAX_SIZE );

  // alignment for second sequence
  private   StringBuffer alignment2 = new StringBuffer ( MAX_SIZE );

  private   int  end1 = 0;			// end of aligned sequence1

  private   int  end2 = 0;			// end of aligned sequence2

  private   String  sequence1 = "";		// first sequence to align

  private   String  sequence2 = "";		// second sequence to align


/******************************************************************************/
  // Constructor Zipper
  public Zipper ()
  {
    initialize ();
  }  // constructor Zipper


/******************************************************************************/
  // Constructor Zipper
  public Zipper ( String seq1, String seq2 )
  {
    initialize ();
    setSequence1 ( seq1 );
    setSequence2 ( seq2 );
    align ();
  }  // constructor Zipper


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    alignment1.setLength ( 0 );
    alignment2.setLength ( 0 );
    end1 = 0;
    end2 = 0;
    sequence1 = "";
    sequence2 = "";
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
  private void evenAlignments ( char gap_char )
  {
    // Even out the sequence lengths with gaps.
    while ( alignment1.length () < alignment2.length () )

      alignment1.append ( gap_char );

    while ( alignment2.length () < alignment1.length () )

      alignment2.append ( gap_char );
  }  // method evenAlignments


/******************************************************************************/
  // This method adds any remaining sequence tails to the alignment.
  private void addTails ()
  {
    // Add the tail of the first sequence.
    if ( end1 < sequence1.length () )
      alignment1.append ( sequence1.substring ( end1 ) );
    end1 = sequence1.length ();

    // Add the tail of the first sequence.
    if ( end2 < sequence2.length () )
      alignment2.append ( sequence2.substring ( end2 ) );
    end2 = sequence2.length ();

    // Pad the sequences to the same length.
    evenAlignments ( '.' );
  }  // method addTails


/******************************************************************************/
  // This method aligns the two sequences.
  public void align ()
  {
    // Align sequences using hash words.
    byte word_size = (byte) 3;
    AminoWords words1 = new AminoWords ( sequence1, word_size );
    AminoWords words2 = new AminoWords ( sequence2, word_size );

    // Match the common words between the two sequences.
    words1.matchCommonWords ( words2 );
    AminoWord [] sorted_words = words1.getSortedCommonWords ();

    // AminoWords.snap ( sorted_words );

    int index = 0;

    // Zip the alignment together using the common words between the alignments.
    while ( index < sorted_words.length )
    {
      // Check if zippered past this word already.
      if ( sorted_words [ index ].getPosition () >= end1 )

        // Check the following window for identities.
        if ( isGoodWord ( sorted_words [ index ] ) == true )

          zipper ( sorted_words [ index ] );

      index++;
    }  // while

    // Add remaining sequence tails to the alignment.
    addTails ();
  }  // method align


/******************************************************************************/
  private int countWindow ( int pos1, int pos2, int window )
  {
    int count = 0;

    for ( int i = 0; i < window; i++ )

      if ( ( pos1 + i < sequence1.length () ) &&
           ( pos2 + i < sequence2.length () ) )

        if ( Msa.equals ( sequence1.charAt ( pos1 + i )
                        , sequence2.charAt ( pos2 + i ) ) == true )

          count++;

    return count;
  }  // method countWindow


/******************************************************************************/
  private boolean isGoodWord ( AminoWord word )
  {
    int pos1 = word.getPosition () - 1;
    int pos2 = word.getCommon ().getPosition () - 1;

    // Check if near the end of either sequence.
    if ( ( pos1 + 10 >= sequence1.length () ) ||
         ( pos2 + 10 >= sequence2.length () ) )  return true;

    // Count the identities in the window starting at this common word.
    int count = countWindow ( pos1, pos2, 10 );

    if ( count > 5 )  return true;

    return false;
  }  // method isGoodWord


/******************************************************************************/
  private void startAlignment ( AminoWord word )
  {
    int pos1 = word.getPosition () - 1;
    int pos2 = word.getCommon ().getPosition () - 1;

    // Check if the start of sequence1 is longer than sequence2.
    int delta = pos1 - pos2;
    if ( delta > 0 )
      for ( int i = 0; i < delta; i++ )  alignment2.append ( '.' );

    // Check if the start of sequence2 is longer than sequence1.
    delta = pos2 - pos1;
    if ( delta > 0 )
      for ( int i = 0; i < delta; i++ )  alignment1.append ( '.' );

    // Append the leading sequence.
    if ( pos1 > 0 )  alignment1.append ( sequence1.substring ( 0, pos1 ) );
    if ( pos2 > 0 )  alignment2.append ( sequence2.substring ( 0, pos2 ) );

    end1 = pos1;
    end2 = pos2;
  }  // method startAlignment


/******************************************************************************/
  private void addMissing ( AminoWord word )
  {
    int pos1 = word.getPosition () - 1;
    int pos2 = word.getCommon ().getPosition () - 1;

    // Append the subsequence(s).
    // if ( pos1 > end1 )  alignment1.append ( sequence1.substring ( end1, pos1 ) );
    // if ( pos2 > end2 )  alignment2.append ( sequence2.substring ( end2, pos2 ) );
    String sub1 = "";		// subsequence 1
    String sub2 = "";		// subsequence 2
    if ( pos1 > end1 )  sub1 = sequence1.substring ( end1, pos1 );
    if ( pos2 > end2 )  sub2 = sequence2.substring ( end2, pos2 );

    if ( ( pos1 > end1 ) || ( pos2 > end2 ) )  slide ( sub1, sub2 );

    end1 = pos1;
    end2 = pos2;

    // Add a gap, if necessary.
    // evenAlignments ( '-' );
  }  // method addMissing


/******************************************************************************/
  private int countIdent ( String seq1, String seq2 )
  {
    int count = 0;

    for ( int i = 0; i < seq1.length (); i++ )

      if ( Msa.equals ( seq1.charAt ( i ), seq2.charAt ( i ) ) == true ) 

        count++;

    // System.out.println ( "Zipper: count = " + count + ", seq1 = " + seq1 + ", seq2 = " + seq2 );

    return count;
  }  // method countIdent


/******************************************************************************/
  private String gap ( String shorter, String longer )
  {
    int delta = longer.length () - shorter.length ();
    StringBuffer gaps = new StringBuffer ();
    while ( gaps.length () < delta )  gaps.append ( '-' );

    if ( countIdent ( shorter, longer.substring ( 0, shorter.length () ) ) >
         countIdent ( shorter, longer.substring ( delta ) ) )
    
      return shorter + gaps;

    return gaps + shorter;
  }  // method gap


/******************************************************************************/
  // This method slide aligns the two sequences.
  private void slide ( String sub1, String sub2 )
  {
    // Check if the subsequences are the same length.
    if ( sub1.length () == sub2.length () )  return;

    // Check if the first subsequence is shorter.
    if ( sub1.length () < sub2.length () )
    {
      alignment1.append ( gap ( sub1, sub2 ) );
      alignment2.append ( sub2 );
      return;
    }  // if
    else
    {
      alignment2.append ( gap ( sub2, sub1 ) );
      alignment1.append ( sub1 );
    }  // if
  }  // method slide


/******************************************************************************/
  private void zipper ( AminoWord word )
  {
    // Check for the start of the alignment.
    if ( end1 == 0 )  startAlignment ( word );

    // Add any skipped sequence.
    addMissing ( word );

    int count = 100;		// Copy the initial word (e.g., loop until mismatch)
    do
    {
      alignment1.append ( sequence1.charAt ( end1 ) );
      alignment2.append ( sequence2.charAt ( end2 ) );

      // Zipper while identical, then count following window.
      if ( Msa.equals ( sequence1.charAt ( end1 ), sequence2.charAt ( end2 ) ) == false )

        count = countWindow ( end1, end2, 10 );

      end1++;
      end2++;
    } 
    while ( ( end1 < sequence1.length () ) &&
            ( end2 < sequence2.length () ) &&
            ( count > 4 ) );
  }  // method zipper


/******************************************************************************/
  public void printAlignment ()
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
          if ( Msa.equals ( alignment1.charAt ( j ), alignment2.charAt ( j ) ) == true )
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
  }  // method printAlignment


/******************************************************************************/
  public static void main ( String [] args )
  {
    //                        "---AIGK-ML-TGVE-" 
    Zipper app = new Zipper ( "PLVAIGKRMLSTGVEQ", "AIGKMLTGVE" );
    app.printAlignment ();
  }  // main


/******************************************************************************/

}  // class Zipper
