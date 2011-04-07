#!/usr/bin/perl -w

use strict;
# use warnings;

# This program determines the length of sequences in a FASTA file.
#
# Author::      Darrell O. Ricke, Ph.D.  (mailto: d_ricke@yahoo.com)
# Copyright::   Copyright (C) 2000 Darrell O. Ricke, Ph.D., Paragon Software
# License::     GNU GPL license:  http://www.gnu.org/licenses/gpl.html
# Contact::     Paragon Software, 1314 Viking Blvd., Cedar, MN 55011
#
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.

###############################################################################
main:
{
  # Change the record separator.
  local $/ = "\n>";

  # Read in sequences one sequence at a time.
  while ( <> )
  {
    chomp;
    s/>//;		# strip ">" from header line

    # Split record into header field and sequence fields.
    my ($header, @seq) = split (/\n/);

    # Combine the sequence lines.
    my $seq = join ( "", @seq );

    my ($name, @desc) = split ( " ", $header );

    my $len = length ( $seq );
    print "$name\t$len\n";
  }  # while
}  # main
