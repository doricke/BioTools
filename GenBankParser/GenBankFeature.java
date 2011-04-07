
import java.util.Vector;


/******************************************************************************/
/**
  This class models features for a GenBank sequence.
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

public class GenBankFeature extends Object
{


/******************************************************************************/

  private String  chromosome = "";		// /chromosome=

  private int     citation = 0;			// /citation=

  private String  coded_by = "";		// /coded_by=

  private String  clone = "";			// /clone=

  private int     codon_start = 0;		// /codon_start=1 or 2 or 3

  private String  db_entrez = "";		// /db_xref="EntrezGene:<identifier>"

  private String  db_flybase = "";		// /db_xref="FLYBASE:<identifier>"

  private String  db_gene_id = "";		// /db_xref="GeneID:<identifier>"

  private String  db_locus = "";		// /db_xref="LocusID:<identifier>"

  private String  db_mgi = "";			// /db_xref="MGI:<identifier>"

  private String  db_mim = "";			// /db_xref="MIM:<identifier>"

  private String  db_xref = "";			// /db_xref="<database>:<identifier>"

  private String  ec_number = "";		// /EC_number=

  private String  evidence = "";		// /evidence=

  private String  experiment = "";		// /experiment=

  private String  function = "";		// /function=

  private String  gene = "";			// /gene=

  private String  gene_synonym = "";	// /gene_synonym=

  private String  go_component = "";	// /go_componenty=

  private String  go_function = "";		// /go_functiony=

  private String  go_process = "";		// /go_processy=

  private String  host = "";            // /host=

  private String  insertion_seq = "";	// /insertion_seq=

  private String  label = "";			// /label=

  private String  locus_tag = "";		// /locus_tag=

  private String  map = "";				// /map=

  private String  mol_type = "";		// /mol_type=

  private String  name = "";			// /name=

  private String  ncrna_class = "";		// /ncRNA_class=

  private String  note = "";			// /note=

  private String  number = "";			// /number=

  private String  organelle = "";		// /organelle=

  private String  organism = "";		// /organism=

  private boolean partial = false;		// /partial

  private String  product = "";			// /product=

  private String  protein_id = "";		// /protein_id=

  private boolean proviral = false;		// /proviral

  private boolean pseudo = false;		// /pseudo

  private String  region_name = "";		// /region_name=

  private String  rpt_family = "";		// /rpt_family=

  private String  rpt_type = "";		// /rpt_type=

  private String  rpt_unit = "";		// /rpt_unit=

  private String  satellite = "";		// /satellite=

  private String  segment = "";			// /segment=

  private String  source = "";			// /source= 

  private String  strain = "";			// /strain=
  
  private String  standard_name = "";	// /standard_name=

  private String  sub_species = "";		// /sub_species=

  private String  synonym = "";			// /synonym=

  private String  taxon = "";			// /db_xref="taxon:#"

  private String  translation = "";		// /translation=

  private String  transposon = "";		// /transposon=

  private boolean virion = false;		// /virion


  private int     cds_end = 0;			// end of CDS

  private int     cds_start = 0;		// start of CDS

  private String feature_type = "";		// feature type

  private char strand = '+';			// strand - '+' or '-'

  private boolean incomplete_5 = false;		// '<' seen on location

  private boolean incomplete_3 = false;		// '>' seen on location

  private Vector starts = new Vector ();	// start locations

  private Vector ends = new Vector ();		// end locations

  private int start = 0;			// start location

  private int end = 0;				// end location


/******************************************************************************/
  // Constructor GenBankFeature
  public GenBankFeature ()
  {
    initialize ();
  }  // constructor GenBankFeature


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    chromosome = "";
    citation = 0;
    clone = "";
    coded_by = "";
    codon_start = 0;
    db_entrez = "";
    db_flybase = "";
    db_gene_id = "";
    db_locus = "";
    db_mgi = "";
    db_mim = "";
    db_xref = "";
    ec_number = "";
    evidence = "";
    experiment = "";
    function = "";
    gene = "";
    gene_synonym = "";
    go_component= "";
    go_function = "";
    go_process = "";
    host = "";
    insertion_seq = "";
    label = "";
    locus_tag = "";
    map = "";
    mol_type = "";
    name = "";
    ncrna_class = "";
    note = "";
    number = "";
    organelle = "";
    organism = "";
    partial = false;
    product = "";
    protein_id = "";
    proviral = false;
    pseudo = false;
    rpt_family = "";
    rpt_type = "";
    rpt_unit = "";
    satellite = "";
    segment = "";
    source = "";
    strain = "";
    standard_name = "";
    sub_species = "";
    synonym = "";
    taxon = "";
    translation = "";
    transposon = "";
    virion = false;

    cds_end = 0;
    cds_start = 0;
    feature_type = "";
    strand = '+';
    incomplete_5 = false;
    incomplete_3 = false;
    starts.removeAllElements ();
    ends.removeAllElements ();
    start = 0;
    end = 0;
  }  // method initialize 


/******************************************************************************/
  public int getCdsEnd ()
  {
    return cds_end;
  }  // method getCdsEnd


/******************************************************************************/
  public int getCdsStart ()
  {
    return cds_start;
  }  // method getCdsStart


/******************************************************************************/
  public String getChromosome ()
  {
    return chromosome;
  }  // method getChromosome


/******************************************************************************/
  public int getCitation ()
  {
    return citation;
  }  // method getCitation


