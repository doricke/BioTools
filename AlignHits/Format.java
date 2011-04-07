
import java.io.*;


/******************************************************************************/
/**
  This class provides methods for creating formated text outputs.
  
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
public class Format extends Object
{

/******************************************************************************/
public Format ( )
{
}  /* constructor Format */


/******************************************************************************/
public static void intWidth ( int i, int width )
{
  int spaces = spacesNeeded ( i, width );

  while ( spaces > 0 )
  {
    System.out.print ( " " );
    spaces--;
  }  // while

  System.out.print ( i );
}  // method intWidth


/******************************************************************************/
public static void intWidthPost ( int i, int width )
{
  int spaces = spacesNeeded ( i, width );

  System.out.print ( i );

  while ( spaces > 0 )
  {
    System.out.print ( " " );
    spaces--;
  }  // while
}  // method intWidthPost


/******************************************************************************/
public static void intWidth ( PrintStream data, int i, int width )
{
  int spaces = spacesNeeded ( i, width );

  while ( spaces > 0 )
  {
    data.print ( " " );
    spaces--;
  }  // while

  data.print ( i );
}  // method intWidth


/******************************************************************************/
public static void longWidth ( PrintStream data, long i, int width )
{
  int spaces = spacesNeeded ( i, width );

  while ( spaces > 0 )
  {
    data.print ( " " );
    spaces--;
  }  // while

  data.print ( i );
}  // method longWidth


/******************************************************************************/
private static int spacesNeeded ( int i, int width )
{
  int wide = 1;

  int temp_i = i;
  while ( temp_i > 9 )
  {
    temp_i /= 10;
    wide++;
  }  // while

  return ( width - wide );
}  // method spacesNeeded


/******************************************************************************/
private static int spacesNeeded ( long i, int width )
{
  int wide = 1;

  long temp_i = i;
  while ( temp_i > 9 )
  {
    temp_i /= 10;
    wide++;
  }  // while

  return ( width - wide );
}  // method spacesNeeded


/******************************************************************************/
public static void precision ( float f, int places )
{
  System.out.print ( ( (int) f ) + "." + decimal ( f, places ) );
}  // method precision


/******************************************************************************/
private static int decimal ( float f, int places )
{
  float dec = f - (float) ( (int) f );

  int temp_places = places;
  while ( temp_places > 0 )
  {
    temp_places--;
    dec *= 10;
  }  // while

  return ( (int) (dec + 0.5) );
}  // method decimal


/******************************************************************************/
public static void precision ( PrintStream data, float f, int places )
{
  data.print ( ( (int) f ) + "." + decimal ( f, places ) );
}  // method precision


/******************************************************************************/
public static void main ( String argv [] )
{
  Format format = new Format ();

  System.out.print ( "int with width 4 '" );
  format.intWidth ( 12, 4 );
  System.out.println ( "'" );

  System.out.print ( "float with 2, 3, & 4 places after the decimal point: '" );
  format.precision ( (float) 3.14159, 2 );
  System.out.print ( "', '" );
  format.precision ( (float) 3.14159, 3 );
  System.out.print ( "', '" );
  format.precision ( (float) 3.14159, 4 );
  System.out.println ( "'" );
}  // method main

}  /* class Format */
