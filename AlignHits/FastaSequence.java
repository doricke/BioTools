

/******************************************************************************/
/**
  This class models a FASTA sequence.
  
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

public class FastaSequence extends Object
{


/******************************************************************************/

  private   String  name = "";		// name of sequence

  private   String  description = "";	// sequence description

  private   String  sequence = "";	// sequence

  private   String  species = "";	// organism species

  private   byte  sequence_type = 0;	// type of sequence


/******************************************************************************/
  // Constructor FastaSequence
  public FastaSequence ()
  {
    initialize ();
  }  // constructor FastaSequence


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    name = "";
    description = "";
    sequence = "";
    species = "";
    sequence_type = 0;
  }  // method initialize 


/******************************************************************************/
  public String getDef ()
  {
    String def = "";

    int index = description.indexOf ( "/description=" );
    if ( index < 0 )  def = getProduct ();			// not found
    else
      def = description.substring ( index + 14 );

    index = def.indexOf ( '"' );
    if ( index > 0 )  def = def.substring ( 0, index );
    else truncate ( def );

    index = def.indexOf ( "(EC" );				// shorten 
    if ( index > 0 )  def = def.substring ( 0, index );

    index = def.indexOf ( "precursor" );
    if ( index > 0 )  def = def.substring ( 0, index );

    index = def.indexOf ( ';' );
    if ( index > 0 )  def = def.substring ( 0, index );

    index = def.indexOf ( '(' );
    if ( index > 0 )  def = def.substring ( 0, index );

    if ( def.startsWith ( "similar to " ) == true )  def = def.substring ( 11 );
    if ( def.startsWith ( "similar to" ) == true )  def = def.substring ( 10 );
    if ( def.startsWith ( "hypothetical protein " ) == true )  def = def.substring ( 21 );

    index = def.indexOf ( "[" );				// shorten 
    if ( index > 0 )  def = def.substring ( 0, index );

    return def.trim ();
  }  // method getDef


/******************************************************************************/
  public String getDescription ()
  {
    return description;
  }  // method getDescription


/******************************************************************************/
  private String truncate ( String value )
  {
    // Truncate to the value tag.
    int index = value.indexOf ( ';' );
    if ( index > 0 )  value = value.substring ( 0, index );

    index = value.indexOf ( ' ' );
    if ( index > 0 )  value = value.substring ( 0, index );

    index = value.indexOf ( '"' );
    if ( index > 0 )  value = value.substring ( 0, index );

    index = value.indexOf ( '_' );
    if ( index > 0 )  value = value.substring ( 0, index );

    return value;
  }  // method truncate


/******************************************************************************/
  // This method returns the gene name if present in the description line.
  public String getGene ()
  {
    String gene = "";			// gene name

    // Search for the /gene= tag.
    int index = description.indexOf ( "/gene=" );
    if ( index < 0 )  return "";
    gene = description.substring ( index + 7 );

    // Check for a SwissProt Name= in the gene name.
    if ( gene.indexOf ( "Name=" ) == 0 )  gene = gene.substring ( 5 );

    return truncate ( gene );
  }  // method getGene


/******************************************************************************/
  public String getId ()
  {
    String id = "";		// SwissProt ID gene name

    // Search for the /id= tag.
    int index = description.indexOf ( "/id=" );
    if ( index <= 0 )  return "";		// not found

    // Get the start of the /id= value.
    id = description.substring ( index + 5 );

    return truncate ( id );
  }  // method getId


/******************************************************************************/
  public int getLength ()
  {
    return sequence.length ();
  }  // method getLength


/******************************************************************************/
  public String getName ()
  {
    return name;
  }  // method getName


/******************************************************************************/
  public String getProduct ()
  {
    String product = "";

    int index = description.indexOf ( "/product=" );
    if ( index < 0 )  return "";			// Not found

    product = description.substring ( index + 10 );

    index = product.indexOf ( '"' );
    if ( index > 0 )  product = product.substring ( 0, index );
    else truncate ( product );

    index = product.indexOf ( "precursor" );
    if ( index > 0 )  product = product.substring ( 0, index );

    index = product.indexOf ( ';' );
    if ( index > 0 )  product = product.substring ( 0, index );

    return product; 
  }  // method getProduct