/******************************************************************************/
  public String getCoordinates ()
  {
    StringBuffer coordinates = new StringBuffer ( 60 );
    coordinates.setLength ( 0 );
    coordinates.append ( "Exons[" );

    int index = 0;
    while ( index < starts.size () )
    {
      coordinates.append ( (String) starts.elementAt ( index ) );

      int exon_start = InputTools.getInteger ( (String) starts.elementAt ( index ) );
      int exon_end   = InputTools.getInteger ( (String) ends  .elementAt ( index ) );

      // Check for CDS start coordinate.
      if ( ( cds_start > exon_start ) && ( cds_start < exon_end ) )
      {
        if ( strand == '+' )
          coordinates.append ( "-" + (cds_start-1) + "$" + cds_start );
        else
          coordinates.append ( "-" + cds_start + "$" + (cds_start+1) );
      }  // if

      // Check for CDS stop coordinate.
      if ( ( cds_end > exon_start ) && ( cds_end < exon_end ) )
      {
        if ( strand == '+' )
          coordinates.append ( "-" + cds_end + "*" + (cds_end+1) );
        else
          coordinates.append ( "-" + (cds_end-1) + "*" + cds_end );
      }  // if

      coordinates.append ( "-" + (String) ends.elementAt ( index ) );

      // Add the exon separator if more exons.
      if ( index + 1 < starts.size () )  coordinates.append ( "|" );
      index++;
    }  // while

    coordinates.append ( "] " );
    return coordinates.toString ();
  }  // method getCoordinates


/******************************************************************************/
  public String getClone ()
  {
    return clone;
  }  // method getClone


/******************************************************************************/
  public String getCodedBy ()
  {
    return coded_by;
  }  // method getCodedBy


/******************************************************************************/
  public int getCodonStart ()
  {
    return codon_start;
  }  // method getCodonStart


/******************************************************************************/
  public String getDbEntrez ()
  {
    return db_entrez;
  }  // method getDbEntrez


/******************************************************************************/
  public String getDbFlybase ()
  {
    return db_flybase;
  }  // method getDbFlybase


/******************************************************************************/
  public String getDbGeneId ()
  {
    return db_gene_id;
  }  // method getDbGeneId


/******************************************************************************/
  public String getDbLocus ()
  {
    return db_locus;
  }  // method getDbLocus


/******************************************************************************/
  public String getDbMgi ()
  {
    return db_mgi;
  }  // method getDbMgi


/******************************************************************************/
  public String getDbMim ()
  {
    return db_mim;
  }  // method getDbMim


/******************************************************************************/
  public String getDbXref ()
  {
    return db_xref;
  }  // method getDbXref


/******************************************************************************/
  public String getEcNumber ()
  {
    return ec_number;
  }  // method getEcNumber


/******************************************************************************/
  public int getEnd ()
  {
    return end;
  }  // method getEnd


/******************************************************************************/
  public Vector getEnds ()
  {
    return ends;
  }  // method getEnds


/******************************************************************************/
  public String getEvidence ()
  {
    return evidence;
  }  // method getEvidence


/******************************************************************************/
  public String getExperiment ()
  {
    return experiment;
  }  // method getExperiment


/******************************************************************************/
  public String getFeatureType ()
  {
    // Check for repetitive element annotation.
    checkRepeat ( note + product + gene + function );

    return feature_type;
  }  // method getFeatureType


/******************************************************************************/
  public String getFunction ()
  {
    return function;
  }  // method getFunction


/******************************************************************************/
  public String getGene ()
  {
    return gene;
  }  // method getGene


/******************************************************************************/
  public String getGeneSynonym ()
  {
    return gene_synonym;
  }  // method getGeneSynonym


/******************************************************************************/
  public String getHost ()
  {
    return host;
  }  // method getHost


/******************************************************************************/
  public String getInsertionSeq ()
  {
    return insertion_seq;
  }  // method getInsertionSeq


/******************************************************************************/
  public String getLabel ()
  {
    return label;
  }  // method getLabel


/******************************************************************************/
  public String getLocusTag ()
  {
    return locus_tag;
  }  // method getLocusTag


/******************************************************************************/
  public String getMap ()
  {
    return map;
  }  // method getMap


/******************************************************************************/
  public String getMolType ()
  {
    return mol_type;
  }  // method getMolType


/******************************************************************************/
  public String getName ()
  {
    return name;
  }  // method getName


/******************************************************************************/
  public String getNcrnaClass ()
  {
    return ncrna_class;
  }  // method getNnrnaClass


/******************************************************************************/
  public String getNote ()
  {
    return note;
  }  // method getNote


/******************************************************************************/
  public String getNumber ()
  {
    return number;
  }  // method getNumber


/******************************************************************************/
  public String getOrganelle ()
  {
    return organelle;
  }  // method getOrganelle


/******************************************************************************/
  public String getOrganism ()
  {
    return organism;
  }  // method getOrganism


/******************************************************************************/
  public boolean getPartial ()
  {
    return partial;
  }  // method getPartial


/******************************************************************************/
  public String getProduct ()
  {
    return product;
  }  // method getProduct


/******************************************************************************/
  public String getProteinId ()
  {
    return protein_id;
  }  // method getProteinId


/******************************************************************************/
  public boolean getProviral ()
  {
    return proviral;
  }  // method getProviral


/******************************************************************************/
  public boolean getPseudo ()
  {
    return pseudo;
  }  // method getPseudo


/******************************************************************************/
  public String getRegionName ()
  {
    return region_name;
  }  // method getRegionName


/******************************************************************************/
  public String getRptFamily ()
  {
    return rpt_family;
  }  // method getRptFamily


/******************************************************************************/
  public String getRptType ()
  {
    return rpt_type;
  }  // method getRptType


/******************************************************************************/
  public String getRptUnit ()
  {
    return rpt_unit;
  }  // method getRptUnit


/******************************************************************************/
  public String getSatellite ()
  {
    return satellite;
  }  // method getSatellite


/******************************************************************************/
  public String getSegment ()
  {
    return segment;
  }  // method getSegment


/******************************************************************************/
  public String getSource()
  {
    return source;
  }  // method getSource


  /******************************************************************************/
    public String getStrain ()
    {
      return strain;
    }  // method getStrain


/******************************************************************************/
  public String getStandardName ()
  {
    return standard_name;
  }  // method getStandardName


