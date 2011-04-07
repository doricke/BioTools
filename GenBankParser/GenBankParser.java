
import java.io.*;
import java.sql.*;
import java.util.Vector;

// import GenBankFeature;
// import GenBankHeader;
// import GenBankLoader;
// import GenBankReference;
// import GenBankSlice;
// import InputTools;


/******************************************************************************/
/**
  This class parses a GenBank sequence entry.
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

public class GenBankParser extends Object
{

/******************************************************************************/

private InputTools genbank_file = new InputTools ();	// GenBank input file

private String genbank_file_name;			// GenBank input file name

private String line = null; 				// Current line of the file


// GenBank file objects.
private GenBankHeader genbank_header = new GenBankHeader ();

private Vector features = new Vector ();

// private Vector references = new Vector ();

private StringBuffer sequence = new StringBuffer ( 140000 );	// sequence data


/******************************************************************************/
public GenBankParser ()
{
  initialize ();
}  // constructor GenBankParser


/******************************************************************************/
public void initialize ()
{
  genbank_file_name = null;
  line = null;

  features.removeAllElements ();
  // references.removeAllElements ();
  sequence.setLength ( 0 );
}  // method initialize


/******************************************************************************/
public void setFileName ( String name )
{
  genbank_file_name = name;
}  // method setFileName


/******************************************************************************/
  private void parseHeader ()
  {
    StringBuffer header = new StringBuffer ( 1024 );	// Individual header section
    header.setLength ( 0 );
    genbank_header.initialize ();

    String header_type = "";			// current line type
    String previous_type = "";			// previous line type

    while ( ( genbank_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) &&
            ( line.startsWith ( "FEATURES" ) != true ) &&
            ( line.startsWith ( "BASE COUNT" ) != true ) &&
            ( line.startsWith ( "ORIGIN" ) != true ) &&
            ( line.startsWith ( "        1" ) != true ) )
    {
      if ( line.length () > 12 )
      {
        header_type = line.substring ( 0, 12 ).trim ();

        // Check for a continuation of the previous line.
        if ( header_type.equals ( "" ) == true )
        {
          if ( header.length () > 0 )
            header.append ( " " );
          header.append ( line.substring ( 12 ).trim () );
        }  // if
        else  // not continuation of previous header line
        {
          // Check for previous header.
          if ( previous_type.length () > 0 )
            genbank_header.setSection ( previous_type, header.toString () );

          previous_type = header_type;
          header.setLength ( 0 );
          header.append ( line.substring ( 12 ).trim () );

          // Check for the ORGANISM header line.
          if ( header_type.equals ( "ORGANISM" ) == true )
          {
            genbank_header.setSection ( header_type, header.toString () );
            previous_type = "Taxonomy";
            header.setLength ( 0 );
          }  // if
        }  // else

      }  // if

      line = genbank_file.getLine ().toString ();
    }  // while

    // Check for previous header.
    if ( header.length () > 0 )
      genbank_header.setSection ( previous_type, header.toString () );
  }  // method parseHeader


/******************************************************************************/
  private int findQualifierEnd ( String feature_doc, int start )
  {
    int end = start + 1;
    boolean in_quote = false;

    while ( end < feature_doc.length () ) 
    {
      if ( feature_doc.charAt ( end ) == '"' )
        in_quote = ! in_quote;

      // Ignore / within a text string.
      if ( ( in_quote == false ) && 
           ( feature_doc.charAt ( end ) == '/' ) )
        return end;

      end++;
    }  // while

    return end;
  }  // method findQualifierEnd


/******************************************************************************/
  private void parseQualifier 
      ( GenBankFeature feature
      , String feature_type
      , String feature_doc
      )
  {
    int start = 0;					// feature_doc index
    int end = feature_doc.indexOf ( "/" );		// feature_doc index

    // Extract the qualifier location.
    if ( end < 0 )
    {
      feature.setLocation ( feature_type, feature_doc );

      // No additional qualifiers.
      return;
    }
    else
      feature.setLocation ( feature_type, feature_doc.substring ( start, end ) );

    // Parse all of the qualifiers that follow location.
    do
    {
      start = end + 1;

      // Find the end of this qualifier.
      end = findQualifierEnd ( feature_doc, start );

      if ( start < feature_doc.length () )
      {
        if ( end >= feature_doc.length () )
          feature.setQualifier ( feature_type, feature_doc.substring ( start ).trim () );
        else
          feature.setQualifier ( feature_type, feature_doc.substring ( start, end ).trim () );
      }  // if
    }  // do
    while ( end < feature_doc.length () );
  }  // method parseQualifier


