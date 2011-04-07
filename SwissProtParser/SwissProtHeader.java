
// import InputTools;

import java.util.*;


/******************************************************************************/
/**
  This class models a SwissProt entry header.
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

public class SwissProtHeader extends Object
{


/******************************************************************************/

  private StringBuffer header = new StringBuffer ( 4096 );	// SwissProt file header

  private StringBuffer references = new StringBuffer ( 4096 );	// Reference section

  // SwissProt Header fields.

  private String accession = "";		// Accession number

  private int length = 0;				// Sequence length

  private String id_name = "";			// ID line name


  private   String ac_line;				// AC line - Accession number(s)

  private   String cc_line;				// CC line - Comments or notes

  private   String chromosome;			// CC line - chromosome

  private   String coordinates;			// CC line - chromosome gene coordinates

  private   String de_line;				// DE line - Description

  private   String dr_line = ""; 		// DR line - Database cross-references

  private   String dt_line;				// DT line - Date

  private   int gene_id;				// DR line - Entrez Gene ID

  private   String gene_name;			// DR line - Entrez Gene name

  private   String gn_line;				// GN line - Gene name(s)

  private   String id_line;				// ID line - Identification

  private   String kw_line;				// KW line - Keywords

  private   int ncbi_taxid;				// OX line - NCBI Organism taxonomy identifier

  private   String oc_line;				// OC line - Organism classification

  private   String og_line;				// OG line - Organelle

  private   String oh_lines;			// OH line - 

  private   String os_line;				// OS line - Organism species

  private   String ox_line;				// OX line - NCBI Organism taxonomy ID

  private   String pe_line;				// PE line - 

  private   String ra_line;				// RA line - Reference authors

  private   String rc_line;				// RC line - Reference comment(s)

  private   String refseq_id;			// NCBI REFSEQ identifier

  private   String rl_line;				// RL line - Reference location

  private   String rn_line;				// RN line - Reference number

  private   String rp_line;				// RP line - Reference position

  private   String rt_line;				// RT line - Reference title

  private   String rx_line;				// RX line - Reference cross-reference(s)

  private   String swiss_id;			// Swiss-Prot identifier


/******************************************************************************/
  // Constructor SwissProtHeader
  public SwissProtHeader ()
  {
    initialize ();
  }  // constructor SwissProtHeader


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    accession = "";
    length = 0;
    header.setLength ( 0 );
    id_name = "";
    references.setLength ( 0 );

    ac_line = "";
    cc_line = "";
    de_line = "";
    dr_line = "";
    dt_line = "";
    gn_line = "";
    id_line = "";
    kw_line = "";
    oc_line = "";
    og_line = "";
    oh_lines = "";
    os_line = "";
    ox_line = "";
    pe_line = "";
    ra_line = "";
    rc_line = "";
    rl_line = "";
    rn_line = "";
    rp_line = "";
    rt_line = "";
    rx_line = "";

    chromosome = "";
    coordinates = "";
    gene_id = 0;
    gene_name = "";
    ncbi_taxid = 0;
    refseq_id = "";
    swiss_id = "";
  }  // method initialize 


/******************************************************************************/
  public void addCcLine ( String value )
  {
    cc_line += value;

    int index = cc_line.indexOf ( "GENE_LOCATION" );
    if ( index <= 0 )  return;

    String temp = cc_line.substring ( index );

    StringTokenizer tokens = new StringTokenizer ( value.substring ( index ), ":" );

    tokens.nextToken ();
    temp = tokens.nextToken ();
    index = temp.lastIndexOf ( ' ' );

    if ( index <= 0 )
      chromosome = temp;
    else
      chromosome = temp.substring ( index + 1 );
    coordinates = tokens.nextToken ();
  }  // method addCcLine


/******************************************************************************/
  private String addLine ( String current, String value )
  {
    if ( current.length () <= 0 )  return value.trim ();

    if ( value.length () > 5 )
      return current + " " + value.substring ( 5 ).trim ();

    return current;
  }  // method addLine


/******************************************************************************/
  public String getAcLine ()
  {
    return ac_line;
  }  // method getAcLine;


/******************************************************************************/
  public String getCcLine ()
  {
    return cc_line;
  }  // method getCcLine;


/******************************************************************************/
  public String getChromosome ()
  {
    return chromosome;
  }  // method getChromosome


