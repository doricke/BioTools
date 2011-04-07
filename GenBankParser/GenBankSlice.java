
import java.util.Vector;

// import GenBankFeature;
// import GenBankHeader;
// import InputTools;
// import OutputTools;
// import SeqTools;
// import Taxons;


/******************************************************************************/
/**
  This class slices desired sequences from a GenBank .seq file.
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

public class GenBankSlice extends Object
{


/******************************************************************************/

  private static final int MIN_AA_LENGTH = 15;		// Minimum protein sequence length to write

  private static final int MIN_DNA_LENGTH = 50;		// Minimum DNA sequence length to write


/******************************************************************************/


  // main taxon translations - group - target
  private OutputTools target_aas_file = new OutputTools ();	// target translations

  private OutputTools target_bacs_file = new OutputTools ();	// target BACs

  private OutputTools target_info_file = new OutputTools ();	// target information file


  // Taxonomy group mRNA sequences
  private OutputTools group_aas_files [] = new OutputTools [ Taxons.taxons.length ];

  private OutputTools group_est_files [] = new OutputTools [ Taxons.taxons.length ];

  private OutputTools group_genomic_files [] = new OutputTools [ Taxons.taxons.length ];

  private OutputTools group_mrna_files [] = new OutputTools [ Taxons.taxons.length ];


  // Species mRNA sequences
  private OutputTools species_aas_files [] = new OutputTools [ Taxons.species.length ];

  private OutputTools species_est_files [] = new OutputTools [ Taxons.species.length ];

  private OutputTools species_genomic_files [] = new OutputTools [ Taxons.species.length ];

  private OutputTools species_mrna_files [] = new OutputTools [ Taxons.species.length ];


  // Feature sequences
  private OutputTools feature_files [] = new OutputTools [ Taxons.features.length ];

  private OutputTools target_repeats_file = new OutputTools ();	// repetitive sequences


  private String chromosome = "";		// chromosome name

  private String sub_species = "";		// sub_species

  private String taxon_id = "";			// taxon id


/******************************************************************************/
  public GenBankSlice ()
  {
    initialize ();
  }  // constructor GenBankSlice


/******************************************************************************/
  public void initialize ()
  {
    chromosome = "";
    sub_species = "";
  }  // method initialize


/******************************************************************************/
  public String makeFileName ( String name, String suffix )
  {
    return name + "." + suffix;
  }  // method makeFileName


/******************************************************************************/
  private void setupFiles ( String name )
  {
    int index;					// String array index

    // Set up the individual files.
    setupFile ( target_bacs_file, name, Taxons.target_name + "_bacs" );
    setupFile ( target_aas_file, name, Taxons.target_name + "_aas" );
    setupFile ( target_info_file, name, Taxons.target_name + "_info" );
    setupFile ( target_repeats_file, name, Taxons.target_name + "_repeats" );

    target_info_file.println ( "Seq_name\tProtein_ID\tGene\tProduct\t"
        + "LocusID\tEntrez\tFlybase\tMGI\tGeneID\tChromosome\tName\tFunction\tNote\t"
        + "LocusTag\tSynonym\tEC_Number\tDefinition" );

    // Set up the group files.
    for ( index = 0; index < Taxons.taxons.length; index++ )
    {
      // Allocate the files.
      group_aas_files [ index ] = new OutputTools ();
      group_est_files [ index ] = new OutputTools ();
      group_genomic_files [ index ] = new OutputTools ();
      group_mrna_files [ index ] = new OutputTools ();

      // Set the file names.
      group_aas_files [ index ].setFileName ( name + "." + Taxons.taxons [ index ] + ".aas" );
      group_est_files [ index ].setFileName ( name + "." + Taxons.taxons [ index ] + ".ests" );
      group_genomic_files [ index ].setFileName ( name + "." + Taxons.taxons [ index ] + ".genomic" );
      group_mrna_files [ index ].setFileName ( name + "." + Taxons.taxons [ index ] + ".mRNA" );

      // Open the output files for writing.
      group_aas_files [ index ].openFile ();
      group_est_files [ index ].openFile ();
      group_genomic_files [ index ].openFile ();
      group_mrna_files [ index ].openFile ();
    }  // for

    // Set up the Taxons features files.
    for ( index = 0; index < Taxons.features.length; index++ )
    {
      // Allocate the files.
      feature_files [ index ] = new OutputTools ();

      // Set the file names.
      feature_files [ index ].setFileName ( name + "." 
          + Taxons.features [ index ].replace ( ' ', '_' ) );

      // Open the output files for writing.
      feature_files [ index ].openFile ();
    }  // for

    // Set up the output files.
    for ( index = 0; index < Taxons.species.length; index++ )
    {
      // Allocate the files.
      species_aas_files [ index ] = new OutputTools ();
      species_est_files [ index ] = new OutputTools ();
      species_genomic_files [ index ] = new OutputTools ();
      species_mrna_files [ index ] = new OutputTools ();

      // Set the file names.
      species_aas_files [ index ].setFileName ( name + "." 
          + Taxons.species [ index ].replace ( ' ', '_' ) + ".aas" );

      species_est_files [ index ].setFileName ( name + "." 
          + Taxons.species [ index ].replace ( ' ', '_' ) + ".ests" );

      species_genomic_files [ index ].setFileName ( name + "." 
          + Taxons.species [ index ].replace ( ' ', '_' ) + ".genomic" );

      species_mrna_files [ index ].setFileName ( name + "." 
          + Taxons.species [ index ].replace ( ' ', '_' ) + ".mRNA" );

      // Open the output files for writing.
      species_aas_files [ index ].openFile ();
      species_est_files [ index ].openFile ();
      species_genomic_files [ index ].openFile ();
      species_mrna_files [ index ].openFile ();
    }  // for
  }  // method setupFiles


