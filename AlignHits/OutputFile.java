
import java.io.*;


/******************************************************************************/
/** 
  This class provides an object model for an output text file.
  
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
public class OutputFile extends Object
{

/******************************************************************************/

/** Current text file name */
private String file_name = "";			

/** Output text file */
private FileOutputStream output_file = null;	

/** Output print stream */
private PrintStream output_data = null;		


/******************************************************************************/
/** This constructor creates and initializes a new OutputFile object. */
public OutputFile ()
{
  initialize ();
}  // constructor OutputFile 


/******************************************************************************/
/** This constructor creates and initializes a new OutputFile object for a named file. */
public OutputFile ( String filename )
{
  initialize ();

  setFileName ( filename );
}  // constructor OutputFile


/******************************************************************************/
/** This method returns the current output text file name. */
public String getFileName ()
{
  return file_name;
}  // method getFileName


/******************************************************************************/
/** This method sets the output text file name. */
public void setFileName ( String filename )
{
  file_name = filename;
}  // method setFileName 


/******************************************************************************/
/** This method initializes this OutputFile object. */
public void initialize ()
{
  file_name = "";
  output_file = null;
  output_data = null;
}  // method initialize 



/******************************************************************************/
/** This method opens the output text file for writing. */
public void openFile ()
{
  try
  {
    output_file = new FileOutputStream ( file_name );

    output_data = new PrintStream ( output_file );
  }  /* try */
  catch ( IOException e )
  {
    System.out.println ( "OutputFile.openFile: IOException on output file: " 
        + file_name + "; " + e );
  }  /* catch */

}  // method openFile 


/******************************************************************************/
/** This method closes the output text file. */
public void closeFile ()
{
  if ( output_data != null )
  {
    output_data.flush ();
    output_data.close ();
  }  // if

  try
  {
    if ( output_file != null )
      output_file.close ();
  }
  catch ( IOException e )
  {
    System.out.println ( "close: " + e );
  }  // catch
}  // method closeFile 


/******************************************************************************/
/** This method deletes the named output text file. */
public void deleteFile ()
{
  closeFile ();

  File delete_file = new File ( file_name );

  delete_file.deleteOnExit ();
  delete_file = null;
  output_file = null;
  output_data = null;
}  // method deleteFile


/******************************************************************************/
/** This method writes an int to the output text file, right justified. */
public void print ( int number, int width )
{
  StringBuffer result = new StringBuffer ();

  result.append ( number );

  while ( result.length () < width )  result.insert ( 0, ' ' );

  output_data.print ( result.toString () );
}  // method print


/******************************************************************************/
/** This method writes a string to the output text file. */
public void print ( String text_string )
{
  output_data.print ( text_string );
}  // method print


/******************************************************************************/
/** This method writes a blank line to the output text file. */
public void println ()
{
  output_data.println ();
}  // method println


/******************************************************************************/
/** This method writes a line terminated with newline to the output text file. */
public void println ( String line )
{
  output_data.println ( line );
}  // method println


/******************************************************************************/
/** This method writes a line terminated with newline to the output text file. */
public void println ( StringBuffer line )
{
  output_data.println ( line.toString () );
}  // method println


/******************************************************************************/
/** An object text method that illustrates the use of the OutputFile object. */
public static void main ( String [] args )
{
  OutputFile app = new OutputFile ();

  app.setFileName ( "test.out" );

  app.openFile ();

  app.println ( "test" );

  app.closeFile ();
}  // method main 

}  // class OutputFile 