/******************************************************************************/
  private void parseFeatures ()
  {
    String feature_name = "";		// FEATURES table feature_name
    boolean in_note = false;		// in /note flag

    StringBuffer documentation = new StringBuffer ( 132 );	// FT table description
    documentation.setLength ( 0 );

    GenBankFeature gb_feature = new GenBankFeature ();
    features.removeAllElements ();

    StringBuffer feature = new StringBuffer ( 1024 );		// Individual feature
    feature.setLength ( 0 );

    // Skip the FEATURES line.
    line = genbank_file.getLine ().toString ();

    // Process the Features table entries.
    while ( ( genbank_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) &&
            ( line.startsWith ( "BASE COUNT" ) != true ) &&
            ( line.startsWith ( "ORIGIN" ) != true ) &&
            ( line.startsWith ( "        1" ) != true ) )
    {
      if ( line.length () > 13 )
      {
        int end = 20;
        if ( line.length () < 20 )  end = line.length ();

        // Check for a continuation line.
        if ( line.substring ( 5, end ).trim ().length () == 0 )
        { 
          if ( line.length () > 21 )
          {
            if ( ( documentation.length () > 0 ) && ( in_note == true ) )
              documentation.append ( " " );
            documentation.append ( line.substring ( 21 ).trim () );
          }  // if
        }  // if 
        else
        {
          // Check for a previous Feature line.
          if ( feature_name.length () > 0 )
          {
            parseQualifier ( gb_feature, feature_name, documentation.toString () );
            features.addElement ( gb_feature );
            if ( gb_feature.getHost ().length () > 0 )
              genbank_header.setHost ( gb_feature.getHost () );
            if ( gb_feature.getStrain ().length () > 0 )
              genbank_header.setStrain ( gb_feature.getStrain () );
            gb_feature = new GenBankFeature ();
          }  // if

          if ( line.substring ( 5, end ).trim ().length () != 0 )
            feature_name = line.substring ( 5, end ).trim ();

          in_note = false; 
          documentation.setLength ( 0 );
          if ( line.length () > 21 )
            documentation.append ( line.substring ( 21 ).trim () );
        }  // else

        // Check if within a /note="text qualifier.
        if ( line.length () > 30 )
          if ( line.charAt ( 21 ) == '/' )
          {
            if ( line.substring ( 21, 27 ).equals ( "/note=" ) == true )
              in_note = true;
            else
              in_note = false;
          }  // if
      }  // if

      line = genbank_file.getLine ().toString ();
    }  // while

    // Check for previous feature.
    if ( feature_name.length () > 0 )
    {
      parseQualifier ( gb_feature, feature_name, documentation.toString () );
      features.addElement ( gb_feature );
      gb_feature = new GenBankFeature ();
    }  // if
  }  // method parseFeatures