/******************************************************************************/
  public String getSequence ()
  {
    return sequence;
  }  // method getSequence


/******************************************************************************/
  public String getShortName ()
  {
    // Truncate the name at the first period.
    int index = name.indexOf ( '.' );
    if ( index > 0 )  return name.substring ( 0, index );

    return name;
  }  // method getShortName


/******************************************************************************/
  public String getShortName ( int length )
  {
    StringBuffer handle = new StringBuffer ();

    // Check for a short name.
    if ( name.length () < length )
    {
      handle.append ( name );

      while ( handle.length () < length )  handle.append ( ' ' );
      return handle.toString ();
    }  // if

    // Truncate the name at the first period.
    int index = name.indexOf ( '.' );
    if ( index > 0 )  handle.append ( name.substring ( 0, index ) );
    else handle.append ( name.substring ( 0, length ) );

    // Blank pad name to length.
    while ( handle.length () < length )  handle.append ( ' ' );

    return handle.toString ();
  }  // method getShortName


/******************************************************************************/
  public String getSpecies ()
  {
    return species;
  }  // method getSpecies


/******************************************************************************/
  public byte getSequenceType ()
  {
    return sequence_type;
  }  // method getSequenceType


/******************************************************************************/
  public String getTaxonKey ()
  {
    String taxon = "";			// Key taxonomy group name

    if ( description.indexOf ( "Hominidae; Homo" ) > 0 )  return "Human";
    if ( description.indexOf ( "Hominidae; Pan" ) > 0 )  return "Chimp";
    if ( description.indexOf ( "Primates" ) > 0 )  return "Primates";
    if ( description.indexOf ( "Mus musculu" ) > 0 )  return "Mouse";
    if ( description.indexOf ( "Rattus" ) > 0 )  return "Rat";
    if ( description.indexOf ( "Sus scrofa" ) > 0 )  return "Pig";
    if ( description.indexOf ( "Oryctolagus cuniculu" ) > 0 )  return "Rabbit";
    if ( description.indexOf ( "Bos taurus" ) > 0 )  return "Bovine";
    if ( description.indexOf ( "Equus caballus" ) > 0 )  return "Horse";
    if ( description.indexOf ( "Ovis aries" ) > 0 )  return "Sheep";
    if ( description.indexOf ( "Canis familiaris" ) > 0 )  return "Dog";
    if ( description.indexOf ( "Cavia porcellus" ) > 0 )  return "Guinea Pig";
    if ( description.indexOf ( "Mammalia" ) > 0 )  return "Mammal";
    if ( description.indexOf ( "Viridiplantae" ) > 0 )  return "Plant";
    if ( description.indexOf ( "Fungi" ) > 0 )  return "Fungi";
    if ( description.indexOf ( "Saccharomyces" ) > 0 )  return "yeast";
    if ( description.indexOf ( "Caenorhabditis" ) > 0 )  return "C. elegans";
    if ( description.indexOf ( "Nematoda" ) > 0 )  return "Nematoda";
    if ( description.indexOf ( "Drosophila" ) > 0 )  return "Fruit fly";
    if ( description.indexOf ( "Arabidopsis thaliana" ) > 0 )  return "Arabidopsis";

    if ( description.indexOf ( "Serpentes" ) > 0 )  return "Snake";
    if ( description.indexOf ( "Chondrichthyes" ) > 0 )  return "Cart. fish";

    if ( description.indexOf ( "Amphibia" ) > 0 )  return "Amphibian";
    if ( description.indexOf ( "Teleostei" ) > 0 )  return "Fish";
    if ( description.indexOf ( "Aves" ) > 0 )  return "Bird";
    if ( description.indexOf ( "Arthropoda" ) > 0 )  return "Insect";
    if ( description.indexOf ( "Vertebrata" ) > 0 )  return "Vertebrate";
    if ( description.indexOf ( "Metazoa" ) > 0 )  return "Metazoan";
    if ( description.indexOf ( "Dictyostelium" ) > 0 )  return "Dictyostelium";
    if ( description.indexOf ( "Bacteria" ) > 0 )  return "Bacteria";
    if ( description.indexOf ( "Archaea" ) > 0 )  return "Archaea";
    if ( description.indexOf ( "Viruses" ) > 0 )  return "Virus";
    if ( description.indexOf ( "Chloroplast" ) > 0 )  return "Chloroplast";
    if ( description.indexOf ( "Eukaryota" ) > 0 )  return "Eukaryota";
    // if ( description.indexOf ( "xx" ) > 0 )  return "xx";
 
    return taxon;
  }  // method getTaxonKey


