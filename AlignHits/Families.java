
// import AminoWords;
// import Best;
// import FastaSequence;
// import Format;
// import LineTools;
// import Msa;
// import Zipper;


/******************************************************************************/
/**
  This class models gene families. 
  
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

public class Families extends Object
{


/******************************************************************************/

  // Maximum number of protein sequences. 
  private final static int MAX_PROTEINS = 60000;


/******************************************************************************/

  // Summary of best matches between proteins
  private   Best best [] = new Best [ MAX_PROTEINS ];

  private   int  families = 0;				// number of familes

  private   int  groups = 0;				// number of family groups

  private   boolean identity = false;			// identity alignment flag

  private   Msa msa = new Msa ();			// Multiple sequence alignment

  private   int  super_families = 0;			// number of super protein familes

  private   int  total = 0;				// number of protein families


/******************************************************************************/
  // Constructor Families
  public Families ()
  {
    initialize ();
  }  // constructor Families


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    families = 0;
    groups = 0;
    msa.initialize ();
    super_families = 0;
    total = 0;
  }  // method initialize 


/******************************************************************************/
  public void addNext ( Best next )
  {
    if ( total >= MAX_PROTEINS )
    {
      System.out.println ( ".addBest: *Warning* too many proteins." );
      return;
    }  // if

    best [ total ] = next;
    total++;
  }  // method addNext


/******************************************************************************/
  public int getTotal ()
  {
    return total;
  }  // method getTotal


/******************************************************************************/
  public String getConsensus ()
  {
    return msa.getConsensus ().toString ();
  }  // method getConsensus


/******************************************************************************/
  public int getFamilies ()
  {
    return families;
  }  // method getFamilies


/******************************************************************************/
  public int getGroups ()
  {
    return groups;
  }  // method getGroups


/******************************************************************************/
  public FastaSequence getFastaSequence ( int index )
  {
    if ( best == null )  return null;
    if ( ( index < 0 ) || ( index >= total ) )
    {
      System.out.println ( ".getFastaSequence: index out of range: " + index );
      return null;
    }  // if

    return best [ index ].getFastaSequence ();
  }  // method getFastaSequence


/******************************************************************************/
  public String getMsa ( String group_name )
  {
    return msa.toString ( group_name );
  }  // method getMsa


/******************************************************************************/
  public int getNewFamily ()
  {
    families++;
    return families;
  }  // method getNewFamily


/******************************************************************************/
  public int getNewSuperFamily ()
  {
    super_families++;
    return super_families;
  }  // method getNewSuperFamily


/******************************************************************************/
  public int getSuperFamilies ()
  {
    return super_families;
  }  // method getSuperFamilies


/******************************************************************************/
  public boolean isDuplicate ( FastaSequence fasta1 )
  {
    if ( total <= 0 )  return false;

    FastaSequence fasta2 = null;
    for ( int i = 0; i < total; i++ )
    {
      fasta2 = best [ i ].getFastaSequence ();

      // Check for duplicate sequence names.
      if ( fasta2.getShortName ().equals ( fasta1.getShortName () ) == true )

        return true;

      // Check for duplicate sequences for the same species.
/*
      if ( fasta2.getSpecies ().equals ( fasta1.getSpecies () ) == true )

        if ( fasta2.getSequence ().toLowerCase ().equals 
           ( fasta1.getSequence ().toLowerCase () ) == true )

          return true;
*/
    }  // for

    return false;
  }  // method isDuplicate


/******************************************************************************/
  public void setIdentity ( boolean value )
  {
    identity = value;
  }  // method setIdentity


/******************************************************************************/
  public void setTotal ( int value )
  {
    total = value;
  }  // method setTotal


/******************************************************************************/
  public void addFastaSequence ( FastaSequence fasta_sequence )
  {
    Best best = new Best ();
    best.setFastaSequence ( fasta_sequence );

    // Add this protein to the list.
    addNext ( best );

    // Find the best match.
    findBest ( total-1 );
  }  // method addFastaSequence