/******************************************************************************/
  private void setupFile ( OutputTools file, String name, String suffix )
  {
    // Truncate the name at the first period.
    int index = name.indexOf ( "." );
    if ( index > 0 )  name = name.substring ( 0, index );

    // Set the file names.
    file.setFileName ( name + "." + suffix );

    // Open the output files for writing.
    file.openFile ();
  }  // method setupFile


/******************************************************************************/
  public void setName ( String name )
  {
    // Truncate the name at the first period.
    int index = name.indexOf ( "." );
    if ( index > 0 )  name = name.substring ( 0, index );

    // Set up the output files using name as the prefix.
    setupFiles ( name );
  }  // method setName


/******************************************************************************/
  public void close ()
  {
    int index;					// array index

    // Close the output files.
    target_bacs_file.closeFile ();
    target_aas_file.closeFile ();
    target_info_file.closeFile ();
    target_repeats_file.closeFile ();

    // Close the group files. 
    for ( index = 0; index < group_mrna_files.length; index++ )
    {
      group_aas_files [ index ].closeFile ();
      group_est_files [ index ].closeFile ();
      group_genomic_files [ index ].closeFile ();
      group_mrna_files [ index ].closeFile ();
    }  // for

    // Close the feature files. 
    for ( index = 0; index < feature_files.length; index++ )

      feature_files [ index ].closeFile ();

    // Close the species files. 
    for ( index = 0; index < species_mrna_files.length; index++ )
    {
      species_aas_files [ index ].closeFile ();
      species_est_files [ index ].closeFile ();
      species_genomic_files [ index ].closeFile ();
      species_mrna_files [ index ].closeFile ();
    }  // for
  }  // method close


/******************************************************************************/
  private String checkRice ( String organism )
  {
    if ( organism.indexOf ( "Oryza sativa" ) < 0 )  return "";

    if ( organism.indexOf ( "japonica" ) > 0 )  return "japonica";
    if ( organism.indexOf ( "indica" )   > 0 )  return "indica";

    return "";
  }  // method checkRice


