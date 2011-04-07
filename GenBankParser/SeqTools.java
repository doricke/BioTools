

/******************************************************************************/
/**
  This class of sequence methods and data structures.
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
public class SeqTools extends Object
{


/******************************************************************************/

private static int MAX_CODON = 64;		// Maximum codon index

private static int [] base_map = { 0,

/* 1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/*26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/* 51  52  53  54  55  56  57  58  59  60  61  62  63  64 */
   64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,

/* A   B  C   D   E   F  G   H   I   J   K   L   M */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* N   O   P   Q   R   S  T  U   V   W   X   Y   Z */
  64, 64, 64, 64, 64, 64, 0, 0, 64, 64, 64, 64, 64,

  64, 64, 64, 64, 64, 64,

/* a   b  c   d   e   f  g   h   i   j   k   l   m */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* n   o   p   q   r   s  t  u   v   w   x   y   z */
  64, 64, 64, 64, 64, 64, 0, 0, 64, 64, 64, 64, 64
};


private static  int [] base_map_3 = { 0,

/* 1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/*26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 */
  70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,70,64,70,70,70,70,70,70,70,70,

/* 51  52  53  54  55  56  57  58  59  60  61  62  63  64 */
   64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64,

/* A   B  C   D   E   F  G   H   I   J   K   L   M */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* N   O   P   Q   R   S  T  U   V   W   X   Y   Z */
  64, 64, 64, 64,  2, 64, 0, 0, 64, 64, 64,  0, 64,

  64, 64, 64, 64, 64, 64,

/* a   b  c   d   e   f  g   h   i   j   k   l   m */  
   2, 64, 1, 64, 64, 64, 3, 64, 64, 64, 64, 64, 64, 

/* n   o   p   q   r   s  t  u   v   w   x   y   z */
  64, 64, 64, 64,  2, 64, 0, 0, 64, 64, 64,  0, 64
};


/* Genetic code */
private static  char [] codon_map = {
  'F', 'F', 'L', 'L',     'S', 'S', 'S', 'S',  
  'Y', 'Y', '*', '*',     'C', 'C', '*', 'W',

  'L', 'L', 'L', 'L',     'P', 'P', 'P', 'P',
  'H', 'H', 'Q', 'Q',     'R', 'R', 'R', 'R',

  'I', 'I', 'I', 'M',     'T', 'T', 'T', 'T',
  'N', 'N', 'K', 'K',     'S', 'S', 'R', 'R',

  'V', 'V', 'V', 'V',     'A', 'A', 'A', 'A',
  'D', 'D', 'E', 'E',     'G', 'G', 'G', 'G'  };


/* Genetic code */
private static  String [] codons = {
  "Phe", "Phe", "Leu", "Leu",     "Ser", "Ser", "Ser", "Ser",  
  "Tyr", "Tyr", "Ter", "Ter",     "Cys", "Cys", "Ter", "Trp",

  "Leu", "Leu", "Leu", "Leu",     "Pro", "Pro", "Pro", "Pro",
  "His", "His", "Gln", "Gln",     "Arg", "Arg", "Arg", "Arg",

  "Ile", "Ile", "Ile", "Met",     "Thr", "Thr", "Thr", "Thr",
  "Asn", "Asn", "Lys", "Lys",     "Ser", "Ser", "Arg", "Arg",

  "Val", "Val", "Val", "Val",     "Ala", "Ala", "Ala", "Ala",
  "Asp", "Asp", "Glu", "Glu",     "Gly", "Gly", "Gly", "Gly"  };


// Map from single letter amino acid code to 3 letter code.
private static String [] map1to3 = {
  //  A      B      C      D      E      F      G      H      I
    "Ala", "Err", "Cys", "Asp", "Glu", "Phe", "Gly", "His", "Ile",
  //  J      K      L      M      N      O      P      Q      R
    "Err", "Lys", "Leu", "Met", "Asn", "Err", "Pro", "Gln", "Arg",
  //  S      T      U      V      W      X      Y      Z
    "Ser", "Thr", "Err", "Val", "Trp", "Ter", "Tyr", "Err" };