/******************************************************************************/
  public void sortHits ()
  {
    // Check for no proteins.
    if ( total <= 1 )  return;

    // Compare this protein to all other proteins.
    AminoWords words = best [ 0 ].getWords ();
    int matches = 0;
    int words_total = 0;
    for ( int i = 1; i < total; i++ )
    {
      // Count the number of words common to both sequences.
      matches = words.countCommonWords ( best [ i ].getWords () );
      if ( words.getTotal () > 0 )
        best [ i ].setPercentFirst ( (byte) ( (matches * 100) / words.getTotal () ) ); 

      words_total = best [ i ].getWords ().getTotal ();
      if ( words_total < words.getTotal () ) 
        best [ i ].setPercentFirst ( (byte) ( (matches * 100) / words_total ) ); 

    }  // for

    // Sort the best hits.
    if ( total == 2 )
    {
      // Find the best matches.
      for ( int i = 0; i < total; i++ )

        findBest ( i );

      return;
    }  // if

    Best temp = null;
    for ( int i = 1; i < total - 1; i++ )
    {
      for ( int j = i + 1; j < total; j++ )
      {
        // Compare by percent words.
        if ( best [ j ].getPercentFirst () > best [ i ].getPercentFirst () )
        {
          temp = best [ i ];
          best [ i ] = best [ j ];
          best [ j ] = temp;
        }  // if
        else
          // Check for the same percent identity.
          if ( best [ j ].getPercentFirst () == best [ i ].getPercentFirst () )
          {
            if ( best [ j ].getFastaSequence ().getLength () >
                 best [ i ].getFastaSequence ().getLength () )
            {
              temp = best [ i ];
              best [ i ] = best [ j ];
              best [ j ] = temp;
            }  // if
          }  // if
      }  // for
    }  // for

    // Find the best matches.
    for ( int i = 0; i < total; i++ )

      findBest ( i );
  }  // method sortHits


/******************************************************************************/
  private void findBest ( int index )
  {
    // Check for the first protein.
    if ( index <= 0 )
    {
      families++;				// New protein family
      best [ index ].setFamily ( families );	// Set the protein family number
      super_families++;				// New protein super family
      best [ index ].setSuperFamily ( super_families );	// Set the protein super family number
      return;
    }  // if

    // Compare this protein to all other proteins.
    int best_count = 0;
    int best_match = 0;
    int best_percent = 0;
    AminoWords words = best [ index ].getWords ();
    int length = best [ index ].getFastaSequence ().getLength ();
    int length2 = length;
    for ( int i = 0; i < index; i++ )
    {
      // Count the number of words common to both sequences.
      int count = words.countCommonWords ( best [ i ].getWords () );

      // Check for a better match.
      if ( count > best_count )
      {
        best_count = count;
        best_match = i;
        // best_percent = (count * 100) / best [ i ].getWordsTotal ();
        length2 = best [ i ].getFastaSequence ().getLength ();
        if ( length < length2 )
          best_percent = (count * 100) / length;
        else
          best_percent = (count * 100) / length2;
      }  // if
    }  // for

    // Record the number of common words with the best match. 
    best [ index ].setCommonWords ( best_count ); 
    best [ index ].setBestMatch ( best_match );

    // Check for a family or super-family match.
    if ( best_count > 9 )
    {
      // Check for a family match.
      if ( ( best_count > 49 ) && ( best_percent >= 26 ) )
      {
        best [ index ].setFamily ( best [ best_match ].getFamily () );
        best [ index ].setSuperFamily ( best [ best_match ].getSuperFamily () );

        // Check for a better match for the previous sequence.
        if ( best_count > best [ best_match ].getCommonWords () )
        {
          best [ best_match ].setCommonWords ( best_count );
          best [ best_match ].setBestMatch ( index );
        }  // if
      }  // if
      else  // super family member
      {
        families++;				// New protein family
        best [ index ].setFamily ( families );		// Set the protein family number

        // Set the protein super family number
        if ( best_count > 19 )
        {
          best [ index ].setSuperFamily ( best [ best_match ].getSuperFamily () );
        }  // if
        else
        {
          super_families++;				// New protein super family
          best [ index ].setSuperFamily ( super_families );	// Set the protein super family number
        }  // else
      }  // else
    }  // if
    else
    {
      families++;				// New protein family
      best [ index ].setFamily ( families );		// Set the protein family number

      super_families++;				// New protein super family
      best [ index ].setSuperFamily ( super_families );	// Set the protein super family number
    }  // else
  }  // method findBest