/******************************************************************************/
  public String getCoordinates ()
  {
    return coordinates;
  }  // method getCoordinates


/******************************************************************************/
  public String getDeLine ()
  {
    return de_line;
  }  // method getDeLine;


/******************************************************************************/
  public String getDrLine ()
  {
    return dr_line;
  }  // method getDrLine


/******************************************************************************/
  public String getDtLine ()
  {
    return dt_line;
  }  // method getDtLine


/******************************************************************************/
  public int getGeneId ()
  {
    return gene_id;
  }  // method getGeneId


/******************************************************************************/
  public String getGeneName ()
  {
    if ( gene_name.length () > 0 )  return gene_name;

    String value = getValue ( gn_line );

    int index = value.indexOf ( ';' );
    if ( index > 0 )
      value = value.substring ( 0, index );

    index = value.indexOf ( ' ' );
    if ( index > 0 )
      value = value.substring ( 0, index );

    index = value.indexOf ( '=' );
    if ( index > 0 )  value = value.substring ( index + 1 );

    return value;
  }  // method getGeneName


/******************************************************************************/
  public String getGnLine ()
  {
    return gn_line;
  }  // method getGnLine


/******************************************************************************/
  public String getKingdom ()
  {
    int index;
    String kingdom = oc_line.substring ( 5 );

    index = kingdom.indexOf ( ';' );
    if ( index > 0 )  kingdom = kingdom.substring ( 0, index );

    index = kingdom.indexOf ( '/' );
    if ( index > 0 )  kingdom = kingdom.substring ( 0, index );

    index = kingdom.indexOf ( ' ' );
    if ( index > 0 )  kingdom = kingdom.substring ( 0, index );

    index = kingdom.indexOf ( '.' );
    if ( index > 0 )  kingdom = kingdom.substring ( 0, index );

    return kingdom;
  }  // method getKingdom


/******************************************************************************/
  public String getId ()
  {
    String id = getValue ( id_line );

    int index = id.indexOf ( " " );
    if ( index < 0 )  return id;
    return id.substring ( 0, index );
  }  // method getId


/******************************************************************************/
  public String getIdLine ()
  {
    return id_line;
  }  // method getIdLine


/******************************************************************************/
  public String getKeywords ()
  {
    return getValue ( kw_line );
  }  // method getKeywords


/******************************************************************************/
  public String getKwLine ()
  {
    return kw_line;
  }  // method getKwLine


/******************************************************************************/
  public int getNcbiTaxid ()
  {
    return ncbi_taxid;
  }  // method getNcbiTaxid


/******************************************************************************/
  public String getOcLine ()
  {
    return oc_line;
  }  // method getOcLine;


/******************************************************************************/
  public String getOgLine ()
  {
    return og_line;
  }  // method getOgLine;


/******************************************************************************/
  public String getOhLines ()
  {
    return oh_lines;
  }  // method getOhLines;


/******************************************************************************/
  public String getOsLine ()
  {
    return os_line;
  }  // method getOsLine;


/******************************************************************************/
  public String getOxLine ()
  {
    return ox_line;
  }  // method getOxLine;


/******************************************************************************/
  public String getPeLine ()
  {
    return pe_line;
  }  // method getPeLine;


/******************************************************************************/
  public String getRaLine ()
  {
    return ra_line;
  }  // method getRaLine;


/******************************************************************************/
  public String getRcLine ()
  {
    return rc_line;
  }  // method getRcLine;


/******************************************************************************/
  public String getRefseqId ()
  {
    return refseq_id;
  }  // method getRefseqId


/******************************************************************************/
  public String getRlLine ()
  {
    return rl_line;
  }  // method getRlLine;


/******************************************************************************/
  public String getRnLine ()
  {
    return rn_line;
  }  // method getRnLine;


/******************************************************************************/
  public String getRpLine ()
  {
    return rp_line;
  }  // method getRpLine;


/******************************************************************************/
  public String getRtLine ()
  {
    return rt_line;
  }  // method getRtLine;


/******************************************************************************/
  public String getRxLine ()
  {
    return rx_line;
  }  // method getRxLine;


/******************************************************************************/
  public String getSwissId ()
  {
    return swiss_id;
  }  // method getSwissId


/******************************************************************************/
  public String getTaxonomy ()
  {
    return getValue ( oc_line );
  }  // method getTaxonomy


/******************************************************************************/
  public void setAcLine ( String value )
  {
    ac_line = value;
  }  // method setAcLine;