private static  char [] complement = {

/*    1    2    3    4    5    6    7    8    9   10 */
' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 20 */
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 30 */
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',  /* 40 */
     ' ', ' ', ' ', ' ', '-', '.', ' ', ' ', ' ', ' ',  /* 50 */
     ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '>',  /* 60 */
     ' ', '<', ' ', ' ',                                /* 64 */

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
   'T', 'V', 'G', 'H', ' ', ' ', 'C', 'D', ' ', ' ', 'M', ' ', 'K',

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
   'N', ' ', ' ', ' ', 'Y', 'S', 'A', 'A', 'B', 'W', 'N', 'R', ' ',

   ' ', ' ', ' ', '^', ' ', ' ',

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
   't', 'v', 'g', 'h', ' ', ' ', 'c', 'd', ' ', ' ', 'm', ' ', 'k',

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
   'n', ' ', ' ', ' ', 'y', 's', 'a', 'a', 'b', 'w', 'n', 'r', ' ',

   ' ', '|', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
};


private static  int [] dna_mask = { 0,

/*    1    2    3    4    5    6    7    8    9   10 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 20 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 30 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 40 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 50 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 60 */
      0,   0,   0,   0,   

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
    1, 0xE,   4, 0xB,   0,   0,   2, 0xD,   0,   0, 0xA,   0,   5,

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
  0xF,   0,   0,   0,   3,   6,   8,   8,   7,   9, 0xF, 0xC,   0,

    0,   0,   0,   0,   0,   0,

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
    1, 0xE,   4, 0xB,   0,   0,   2, 0xD,   0,   0, 0xA,   0,   5,

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
  0xF,   0,   0,   0,   3,   6,   8,   8,   7,   9, 0xF, 0xC,   0 };


private static  int [] mask_score = { 0,

/*    1    2    3    4    5    6    7    8    9   10 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 20 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 30 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 40 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 50 */
      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  /* 60 */
      0,   0,   0,   0,   

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
    4,   1,   4,   1,   0,   0,   4,   1,   0,   0,   2,   0,   2,

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
    0,   0,   0,   0,   2,   2,   4,   4,   1,   2,   0,   2,   0,

    0,   0,   0,   0,   0,   0,

/*  a    b    c    d    e    f    g    h    i    j    k    l    m */
    4,   1,   4,   1,   0,   0,   4,   1,   0,   0,   2,   0,   2,

/*  n    o    p    q    r    s    t    u    v    w    x    y    z */
    0,   0,   0,   0,   2,   2,   4,   4,   1,   2,   0,   2,   0 };



/******************************************************************************/
public SeqTools ()
{
}  // constructor SeqTools


/******************************************************************************/
public static int computeStartCodon ( String mRNA )
{
  // Check if the mRNA sequence is too short.
  if ( mRNA.length () < 3 )  return 0;

  // Check for the start codon.
  if ( mapCodon ( mRNA.substring ( 0, 3 ) ) == 'M' )  return 1;

  return 0;		// No ATG found.
}  // method computeStartCodon


/******************************************************************************/
// This method assumes the end of the mRNA is the stop codon.
public static int computeStopCodon ( String mRNA )
{
  // Check if the mRNA sequence is too short.
  if ( mRNA.length () < 3 )  return 0;

  // Find the last base.
  int last_base = mRNA.length () - 1;

  // Check for a 3' incomplete marker.
  if ( mRNA.charAt ( last_base ) == '>' )  last_base--;

  // Compute the first base of the stop codon.
  int first_base = last_base - 2;

  // Check for a valid first base of the stop codon.
  if ( first_base < 0 )  return 0;

  // Check for the stop codon.
  if ( mapCodon ( mRNA.substring ( first_base ) ) == '*' )

    return first_base + 1;

  return 0;		// No TAA, TAG, or TGA found.
}  // method computeStopCodon