/******************************************************************************/
  public int checkGene ( String gene )
  {
    FastaSequence sequence = null;

    // Check for a protein for this gene with group_id set.
    for ( int i = 0; i < total; i++ )

      // Check for a known group id.
      if ( best [ i ].getGroup () > 0 )
      {
        sequence = best [ i ].getFastaSequence ();

        // Check for matching gene names.
        if ( gene.toLowerCase ().equals ( sequence.getGene ().toLowerCase () ) == true )

          return best [ i ].getGroup ();
      }  // if 

    return 0;
  }  // method checkGene


/******************************************************************************/
  public boolean isGeneMatches ( String gene, int index )
  {
    FastaSequence sequence = null;

    // Check for a protein for this gene with group_id set.
    for ( int i = index + 1; i < total; i++ )
    {
      sequence = best [ i ].getFastaSequence ();

      // Check for matching gene names.
      if ( gene.toLowerCase ().equals ( sequence.getGene ().toLowerCase () ) == true )

        return true;
    }  // if 

    return false;
  }  // method isGeneMatches


/******************************************************************************/
  public int checkDef ( String def )
  {
    FastaSequence sequence = null;
    String def_lower = def.toLowerCase ();

    // Check for a protein for this def with group_id set.
    for ( int i = 0; i < total; i++ )

      // Check for a known group id.
      if ( best [ i ].getGroup () > 0 )
      {
        sequence = best [ i ].getFastaSequence ();

        // Check for matching def strings.
        if ( def_lower.equals ( sequence.getDef ().toLowerCase () ) == true )

          return best [ i ].getGroup ();
      }  // if 

    return 0;
  }  // method checkDef


/******************************************************************************/
  public boolean isDefMatches ( String def, int index )
  {
    // Check for the end of the list.
    if ( index + 1 >= total )  return false;

    FastaSequence sequence = null;
    String def_lower = def.toLowerCase ();

    // Check for a protein for this def with group_id set.
    for ( int i = index + 1; i < total; i++ )
    {
      sequence = best [ i ].getFastaSequence ();

      // Check for matching def strings.
      if ( def_lower.equals ( sequence.getDef ().toLowerCase () ) == true )

        return true;
    }  // for

    return false;
  }  // method isDefMatches


