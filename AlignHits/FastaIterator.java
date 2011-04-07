
// import FastaSequence;


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

public class FastaIterator extends InputFile
{


/******************************************************************************/

  FastaSequence fasta_sequence = null;		// Current FASTA sequence


/******************************************************************************/
public FastaIterator ()
{
  initialize ();
}  // constructor FastaIterator


/******************************************************************************/
public FastaIterator ( String file_name )
{
  initialize ();

  setFileName ( file_name );
  openFile ();
}  // method FastaIterator


/*******************************************************************************/
  public FastaSequence getFastaSequence ()
  {
    return fasta_sequence;
  }  // method getFastaSequence


/*******************************************************************************/
  private String readSequence ()
  {
    StringBuffer sequence = new StringBuffer ( 10000 );

    // Advance past the header line.
    nextLine ();		// get the next line

    while ( ( line.length () > 0 ) && ( line.charAt ( 0 ) != '>' ) )
    {
      sequence.append ( line.toString ().trim () );

      nextLine ();		// get the next line
    }  // while

    return sequence.toString ();
  }  // method readSequence


/*******************************************************************************/
  public FastaSequence next ()
  {
    fasta_sequence = new FastaSequence ();		// create a new object

    // Check for first or blank line.
    if ( line.length () <= 0 )  nextLine ();

    // Parse the header line.
    fasta_sequence.parseHeader ( line.toString () );

    // Read in the sequence.
    fasta_sequence.setSequence ( readSequence () );

    return fasta_sequence;
  }  // method next


/******************************************************************************/

}  // class FastaIterator