/******************************************************************************/
  public int getStart ()
  {
    return start;
  }  // method getStart


/******************************************************************************/
  public Vector getStarts ()
  {
    return starts;
  }  // method getStarts


/******************************************************************************/
  public char getStrand ()
  {
    return strand;
  }  // method getStrand


/******************************************************************************/
  public String getSubSpecies ()
  {
    return sub_species;
  }  // method getSubSpecies


/******************************************************************************/
  public String getSynonym ()
  {
    return synonym;
  }  // method getSynonym


/******************************************************************************/
  public String getTaxon ()
  {
    return taxon;
  }  // method getTaxon


/******************************************************************************/
  public String getTranslation ()
  {
    return translation;
  }  // method getTranslation


/******************************************************************************/
  public String getTransposon ()
  {
    return transposon;
  }  // method getTransposon


/******************************************************************************/
  public boolean getVirion ()
  {
    return virion;
  }  // method getVirion


/******************************************************************************/
  public String getQualifiers ()
  {
    StringBuffer qualifiers = new StringBuffer ( 1024 );

    // Include the exon coordinates.
    qualifiers.append ( getCoordinates () );

    qualifiers.append ( getValue ( "chromosome", chromosome ) );
    qualifiers.append ( getValue ( "citation", citation ) );
    qualifiers.append ( getValue ( "clone", clone ) );
    qualifiers.append ( getValue ( "coded_by", coded_by ) );
    qualifiers.append ( getValue ( "codon_start", codon_start ) );
    qualifiers.append ( getValue ( "db_entrez", db_entrez ) );
    qualifiers.append ( getValue ( "db_flybase", db_flybase ) );
    qualifiers.append ( getValue ( "db_gene_id", db_gene_id ) );
    qualifiers.append ( getValue ( "db_locus", db_locus ) );
    qualifiers.append ( getValue ( "db_mgi", db_mgi ) );
    qualifiers.append ( getValue ( "db_mim", db_mim ) );
    qualifiers.append ( getValue ( "db_xref", db_xref ) );
    qualifiers.append ( getValue ( "ec_number", ec_number ) );
    qualifiers.append ( getValue ( "evidence", evidence ) );
    // qualifiers.append ( getValue ( "experiment", experiment ) );
    qualifiers.append ( getValue ( "function", function ) );
    qualifiers.append ( getValue ( "gene", gene ) );
    qualifiers.append ( getValue ( "gene_synonym", gene_synonym ) );
    qualifiers.append ( getValue ( "host", host ) );
    qualifiers.append ( getValue ( "insertion_seq", insertion_seq ) );
    qualifiers.append ( getValue ( "label", label ) );
    qualifiers.append ( getValue ( "locus_tag", locus_tag ) );
    qualifiers.append ( getValue ( "map", map ) );
    qualifiers.append ( getValue ( "name", name ) );
    qualifiers.append ( getValue ( "number", number ) );
    qualifiers.append ( getValue ( "organelle", organelle ) );
    qualifiers.append ( getValue ( "organism", organism ) );
    qualifiers.append ( getValue ( "partial", partial ) );
    qualifiers.append ( getValue ( "product", product ) );
    qualifiers.append ( getValue ( "protein_id", protein_id ) );
    qualifiers.append ( getValue ( "proviral", proviral ) );
    qualifiers.append ( getValue ( "pseudo", pseudo ) );
    qualifiers.append ( getValue ( "region_name", region_name ) );
    qualifiers.append ( getValue ( "rpt_family", rpt_family ) );
    qualifiers.append ( getValue ( "rpt_type", rpt_type ) );
    qualifiers.append ( getValue ( "rpt_unit", rpt_unit ) );
    qualifiers.append ( getValue ( "standard_name", standard_name ) );
    qualifiers.append ( getValue ( "strain", strain ) );
    qualifiers.append ( getValue ( "sub_species", sub_species ) );
    qualifiers.append ( getValue ( "synonym", synonym ) );
    qualifiers.append ( getValue ( "transposon", transposon ) );
    qualifiers.append ( getValue ( "virion", virion ) );

    qualifiers.append ( getValue ( "ncRNA_class", ncrna_class ) );
    qualifiers.append ( getValue ( "note", note ) );
//    qualifiers.append ( getValue ( "translation", translation ) );

    return qualifiers.toString ();
  }  // method getQualifiers


/******************************************************************************/
  public boolean isIncomplete5 ()
  {
    return incomplete_5;
  }  // method isIncomplete5


/******************************************************************************/
  public boolean isIncomplete3 ()
  {
    return incomplete_3;
  }  // method isIncomplete3


/******************************************************************************/
  public boolean isPartial ()
  {
    return partial;
  }  // method isPartial


/******************************************************************************/
  public boolean isProviral ()
  {
    return proviral;
  }  // method isProviral


/******************************************************************************/
  public boolean isPseudo ()
  {
    return pseudo;
  }  // method isPseudo


/******************************************************************************/
  public boolean isVirion ()
  {
    return virion;
  }  // method isVirion


/******************************************************************************/
  public void setChromosome ( String value )
  {
    chromosome = value;
  }  // method setChromosome


/******************************************************************************/
  public void setCitation ( int value )
  {
    citation = value;
  }  // method setCitation


/******************************************************************************/
  public void setClone ( String value )
  {
    clone = value;
  }  // method setClone


/******************************************************************************/
  public void setCodedBy ( String value )
  {
    coded_by = value;
  }  // method setCodedBy


/******************************************************************************/
  public void setCodonStart ( int value )
  {
    codon_start = value;
  }  // method setCodonStart


/******************************************************************************/
  public void setDbEntrez ( String value )
  {
    db_entrez = value;
  }  // method setDbEntrez


/******************************************************************************/
  public void setDbFlybase ( String value )
  {
    db_flybase = value;
  }  // method setDbFlybase