/******************************************************************************/
  public void groupGenes ()
  {
    FastaSequence sequence = null;

    // Group the sequences by SwissProt IDs.
    String groups_id = "";
    String seq_id = "";
    for ( int i = 0; i < total; i ++ )

      if ( best [ i ].getGroup () == 0 )
      {
        sequence = best [ i ].getFastaSequence ();
        groups_id = sequence.getId ();

        if ( groups_id.length () > 0 )
        {
          groups++;
          best [ i ].setGroup ( groups );
          best [ i ].setGroupId ( groups_id );

          // Assign matching IDs to this group.
          if ( i + 1 < total )
            for ( int j = i + 1; j < total; j++ )
            {
              sequence = best [ j ].getFastaSequence ();
              seq_id = sequence.getId ();

              // Check that the family id matches.         
              // if ( best [ i ].getFamily () == best [ j ].getFamily () )
              { 
                if ( seq_id.equals ( groups_id ) == true )
                {
                  best [ j ].setGroup ( groups );
                  best [ j ].setGroupId ( seq_id );
                }  // if
              }  // if
            }  // for
        }  // if
      }  // if

    // System.out.println ( "By Swiss ID: --------------------------------------------------" );
    // summarize ();

    // Group the sequences by gene names.
    int group_id = 0;
    String group_gene = "";
    String seq_gene = "";
    for ( int i = 0; i < total; i ++ )

      if ( best [ i ].getGroup () == 0 )
      {
        sequence = best [ i ].getFastaSequence ();
        group_gene = sequence.getGene ().toLowerCase ();

        if ( group_gene.length () > 0 )
        {
          group_id = checkGene ( group_gene );

          if ( group_id == 0 )
            if ( isGeneMatches ( group_gene, i ) == true )  
              group_id = ++groups;

          if ( group_id > 0 )
          {
            best [ i ].setGroup ( group_id );
            best [ i ].setGroupId ( group_gene );

            // Assign matching genes to this group.
            if ( i + 1 < total )
              for ( int j = i + 1; j < total; j++ )
                if ( best [ j ].getGroup () == 0 )
                {
                  sequence = best [ j ].getFastaSequence ();
                  seq_gene = sequence.getGene ();
              
                  if ( seq_gene.toLowerCase ().equals ( group_gene ) == true )
                  {
                    best [ j ].setGroup ( group_id );
                    best [ j ].setGroupId ( seq_gene );
                  }  // if
                }  // if
          }  // if
        }  // if
      }  // if

    // System.out.println ( "By Gene name : --------------------------------------------------" );
    // summarize ();

    // Group the sequences by product/gene definition.
    group_id = 0;
    String group_def = "";
    String seq_def = "";
    for ( int i = 0; i < total; i ++ )

      if ( best [ i ].getGroup () == 0 )
      {
        sequence = best [ i ].getFastaSequence ();
        group_def = sequence.getDef ();

        if ( group_def.length () > 0 )
        {
          group_id = checkDef ( group_def );

          if ( group_id == 0 )
            if ( isDefMatches ( group_def, i ) == true )  
              group_id = ++groups;

          if ( group_id > 0 )
          {
            best [ i ].setGroup ( group_id );
            best [ i ].setGroupId ( group_def );
  
            // Assign matching genes to this group.
            if ( i + 1 < total )
              for ( int j = i + 1; j < total; j++ )
                if ( best [ j ].getGroup () == 0 )
                {
                  sequence = best [ j ].getFastaSequence ();
                  seq_def = sequence.getDef ().toLowerCase ();
                
                  if ( seq_def.equals ( group_def.toLowerCase () ) == true )
                  {
                    best [ j ].setGroup ( group_id );
                    best [ j ].setGroupId ( group_def );
                  }  // if
                }  // if
          }  // if
        }  // if
      }  // if

    // System.out.println ( "By Def: --------------------------------------------------" );
    // summarize ();

    // Add orphans to best match groups.
    int best_match = 0;
    for ( int i = 0; i < total; i++ )

      if ( best [ i ].getGroup () == 0 )
      {
        best_match = best [ i ].getBestMatch ();
        if ( best [ best_match ].getGroup () > 0 )
    
          if ( best [ i ].getPercentWords () >= 50 )
            best [ i ].setGroup ( best [ best_match ].getGroup () );
      }  // if

    // System.out.println ( "By Best match: --------------------------------------------------" );
    // summarize ();
  }  // method groupGenes


/******************************************************************************/
  public void alignFamily ( int family )
  {
    msa.initialize ();

    System.out.print ( "---------------------------------------");
    System.out.println ( "---------------------------------------");
    System.out.println ( "Family = " + family );
    System.out.println ();

    // Count the family members.
    int count = 0;
    for ( int i = 0; i < total; i++ )
      if ( best [ i ].getFamily () == family )  count++;

    // Check if no family members.
    if ( count <= 0 )  return;

    // Set up for the size of this family.
    msa.setSize ( count );

    // Add the sequences to the alignment.
    for ( int i = 0; i < total; i++ )
      if ( best [ i ].getFamily () == family )
        msa.addBest ( best [ i ], identity );

    // Replace the gap characters at the ends of the sequence.
    msa.deGapEnds ();

    // Trim the ends of the MSA.
    // msa.trimMsaEnds ();

    msa.printMsa ();
  }  // method alignFamily


