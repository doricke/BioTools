

import java.io.*;


/******************************************************************************/
/**
  This class models a text output file.
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
public class OutputTools extends Object
{

/******************************************************************************/

private  String  fileName = "";				// Current file name

private  int  lines_written = 0;			// number of lines written to file

private  FileOutputStream  output_file = null;		// Output file stream

private  PrintStream  output_data = null;		// Output print stream


/******************************************************************************/
public OutputTools ()
{
  initialize ();
}  // constructor OutputTools 


/******************************************************************************/
public OutputTools ( String filename )
{
  initialize ();

  setFileName ( filename );
}  // constructor OutputTools


/******************************************************************************/
public String getFileName ()
{
  return fileName;
}  // method getFileName


/******************************************************************************/
  public int getLinesWritten ()
  {
    return lines_written;
  }  // method getLinesWritten


/******************************************************************************/
public PrintStream getPrintStream ()
{ 
  return output_data;
}  // method getPrintStream


/******************************************************************************/
public void setFileName ( String filename )
{
  fileName = filename;
}  // method setFileName 


/******************************************************************************/
public void initialize ()
{
  fileName = "";
  lines_written = 0;
  output_file = null;
  output_data = null;
}  // method initialize 



/******************************************************************************/
public void openFile ()
{
  try
  {
    output_file = new FileOutputStream ( fileName );

    output_data = new PrintStream ( output_file );
  }  /* try */
  catch ( IOException e )
  {
    System.out.println ( "OutputTools.openFile: IOException on output file: " 
        + fileName + "; " + e );
  }  /* catch */

}  // method openFile 


/******************************************************************************/
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

  // Check for no lines written.
  if ( lines_written == 0 )  deleteFile ();
}  // method closeFile 


/******************************************************************************/
public void deleteFile ()
{
  File delete_file = new File ( fileName );

  // System.out.println ( "OutputTools.deleteFile: trying to delete: " + fileName );

  delete_file.deleteOnExit ();
  delete_file = null;
  output_file = null;
  output_data = null;
}  // method deleteFile


/******************************************************************************/
public void print ( String text_string )
{
  output_data.print ( text_string );
}  // method print


/******************************************************************************/
public void println ()
{
  output_data.println ();
  lines_written++;
}  // method println


/******************************************************************************/
public void println ( String line )
{
  output_data.println ( line );
  lines_written++;
}  // method println


/******************************************************************************/
public void println ( StringBuffer line )
{
  output_data.println ( line.toString () );
  lines_written++;
}  // method println


/******************************************************************************/
public static void main ( String [] args )
{
  OutputTools app = new OutputTools ();

  app.setFileName ( "test.out" );

  app.openFile ();

  app.println ( "test" );

  app.closeFile ();
}  // method main 

}  // class OutputTools 