/******************************************************************************/
  private void parseSequence ()
  {
    // Reset the DNA sequence for the next sequence.
    sequence.setLength ( 0 );

    // Read in the DNA sequence.
    while ( ( genbank_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) )
    {
      // Add the current sequence line to the DNA sequence.
      int start = 10;
      while ( start < line.length () )
      {
        if ( line.length () >= start + 10 )
          sequence.append ( line.substring ( start, start + 10 ) );
        else
          sequence.append ( line.substring ( start ).trim () );

        start += 11;
      }  // while 

      line = genbank_file.getLine ().toString ();
    }  // while

    if ( sequence.length () != genbank_header.getSequenceLength () )
    {
      System.out.println ();
      System.out.println ( "*Warning* sequence length (" 
          + sequence.length () 
          + ") is different from header length ("
          + genbank_header.getSequenceLength () 
          + ")" );
      System.out.println ();
    }  // if

    // writeSequence ( sequence.toString () );
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
  private void mergeFeatures ()
  {
    // Check for one or less features.
    if ( features.size () <= 1 )  return;

    // Merge CDS with mRNA features.
    for ( int index = features.size () - 2; index >= 0; index-- )
    {
      GenBankFeature feature1 = (GenBankFeature) features.elementAt ( index );

      // Check for a mRNA feature.
      if ( feature1.getFeatureType ().equals ( "mRNA" ) == true ) 
      {
        // Check following features for a CDS feature.
        int index2 = index + 1;
        boolean done = false;
        while ( ( index2 < features.size () ) && ( done == false ) )
        {
          GenBankFeature feature2 = (GenBankFeature) features.elementAt ( index2 );

          // Check for a CDS feature.
          if ( feature2.getFeatureType ().equals ( "CDS" ) == true )
          {
            // Check that the coordinates overlap.
            if ( ( feature1.getStart () <= feature2.getStart () ) &&
                 ( feature1.getEnd () >= feature2.getEnd () ) )
            {
              // Merge the CDS feature into the mRNA feature.
              feature1.merge ( feature2 );
    
              // Delete the CDS feature.
              features.removeElementAt ( index2 );
              features.setElementAt ( feature1, index );
              done = true;
            }  // if
          }  // if

          // Check for the next feature being past the end of the mRNA.
          if ( feature2.getStart () > feature1.getEnd () )  done = true;
          index2++;
        }  // while
      }  // if
    }  // while
  }  // method mergeFeatures

 
/******************************************************************************/
  private void parseEntry ()
  {
    if ( genbank_file == null )  return;

    line = genbank_file.getLine ().toString ();

    // Process the GenBank entry.
    while ( ( genbank_file.isEndOfFile () != true ) &&
            ( line.startsWith ( "//" ) != true ) )
    {
      if ( line.startsWith ( "LOCUS" ) )
      {
        // System.out.println ( "parseEntry: " + line.toString () );
        parseHeader ();
      }  // if

      if ( line.startsWith ( "FEATURES" ) == true )
      {
        parseFeatures ();
      }  // if

      if ( line.startsWith ( "        1" ) == true )
      {
        parseSequence ();
      }  // if

      if ( line.startsWith ( "//" ) != true )
        line = genbank_file.getLine ().toString ();
    }  // while

    // Merge mRNA and CDS features.
    mergeFeatures ();
  }  // method parseEntry


/******************************************************************************/
  public void processFile ()
  {
    // Setup GenBank entry processor.
    // GenBankLoader gb_loader = new GenBankLoader ();
    GenBankSlice gb_slice = new GenBankSlice ();
    gb_slice.setName ( genbank_file_name );

    // Set the input file name.
    genbank_file.initialize ();
    genbank_file.setFileName ( genbank_file_name );

    // Open the input file.
    genbank_file.openFile ();

    // Process the input file.
    while ( genbank_file.isEndOfFile () != true )
    {
      parseEntry ();

      gb_slice.processEntry ( genbank_header, features, sequence.toString () );

      // Extract domains from the entry.
/*
      gb_loader.processEntry 
          ( "GenBank"
          , genbank_file_name
          , genbank_header
          , features
          , sequence.toString () 
          );
*/
      genbank_header.initialize ();
      features.removeAllElements ();
      sequence.setLength ( 0 );
    }  // while

    // Close input file.
    genbank_file.closeFile();
    features.removeAllElements ();

    // Close the output files.
    // gb_loader.initialize ();
    // gb_loader = null;

    gb_slice.close ();
    gb_slice = null;
  }  // method processFile


/******************************************************************************/
  private void usage ()
  {
    System.out.println ( "The command line syntax for this program is:" );
    System.out.println ();
    System.out.println ( "java GenBankParser <GenBank_file>" );
    System.out.println ();
    System.out.print   ( "where <GenBank_file> is the file name of a " );
    System.out.println ( "GenBank DNA sequence file." );
  }  // method usage


/******************************************************************************/
  public static void main ( String [] args )
  {
    GenBankParser app = new GenBankParser ();

    if ( args.length != 1 )
      app.usage ();
    else
    {
	 app.setFileName ( args[ 0 ] );
	 app.processFile ();
    }  // else
  }  // method main
}  // class GenBankParser