/******************************************************************************/
  public void alignGroup ( int group, String group_id )
  {
    msa.initialize ();

    System.out.print ( "---------------------------------------");
    System.out.println ( "---------------------------------------");
    if ( group > 0 )
      System.out.println ( "Group = " + group + " " + group_id );
    else
      System.out.println ( "Group name: " + group_id );
    System.out.println ();

    // Count the group members.
    int count = 0;
    for ( int i = 0; i < total; i++ )
      if ( best [ i ].getGroup () == group )  count++;

    // Check if no group members.
    if ( count <= 0 )  return;

    // Set up for the size of this group.
    msa.setSize ( count );

    // Add the sequences to the alignment.
    for ( int i = 0; i < total; i++ )
      if ( ( best [ i ].getGroup () == group ) || ( group == 0 ) )
        msa.addBest ( best [ i ], identity );

    // Replace the gap characters at the ends of the sequence.
    msa.deGapEnds ();

    // Trim the ends of the MSA.
    // msa.trimMsaEnds ();

    // Print out the multiple sequence group alignment.
    msa.printMsa ();

    // Write the group multiple sequence alignment to a file.
    if ( group == 0 )  msa.writeMsa ( group_id );
  }  // method alignGroup


/*******************************************************************************/
  public void zipperGroup ( int group, String group_id )
  {
    System.out.print ( "Zipper---------------------------------");
    System.out.println ( "---------------------------------------");
    System.out.println ( "Group = " + group + " " + group_id );
    System.out.println ();

    if ( total <= 1 )  return;

    String seq1 = best [ 0 ].getFastaSequence ().getSequence ();
    Zipper zipper = new Zipper ();
    for ( int i = 1; i < total; i++ )
      if ( best [ i ].getGroup () == group )
      {
        System.out.println ( best [ 0 ].getFastaSequence ().getName () + " X " +
                             best [ i ].getFastaSequence ().getName () );
        zipper.setSequence1 ( seq1 );
        zipper.setSequence2 ( best [ i ].getFastaSequence ().getSequence () );
        zipper.align ();
        zipper.printAlignment ();
        zipper.initialize ();
      }  // if

  }  // method zipperGroup


/*******************************************************************************/
  public String getGroupName ( int group )
  {
    String name = "";

    // Scan the group members.
    for ( int i = 0; i < total; i++ )
      if ( best [ i ].getGroup () == group )
      {
        name = best [ i ].getGroupId ();

        if ( name.length () > 0 )  return name + "_" + group;
      }  // if

    return "Group_" + group;
  }  // method getGroupName


/******************************************************************************/
  public void printInfo ()
  {
    FastaSequence sequence = null;

    System.out.print ( " #  Seq. Name  Gene     ID   Taxon.     Species   " );
    System.out.println ( "           group %ident Product" );
    for ( int i = 0; i < total; i++ )
    {
      sequence = best [ i ].getFastaSequence ();

      Format.intWidth ( i, 3 );
      System.out.print ( " " + sequence.getShortName ( 10 ) );
      System.out.print ( " " + LineTools.pad ( sequence.getGene (), 8 ) );
      System.out.print ( " " + LineTools.pad ( sequence.getId (), 4 ) );
      System.out.print ( " " + LineTools.pad ( sequence.getTaxonKey (), 10 ) );
      System.out.print ( " " + LineTools.pad ( sequence.getSpecies (), 20 ) );
      System.out.print ( "  g_" );
      Format.intWidthPost ( best [ i ].getGroup (), 3 );
      System.out.print ( "  " );
      Format.intWidth ( best [ i ].getPercentFirst (), 3 );
      System.out.print ( "%  " + sequence.getDef () );
      System.out.println ();
    }  // for
    System.out.println ();
  }  // method printInfo


/******************************************************************************/
  public void summarize ()
  {
    System.out.println ();
    System.out.println ( total + " protein sequences" );
    System.out.println ( families + " protein extended families" );
    System.out.println ( groups + " protein family groups" );
    System.out.println ( super_families + " protein super-families" );
    System.out.println ();
    System.out.println ( best [ 0 ].titleLine () );
    for ( int i = 0; i < total; i++ )
    {
      System.out.print ( LineTools.pad ( "" + i, 3 ) + "  " );
      best [ i ].summarize ();
    }  // for
    System.out.println ();

    printInfo ();
  }  // method summarize


/******************************************************************************/

}  // class Families
