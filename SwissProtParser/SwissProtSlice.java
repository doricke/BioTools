
import java.util.Vector;

// import SwissProtFeature;
// import SwissProtHeader;
// import InputTools;
// import OutputTools;
// import SeqTools;


/******************************************************************************/
/**
  This class slices the SwissProt database entries.
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

public class SwissProtSlice extends Object
{

/******************************************************************************/

  private OutputTools fasta_file = new OutputTools ();	// All FT domains


/******************************************************************************/
  public SwissProtSlice ()
  {
    initialize ();
  }  // constructor SwissProtSlice


/******************************************************************************/
  public void initialize ()
  {
  }  // method initialize


/******************************************************************************/
  public void setName ( String name )
  {
    // Truncate the name at the first period.
    int index = name.indexOf ( "." );
    if ( index > 0 )  name = name.substring ( 0, index );

    // Set the file names.
    fasta_file.setFileName    ( name + ".domains" );

    // Open the output files for writing.
    fasta_file.openFile ();
  }  // method setName


/******************************************************************************/
  public void close ()
  {
    // Close the output files.
    fasta_file.closeFile ();
  }  // method close


/******************************************************************************/
  private void writeHeaderLine
      ( OutputTools      output_file
      , SwissProtHeader  sp_header
      )
  {
    output_file.print ( ">" );

    // output_file.print ( sp_header.getAccession () );
    output_file.print ( sp_header.getId () );

    output_file.print ( " /organism=\"" );
    output_file.print ( sp_header.getOrganism () );

    output_file.print ( "\" /accession=\"" );
    output_file.print ( sp_header.getAccession () );
 
    if ( sp_header.getGeneName ().length () > 0 )
    { 
      output_file.print ( "\" /gene=\"" );
      output_file.print ( sp_header.getGeneName () );
    }  // if

    if ( sp_header.getKeywords ().length () > 0 )
    { 
      output_file.print ( "\" /keywords=\"" );
      output_file.print ( sp_header.getKeywords () );
    }  // if

    if ( sp_header.getEcNumber ().length () > 0 )
    { 
      output_file.print ( "\" /EC=\"" );
      output_file.print ( sp_header.getEcNumber () );
    }  // if

    if ( sp_header.getGeneId () > 0 )
    { 
      output_file.print ( "\" /gene_id=\"" );
      output_file.print ( sp_header.getGeneId () );
    }  // if

    if ( sp_header.getChromosome ().length () > 0 )
    { 
      output_file.print ( "\" /chromosome=\"" );
      output_file.print ( sp_header.getChromosome () );
    }  // if

    if ( sp_header.getCoordinates ().length () > 0 )
    { 
      output_file.print ( "\" /coordinates=\"" );
      output_file.print ( sp_header.getCoordinates () );
    }  // if

    if ( sp_header.getNcbiTaxid () > 0 )
    { 
      output_file.print ( "\" /taxon_id=\"" );
      output_file.print ( sp_header.getNcbiTaxid () );
    }  // if

    if ( sp_header.getSwissId ().length () > 0 )
    {
      output_file.print ( "\" /swiss_id=\"" );
      output_file.print ( sp_header.getSwissId () );
    }  // if

    if ( sp_header.getRefseqId ().length () > 0 )
    {
      output_file.print ( "\" /refseq_id=\"" );
      output_file.print ( sp_header.getRefseqId () );
    }  // if
 
    output_file.print ( "\" /description=\"" );
    output_file.print ( sp_header.getDescription () );
  
    output_file.print ( "\" /id=\"" );
    output_file.print ( sp_header.getId () );

    output_file.print ( "\" /taxonomy=\"" );
    output_file.print ( sp_header.getTaxonomy () );
    output_file.println ( "\"" );
  }  // method writeHeaderLine


/******************************************************************************/
  private void writeHeaderLine
      ( OutputTools      output_file
      , SwissProtHeader  sp_header
      , SwissProtFeature feature
      )
  {
    output_file.print ( ">" );

    output_file.print ( sp_header.getAccession () );
    output_file.print ( "_" + feature.getFromValue () );
    output_file.print ( "-" + feature.getToValue () );

    output_file.print ( " /organism=\"" );
    output_file.print ( sp_header.getOrganism () );
  
    output_file.print ( "\" /description=\"" );
    output_file.print ( feature.getDescription () );
    output_file.println ( "\"" );
  }  // method writeHeaderLine


/******************************************************************************/
  private void writeEntry
      ( SwissProtHeader  sp_header
      , String           aa_sequence
      )
  {
    // Write out the header line.
    writeHeaderLine ( fasta_file, sp_header );

    // Write out the specified sequence.
    SeqTools.writeFasta ( fasta_file, aa_sequence );
  }  // method writeEntry


