

/******************************************************************************/
/**
  This class models a protein consensus sequence. 
  
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

public class Consensus extends Object
{


/******************************************************************************/

// Maximum number of letters to track.
final private static byte MAX_LETTERS = 27;


/******************************************************************************/

private int count = 0;			// Number of letters added

private int gaps = 0;			// Number of gap letters

private short [] letters = new short [ MAX_LETTERS ];	// Letter counts

private int total = 0;			// Number of characters added


/******************************************************************************/
public Consensus ()
{
  initialize ();
}  // constructor Consensus


/******************************************************************************/
public void initialize ()
{
  count = 0;
  gaps = 0;
  total = 0;
  for ( int i = 0; i < MAX_LETTERS; i++ )
    letters [ i ] = (short) 0;
}  // method initialize


/******************************************************************************/
public void addLetter ( char letter )
{
  // Don't count leading and tailing periods.
  // if ( letter == '.' )  return;

  total++;

  // Check for a letter.
  if ( ( letter >= 'a' ) && ( letter <= 'z' ) )
  {
    letters [ letter - 'a' + 1 ]++;
    count++;
    return;
  }  // if

  // Check for a letter.
  if ( ( letter >= 'A' ) && ( letter <= 'Z' ) )
  {
    letters [ letter - 'A' + 1 ]++;
    count++;
    return;
  }  // if

  // Check for a gap character.
  if ( letter == '-' )  gaps++;
  else
  {
     if ( ( letter != ' ' ) && ( letter != '.' ) )
       System.out.println ( "Consensus.addLetter: *Warning* unknown letter '" + letter + "'" );
     letters [ 0 ]++;
  }  // else
}  // method addLetter


/******************************************************************************/
public char getConsensus ()
{
  char cons = ' ';

  // Check for no letters.
  if ( count <= 0 )  return cons;

  // Check for multiple gaps at this position.
  if ( gaps > total / 2 )  return '-';

  for ( int i = 1; i < MAX_LETTERS; i++ )
  {
    cons = (char) ('a' + i - 1);

    // Check for only letter.
    if ( letters [ i ] == total )  return  ( (char) ('A' + i - 1) );
    if ( letters [ i ] == count )  return cons;

    // Check for a dominant letter.
    if ( letters [ i ] > ( count / 2 ) )  return cons;
  }  // for

  cons = ' ';

  // Check for conservative substitutions.
  if ( ( letters [ 'I' - 'A' + 1 ] 
       + letters [ 'L' - 'A' + 1 ] 
       + letters [ 'V' - 'A' + 1 ] ) == count )  return 'l';

  if ( ( letters [ 'I' - 'A' + 1 ] 
       + letters [ 'L' - 'A' + 1 ] 
       + letters [ 'V' - 'A' + 1 ] 
       + letters [ 'M' - 'A' + 1 ] ) == count )  return 'i';

  if ( ( letters [ 'S' - 'A' + 1 ] 
       + letters [ 'T' - 'A' + 1 ] ) == count )  return 's';

  if ( ( letters [ 'A' - 'A' + 1 ] 
       + letters [ 'T' - 'A' + 1 ] ) == count )  return 'a';

  if ( ( letters [ 'D' - 'A' + 1 ] 
       + letters [ 'N' - 'A' + 1 ] ) == count )  return 'd';

  if ( ( letters [ 'E' - 'A' + 1 ] 
       + letters [ 'N' - 'A' + 1 ] ) == count )  return 'e';

  if ( ( letters [ 'Q' - 'A' + 1 ] 
       + letters [ 'E' - 'A' + 1 ] ) == count )  return 'q';

  if ( ( letters [ 'R' - 'A' + 1 ] 
       + letters [ 'K' - 'A' + 1 ] ) == count )  return 'r';

  if ( ( letters [ 'G' - 'A' + 1 ] 
       + letters [ 'A' - 'A' + 1 ] ) == count )  return 'g';

  if ( ( letters [ 'F' - 'A' + 1 ] 
       + letters [ 'Y' - 'A' + 1 ] ) == count )  return 'f';

  return cons;
}  // getConsensus


/******************************************************************************/
public int getCount ()
{
  return count;
}  // method getCount


/******************************************************************************/
public int getCount ( char amino )
{
  if ( ( amino >= 'a' ) && ( amino <= 'z' ) )
    return letters [ amino - 'a' + 1 ];

  if ( ( amino >= 'A' ) && ( amino <= 'Z' ) )
    return letters [ amino - 'A' + 1 ];

  return 0;
}  // method getCount


/******************************************************************************/
public char getFrequent ()
{
  int best = 0;
  char freq = 'A';
  char letter = ' ';

  // Check for no letters.
  if ( count <= 0 )  return freq;

  for ( int i = 1; i < MAX_LETTERS; i++ )
  {
    letter = (char) ('A' + i - 1);

    // Check for only letter.
    if ( letters [ i ] > best )
    {
      freq = letter;
      best = letters [ i ];
    }  // if
  }  // for

  return freq;
}  // method getFrequent


/******************************************************************************/
public int getGaps ()
{
  return gaps;
}  // method getGaps


/******************************************************************************/
public int getTotal ()
{
  return total;
}  // method getTotal


/******************************************************************************/
public void snap ()
{
  char letter = ' ';

  if ( gaps > 0 )  System.out.println ( "- = " + gaps );
  for ( int i = 1; i < MAX_LETTERS; i++ )
    if ( letters [ i ] > 0 )
    {
      letter = (char) ('A' + i - 1);
      System.out.println ( letter + " " + letters [ i ] );
    }  // if
}  // method snap


/******************************************************************************/
  public static void main ( String [] args )
  {
    Consensus app = new Consensus ();

    // app.();
  }  // method main

}  // class Consensus