/******************************************************************************/
  public void setName ( String value )
  {
    if ( value.indexOf ( "gi|" ) == 0 )
    {
      name = "";
      int index = 0;
      do
      {
        index = value.lastIndexOf ( '|' );
        if ( index + 1 < value.length () )
          name = value.substring ( index + 1 );
        else
          value = value.substring ( 0, index );
      }
      while ( name.length () <= 0 );
    }  // if
    else
      name = value;
  }  // method setName


/******************************************************************************/
  public void setDescription ( String value )
  {
    // System.out.println ( "setDescription: " + value );

    description = value;

    parseSpecies ();
  }  // method setDescription


/******************************************************************************/
  public void setSequence ( String value )
  {
    sequence = value;
  }  // method setSequence


/******************************************************************************/
  public void setSpecies ( String value )
  {
    species = value;
  }  // method setSpecies


/******************************************************************************/
  public void setSequenceType ( byte value )
  {
    sequence_type = value;
  }  // method setSequenceType


/******************************************************************************/
  public void parseHeader ( String line )
  {
    // Check for no header line.
    if ( line.length () <= 0 )
    {
      System.out.println ( "FastaSequence.parseHeader: *Warning* No header line." );
      return;
    }  // if

    // Check for invalid header line.
    if ( line.charAt ( 0 ) != '>' )
    {
      System.out.println ( "FastaSequence.parseHeader: *Warning* Invalid header line '" + line + "'" );
      return;
    }  // if

    int index1 = line.indexOf ( ' ' );
    int index2 = line.indexOf ( '\t' );
    if ( ( index2 > 0 ) && ( index2 < index1 ) )  index1 = index2;
    if ( ( index1 < 0 ) && ( index2 > 0 ) )  index1 = index2;

    if ( index1 < 0 )
    {
      setName ( line.substring ( 1 ).trim () );
      return;
    }  // if
 
    setName ( line.substring ( 1, index1 ) );

    if ( index1 + 1 < line.length () ) 
      setDescription ( line.substring ( index1 + 1 ).trim () );
  }  // method parseHeader


/******************************************************************************/
private void parseSpecies ()
{
  if ( description == null )  return;

  int index1 = description.indexOf ( "/organism=" );
  if ( index1 < 0 )  return;

  int index2 = description.indexOf ( '"', index1 + 11 );

  // System.out.println ( index1 + " " + index2 + " " + description.substring ( index1 + 11 ) );

  if ( index2 > index1 )
    setSpecies ( description.substring ( index1 + 11, index2 ) ); 
}  // method parseSpecies


/******************************************************************************/
public String getGeneInfo ()
{
  StringBuffer info = new StringBuffer ();
  info.append ( getShortName () + "\t" );

  String str = getGene ();
  if ( str.length () > 0 )
    info.append ( str + "\t" );
  else
    info.append ( "gene\t" );

  str = getId ();
  if ( str.length () > 0 )
    info.append ( str + "\t" );
  else
    info.append ( "id\t" );

  str = getTaxonKey ();
  if ( str.length () > 0 )
    info.append ( str + "\t" );
  else
    info.append ( "taxon\t" );

  info.append ( getSpecies () + "\t" );

  info.append ( getDef () );

  return info.toString ();
}  // method getGeneInfo


/******************************************************************************/
public String toString ()
{
  return toString ( name, description, sequence );
}  // method toString


/******************************************************************************/
public String toString ( String name, String description, String sequence )
{
  StringBuffer str = new StringBuffer ( 10000 );
  str.append ( ">" + name + " " + description + "\n" );

  for ( int index = 0; index < sequence.length (); index += 50 )
  {
    if ( index + 50 >= sequence.length () )
      str.append ( sequence.substring ( index ) );
    else
      str.append ( sequence.substring ( index, index + 50 ) );

    if ( index + 50 < sequence.length () )  str.append ( "\n" );
  }  // for

  return str.toString ();
}  // method toString


/******************************************************************************/

}  // class FastaSequence