/******************************************************************************/
  public void setCcLine ( String value )
  {
    cc_line = value;
  }  // method setCcLine;


/******************************************************************************/
  public void setDeLine ( String value )
  {
    de_line = value;
  }  // method setDeLine;


/******************************************************************************/
  public void setDrLine ( String value )
  {
    StringTokenizer tokens = null;
    dr_line += value;

    int index = value.indexOf ( "UniProtKB/" );
    if ( ( index > 0 ) && ( swiss_id.length () == 0 ) )
    {
      tokens = new StringTokenizer ( value.substring ( index ), ";" );
      tokens.nextToken ();
      tokens.nextToken ();
      swiss_id = tokens.nextToken ().substring ( 1 );
    }  // if

    index = value.indexOf ( "REFSEQ_" );
    if ( index <= 0 )  index = value.indexOf ( "RefSeq; " );
    if ( index > 0 ) 
    {
      tokens = new StringTokenizer ( value.substring ( index ), ";" );
      tokens.nextToken ();
      refseq_id = tokens.nextToken ().substring ( 1 );
      index = refseq_id.indexOf ( '.' );
      if ( index > 0 )  refseq_id = refseq_id.substring ( 0, index );
    }  // if

    index = value.indexOf ( "Entrez Gene" );
    if ( index <= 0 ) return;

    tokens = new StringTokenizer ( value.substring ( index ), ";" );

    tokens.nextToken ();
    gene_id = InputTools.getInteger ( tokens.nextToken () );
    gene_name = tokens.nextToken ().substring ( 1 );
  }  // method setDrLine;


/******************************************************************************/
  public void setDtLine ( String value )
  {
    dt_line = value;
  }  // method setDtLine;


/******************************************************************************/
  public void setGnLine ( String value )
  {
    gn_line = value;
  }  // method setGnLine;


/******************************************************************************/
  public void setIdLine ( String value )
  {
    id_line = value;
  }  // method setIdLine;


/******************************************************************************/
  public void setKwLine ( String value )
  {
    kw_line = value;
  }  // method setKwLine;


/******************************************************************************/
  public void setOcLine ( String value )
  {
    oc_line = value;
  }  // method setOcLine;


/******************************************************************************/
  public void setOgLine ( String value )
  {
    og_line = value;
  }  // method setOgLine;


/******************************************************************************/
  public void setOhLine ( String value )
  {
    oh_lines += value;
  }  // method setOhLine;


/******************************************************************************/
  public void setOsLine ( String value )
  {
    os_line = value;
  }  // method setOsLine;


/******************************************************************************/
  public void setOxLine ( String value )
  {
    ox_line = value;

    int index = value.indexOf ( '=' );
    if ( index <= 0 )  return;
    ncbi_taxid = InputTools.getInteger ( value.substring ( index + 1 ) );
  }  // method setOxLine;


/******************************************************************************/
  public void setPeLine ( String value )
  {
    pe_line = value;
  }  // method setPeLine;


/******************************************************************************/
  public void setRaLine ( String value )
  {
    ra_line = value;
  }  // method setRaLine;


/******************************************************************************/
  public void setRcLine ( String value )
  {
    rc_line = value;
  }  // method setRcLine;


/******************************************************************************/
  public void setRlLine ( String value )
  {
    rl_line = value;
  }  // method setRlLine;


/******************************************************************************/
  public void setRnLine ( String value )
  {
    rn_line = value;
  }  // method setRnLine;


/******************************************************************************/
  public void setRpLine ( String value )
  {
    rp_line = value;
  }  // method setRpLine;


/******************************************************************************/
  public void setRtLine ( String value )
  {
    rt_line = value;
  }  // method setRtLine;


/******************************************************************************/
  public void setRxLine ( String value )
  {
    rx_line = value;
  }  // method setRxLine;


/******************************************************************************/
  public String getAccession ()
  {
    return accession;
  }  // method getAccession


/******************************************************************************/
  public String getComment ()
  {
    if ( cc_line.length () <= 5 )  return "";

    return cc_line.substring ( 5 );
  }  // method getComment


/******************************************************************************/
  public String getDescription ()
  {
    return getValue ( de_line );
  }  // method getDescription