/******************************************************************************/
  private void writeHeaderLine
      ( OutputTools    output_file
      , GenBankHeader  gb_header
      , GenBankFeature feature
      )
  {
    output_file.print ( ">" );

    if ( feature.getProteinId ().length () > 0 )
      output_file.print ( feature.getProteinId () );
    else
    {
      output_file.print ( gb_header.getVersionName () );
      output_file.print ( "_" + (String) feature.getStarts ().firstElement () );
      output_file.print ( "-" + (String) feature.getEnds   ().lastElement () );
    }  // else

    output_file.print ( " /organism=\"" );
    output_file.print ( gb_header.getOrganism () );
    output_file.print ( "\" " );

    // Print out the sub_species (if it is known)
    if ( sub_species.length () > 0 )  
      sub_species = checkRice ( gb_header.getOrganism () );
    if ( sub_species.length () > 0 )
      output_file.print ( "/sub_species=\"" + sub_species + "\" " );
    
    // Print the chromosome location (if it is known). 
    if ( chromosome.length () > 0 )
      output_file.print ( "/chromosome=\"" + chromosome + "\" " );

    // Print out the molecule type.
    output_file.print ( "/molecule=\"" + gb_header.getMolecule () + "\" " );

    // Print out the sequence date.
    output_file.print ( "/date=\"" + gb_header.getDate () + "\" " );
 
    output_file.print ( feature.getQualifiers () );
    
    // Print the organism taxon id (if it is known). 
    if ( taxon_id.length () > 0 )
      output_file.print ( "/taxon_id=\"" + taxon_id + "\" " );

    output_file.print ( " /taxonomy=\"" + gb_header.getTaxonomy () + "\"" );

    if ( gb_header.getHost ().length () > 0 )
      output_file.print ( " /host=\"" + gb_header.getHost () + "\"" );

    if ( gb_header.getStrain ().length () > 0 )
      output_file.print ( " /strain=\"" + gb_header.getStrain () + "\"" );

    output_file.println ( " /def=\"" + gb_header.getDefinition () + "\"" );
  }  // method writeHeaderLine


/******************************************************************************/
  private void writeHeaderLine
      ( OutputTools    output_file
      , GenBankHeader  gb_header
      )
  {
    output_file.print ( ">" );

    output_file.print ( gb_header.getVersionName () );

    output_file.print ( " /organism=\"" );
    output_file.print ( gb_header.getOrganism () );
    output_file.print ( "\" " );

    // Print out the sub_species (if it is known)
    if ( sub_species.length () > 0 )  
      sub_species = checkRice ( gb_header.getOrganism () );
    if ( sub_species.length () > 0 )
      output_file.print ( "/sub_species=\"" + sub_species + "\" " );

    // Print the chromosome location if it is known. 
    if ( chromosome.length () > 0 )
      output_file.print ( " /chromosome=\"" + chromosome + "\" " );

    // Print out the molecule type.
    output_file.print ( "/molecule=\"" + gb_header.getMolecule () + "\" " );

    // Print out the sequence date.
    output_file.print ( "/date=\"" + gb_header.getDate () + "\" " );

    output_file.print ( " /taxonomy=\"" + gb_header.getTaxonomy () + "\"" );

    if ( gb_header.getHost ().length () > 0 )
      output_file.print ( " /host=\"" + gb_header.getHost () + "\"" );

    if ( gb_header.getStrain ().length () > 0 )
      output_file.print ( " /strain=\"" + gb_header.getStrain () + "\"" );

    output_file.println ( " /def=\"" + gb_header.getDefinition () + "\"" );
  }  // method writeHeaderLine


/******************************************************************************/
  private void writeFeature
      ( OutputTools    output_file
      , GenBankHeader  gb_header
      , GenBankFeature feature
      , String         sequence
      )
  {
    // System.out.println ( "writeFeature called: " + feature.getFeatureType () );

    StringBuffer mRNA = new StringBuffer ( 3000 );

    Vector starts = feature.getStarts ();
    Vector ends   = feature.getEnds ();

    // Set the 5' incomplete flag if known.
    // if ( feature.isIncomplete5 () == true )  mRNA.append ( "<" );

    // Splice out the coordinate from the sequence.
    int index = 0;			// Vector index
    while ( ( index < starts.size () ) && ( index < ends.size () ) )
    {
      // Get the coordinates of the feature.
      int start = InputTools.getInteger ( (String) starts.elementAt ( index ) );
      int end   = InputTools.getInteger ( (String) ends  .elementAt ( index ) );

      // Check the coordinates.
      if ( ( start < 1 ) ||
           ( end   < 1 ) || 
           ( end   < start ) ||
           ( start >= sequence.length () ) ||
           ( end   >  sequence.length () ) )  
      {
        // System.out.println ( "\t[" + start + ".." + end + "]" );
      }  // if
      else
      {
        if ( end < sequence.length () )
          mRNA.append ( sequence.substring ( start-1, end ) );
        else
          mRNA.append ( sequence.substring ( start-1 ) );
      }  // if

      index++;

      // Insert a marker of exon boundaries.
      // if ( index < starts.size () )  mRNA.append ( "|" );
    }  // while

    // Set the 3' incomplete flag if known.
    // if ( feature.isIncomplete3 () == true )  mRNA.append ( ">" );

    // Check for very short sequence.
    if ( mRNA.length () < MIN_DNA_LENGTH )
    {
      // System.out.println ( "Ignoring short mRNAs, length = " + mRNA.length () );
      return;
    }  // if

    // Write out the header line.
    writeHeaderLine ( output_file, gb_header, feature );

    // Write out the specified sequence.
    if ( feature.getStrand () == '+' )
      SeqTools.writeFasta ( output_file, mRNA.toString () );
    else
      SeqTools.writeFasta ( output_file, 
          SeqTools.reverseSequence ( mRNA.toString () ) );

    mRNA.setLength ( 0 );
    mRNA = null;
  }  // method writeFeature


