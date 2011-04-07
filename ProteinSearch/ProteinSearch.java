
// import AminoWords;
// import FastaIterator;
// import FastaSequence;
// import InputFile;
// import LineTools;
// import OutputFile;


/******************************************************************************/
/**
  This program searches a list of FASTA databases with a query file of FASTA sequences.
  
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

public class ProteinSearch extends Object
{


/******************************************************************************/

// Maximum number of query proteins
final static private int MAX_PROTEINS = 200000;


/******************************************************************************/

private AminoWords [] best = new AminoWords [ MAX_PROTEINS ];

// Matched protein families file.
private  OutputFile []  family_files = new OutputFile [ MAX_PROTEINS ];

private int total = 0;			// Current number of query proteins


/******************************************************************************/
public ProteinSearch ()
{
  initialize ();
}  // constructor ProteinSearch


/******************************************************************************/
private void initialize ()
{
}  // method initialize


/******************************************************************************/
public void close ()
{
  if ( total <= 0 )  return;

  // Close the protein family files.
  for ( int i = 0; i < total; i++ )
  {
    family_files [ i ].closeFile ();

    best [ i ].initialize ();
  }  // for
}  // method close


/*******************************************************************************/
  public void processQueryFile ( String file_name )
  {
    System.out.println ( "Processing: " + file_name );

    // Set up the input file.
    FastaIterator fasta_file = new FastaIterator ( file_name );

    while ( fasta_file.isEndOfFile () == false )
    {
      // Get the next FASTA sequence.
      FastaSequence fasta_sequence = fasta_file.next ();

      if ( fasta_sequence.getLength () > 0 )
      {
        // Add the FASTA sequence to the protein families.
        best [ total ] = new AminoWords ();
        best [ total ].createWords ( fasta_sequence.getSequence () );

        family_files [ total ] = new OutputFile ( fasta_sequence.getName () + ".hits" );
        family_files [ total ].openFile ();

        // Write the query sequence as the fist sequence in the hits file.
        family_files [ total ].println ( fasta_sequence.toString () );

        if ( total < MAX_PROTEINS - 1 )  total++;
        else
          System.out.println ( "*Warning* too many query sequences." );
        // families.addFasta ( fasta_sequence );
      }  // if
    }  // while

    // Close file.
    fasta_file.closeFile ();

    System.out.println ( total + " query sequences read." );
  }  // method processQueryFile


/*******************************************************************************/
  public void processTargetFile ( String file_name, int min_identity )
  {
    System.out.println ( "Processing: " + file_name );

    AminoWords target = new AminoWords ();

    // Set up the input file.
    FastaIterator fasta_file = new FastaIterator ( file_name );

    int query_words = 0;
    int target_words = 0;
    int count = 0;
    int query_percent = 0;
    int target_percent = 0;

    while ( fasta_file.isEndOfFile () == false )
    {
      // Get the next FASTA sequence.
      FastaSequence fasta_sequence = fasta_file.next ();

      // Add the FASTA sequence to the protein families.
      if ( fasta_sequence.getLength () > 0 )
      {
        target.reset ();
        target.createWords ( fasta_sequence.getSequence () );

        // Compare to the query sequences.
        target_words = target.getTotal ();

        for ( int i = 0; i < total; i++ )
        {
          target.clearCommonWords ();
          best [ i ].clearCommonWords ();
          target.matchCommonWords ( best [ i ] );
/*
System.out.println ( "Query words:" );
best [ i ].snap ();
System.out.println ( "Target words:" );
target.snap ();
*/

          AminoWord [] sorted_words = target.getSortedCommonWords ();
          if ( sorted_words == null )  count = 0;
          else count = sorted_words.length;

          query_words = best [ i ].getTotal ();
          if ( target_words < query_words )  query_words = target_words;

          query_percent = 0;
          target_percent = 0;
          if ( query_words > 0 )  query_percent = ( count * 100 ) / query_words;
          if ( target_words > 0 )  target_percent = ( count * 100 ) / target_words;

          // Triplet words
          // if ( ( count >= 20 ) && ( query_percent >= min_identity ) && ( target_percent >= min_identity ) )  
          if ( ( count >= 30 ) && ( query_percent >= min_identity ) && ( target_percent >= min_identity ) )  
          {
            System.out.println ( fasta_sequence.getName () + " X [" + i + "] " 
                + count + " of " + query_words + " (" + query_percent + "%/" + target_percent + "%)" );

            family_files [ i ].println ( fasta_sequence.toString () );
          }  // if
        }  // for

        // families.addFasta ( fasta_sequence );
      }  // if
    }  // while

    // Close file.
    fasta_file.closeFile ();
    target.reset ();
  }  // method processTargetFile


/******************************************************************************/
  public void processTargetFiles ( String file_name, int minimum_identity )
  {
    StringBuffer name_line = new StringBuffer ( 80 );	// Current line of the file

    // Get the file name of the list of GeneMark.HMM output files.
    InputFile name_list = new InputFile ();
    name_list.setFileName ( file_name );
    name_list.openFile ();

    // Process the list of FASTA protein sequences.
    while ( name_list.isEndOfFile () == false )
    {
      // Read the next line from the list of names file.
      name_line = name_list.nextLine ();

      if ( name_list.isEndOfFile () == false )
      {
        String name = name_line.toString ().trim ();

        // Process this file
        processTargetFile ( name, minimum_identity );
      }  // if
    }  // while

    // Summarize the protein family information.
    // families.summarize ();

    // Close the files.
    name_list.closeFile ();
    close ();
  }  // method processTargetFiles


/******************************************************************************/
  private void usage ()
  {
    System.out.println ( "The command line syntax for this program is:" );
    System.out.println ();
    System.out.println ( "java -jar ProteinSearch.jar <query.fasta> <file.list> <minimum identity>" );
    System.out.println ();
    System.out.print   ( "where <query.fasta> is the FASTA file of " );
    System.out.println ( "query protein sequences." );
    System.out.print   ( "And, <file.list> is the file name of the " );
    System.out.println ( "list of target protein FASTA databases." );
    System.out.print   ( "And, <minimum identity> is the minimum" );
    System.out.println ( "percent identity for hits to keep." );
  }  // method usage


/******************************************************************************/
  public static void main ( String [] args )
  {
    ProteinSearch app = new ProteinSearch ();

    if ( args.length <= 1 )
      app.usage ();
    else
    {
      int min_identity = 60;		// minimum percent identity

      if ( args.length >= 3 )  min_identity = LineTools.getInteger ( args [ 2 ] );

      System.out.println ( "minimum identity = " + min_identity );

      app.processQueryFile ( args [ 0 ] );
      app.processTargetFiles ( args [ 1 ], min_identity );
    }  // else
  }  // method main

}  // class ProteinSearch