/******************************************************************************/
  private void writeFeature
      ( OutputTools      output_file
      , SwissProtHeader  sp_header
      , SwissProtFeature feature
      , String           aa_sequence
      )
  {
    // Get the coordinates of the feature.
    int start = feature.getFromValue ();
    int end   = feature.getToValue ();

    // Check the coordinates.
    if ( ( start < 1 ) ||
         ( end   < 1 ) || 
         ( end   < start ) ||
         ( start >= aa_sequence.length () ) ||
         ( end   >  aa_sequence.length () ) ||
         ( end - start + 1 <= 24 ) )  

      return;

    // Splice out the coordinate from the AA sequence.
    String aa_seg = aa_sequence.substring ( start-1, end );

    // Check for very short sequence.
    if ( aa_seg.length () < 2 )  return;

    // Write out the header line.
    writeHeaderLine ( output_file, sp_header, feature );

    // Write out the specified sequence.
    SeqTools.writeFasta ( output_file, aa_seg );
  }  // method writeFeature


/******************************************************************************/
  private void processFeature
      ( SwissProtHeader  sp_header
      , SwissProtFeature feature
      , String           aa_sequence
      )
  {
    // Check for a key_name of interest.
    if ( feature.getKeyName ().equals ( "DOMAIN" ) == true ) 
    { 
      String description = feature.getDescription ();

      // Check for low complexity domains.
      if ( ( description.equals  ( "ALPHA" ) != true ) &&
           ( description.equals  ( "BETA" ) != true ) &&
           ( description.equals  ( "POLAR" ) != true ) &&

           ( description.indexOf ( "CATALYTIC" ) == -1 ) &&
           ( description.indexOf ( "COIL" ) == -1 ) &&
           ( description.indexOf ( "CYTOPLASMIC" ) == -1 ) &&
           ( description.indexOf ( "CYSTEINE DOMAIN" ) == -1 ) &&
           ( description.indexOf ( "EXTENSIN REPETIVE" ) == -1 ) &&
           ( description.indexOf ( "EXTERNAL LOOP" ) == -1 ) &&
           ( description.indexOf ( "EXTRACELLULAR" ) == -1 ) &&
           ( description.indexOf ( "GLOBULAR" ) == -1 ) &&
           ( description.indexOf ( "HIGHLY CHARGED" ) == -1 ) &&
           ( description.indexOf ( "HIGHLY REPETITIVE" ) == -1 ) &&
           ( description.indexOf ( "HINGE" ) == -1 ) &&
           ( description.indexOf ( "HYDROPHOBIC" ) == -1 ) &&
           ( description.indexOf ( "INSERTION IS1" ) == -1 ) &&
           ( description.indexOf ( "INTERMEMBRANE" ) == -1 ) &&
           ( description.indexOf ( "INTERVENING SEQUENCE" ) == -1 ) &&
           ( description.indexOf ( "INTRAVACUOLAR" ) == -1 ) &&
           ( description.indexOf ( "LINKER" ) == -1 ) &&
           ( description.indexOf ( "LUMENAL" ) == -1 ) &&
           ( description.indexOf ( "NONHELICAL REGION" ) == -1 ) &&
           ( description.indexOf ( "NUCLEAR LOCALIZATION" ) == -1 ) &&
           ( description.indexOf ( "PENTAPEPTIDE" ) == -1 ) &&
           ( description.indexOf ( "PERIPLASMIC" ) == -1 ) &&
           ( description.indexOf ( "PROLINE DOMAIN" ) == -1 ) &&
           ( description.indexOf ( "POLY-" ) == -1 ) &&
           ( description.indexOf ( "REPEATS" ) == -1 ) &&
           ( description.indexOf ( "SPACER" ) == -1 ) &&
           ( description.indexOf ( "TANDEM REPEAT" ) == -1 ) &&
           ( description.indexOf ( "-RICH" ) == -1 ) )

        writeFeature ( fasta_file, sp_header, feature, aa_sequence );
    }  // if
  }  // method processFeature


/******************************************************************************/
  public void processEntry
      ( SwissProtHeader sp_header
      , Vector          sp_features
      , String          aa_sequence
      )
  {
    // Check for a SwissProt Header object.
    if ( sp_header == null )  return;

    // Check for a AA sequence.
    if ( aa_sequence.length () <= 0 )  return;

    // Check for an Eukaryota taxonomy
    // if ( sp_header.getKingdom ().equals ( "Eukaryota" ) == false )  return;

    writeEntry ( sp_header, aa_sequence );
  }  // method processEntry


/******************************************************************************/

}  // class SwissProtSlice