/******************************************************************************/
  private void writeTranslation 
      ( OutputTools    output_file
      , GenBankHeader  gb_header
      , GenBankFeature feature
      , String         sequence
      )
  {
    // Ignore short translations.
    if ( sequence.length () < MIN_AA_LENGTH )  return;

    // Write out the header line.
    writeHeaderLine ( output_file, gb_header, feature );

    SeqTools.writeFasta ( output_file, sequence );
  }  // method writeTranslation


/******************************************************************************/
  private void writeInfo
      ( GenBankHeader   gb_header
      , GenBankFeature  feature
      )
  {
    if ( feature.getProteinId ().length () > 0 )
      target_info_file.print ( feature.getProteinId () );
    else
    {
      target_info_file.print ( gb_header.getVersionName () );
      // target_info_file.print ( "_" + (String) feature.getStarts ().firstElement () );
      // target_info_file.print ( "-" + (String) feature.getEnds   ().lastElement () );
    }  // else

    target_info_file.print ( "\t" + feature.getProteinId () );
    target_info_file.print ( "\t" + feature.getGene () );
    target_info_file.print ( "\t" + feature.getProduct () );

    target_info_file.print ( "\t" + feature.getDbLocus () );
    target_info_file.print ( "\t" + feature.getDbMim () );
    target_info_file.print ( "\t" + feature.getDbEntrez () );
    target_info_file.print ( "\t" + feature.getDbFlybase () );
    target_info_file.print ( "\t" + feature.getDbMgi () );
    target_info_file.print ( "\t" + feature.getDbGeneId () );

    target_info_file.print ( "\t" + chromosome );
    target_info_file.print ( "\t" + feature.getName () );
    target_info_file.print ( "\t" + feature.getFunction () );
    target_info_file.print ( "\t" + feature.getNote () );
    target_info_file.print ( "\t" + feature.getLocusTag () );
    target_info_file.print ( "\t" + feature.getSynonym () );
    target_info_file.print ( "\t" + feature.getEcNumber () );

    target_info_file.println ( "\t" + gb_header.getDefinition () );
  }  // writeInfo


/******************************************************************************/
  private void selectTranslation
      ( GenBankHeader   gb_header
      , GenBankFeature  feature
      )
  {
    // Check for a peptide sequence.
    String translation = feature.getTranslation ();

    if ( translation.length () < 19 )  return;

    // Get the species taxonomy information.
    String taxonomy = gb_header.getTaxonomy ();

    if ( taxonomy.indexOf ( Taxons.target_name ) >= 0 )

      writeTranslation ( target_aas_file, gb_header, feature, translation );

    // Check for a group Taxons.species.
    String organism = gb_header.getOrganism ();

    // Search the list of selected Taxons.species.
    for ( int index = 0; index < species_mrna_files.length; index++ )

      if ( organism.startsWith ( Taxons.species [ index ] ) == true )
      {
        writeTranslation ( species_aas_files [ index ], gb_header, feature, translation );
      }  // if

    // Search the list of selected group Taxons.species.
    for ( int index = 0; index < group_mrna_files.length; index++ )

      if ( taxonomy.indexOf ( Taxons.taxons [ index ] ) >= 0 )
      {
        writeTranslation ( group_aas_files [ index ], gb_header, feature, translation );
      }  // if
  }  // method selectTranslation