/******************************************************************************/
  public void setDbGeneId ( String value )
  {
    db_gene_id = value;
  }  // method setDbGeneId


/******************************************************************************/
  public void setDbLocus ( String value )
  {
    db_locus = value;
  }  // method setDbLocus


/******************************************************************************/
  public void setDbMgi ( String value )
  {
    db_mgi = value;
  }  // method setDbMgi


/******************************************************************************/
  public void setDbMim ( String value )
  {
    db_mim = value;
  }  // method setDbMim


/******************************************************************************/
  public void setDbXref ( String value )
  {
    String identifier = "";		// identifier name
    int index = value.indexOf ( ':' );
    if ( index > 0 )  identifier = value.substring ( index+1 );
    if ( value.startsWith ( "EntrezGene:" ) == true )  setDbEntrez ( identifier );
    else 
      if ( value.startsWith ( "FLYBASE:" ) == true )  setDbFlybase ( identifier );
      else 
        if ( value.startsWith ( "GeneID:" ) == true )  setDbGeneId ( identifier );
        else 
          if ( value.startsWith ( "LocusID:" ) == true )  setDbLocus ( identifier );
          else  
            if ( value.startsWith ( "MGI:" ) == true )  setDbMgi ( identifier );
            else  
              if ( value.startsWith ( "MIM:" ) == true )  setDbMim ( identifier );
              else  
                if ( value.startsWith ( "taxon:" ) == true )  setTaxon ( identifier );
                else  
                  if ( db_xref.length () > 0 )  db_xref += ", " + value;
                  else db_xref = value;
  }  // method setDbXref


/******************************************************************************/
  public void setEcNumber ( String value )
  {
    ec_number = value;
  }  // method setEcNumber


/******************************************************************************/
  public void setEnd ( int value )
  {
    end = value;
  }  // method setEnd


/******************************************************************************/
  public void setEvidence ( String value )
  {
    evidence = value;
  }  // method setEvidence


/******************************************************************************/
  public void setExperiment ( String value )
  {
    experiment = value;
  }  // method setExperiment


/******************************************************************************/
  public void setFeatureType ( String value )
  {
    feature_type = value;
  }  // method setFeatureType


/******************************************************************************/
  public void setFunction ( String value )
  {
    function = value;
  }  // method setFunction


/******************************************************************************/
  public void setGene ( String value )
  {
    gene = value;
  }  // method setGene


/******************************************************************************/
  public void setGeneSynonym ( String value )
  {
    gene_synonym = value;
  }  // method setGeneSynonym


/******************************************************************************/
  public void setGoComponent ( String value )
  {
    go_component += value;
  }  // method setGoComponent


/******************************************************************************/
  public void setGoFunction ( String value )
  {
    go_function += value;
  }  // method setGoFunction


/******************************************************************************/
  public void setGoProcess ( String value )
  {
    go_process = value;
  }  // method setGoProcess


/******************************************************************************/
  public void setHost ( String value )
  {
    if ( value.length () > 0 )
      host = value;
  }  // method setHost


/******************************************************************************/
  public void setIncomplete5 ( boolean value )
  {
    incomplete_5 = value;
  }  // method setIncomplete5


/******************************************************************************/
  public void setIncomplete3 ( boolean value )
  {
    incomplete_3 = value;
  }  // method setIncomplete3


/******************************************************************************/
  public void setInsertionSeq ( String value )
  {
    insertion_seq = value;
  }  // method setInsertionSeq


/******************************************************************************/
  public void setLabel ( String value )
  {
    label = value;
  }  // method setLabel


/******************************************************************************/
  public void setLocusTag ( String value )
  {
    locus_tag = value;
  }  // method setLocusTag


/******************************************************************************/
  public void setMap ( String value )
  {
    map = value;
  }  // method setMap


/******************************************************************************/
  public void setMolType ( String value )
  {
    mol_type = value;
  }  // method setMolType


/******************************************************************************/
  public void setName ( String value )
  {
    name = value;
  }  // method setName


/******************************************************************************/
  public void setNcrnaClass ( String value )
  {
    ncrna_class = value;
  }  // method setNcrnaClass


/******************************************************************************/
  public void setNote ( String value )
  {
    note = value;
  }  // method setNote


/******************************************************************************/
  public void setNumber ( String value )
  {
    number = value;
  }  // method setNumber


/******************************************************************************/
  public void setOrganelle ( String value )
  {
    organelle = value;
  }  // method setOrganelle


/******************************************************************************/
  public void setOrganism ( String value )
  {
    organism = value;
  }  // method setOrganism


/******************************************************************************/
  public void setPartial ( boolean value )
  {
    partial = value;
  }  // method setPartial


/******************************************************************************/
  public void setProduct ( String value )
  {
    product = value;
  }  // method setProduct


/******************************************************************************/
  public void setProteinId ( String value )
  {
    protein_id = value;
  }  // method setProteinId


/******************************************************************************/
  public void setProviral ( boolean value )
  {
    proviral = value;
  }  // method setProviral


/******************************************************************************/
  public void setPseudo ( boolean value )
  {
    pseudo = value;
  }  // method setPseudo


/******************************************************************************/
  public void setRegionName ( String value )
  {
    region_name = value;
  }  // method setRegionName


/******************************************************************************/
  public void setRptFamily ( String value )
  {
    rpt_family = value;
  }  // method setRptFamily


/******************************************************************************/
  public void setRptType ( String value )
  {
    rpt_type = value;
  }  // method setRptType


/******************************************************************************/
  public void setRptUnit ( String value )
  {
    rpt_unit = value;
  }  // method setRptUnit


/******************************************************************************/
  public void setSatellite ( String value )
  {
    satellite = value;
  }  // method setSatellite


/******************************************************************************/
  public void setSegment ( String value )
  {
    segment = value;
  }  // method setSegment


