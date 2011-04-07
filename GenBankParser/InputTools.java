
import java.io.*;


/******************************************************************************/
/**
  This class models a text input file.
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

public class InputTools extends Object
{

/******************************************************************************/

private boolean end_of_file = false;			// End of file flag

private String fileName = "";				// Input file name

private BufferedReader input_file;			// input file

private boolean is_valid_file = true;			// valid input file

private StringBuffer line = new StringBuffer ( 540 );	// current line of the file

private long line_number = 0;				// current line number


/******************************************************************************/
public InputTools ()
{
  initialize ();
}  // constructor InputTools


/******************************************************************************/
public void initialize ()
{
  end_of_file = false;
  fileName = "";
  input_file = null;
  is_valid_file = true;
  line.setLength ( 0 );
  line_number = 0;
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
  while ( ( index < line.length () ) &&
          ( ( line.charAt ( index ) == ' ' ) ||
            ( line.charAt ( index ) == '\t' ) ) )  index++;

  // Check if off the end of the line.
  if ( index >= line.length () )  return ( i );

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
  // This method returns the next line from the input file or an empty line.
  public StringBuffer getLine ()
  {
    // Initialize the next line.
    line.setLength ( 0 );
    line_number++;

    // Check for end of file - if end then return an empty line.
    if ( end_of_file == true )  return line;

    // Try to read in the next line from the file.
    try 
    {
      String file_line = input_file.readLine ();

      // Check for end of file.
      if ( file_line == null ) 
        end_of_file = true;
      else
        line.append ( file_line );

      file_line = null;
    }
    catch ( IOException e )
    {
      end_of_file = true;

      System.out.println ( "InputTools.getLine exception: " + e );
    }  // catch

    return line;
  }  // method getLine


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
  public boolean isEndOfFile ()
  {
    return end_of_file;
  }  // method isEndOfFile


/******************************************************************************/
  public boolean isValidFile ()
  {
    return is_valid_file;
  }  // method isValidFile


/******************************************************************************/
  public void setEndOfFile ()
  {
    end_of_file = true;
  }  // method setEndOfFile


/******************************************************************************/
public void setFileName ( String file_name )
{
  fileName = file_name;
}  /* method setFileName */


/******************************************************************************/
  public void setInvalidFile ()
  {
    is_valid_file = false;
  }  // method setInvalidFile


/******************************************************************************/
public void openFile ()
{
  closeFile ();

  try
  {
    input_file = new BufferedReader ( new InputStreamReader 
        ( new FileInputStream ( fileName ) ) );
    end_of_file = false;
    is_valid_file = true;
    line.setLength ( 0 );
    line_number = 0;
  }  // try 
  catch ( IOException e )
  {
    System.out.println ( "InputTools.openFile: IOException on input file " +
        fileName + "; " + e );
    end_of_file = true;
    is_valid_file = false;
  }  /* catch */
}  // method openFile


/******************************************************************************/
public void closeFile ()
{
  if ( input_file == null )  return;

  /* Close the input file */
  try 
  {
    input_file.close ();
  }
  catch ( IOException e  )
  {
    System.out.println ( "InputTools.close: IOException while closing input file: " 
        + fileName + "; " + e  );
    return;
  }  // catch
}  // method closeFile


/******************************************************************************/
  public boolean blankLine ()
  {
    // Check for a blank line.
    if ( line.toString ().trim ().length () <= 0 )  return is_valid_file;

    // The expected start of the line was not found.
    is_valid_file = false;
    System.out.print ( "Expected a blank line in " );
    System.out.print ( fileName + ", line " + line_number + " saw '" );
    System.out.println ( line.toString () + "'" );

    return is_valid_file;
  }  // method blankLine


/******************************************************************************/
  public boolean validateStart ( String line_start )
  {
    if ( line.toString ().startsWith ( line_start ) == true )
      return is_valid_file;

    // The expected start of the line was not found.
    is_valid_file = false;
    System.out.print ( "Expected " + line_start + " at the beginning of " );
    System.out.print ( fileName + ", line " + line_number + " saw '" );
    System.out.println ( line.toString () + "'" );

    return is_valid_file;
  }  // method validateStart


/******************************************************************************/
  public static void main ( String [] args )
  {
    InputTools app = new InputTools ();

    app.setFileName ( "test.file" );

    // Open the file.
    app.openFile ();

    // Copy the file to output.
    while ( app.isEndOfFile () == false )

      System.out.println ( app.getLine ().toString () );

    app.closeFile ();
  }  // method main

}  // class InputTools

