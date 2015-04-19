# BioTools
The BioTools are complete Bioinformatics programs.

<h2>Database Searching and Alignment Tools:</h2>

  ProteinSearch - searches a list of protein FASTA databases with for protein sequences.
                - this program can identify evolutionarily related sequences deep into 
                  the twilight zone (as low as 5% sequence identity).

  AlignHits - generates protein multiple sequence alignments from ProteinSearch results
            - this program can align more than 20,000 protein sequences!


<h2>Parser Tools:</h2>

  GenBankParser - GenBank source file parser; generates FASTA files

  SwissProtParser - SwissProt source file parser; generates FASTA file

  <b>java -jar SwissProtParser.jar uniprot_sprot.dat

 <h4>Example output</h4>
<pre>
>A1BG_HUMAN /organism="Homo sapiens (Human)" /accession="P04217" /gene="A1BG" /taxon_id="9606" /refseq_id="NP_570602" /description="RecName: Full=Alpha-1B-glycoprotein; AltName: Full=Alpha-1-B glycoprotein; Flags: Precursor;" /id="A1BG_HUMAN" /taxonomy="Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia; Eutheria; Euarchontoglires; Primates; Haplorrhini; Catarrhini; Hominidae; Homo"
MSMLVVFLLLWGVTWGPVTEAAIFYETQPSLWAESESLLKPLANVTLTCQ
AHLETPDFQLFKNGVAQEPVHLDSPAIKHQFLLTGDTQGRYRCRSGLSTG
WTQLSKLLELTGPKSLPAPWLSMAPVSWITPGLKTTAVCRGVLRGVTFLL
RREGDHEFLEVPEAQEDVEATFPVHQPGNYSCSYRTDGEGALSEPSATVT
IEELAAPPPPVLMHHGESSQVLHPGNKVTLTCVAPLSGVDFQLRRGEKEL
LVPRSSTSPDRIFFHLNAVALGDGGHYTCRYRLHDNQNGWSGDSAPVELI
LSDETLPAPEFSPEPESGRALRLRCLAPLEGARFALVREDRGGRRVHRFQ
SPAGTEALFELHNISVADSANYSCVYVDLKPPFGGSAPSERLELHVDGPP
PRPQLRATWSGAVLAGRDAVLRCEGPIPDVTFELLREGETKAVKTVRTPG
AAANLELIFVGPQHAGNYRCRYRSWVPHTFESELSDPVELLVAES
</pre>

<h2>Utilities:</h2>

  lengths.pl - summarizes the lengths of sequences in a FASTA sequences file.

  Motifs.pl - identifies Prosite motifs in a protein FASTA sequences file.
