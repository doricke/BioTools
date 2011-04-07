
import java.text.NumberFormat;


/******************************************************************************/
/**
  This class provides methods for working with text strings.
  
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

public class LineTools extends Object
{

/******************************************************************************/

protected String line = "";			// text string


/******************************************************************************/
public LineTools ()
{
  initialize ();
}  // constructor LineTools


/******************************************************************************/
public void initialize ()
{
  line = "";
}  // method initialize


/******************************************************************************/
// This method counts the occurances of target in line.

  public static int countChar ( char target, String line )
  {
    int sum = 0;

    // Look for the first target character in line.
    int index = line.indexOf ( target );

    // Loop, counting all occurances.
    while ( index >= 0 )
    {
      sum++;

      // Check if reached the last character in line.
      if ( index == line.length () - 1 )  return sum;

      // Search for an additional target character in line.
      index = line.indexOf ( target, index+1 );
    }  // while

    return sum;		// return the count
  }  // method countChar


/******************************************************************************/
public static int getInteger ( String line )
{
  int i = 0;
  int index = 0;
  int sign = 1;					// Default sign = +

  // Skip leading white space.
  while ( ( ( line.charAt ( index ) == ' ' ) ||
            ( line.charAt ( index ) == '\t' ) ) &&
          ( index < line.length () - 1 ) )

    index++;

  if ( index >= line.length () )  return 0;

  // Check for a sign.
  if ( line.charAt ( index ) == '+' )
    index++;
  else
    if ( line.charAt ( index ) == '-' )
    {
      sign = -1;
      index++;
    }  /* if */

  // Traverse the integer.
  while ( index < line.length () )
  {
    if ( ( line.charAt ( index ) >= '0' ) && ( line.charAt ( index ) <= '9' ) )

      i = i * 10 + (int) line.charAt ( index ) - (int) '0';

    else  index = line.length ();		// Terminate loop

    index++;
  }  /* while */

  // Set the sign.
  i *= sign;

  return ( i );					// Return the integer
}  /* method getInteger */


/******************************************************************************/
  public static int getInteger ( String line, int start, int end )
  {
    // Check start coordinate.
    if ( start >= line.length () )

      return 0;

    // Check end coordinate.
    if ( end > line.length () )
    {
      if ( start < line.length () )  return getInteger ( line.substring ( start ) );
      return 0;
    }  // if

    // Check if end at end of line.
    if ( end == line.length () )

      return getInteger ( line.substring ( start ) );

    else

      return getInteger ( line.substring ( start, end ) );
  }  // method getInteger


/******************************************************************************/
  public static double getDouble ( String line )
  {
    double num = 0.0d;

    if ( line.length () <= 0 )  return num;

    num = (double) getInteger ( line );

    // Check for a fraction.
    int index = line.indexOf ( '.' );
    if ( index < 0 )  return num;
    if ( index + 1 >= line.length () ) return num;

    // Get the fractional part of the number.
    double fraction = (double) getInteger ( line.substring ( index + 1 ) );
    while ( fraction >= 1.0d )  fraction /= 10.0d;

    num += fraction;
    return num;
  }  // method getDouble


/******************************************************************************/
  public static float getFloat ( String line )
  {
    float num = 0.0f;

    if ( line.length () <= 0 )  return num;

    num = (float) getInteger ( line );

    // Check for a fraction.
    int index = line.indexOf ( '.' );
    if ( index < 0 )  return num;
    if ( index + 1 >= line.length () ) return num;

    // Get the fractional part of the number.
    int digits = 0;
    float fraction = 0.0f; 
    int next = 1;
    while ( ( index + next < line.length () ) &&
            ( line.charAt ( index + next ) >= '0' ) &&
            ( line.charAt ( index + next ) <= '9' ) )
    {
      digits++;
      fraction = fraction * 10.0f + (float) (line.charAt ( index + next ) - '0');
      next++;
    }  // while

    for ( int i = 1; i <= digits; i++ )
      fraction /= 10.0f;

    num += fraction;

    // Check for exponent notation.
    int exponent = 0;
    if ( ( index + next < line.length () ) &&
         ( line.charAt ( index + next ) == 'e' ) )

      exponent = getInteger ( line.substring ( index + next + 1 ) );

    if ( exponent < 0 )
      for ( int i = -1; i >= exponent; i-- )
        num /= 10.0f;

    if ( exponent > 0 )
      for ( int i = 1; i <= exponent; i++ )
        num *= 10.0f;

    return num;
  }  // method getFloat


