

/******************************************************************************/
/**
  This class models a SwissProt sequence feature.
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

public class SwissProtFeature extends Object
{

/******************************************************************************/

  private String key_name;		// Key name			

  private String from;			// 'FROM' endpoint

  private String to;			// 'TO' endpoint

  private String description;	// Description 


  boolean partial = false;		// /partial
 

/******************************************************************************/
  public SwissProtFeature ()
  {
    initialize ();
  }  // constructor SwissProtFeature


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    key_name = "";
    from = "";
    to = "";
    description = "";

    partial = false;
  }  // method initialize


/******************************************************************************/
  public String getDescription ()
  {
    return description;
  }  // method getDescription


/******************************************************************************/
  public String getFrom ()
  {
    return from;
  }  // method getFrom


/******************************************************************************/
  public int getFromValue ()
  {
    if ( from.length () <= 0 )  return 0;

    if ( from.charAt ( 0 ) == '<' )
      return ( InputTools.getInteger ( from.substring ( 1 ) ) );
    else
      return ( InputTools.getInteger ( from ) );
  }  // method getFrom


/******************************************************************************/
  public String getKeyName ()
  {
    return key_name;
  }  // method getKeyName


/******************************************************************************/
  public boolean getPartial ()
  {
    return partial;
  }  // method getPartial


/******************************************************************************/
  public String getTo ()
  {
    return to;
  }  // method getTo


/******************************************************************************/
  public int getToValue ()
  {
    if ( to.length () <= 0 )  return 0;

    if ( to.charAt ( 0 ) == '>' )
      return ( InputTools.getInteger ( to.substring ( 1 ) ) );
    else
      return ( InputTools.getInteger ( to ) );
  }  // method getTo


/******************************************************************************/
  public boolean isPartial ()
  {
    return partial;
  }  // method isPartial


/******************************************************************************/
  public void setDescription ( String value )
  {
    description = value;

    // Trim off the trailing period.
    int index = value.length () - 1;
    if ( index < 0 )  return;

    description = value.substring ( 0, index );
  }  // method setDescription


/******************************************************************************/
  public void setFrom ( String value )
  {
    from = value;

    // Check for a partial flag.
    if ( value.indexOf ( "<" ) != -1 )  partial = true;
  }  // method setFrom


/******************************************************************************/
  public void setKeyName ( String value )
  {
    key_name = value;
  }  // method setKeyName


/******************************************************************************/
  public void setPartial ( boolean value )
  {
    partial = value;
  }  // method setPartial


/******************************************************************************/
  public void setTo ( String value )
  {
    to = value;

    // Check for a partial flag.
    if ( value.indexOf ( ">" ) != -1 )  partial = true;
  }  // method setTo


/******************************************************************************/
  public void setFt ( String keyname, String start, String end, String desc )
  {
    setKeyName ( keyname );

    setFrom ( start );

    setTo ( end );

    setDescription ( desc );
  }  // method setFt


/******************************************************************************/

}  // class SwissProtFeature