/******************************************************************************/
public static int findBestPattern 
    ( String sequence
    , int first
    , int last
    , String pattern 
    )
{
  int best_start = -1;		// the best position in the sequence
  int score = 0;		// the best position in the sequence

  // Evaluate all possible pattern positions in the sequence.
  for ( int s = first; s <= last - pattern.length () + 1; s++ )
  {
    // Evaluate the pattern at this position.
    int matches = 0;
    int new_score = 0;
    for ( int p = 0; p < pattern.length (); p++ )
    {
      // Check if the bases match allowing for IUB codes.
      if ( (dna_mask [ sequence.charAt ( s + p ) ] & dna_mask [ pattern.charAt ( p ) ]) != 0 )
      {
        matches++;
        int s_score = mask_score [ sequence.charAt ( s + p ) ];
        int p_score = mask_score [ pattern.charAt ( p ) ];

        // Add the lowest score (based on IUB letters).
        if ( s_score < p_score )
          new_score += s_score;
        else
          new_score += p_score;
      }  // if
    }  // for p

    // Check for a better pattern match & select 3' most pattern.
    if ( ( new_score >= score ) && ( matches == pattern.length () ) )
    {
      score = new_score;
      best_start = s;
    }  // if
  }  // for s

  return best_start;
}  // method findBestPattern


