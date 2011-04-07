


/******************************************************************************/
/**
  This class models a GenBank sequence publication reference.
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
public class GenBankReference extends Object
{


/******************************************************************************/

  private String reference = "";		// REFERENCE

  private String authors = "";		// AUTHORS

  private String title = "";		// TITLE

  private String journal = "";		// JOURNAL

  private String medline = "";		// MEDLINE

  private String pubmed = "";		// PUBMED

  private String remark = "";		// REMARK


/******************************************************************************/
  // Constructor GenBankReference
  public GenBankReference ()
  {
    initialize ();
  }  // constructor GenBankReference


/******************************************************************************/
  // Initialize class variables.
  public void initialize ()
  {
    reference = "";
    authors = "";
    title = "";
    journal = "";
    medline = "";
    pubmed = "";
    remark = "";
  }  // method initialize 


/******************************************************************************/
  public String getReference ()
  {
    return reference;
  }  // method getReference


/******************************************************************************/
  public String getAuthors ()
  {
    return authors;
  }  // method getAuthors


/******************************************************************************/
  public String getTitle ()
  {
    return title;
  }  // method getTitle


/******************************************************************************/
  public String getJournal ()
  {
    return journal;
  }  // method getJournal


/******************************************************************************/
  public String getMedline ()
  {
    return medline;
  }  // method getMedline


/******************************************************************************/
  public String getPubmed ()
  {
    return pubmed;
  }  // method getPubmed


/******************************************************************************/
  public String getRemark ()
  {
    return remark;
  }  // method getRemark


/******************************************************************************/
  public void setReference ( String value )
  {
    reference = value;
  }  // method setReference


/******************************************************************************/
  public void setAuthors ( String value )
  {
    authors = value;
  }  // method setAuthors


/******************************************************************************/
  public void setTitle ( String value )
  {
    title = value;
  }  // method setTitle


/******************************************************************************/
  public void setJournal ( String value )
  {
    journal = value;
  }  // method setJournal


/******************************************************************************/
  public void setMedline ( String value )
  {
    medline = value;
  }  // method setMedline


/******************************************************************************/
  public void setPubmed ( String value )
  {
    pubmed = value;
  }  // method setPubmed


/******************************************************************************/
  public void setRemark ( String value )
  {
    remark = value;
  }  // method setRemark


/******************************************************************************/

}  // class GenBankReference