/******************************************************************************/
  private void processFeature
      ( GenBankHeader   gb_header
      , GenBankFeature  feature
      , String          sequence
      )
  {
    // Ignore short sequences.
    if ( sequence.length () < MIN_DNA_LENGTH )  return;

    String taxonomy = gb_header.getTaxonomy ();

    // Check for a group Taxons.species.
    if ( taxonomy.indexOf ( Taxons.target_name ) >= 0 )
    {
      // Extract the rRNA, scRNA, snRNA, and rRNA Taxons.features/sequences.
      for ( int index = 0; index < feature_files.length; index++ )
      {
        if ( gb_header.getSequenceType ().equals ( Taxons.features [ index ] ) == true )
        {
          // Write out the header line.
          writeHeaderLine ( feature_files [ index ], gb_header, feature );
  
          SeqTools.writeFasta ( feature_files [ index ], sequence );
        }  // if
        else
  
          if ( feature.getFeatureType ().equals ( Taxons.features [ index ] ) == true )
    
            writeFeature ( feature_files [ index ], gb_header, feature, sequence );
      }  // for
    }  // if

    // Check for a feature type of interest.
    if ( ( feature.getFeatureType ().equals ( "mRNA" ) == true ) ||
         // ( feature.getFeatureType ().equals ( "Protein" ) == true ) ||
         ( feature.getFeatureType ().equals ( "CDS" ) == true ) )
    {
      // System.out.println ( "Found feature: " + feature.getFeatureType () + " " + feature.getProteinId () );
 
      // Ignore pseudo genes.
      if ( feature.isPseudo () == false )
      {
        // Select which output file to write to.

        // Check for a group Taxons.species.
        String organism = gb_header.getOrganism ();

        // Search the list of selected Taxons.species.
        for ( int index = 0; index < species_mrna_files.length; index++ )

          if ( organism.startsWith ( Taxons.species [ index ] ) == true )

            writeFeature ( species_mrna_files [ index ], gb_header, feature, sequence );

        // Search the list of selected group Taxons.species.
        // System.out.println ( "Taxonomy: " + taxonomy );

        for ( int index = 0; index < group_mrna_files.length; index++ )
        {
          // System.out.println ( "Checking taxon group: " + Taxons.taxons [ index ] );

          if ( taxonomy.indexOf ( Taxons.taxons [ index ] ) >= 0 )
          {
            // System.out.println ( "**** Found taxon group: " + Taxons.taxons [ index ] );

            writeFeature ( group_mrna_files [ index ], gb_header, feature, sequence );
          }  // if
        }  // for

        // Check for a peptide sequence.
        selectTranslation ( gb_header, feature );

        // Write out the gene information.
        writeInfo ( gb_header, feature );
      }  // if
    }  // if
    else
      if ( ( feature.getFeatureType ().equals ( "LTR" ) == true ) ||
           ( feature.getFeatureType ().equals ( "repeat_region" ) == true ) ||
           ( feature.getFeatureType ().equals ( "repeat_unit" ) == true ) ||
           ( feature.getFeatureType ().equals ( "satellite" ) == true ) ||
           ( feature.getInsertionSeq ().length () > 0 ) ||
           ( feature.isProviral () == true ) ||
           ( feature.getRptFamily ().length () > 0 ) ||
           ( feature.getRptType ().length () > 0 ) ||
           ( feature.getRptUnit ().length () > 0 ) ||
           ( feature.getTransposon ().length () > 0 ) ||
           ( feature.isVirion () == true ) )
      {
        // Check for target.
        if ( taxonomy.indexOf ( Taxons.target_name ) >= 0 )
          writeFeature ( target_repeats_file, gb_header, feature, sequence );
      }  // if
  }  // method processFeature


/******************************************************************************/
  private void processEST
      ( GenBankHeader  gb_header
      , String         sequence
      )
  {
    // Ignore short sequences.
    if ( sequence.length () < MIN_DNA_LENGTH )  return;

    // Search the list of selected species.
    String organism = gb_header.getOrganism ();
    for ( int index = 0; index < species_est_files.length; index++ )

      if ( organism.startsWith ( Taxons.species [ index ] ) == true )
      {
        writeHeaderLine ( species_est_files [ index ], gb_header );
        SeqTools.writeFasta ( species_est_files [ index ], sequence.toString () );
      }  // if

    // Search the list of taxonomy names of interest.
    String taxonomy = gb_header.getTaxonomy ();
    for ( int index = 0; index < group_est_files.length; index++ )

      if ( taxonomy.indexOf ( Taxons.taxons [ index ] ) >= 0 )
      {
        writeHeaderLine ( group_est_files [ index ], gb_header );
        SeqTools.writeFasta ( group_est_files [ index ], sequence.toString () );
      }  // if
  }  // method processEST