/******************************************************************************/
  public String getEcNumber ()
  {
    int index = de_line.indexOf ( "(EC " );
    if ( index == -1 )  return "";

    int end = de_line.indexOf ( ")", index );

    if ( end == -1 )
      return de_line.substring ( index + 4 );
    else
      return de_line.substring ( index + 4, end );
  }  // method getEcNumber


/******************************************************************************/
  public String getIdName ()
  {
    return id_name;
  }  // method getIdName


/******************************************************************************/
  public StringBuffer getHeader ()
  {
    return header;
  }  // method getHeader


/******************************************************************************/
  public int getLength ()
  {
    return length;
  }  // method getLength


/******************************************************************************/
  public String getOrganism ()
  {
    String value = getValue ( os_line );

    // Clip off the common name.
    int clip = value.indexOf ( " (" );

    if ( clip > 0 )
      return value.substring ( 0, clip );

    return value;
  }  // method getOrganism


/******************************************************************************/
  public StringBuffer getReferences ()
  {
    return references;
  }  // method getReferences


/******************************************************************************/
  private String getValue ( String value )
  {
    if ( value.length () <= 5 )  return "";

    int index = value.length ();
    if ( value.charAt ( index - 1 ) == '.' )
      return value.substring ( 5, index - 1 );
    else
      return value.substring ( 5 );
  }  // method getValue


/******************************************************************************/
  public void setSection ( String section_type, String section )
  {
    header.append ( section_type );
    header.append ( "   " );
    header.append ( section );
    header.append ( "\n" );

    if ( section_type.equals ( "AC" ) == true )
      crackAcLine ( section );

    else if ( section_type.equals ( "CC" ) == true )
      addCcLine ( section );

    else if ( section_type.equals ( "DE" ) == true )
      de_line = addLine ( de_line, section );

    else if ( section_type.equals ( "DR" ) == true )
      setDrLine ( section );

    else if ( section_type.equals ( "DT" ) == true )
      dt_line = section;

    else if ( section_type.equals ( "GN" ) == true )
      gn_line = addLine ( gn_line, section );

    else if ( section_type.equals ( "ID" ) == true )
      crackIdLine ( section );

    else if ( section_type.equals ( "KW" ) == true )
      kw_line = addLine ( kw_line, section );

    else if ( section_type.equals ( "OC" ) == true )
      oc_line = addLine ( oc_line, section );

    else if ( section_type.equals ( "OG" ) == true )
      og_line = section;

    else if ( section_type.equals ( "OH" ) == true )
      oh_lines += section;

    else if ( section_type.equals ( "OS" ) == true ) 
      os_line = addLine ( os_line, section );

    else if ( section_type.equals ( "OX" ) == true )
      setOxLine ( section );

    else if ( section_type.equals ( "PE" ) == true )
      pe_line = section;

    else if ( ( section_type.equals ( "RA" ) == true ) ||
              ( section_type.equals ( "RC" ) == true ) ||
              ( section_type.equals ( "RG" ) == true ) ||
              ( section_type.equals ( "RL" ) == true ) ||
              ( section_type.equals ( "RN" ) == true ) ||
              ( section_type.equals ( "RP" ) == true ) ||
              ( section_type.equals ( "RT" ) == true ) ||
              ( section_type.equals ( "RX" ) == true ) )
         {
           // Note that this should be replaced with a Vector of references.
           references.append ( section_type );
           references.append ( "   " );
           references.append ( section );
           references.append ( "\n" );
         }  // if
         else
           System.out.println ( "SwissProtHeader.setSection Unknown type '" 
               + section_type
               + "   " 
               + section
               );

  }  // method setSection


/******************************************************************************/
  private void crackAcLine ( String acc )
  {
    if ( acc.length () <= 5 )  return;

    int index = acc.indexOf ( ";" );

    if ( index < 0 )
       accession = acc.substring ( 5 );
    else
      if ( index > 5 )
        accession = acc.substring ( 5, index );
  }  // method crackAcLine


/******************************************************************************/
  private void crackIdLine ( String line )
  {
    setIdLine ( line );

    int end = line.lastIndexOf ( "AA" );
    if ( end < 0 )  return;

    int start = line.lastIndexOf ( ";", end );
    if ( start < 0 )  return;

    length = InputTools.getInteger ( line.substring ( start+1, end-1 ) );

    end = line.indexOf ( " ", 5 );
    id_name = line.substring ( 5, end );
  }  // method crackIdLine


/******************************************************************************/

}  // class SwissProtHeader
