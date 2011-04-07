
import java.io.*;
import java.sql.*;
import java.util.Vector;

// import SwissProtFeature;
// import SwissProtHeader;
// import SwissProtSlice;
// import InputTools;


/******************************************************************************/
/**
  This class parses the SwissProt database file.
  @author	    Darrell O. Ricke, Ph.D.  (mailto: d_ricke@yahoo.com)
  Copyright:	Copyright (c) 2000 Paragon Software
  License:	    GNU GPL license (http://www.gnu.org/licenses/gpl.html)  
  Contact:   	Paragon Software, 1314 Viking Blvd., Cedar, MN 55011
 
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

public class SwissProtParser extends Object
{

/******************************************************************************/

private InputTools swissprot_file = new InputTools ();	// SwissProt input file

private String swissprot_file_name;			// SwissProt input file name

private String line = null; 				// Current line of the file

// SwissProt file header object.
private SwissProtHeader swissprot_header = new SwissProtHeader ();

private StringBuffer aa_sequence = new StringBuffer ( 140000 );	// AA sequence

private Vector features = new Vector ();


/******************************************************************************/
public SwissProtParser ()
{
  initialize ();
}  // constructor SwissProtParser


/******************************************************************************/
private void initialize ()
{
  swissprot_file_name = null;
  line = null;
  aa_sequence.setLength ( 0 );

  features.removeAllElements ();
}  // method initialize


/******************************************************************************/
public void setFileName ( String name )
{
  swissprot_file_name = name;
}  // method setFileName


/******************************************************************************/
  private void parseHeader ()
  {
    StringBuffer header = new StringBuffer ( 1024 );	// Individual header section
    header.setLength ( 0 );
    swissprot_header.initialize ();

    String header_type = "";			// current line type
    String previous_type = "";			// previous line type

    while ( ( swissprot_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) &&
            ( line.startsWith ( "FT" ) != true ) &&
            ( line.startsWith ( "SQ" ) != true ) )
    {
      previous_type = header_type;
      header_type = line.substring ( 0, 2 );

      if ( line.length () > 6 )
      {
        // Check for a continuation of the previous line.
        if ( ( header_type.equals ( previous_type ) == true ) &&
             ( header_type.equals ( "DE" ) == false ) &&
             ( header_type.equals ( "GN" ) == false ) &&
             ( header_type.equals ( "OC" ) == false ) &&
             ( header_type.equals ( "OS" ) == false ) &&
             ( header_type.equals ( "KW" ) == false ) )

          header.append ( "\n" + line );

        else  // not continuation of previous header line
        {
          // Check for previous header.
          if ( previous_type.length () > 0 )
            swissprot_header.setSection ( previous_type, header.toString () );

          header.setLength ( 0 );
          header.append ( line );
        }
      }  // if

      line = swissprot_file.getLine ().toString ();
    }  // while

    // Check for previous header.
    if ( header.length () > 0 )
      swissprot_header.setSection ( header_type, header.toString () );
  }  // method parseHeader


/******************************************************************************/
  private void parseFeatures ()
  {
    String key_name = "";	// FT table key_name
    String from = "";		// FT table from
    String to = "";		// FT table to

    StringBuffer description = new StringBuffer ( 132 );	// FT table description
    description.setLength ( 0 );

    SwissProtFeature sp_feature = new SwissProtFeature ();
    features.removeAllElements ();

    StringBuffer feature = new StringBuffer ( 1024 );		// Individual feature
    feature.setLength ( 0 );

    // Process the Features table entries.
    while ( ( swissprot_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) &&
            ( line.startsWith ( "SQ" ) != true ) )
    {
      if ( line.length () > 6 )
      {
        int end = 27;
        if ( line.length () < 27 )  end = line.length ();

        // Check for a Description continuation line.
        if ( line.substring ( 5, end ).trim ().length () == 0 )
        { 
          if ( line.length () > 34 )
            description.append ( " " + line.substring ( 34 ) );
        }  // if 
        else
        {
          // Check for a previous FT line.
          if ( key_name.length () > 0 )
          {
            sp_feature.setFt ( key_name, from, to, description.toString () );
            features.addElement ( sp_feature );
            sp_feature = new SwissProtFeature ();
          }  // if

          if ( line.length () >= 14 )
            key_name = line.substring ( 5, 13 ).trim ();

          if ( line.length () >= 21 )
            from     = line.substring ( 14, 20 ).trim ();

          if ( line.length () >= 28 )
            to = line.substring ( 21, 27 ).trim ();
 
          description.setLength ( 0 );
          if ( line.length () >= 35 )
            description.append ( line.substring ( 34 ) );
        }  // else
      }  // if

      line = swissprot_file.getLine ().toString ();
    }  // while

    // Check for previous feature.
    if ( key_name.length () > 0 )
    {
      sp_feature.setFt ( key_name, from, to, description.toString () );
      features.addElement ( sp_feature );
      sp_feature = new SwissProtFeature ();
    }  // if
  }  // method parseFeatures