/******************************************************************************/
  public void setSource ( String value )
  {
    source = value;
  }  // method setSource


  /******************************************************************************/
    public void setStrain ( String value )
    {
      if ( value.length () > 0 )
        strain = value;
    }  // method setStrain


/******************************************************************************/
  public void setStandardName ( String value )
  {
    standard_name = value;
  }  // method setStandardName


/******************************************************************************/
  public void setStart ( int value )
  {
    start = value;
  }  // method setStart


/******************************************************************************/
  public void setSubSpecies ( String value )
  {
    sub_species = value;
  }  // method setSubSpecies


/******************************************************************************/
  public void setSynonym ( String value )
  {
    synonym = value;
  }  // method setSynonym


/******************************************************************************/
  public void setTaxon ( String value )
  {
    taxon = value;
  }  // method setTaxon


/******************************************************************************/
  public void setTranslation ( String value )
  {
    translation = value;
  }  // method setTranslation


/******************************************************************************/
  public void setTransposon ( String value )
  {
    transposon = value;
  }  // method setTransposon


/******************************************************************************/
  public void setVirion ( boolean value )
  {
    virion = value;
  }  // method setVirion


/******************************************************************************/
  private int getCoordinate1 ( String coord )
  {
    if ( ( coord == null ) || ( coord.length () <= 0 ) )  return 0;

    // Check for "<number"
    if ( coord.charAt ( 0 ) == '<' )
    {
      partial = true;
      incomplete_5 = true;
      coord = coord.substring ( 1 );
    }  // if

    // Check for "(number.number)"
    if ( coord.charAt ( 0 ) == '(' )
    {
      incomplete_5 = true;
      coord = coord.substring ( 1 );
    }  // if

    return InputTools.getInteger ( coord );
  }  // method getCoordinate1


/******************************************************************************/
  private int getCoordinate2 ( String coord )
  {
    // Check for no coordinate.
    if ( ( coord == null ) || ( coord.length () <= 0 ) )  return 0;

    // Check for ">number"
    if ( coord.charAt ( 0 ) == '>' )
    {
      partial = true;
      incomplete_3 = true;
      coord = coord.substring ( 1 );
    }  // if

    // Check for "(number.number)"
    if ( coord.charAt ( 0 ) == '(' )
    {
      incomplete_5 = true;

      coord = coord.substring ( coord.indexOf ( "." ) + 1 );
    }  // if

    return InputTools.getInteger ( coord );
  }  // method getCoordinate2


/******************************************************************************/
  // This method sets the coordinate limits for this feature.
  private void newCoordinate ( int coordinate )
  {
    if ( ( coordinate < start ) || ( start == 0 ) )  start = coordinate;

    if ( ( coordinate > end ) || ( end == 0 ) )  end = coordinate;
  }  // method newCoordinate


/******************************************************************************/
  private void setPair ( String pair )
  {
    // System.out.println ( "\t" + pair );

    // Check for an indirect reference:
    if ( pair.indexOf ( ':' ) > 0 )
    {
      partial = true;
      return;
    }  // if

    int index = pair.indexOf ( ".." );

    // Check for a single location.
    if ( index == -1 )
    {
      int location = getCoordinate1 ( pair );
      starts.addElement ( "" + location );
      ends.addElement ( "" + location );
    }  // if
    else
    {
      int pair_start = getCoordinate1 ( pair.substring ( 0, index ) );
      int pair_end = getCoordinate2 ( pair.substring ( index + 2 ) );

      starts.addElement ( "" + pair_start );
      ends.addElement ( "" + pair_end );

      // Set the start and end coordinates of this feature.
      newCoordinate ( pair_start );
      newCoordinate ( pair_end );
    }  // else
  }  // method setPair


/******************************************************************************/
  public String removeOperators ( String value )
  {
    // Remove operators.
    while ( ( value.indexOf ( "complement" ) == 0 ) ||
            ( value.indexOf ( "group" ) == 0 ) ||
            ( value.indexOf ( "order" ) == 0 ) ||
            ( value.indexOf ( "join" ) == 0 ) ||
            ( value.indexOf ( "one-of" ) == 0 ) )
    {
      if ( ( value.indexOf ( "group" ) == 0 ) ||
           ( value.indexOf ( "order" ) == 0 ) ||
           ( value.indexOf ( "one-of" ) == 0 ) )  partial = true;

      if ( value.indexOf ( "complement" ) == 0 )  strand = '-';

      int index = value.indexOf ( "(" );
      if ( index > 0 )
        value = value.substring ( index + 1, value.length () - 1 );
      else
        System.out.println ( "No parenthesis: " + value );
    }  // while

    return value;
  }  // method removeOperators


/******************************************************************************/
  public void setLocation ( String feature_name, String value )
  {
    int index;				// value index

// System.out.println ( feature_name + " " + value );

    feature_type = feature_name;

    // Remove operators.
    value = removeOperators ( value );

    // Process each location pair.
    do
    {
      index = value.indexOf ( "," );

      if ( index == -1 )
      {
        setPair ( removeOperators ( value ) );
        return;
      }  // if
      else
        setPair ( removeOperators ( value.substring ( 0, index ) ) );

      value = value.substring ( index+1 );
    }
    while ( value.length () > 0 );
  }  // setLocation


/******************************************************************************/
  private String getValue ( String value )
  {
    // Assert: value
    if ( value.length () <= 3 )  return "";

    int index = value.indexOf ( "=" );

    if ( index < 0 )  return "";

    // Remove the quote after the equal sign.
    if ( value.length () > index + 1 )

      if ( value.charAt ( index + 1 ) == '"' )  index++;
    
    if ( value.length () > index + 1 )
      value = value.substring ( index+1 );

    // Remove trailing double quote.
    if ( value.charAt ( value.length () - 1 ) == '"' )
      return value.substring ( 0, value.length () - 1 );
    else
      return value;
  }  // method getValue