/******************************************************************************/
  private void processSpecies
      ( GenBankHeader  gb_header
      , String         sequence
      , OutputTools [] species_files
      , OutputTools [] group_files
      )
  {
    // Ignore short sequences.
    if ( sequence.length () < MIN_DNA_LENGTH )  return;

    // Search the list of selected species.
    String organism = gb_header.getOrganism ();
    for ( int index = 0; index < species_files.length; index++ )

      if ( organism.startsWith ( Taxons.species [ index ] ) == true )
      {
        writeHeaderLine ( species_files [ index ], gb_header );
        SeqTools.writeFasta ( species_files [ index ], sequence.toString () );
      }  // if

    // Search the list of taxonomy names of interest.
    String taxonomy = gb_header.getTaxonomy ();
    for ( int index = 0; index < group_files.length; index++ )

      if ( taxonomy.indexOf ( Taxons.taxons [ index ] ) >= 0 )
      {
        writeHeaderLine ( group_files [ index ], gb_header );
        SeqTools.writeFasta ( group_files [ index ], sequence.toString () );
      }  // if
  }  // method processSpecies


/******************************************************************************/
  private void checkChromosome ( Vector features )
  {
    // Check if chromosome is specified in source feature.
    if ( features.size () > 0 )
    {
      GenBankFeature feature = (GenBankFeature) features.elementAt ( 0 );

      // Set the sub_species.
      sub_species = feature.getSubSpecies ();

      chromosome = feature.getChromosome ();
    }  // if
  }  // method checkChromosome


/******************************************************************************/
  public void processEntry
      ( GenBankHeader  gb_header
      , Vector         features
      , String         sequence
      )
  {
    // System.out.println ( "GenBankSlice.processEntry called: org: " + gb_header.getOrganism () );
    // System.out.println ( gb_header.getVersionName () );

    // Check for a GenBank Header object.
    if ( gb_header == null )  return;

    // Check for a sequence.
    if ( sequence.length () <= 0 )  return;

    // Check for a chloroplast or mitochondrial sequence.
    if ( ( gb_header.getOrganism ().startsWith ( "Chloroplast" ) == true ) ||
         ( gb_header.getOrganism ().startsWith ( "Mitochon"    ) == true ) ||
         ( gb_header.getOrganism ().startsWith ( "Plastid"    ) == true ) )

      return;

    // Set the chromosome number.
    checkChromosome ( features );
/*
    // Collect target BACs.
    if ( ( sequence.length () > 50000 ) &&
         ( gb_header.getTaxonomy ().indexOf ( Taxons.target_name ) >= 0 ) )
    {
      // Write out the header line.
      writeHeaderLine ( target_bacs_file, gb_header );

      SeqTools.writeFasta ( target_bacs_file, sequence );
    }  // if
*/
    // Process the Features table.
    // System.out.print ( "\t" + features.size () + " features" );

    taxon_id = "";
    if ( features.size () > 0 )
      taxon_id = ((GenBankFeature) features.elementAt ( 0 )).getTaxon ();

    for ( int index = 0; index < features.size (); index++ )

      processFeature 
          ( gb_header
          , (GenBankFeature) features.elementAt ( index )
          , sequence 
          );

    // Capture mRNA & EST sequences with no features.
    if ( gb_header.getSequenceType ().equals ( "mRNA" ) == true )
    {
      if ( ( features.size () <= 1 )  && ( gb_header.getDivisionCode ().equals ( "EST" ) == false ) )

        processSpecies ( gb_header, sequence, species_mrna_files, group_mrna_files );

      else

        // Check for only source feature & species of interest.
        if ( gb_header.getDivisionCode ().equals ( "EST" ) )
  
          // Extract the EST sequence for target species.
          processEST ( gb_header, sequence );
    }  // if
    else
    {
      // Check for genomic sequence.
      if ( gb_header.getSequenceType ().equals ( "DNA" ) == true )

        processSpecies ( gb_header, sequence, species_genomic_files, group_genomic_files );

      else
        // Check for protein sequence.
        if ( gb_header.getSequenceType ().equals ( "aa" ) == true )

          processSpecies ( gb_header, sequence, species_aas_files, group_aas_files );
    }  // else
  }  // method processEntry


/******************************************************************************/

}  // class GenBankSlice