/******************************************************************************/
  private void parseSequence ()
  {
    // Reset the AA sequence for the next sequence.
    aa_sequence.setLength ( 0 );
    line = swissprot_file.getLine ().toString ();

    // Read in the AA sequence.
    while ( ( swissprot_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) )
    {
      // Add the current sequence line to the AA sequence.
      int start = 5;
      while ( start < line.length () )
      {
        if ( line.length () >= start + 10 )
          aa_sequence.append ( line.substring ( start, start + 10 ) );
        else
          aa_sequence.append ( line.substring ( start ).trim () );

        start += 11;
      }  // while 

      line = swissprot_file.getLine ().toString ();
    }  // while

    if ( aa_sequence.length () != swissprot_header.getLength () )

      System.out.println ( "*Warning* sequence length (" 
          + aa_sequence.length () 
          + ") is different from header length ("
          + swissprot_header.getLength () 
          + ")" );
  }  // method parseSequence


/******************************************************************************/
private void writeSequence ( String sequence )
{
  for( int index = 0; index < sequence.length(); index += 50 )
  {
    if( index + 50 >= sequence.length() )
      System.out.println( sequence.substring( index ) );
    else
      System.out.println( sequence.substring( index, index + 50 ) );
  } // end: for
}  // method writeSequence

 
/******************************************************************************/
  private void parseEntry ()
  {
    // Initialize.
    aa_sequence.setLength ( 0 );
    features.removeAllElements ();

    if ( swissprot_file == null )  return;

    line = swissprot_file.getLine ().toString ();

    // Process the SwissProt entry.
    while ( ( swissprot_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) )
    {
      if ( line.startsWith ( "ID" ) )
        parseHeader ();

      if ( line.startsWith ( "FT" ) == true )
        parseFeatures ();

      if ( line.startsWith ( "SQ" ) == true )
        parseSequence ();

      if ( line.startsWith ( "//" ) != true )
        line = swissprot_file.getLine ().toString ();
    }  // while
  }  // method parseEntry


/******************************************************************************/
  public void processFile ()
  {
    // Setup SwissProt entry processor.
    SwissProtSlice slice = new SwissProtSlice ();
    slice.setName ( swissprot_file_name );

    // Set the input file name.
    swissprot_file.initialize ();
    swissprot_file.setFileName ( swissprot_file_name );

    // Open the input file.
    swissprot_file.openFile ();

    // Process the input file.
    while ( swissprot_file.isEndOfFile () != true )
    {
      parseEntry ();

      // Extract domains from the entry.
      slice.processEntry ( swissprot_header, features, aa_sequence.toString () );
    }  // while

    // Close input file.
    swissprot_file.closeFile();
    features.removeAllElements ();

    // Close the output files.
    slice.close ();
    slice = null;
  }  // method processFile


/******************************************************************************/
  private void usage ()
  {
    System.out.println ( "The command line syntax for this program is:" );
    System.out.println ();
    System.out.println ( "java SwissProtParser <swissprot_file>" );
    System.out.println ();
    System.out.print   ( "where <swissprot_file> is the file name of a " );
    System.out.println ( "SwissProt protein sequence file." );
  }  // method usage


/******************************************************************************/
  public static void main ( String [] args )
  {
    SwissProtParser app = new SwissProtParser ();

    if ( args.length != 1 )
      app.usage ();
    else
    {
	 app.setFileName ( args[ 0 ] );
	 app.processFile ();
    }  // else
  }  // method main

}  // class SwissProtParser

