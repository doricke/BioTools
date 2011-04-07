
// import AminoWord;
// import AminoWords;
// import FastaSequence;
// import LineTools;


/******************************************************************************/
/**
  This class models the best pair match between two protein sequences.
  
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

public class Best extends Object
{


/******************************************************************************/

  private final static int WORD_SIZE = 3;	// Amino acid peptide word size


/******************************************************************************/

  private   int  best_match = 0;		// best protein match

  private   int  common_words = 0;		// common amino acid words

  private   boolean  duplicate = false;		// duplicate sequence flag

  private   int  family = 0;			// protein family

  private   int  group = 0;			// protein family group

  private   String  group_id = "";		// protein family group identifier

  private   FastaSequence  fasta_sequence = null;	// FASTA sequence

  private   byte  percent_first = 0;		// percent identity to first sequence

  private   byte  percent_words = 0;		// percent identity to best match

  private   int  super_family = 0;		// super-family

  private   AminoWords  words = new AminoWords ();	// hashed FASTA sequence

  private   int  word_size = WORD_SIZE;		// default word size


/******************************************************************************/
  // Constructor Best
  public Best ()
  {
    initialize ();
  }  // constructor Best


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    best_match = 0;
    common_words = 0;
    duplicate = false;
    family = 0;
    fasta_sequence = null;
    group = 0;
    group_id = "";
    percent_first = 0;
    percent_words = 0;
    super_family = 0;
    words.initialize ();
    word_size = WORD_SIZE;
  }  // method initialize 


/******************************************************************************/
  public int getBestMatch ()
  {
    return best_match;
  }  // method getBestMatch


/******************************************************************************/
  public int getCommonWords ()
  {
    return common_words;
  }  // method getCommonWords


/******************************************************************************/
  public boolean getDuplicate ()
  {
    return duplicate;
  }  // getDuplicate


/******************************************************************************/
  public int getFamily ()
  {
    return family;
  }  // method getFamily


/******************************************************************************/
  public FastaSequence getFastaSequence ()
  {
    return fasta_sequence;
  }  // method getFastaSequence


/******************************************************************************/
  public int getGroup ()
  {
    return group;
  }  // method getGroup


/******************************************************************************/
  public String getGroupId ()
  {
    return group_id;
  }  // method getGroupId


/******************************************************************************/
  public byte getPercentFirst ()
  {
    return percent_first;
  }  // method getPercentFirst


/******************************************************************************/
  public byte getPercentWords ()
  {
    percent_words = (byte) 0;

    if ( words.getTotal () > 0 )

      percent_words = (byte) ( ( common_words * 100 ) / words.getTotal () );

    return percent_words;
  }  // method getPercentWords


/******************************************************************************/
  public String getSequence ()
  {
    if ( fasta_sequence == null )  return "";

    return fasta_sequence.getSequence (); 
  }  // method getSequence


/******************************************************************************/
  public String getSequenceName ()
  {
    if ( fasta_sequence == null )  return "";

    return fasta_sequence.getShortName (); 
  }  // method getSequenceName


/******************************************************************************/
  public int getSuperFamily ()
  {
    return super_family;
  }  // method getSuperFamily


/******************************************************************************/
  public AminoWords getWords ()
  {
    return words;
  }  // method getWords


/******************************************************************************/
  public int getWordsTotal ()
  {
    return words.getTotal ();
  }  // method getWordsTotal


/******************************************************************************/
  public int getWordSize ()
  {
    return word_size;
  }  // method getWordSize


/******************************************************************************/
  public boolean isDuplicate ()
  {
    return duplicate;
  }  // isDuplicate


/******************************************************************************/
  public void setBestMatch ( int value )
  {
    best_match = value;
  }  // method setBestMatch


/******************************************************************************/
  public void setCommonWords ( int value )
  {
    common_words = value;
  }  // method setCommonWords


/******************************************************************************/
  public void setDuplicate ( boolean value )
  {
    duplicate = value;
  }  // method setDuplicate


/******************************************************************************/
  public void setFamily ( int value )
  {
    family = value;
  }  // method setFamily


/******************************************************************************/
  public void setFastaSequence ( FastaSequence value )
  {
    fasta_sequence = value;

    // Compute the amino acids hash words.
    computeWords ();
  }  // method setFastaSequence


/******************************************************************************/
  public void setGroup ( int value )
  {
    group = value;
  }  // method setGroup


/******************************************************************************/
  public void setGroupId ( String value )
  {
    group_id = value;
  }  // method setGroupId


/******************************************************************************/
  public void setPercentFirst ( byte value )
  {
    percent_first = value;
  }  // method setPercentFirst


/******************************************************************************/
  public void setSuperFamily ( int value )
  {
    super_family = value;
  }  // method setSuperFamily


/******************************************************************************/
  public void setWordSize ( int value )
  {
    // Assert: valid value.
    if ( value <= 0 )
      System.out.println ( "Best.setWordSize: *Warning* invalid word size: " + value );
    else
      word_size = value;
  }  // method setWordSize


/******************************************************************************/
  private void computeWords ()
  {
    // Validate the sequence length.
    int length = fasta_sequence.getLength ();
    if ( length <= 0 )  return;

    // number of triplet amino acids in sequence
    words.setSize ( length - word_size + 1 );

    // Amino acid triplet hash word.
    AminoWord amino_word = new AminoWord ();
    amino_word.setSize ( (byte) word_size );

    // Hash the sequence.
    String sequence = fasta_sequence.getSequence ();
    String word;
    for ( int i = 0; i < length - word_size; i++ )
    {
      if ( i + word_size < length )
        word = sequence.substring ( i, i + word_size );
      else
        word = sequence.substring ( length - word_size );

      // Check for a word with no duplicate amino acids.
      if ( amino_word.isGoodWord ( word ) == true )
      {
        amino_word.setAminos ( word );
        words.addWord ( amino_word.getWord (), word, i + 1 );
      }  // if
    }  // for
  }  // method computeWords


/******************************************************************************/
  public String toString ()
  {
    return fasta_sequence.getName () 
        + "\tg_" 
        + group
        + "\tf_" 
        + family 
        + "\ts_" 
        + super_family 
        + "\t" 
        + common_words 
        + "\t" 
        + getPercentWords ()
        + "%\t" 
        + best_match
        + "\t"
        + group_id
        + "\t"
        + percent_first
        + "%";
  }  // method toString


/******************************************************************************/
  public String titleLine ()
  {
    return "#    Seq. Name         Group  Family  Super  # common     %  Best   First%  Group name  Product";
  }  // method titleLine


/******************************************************************************/
  public void summarize ()
  {
    System.out.print ( fasta_sequence.getShortName ( 16 )  );
    System.out.print ( "  g_" + LineTools.pad ( "" + group, 3 ) );
    System.out.print ( "  f_" + LineTools.pad ( "" + family, 3 ) );
    System.out.print ( "   s_" + LineTools.pad ( "" + super_family, 3 ) );
    System.out.print ( "     " + LineTools.pad ( "" + common_words, 3 ) );
    System.out.print ( "    " + LineTools.leftPad ( "" + getPercentWords (), 3 ) + "%" );
    System.out.print ( " -" + LineTools.leftPad ( "" + best_match, 4 ) );
    System.out.print ( "    " + LineTools.leftPad ( "" + percent_first, 3 ) + "%" );
    System.out.print ( "   " + LineTools.pad ( group_id, 10 ) );
    System.out.println ( "  " + fasta_sequence.getDef () );
  }  // method summarize


/******************************************************************************/

}  // class Best
