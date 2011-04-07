
import java.io.*;


/******************************************************************************/
/**
  This class models an input text file.
  
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

public class InputFile extends Object
{

/******************************************************************************/

/** End of file status flag */
protected boolean end_of_file = false;			

/** Input text file name */
protected String file_name = "";				

/** Input text file */
protected BufferedReader input_file;			

/** Current text line of the input text file */
protected StringBuffer line = new StringBuffer ( 540 );	


/******************************************************************************/
/** This constructor creates and initializes a new InputFile object. */
public InputFile ()
{
  initialize ();
}  // constructor InputFile


/******************************************************************************/
/**
  This method initializes the InputFile object.
*/
public void initialize ()
{
  end_of_file = false;
  file_name = "";
  input_file = null;
  line.setLength ( 0 );
}  // method initialize


/******************************************************************************/
/** This method closes the input text file. */
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
    System.out.println ( "InputFile.close: IOException while closing input file: " 
        + file_name + "; " + e  );
    return;
  }  // catch
}  // method closeFile


/******************************************************************************/
/**
  This method returns the input text file name.

  @return String containing the current text file name.
*/
  public String getFileName ()
  {
    return file_name;
  }  // method getFileName


/******************************************************************************/
/**
  This method returns the current text line.

  @return StringBuffer containing the current text line.
*/
  public StringBuffer getLine ()
  {
    return line;
  }  // method getLine


/******************************************************************************/
/**
  This method returns the end of file status indicator.

  @return Boolean value indicating if the end of file has been reached
*/
  public boolean isEndOfFile ()
  {
    return end_of_file;
  }  // method isEndOfFile


/******************************************************************************/
/** This method opens the input text file for reading. */
public void openFile ()
{
  closeFile ();

  try
  {
    input_file = new BufferedReader ( new InputStreamReader 
        ( new FileInputStream ( file_name ) ) );
    end_of_file = false;
    line.setLength ( 0 );
  }  // try 
  catch ( IOException e )
  {
    System.out.println ( "InputFile.openFile: IOException on input file " +
        file_name + "; " + e );
    end_of_file = true;
  }  /* catch */
}  // method openFile


/******************************************************************************/
/**
  This method returns the next text line from the input file or an empty line.

  @return StringBuffer containing the next text line from the input file.
*/
  public StringBuffer nextLine ()
  {
    // Initialize the next line.
    line.setLength ( 0 );

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

      System.out.println ( "InputFile.nextLine exception: " + e );
    }  // catch

    return line;
  }  // method nextLine


/******************************************************************************/
/**
  This method sets the input text file name.

  @param file_name The input text file name
*/
public void setFileName ( String value )
{
  file_name = value;
}  /* method setFileName */


/******************************************************************************/
/**
  This method sets the end of file flag to true.
*/
public void setEndOfFile ()
{
  end_of_file = true;
}  /* method setEndOfFile */


/******************************************************************************/
/** An object test method that illustrates the use of the InputFile object. */
  public static void main ( String [] args )
  {
    InputFile app = new InputFile ();

    app.setFileName ( "test.file" );

    // Open the file.
    app.openFile ();

    // Copy the file to output.
    while ( app.isEndOfFile () == false )

      System.out.println ( app.nextLine ().toString () );

    app.closeFile ();
  }  // method main

}  // class InputFile