/******************************************************************************/
  // This method returns the current text line.
  public String getLine ()
  {
    return line;
  }  // method getLine


/******************************************************************************/
  // This method sets the current text line.
  public void setLine ( String value )
  {
    line = value;
  }  // method setLine


/******************************************************************************/
  // This method checks if a character is a letter.
  public static boolean isLetter ( char letter )
  {
    if ( ( letter >= 'a' ) && ( letter <= 'z' ) )  return true;
    if ( ( letter >= 'A' ) && ( letter <= 'Z' ) )  return true;
    return false;
  }  // method isLetter


/******************************************************************************/
  // This method gets a substring from the current line.
  public static String substring ( String line, int start, int end )
  {
    // Check start coordinate.
    if ( start >= line.length () )

      return "";

    // Check end coordinate.
    if ( end > line.length () )
    {
      if ( start < line.length () )  return line.substring ( start );
      return "";
    }  // if

    // Check if end at end of line.
    if ( end == line.length () )

      return line.substring ( start );

    else

      return line.substring ( start, end );
  }  // method substring


/******************************************************************************/
  public boolean blankLine ()
  {
    // Check for a blank line.
    if ( line.trim ().length () <= 0 )  return true;

    return false;
  }  // method blankLine


/*******************************************************************************/
  public static int compareTo ( String a, String b )
  {
    int i = 0;
    int a_length = a.length ();
    int b_length = b.length ();

    // Compare the strings one character at a time.
    while ( ( i < a_length ) &&
            ( i < b_length ) )
    {
      if ( a.charAt ( i ) < b.charAt ( i ) )  return -1;	// a < b
      if ( a.charAt ( i ) > b.charAt ( i ) )  return  1;	// b > a
      i++;
    }  // while

    // Check if the strings are identical.
    if ( ( i >= a_length ) && ( i >= b_length ) )  return 0;	// a == b

    if ( i >= a_length )  return -1;		// a < b
    return 1;		// b > a
  }  // method compareTo


/*******************************************************************************/
  public static String leftPad ( String value, int length )
  {
    if ( value.length () > length )  return value.substring ( 0, length );
    if ( value.length () == length )  return value;

    StringBuffer pad = new StringBuffer ( value );
    while ( pad.length () < length )  pad.insert ( 0, ' ' );

    return pad.toString ();
  }  // method leftPad


/*******************************************************************************/
  public static String pad ( String value, int length )
  {
    if ( value.length () > length )  return value.substring ( 0, length );
    if ( value.length () == length )  return value;

    StringBuffer pad = new StringBuffer ( value );
    while ( pad.length () < length )  pad.append ( ' ' );

    return pad.toString ();
  }  // method pad


/******************************************************************************/
  public boolean startsWith ( String line_start )
  {
    if ( line.startsWith ( line_start ) == true )
      return true;

    return false;
  }  // method startsWith


/******************************************************************************/
  public boolean validateStart ( String line_start )
  {
    if ( line.startsWith ( line_start ) == true )  return true;

    // The expected start of the line was not found.
    System.out.print ( "Expected " + line_start + " at the beginning of " );
    System.out.println ( "line: '" + line + "'" );

    return false;
  }  // method validateStart


/******************************************************************************/
  public static String formatDouble ( double value, int digits )
  {
    NumberFormat nf = NumberFormat.getInstance ();
    nf.setMaximumFractionDigits ( digits );
    return nf.format ( value );
  }  // method formatDouble


/******************************************************************************/
  public static void main ( String [] args )
  {
    LineTools app = new LineTools ();
  }  // method main

}  // class LineTools