/******************************************************************************/
  public void setQualifier ( String feature_name, String value )
  {
    String qualifier_name;		// /qualifier_name
    int index = value.indexOf ( '=' );

    // System.out.println ( "GenBankFeature.setQualifier: feature_name = " 
    //     + feature_name + " value = '" + value + "'" );
 

    if ( ( feature_type.equals ( "repeat_region" ) != true ) &&
         ( feature_name.equals ( feature_type ) != true ) )
    {
      System.out.println ( "*Warning* setQualifier feature name (" 
          + feature_name + ") different from setLocation name (" 
          + feature_type + ")" );
    }  // if

    if ( index < 0 )
      qualifier_name = value;
    else
      qualifier_name = value.substring ( 0, index );

    // System.out.println ( "qual\t" + qualifier_name + ":\t" + value );

         if ( qualifier_name.equals ( "chromosome" ) == true )
           chromosome = getValue ( value );

    else if ( qualifier_name.equals ( "citation" ) == true )
           citation = InputTools.getInteger ( getValue ( value ).substring ( 1 ) );

    else if ( qualifier_name.equals ( "clone" ) == true )
           clone = getValue ( value );

    else if ( qualifier_name.equals ( "coded_by" ) == true )
           setCodedBy ( getValue ( value ) );

    else if ( qualifier_name.equals ( "codon_start" ) == true )
           codon_start = InputTools.getInteger ( getValue ( value ) );

    else if ( qualifier_name.equals ( "db_xref" ) == true )
           setDbXref ( getValue ( value ) );

    else if ( qualifier_name.equals ( "EC_number" ) == true )
           ec_number = getValue ( value );

    else if ( qualifier_name.equals ( "evidence" ) == true )
           evidence = getValue ( value );

    else if ( qualifier_name.equals ( "experiment" ) == true )
           experiment = getValue ( value );

    else if ( qualifier_name.equals ( "function" ) == true )
           function = getValue ( value );

    else if ( qualifier_name.equals ( "gene" ) == true )
           gene = getValue ( value );

    else if ( qualifier_name.equals ( "gene_synonym" ) == true )
           gene_synonym = getValue ( value );

    else if ( qualifier_name.equals ( "go_component" ) == true )
           go_component = getValue ( value );

    else if ( qualifier_name.equals ( "go_function" ) == true )
           go_function += getValue ( value );

    else if ( qualifier_name.equals ( "go_process" ) == true )
           go_process += getValue ( value );

    else if ( qualifier_name.equals ( "host" ) == true )
           host = getValue ( value );

    else if ( qualifier_name.equals ( "insertion_seq" ) == true )
           insertion_seq = getValue ( value );

    else if ( qualifier_name.equals ( "label" ) == true )
           label = getValue ( value );

    else if ( qualifier_name.equals ( "locus_tag" ) == true )
           locus_tag = getValue ( value );

    else if ( qualifier_name.equals ( "map" ) == true )
           map = getValue ( value );

    else if ( qualifier_name.equals ( "mol_type" ) == true )
           mol_type = getValue ( value );

    else if ( qualifier_name.equals ( "name" ) == true )
           setName ( getValue ( value ) );

    else if ( qualifier_name.equals ( "ncRNA_class" ) == true )
           setNcrnaClass ( getValue ( value ) );

    else if ( qualifier_name.equals ( "note" ) == true )
           setNote ( getValue ( value ) );

    else if ( qualifier_name.equals ( "number" ) == true )
           number = getValue ( value );

    else if ( qualifier_name.equals ( "organelle" ) == true )
           organelle = getValue ( value );

    else if ( qualifier_name.equals ( "organism" ) == true )
           organism = getValue ( value );

    else if ( qualifier_name.equals ( "partial" ) == true )
           partial = true;

    else if ( qualifier_name.equals ( "product" ) == true )
           setProduct ( getValue ( value ) );

    else if ( qualifier_name.equals ( "protein_id" ) == true )
           protein_id = getValue ( value );

    else if ( qualifier_name.equals ( "proviral" ) == true )
           proviral = true;

    else if ( qualifier_name.equals ( "pseudo" ) == true )
           pseudo = true;

    else if ( qualifier_name.equals ( "region_name" ) == true )
           setRegionName ( getValue ( value ) );

    else if ( qualifier_name.equals ( "rpt_family" ) == true )
           rpt_family = getValue ( value );

    else if ( qualifier_name.equals ( "rpt_type" ) == true )
           rpt_type = getValue ( value );

    else if ( qualifier_name.equals ( "rpt_unit" ) == true )
           rpt_unit = getValue ( value );

    else if ( qualifier_name.equals ( "satellite" ) == true )
           satellite = getValue ( value );

    else if ( qualifier_name.equals ( "segment" ) == true )
           segment = getValue ( value );

    else if ( qualifier_name.equals ( "source" ) == true )
           source = getValue ( value );

    else if ( qualifier_name.equals ( "strain" ) == true )
           strain = getValue ( value );

    else if ( qualifier_name.equals ( "standard_name" ) == true )
           standard_name = getValue ( value );

    else if ( qualifier_name.equals ( "sub_species" ) == true )
           sub_species = getValue ( value );

    else if ( qualifier_name.equals ( "synonym" ) == true )
           synonym = getValue ( value );

    else if ( qualifier_name.equals ( "translation" ) == true )
           translation = getValue ( value );

    else if ( qualifier_name.equals ( "transposon" ) == true )
           transposon = getValue ( value );

    else if ( qualifier_name.equals ( "virion" ) == true )
           virion = true;

    else if ( ( qualifier_name.equals ( "allele" ) == true ) ||
              ( qualifier_name.equals ( "anticodon" ) == true ) ||
              ( qualifier_name.equals ( "bio_material" ) == true ) ||
              ( qualifier_name.equals ( "bound_moiety" ) == true ) ||
              ( qualifier_name.equals ( "breed" ) == true ) ||
              ( qualifier_name.equals ( "codon" ) == true ) ||
              ( qualifier_name.equals ( "collected_by" ) == true ) ||
              ( qualifier_name.equals ( "cons_splice" ) == true ) ||
              ( qualifier_name.equals ( "country" ) == true ) ||
              ( qualifier_name.equals ( "cell_line" ) == true ) ||
              ( qualifier_name.equals ( "cell_type" ) == true ) ||
              ( qualifier_name.equals ( "clone_lib" ) == true ) ||
              ( qualifier_name.equals ( "collection_date" ) == true ) ||
              ( qualifier_name.equals ( "common" ) == true ) ||
              ( qualifier_name.equals ( "compare" ) == true ) ||
              ( qualifier_name.equals ( "cultivar" ) == true ) ||
              ( qualifier_name.equals ( "culture_collection" ) == true ) ||
              ( qualifier_name.equals ( "dev_stage" ) == true ) ||
              ( qualifier_name.equals ( "direction" ) == true ) ||
              ( qualifier_name.equals ( "ecotype" ) == true ) ||
              ( qualifier_name.equals ( "environmental_sample" ) == true ) ||
              ( qualifier_name.equals ( "estimated_length" ) == true ) ||
              ( qualifier_name.equals ( "exception" ) == true ) ||
              ( qualifier_name.equals ( "frequency" ) == true ) ||
              ( qualifier_name.equals ( "focus" ) == true ) ||
              ( qualifier_name.equals ( "germline" ) == true ) ||
              ( qualifier_name.equals ( "haplotype" ) == true ) ||
              ( qualifier_name.equals ( "identified_by" ) == true ) ||
              ( qualifier_name.equals ( "inference" ) == true ) ||
              ( qualifier_name.equals ( "isolate" ) == true ) ||
              ( qualifier_name.equals ( "isolation_source" ) == true ) ||
              ( qualifier_name.equals ( "lab_host" ) == true ) ||
              ( qualifier_name.equals ( "lat_lon" ) == true ) ||
              ( qualifier_name.equals ( "macronuclear" ) == true ) ||
              ( qualifier_name.equals ( "mobile_element" ) == true ) ||
              ( qualifier_name.equals ( "mobile_element_type" ) == true ) ||
              ( qualifier_name.equals ( "mod_base" ) == true ) ||
              ( qualifier_name.equals ( "old_locus_tag" ) == true ) ||
              ( qualifier_name.equals ( "operon" ) == true ) ||
              ( qualifier_name.equals ( "PCR_conditions" ) == true ) ||
              ( qualifier_name.equals ( "PCR_primers" ) == true ) ||
              ( qualifier_name.equals ( "phenotype" ) == true ) ||
              ( qualifier_name.equals ( "plasmid" ) == true ) ||
              ( qualifier_name.equals ( "pop_variant" ) == true ) ||
              ( qualifier_name.equals ( "rearranged" ) == true ) ||
              ( qualifier_name.equals ( "replace" ) == true ) ||
              ( qualifier_name.equals ( "ribosomal_slippage" ) == true ) ||
              ( qualifier_name.equals ( "rpt_unit_seq" ) == true ) ||
              ( qualifier_name.equals ( "rpt_unit_range" ) == true ) ||
              ( qualifier_name.equals ( "selenocysteine" ) == true ) ||
              ( qualifier_name.equals ( "serotype" ) == true ) ||
              ( qualifier_name.equals ( "serovar" ) == true ) ||
              ( qualifier_name.equals ( "sex" ) == true ) ||
              ( qualifier_name.equals ( "sequenced_mol" ) == true ) ||
              ( qualifier_name.equals ( "site_type" ) == true ) ||
              ( qualifier_name.equals ( "specific_host" ) == true ) ||
              ( qualifier_name.equals ( "specimen_voucher" ) == true ) ||
              ( qualifier_name.equals ( "sub_clone" ) == true ) ||
              ( qualifier_name.equals ( "sub_strain" ) == true ) ||
              ( qualifier_name.equals ( "strain" ) == true ) ||
              ( qualifier_name.equals ( "tissue_lib" ) == true ) ||
              ( qualifier_name.equals ( "tissue_type" ) == true ) ||
              ( qualifier_name.equals ( "transgenic" ) == true ) ||
              ( qualifier_name.equals ( "transcript_id" ) == true ) ||
              ( qualifier_name.equals ( "trans_splicing" ) == true ) ||
              ( qualifier_name.equals ( "transl_except" ) == true ) ||
              ( qualifier_name.equals ( "transl_table" ) == true ) ||
              ( qualifier_name.equals ( "usedin" ) == true ) ||
              ( qualifier_name.equals ( "variety" ) == true ) )
           ;

    else
      System.out.println ( "\tIgnoring: '" + qualifier_name + "' = " + value );
  }  // setQualifier