/******************************************************************************/
public static int findTATAbox
    ( String sequence
    , int first
    , int last
    )
{
  int best_start = -1;		// the best position in the sequence
  int score = 0;		// the best position in the sequence

  // Evaluate all possible pattern positions in the sequence.
  for ( int s = first; s <= last - 6; s++ )
  {
    // Evaluate the TATA pattern at this position.
    int new_score = 0;

    if ( (dna_mask [ sequence.charAt ( s ) ] & dna_mask [ 'T' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s ) ] * 2;

    if ( (dna_mask [ sequence.charAt ( s + 1 ) ] & dna_mask [ 'A' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s + 1 ) ] * 2;

    if ( (dna_mask [ sequence.charAt ( s + 2 ) ] & dna_mask [ 'T' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s + 2 ) ] * 2;

    if ( (dna_mask [ sequence.charAt ( s + 3 ) ] & dna_mask [ 'A' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s + 3 ) ] * 2;

    if ( (dna_mask [ sequence.charAt ( s + 4 ) ] & dna_mask [ 'A' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s + 4 ) ] * 2;
    else
      if ( (dna_mask [ sequence.charAt ( s + 4 ) ] & dna_mask [ 'T' ]) != 0 )
        new_score += mask_score [ sequence.charAt ( s + 4 ) ];

    if ( (dna_mask [ sequence.charAt ( s + 5 ) ] & dna_mask [ 'A' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s + 5 ) ] * 2;

    if ( (dna_mask [ sequence.charAt ( s + 6 ) ] & dna_mask [ 'A' ]) != 0 )
      new_score += mask_score [ sequence.charAt ( s + 6 ) ] * 2;
    else
      if ( (dna_mask [ sequence.charAt ( s + 6 ) ] & dna_mask [ 'T' ]) != 0 )
        new_score += mask_score [ sequence.charAt ( s + 6 ) ];

    // Check for a better pattern match & select 3' most pattern.
    if ( ( new_score >= score ) && ( new_score >= 48 ) )
    {
      score = new_score;
      best_start = s;
    }  // if
  }  // for s

  return best_start;
}  // method findTATAbox


/******************************************************************************/
public static int findPattern ( String sequence, int first, int list )
{
  return 0;
}  // method findPattern


/******************************************************************************/
/* This function translates a codon into an amino acid. */
public static char mapCodon ( String codon_seq )
{
  char amino_acid;	/* translated amino acid */
  int  index;		/* codon table index */


  String codon = codon_seq.toUpperCase ();

// System.out.println ( "mapCodon: codon_seq = '" + codon_seq + "'" );

  index = base_map   [ codon.charAt ( 0 ) ] * 16 +
          base_map   [ codon.charAt ( 1 ) ] *  4 +
          base_map_3 [ codon.charAt ( 2 ) ];

  if ( index >= MAX_CODON )
  {
    index = base_map [ codon.charAt ( 0 ) ] * 16 +
            base_map [ codon.charAt ( 1 ) ] *  4;

    /* TRA */
    if ( ( codon.charAt ( 0 ) == 'T' ) && ( codon.charAt ( 1 ) == 'R' ) &&
         ( codon.charAt ( 2 ) == 'A' ) )  return ( '*' );

    /* YTR */
    if ( ( codon.charAt ( 0 ) == 'Y' ) && ( codon.charAt ( 1 ) == 'T' ) )

      if ( ( codon.charAt ( 2 ) == 'R' ) || ( codon.charAt ( 2 ) == 'A' ) ||
           ( codon.charAt ( 2 ) == 'G' ) )  return ( 'L' );

    /* MGR */
    if ( ( codon.charAt ( 0 ) == 'M' ) && ( codon.charAt ( 1 ) == 'G' ) )

      if ( ( codon.charAt ( 2 ) == 'R' ) || ( codon.charAt ( 2 ) == 'A' ) ||
           ( codon.charAt ( 2 ) == 'G' ) )  return ( 'R' );

    if ( index >= MAX_CODON )  return ( 'X' );

    amino_acid = codon_map [ index ];

    /* GCN, GGN, CCN, ACN, & GTN */
    if ( ( amino_acid == 'A' ) || ( amino_acid == 'G' ) || 
         ( amino_acid == 'P' ) ||
         ( amino_acid == 'T' ) || ( amino_acid == 'V' ) )
      return ( amino_acid );

    /* CTN */
    if ( ( amino_acid == 'L' ) && ( codon.charAt ( 0 ) == 'C' ) )
      return ( amino_acid );

    /* CGN */
    if ( ( amino_acid == 'R' ) && ( codon.charAt ( 0 ) == 'C' ) )
      return ( amino_acid );

    /* TCN */
    if ( ( amino_acid == 'S' ) && ( codon.charAt ( 0 ) == 'T' ) )
      return ( amino_acid );

    /* ATH */
    if ( ( amino_acid == 'I' ) && ( ( codon.charAt ( 2 ) == 'H' ) ||
         ( codon.charAt ( 2 ) == 'K' ) || ( codon.charAt ( 2 ) == 'W' ) ) )
      return ( amino_acid );
 
    return ( 'X' );
  }
  else
    return ( codon_map [ index ] );
}  // method mapCodon 


/******************************************************************************/
/* This function translates a codon into an amino acid (three letter code). */
public static String mapCodon3 ( String codon_seq )
{
  String amino_acid;	/* translated amino acid */
  int  index;		/* codon table index */


  String codon = codon_seq.toUpperCase ();

// System.out.println ( "mapCodon3: codon_seq = '" + codon_seq + "'" );

  index = base_map   [ codon.charAt ( 0 ) ] * 16 +
          base_map   [ codon.charAt ( 1 ) ] *  4 +
          base_map_3 [ codon.charAt ( 2 ) ];

  if ( index >= MAX_CODON )
  {
    index = base_map [ codon.charAt ( 0 ) ] * 16 +
            base_map [ codon.charAt ( 1 ) ] *  4;

    /* TRA */
    if ( ( codon.charAt ( 0 ) == 'T' ) && ( codon.charAt ( 1 ) == 'R' ) &&
         ( codon.charAt ( 2 ) == 'A' ) )  return ( "Ter" );

    /* YTR */
    if ( ( codon.charAt ( 0 ) == 'Y' ) && ( codon.charAt ( 1 ) == 'T' ) )

      if ( ( codon.charAt ( 2 ) == 'R' ) || ( codon.charAt ( 2 ) == 'A' ) ||
           ( codon.charAt ( 2 ) == 'G' ) )  return ( "Leu" );

    /* MGR */
    if ( ( codon.charAt ( 0 ) == 'M' ) && ( codon.charAt ( 1 ) == 'G' ) )

      if ( ( codon.charAt ( 2 ) == 'R' ) || ( codon.charAt ( 2 ) == 'A' ) ||
           ( codon.charAt ( 2 ) == 'G' ) )  return ( "Arg" );

    if ( index >= MAX_CODON )  return ( "Xxx" );

    char aminoacid = codon_map [ index ];
    amino_acid = codons [ index ];

    /* GCN, GGN, CCN, ACN, & GTN */
    if ( ( aminoacid == 'A' ) || ( aminoacid == 'G' ) || 
         ( aminoacid == 'P' ) ||
         ( aminoacid == 'T' ) || ( aminoacid == 'V' ) )
      return ( amino_acid );

    /* CTN */
    if ( ( aminoacid == 'L' ) && ( codon.charAt ( 0 ) == 'C' ) )
      return ( amino_acid );

    /* CGN */
    if ( ( aminoacid == 'R' ) && ( codon.charAt ( 0 ) == 'C' ) )
      return ( amino_acid );

    /* TCN */
    if ( ( aminoacid == 'S' ) && ( codon.charAt ( 0 ) == 'T' ) )
      return ( amino_acid );

    /* ATH */
    if ( ( aminoacid == 'I' ) && ( ( codon.charAt ( 2 ) == 'H' ) ||
         ( codon.charAt ( 2 ) == 'K' ) || ( codon.charAt ( 2 ) == 'W' ) ) )
      return ( amino_acid );
 
    return ( "Xxx" );
  }
  else
    return ( codons [ index ] );
}  // method mapCodon3 


/******************************************************************************/
public static String convert1to3
    ( String seq
    )
{
  StringBuffer aa_seq = new StringBuffer ( seq.length () * 3 );

  for ( int i = 0; i < seq.length (); i++ )
  {
    if ( ( seq.charAt ( i ) >= 'a' ) && ( seq.charAt ( i ) <= 'z' ) )
      aa_seq.append ( map1to3 [ (int) (seq.charAt ( i ) - 'a') ] );
    else
      if ( ( seq.charAt ( i ) >= 'A' ) && ( seq.charAt ( i ) <= 'Z' ) )
        aa_seq.append ( map1to3 [ (int) (seq.charAt ( i ) - 'A') ] );
      else
        if ( seq.charAt ( i ) == '*' )
          aa_seq.append ( "Ter" );
        else
          if ( seq.charAt ( i ) == '.' ) 
            aa_seq.append ( "..." );
          else
            if ( seq.charAt ( i ) == '-' )
              aa_seq.append ( "---" );
            else
              aa_seq.append ( "Err" );
  }  // for

  return aa_seq.toString ();
}  // method convert1to3


/******************************************************************************/
  public static String getCodon ( String sequence )
  {
    StringBuffer codon = new StringBuffer ( 4 );
    codon.setLength ( 0 );

    for ( int i = 0; i < sequence.length (); i++ )
    {
      // Append the next base if it is a letter.
      if ( ( ( sequence.charAt ( i ) >= 'A' ) && ( sequence.charAt ( i ) <= 'Z' ) ) ||
           ( ( sequence.charAt ( i ) >= 'a' ) && ( sequence.charAt ( i ) <= 'z' ) ) )

        codon.append ( sequence.charAt ( i ) );

      if ( codon.length () == 3 )  return codon.toString ();
    }  // for

    return codon.toString ();
  }  // method getCodon


/******************************************************************************/
public static String translate
  ( String seq
  , int    first
  , int    last
  )
{
  StringBuffer aa_seq = new StringBuffer ( seq.length () / 3 );

  for ( int i = first; i < last - 1; i += 3 )
  {
    aa_seq.append ( mapCodon ( getCodon ( seq.substring ( i ) ) ) );
  }  // for

  return aa_seq.toString ();
}  // method translate


/******************************************************************************/
public static String translate ( String seq )
{
  return translate ( seq, 0, seq.length () - 1 );
}  // method translate


/******************************************************************************/
public static String translate3 ( String seq )
{
  return translate3 ( seq, 0, seq.length () - 1 );
}  // method translate3


/******************************************************************************/
public static String translate3
  ( String seq
  , int    first
  , int    last
  )
{
  StringBuffer aa_seq = new StringBuffer ( seq.length () );

  for ( int i = first; i < last - 1; i += 3 )
  {
    aa_seq.append ( mapCodon3 ( getCodon ( seq.substring ( i ) ) ) );
  }  // for

  return aa_seq.toString ();
}  // method translate3


/******************************************************************************/
  // This method search for IUB codes in a sequence.
  public static boolean isIUBs ( String sequence )
  {
    for ( int index = 0; index < sequence.length (); index++ )

      if ( mask_score [ sequence.charAt ( index ) ] != 4 )  return true;

    return false;
  }  // method is IUBs


/******************************************************************************/
// This method removes the markers <, >, and | from a DNA sequence.
public static String removeMarkers ( String sequence )
{
  if ( sequence.length () <= 0 )  return "";

  StringBuffer seq = new StringBuffer ( sequence );

  for ( int i = seq.length () - 1; i >= 0; i-- )

    // Delete all non-sequence letters.
    if ( ( ( seq.charAt ( i ) >= 'A' ) && ( seq.charAt ( i ) <= 'Z' ) ) ||
         ( ( seq.charAt ( i ) >= 'a' ) && ( seq.charAt ( i ) <= 'z' ) ) )
    {
    }  // if
    else
      seq.deleteCharAt ( i );

  return seq.toString (); 
}  // method removeMarkers


/******************************************************************************/
/* This function reverses and complements a DNA sequence. */
public static String reverseSequence ( String seq )
{
  StringBuffer rev = new StringBuffer ( seq );

  for ( int index = 0; index < seq.length (); index++ )

    rev.setCharAt ( index, 
        complement [ seq.charAt ( seq.length () - index - 1 ) ] );

  return rev.toString ();
}  // method reverseSequence 


/******************************************************************************/
public static void writeFasta ( OutputTools file, String sequence )
{
  for ( int index = 0; index < sequence.length (); index += 60 )
  {
    if ( index + 60 >= sequence.length() )

      file.println ( sequence.substring( index ) );

    else

      file.println ( sequence.substring( index, index + 60 ) );
  }  // end: for
}  // method writeFasta


/******************************************************************************/
private static int extent ( String seq1, String seq2 )
{
  // Extend the alignment
  int length = 0;
  while ( ( length < seq1.length () ) && 
          ( length < seq2.length () ) &&
          ( seq1.charAt ( length ) == seq2.charAt ( length ) ) )

    length++;

  return length;
}  // method extent


/******************************************************************************/
public static void align ( String genomic, String cdna )
{
  int  length;
  int  g_start = -1;
  int  c_start = 0;
  int  window = 12;

  // Find the first block that aligns.
  boolean found = false;
  while ( ( c_start < cdna.length () - window ) && ( found == false ) )
  {
    // Search for the start of the cDNA sequence.
    g_start = genomic.indexOf ( cdna.substring ( c_start, window ) );

    // Check if found.
    if ( g_start != -1 )
      found = true;
    else
      c_start++;
  }  // for 

  System.out.println ( "cDNA starting index = " + c_start );
  System.out.println ( "gDNA starting index = " + g_start );

  // Extend the alignment
  length = extent ( genomic.substring ( g_start ), cdna.substring ( c_start ) );

  System.out.println ( "Aligned segment length = " + length );
  System.out.println ();
  System.out.println ( "Genomic: [" + g_start + "," + (g_start + length - 1) + "]" );
  System.out.println ( "cDNA   : [" + g_start + "," + (g_start + length - 1) + "]" );

}  // method align


/******************************************************************************/
  public static void main ( String [] args )
  {
    char c;
    int i;

    c = 'A';
    i = (int) c;

    System.out.println ( "Char " + c + " = " + i );

    String seq = "AGTC";

    System.out.println ( "Sequence " + seq + " complement = " + 
        reverseSequence ( seq ) );

    seq = "ATG";

    char aa =  mapCodon ( seq );

    System.out.println ( "Codon " + seq + " tranlation = " + aa );

    seq = "ATGTTTCTTATTGTTTCTCCTACTGCTTATTAATAGCATCAAAATAAAGATGAATGTTGATGGCGTAGTAGAGGT";
//  DNA = "ATGTTTCTTATT........TACTGCTTATTAA........AAATAAAGATGA........GGCGTAGTAGAGG";
//                   1         2         3         4         5         6         7
//         012345678901234567890123456789012345678901234567890123456789012345678901234567890

    // Translate a DNA sequence into a protein sequence.
    for ( i = 0; i <= 2; i++ )

      System.out.println ( "Frame " + i + " " +
        translate ( seq, i, seq.length () - 1 ) );

    // Translate a DNA sequence into a protein sequence (3 letter code).
    for ( i = 0; i <= 2; i++ )

      System.out.println ( "Frame " + i + " " +
        translate3 ( seq, i, seq.length () - 1 ) );

    String cDNA = "ATGTTTCTTATTTACTGCTTATTAAAAATAAAGATGAGGCGTAGTAGAGG";

    align ( seq, cDNA );
  }  // method main


/******************************************************************************/

}  // class SeqTools