/******************************************************************************/
  private String getValue ( String name, boolean value )
  {
    // Check if the value is not set.
    if ( value == false )  return "";

    return "/" + name + "=" + value + " ";
  }  // method getValue


/******************************************************************************/
  private String getValue ( String name, int value )
  {
    // Check if the value is not set.
    if ( value == 0 )  return "";

    return "/" + name + "=" + value + " ";
  }  // method getValue


/******************************************************************************/
  private String getValue ( String name, String value )
  {
    // Check if the value is not set.
    if ( value.length () == 0 )  return "";

    return "/" + name + "=\"" + value + "\" ";
  }  // method getValue


/******************************************************************************/
  // This method sets the feature_type to "repeat_region" if a repeat is found.
  private void checkRepeat ( String value )
  {
    // Case sensitive patterns.
    if ( value.indexOf ( "MuDR" ) >= 0 )  feature_type = "repeat_region";
    if ( value.indexOf ( " mudrA" ) >= 0 )  feature_type = "repeat_region";
    if ( value.indexOf ( " mudr " ) >= 0 )  feature_type = "repeat_region";
    if ( value.indexOf ( "MURAZC" ) >= 0 )  feature_type = "repeat_region";
    if ( value.indexOf ( "Ty3-Gypsy" ) >= 0 )  feature_type = "repeat_region";
    if ( value.indexOf ( "Gypsy-Ty3" ) >= 0 )  feature_type = "repeat_region";
    if ( value.indexOf ( "TNP2-like" ) >= 0 )  feature_type = "repeat_region";

    // Case insensitive patterns.
    String text = value.toLowerCase ();
    if ( text.indexOf ( "copia-like" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "copia-type" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "copia poly" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag-pol" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag,pol" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag/pol" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag and pol" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag,protease" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag-protease" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag, protease" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag polyprotein" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "gag, polyprotein" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "integrase" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "pol polyprotein" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "retroelement" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "retrolelement" ) >= 0 )  feature_type = "repeat_region";  // spelling mistake in GenBank
    if ( text.indexOf ( "retrotranspos" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "retrotranpos" ) >= 0 )  feature_type = "repeat_region";  // spelling mistake in GenBank
    if ( text.indexOf ( "retroviral" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "retrovirus" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "reverse-transcriptase" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "transposon" ) >= 0 )  feature_type = "repeat_region";
    if ( text.indexOf ( "transposase" ) >= 0 )  feature_type = "repeat_region";

    // /function includes virus resistance
    // if ( text.indexOf ( "virus" ) >= 0 )  feature_type = "repeat_region";
  }  // method checkRepeat


/******************************************************************************/
  public void merge ( GenBankFeature cds )
  {
    // Update empty fields.
    if ( chromosome.length () == 0 )  chromosome = cds.getChromosome ();
    if ( citation == 0 )  citation = cds.getCitation ();
    if ( clone.length () == 0 )  clone = cds.getClone ();
    if ( coded_by.length () == 0 )  coded_by = cds.getCodedBy ();
    if ( codon_start == 0 )  codon_start = cds.getCodonStart ();
    if ( db_entrez.length () == 0 )  db_entrez = cds.getDbEntrez ();
    if ( db_flybase.length () == 0 )  db_flybase = cds.getDbFlybase ();
    if ( db_gene_id.length () == 0 )  db_gene_id = cds.getDbGeneId ();
    if ( db_locus.length () == 0 )  db_locus = cds.getDbLocus ();
    if ( db_mgi.length () == 0 )  db_mgi = cds.getDbMgi ();
    if ( db_mim.length () == 0 )  db_mim = cds.getDbMim ();
    if ( db_xref.length () == 0 )  db_xref = cds.getDbXref ();
    if ( ec_number.length () == 0 )  ec_number = cds.getEcNumber ();
    if ( evidence.length () == 0 )  evidence = cds.getEvidence ();
    if ( experiment.length () == 0 )  experiment = cds.getExperiment ();
    if ( function.length () == 0 )  function = cds.getFunction ();
    if ( gene.length () == 0 )  setGene ( cds.getGene () );
    if ( gene_synonym.length () == 0 )  setGeneSynonym ( cds.getGeneSynonym () );
    if ( host.length () == 0 )  setHost ( cds.getHost () );
    if ( insertion_seq.length () == 0 )  insertion_seq = cds.getInsertionSeq ();
    if ( label.length () == 0 )  label = cds.getLabel ();
    if ( locus_tag.length () == 0 )  locus_tag = cds.getLocusTag ();
    if ( map.length () == 0 )  map = cds.getMap ();
    if ( mol_type.length () == 0 )  mol_type = cds.getMolType ();
    if ( name.length () == 0 )  name = cds.getName ();
    if ( note.length () == 0 )  setNote ( cds.getNote () );
    if ( ncrna_class.length () == 0 )  setNcrnaClass ( cds.getNcrnaClass () );
    if ( number.length () == 0 )  number = cds.getNumber ();
    if ( organelle.length () == 0 )  organelle = cds.getOrganelle ();
    if ( organism.length () == 0 )  organism = cds.getOrganism ();
    if ( partial == false )  partial = cds.getPartial ();
    if ( product.length () == 0 )  setProduct ( cds.getProduct () );
    if ( protein_id.length () == 0 )  protein_id = cds.getProteinId ();
    if ( proviral == false )  proviral = cds.getProviral ();
    if ( pseudo == false )  pseudo = cds.getPseudo ();
    if ( region_name.length () == 0 )  region_name = cds.getRegionName ();
    if ( rpt_family.length () == 0 )  rpt_family = cds.getRptFamily ();
    if ( rpt_type.length () == 0 )  rpt_type = cds.getRptType ();
    if ( rpt_unit.length () == 0 )  rpt_unit = cds.getRptUnit ();
    if ( segment.length () == 0 )  segment = cds.getSegment ();
    if ( satellite.length () == 0 )  satellite = cds.getSatellite ();
    if ( source.length () == 0 )  source = cds.getSource ();
    if ( strain.length () == 0 )  strain = cds.getStrain ();
    if ( standard_name.length () == 0 )  standard_name = cds.getStandardName ();
    if ( sub_species.length () == 0 )  sub_species = cds.getSubSpecies ();
    if ( synonym.length () == 0 )  synonym = cds.getSynonym ();
    if ( translation.length () == 0 )  translation = cds.getTranslation ();
    if ( transposon.length () == 0 )  transposon = cds.getTransposon ();
    if ( virion == false )  virion = cds.getVirion ();
    if ( incomplete_5 == false )  incomplete_5 = cds.isIncomplete5 ();
    if ( incomplete_3 == false )  incomplete_3 = cds.isIncomplete3 ();

    cds_start = cds.getStart ();
    cds_end   = cds.getEnd ();

    // The fields starts, ends, feature_type, & strand are not merged.
  }  // method merge


/******************************************************************************/

}  // class GenBankFeature
