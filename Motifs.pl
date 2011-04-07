#!/usr/bin/perl -w


use strict;
# use warnings;

# This program detects Prosite motifs in protein sequence a FASTA file.
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
  # Read in the prosite patterns.
  my %patterns;
  my %accessions;
  my %descriptions;
  # open PROSITE, "prosite.dat.perl";
  while ( <DATA> )
  {
    chomp;

    my @fields = split ( /\t/, $_ );	# split line on tabs

    $patterns{$fields[0]} = $fields[1];
    $accessions{$fields[0]} = $fields[2];
    $descriptions{$fields[0]} = $fields[3];
  }  # while
  # close PROSITE;


  # Change the record separator.
  local $/ = "\n>";

  # Read in protein sequences one sequence at a time.
  while ( <> )
  {
    chomp;
    s/>//;		# strip ">" from header line

    # Split record into header field and sequence fields.
    my ($header, @seq) = split (/\n/);

    # Combine the sequence lines.
    my $seq = join ( "", @seq );

    my ($name, @desc) = split ( " ", $header );

    # Search the protein sequence for the Prosite patterns.
    foreach my $motif_name ( keys %patterns )
    {
      while ( $seq =~ /($patterns{$motif_name})/g )
      {
        my $motif_start = length ( $` ) + 1;
        my $motif_end = length ( $` ) + length ( $1 );

        print "$name\t";
        print "$motif_start\t";
        print "$motif_end\t";
        print "$motif_name\t";
        print "$accessions{$motif_name}\t";
        print "$descriptions{$motif_name}\t";
        print "$1\t";
        print "$1\n";
      }  # while
    }  # foreach
  }  # while
}  # main
__DATA__
ASX_HYDROXYL	C.[DN].{4}[FY].C.C	PS00010	Aspartic acid and asparagine hydroxylation site.
PHOSPHOPANTETHEINE	[DEQGSTALMKRH][LIVMFYSTAC][GNQ][LIVMFYAG][DNEKHS]S[LIVMST][^PCFY][STAGCPQLIVMF][LIVMATN][DENQGTAKRHLM][LIVMWSTA][LIVGSTACR][^LPIY][^VY][LIVMFA]	PS00012	Phosphopantetheine attachment site.
PROKAR_NTER_METHYL	[KRHEQSTAG]G[FYLIVM][ST][LT][LIVP]E[LIVMFWSTAG]{14}	PS00409	Prokaryotic N-terminal methylation site.
ER_TARGET	[KRHQSA][DENQ]EL$	PS00014	Endoplasmic reticulum targeting sequence.
CNMP_BINDING_1	[LIVM][VIC].[^H]G[DENQTA].[GAC][^L].[LIVMFY]{4}.{2}G	PS00888	Cyclic nucleotide-binding domain signature 1.
CNMP_BINDING_2	[LIVMF]GE.[GAS][LIVM].{5,11}R[STAQ]A.[LIVMA].[STACV]	PS00889	Cyclic nucleotide-binding domain signature 2.
ACTININ_1	[EQ][^LNYH].[ATV][FY][^LDAM][^T]W[^PG]N	PS00019	Actinin-type actin-binding domain signature 1.
ACTININ_2	[LIVM].[SGNL][LIVMN][DAGHENRS][SAGPNVT].[DNEAG][LIVM].[DEAGQ].{4}[LIVM].[LM][SAG][LIVM][LIVMT][WS].{0,1}[LIVM]{2}	PS00020	Actinin-type actin-binding domain signature 2.
ANAPHYLATOXIN_1	[CSH]C.{2}[GAP].{7,8}[GASTDEQR]C[GASTDEQL].{3,9}[GASTDEQN].{2}[CE].{6,7}CC	PS01177	Anaphylatoxin domain signature.
APPLE	C.{3}[LIVMFY].{5}[LIVMFY].{3}[DENQ][LIVMFY].{10}C.{3}CT.{4}C.[LIVMFY]F.[FY].{13,14}C.[LIVMFY][RK].[ST].{14,15}SG.[ST][LIVMFY].{2}C	PS00495	Apple domain.
AGOUTI_1	CX{6}CX{6}CCX{2}CX{2}CXCX{6}CXCX{6,9}C	PS60024	Agouti domain signature.
CTCK_1	CC.{13}C.{2}[GN].{12}C.C.{2,4}C	PS01185	C-terminal cystine knot signature.
DISINTEGRIN_1	C.{2}[GS].CC.{1,2}[NQRSEKD]C.[FMYLVI].{6}C[RKNQ]	PS00427	Disintegrins signature.
EF_HAND_1	D[^W][DNS][^ILVFYW][DENSTG][DNQGHRK][^GP][LIVMC][DENQSTAGC].{2}[DE][LIVMFYW]	PS00018	EF-hand calcium-binding domain.
EGF_1	C.C.{2}[^V].{2}G[^C].C	PS00022	EGF-like domain signature 1.
EGF_2	C.C.{2}[GP][FYW].{4,8}C	PS01186	EGF-like domain signature 2.
EGF_CA	[DEQN].[DEQN]{2}C.{3,14}C.{3,7}C.[DN].{4}[FY].C	PS01187	Calcium-binding EGF-like domain signature.
EGF_LAM_1	C.{1,2}C.{5}G.{2}C.{2}C.{3,4}[FYW].{3,15}C	PS01248	Laminin-type EGF-like (LE) domain signature.
FA58C_1	[GASP]W.{7,15}[FYW][LIV].[LIVFA][GSTDEN].{6}[LIVF].{2}[IV].[LIVT][QKMT]G	PS01285	Coagulation factors 5/8 type C domain (FA58C) signature 1.
FA58C_2	P.{8,10}[LM]R.[GE][LIVP].GC	PS01286	Coagulation factors 5/8 type C domain (FA58C) signature 2.
FERM_1	W[LIV].{3}[KRQ].[LIVM].{2}[QH].{0,2}[LIVMF].{6,8}[LIVMF].{3,5}F[FY].{2}[DENS]	PS00660	FERM domain signature 1.
FERM_2	[HYW].{9}[DENQSTV][SA].{3}[FYC][LIVM].{2,3}[ACVWD].{2}[LM].{2}[FY][GM].[DENQSTH][LIVMFYS]	PS00661	FERM domain signature 2.
FN1_1	C.{6,8}[LFY].{5}[FYW].[RK].{8,10}C.C.{6,9}C	PS01253	Fibronectin type-I domain signature.
FN2_1	C.{2}PF.[FYWIV].{7}C.{8,10}WC.{4}[DNSR][FYW].{3,5}[FYW].[FYWI]C	PS00023	Fibronectin type-II collagen-binding domain signature.
FIBRIN_AG_C_DOMAIN	WW[LIVMFYW].{2}C.{2}[GSA].{2}NG	PS00514	Fibrinogen beta and gamma chains C-terminal domain signature.
GLA_1	EX{2}[ERK]EXCX{6}[EDR].{10,11}[FYA][YW]	PS00011	Vitamin K-dependent carboxylation domain.
HEMOPEXIN	[LIFAT][^IL].{2}W.{2,3}[PE].[^VF][LIVMFY][DENQS][STA][AV][LIVMFY]	PS00024	Hemopexin domain signature.
KRINGLE_1	[FY]C[RH][NS].{7,8}[WY]C	PS00021	Kringle domain signature.
LDLRA_1	C[VILMA].{5,6}C[DNH].{3}[DENQHT]C.{3,4}[STADEW][DEH][DE].{1,5}C	PS01209	LDL-receptor class A (LDLRA) domain signature.
C_TYPE_LECTIN_1	C[LIVMFYATG].{5,12}[WL][^T][DNSR][^C][^LI]C.{5,6}[FYWLIVSTA][LIVMSTA]C	PS00615	C-type lectin domain signature.
LIM_DOMAIN_1	C.{2}C.{15,21}[FYWHPCR]H.{2}[CH].{2}C.{2}C.{3}[LIVMF]	PS00478	LIM zinc-binding domain signature.
LINK_1	C.{15}A.{3,4}[GK].{3}C.{2}G.{8,9}P.{7}C	PS01241	Link domain signature.
OSTEONECTIN_1	C.[DNS].{2}C.{2}G[KRH].C.{6,7}P.C.C.{3,5}CP	PS00612	Osteonectin domain signature 1.
OSTEONECTIN_2	FP.R[IM].DWL.[NQ]	PS00613	Osteonectin domain signature 2.
P_TREFOIL	[KRH].{2}C.[FYPSTV].{3,4}[ST].{3}C.{4}CC[FYWH]	PS00025	P-type 'Trefoil' domain signature.
CBM1_1	CGG.{4,7}G.{3}C.{4,5}C.{3,5}[NHGS].[FYWMI].{2}QC	PS00562	CBM1 (carbohydrate binding type-1) domain signature.
CBM2_A	WN[STAGR][STDN][LIVM].{2}[GST].[GST].{2}[LIVMFT][GA]	PS00561	CBM2a (carbohydrate-binding type-2) domain signature.
CHIT_BIND_I_1	C.{4,5}CCS.{2}G.CG.{3,4}[FYW]C	PS00026	Chitin recognition or binding domain signature.
CHIT_BIND_RR_1	G.{7}[DEN]G.{6}[FY].A[DNG].{2,3}G[FY].[APV]	PS00233	Chitin-binding type R&R domain signature.
BARWIN_1	CG[KR]CL.V.N	PS00771	Barwin domain signature 1.
BARWIN_2	V[DN]Y[EQD]FV[DN]C	PS00772	Barwin domain signature 2.
BIR_REPEAT_1	[HKEPILVY].{2}R.{3,7}[FYW].{11,14}[STAN]G[LMF].[FYHDA].{4}[DESL].{2,3}CX{2}C.{6}[WA]X{9}H.{4}[PRSD].C.{2}[LIVMA]	PS01282	BIR repeat.
4_DISULFIDE_CORE	C[^G][^C][DN].{2}C.{3}[^G].CC	PS00317	WAP-type 'four-disulfide core' domain signature.
CAP_GLY_1	[GD].{0,10}[FYWA].{0,1}G[LIVM].{0,2}[LIVMFYD].{0,7}G[KN][NHW].{0,1}G[STARCV].{0,2}[GD].{0,2}[LY][FC]	PS00845	CAP-Gly domain signature.
LY6_UPAR	[EQR]C[LIVMFYAH].C.{5,8}C.{3,8}[EDNQSTV]C[^C].{5}C.{12,24}C	PS00983	Ly-6 / u-PAR domain signature.
MAM_1	[GE].[LIVMFY]{2}.{3}[STA].{10,11}[LV].{4}[LIVMF].{6,7}C[LIVM].F.[LIVMFY].{3}[GSC]	PS00740	MAM domain signature.
SMB_1	C.C.{3}C.{5,6}CC.[DN][FY].{3}C	PS00524	Somatomedin B domain (SMB) signature.
SRCR_1	[GNRVM].{5}[GLKA].{2}[EQ].{6}[WPS][GLKH].{2}C.{3}[FYW].{8}[CM].{3}G	PS00420	SRCR domain signature.
THYROGLOBULIN_1_1	[FYWHPVAS].{3}C.{3,4}[SG].[FYW].{3}Q.{5,12}[FYW]C[VA].{3,4}[SG]	PS00484	Thyroglobulin type-1 repeat signature.
VWFC_1	C.{2,3}C[^CG]C.{6,14}C.{3,4}C.{2,10}C.{9,16}CC.{2,4}C	PS01208	VWFC domain signature.
WW_DOMAIN_1	W.{9,11}[VFY][FYW].{6,7}[GSTNE][GSTQCR][FYW][^R][^SA]P	PS01159	WW/rsp5/WWP domain signature.
ZF_MYND_1	[CH]X{2,4}CX{7,17}CX{0,2}CX{4}[YFT]CX{3}[CH]X{6,9}HX{3,4}C	PS01360	Zinc finger MYND-type signature.
ZF_PHD_1	C.{1,2}C.{5,45}[VMFLWIE].C.{1,4}C.{1,4}[WYFVQHLT]H.{2}C.{5,45}[WFLYI].C.{2}C	PS01359	Zinc finger PHD-type signature.
ZF_DAG_PE_1	H.[LIVMFYW].{8,11}C.{2}C.{3}[LIVMFC].{5,10}C.{2}C.{4}[HD].{2}C.{5,9}C	PS00479	Zinc finger phorbol-ester/DAG-type signature.
ZF_RANBP2_1	W.C.{2,4}C.{3}N.{6}C.{2}C	PS01358	Zinc finger RanBP2-type signature.
ZF_RING_1	C.H.[LIVMFY]C.{2}C[LIVMYA]	PS00518	Zinc finger RING-type signature.
ZF_ZZ_1	C.{2}C.{4,8}[RHDGSCV][YWFMVIL].[CS].{2,5}[CHEQ].[DNSAGE][YFVLI].[LIVFM]C.{2}C	PS01357	Zinc finger ZZ-type signature.
ZP_1	[LIVMFYW].{7}[STAPDNLR].{3}[LIVMFYW].[LIVMFYW].[LIVMFYW].{2}C[LIVMFYW].[STA][PSLT].{2,4}[DENSG].[STADNQLFM].{6}[LIVM]{2}.{3,4}C	PS00682	ZP domain signature.
SLH_DOMAIN	[LVFYT].[DA].{2,5}[DNGSATPHY][FYWPDA].{4}[LIV].{2}[GTALV].{4,6}[LIVFYC].{2}G.[PGSTA].{2,3}[MFYA].[PGAV].{3,10}[LIVMA][STKR][RY].[EQ].[STALIVM]	PS01072	S-layer homology domain signature.
HOMEOBOX_1	[LIVMFYG][ASLVR].{2}[LIVMSTACN].[LIVM][^Y].{2}[^L][LIV][RKNQESTAIY][LIVFSTNKH]W[FYVC].[NDQTAH].{5}[RKNAIMW]	PS00027	'Homeobox' domain signature.
ANTENNAPEDIA	[LIVMFE][FY]PWM[KRQTA]	PS00032	'Homeobox' antennapedia-type protein signature.
ENGRAILED	LMA[EQ]GLYN	PS00033	'Homeobox' engrailed-type protein signature.
PAIRED_1	RPC.{11}CVS	PS00034	Paired domain signature.
POU_1	[RKQ]R[LIM].[LF]G[LIVMFY].Q.[DNQ]VG	PS00035	POU-specific (POUs) domain signature 1.
POU_2	SQ[STK][TA]I[SC]R[FH][ET].[LSQ].{0,1}[LIR][ST]	PS00465	POU-specific (POUs) domain signature 2.
ZINC_FINGER_C2H2_1	C.{2,4}C.{3}[LIVMFYWC].{8}H.{3,5}H	PS00028	Zinc finger C2H2 type domain signature.
ZF_DOF_1	C.{2}C.{7}[CS].{13}C.{2}C.R.WT.GG	PS01361	Zinc finger Dof-type signature.
ZF_TFIIS_1	C.{2}C.{9}[LIVMQSART][QH][STQLMI][RA][SACR].[DE][DET][PGSEAM].{6}C.{2,5}C.{3}[FWE]	PS00466	Zinc finger TFIIS-type signature.
NUCLEAR_REC_DBD_1	C.{2}C.{1,2}[DENAVSPHKQT].{5,6}[HNY][FY].{4}C.{2}C.{2}F{2}.R	PS00031	Nuclear hormones receptors DNA-binding region signature.
GATA_ZN_FINGER_1	C.[DNEHQSTI]C.{4,6}[ST].{2}[WM][HR][RKENAMSLPGQT].{3,4}[GNEP].{3,6}C[NES][ASNR]C	PS00344	GATA-type zinc finger domain.
PARP_ZN_FINGER_1	C[KR].C.{3}I.[KAL].{3}[RG].{16,18}W[FYH]H.{2}C	PS00347	Poly(ADP-ribose) polymerase zinc finger domain signature.
ZN2_CY6_FUNGAL_1	[GASTPV]C.{2}C[RKHSTACW].{2}[RKHQ].{2}C.{5,12}C.{2}C.{6,8}C	PS00463	Zn(2)-C6 fungal-type DNA-binding domain signature.
ZF_DKSA_1	C[DESN].[CTS].{3}I.{3}[RK].{4}P.{4}[CSLAT].{2}[CAYF]	PS01102	Prokaryotic dksA C4-type zinc finger.
COPPER_FIST_1	M[LIVMFP][LIVMF]{2}.{3}[KN][MY]AC.{2}C[IL][KR].H[KR].{3}C.H.{8,9}[KR].[KRP]GRP	PS01119	Copper-fist DNA-binding domain signature.
BZIP_BASIC	[KR].{1,3}[RKSAQ]N[^VL].[SAQ]{2}[^L][RKTAENQ].R[^S][RK]	PS00036	Basic-leucine zipper (bZIP) domain signature.
MYB_1	W[ST][^W][^PTLN]E[DE][^GIYS][^GYPH][LIV]	PS00037	Myb DNA-binding domain repeat signature 1.
MYB_2	W.{2}[LI][SAG].{4,5}R[^RE].{3}[^AG].{3}[YW].{3}[LIVM]	PS00334	Myb DNA-binding domain repeat signature 2.
P53	MCNSSC[MV]GGMNRR	PS00348	p53 family signature.
NFYA_HAP2_1	[FY]VN[AS]KQ[FY].{2}I[ILM][KR]RR.{2}RAK[LA]E	PS00686	NF-YA/HAP2 subunit signature.
NFYB_HAP3	C[VA][ST]E.ISF[LIVM]T[SGC]EA[SCN][DE][KRQ]C	PS00685	NF-YB/HAP3 subunit signature.
COE	CSRCC[DE][KR]KSC	PS01345	COE family signature.
COLD_SHOCK	[FYKH]G[FL][IL].{6,7}[DER][LIVM][FQ].H.[STKR].[LIVMFYC]	PS00352	'Cold-shock' domain signature.
CTF_NFI_1	RKRKYFKKHEKR	PS00349	CTF/NF-I DNA-binding domain signature.
DM_1	C.{2}C.{2}H.{8}H.{3,4}C.{4}C.C.{2,3}C	PS40000	DM DNA-binding domain signature.
ETS_DOMAIN_1	L[FYW][QEDH]F[LI][LVQK][^N][LI]L	PS00345	Ets-domain signature 1.
ETS_DOMAIN_2	[RKHN].{2}M.Y[DENQ].[LIVM][STAG]R[STAG][LI]R.Y	PS00346	Ets-domain signature 2.
FORK_HEAD_1	[KR]P[PTQ][FYLVQH]S[FY].{2}[LIVM].{3,4}[AC][LIM]	PS00657	Fork head domain signature 1.
FORK_HEAD_2	W[QKR][NSD][SA][LIV]RH	PS00658	Fork head domain signature 2.
HSF_DOMAIN	L.{3}[FY]KH.N.[STAN]SF[LIVM]RQL[NH].Y.[FYW][RKH]K[LIVM]	PS00434	HSF-type DNA-binding domain signature.
IRF	W.[DNH].{5}[LIVF].[IV]PW.H.{9,10}[DE].{2}[LIVF]F[KRQ].[WR]A	PS00601	Tryptophan pentad repeat (IRF family) signature.
REL_1	FRY.CEG	PS01204	NF-kappa-B/Rel/dorsal domain signature.
MADS_BOX_1	[RGS].[RKS].{5}[IL].[DNGSK].{3}[KR].{2}T[FY].[RK]{3}.{2}[LIVM].[KE]K[AT].[EQ][LIVM][STA].L.{4}[LIVM].[LIVM][LIVMT][LIVM].{6}[LIVMFC].{2}[FYLS]	PS00350	MADS-box domain signature.
TBOX_1	[LQI]W.{2}[FCL].{3,4}[NT]E[MQ][LIVNM][LIV][TLF].{2}[GR][RG][KRQM]	PS01283	T-box domain signature 1.
TBOX_2	[LIVMFYWE]H[PADHL][DENQRS][GSE].{3}G.{2}[WL][ML].{3}[IVA].F	PS01264	T-box domain signature 2.
TEA_1	GRNELI.{2}[YH]I.{3}[TC].{3}RT[RK]{2}Q[LIVM]SSH[LIVM]QV	PS00554	TEA domain signature.
TFIIB	G[KR].{3}[STAGN][^S][LIVMYA][GSTA]{2}[CSAV][LIVM][LIVMFY][LIVMA][GSA][STAC]	PS00782	Transcription factor TFIIB repeat signature.
TFIID	Y.[PK].{2}[IF].{2}[LIVM]{2}.[KRH].{3}P[RKQ].{3}L[LIVM]F.[STN]G[KR][LIVMA].{3}G[TAGL][KR].{7}[AGCS].{7}[LIVMF]	PS00351	Transcription factor TFIID repeat signature.
TSC22	MDLVK.HL.{2}AVREEVE	PS01289	TSC-22 / dip / bun family signature.
GREAB_1	[EKH][LHVI].{9,10}[IVNLR].{3}[LIV].{6}GD.{2}EN[GSA].Y	PS00829	Prokaryotic transcription elongation factors signature 1.
GREAB_2	S.{2}S[PK][LIVMF][AG].[SAGNE][LIVM][LIVY].{4}[DNG][DE]	PS00830	Prokaryotic transcription elongation factors signature 2.
DEAD_ATP_HELICASE	[LIVMF]{2}DEAD[RKEN].[LIVMFYGSTN]	PS00039	DEAD-box subfamily ATP-dependent helicases signature.
DEAH_ATP_HELICASE	[GSAH].[LIVMF]{3}DE[ALIV]H[NECR]	PS00690	DEAH-box subfamily ATP-dependent helicases signature.
FIBRILLARIN	[GST][LIVMAPKR][IVEAT][FY][GSAC][IVL]E[FYV][SA].{0,1}[REA].{2}[RQSFT][DEK]	PS00566	Fibrillarin signature.
MCM_1	G[IVT][LVAC]{2}[IVT]D[DE][FL][DNST]	PS00847	MCM family signature.
XPA_1	C.[DE]C.{3}[LIVMF].{1,2}D.{2}L.{3}F.{4}C.{2}C	PS00752	XPA protein signature 1.
XPA_2	[LIVM]{2}T[KR]TE.K.[DE]Y[LIVMF]{2}.D.[DE]	PS00753	XPA protein signature 2.
XPG_1	[VILT][KREIT][PV].[FYIL][VI][FW]DG.{2}[PILHSTF].[LVCQMFAKS]K	PS00841	XPG protein signature 1.
XPG_2	[GSN][LIVM][PERD][FYSCV][LIVM].AP.EA[DE][PAS][QSE][CLM]	PS00842	XPG protein signature 2.
HTH_ARAC_FAMILY_1	[KRQ][LIVMA].{2}[GSTALIV][^FYWPGDN].{2}[LIVMSA].{4,9}[LIVMF].[^PLH][LIVMSTA][GSTACIL][^GPK][^F].[GANQRF][LIVMFY].{4,5}[LFY].{3}[FYIVA][^FYWHCM][^PGVI].{2}[GSADENQKR].[NSTAPKL][PARL]	PS00041	Bacterial regulatory proteins, araC family signature.
HTH_ARSR_1	C.{2}D[LIVM].{6}[ST].{4}S[HYR][HQ]	PS00846	Bacterial regulatory proteins, arsR family signature.
HTH_ASNC_1	[GSTAP].{2}[DNEQA][LIVM][GSA].{2}[LIVMFYT][GAN][LIVMST][ST].{6}R[LIVT].{2}[LIVM].{3}G	PS00519	AsnC-type HTH domain signature.
HTH_CRP_1	[LIVM][STAG][RHNWM].{2}[LIM][GA].[LIVMFYAS][LIVSC][GA].[STACN].{2}[MST].{1,2}[GSTN]R.[LIVMF].{2}[LIVMF]	PS00042	Crp-type HTH domain signature.
HTH_DEOR_1	R[^G].{2}[LIVM].{3}[LIVM].{16,17}[STA].{2}T[LIVMA][RH][KRNAQ]D[LIVMF]	PS00894	DeoR-type HTH domain signature.
HTH_LACI_1	[LIVM].[DE][LIVM]A.{2}[STAGV].V[GSTP].{2}[STAG][LIVMA].{2}[LIVMFYAN][LIVMC]	PS00356	LacI-type HTH domain signature.
HTH_LUXR_1	[GDC].{2}[NSTAVY].{2}[IV][GSTA].{2}[LIVMFYWCT].[LIVMFYWCR].{3}[NST][LIVM].{2}[^T].{2}[NRHSA][LIVMSTA].{2}[KR]	PS00622	LuxR-type HTH domain signature.
HTH_MARR_1	[STNAQ][LIAMV].{0,1}[RNGSYKE].{4,5}[LM][EIVLA].{2}[GESD][LFYWHA][LIVC].{7}[DNS][RKQG][RK].{6}[TS].{2}[GAS]	PS01117	MarR-type HTH domain signature.
HTH_MERR_1	[GSA].[LIVMFA][ASM].{2}[STACLIV][GSDENQR][LIVC][STANHK].{3}[LIVM][RHF].[YW][DEQ].{2,3}[GHDNQ][LIVMF]{2}	PS00552	MerR-type HTH domain signature.
HTH_RRF2_1	L.{3}[GRS][LIVY].{2}[STA].{2}G.{2}GG[FYIV].[LIF]	PS01332	Rrf2-type HTH domain signature.
HTH_TETR_1	[GS][LIVMFYSP].{2,3}[TS][LIVMTA].{2}[LIVM].{5}[LIVQSA][STAGENQH].[GPART].[LIVMFA][FYSTNRH].[HFYRA][FVW].[DNSTKAG][KQMT].{2,3}[LIVM]	PS01081	TetR-type HTH domain signature.
ANTITERMINATORS_BGLG	[ST][DM]H[LIC].{2}[FA][LIY][EQK]R.{2}[QNKA]	PS00654	Transcriptional antiterminators bglG family signature.
SIGMA54_1	P[LIVM].[LIVM].{2}[LIVM]A.{2}[LIVMFT].{2}[HS].ST[LIVM]SR	PS00717	Sigma-54 factors family signature 1.
SIGMA54_2	RRT[IV][ATN]KYR	PS00718	Sigma-54 factors family signature 2.
SIGMA70_1	[DE][LIVMF]{2}[HEQS].G.[LIVMFA]GL[LIVMFYE].[GSAM][LIVMAP]	PS00715	Sigma-70 factors family signature 1.
SIGMA70_2	[STN].{2}[DENQ][LIVMT][GAS].{4}[LIVMF][PSTG].{3}[LIVMA].[NQR][LIVMA][EQH].{3}[LIVMFWK].{2}[LIVM]	PS00716	Sigma-70 factors family signature 2.
SIGMA70_ECF	[STAIV][PQDEL][DE][LIV][LIVTA]Q.[STAV][LIVMFYC][LIVMAK].[GSTAIV][LIMFYWQ].{12,14}[STAP][FYW][LIF].{2}[IV]	PS01063	Sigma-70 factors ECF subfamily signature.
SIGMA54_INTERACT_1	[LIVMFY]{3}.G[DEQ][STE]G[STAV]GK.{2}[LIVMFY]	PS00675	Sigma-54 interaction domain ATP-binding region A signature.
SIGMA54_INTERACT_2	[GS].[LIVMFA].{2}[AS][DNEQASH][GNEKT]G[STIM][LIVMFY]{3}[DE][EK][LIVM]	PS00676	Sigma-54 interaction domain ATP-binding region B signature.
SIGMA54_INTERACT_3	[FYW]P[GS]N[LIVM]R[EQ]L.[NHAT]	PS00688	Sigma-54 interaction domain C-terminal part signature.
HISTONE_LIKE	[GSK]F.{2}[LIVMF].{4}[RKEQA].{2}[RST].{1,2}[GA].[KN]P.[TN]	PS00045	Bacterial histone-like DNA-binding proteins signature.
DPS_1	H[FW].[LIVM].G.{5}[LV]H.{3}[DE]	PS00818	Dps protein family signature 1.
DPS_2	[LIVMFY][DH].[LIVM][GA]ER.{3}[LIF][GDN].{2}[PA]	PS00819	Dps protein family signature 2.
RADC	HNHP[SQ]G	PS01302	DNA repair protein radC family signature.
RECA_1	AL[KR][IF][FY][STA][STAD][LIVMQ]R	PS00321	recA signature.
RECF_1	[LIFV].{6}[LIF][LIVF].[GSDE][GSTADNPE][PASG].{2}RR.[FYW][LIVMF][DN]	PS00617	RecF protein signature 1.
RECF_2	[LIV][LIFYMV].[LIVM]D[DEA][LIVF].{2}[EHCGK]LD.{2}[KRH].{3}[LIVF]	PS00618	RecF protein signature 2.
RECR	C.{2}C.{3,5}[STACD].{4}C.[LIVFQ]C.{4}[RD][NQDS]	PS01300	RecR protein signature.
SSRP	[STAC]G[LIVM].L.G.E[LIVM][KQ][SA][LIVMA]	PS01317	SsrA-binding protein.
HISTONE_H2A	[AC]GL.FPV	PS00046	Histone H2A signature.
HISTONE_H2B	[KR]E[LIVM][EQ]T.{2}[KR].[LIVM]{2}.[PAG][DE]L.[KR]HA[LIVM][STA]EG	PS00357	Histone H2B signature.
HISTONE_H3_1	KAPRK[QH][LI]	PS00322	Histone H3 signature 1.
HISTONE_H3_2	PF.[RA]L[VA][KRQ][DEG][IV]	PS00959	Histone H3 signature 2.
HISTONE_H4	GAKRH	PS00047	Histone H4 signature.
HMG_BOX_1	[FI]S[KR]KC.[EK]RWKT[MV]	PS00353	HMG box A DNA-binding domain signature.
HMGI_Y	[AT].{1,2}[RK]{2}[GP]RGRP[RK].	PS00354	HMG-I and HMG-Y DNA-binding domain (A+T-hook).
HMG14_17	[RQ]RSA[RS]LSA[RKM][PL]	PS00355	HMG14 and HMG17 signature.
BROMODOMAIN_1	[STANVFHG].{2}[FAS].{4}[DNSPAKT].{0,7}[DENQTFG]Y[HFYLRKT].{2}[LIVMFY].{3}[LIVM].{4}[LIVM].{6,10}Y.{12,13}[LIVM].{2}N[SACF].{2}[FY]	PS00633	Bromodomain signature.
CHROMO_1	[FYL].[LIVMC][KR]W.[GDNR][FYWLME].{5,6}[ST]W[ESV][PSTDEN].{2,3}[LIVMC]	PS00598	Chromo domain signature.
RCC1_1	G.ND.{2}[AV]LGR.T	PS00625	Regulator of chromosome condensation (RCC1) signature 1.
RCC1_2	[LIVMFA][STAGC]{2}G.[^TAV]H[STAGLI][LIVMFA][^KI][LIVM]	PS00626	Regulator of chromosome condensation (RCC1) signature 2.
PROTAMINE_P1	[AV]R[NFY]R.{2,3}[ST][^S]S[^NS]S	PS00048	Protamine P1 signature.
TP1	SKRKYRK	PS00541	Nuclear transition protein 1 signature.
TP2_1	H.{3}HS[NS]S.PQ[SG]	PS00970	Nuclear transition protein 2 signature 1.
TP2_2	K.RK.{2}EGK.{2}K[KR]K	PS00971	Nuclear transition protein 2 signature 2.
RIBOSOMAL_L1	[IMGV].{2}[LIVA].{2,3}[LIVMY][GAS].{2}[LMSF][GSNH][PTKR][KRAVG][GN].[LIMF]P[DENSTKQPRAGVI]	PS01199	Ribosomal protein L1 signature.
RIBOSOMAL_L2	P.{2}RG[STAIV]{2}.N[APK].[DE]	PS00467	Ribosomal protein L2 signature.
RIBOSOMAL_L3	[FL].{6}[DN].{2}[AGS].[ST].G[KRH]G.{2}G.{3}R	PS00474	Ribosomal protein L3 signature.
RIBOSOMAL_L5	[LIVM].{2}[LIVM][STAVC][GE][QV].{2}[LIVMA].[STC].[STAG][KRH].[STA]	PS00358	Ribosomal protein L5 signature.
RIBOSOMAL_L6_1	[PS][DENS].YK[GA]KG[LIVM]	PS00525	Ribosomal protein L6 signature 1.
RIBOSOMAL_L6_2	[QLS].{3}[LIVM].{2}[KRWYF].{2}R.F.DG[LIVM][YF][LIVM].{2}[KR]	PS00700	Ribosomal protein L6 signature 2.
RIBOSOMAL_L9	G.{2}[GNF].{4}[VAI].{2}G[FY].{2}[NH][FYWL]L.{5}[GA].{3}[STNG]	PS00651	Ribosomal protein L9 signature.
RIBOSOMAL_L10	[KNQ].{2}[^K].{3}[^A][^L].{9}[LIVMFY].{2}[DENHR].{2}[GS][LIVMF][STDNQC][VTA].[DENQKHPSA][LIVMSAD].{2}[LIMF][KR]	PS01109	Ribosomal protein L10 signature.
RIBOSOMAL_L11	[RKN].[LIVM].G[ST].{2}[SNQ][LIVM]G.[^M][LIVM].{0,1}[DENG]	PS00359	Ribosomal protein L11 signature.
RIBOSOMAL_L13	[LIVM][KRVLYFS][GKR]M[LIV][PST].{4,5}[GSKR][NQEKRAH].{5}[LIVM].[AIVL][LFYV].[GDNS]	PS00783	Ribosomal protein L13 signature.
RIBOSOMAL_L14	[GA][LIV]{3}.{9,10}[DNS]G.{4}[FY].{2}[NT].{2}V[LIV]	PS00049	Ribosomal protein L14 signature.
RIBOSOMAL_L15	[KR][LIVM]{2}[GASL].[GT].[LIVMA].{2,5}[LIVMF].[LIVMF].{3,4}[LIVMFCA][ST].{2}A.{3}[LIVM].{3}G	PS00475	Ribosomal protein L15 signature.
RIBOSOMAL_L16_1	[KRG][KR].[GSAC][KRQVA][LIVMK][WY][LIVM][KRN][LIVM][LFY][APK]	PS00586	Ribosomal protein L16 signature 1.
RIBOSOMAL_L16_2	RMG.[GR]KG.{4}[FWKR]	PS00701	Ribosomal protein L16 signature 2.
RIBOSOMAL_L17	[IL].[STV][GT].{2}[KR].[KRAF].{6}[DE].[LIMV][LIVMT][TE].[STAG][KR]	PS01167	Ribosomal protein L17 signature.
RIBOSOMAL_L19	[LIVMF].[KRGTIEQSN].[GSAIYN][KRQDAVLSIH][VGAIT][RSNAK].{0,1}[KRAQ][SAKG][KYR][KLI][LYSFT][YF][LIM][RK]	PS01015	Ribosomal protein L19 signature.
RIBOSOMAL_L20	K.{3}[KRCV].[LIVM]W[IVN][STNALVQCMI][RH][LIVM][NS].{3}[RKHSG]	PS00937	Ribosomal protein L20 signature.
RIBOSOMAL_L21	[IVTL].{3}[KR].{3}[KRQ][KT].{6}G[HFY][RK][RQT].{2}[STL]	PS01169	Ribosomal protein L21 signature.
RIBOSOMAL_L22	[RKQN].{2}[^G].[RH][GAS].G[KRQS].{8}[^L][HDN][LIVM][^A][LIVMS].[LIVM]	PS00464	Ribosomal protein L22 signature.
RIBOSOMAL_L23	[RK]{2}[AM][IVFYT][IV][RKT]L[STANEQK].{7}[LIVMFT]	PS00050	Ribosomal protein L23 signature.
RIBOSOMAL_L24	[GDEN]D.[IV].[IV][LIVMA].G.{2}[KRA][GNQK].{2,3}[GA].[IV]	PS01108	Ribosomal protein L24 signature.
RIBOSOMAL_L27	G.[LIVM]{2}.RQRG.{5}G	PS00831	Ribosomal protein L27 signature.
RIBOSOMAL_L29	[KNQS][PSTLNH][^D][^F][LIVMFA][KRGSADN].[LIVYSTA][KR][KRHQS][DESTANQRL][LIV]A[KRCQVT][LIVMA]	PS00579	Ribosomal protein L29 signature.
RIBOSOMAL_L30	[IVTAS][LIVM].{2}[LF].[LI].[KRHQEG].{2}[STNQH].[IVTR].{10}[LMSN][LIV].{2}[LIVA].{2}[LMFY][IVT]	PS00634	Ribosomal protein L30 signature.
RIBOSOMAL_L31	[DES][IVT].{4}H[PT][FAVY][FYW][TISN].{9,13}[GN][KRHNQ]	PS01143	Ribosomal protein L31 signature.
RIBOSOMAL_L33	[YW].[STKV].[KR][NSKQ].{3,4}[PATQS].{1,2}[LIVMF][EAQVSIT].{2}K[FYH][CSD]	PS00582	Ribosomal protein L33 signature.
RIBOSOMAL_L34	[KFQ][RGMP][TN][FYWL][EQSG].{5}[KRHS].{4,5}GF.{2}R	PS00784	Ribosomal protein L34 signature.
RIBOSOMAL_L35	K[STNV][^F].[GSAM][SAILV].[KRA]R[IVFY].{14,16}[GSANQKR]H	PS00936	Ribosomal protein L35 signature.
RIBOSOMAL_L36	[CHDS].{2}[CND].{2}[LIVM].R.{3}[LIVMNR].[LIVM].[CN].{3,4}[KRSN][HLFR].[QCAV].Q	PS00828	Ribosomal protein L36 signature.
RIBOSOMAL_L1E	[NP].{3}[KRM].{2}A[LIVTK].[SA][AC][LIV].[ALCM][STL][SGAKTI].{7}[RK][GS]H	PS00939	Ribosomal protein L1e signature.
RIBOSOMAL_L6E	N.{2}P[LI]RR.{4}[FY]VIATS.{1,2}K	PS01170	Ribosomal protein L6e signature.
RIBOSOMAL_L7AE	[CAS].{4}[IVA]P[FY].{2}[LIVM].[GSQNK][KRQ].{2}LG	PS01082	Ribosomal protein L7Ae signature.
RIBOSOMAL_L10E	ADR.{3}[GR][MH]R.[SAP][FYW]G[KRVTS][PANI].[GS].{1,2}A[KRLV][LIVQ]	PS01257	Ribosomal protein L10e signature.
RIBOSOMAL_L13E	[KR].G[KR]GF[ST][LVF].E[LVI].{3}G	PS01104	Ribosomal protein L13e signature.
RIBOSOMAL_L15E	[DENT][KR][AL]R.[LIQ]G[FY].[SAP].{2}G[LIVMFY][LIVMFYKS][LIVMFY][LIVMFYA]R.[RNAS][IVL].[KRC]G	PS01194	Ribosomal protein L15e signature.
RIBOSOMAL_L18E	[KREWDI].L.{2}[PSRG][KRS].{2}[RHY][PSA].[LIVM][NSA][LIVM].[RK][LIVM]	PS01106	Ribosomal protein L18e signature.
RIBOSOMAL_L19E	Q[KR]R[LIVM].[SA].{4}[CV]G.{3}[IV][WK][LIVF][DNSV][PE]	PS00526	Ribosomal protein L19e signature.
RIBOSOMAL_L21E	[GN][DEQ].V.{10,11}[GVT].{2}[FYHDN].{2}[FY].G.[TV]G	PS01171	Ribosomal protein L21e signature.
RIBOSOMAL_L24E	[FY].[GSHE].{2}[IVLF].P[GA].G.{2}[FYV].[KRHE].D	PS01073	Ribosomal protein L24e signature.
RIBOSOMAL_L27E	GK[NS].WFF.{2}L[RH]F$	PS01107	Ribosomal protein L27e signature.
RIBOSOMAL_L30E_1	[STA].{5}G.[QKRN].{2}[LIVMQ][KRQT].{2}[KR].[GS].{2}[KQ].[LIVM]{3}	PS00709	Ribosomal protein L30e signature 1.
RIBOSOMAL_L30E_2	[DE][LM]G[STALPD].{2}[GK][KR].{6}[LIVM].[LIVM].[DEN].[GI]	PS00993	Ribosomal protein L30e signature 2.
RIBOSOMAL_L31E	[VI][KRWVI][LIV][DSAG].{2}[LIV][NS].[AKQEHFYLCT].W.[KRQE][GS]	PS01144	Ribosomal protein L31e signature.
RIBOSOMAL_L32E	F.R.{4}[KRL].{2}[KRT][LIVMFT].{3,5}WR[KR].{2}G	PS00580	Ribosomal protein L32e signature.
RIBOSOMAL_L34E	[ST].S.[KRQ].{4}[KR][TL]P[GS][GN]	PS01145	Ribosomal protein L34e signature.
RIBOSOMAL_L35AE	G[KT][LIVM].[RD].HG.{2}G.V.[AVS].F.{3}[LI]P	PS01105	Ribosomal protein L35Ae signature.
RIBOSOMAL_L36E	PYE[KR]R.[LIVMT][DE][LIVM]{2}[KR]	PS01190	Ribosomal protein L36e signature.
RIBOSOMAL_L37E	GT.[SAVTP].G.[KRHIM].{4,5}H.{2}C.RCG	PS01077	Ribosomal protein L37e signature.
RIBOSOMAL_L39E	[KRM][PTKS].{3}[LIVMFG].{2}[NHS].{3}R[DNHY]WR[RS]	PS00051	Ribosomal protein L39e signature.
RIBOSOMAL_L44E	K.[TV]KK.{2}L[KR].{2}C	PS01172	Ribosomal protein L44e signature.
RIBOSOMAL_S2_1	[LIVMFA].[^GPRV][LIVMFYC]{2}[^LPC][STAC][GSTANQEKR][STALV][HY][LIVMF]G	PS00962	Ribosomal protein S2 signature 1.
RIBOSOMAL_S2_2	P.{2}[LIVMF]{2}[LIVMS].[GDN].{3}[DENL].{3}[LIVM].E.{4}[GNQKRH][LIVM][AP]	PS00963	Ribosomal protein S2 signature 2.
RIBOSOMAL_S3	[GSTA][KR].{6}G.[LIVMT].{2}[NQSCH].{1,3}[LIVFCA].{3}[LIV][DENQ].{7}[LMT].{2}G.{2}[GS]	PS00548	Ribosomal protein S3 signature.
RIBOSOMAL_S4	[LIVM][DERA].R[LI].{3}[LIVMC][VMFYHQL][KRTS].{3}[STAGCVF].[ST].{3}[SAI][KRQ].[LIVMF]{2}	PS00632	Ribosomal protein S4 signature.
RIBOSOMAL_S5	G[KRQEA].{3}[FYVIM].[ACVTI].{2}[LIVMA][LIVMAT][AG][DN].{2,3}G.[LIVMA][GS].[SAG].{5,6}[DEQGHS][LIVMARFY].{2,3}[AS][LIVMFRY]	PS00585	Ribosomal protein S5 signature.
RIBOSOMAL_S6	G.[KRC][DENQRH]L[SA]Y.I[KRNSA]	PS01048	Ribosomal protein S6 signature.
RIBOSOMAL_S7	[DENSK].[LIVMDET].{3}[LIVMFTA]{2}.{6}GK[KR].{5}[LIVMF][LIVMFC].{2}[STAC]	PS00052	Ribosomal protein S7 signature.
RIBOSOMAL_S8	[GE].{2}[LIV]{2}[STY][ST][^A].G[LIVM]{2}.{4}[AG][KRHAYIL]	PS00053	Ribosomal protein S8 signature.
RIBOSOMAL_S9	[GS]GG.{2}[GSA][QK].{2}[SA].{3}[GSA].[GSTAV][KR][GSALVD][LIFV]	PS00360	Ribosomal protein S9 signature.
RIBOSOMAL_S10	[AV].{3}[GDNSR][LIVMSTAG].{3}GP[LIVM].[LIVM]PT	PS00361	Ribosomal protein S10 signature.
RIBOSOMAL_S11	[LIVMFR].[GSTACQI][LIVMF].{1,2}[GSTALVM].{0,1}[GSN][LIVMFY].[LIVM].{4}[DEN].[TS][PS].[PA][STCHF][DN]	PS00054	Ribosomal protein S11 signature.
RIBOSOMAL_S12	[RK].PNS[AR].R	PS00055	Ribosomal protein S12 signature.
RIBOSOMAL_S13_1	[KRQSEAT][GS].RH.{2}[GSNHKLCD].{2}[LIVMCT][RNH]GQ	PS00646	Ribosomal protein S13 signature.
RIBOSOMAL_S14	[RP].{0,1}C.{11,12}[LIVMF][^L][LIVMF][SC][RG].[^D][^PK][RN]	PS00527	Ribosomal protein S14 signature.
RIBOSOMAL_S15	[LIVM].{2}H[LIVMFY].{3}[^S].D.{2}[STAGN].{3}[LF].{2}[^A].{6}[LIVM].{2}[FY]	PS00362	Ribosomal protein S15 signature.
RIBOSOMAL_S16	[LIVMT].[LIVM][KR]L[STAK]R[^E]G[AKR]	PS00732	Ribosomal protein S16 signature.
RIBOSOMAL_S17	GD.[LIV].[LIVA].[QEK].[RK]P[LIV]S	PS00056	Ribosomal protein S17 signature.
RIBOSOMAL_S18	[IVRLP][DYN][YLF].{2,3}[LIVMTPFS].{2}[LIVM].{2}[FYTS][LIVMT][STNQG][DERPN].{1,2}[GYAH][KCR][LIVM].{3}[RHG][LIVMASR]	PS00057	Ribosomal protein S18 signature.
RIBOSOMAL_S19	[STDNQ]G[KRNQMHSI].{6}[LIVM].{4}[LIVMC][GSD].{2}[LFI][GAS][DE][FYM].{2}[ST]	PS00323	Ribosomal protein S19 signature.
RIBOSOMAL_S21	[DE].A[LIY][KR][RA][FL]K[KR].{3}[KR]	PS01181	Ribosomal protein S21 signature.
RIBOSOMAL_S3AE	[LIV].[GHN]R[IVNT].E.[SCT][LV].[DE][LVI]	PS01191	Ribosomal protein S3Ae signature.
RIBOSOMAL_S4E	H.KR[LIVMF][SANK].P.{2}[WY].[LIVM].[KRP]	PS00528	Ribosomal protein S4e signature.
RIBOSOMAL_S6E	[LIVM][STAMR]GG.[DG].{2}G.[PV]M	PS00578	Ribosomal protein S6e signature.
RIBOSOMAL_S7E	[KRAI]L.RELEKKF[SAPQG].[KRN][HED]	PS00948	Ribosomal protein S7e signature.
RIBOSOMAL_S8E	[KR].{2}[ST]G[GAR].{5,6}[KRHSA].[KRT].[KR].[EA][LIMPA]G	PS01193	Ribosomal protein S8e signature.
RIBOSOMAL_S12E	[SA][LI][KREQP].{2}[LIVM].{2}[SA].{3}[DNG]G[LIV].{2}G[LIV]	PS01189	Ribosomal protein S12e signature.
RIBOSOMAL_S17E	[IVDYPKS].[ST]K.[LIVMTF][RIKM]N.[IV][SA]G[FY].[TV][HKRA]	PS00712	Ribosomal protein S17e signature.
RIBOSOMAL_S19E	P.{6}[SANG].{2}[LIVMAC].R.[ALIV][LV][QH].L[EQ]	PS00628	Ribosomal protein S19e signature.
RIBOSOMAL_S21E	[LV]Y[IVC]PRKCS[SAT]	PS00996	Ribosomal protein S21e signature.
RIBOSOMAL_S24E	[FYAT]G.{2}[KREILYV][STAI].[GCAVI][FYKRE][GTALV].[LIVYA]Y[DENQAKY][STDN].{7}E	PS00529	Ribosomal protein S24e signature.
RIBOSOMAL_S26E	[YH]C[VI][SA]CAIH	PS00733	Ribosomal protein S26e signature.
RIBOSOMAL_S27E	[QKRTE]C.{2}C.{6}F[GSDA].[PSA].{5}C.{2}C[GSAQ].{2}[LIV].{2}[PS].G	PS01168	Ribosomal protein S27e signature.
RIBOSOMAL_S28E	E[ST][EA]REA[RK].[LI]	PS00961	Ribosomal protein S28e signature.
DNA_MISMATCH_REPAIR_1	GFRGE[AG]L	PS00058	DNA mismatch repair proteins mutL / hexB / PMS1 signature.
DNA_MISMATCH_REPAIR_2	[STA][LIVMF].[LIVM].DE[LIVMFY][GCA][RKHAS][GS][GST].{4}G	PS00486	DNA mismatch repair proteins mutS family signature.
NUDIX	G.{5}E.{4}[TAGCV][LIVMACF].R[EL][LIVMFGSTA].[EA]E.[GNDTHR]	PS00893	Nudix hydrolase signature.
DNAA	[IL][GA].{2}[LIVMF][SGADENK].{0,1}[KR].H[STPA][STAV][LIVM].{2}[SGAMN].{3}[LIVM]	PS01008	DnaA protein signature.
SASP_1	K.E[LIV]A.[DE][LIVMF]G[LIVMF]	PS00304	Small, acid-soluble spore proteins, alpha/beta type, signature 1.
SASP_2	[KRC][SAQ].G.[VF]G[GA].[LIVM].[KR][KRC][LIVM]{2}	PS00684	Small, acid-soluble spore proteins, alpha/beta type, signature 2.
ADH_ZINC	GHE.[^EL]G[^AP].{4}[GA].{2}[IVSAC]	PS00059	Zinc-containing alcohol dehydrogenases signature.
QOR_ZETA_CRYSTAL	[GSDN][DEQHKM].{2}L.{3}[SAG]{2}GG.G.{4}Q.{2}[KRS]	PS01162	Quinone oxidoreductase / zeta-crystallin signature.
ADH_IRON_1	[STALIV][LIVF].[DE].{6,7}P.{4}[ALIV].[GST].{2}D[TAIVM][LIVMF].{4}E	PS00913	Iron-containing alcohol dehydrogenases signature 1.
ADH_IRON_2	[GSW].[LIVTSACD][GH].{2}[GSAE][GSHYQ].[LIVTP][GAST][GAS].{3}[LIVMT].[HNS][GA].[GTAC]	PS00060	Iron-containing alcohol dehydrogenases signature 2.
ADH_SHORT	[LIVSPADNK].{9}[^P].{2}Y[PSTAGNCV][STAGNQCIVM][STAGC]K[^PC][SAGFYR][LIVMSTAGD].[^K][LIVMFYW][^D].[^YR][LIVMFYWGAPTHQ][GSACQRHM]	PS00061	Short-chain dehydrogenases/reductases family signature.
ALDOKETO_REDUCTASE_1	G[FY]R[HSAL][LIVMF]D[STAGCL][AS].{5}[EQ].{2}[LIVMCA][GS]	PS00798	Aldo/keto reductase family signature 1.
ALDOKETO_REDUCTASE_2	[LIVMFY].{8}[^L][KREQ][^K][LIVM]G[LIVM][SC]N[FY]	PS00062	Aldo/keto reductase family signature 2.
ALDOKETO_REDUCTASE_3	[LIVM][PAIV][KR][ST][^EPQG][^RFI].{2}R[^SVAF].[GSTAEQK][NSL].[^LVRI][LIVMFA]	PS00063	Aldo/keto reductase family putative active site signature.
HOMOSER_DHGENASE	A.{3}G[LIVMFY][STAG].{2,3}[DNS]P.{2}D[LIVM].G.D.{3}K	PS01042	Homoserine dehydrogenase signature.
NAD_G3PDH	[GSA][ATIVS][LIVMYCAFST]K[DN][LIVMA][LIVMFYT][GA].[GACKMSIFT].G[ALIVMF].{2}[SGAQ][LIVMYERAKQFS].{0,1}[TLIVMFYWAQ][ETGAS].{0,1}[NDVS]	PS00957	NAD-dependent glycerol-3-phosphate dehydrogenase signature.
FAD_G3PDH_1	[IV]GGG.{2}G[STACV]G.[AT].[DQ].{3}[RAS]G	PS00977	FAD-dependent glycerol-3-phosphate dehydrogenase signature 1.
FAD_G3PDH_2	GGK.{2}[GSTE]YR.{2}A	PS00978	FAD-dependent glycerol-3-phosphate dehydrogenase signature 2.
MANNITOL_DHGENASE	[LIVMY].[FS].{2}[STAGCV].VDR[IV].[PS]	PS00974	Mannitol dehydrogenases signature.
HISOL_DEHYDROGENASE	[IVTPM][DEG].{2,3}[AYEPQ]G[PT][ST][ED][LIVSTA][LIVMAECGFT][LIVMA][LIVMAYF][ACNDSTI].{2,3}[ACNGVST].{4,6}[LIVMAC][AVLKIT][SACLYWNRMTV][DEG][LIVMFCA][LIVMKFR][SAGVI].{2}EH	PS00611	Histidinol dehydrogenase signature.
L_LDH	[LIVMA]G[EQ]HG[DN][ST]	PS00064	L-lactate dehydrogenase active site.
D_2_HYDROXYACID_DH_1	[LIVMA][AG][IVT][LIVMFY][AG].G[NHKRQGSAC][LIV]G.{13,14}[LIVMFT][^A].[FYWCTH][DNSTK]	PS00065	D-isomer specific 2-hydroxyacid dehydrogenases NAD-binding signature.
D_2_HYDROXYACID_DH_2	[LIVMFYWA][LIVFYWC].{2}[SAC][DNQHR][IVFA][LIVF].[LIVF][HNI].P.{4}[STN].{2}[LIVMF].[GSDN]	PS00670	D-isomer specific 2-hydroxyacid dehydrogenases signature 2.
D_2_HYDROXYACID_DH_3	[LMFATCYV][KPQNHAR].[GSTDNK].[LIVMFYWRC][LIVMFYW]{2}N.[STAGC]R[GP].[LIVH][LIVMCT][DNVE]	PS00671	D-isomer specific 2-hydroxyacid dehydrogenases signature 3.
3_HYDROXYISOBUT_DH	[LIVMFY]{2}GLG.[MQ]G.{2}[MA][SAV].[SNHR]	PS00895	3-hydroxyisobutyrate dehydrogenase signature.
HMG_COA_REDUCTASE_1	[RKH].[^Y][^I].[^I][^L]D.MG.N.[LIVMA]	PS00066	Hydroxymethylglutaryl-coenzyme A reductases signature 1.
HMG_COA_REDUCTASE_2	[LIVM]G.[LIVM]GG[AG]T	PS00318	Hydroxymethylglutaryl-coenzyme A reductases signature 2.
HMG_COA_REDUCTASE_3	A[LIVM].[STAN].{2}[LI].[KRNQ][GSA]H[LM].[FYLH]	PS01192	Hydroxymethylglutaryl-coenzyme A reductases signature 3.
3HCDH	[DNES].{2}[GA]F[LIVMFYA].[NT]R.{3}[PA][LIVMFY][LIVMFYST].{5,6}[LIVMFYCT][LIVMFYEAH].{2}[GVE]	PS00067	3-hydroxyacyl-CoA dehydrogenase signature.
MDH	[LIVM]T[TRKMN]LD.{2}R[STA].{3}[LIVMFY]	PS00068	Malate dehydrogenase active site signature.
MALIC_ENZYMES	[FM].[DV]D.{2}[GS]T[GSA].[IV].[LIVMAT][GAST][GASTC][LIVMFA][LIVMFY]	PS00331	Malic enzymes signature.
IDH_IMDH	[NSK][LIMYTV][FYDNH][GEA][DNGSTY][IMVYL].[STGDN][DN].{1,2}[SGAP].{3,4}[GE][STG][LIVMPA][GA][LIVMF]	PS00470	Isocitrate and isopropylmalate dehydrogenases signature.
6PGD	[LIVM].[DG].{2}[GAEHS][NQSD][KS]G[TE]G.W	PS00461	6-phosphogluconate dehydrogenase signature.
G6P_DEHYDROGENASE	DH[YF]LGK[EQK]	PS00069	Glucose-6-phosphate dehydrogenase active site.
IMP_DH_GMP_RED	[LIVMT][RK][LIVM]G[LIVM]G.G[SRK][LIVMAT]C.T	PS00487	IMP dehydrogenase / GMP reductase signature.
BACTERIAL_PQQ_1	[DEN][WV].{3}G[RKNM].{6}[FYW][SV].{4}[LIVM]N.{2}NV.{2}L[RKT]	PS00363	Bacterial quinoprotein dehydrogenases signature 1.
BACTERIAL_PQQ_2	W.{4}[YF]D.{3}[DN][LIVMFYT][LIVMFY]{3}.{2}G.{2}[STAG][PVT]	PS00364	Bacterial quinoprotein dehydrogenases signature 2.
FMN_HYDROXY_ACID_DH	SNHG[AG]RQ	PS00557	FMN-dependent alpha-hydroxy acid dehydrogenases active site.
GMC_OXRED_1	[GA][RKNC].[LIVW]G{2}[GST]{2}.[LIVM][NH].{3}[FYWA].{2}[PAG].{5}[DNESHQA]	PS00623	GMC oxidoreductases signature 1.
GMC_OXRED_2	[GS][PSTA].{2}[ST][PS].[LIVM]{2}.{2}SG[LIVM]G	PS00624	GMC oxidoreductases signature 2.
MOLYBDOPTERIN_EUK	[GA][^A].{2}[KRNQHT].{11,14}[LIVMFYWS].{3}[^V][^GK].{3}[LIVMF].C.{2}[DEN]R.{2}[DE]	PS00559	Eukaryotic molybdopterin oxidoreductases signature.
MOLYBDOPTERIN_PROK_1	[STAN].[CH].{2,3}C[STAG][GSTVMF].C.[LIVMFYW].[LIVMA].{3,4}[DENQKHT]	PS00551	Prokaryotic molybdopterin oxidoreductases signature 1.
MOLYBDOPTERIN_PROK_2	[STA].[STAC]{2}.{2}[STA]D[LIVMY]{2}LP.[STAC]{2}.{2}E	PS00490	Prokaryotic molybdopterin oxidoreductases signature 2.
MOLYBDOPTERIN_PROK_3	A.{3}[GDTN][IF].[DNQTKEH].[DEAQ].[LIVM].[LIVMC].[NS].{2}[GS].{4,5}[AV].[LIVMEF][STY]	PS00932	Prokaryotic molybdopterin oxidoreductases signature 3.
ALDEHYDE_DEHYDR_GLU	[LIVMFGA]E[LIMSTAC][GS]G[KNLM][SADN][TAPFV]	PS00687	Aldehyde dehydrogenases glutamic acid active site.
ALDEHYDE_DEHYDR_CYS	[FYLVA].[^GVEP][^DILV]G[QE][^LPYG]C[LIVMGSTANC][AGCN][^HE][GSTADNEKR]	PS00070	Aldehyde dehydrogenases cysteine active site.
ASD	[LIVM][SADN].{2}C.R[LIVM].{4}[GSC]H[STA]	PS01103	Aspartate-semialdehyde dehydrogenase signature.
GAPDH	[ASV]SC[NT]T[^S].[LIM]	PS00071	Glyceraldehyde 3-phosphate dehydrogenase active site.
ARGC	[LIVMA][GSA].[PA]GC[FYN][AVPST]T[GSACVT].{3}[GTACLPS][LIVMCAF].[PL]	PS01224	N-acetyl-gamma-glutamyl-phosphate reductase active site.
PROA	[VA].{5}A[LIVAMTCK].[HWFY][IM].{2}[HYWNRFT][GSNT][STAG].{0,1}H[ST][DE].{1,2}I	PS01223	Gamma-glutamyl phosphate reductase signature.
DAPB	[ERND][IVL].[ED].H.{3}K.[DE].{2}S[GA][TAS][ALCGS]	PS01298	Dihydrodipicolinate reductase signature.
STEROL_REDUCT_1	G.{2}[LIVMF][IFYH][DN].[FYWM].G.{2}[LF][NY]P[RQ]	PS01017	Sterol reductase family signature 1.
STEROL_REDUCT_2	[LIVM]{2}[LIVMFT][HWD]R.{2}RD.{3}C.{2}KY[GK].{2}[FW].{2}Y	PS01018	Sterol reductase family signature 2.
DHODEHASE_1	[GSA].{4}[GK][GSTA][LIVFSTA][GST].{3}[NQRK].G[NHY].{2}P[RTV]	PS00911	Dihydroorotate dehydrogenase signature 1.
DHODEHASE_2	[LIVM]{2}[GSA].GG[IV].[STGDN].{3}[ACV].{2}[^A][^R].[^L]GA	PS00912	Dihydroorotate dehydrogenase signature 2.
COPROGEN_OXIDASE	K.[WQA][CA].{2}[FYH]{2}.[LIVM].[HY]R.E.RG[LIVMT]GG[LIVM]F[FY]D	PS01021	Coproporphyrinogen III oxidase signature.
FRD_SDH_FAD_BINDING	R[ST]H[ST].{2}A.GG	PS00504	Fumarate reductase / succinate dehydrogenase FAD-binding site.
ACYL_COA_DH_1	[GAC][LIVM][ST]E.{2}[GSAN]G[ST]D.{2}[GSA]	PS00072	Acyl-CoA dehydrogenases signature 1.
ACYL_COA_DH_2	[QDE].[^P]G[GS].G[LIVMFY].{2}[DEN].{4}[KR].{3}[DEN]	PS00073	Acyl-CoA dehydrogenases signature 2.
ALADH_PNT_1	[GA][LIVM][PKV].{0,1}E.{3}[NG]E.{1,3}R[VT][AG].[ST]P.[GSTVN][VA].{2}[LI].[KRHNGSED].G	PS00836	Alanine dehydrogenase & pyridine nucleotide transhydrogenase signature 1.
ALADH_PNT_2	[LIVM][LIVMF]G[GAV]G.[AV][GA].{2}[SA].{3}[GA].[SGR][LIVM][GN]A.V.{3}[DE]	PS00837	Alanine dehydrogenase & pyridine nucleotide transhydrogenase signature 2.
GLFV_DEHYDROGENASE	[LIV].{2}GG[SAG]K.[GV].{3}[DNST][PL]	PS00074	Glu / Leu / Phe / Val dehydrogenases active site.
DAO	[LIVM]{2}H[NHA]YG.[GSA]{2}.G.{5}G.A	PS00677	D-amino acid oxidases signature.
PYRIDOX_OXIDASE	[LIVFMYWA]EFW[QHGENRAMVYLCF].{4}[RW][LIVMK][HN][DNESIT]R	PS01064	Pyridoxamine 5'-phosphate oxidase signature.
COPPER_AMINE_OXID_1	[LIVM][LIVMA][LIVMF].{4}[ST].{2}NY[DE][YN]	PS01164	Copper amine oxidase topaquinone signature.
COPPER_AMINE_OXID_2	T.[GS].{2}H[LIVMF].{3}E[DE].P	PS01165	Copper amine oxidase copper-binding site signature.
LYSYL_OXIDASE	W.WH.CH.H[YN]HS[MI][DE]	PS00926	Lysyl oxidase putative copper-binding region signature.
P5CR	[PALF].{2,3}[LIV].{3}[LIVM][STAC][STV].[GANK]G.T.{2}[AG][LIV].{2}[LMF][DENQK]	PS00521	Delta 1-pyrroline-5-carboxylate reductase signature.
DHFR	[LVAGC][LIF]G.{4}[LIVMF]PW.{4,5}[DE].{3}[FYIV].{3}[STIQ]	PS00075	Dihydrofolate reductase signature.
THF_DHG_CYH_1	[EQLT].[EQKDS][LIVM]{2}.{2}[LIVM].{2}[LIVMY]N.[DNS].{5}[LIVMF]{3}Q[LM]P[LVI]	PS00766	Tetrahydrofolate dehydrogenase/cyclohydrolase signature 1.
THF_DHG_CYH_2	P[GK]G[VI]GP[MFI]T[IVA]	PS00767	Tetrahydrofolate dehydrogenase/cyclohydrolase signature 2.
OX2_COVAL_FAD	P.{7}[^L].{2}[DE][LIVM].{3}[LIVM].{9,12}[LIVM].{3}[GSA][GSTCHRQ]GH	PS00862	Oxygen oxidoreductases covalent FAD-binding site.
PYRIDINE_REDOX_1	GG.C[LIVA].{2}GC[LIVM]P	PS00076	Pyridine nucleotide-disulphide oxidoreductases class-I active site.
PYRIDINE_REDOX_2	C.{2}CD[GAS].{2,4}[FYA].{4}[LIVMAT].{0,1}[LIVM]{2}[GI][GDS][GRD][DN]	PS00573	Pyridine nucleotide-disulphide oxidoreductases class-II active site.
COMPLEX1_ND1_1	G[LIVMFYKRSAQT][LIVMAGPF][QAM].[LIVMFYCA].D[AGIM][LIVMFTA][KS][LVMYSTI][LIVMFYGA].[KRE][EQG]	PS00667	Respiratory-chain NADH dehydrogenase subunit 1 signature 1.
COMPLEX1_ND1_2	PFD[LIVMFYQN][STAGPVMI]E[GACS]E.{0,2}[EQLN][LIVMS].{1,2}G	PS00668	Respiratory-chain NADH dehydrogenase subunit 1 signature 2.
COMPLEX1_20K	[GN].[DE][KRHST][LIVMFA][LIVMF]P[IV]D[LIVMFYWA][LIVMFYWK].P.CP[PT]	PS01150	Respiratory-chain NADH dehydrogenase 20 Kd subunit signature.
COMPLEX1_24K	[DKG].{2}[FLV][STKD].{5}C[LMNQ][GA].C.{2}[GA]P	PS01099	Respiratory-chain NADH dehydrogenase 24 Kd subunit signature.
COMPLEX1_30K	ERE.{2}[DE][LIVMFY]{2}.{6}[HK].{3}[KRP].[LIVM][LIVMYS]	PS00542	Respiratory chain NADH dehydrogenase 30 Kd subunit signature.
COMPLEX1_49K	[LIVMH]H[RT][GA].EK[LIVMTN].E.[KRQ]	PS00535	Respiratory chain NADH dehydrogenase 49 Kd subunit signature.
COMPLEX1_51K_1	G[AM]G[AR]Y[LIVM]CG[DE]{2}[STA]{2}[LIM]{2}[END]S	PS00644	Respiratory-chain NADH dehydrogenase 51 Kd subunit signature 1.
COMPLEX1_51K_2	E[ST]CG.C.PCR.G	PS00645	Respiratory-chain NADH dehydrogenase 51 Kd subunit signature 2.
COMPLEX1_75K_1	P.{2}C[YWSD].{7}[GA].CR.C	PS00641	Respiratory-chain NADH dehydrogenase 75 Kd subunit signature 1.
COMPLEX1_75K_2	CP.C[DE].[GS]{2}.C.LQ	PS00642	Respiratory-chain NADH dehydrogenase 75 Kd subunit signature 2.
COMPLEX1_75K_3	RC[LIVM].C.RC[LIVMT].[LMFY]	PS00643	Respiratory-chain NADH dehydrogenase 75 Kd subunit signature 3.
NIR_SIR	[STVN]GC.{3}C.{6}[DE][LIVMF][GAT][LIVMF]	PS00365	Nitrite and sulfite reductases iron-sulfur/siroheme-binding site.
URICASE	[LV].[LV][LIV]K[STV][ST].[SN].F.{2}[FY].{4}[FY].{2}L.{5}R	PS00366	Uricase signature.
COX1_CUB	[YWG][LIVFYWTA]{2}[VGS]H[LNP].V.{44,47}HH	PS00077	Heme-copper oxidase catalytic subunit, copper B binding region signature.
COX2	V.H.{33,40}C.{3}C.{3}H.{2}M	PS00078	CO II and nitrous oxide reductase dinuclear copper centers signature.
COX5B	[LIVM]{2}[FYW].{10}C.{2}CG.{2}[FY]KL	PS00848	Cytochrome c oxidase subunit Vb, zinc binding region signature.
COX6A	[LIV]R.K.[FYW].W[GS]DG.[KH][ST].F.N	PS01329	Cytochrome c oxidase subunit VIa signature.
MULTICOPPER_OXIDASE1	G.[FYW].[LIVMFYW].[CST].[^PR][^K].{2}[^S].[^LFH]G[LM].{3}[LIVMFYW]	PS00079	Multicopper oxidases signature 1.
MULTICOPPER_OXIDASE2	HCH.{3}H.{3}[AG][LM]	PS00080	Multicopper oxidases signature 2.
PEROXIDASE_1	[DET][LIVMTA][^NSYL][^RPFC][LIVM][LIVMSTAG][SAG][LIVMSTAG]H[STA][LIVMFY]	PS00435	Peroxidases proximal heme-ligand signature.
PEROXIDASE_2	[SGATV][^D].{2}[LIVMA]R[LIVMA].[FW]H[^V][SAC]	PS00436	Peroxidases active site signature.
CATALASE_1	R[LIVMFSTAN]F[GASTNP]Y.D[AST][QEH]	PS00437	Catalase proximal heme-ligand signature.
CATALASE_2	[IF].[RH].{4}[EQ]R.{2}H.{2}[GAS][GASTFY][GAST]	PS00438	Catalase proximal active site signature.
GLUTATHIONE_PEROXID_1	[GNDRC][RKHNQFYCS].[LIVMFCS][LIVMF]{2}.N[VT].[STCA].C[GA].[TA]	PS00460	Glutathione peroxidases active site.
GLUTATHIONE_PEROXID_2	[LIV][AGD]FP[CS][NG]QF	PS00763	Glutathione peroxidases signature 2.
LIPOXYGENASE_1	[HQ][EQ].{3}H.[LMA][NEQHRCS][GSTA]H[LIVMSTAC]{2}.E	PS00711	Lipoxygenases iron-binding region signature 1.
LIPOXYGENASE_2	[LIVMACST]HP[LIVM].[KRQV][LIVMF]{2}.[AP]H	PS00081	Lipoxygenases iron-binding region signature 2.
EXTRADIOL_DIOXYGENAS	[GNTIV].H.{5,7}[LIVMF]Y.{2}[DENTA]P.[GP].{2,3}E	PS00082	Extradiol ring-cleavage dioxygenases signature.
INTRADIOL_DIOXYGENAS	[LIVMF].G.[LIVM].{4}[GS].{2}[LIVMA].{4}[LIVM][DE][LIVMFYC].{6}G.[FY]	PS00083	Intradiol ring-cleavage dioxygenases signature.
IDO_1	GGS[AN][GA]QSS.{2}Q	PS00876	Indoleamine 2,3-dioxygenase signature 1.
IDO_2	[FY]L[DQ][DE][LIVM].{2}YM.{3}H[KR]	PS00877	Indoleamine 2,3-dioxygenase signature 2.
LYS_HYDROXYLASE	PHHD[SA]STF	PS01325	Lysyl hydroxylase signature.
RING_HYDROXYL_ALPHA	C.HR[GAR].{7,8}[GEKVI][NERAQ].{4,5}C.[FY]H	PS00570	Bacterial ring hydroxylating dioxygenases alpha-subunit signature.
NOS	[GR]C[IV]GR[ILS].W	PS60001	Nitric oxide synthase (NOS) signature.
BACTERIAL_LUCIFERASE	[GA][LIVM]P[LIVM].[LIVMFY].W.{6}[RK].{6}Y.{3}[AR]	PS00494	Bacterial luciferase subunits signature.
UBIH	HP[LIV][AG]GQG.N.G.{2}D	PS01304	ubiH/COQ6 monooxygenase family signature.
BIOPTERIN_HYDROXYL	PD.{2}H[DE][LIVF][LIVMFY]GH[LIVMC][PA]	PS00367	Biopterin-dependent aromatic amino acid hydroxylases signature.
CU2_MONOOXYGENASE_1	HHM.{2}F.C	PS00084	Copper type II, ascorbate-dependent monooxygenases signature 1.
CU2_MONOOXYGENASE_2	H.F.{4}HTH.{2}G	PS00085	Copper type II, ascorbate-dependent monooxygenases signature 2.
TYROSINASE_1	H.{4,5}F[LIVMFTP].[FW]HR.{2}[LVMT].{3}E	PS00497	Tyrosinase CuA-binding region signature.
TYROSINASE_2	DP.F[LIVMFYW].{2}H.{3}D	PS00498	Tyrosinase and hemocyanins CuB-binding region signature.
FATTY_ACID_DESATUR_1	GE.[FYN]HN[FY]HH.FP.DY	PS00476	Fatty acid desaturases family 1 signature.
FATTY_ACID_DESATUR_2	[ST][SA].{3}[QR][LI].{5,6}DY.{2}[LIVMFYW][LIVM][DE]	PS00574	Fatty acid desaturases family 2 signature.
CYTOCHROME_P450	[FW][SGNH].[GD][^F][RKHPT][^P]C[LIVMFAP][GAD]	PS00086	Cytochrome P450 cysteine heme-iron ligand signature.
HEME_OXYGENASE	L[IV]AH[STACH]Y[STV][RT]Y[LIVM]G	PS00593	Heme oxygenase signature.
SOD_CU_ZN_1	[GA][IMFAT]H[LIVF]H[^S].[GP][SDG].[STAGDE]	PS00087	Copper/Zinc superoxide dismutase signature 1.
SOD_CU_ZN_2	G[GNHD][SGA][GR].R.[SGAWRV]C.{2}[IV]	PS00332	Copper/Zinc superoxide dismutase signature 2.
SOD_MN	D.[WF]EH[STA][FY]{2}	PS00088	Manganese and iron superoxide dismutases signature.
RIBORED_LARGE	W.{2}[LIVF].{6,7}G[LIVM][FYRA][NH].{3}[STAQLIVM][ASC].{2}[PA]	PS00089	Ribonucleotide reductase large subunit signature.
RIBORED_SMALL	[IVMSEQ]E.{1,2}[LIVTA][HY][GSA].[STAVM]Y.{2}[LIVMQ].{3}[LIVFY][IVFYCSA]	PS00368	Ribonucleotide reductase small subunit signature.
NITROGENASE_1_1	[LIVMFYH][LIVMFST]H[AG][AGSP][LIVMNQA][AG]C	PS00699	Nitrogenases component 1 alpha and beta subunits signature 1.
NITROGENASE_1_2	[STANQ][ET]C.{5}GD[DN][LIVMT].[STAGR][LIVMFYST]	PS00090	Nitrogenases component 1 alpha and beta subunits signature 2.
NIFH_FRXC_1	E.GGP.{2}[GA].GC[AG]G	PS00746	NifH/frxC family signature 1.
NIFH_FRXC_2	D.LGDVVCGGF[AGSP].P	PS00692	NifH/frxC family signature 2.
IPNS_1	[RK].[STA].{2}S.CY[SL]	PS00185	Isopenicillin N synthetase signature 1.
IPNS_2	[LIVM]{2}.CG[STA].{2}[STAG].{2}T.[DNG]	PS00186	Isopenicillin N synthetase signature 2.
NI_HGENASE_L_1	RG[LIVMF]E.{15}[QESMP][RK].C[GR][LIVM]C	PS00507	Nickel-dependent hydrogenases large subunit signature 1.
NI_HGENASE_L_2	[FY]D[PI]C[LIMAV][ASG]C.{2,3}H	PS00508	Nickel-dependent hydrogenases large subunit signature 2.
GLUTR	[HY][LIVMAF].{2}[LIVM][GSTACIV][GSTAC][GSA][LIVMFA][DEQHY]S.[LIVMAS][LIVMFKS][GFA][DE].[EQRD][IV][LIVTQAM][TAGRKS]Q[LIVMF][KRE]	PS00747	Glutamyl-tRNA reductase signature.
PHYTOENE_DH	[NG].[FYWV][LIVMF].G[AGC][GS][TA][HQT]PG[STAV]G[LIVM].{5}[GS]	PS00982	Bacterial-type phytoene dehydrogenase signature.
GLY_RADICAL_1	[STIV].R[IVT][CSA]GY[^GI][GACV]	PS00850	Glycine radical domain signature.
NNMT_PNMT_TEMT	LIDIGSGPT[IV]YQ[LV]L[SA]AC	PS01100	NNMT/PNMT/TEMT family of methyltransferases signature.
TRMA_1	[DN]P[PAS]R.G.{14,19}[LIVMAF][LIVMCAFT][YAHG].[SAG]C[NAMDSYHKGQ].{1,2}[TNKSI]	PS01230	RNA methyltransferase trmA family signature 1.
TRMA_2	[LIVMF][DN].FP[QHYWM][ST].[HR][LIVMFYT]E	PS01231	RNA methyltransferase trmA family signature 2.
THYMIDYLATE_SYNTHASE	R.{2}[LIVMT].{2,3}[FWY][QNYDI].{8,13}[LVESI].PC[HAVMLC].{3}[QMTLHD][FYWL].{0,1}[LV]	PS00091	Thymidylate synthase active site.
RRNA_A_DIMETH	[LIVMAC][LIVMFYWT][DE].G[STAPVLCG]G.[GAS].[LIVMF][ST].{2,3}[LIVMA].{5,8}[LIVMYF].[STAGVLC][LIVMFYHCS]E.D	PS01131	Ribosomal RNA adenine dimethylases signature.
MGMT	[LIVMF]PCHR[LIVMF]{2}	PS00374	Methylated-DNA--protein-cysteine methyltransferase active site.
N6_MTASE	[LIVMAC][LIVFYWA][^DYP][DN]PP[FYW]	PS00092	N-6 Adenine-specific DNA methylases signature.
N4_MTASE	[LIVMF]TSPP[FY]	PS00093	N-4 cytosine-specific DNA methylases signature.
C5_MTASE_1	[DENKS].[FLIV].{2}[GSTC].PC.[^V][FYWLIM]S	PS00094	C-5 cytosine-specific DNA methylases active site.
C5_MTASE_2	[RKQGTF].{2}GN[SA][LIVF].[VIP].[LVMT].{3}[LIVM].{3}[LIVM]	PS00095	C-5 cytosine-specific DNA methylases C-terminal signature.
PCMT	[GSAED][DN]G.{2}G[FYWLV].{3}[GSA][PTL][FY][DNSHE].I	PS01279	Protein-L-isoaspartate(D-aspartate) O-methyltransferase signature.
SUMT_1	[LIVM][GS][STAL]GPG.{3}[LIVMFY][LIVM]T[LIVM][KRHQG][AG]	PS00839	Uroporphyrin-III C-methyltransferase signature 1.
SUMT_2	[VW].{2}[LI].{2}[GA][DT].{3}[FYW][GS].{8}[LIVFA].{5,6}[LIVMFYWPAC].[LIVMY].[PN]G	PS00840	Uroporphyrin-III C-methyltransferase signature 2.
UBIE_1	Y[DN].[MLAFTI]N.{2}[LIVMN]S.{3}[HQD].{2}W	PS01183	ubiE/COQ5 methyltransferase family signature 1.
UBIE_2	RV[LIVMCT][KRQ][PVRK][GMKD][GAS].[LIVMFAT].{2}[LIVMCA][ED].[SGT]	PS01184	ubiE/COQ5 methyltransferase family signature 2.
SHMT	[DEQHY][LIVMFYA].[GSTMVA][GSTAV][ST][STVM][HQ]K[STG][LFMI].[GAS][PGAC][RQ][GSARH][GA]	PS00096	Serine hydroxymethyltransferase pyridoxal-phosphate attachment site.
GART	G.[STMC][IVTL].[FYWVQELKR][VMAT].[DEVMKAI].[LIVMY]D.G.{2}[LIVTYA].{6}[LIVM]	PS00373	Phosphoribosylglycinamide formyltransferase active site.
CARBAMOYLTRANSFERASE	F.[EK].S[GT]RT	PS00097	Aspartate and ornithine carbamoyltransferases signature.
TRANSKETOLASE_1	R.{3}[LIVMTA][DENQSTHKF].{5,6}[GSN]GH[PLIVMF][GSTA].{2}[LIMC][GS]	PS00801	Transketolase signature 1.
TRANSKETOLASE_2	[GP][DEQGSANPHVT][DN]G[PAEQ][ST][HQ].[PAGM][LIVMYACNQS][DEFYWLA].{2}[STAPG].{2}[RGANQS]	PS00802	Transketolase signature 2.
TRANSALDOLASE_1	[DGH][IVSAC]T[ST]NP[STA][LIVMF]{2}	PS01054	Transaldolase signature 1.
TRANSALDOLASE_2	[LIVMA].[LIVM]K[LIVM][PAS].[STC].[DENQPAS][GC][LIVM].[AGV].{0,1}[QEKRSTH].[LIVMF]	PS00958	Transaldolase active site.
ACYLTRANSF_C_1	[LI][PK].[LVPQ]P[IVTAL]P.[LIVMA].[DENQAS][ST][LIVMA].{2}[LY]	PS00439	Acyltransferases ChoActase / COT / CPT family signature 1.
ACYLTRANSF_C_2	R[FYW].[DA][KA].{0,1}[LIVMFY].[LIVMFY]{2}.{3}[DNS][GSA].{6}[DE][HS].{3}[DE][GAC]	PS00440	Acyltransferases ChoActase / COT / CPT family signature 2.
THIOLASE_1	[LIVM][NST][^T].C[SAGLI][ST][SAG][LIVMFYNS].[STAG][LIVM].{6}[LIVM]	PS00098	Thiolases acyl-enzyme intermediate signature.
THIOLASE_2	N.{2}GG.[LIVM][SA].GHP.[GAS].[ST]G	PS00737	Thiolases signature 2.
THIOLASE_3	[AG][LIVMA][STAGCLIVM][STAG][LIVMA]C[^Q][AG].[AG].[AG].[SAG]	PS00099	Thiolases active site.
CAT	Q[LIV]HH[SA].{2}DG[FY]H	PS00100	Chloramphenicol acetyltransferase active site.
HEXAPEP_TRANSFERASES	[LIV][GAED].{2}[STAV].[LIV].{3}[LIVAC].[LIV][GAED].{2}[STAVR].[LIV][GAED].{2}[STAV].[LIV].{3}[LIV]	PS00101	Hexapeptide-repeat containing-transferases signature.
B_KETOACYL_SYNTHASE	G[^A][^KGR].{2}[LIVMFTAP][^R].[AGC]C[STA]{2}[STAG].{2}[^LI][LIVMF]	PS00606	Beta-ketoacyl synthases active site.
CHALCONE_SYNTH	R[LIVMFYS].[LIVM].[QHG].GC[FYNA][GAPV]G[GAC][STAVK].[LIVMF][RAL]	PS00441	Chalcone and stilbene synthases active site.
NMT_1	[DEK][IV]N[FS]LC.HK	PS00975	Myristoyl-CoA:protein N-myristoyltransferase signature 1.
NMT_2	KFG.GDG	PS00976	Myristoyl-CoA:protein N-myristoyltransferase signature 2.
NODA	[RHQ][ST]W[GSA]GARPE	PS01349	Nodulation protein nodA signature.
G_GLU_TRANSPEPTIDASE	T[STA]H.[ST][LIVMA].{4}G[SN].V[STA].T.T[LIVM][NE].{1,2}[FY]G	PS00462	Gamma-glutamyltranspeptidase signature.
TRANSGLUTAMINASES	[GT]Q[CA]WV.[SA][GAS][IVT].{2}T.[LMSC]R[CSAG][LV]G	PS00547	Transglutaminases active site.
PHOSPHORYLASE	EA[SC]G.[GS].MK.{2}[LM]N	PS00102	Phosphorylase pyridoxal-phosphate attachment site.
UDPGT	[FW].{2}[QL].{2}[LIVMYA][LIMV].{4,6}[LVGAC][LVFYAHM][LIVMF][STAGCM][HNQ][STAGC]G.{2}[STAG].{3}[STAGL][LIVMFA].{4,5}[PQR][LIVMTA].{3}[PA].{2,3}[DES][QEHNR]	PS00375	UDP-glycosyltransferases signature.
PUR_PYR_PR_TRANSFER	[LIVMFYWCTA][LIVM][LIVMA][LIVMFC][DE]D[LIVMS][LIVM][STAVD][STAR][GAC].[STAR]	PS00103	Purine/pyrimidine phosphoribosyl transferases signature.
GATASE_TYPE_I	[PAS][LIVMFYT][LIVMFY]G[LIVMFY]C[LIVMFYN]G.[QEH].[LIVMFA]	PS00442	Glutamine amidotransferases class-I active site.
GATASE_TYPE_II	^.{0,11}C[GS][IV][LIVMFYW][AG]	PS00443	Glutamine amidotransferases class-II active site.
PNP_UDP_1	[GST].G[LIVM]G.[PA]S.[GSTAL][IL].{3}EL	PS01232	Purine and other phosphorylases family 1 signature.
PNP_MTAP_2	[LIVF].{3}[GS].{2}H.[LIVMFY].{4}[LIVMF].{3}[ATV].{1,2}[LIVM].[ATV].{4}[GN].{3,4}[LIVMF]{2}.{2}[STN][SAGT].G[GS][LIVM]	PS01240	Purine and other phosphorylases family 2 signature.
THYMID_PHOSPHORYLASE	[SA][GS]R[GA][LIV].{2}[TAP][GAS]GT.D.[LIVMF][EDS]	PS00647	Thymidine and pyrimidine-nucleoside phosphorylases signature.
ATP_P_PHORIBOSYLTR	E.{5}[GND].[SAG].{2}[IV].[DE][LIV].{2}[ST]G.T[LMI]	PS01316	ATP phosphoribosyltransferase signature.
ART	[FY].[FY]K.{2}H[FY].L[STI].A	PS01291	NAD:arginine ADP-ribosyltransferases signature.
LGT	[GI]R.[GA]N[FWY][LIVMFA][NG].E.{2}G	PS01311	Prolipoprotein diacylglyceryl transferase signature.
ADOMET_SYNTHETASE_1	[GN][AS]GDQG.{3}G[FYHG]	PS00376	S-adenosylmethionine synthetase signature 1.
ADOMET_SYNTHETASE_2	G[GA]G[ASC][FY]S.K[DE]	PS00377	S-adenosylmethionine synthetase signature 2.
POLYPRENYL_SYNTHET_1	[LIVM]{2}.DD.{2,4}D.{4}RR[GH]	PS00723	Polyprenyl synthetases signature 1.
POLYPRENYL_SYNTHET_2	[LIVMFY]G.{2}[FYL]Q[LIVM].DD[LIVMFY].[DNG]	PS00444	Polyprenyl synthetases signature 2.
UPP_SYNTHETASE	[DEH][LIVMF][LIVMFC][LIVMF]R[STPV][SGAC][GEN].{1,2}R.S.[FY][LMFV][LIPMVT][YWL]	PS01066	Undecaprenyl pyrophosphate synthetase family signature.
UBIA	N.{3}[DEH].{2}[LIMFYT]D.{2}[VM].R[ST].{2}R.{4}[GYNKR]	PS00943	UbiA prenyltransferase family signature.
SPERMIDINE_SYNTHASE_1	[VAI][LAV][LIV]{2}GGG.[GC].{2}[LIVA].E	PS01330	Spermidine/spermine synthases family signature.
SQUALEN_PHYTOEN_SYN_1	Y[CSAM].{2}[VSG]A[GSA][LIVAT][IV]G.{2}[LMSC].{2}[LIV]	PS01044	Squalene and phytoene synthases signature 1.
SQUALEN_PHYTOEN_SYN_2	[LIVM]G.{3}Q.{2,3}[ND][IFL].[RE]D[LIVMFY].{2}[DE].{4,7}R.[FY].P	PS01045	Squalene and phytoene synthases signature 2.
DHPS_1	[LIVM].[AG][LIVMF]{2}N.T.[DN]S[FLMI].D.[SG]	PS00792	Dihydropteroate synthase signature 1.
DHPS_2	[GE][SAV].[LIVM]{2}D[LIVMF]G[GPA].{2}[STA].P	PS00793	Dihydropteroate synthase signature 2.
EPSP_SYNTHASE_1	[LIVF][^LV].[GANQK][NLG][SA][GA][TAI][STAGV][^N]R.[LIVMFYAT].[GSTAP]	PS00104	EPSP synthase signature 1.
EPSP_SYNTHASE_2	[KR].[KH]E[CSTVI][DNE]R[LIVMY].[GSTAVLD][LIVMCTF].{3}[LIVMFA].{2}[LIVMFCGANY]G	PS00885	EPSP synthase signature 2.
FLAP_GST2_LTC4S	G.{3}FERV[FY].A[NQ].NC	PS01297	FLAP/GST2/LTC4S family signature.
AA_TRANSFER_CLASS_1	[GS][LIVMFYTAC][GSTA]K.{2}[GSALVN][LIVMFA].[GNAR][^V]R[LIVMA][GA]	PS00105	Aminotransferases class-I pyridoxal-phosphate attachment site.
AA_TRANSFER_CLASS_2	T[LIVMFYW][STAG]K[SAG][LIVMFYWR][SAG][^ENKR][^TNDR][SAG]	PS00599	Aminotransferases class-II pyridoxal-phosphate attachment site.
AA_TRANSFER_CLASS_3	[LIVMFYWCS][LIVMFYWCAH].D[ED][IVA].{2,3}[GAT][LIVMFAGCYN].{0,1}[RSACLIH].[GSADEHRM].{10,16}[DH][LIVMFCAG][LIVMFYSTAR].{2}[GSA]K.{2,3}[GSTADNV][GSAC]	PS00600	Aminotransferases class-III pyridoxal-phosphate attachment site.
AA_TRANSFER_CLASS_4	E.[STAGCI].{2}N[LIVMFAC][FY].{6,12}[LIVMFA].T.{6,8}[LIVM].[GS][LIVM].[KR]	PS00770	Aminotransferases class-IV signature.
AA_TRANSFER_CLASS_5	[LIVFYCHT][DGH][LIVMFYAC][LIVMFYA].{2}[GSTAC][GSTA][HQR]K.{4,6}G.[GSAT].[LIVMFYSAC]	PS00595	Aminotransferases class-V pyridoxal-phosphate attachment site.
HEXOKINASES	[LIVM]GF[TN]FS[FY]P.{5}[LIVM][DNST].{3}[LIVM].{2}WTK.[LF]	PS00378	Hexokinases signature.
GALACTOKINASE	GR.N[LIV]IG[DE]H.DY	PS00106	Galactokinase signature.
GHMP_KINASES_ATP	[LIVM][PK].[GSTA].{0,1}G[LM][GS]SS[GSA][GSTAC]	PS00627	GHMP kinases putative ATP-binding domain.
PHOSPHOFRUCTOKINASE	[RK].{4}[GAS]H.[QL][QR][GS][GF].{5}[DE][RL]	PS00433	Phosphofructokinase signature.
PFKB_KINASES_1	[AG]G.{0,1}[GAP].N[^AGLS][STA].{2}[^A].[^G][^GNKA][GS].{9}G	PS00583	pfkB family of carbohydrate kinases signature 1.
PFKB_KINASES_2	[DNSK][PSTV].[SAG]{2}[GD]D.{3}[SAGV][AG][LIVMFYA][LIVMSTAP]	PS00584	pfkB family of carbohydrate kinases signature 2.
ROK	[LIVM].{2}G[LIVMFCT]G.[GA][LIVMFA].{3}[^V].{4}G.{3,5}[GATP][^G].G[RKH]	PS01125	ROK family signature.
PHOSPHORIBULOKINASE	K[LIVM].RD.{3}RG.[ST].E	PS00567	Phosphoribulokinase signature.
TK_CELLULAR_TYPE	[GA].{1,2}[DE].Y.[STAPV].C[NKR].[CH][LIVMFYWH]	PS00603	Thymidine kinase cellular-type signature.
FGGY_KINASES_1	[MFYGS].[PST].{2}K[LIVMFYW][^G]W[LIVMF][^E][DENQTKR][ENQH]	PS00933	FGGY family of carbohydrate kinases signature 1.
FGGY_KINASES_2	[GSA].[LIVMFYW][^D]G[LIVM].{7,8}[HDENQ][LIVMF][^PEQ][^DTAI][AS][STALIVM][LIVMFY][DEQ]	PS00445	FGGY family of carbohydrate kinases signature 2.
PROTEIN_KINASE_ATP	[LIV]G[^P]G[^P][FYWMGSTNH][SGA][^PW][LIVCAT][^PD].[GSTACLIVMFY].{5,18}[LIVMFYWCSTAR][AIVP][LIVMFAGCKR]K	PS00107	Protein kinases ATP-binding region signature.
PROTEIN_KINASE_ST	[LIVMFYC].[HY].D[LIVMFY]K.{2}N[LIVMFYCT]{3}	PS00108	Serine/Threonine protein kinases active-site signature.
PROTEIN_KINASE_TYR	[LIVMFYC][^A][HY].D[LIVMFY][RSTAC][^D][^PF]N[LIVMFYC]{3}	PS00109	Tyrosine protein kinases specific active-site signature.
MAPK	F.{10}RE.{72,86}RD.K.{9}[CS]	PS01351	MAP kinase signature.
CK2_BETA	CP.[LIVMYAT].C.{5}[LI]P[LIVMCA]G.{9}V[KRM].{2}C[PA].C	PS01101	Casein kinase II regulatory subunit signature.
PYRUVATE_KINASE	[LIVAC].[LIVM]{2}[SAPCV]K[LIV]E[NKRST].[DEQHS][GSTA][LIVM]	PS00110	Pyruvate kinase active site signature.
SHIKIMATE_KINASE	[KR].{2}E.{3}[LIVMF].{8,12}[LIVMF]{2}[SA].G{3}.[LIVMFG]	PS01128	Shikimate kinase signature.
DAGK_PROKAR	E.[LIVM]N[ST][SA][LIV]E.{2}VD	PS01069	Prokaryotic diacylglycerol kinase signature.
PI3_4_KINASE_1	[LIVMFAC]K.{1,3}[DEA][DE][LIVMCP]RQ[DE].{4}Q	PS00915	Phosphatidylinositol 3- and 4-kinases signature 1.
PI3_4_KINASE_2	[GSET].[AVE].{3}[LIVM].{2}[FYHW][LIVM]{2}.[LIVMFN].DR[HNG].{2}N	PS00916	Phosphatidylinositol 3- and 4-kinases signature 2.
ACETATE_KINASE_1	[LIVMFANT][LIVM].[LIVMA]N.GS[ST]{2}.[KE]	PS01075	Acetate and butyrate kinases family signature 1.
ACETATE_KINASE_2	[LIVMFATQ][LIVMA].{2}H.G.[GT].[ST][LIVMA].[TAVC].{3}G	PS01076	Acetate and butyrate kinases family signature 2.
PGLYCERATE_KINASE	[KRHGTCVN][VT][LIVMF][LIVMC]R.D.N[SACV]P	PS00111	Phosphoglycerate kinase signature.
ASPARTOKINASE	[LIVM].K[FY]GG[ST][SC][LIVM]	PS00324	Aspartokinase signature.
GLUTAMATE_5_KINASE	[GSTNAD].{2}[GAS].G[GC][IM].[STAG]K[LIVMCT].[SAI][TCAGFS].{2}[GALVCMI]	PS00902	Glutamate 5-kinase signature.
GUANIDO_KINASE	CP.{0,1}[ST]N[ILV]GT	PS00112	ATP:guanido phosphotransferases active site.
PTS_HPR_HIS	G[LIVM]H[STAV]R[PAS][GSTA][STAMVN]	PS00369	PTS HPR component histidine phosphorylation site signature.
PTS_HPR_SER	[GSTADE][KREQSTIV].[^EPRK][^VPGL].[KRDN]S[LIVMF]{2}[^EVPL][LIVM][^EATN].[LIVM][GADE]	PS00589	PTS HPR component serine phosphorylation site signature.
PTS_EIIA_TYPE_1_HIS	G.{2}[LIVMFA][LIVMF]{2}H[LIVMF]G[LIVMF].T[LIVA]	PS00371	PTS EIIA domains phosphorylation site signature 1.
PTS_EIIA_TYPE_2_HIS	[DENQ].{6}[LIVMF][GA].{2}[LIVM]A[LIVM]PH[GAC]	PS00372	PTS EIIA domains phosphorylation site signature 2.
PTS_EIIB_TYPE_1_CYS	N[LIVMFY].{5}C.TR[LIVMF].[LIVMF].[LIVM].[DQEN]	PS01035	PTS EIIB domains cysteine phosphorylation site signature.
ADENYLATE_KINASE	[LIVMFYWCA][LIVMFYW]{2}DG[FYI]PR.{3}[NQ]	PS00113	Adenylate kinase signature.
NDP_KINASES	N.{2}H[GA]SD[GSA][LIVMPKNE]	PS00469	Nucleoside diphosphate kinases active site.
GUANYLATE_KINASE_1	[TS][ST]R.{2}[KR].{2}[DE].{2}[GA].{2}Y.[FY][LIVMKHRT]	PS00856	Guanylate kinase-like signature.
THYMIDYLATE_KINASE	[LIV][LIVMGSTC][DET][RH][FYHCS].{2}S[GSTNP].[AVC][FY][STANQ]	PS01331	Thymidylate kinase signature.
PRPP_SYNTHETASE	D[LIM]H[SANDT].[QS][IMSTAVF][QMLPH][GA][FY]F.{2}P[LIVMFCT]D	PS00114	Phosphoribosyl pyrophosphate synthetase signature.
HPPK	[KRHD].[GA][PSAE]R.{2}D[LIV]D[LIVM]{2}	PS00794	7,8-dihydro-6-hydroxymethylpterin-pyrophosphokinase signature.
RNA_POL_PHAGE_1	P[LIVM].{2}D[GA][ST][AC][SN][GA][LIVMFY]Q	PS00900	Bacteriophage-type RNA polymerase family active site signature 1.
RNA_POL_PHAGE_2	[LIVMF].R.{3}K.{2}[LIVMF]M[PT].{2}Y	PS00489	Bacteriophage-type RNA polymerase family active site signature 2.
RNA_POL_II_REPEAT	Y[ST]P[ST]SP[STANK]	PS00115	Eukaryotic RNA polymerase II heptapeptide repeat.
RNA_POL_BETA	G.[KN][LIVMFA][STAC][GSTNR].[HSTA][GSAI][QNH]K[GL][IVTEC]	PS01166	RNA polymerases beta chain signature.
RNA_POL_M_15KD	[FY]C.[DEKSTG]C[GNK][DNSA][LIVMHG][LIVM].{8,14}C.{1,2}C	PS01030	RNA polymerases M / 15 Kd subunits signature.
RNA_POL_D_30KD	N[SGAT][LIVMF]RR.{9}[SAR].{3}[VA].{4}N.[STA].{3}[DN]E.[LIV][GSA].R[LI][GAS][LIVM]{2}[PV]	PS00446	RNA polymerases D / 30 to 40 Kd subunits signature.
RNA_POL_H_23KD	H[NQEIVMYD][LIVMYAS]VP[EKT]H.{2}[LIVM].{2}[DESAG][ET]	PS01110	RNA polymerases H / 23 Kd subunits signature.
RNA_POL_K_14KD	[ST].[FY]E.[AT]R.[LIVM][GSA].R[SA].Q	PS01111	RNA polymerases K / 14 to 18 Kd subunits signature.
RNA_POL_L_13KD	[LIVMFA].{2}[EYD].[HYN][ST][LIVMFY].[NSTR].[LIV].{3}[LIVA].{4,5}[VPG].{4}[FY].{3}[HPY][PEV]	PS01154	RNA polymerases L / 13 to 16 Kd subunits signature.
RNA_POL_N_8KD	[LIVMFD][LIVMFP]P[LIVM].C[FL][ST]CG	PS01112	RNA polymerases N / 8 Kd subunits signature.
DNA_POLYMERASE_A	R.{2}[GSAV]K.{3}[LIVMFY][AGQ].{2}Y.{2}[GS].{3}[LIVMA]	PS00447	DNA polymerase family A signature.
DNA_POLYMERASE_B	[YA][GLIVMSTAC]DTD[SG][LIVMFTC][^LA][LIVMSTAC]	PS00116	DNA polymerase family B signature.
DNA_POLYMERASE_X	G[SG][LFY].R[GE].{3}[SGCL].D[LIVM]D[LIVMFY]{3}.{2}[SAP]	PS00522	DNA polymerase family X signature.
GAL_P_UDP_TRANSF_I	FEN[RK]G.{3}G.{4}HPH.Q	PS00117	Galactose-1-phosphate uridyl transferase family 1 active site signature.
GAL_P_UDP_TRANSF_II	DLPI[VS]GG[ST][LIVM]{2}[STAV]H[DEN]H[FY]Q[GAT]G	PS01163	Galactose-1-phosphate uridyl transferase family 2 signature.
ADP_GLC_PYROPHOSPH_1	[AG]GG.G[STKA].L.{2}L[TA].{3}[AST].P[AS][LV]	PS00808	ADP-glucose pyrophosphorylase signature 1.
ADP_GLC_PYROPHOSPH_2	W[FY].G[ST][AS][DNSH][AS][LIVMFYW]	PS00809	ADP-glucose pyrophosphorylase signature 2.
ADP_GLC_PYROPHOSPH_3	[APV][GS]MG[LIVMN]Y[IVC][LIVMFY].{2}[DENPHKRQS]	PS00810	ADP-glucose pyrophosphorylase signature 3.
CDS	S.[LIVMF]KR.{4}KD.[GSA].{2}[LIF][PGS].HGG[LIVMF].DR[LIVMFT]D	PS01315	Phosphatidate cytidylyltransferase signature.
RIBONUCLEASE_PH	[CA][DE][LIVM]{2}[NQV][GTA]D[GA][SG].{2,3}[TAVLC][AT]	PS01277	Ribonuclease PH signature.
25A_SYNTH_1	GS.[AG][KRN].T.L[KRN].{3}[DE].[DET][LM][VI].F	PS00832	2'-5'-oligoadenylate synthetases signature 1.
25A_SYNTH_2	RP[VI]ILDP.[DE]PT	PS00833	2'-5'-oligoadenylate synthetases signature 2.
CDP_ALCOHOL_P_TRANSF	DG.{2}AR.{7,8}G.{3}D.{3}D	PS00379	CDP-alcohol phosphatidyltransferases signature.
MRAY_1	[KRA].{2}[TIVK]P[ST][MGA][GA]G[LIVSA].[LIVMF]{2}	PS01347	MraY family signature 1.
MRAY_2	[NHS].{2}[NK].[TINAS][DN]G[ILVM]DG[LM]	PS01348	MraY family signature 2.
PEP_ENZYMES_PHOS_SITE	G[GA].[STN].H[STA][STAV][LIVM]{2}[STAV][RG]	PS00370	PEP-utilizing enzymes phosphorylation site signature.
PEP_ENZYMES_2	[DEQSKN].[LIVMF][SA][LIVMF]G[ST]ND[LIVM].Q[LIVMFYGT][STALIV][LIVMFY][GAS].{2}R	PS00742	PEP-utilizing enzymes signature 2.
RHODANESE_1	[FY].{3}H[LIV]PGA.{2}[LIVF]	PS00380	Rhodanese signature 1.
RHODANESE_2	[AV].{2}[FY][DEAP]G[GSA][WF].E[FYW]	PS00683	Rhodanese C-terminal signature.
COA_TRANSF_1	[DN][GN].{2}[LIVMFA]{3}GGF.{3}G.P	PS01273	Coenzyme A transferases signature 1.
COA_TRANSF_2	[LF][HQ]SENG[LIVF]{2}[GA]	PS01274	Coenzyme A transferases signature 2.
ISPD	[IVT][LIVMC][IVT][HS]D[SGAV][AV]R	PS01295	4-diphosphocytidyl-2C-methyl-D-erythritol synthase signature.
PA2_HIS	CC[^P].H[^LGY].C	PS00118	Phospholipase A2 histidine active site.
PA2_ASP	[LIVMA]C[^LIVMFYWPCST]CD[^GS][^G][^N].[^QS]C	PS00119	Phospholipase A2 aspartic acid active site.
LIPASE_SER	[LIV][^KG][LIVFY][LIVMST]G[HYWV]S[^YAG]G[GSTAC]	PS00120	Lipases, serine active site.
COLIPASE	Y.{2}YY.C.C	PS00121	Colipase signature.
LIPASE_GDSL_SER	[LIVMFYAG]{4}GDS[LIVM].{1,2}[TAG]G	PS01098	Lipolytic enzymes "G-D-S-L" family, serine active site.
LIPASE_GDXG_HIS	[LIVMF]{2}.[LIVMF]HGG[SAG][FYW].{3}[STDN].{1,2}[STYA][HAGFT]	PS01173	Lipolytic enzymes "G-D-X-G" family, putative histidine active site.
LIPASE_GDXG_SER	[LIVM].[LIVMF][SA]GDS[CAS]G[GA].[LI][CAVT]	PS01174	Lipolytic enzymes "G-D-X-G" family, putative serine active site.
CARBOXYLESTERASE_B_1	F[GR]G.{4}[LIVM].[LIV].G.S[STAG]G	PS00122	Carboxylesterases type-B serine active site.
CARBOXYLESTERASE_B_2	[EDA][DG]CL[YTF][LIVT][DNS][LIV][LIVFYW].[PQR]	PS00941	Carboxylesterases type-B signature 2.
PECTINESTERASE_1	[GSTNP].{6}[FYVHR][IVN][KEP].G[STIVKRQ]Y[DNQKRMV][EP].{3}[LIMVA]	PS00800	Pectinesterase signature 1.
PECTINESTERASE_2	[IV].G[STAD][LIVT]D[FYI][IV][FSN]G	PS00503	Pectinesterase signature 2.
PEPT_TRNA_HYDROL_1	[FYH].{2}[TN][RK]HN.G.{2}[LIVMFAYCT][LIVMFA][DEN]	PS01195	Peptidyl-tRNA hydrolase signature 1.
PEPT_TRNA_HYDROL_2	[GS].{3}HNG[LIVM][KR][DNS][LIVMTC]	PS01196	Peptidyl-tRNA hydrolase signature 2.
4HBCOA_THIOESTERASE	[QR][IV].{4}[TC]D.{2}G[IV]V.[HF].{2}[FY]	PS01328	4-hydroxybenzoyl-CoA thioesterase family active site.
ALKALINE_PHOSPHATASE	[IV].DS[GAS][GASC][GAST][GA]T	PS00123	Alkaline phosphatase active site.
HIS_ACID_PHOSPHAT_1	[LIVM].{2}[LIVMA].{2}[LIVM].RH[GN].R.[PAS]	PS00616	Histidine acid phosphatases phosphohistidine signature.
HIS_ACID_PHOSPHAT_2	[LIVMF].[LIVMFAG][^T].[STAGI]HD[STANQ][^V][LIVM].{2}[LIVMFY].{2}[STA]	PS00778	Histidine acid phosphatases active site signature.
ACID_PHOSPH_CL_A	GSYPSGHT	PS01157	Class A bacterial acid phosphatases signature.
5_NUCLEOTIDASE_1	[LIVM].[LIVM]{2}[HEA][TI].D.H[GSA].[LIVMF]	PS00785	5'-nucleotidase signature 1.
5_NUCLEOTIDASE_2	[FYPH].{4}[LIVM]GNHEF[DN]	PS00786	5'-nucleotidase signature 2.
FBPASE	[AG][RK][LI].{1,2}[LIV][FY]E.{2}P[LIVM][GSA]	PS00124	Fructose-1-6-bisphosphatase active site.
SER_THR_PHOSPHATASE	[LIVMN][KR]GNHE	PS00125	Serine/threonine specific protein phosphatases signature.
PR55_1	EFDYLKSLEIEEKIN	PS01024	Protein phosphatase 2A regulatory subunit PR55 signature 1.
PR55_2	[NH][AG]H[TAD]YHINSIS[LIVMN][NS]SD	PS01025	Protein phosphatase 2A regulatory subunit PR55 signature 2.
PP2C	[LIVMFY][LIVMFYA][GSAC][LIVM][FYC]DGH[GAV]	PS01032	Protein phosphatase 2C signature.
TYR_PHOSPHATASE_1	[LIVMF]HC.{2}G.{3}[STC][STAGP].[LIVMFY]	PS00383	Tyrosine specific protein phosphatases active site.
IMP_1	[FWV].{0,1}[LIVM]DP[LIVM]D[SG][ST].{2}[FYA].{0,1}[HKRNSTY]	PS00629	Inositol monophosphatase family signature 1.
IMP_2	[WYV]D.[AC][GSA][GSAPV].[LIVFACP][LIVM][LIVAC].{3}[GH][GA]	PS00630	Inositol monophosphatase family signature 2.
PROKAR_ZN_DEPEND_PLPC	HY.[GT]D[LIVMAF][DNSH].P.H[PA].N	PS00384	Prokaryotic zinc-dependent phospholipase C signature.
PDEASE_I	HD[LIVMFY].H.[AG].{2}[NQ].[LIVMFY]	PS00126	3'5'-cyclic nucleotide phosphodiesterases signature.
PDEASE_II	H.HLDH[LIVM].[GS][LIVMA][LIVM]{2}.S[AP]	PS00607	cAMP phosphodiesterases class-II signature.
SULFATASE_1	[SAPG][LIVMST][CS][STACG]P[STA]R.{2}[LIVMFW]{2}[TAR]G	PS00523	Sulfatases signature 1.
SULFATASE_2	G[YV].[ST].{2}[IVAS]GK.{0,1}[FYWMK][HL]	PS00149	Sulfatases signature 2.
PHOSPHOTRIESTERASE_1	G.TL.HEH[LIV]	PS01322	Phosphotriesterase family signature 1.
PHOSPHOTRIESTERASE_2	A.A.A.{2}[^N].G[^S]P[LIVM].{2}H	PS01323	Phosphotriesterase family signature 2.
AP_NUCLEASE_F1_1	[APF]D[LIVMF]{2}[^T][LIVM]QE[^G]K	PS00726	AP endonucleases family 1 signature 1.
AP_NUCLEASE_F1_2	D[ST][FY][RP][KHQ].{7,8}[FYWD][ST][FYW]{2}	PS00727	AP endonucleases family 1 signature 2.
AP_NUCLEASE_F1_3	N.G.R[LIVM]D[LIVMFYH].[LV].S	PS00728	AP endonucleases family 1 signature 3.
AP_NUCLEASE_F2_1	H[GSAD].Y[LIF][LIMN]N[LIVMFCAP][AGC]	PS00729	AP endonucleases family 2 signature 1.
AP_NUCLEASE_F2_2	[GSARY][LIVMF][CT][LIVMFY]DTCH	PS00730	AP endonucleases family 2 signature 2.
AP_NUCLEASE_F2_3	[LIVMFW]H.N[DEG][SA].{4}[GNAQ].{3}D.H	PS00731	AP endonucleases family 2 signature 3.
DNASE_I_1	[LIVM]{2}[AP][LQ]H[STA][STAE]P.{5}E[LIVM][DN].L.[DE]V	PS00919	Deoxyribonuclease I signature 1.
DNASE_I_2	GDFNA.C[SAK]	PS00918	Deoxyribonuclease I signature 2.
RUVC	G.[GA].[AG].K.[EQA][IVM].{16,19}D.[SAVT]D[AG].[AGS][LIVMCA][ACS]	PS01321	Crossover junction endodeoxyribonuclease ruvC signature.
ENDONUCLEASE_III_1	C.{3}[KRSN]P[KRAGL]C.{2}C.{5}C	PS00764	Endonuclease III iron-sulfur binding region signature.
ENDONUCLEASE_III_2	[GSTENA].[LIVMF]P.{5}[LIVMW].{2,3}[LI][PAS]G[IV][GA].{3}[GAC].{2,3}[LIVMA].{1,2}[GSALVI][LIVMFYW][GANKD]	PS01155	Endonuclease III family signature.
RIBONUCLEASE_II	[HI][FYE][GSTAM][LIVM].{4,5}Y[STALV].[FWVAC][TV][SA]P[LIVMA][RQ][KR][FY].D.{3}[HQ]	PS01175	Ribonuclease II family signature.
RNASE_3_1	[DEQ][KRQT][LMF]E[FYW][LV]GD[SARHG]	PS00517	Ribonuclease III family signature.
RIBONUCLEASE_P	[LIVMFYSNAD].{2}A.{2}R[NH][KRQLYAT][LIVMFSA][KRA]R.[LIVMTA][KR]	PS00648	Bacterial ribonuclease P protein component signature.
RNASE_T2_1	[FYWL].[LIVM]HGL[WY]P	PS00530	Ribonuclease T2 family histidine active site 1.
RNASE_T2_2	[LIVMF].{2}[HDGTY][EQ][FYW].[KRT]H[GA].C	PS00531	Ribonuclease T2 family histidine active site 2.
RNASE_PANCREATIC	CK.{2}NTF	PS00127	Pancreatic ribonuclease family signature.
NUCLEASE_NON_SPEC	DRGH[QLIM].{3}[AG]	PS01070	DNA/RNA non-specific endonucleases active site.
TNASE_1	DGDT[LIVM].[LIVMC].{9,10}R[LIVM].{2}[LIVM]D.PE	PS01123	Thermonuclease family signature 1.
TNASE_2	[DT][KRP][YQ][GQ]R.[LVY][GA].[IV][FYW]	PS01284	Thermonuclease family signature 2.
TATD_1	[LIVMFY]{2}D[STA]H.H[LIVMFP][DN]	PS01137	TatD deoxyribonuclease family signature 1.
TATD_2	P[LIVM].[LIVM]H.R.[TA].[DE]	PS01090	TatD deoxyribonuclease family signature 2.
TATD_3	[LVSAT][LIVA].{2}[LIVMT][PSD].{3}[LI][LIVMT][LIVMST]ETD.P	PS01091	TatD deoxyribonuclease family signature 3.
BETA_AMYLASE_1	H.CGGNVGD	PS00506	Beta-amylase active site 1.
BETA_AMYLASE_2	G.[SA]GE[LIVM]RYPSY	PS00679	Beta-amylase active site 2.
GLUCOAMYLASE	[STN][GP].{1,2}[DE].WEE.{2}[GS]	PS00820	Glucoamylase active site region signature.
POLYGALACTURONASE	[GSDENKRH].{2}[VMFC].{2}[GS]HG[LIVMAG].{1,2}[LIVM]GS	PS00502	Polygalacturonase active site.
CLOS_CELLULOSOME_RPT	D[LIVMFY][DNV].[DNS].{2}[LIVM][DN][SALM].D.{3}[LIVMF].[RKS].[LIVMF]	PS00448	Clostridium cellulosome enzymes repeated domain signature.
CHITINASE_18	[LIVMFY][DN]G[LIVMF][DN][LIVMF][DN].E	PS01095	Chitinases family 18 active site.
CHITINASE_19_1	C.{4,5}FY[ST].{3}[FY][LIVMF].A.{3}[YF].{2}F[GSA]	PS00773	Chitinases family 19 signature 1.
CHITINASE_19_2	[LIVM][GSA]F.[STAG]{2}[LIVMFY]W[FY]W[LIVM]	PS00774	Chitinases family 19 signature 2.
LACTALBUMIN_LYSOZYME	C.{3}C.{2}[LMF].{3}[DEN][LI].{5}C	PS00128	Alpha-lactalbumin / lysozyme C signature.
ALPHA_GALACTOSIDASE	G[LIVMFY].{2}[LIVMFY].[LIVM]D[DF].{1,2}W.{3,7}[RV][DNSF]	PS00512	Alpha-galactosidase signature.
TREHALASE_1	PGGRF.E.Y.WD.Y	PS00927	Trehalase signature 1.
TREHALASE_2	QWD.P.[GAV]W[PAS]P	PS00928	Trehalase signature 2.
ALPHA_L_FUCOSIDASE	P.{2}L.{3}KWE.C	PS00385	Alpha-L-fucosidase putative active site.
GLYCOSYL_HYDROL_F1_1	[LIVMFSTC][LIVFYS][LIV][LIVMST]ENG[LIVMFAR][CSAGN]	PS00572	Glycosyl hydrolases family 1 active site.
GLYCOSYL_HYDROL_F1_2	F.[FYWM][GSTA].[GSTA].[GSTA]{2}[FYNH][NQ].E.[GSTA]	PS00653	Glycosyl hydrolases family 1 N-terminal signature.
GLYCOSYL_HYDROL_F2_1	N.[LIVMFYWD]R[STACN]{2}HYP.{4}[LIVMFYWS]{2}.{3}[DN].{2}G[LIVMFYW]{4}	PS00719	Glycosyl hydrolases family 2 signature 1.
GLYCOSYL_HYDROL_F2_2	[DENQLF][KRVW]N[HRY][STAPV][SAC][LIVMFS][LIVMFSA][LIVMFS]W[GSV].{2,3}NE	PS00608	Glycosyl hydrolases family 2 acid/base catalyst.
GLYCOSYL_HYDROL_F3	[LIVM]{2}[KR].[EQKRD].{4}G[LIVMFTC][LIVT][LIVMF][ST]D.{2}[SGADNIT]	PS00775	Glycosyl hydrolases family 3 active site.
GLYCOSYL_HYDROL_F4	[PS].[SAC].[LIVMFY]{2}[QN].{2}NP.{4}[TA].{9,11}[KRD].[LIV][GN].C	PS01324	Glycosyl hydrolases family 4 signature.
GLYCOSYL_HYDROL_F5	[LIV][LIVMFYWGA]{2}[DNEQG][LIVMGST][^SENR]NE[PV][RHDNSTLIVFY]	PS00659	Glycosyl hydrolases family 5 signature.
GLYCOSYL_HYDROL_F6_1	V.Y.{2}P.RDC[GSAF].{2}[GSA]{2}.G	PS00655	Glycosyl hydrolases family 6 signature 1.
GLYCOSYL_HYDROL_F6_2	[LIVMYA][LIVA][LIVT][LIV]EPD[SAL][LI][PSAG]	PS00656	Glycosyl hydrolases family 6 signature 2.
GLYCOSYL_HYDROL_F8	A[ST]D[AG]D.{2}[IM]A.[SA][LIVM][LIVMG].A.{3}[FW]	PS00812	Glycosyl hydrolases family 8 signature.
GLYCOSYL_HYDROL_F9_1	[STV].[LIVMFY][STV].{2}G.[NKR].{4}[PLIVM]H.R	PS00592	Glycosyl hydrolases family 9 active sites signature 1.
GLYCOSYL_HYDROL_F9_2	[FYW].D.{4}[FYW].{3}E.[STA].{3}N[STA]	PS00698	Glycosyl hydrolases family 9 active sites signature 2.
GLYCOSYL_HYDROL_F10	[GTA][^QNAG][^GSV][LIVN].[IVMF][ST]E[LIY][DN][LIVMF]	PS00591	Glycosyl hydrolases family 10 active site.
GLYCOSYL_HYDROL_F11_1	[PSA][LQ].E[YF]Y[LIVM]{2}[DE].[FYWHN]	PS00776	Glycosyl hydrolases family 11 active site signature 1.
GLYCOSYL_HYDROL_F11_2	[LIVMF].{2}E[AG][YWG][QRFGS][SG][STAN]G.[SAF]	PS00777	Glycosyl hydrolases family 11 active site signature 2.
GLYCOSYL_HYDROL_F16	E[LIV]D[LIVF].{0,1}E.{2}[GQ][KRNF].[PSTA]	PS01034	Glycosyl hydrolases family 16 active sites.
GLYCOSYL_HYDROL_F17	[LIVMKS].[LIVMFYWA]{3}[STAG]E[STACVI]G[WY]P[STN].[SAGQ]	PS00587	Glycosyl hydrolases family 17 signature.
GLYCOSYL_HYDROL_F25	D[LIVM].{3}[NQ][PGE].{9,15}[GR].{4}[LIVMFY]{2}K.[ST]E[GS].{2}[FYL].[DN]	PS00953	Glycosyl hydrolases family 25 active sites signature.
GLYCOSYL_HYDROL_F31_1	[GFY][LIVMF]W.DM[NSA]E	PS00129	Glycosyl hydrolases family 31 active site.
GLYCOSYL_HYDROL_F31_2	G[AVP][DT][LIVMTAS][CG]G[FY].{3}[STP].{3}L[CL].RW.{2}[LVMI][GSA][SA][FY].P[FY].R[DNA]	PS00707	Glycosyl hydrolases family 31 signature 2.
GLYCOSYL_HYDROL_F32	H.{2}[PV].{4}[LIVMA]NDPN[GA]	PS00609	Glycosyl hydrolases family 32 active site.
GLYCOSYL_HYDROL_F35	GGP[LIVM]{2}.{2}Q.ENE[FY]	PS01182	Glycosyl hydrolases family 35 putative active site.
GLYCOSYL_HYDROL_F39	W.FE.WNEP[DN]	PS01027	Glycosyl hydrolases family 39 active site.
GLYCOSYL_HYDROL_F45	[STA]TRY[FYW]D.{5}[CA]	PS01140	Glycosyl hydrolases family 45 active site.
CHITOSANASE_46_80	E[DNQ].{8,17}Y.{7}D.[RD][GP].[TS].{3}[AIVFLY]G.{5,11}D	PS60000	Chitosanases families 46 and 80 active sites signature.
TRANSGLYCOSYLASE	[LIVM].{3}ES.{3}[AP].{3}S.{5}G[LIVM][LIVMFYW].[LIVMFYW].{4}[SAG]	PS00922	Prokaryotic transglycosylases signature.
IUNH	D.D[PT][GA].DD[TAV][VI]A	PS01247	Inosine-uridine preferring nucleoside hydrolase family signature.
ALKYLBASE_DNA_GLYCOS	G[IV][GK].W[ST][AVI].[LIVMFY]{2}.[LIVM].{8}[MF].{2}[ED]D	PS00516	Alkylbase DNA glycosidases alkA family signature.
ZF_FPG_1	C.{1,4}C[GSANHK].{1,2}[IVML].{7,11}R[GSANPVLMT].{2}[FYWIL]C.{2}CQ	PS01242	Zinc finger FPG-type signature.
U_DNA_GLYCOSYLASE	[KR][LIVA][LIVC][LIVM].G[QI]DPY	PS00130	Uracil-DNA glycosylase signature.
ADOHCYASE_1	[GSAP][CS]N.[FYLM]S[ST][QALKHD][DENG].[AV][AVTS][ADERQ][ACSG][LIVMCG]	PS00738	S-adenosyl-L-homocysteine hydrolase signature 1.
ADOHCYASE_2	[GA][KSR].{3}[LIV].G[FY]G.[VC]G[KRL][GA].{1,2}[ASC]	PS00739	S-adenosyl-L-homocysteine hydrolase signature 2.
CYTOSOL_AP	[NS][TS]DAEGR[LVMI]	PS00631	Cytosol aminopeptidase signature.
PROLINE_PEPTIDASE	[HA][GSYR][LIVMT][SG]H.[LIV]G[LIVMNKS].[IVEL][HNC][DEV]	PS00491	Aminopeptidase P and proline dipeptidase signature.
MAP_1	[MFY].GHG[LIVMC][GSHN].{3}H.{4}[LIVM].{1,2}[HN][YWVHF]	PS00680	Methionine aminopeptidase subfamily 1 signature.
MAP_2	[DA][LIVMY].K[LIVM]D.G.[HQ][LIVMS][DNS]G.{3}[DN]	PS01202	Methionine aminopeptidase subfamily 2 signature.
RENAL_DIPEPTIDASE	[LIVM]EG[GA].{2}[LIVMF].{6}L.{3}Y.{2}G[LIVM]R	PS00869	Renal dipeptidase active site.
CARBOXYPEPT_SER_SER	[LIVM].[GSTA]ESY[AG][GS]	PS00131	Serine carboxypeptidases, serine active site.
CARBOXYPEPT_SER_HIS	[LIVF].{2}[LIVSTA].[IVPST].[GSDNQL][SAGV][SG]H.[IVAQ]P.{3}[PSA]	PS00560	Serine carboxypeptidases, histidine active site.
CARBOXYPEPT_ZN_1	[PK].[LIVMFY].[LIVMFY].{2}[^E].H[STAG].E.[LIVM][STAG][^L].{5}[LIVMFYTA]	PS00132	Zinc carboxypeptidases, zinc-binding region 1 signature.
CARBOXYPEPT_ZN_2	H[STAG][^ADNV][^VGFI][^YAR][LIVME][^SDEP].[LIVMFYW]P[FYW]	PS00133	Zinc carboxypeptidases, zinc-binding region 2 signature.
PYRASE_GLU	G.{2}[GAP].{4}[LIV][ST].E[KR][LIVC][AG].[NG]	PS01333	Pyrrolidone-carboxylate peptidase glutamic acid active site.
PYRASE_CYS	[LIVF].[GSAVC].[LIVM]S.[STAD]AG.[FY][LIVN]C[DNS]	PS01334	Pyrrolidone-carboxylate peptidase cysteine active site.
TRYPSIN_HIS	[LIVM][ST]A[STAG]HC	PS00134	Serine proteases, trypsin family, histidine active site.
TRYPSIN_SER	[DNSTAGC][GSTAPIMVQH].{2}G[DE]SG[GS][SAPHV][LIVMFYWH][LIVMFYSTANQH]	PS00135	Serine proteases, trypsin family, serine active site.
SUBTILASE_ASP	[STAIV][^ERDL][LIVMF][LIVM]D[DSTA]G[LIVMFC].{2,3}[DNH]	PS00136	Serine proteases, subtilase family, aspartic acid active site.
SUBTILASE_HIS	HG[STM].[VIC][STAGC][GS].[LIVMA][STAGCLV][SAGM]	PS00137	Serine proteases, subtilase family, histidine active site.
SUBTILASE_SER	GTS.[SA].P.[^L][STAVC][AG]	PS00138	Serine proteases, subtilase family, serine active site.
V8_HIS	[ST]G[LIVMFYW]{3}[GN].{2}T[LIVM].T.{2}H	PS00672	Serine proteases, V8 family, histidine active site.
V8_SER	T.{2}[GC][NQ]SGS.[LIVM][FY]	PS00673	Serine proteases, V8 family, serine active site.
PRO_ENDOPEP_SER	D.{3}A.{3}[LIVMFYW].{14}G.S.GG[LIVMFYW]{2}	PS00708	Prolyl endopeptidase family serine active site.
CLP_PROTEASE_SER	T.{2}[LIVMF]G.A[SAC]S[MSA][PAG][STA]	PS00381	Endopeptidase Clp serine active site.
CLP_PROTEASE_HIS	R.{3}[EAP].{3}[LIVMFYT][LM][LIVM]HQP	PS00382	Endopeptidase Clp histidine active site.
LON_SER	DG[PD]SA[GS][LIVMCA][TA][LIVM]	PS01046	ATP-dependent serine proteases, lon family, serine active site.
THIOL_PROTEASE_CYS	Q[^V].[^DE][GE][^F]C[YW][^DN].[STAGC][STAGCV]	PS00139	Eukaryotic thiol (cysteine) proteases cysteine active site.
THIOL_PROTEASE_HIS	[LIVMGSTAN][^IEVK]H[GSACE][LIVM][^GPSI][LIVMAT]{2}G[^SLAG][GSADNH]	PS00639	Eukaryotic thiol (cysteine) proteases histidine active site.
THIOL_PROTEASE_ASN	[FYCH][WI][LIVT].[KRQAG]N[ST]W.{3}[FYW]G.{2}G[LFYW][LIVMFYG].[LIVMF]	PS00640	Eukaryotic thiol (cysteine) proteases asparagine active site.
UCH_1	Q.{3}N[SA]CG.{3}[LIVM]{2}H[SA][LIVM][SA]	PS00140	Ubiquitin carboxyl-terminal hydrolase family 1 cysteine active-site.
UCH_2_1	G[LIVMFY].{1,3}[AGCY][NASMQG].C[FYWC][LIVMFCA][NSTAD][SACV].[LIVMSF][QF]	PS00972	Ubiquitin carboxyl-terminal hydrolases family 2 signature 1.
UCH_2_2	[YMF].[LK].[SAGNC][LIVMFT].{2}H.G.{4,6}GH[YF]	PS00973	Ubiquitin carboxyl-terminal hydrolases family 2 signature 2.
CASPASE_HIS	H.{2,4}[SC].{2}[^A].[LIVMF]{2}[ST]HG	PS01121	Caspase family histidine active site.
CASPASE_CYS	KPK[LIVMF][LIVMFY][LIVMF]{2}[QP][AF]C[RQG][GE]	PS01122	Caspase family cysteine active site.
ASP_PROTEASE	[LIVMFGAC][LIVMTADN][LIVFSA]D[ST]G[STAV][STAPDENQ][^GQ][LIVMFSTNC][^EGK][LIVMFGTA]	PS00141	Eukaryotic and viral aspartyl proteases active site.
OMPTIN_1	WTD.S.HP.T	PS00834	Aspartyl proteases, omptin family signature 1.
OMPTIN_2	AGYQE[ST]R[FYW]S[FYW][TN]A.GG[ST]Y	PS00835	Aspartyl proteases, omptin family signature 2.
ZINC_PROTEASE	[GSTALIVN][^PCHR][^KND]HE[LIVMFYW][^DEHRKP]H[^EKPC][LIVMFYWGSPQ]	PS00142	Neutral zinc metallopeptidases, zinc-binding region signature.
CYSTEINE_SWITCH	PRC[GN].P[DR][LIVSAPKQ]	PS00546	Matrixins cysteine switch.
INSULINASE	G.{8,9}G.[STA]H[LIVMFY][LIVMC][DERN][HRKL][LMFAT].[LFSTH].[GSTAN][GST]	PS00143	Insulinase family, zinc-binding region signature.
GLYCOPROTEASE	[KRC][GSAT].{4}[FYWLMH][DQNGKRH].P.[LIVMFY].{3}H.{2}[GSA]H[LIVMFA]	PS01016	Glycoprotease family signature.
PROTEASOME_A	[FYNAGS].{4}[STNLV].[FYW]S[PDS].{0,1}G[RKHDS].{2}Q[LIVA][DENR][YH][GSAD].{2}[GSAG]	PS00388	Proteasome A-type subunits signature.
PROTEASOME_B	[LIVMACFT][GSA][LIVMF].[FYLVGAC].{2}[GSACFYI][LIVMSTACF][LIVMSTAC][LIVMFSTAC][GACI][GSTACV][DES].{15,16}[RK].{12,13}G.{2}[GSTA]D	PS00854	Proteasome B-type subunits signature.
SPASE_I_1	[GS][^PR]SM[^RS][PS][AT][LF]	PS00501	Signal peptidases I serine active site.
SPASE_I_2	KR[LIVMSTA]{2}[GA].[PG]G[DEQ].[LIVM].[LIVMFY]	PS00760	Signal peptidases I lysine active site.
SPASE_I_3	[LIVMFYW]{2}[^NLPA][^T]GD[NH][^PIEW].{2}[SND].{2}[SG]	PS00761	Signal peptidases I signature 3.
SPASE_II	[LIVM].[GASF][GA][GAST][LIVMTF][GAS]N[LVMFGIA][LIVFYGT]D[RIK][LIVMFAC]	PS00855	Signal peptidases II signature.
PEPTIDASE_U32	E.F.{2}G[SA][LIVM]C.{4}G.C.[LIVM]S	PS01276	Peptidase family U32 signature.
AMIDASES	G[GAV]S[GS]{2}G.[GSAE][GSAVYCT].[LIVMT][GSA].{6}[GSAT].[GA].[DE].[GA].S[LIVM]R.P[GSACTL]	PS00571	Amidases signature.
ASN_GLN_ASE_1	[LIVM].[^L]TGGT[IV][AGS]	PS00144	Asparaginase / glutaminase active site signature 1.
ASN_GLN_ASE_2	[GA].[LIVM].{2}HGTDT[LIVM]	PS00917	Asparaginase / glutaminase active site signature 2.
UREASE_1	T[AY][GA][GATR][LIVMF]D.H[LIVM]H.{3}[PA]	PS01120	Urease nickel ligands signature.
UREASE_2	[LIVM]{2}[CT]H[HNG]L.{3}[LIVM].{2}D[LIVM].F[AS]	PS00145	Urease active site.
ARGE_DAPE_CPG2_1	[LIV][GALMY][LIVMF][^Q][GSA]H.D[TV][STAV]	PS00758	ArgE / dapE / ACY1 / CPG2 / yscS family signature 1.
ARGE_DAPE_CPG2_2	[GSTAI][SANQCVIT]D.K[GSACN].{1,2}[LIVMA].{2}[LIVMFY].{12,17}[LIVM].[LIVMF][LIVMSTAGC][LIVMFA].{2}[DNGM]EE.{0,1}[GSTNE]	PS00759	ArgE / dapE / ACY1 / CPG2 / yscS family signature 2.
DIHYDROOROTASE_1	D[LIVMFYWSAP]H[LIVA]H[LIVF][RN].[PGANF]	PS00482	Dihydroorotase signature 1.
DIHYDROOROTASE_2	[GAVS][ST]D.APH.{4}K	PS00483	Dihydroorotase signature 2.
BETA_LACTAMASE_A	[FY].[LIVMFY][^E]S[TV].K.{3}[^T][AGLM][^D][^KA][LC]	PS00146	Beta-lactamase class-A active site.
BETA_LACTAMASE_C	[FY]E[LIVM]GS[LIVMG][SA]K	PS00336	Beta-lactamase class-C active site.
BETA_LACTAMASE_D	[PA].S[ST]FK[LIV][PALV].[STA][LI]	PS00337	Beta-lactamase class-D active site.
BETA_LACTAMASE_B_1	[LI].[STN][HN].H[GSTAD]D.{2}G[GP].{7,8}[GS]	PS00743	Beta-lactamases class B signature 1.
BETA_LACTAMASE_B_2	P.{3}[LIVM]{2}.G.C[LIVMF]{2}K	PS00744	Beta-lactamases class B signature 2.
ARGINASE_1	[LIVMF]GG.H.[LIVMT][STAV].[PAG].[^A].[GSTA]	PS00147	Arginase family signature 1.
ARGINASE_2	[LIVM]{2}[^A][LIVMFY]D[AS]H.D	PS00148	Arginase family signature 2.
ARGINASE_3	[ST][LIVMFY]D[LIVM]D.{3}[PAQ].{3}P[GSA].{7}G	PS01053	Arginase family signature 3.
A_DEAMINASE	[SA][LIVM][NGS][STA]DDP	PS00485	Adenosine and AMP deaminase signature.
CYT_DCMP_DEAMINASES	[CH][AGV]E.{2}[LIVMFGAT][LIVM].{17,33}PC.{2,8}C.{3}[LIVM]	PS00903	Cytidine and deoxycytidylate deaminases zinc-binding region signature.
GTP_CYCLOHYDROL_1_1	[DENGQST][LIVMPF][LIVM].{1,2}[KRNQELD][DENKGS][LIVM].{3}[STG].C[EP]HH	PS00859	GTP cyclohydrolase I signature 1.
GTP_CYCLOHYDROL_1_2	[SA].[RK].Q[LIVMT]QE[RNAK][LIM][TSNV]	PS00860	GTP cyclohydrolase I signature 2.
NITRIL_CHT_1	G.{2}[LIVMFY]{2}.[IF].E.{2}[LIVM].GYP	PS00920	Nitrilases / cyanide hydratase signature 1.
NITRIL_CHT_2	G[GAQ].{2}C[WA]E[NH].{2}[PST][LIVMFYS].[KR]	PS00921	Nitrilases / cyanide hydratase active site signature.
PPASE	D[SGDN]D[PE][LIVMF]D[LIVMGAC]	PS00387	Inorganic pyrophosphatase signature.
ACYLPHOSPHATASE_1	[LIV].G.VQ[GH]V.[FM]R	PS00150	Acylphosphatase signature 1.
ACYLPHOSPHATASE_2	G[FYW][AVC][KRQAM]N.{3}G.V.{5}G	PS00151	Acylphosphatase signature 2.
ATPASE_ALPHA_BETA	P[SAP][LIV][DNH][^LKGN][^F][^S]S[^DCPH]S	PS00152	ATP synthase alpha and beta subunits signature.
ATPASE_GAMMA	[IV]T.E.{2}[DE].{3}GA.[SAKR]	PS00153	ATP synthase gamma subunit signature.
ATPASE_DELTA	[LIVM].[LIVMFYT].{3}[LIVMT][DENQK].[^G][LIVM].[GSA]G[LIVMFYGA][^S][LIVM][KRHENQ].[GSEN]	PS00389	ATP synthase delta (OSCP) subunit signature.
ATPASE_A	[STAGN][^E][STAG][LIVMF]RL[^LP][SAGV]N[LIVMT]	PS00449	ATP synthase a subunit signature.
ATPASE_C	[GSTA]R[NQ]P.{5}[^A].[^F].{2}[LIVMFYW]{2}.{3}[LIVMFYW].[DE]	PS00605	ATP synthase c subunit signature.
ATPASE_E1_E2	DKTGT[LIVM][TI]	PS00154	E1-E2 ATPases phosphorylation site.
ATPASE_NA_K_BETA_1	[FYWMV].{2}[FYWM].[FYW][DN].{6}[LIVMF][GA]RT.{3}[WRL]	PS00390	Sodium and potassium ATPases beta subunits signature 1.
ATPASE_NA_K_BETA_2	[RK].{2}C[RKQWI].{5}L.{2}C[SA]G	PS00391	Sodium and potassium ATPases beta subunits signature 2.
GDA1_CD39_NTPASE	[LIVM].G.{2}EG.[FYLS].[FW][LIVA][TAG].N[HYF]	PS01238	GDA1/CD39 family of nucleoside phosphatases signature.
T4_DEIODINASE	RPL[IV].[NS]FGS[CA][TS]CP.F	PS01205	Iodothyronine deiodinases active site.
CUTINASE_1	P.[STA].[LIV][IVT].[GS]GYS[QL]G	PS00155	Cutinase, serine active site.
CUTINASE_2	C.{3}D.[IV]C.G[GST].{2}[LIVM].{2,3}H	PS00931	Cutinase, aspartate and histidine active sites.
DDC_GAD_HDC_YDC	S[LIVMFYW].[^KG].{3}K[LIVMFYWGH][LIVMFYWG].[^R].[LIVMFYW][^V][CA].{2}[LIVMFYWQ][^K].[RK]	PS00392	DDC / GAD / HDC / TyrDC pyridoxal-phosphate attachment site.
OKR_DC_1	[STAV].S.HK.{2}[GSTAN]{2}.[STA]Q[STA]{2}	PS00703	Orn/Lys/Arg decarboxylases family 1 pyridoxal-P attachment site.
ODR_DC_2_1	[FY][PA].K[SACV][NHCLFW].{4}[LIVMF][LIVMTA].{2}[LIVMA].{3}[GTE]	PS00878	Orn/DAP/Arg decarboxylases family 2 pyridoxal-P attachment site.
ODR_DC_2_2	[GSA].{2,6}[LIVMSCP].[^N][LIVMF][DNS][LIVMCA]GGG[LIVMFY][GSTPCEQ]	PS00879	Orn/DAP/Arg decarboxylases family 2 signature 2.
OMPDECASE	[LIVMFTAR][LIVMF].D.K.{2}D[IV][ADGP].T[CLIVMNTA]	PS00156	Orotidine 5'-phosphate decarboxylase active site.
PEPCASE_1	[VTI].TAHPT[EQ].{2}R[KRHAQ]	PS00781	Phosphoenolpyruvate carboxylase active site 1.
PEPCASE_2	[IVLC]M[LIVM]GYSDS.K[DF][STAG]G	PS00393	Phosphoenolpyruvate carboxylase active site 2.
PEPCK_GTP	[FY]PS[AGMS]CGKT[NS]	PS00505	Phosphoenolpyruvate carboxykinase (GTP) signature.
PEPCK_ATP	LIGDDEH.W.[DEPKVNA].[GVS][IV].N	PS00532	Phosphoenolpyruvate carboxykinase (ATP) signature.
UROD_1	[SP][IVCLAM]W[LIVMFYC][LM]R[QR][AVS]GR	PS00906	Uroporphyrinogen decarboxylase signature 1.
UROD_2	[LIMF][GAVS]F[STAGCV][STAGC].[PA][FWYV]T[LIVM].{2}Y.{2,3}[ADE][GK]	PS00907	Uroporphyrinogen decarboxylase signature 2.
IGPS	[LIVMFY][LIVMC].E[LIVMFYC]K[KRSPQV][STAHKRYC]SP[STRK].{3,7}[LIVMFYST]	PS00614	Indole-3-glycerol phosphate synthase signature.
RUBISCO_LARGE	G.[DN]F.K.DE	PS00157	Ribulose bisphosphate carboxylase large chain active site.
ADOMETDC	[SA][FY][LIV]L[STN]ESS[LIVMF]F[LIV]	PS01336	S-adenosylmethionine decarboxylase signature.
PHOSPHOKETOLASE_1	EGGELGY	PS60002	Phosphoketolase signature 1.
PHOSPHOKETOLASE_2	G.{3}[DN].P.{2}[LIVFT].{3}[LIVM].GDGE	PS60003	Phosphoketolase signature 2.
ALDOLASE_CLASS_I	[LIVM].[LIVMFYW]EG.[LSI]LK[PA][SN]	PS00158	Fructose-bisphosphate aldolase class-I active site.
ALDOLASE_CLASS_II_1	[FYVMT].{1,3}[LIVMH][APNT][LIVM].{1,2}[LIVM]H.DH[GACH]	PS00602	Fructose-bisphosphate aldolase class-II signature 1.
ALDOLASE_CLASS_II_2	[LIVM]E.E[LIVM]G.{2}[GM][GSTA].E	PS00806	Fructose-bisphosphate aldolase class-II signature 2.
MALATE_SYNTHASE	[KR][DENQ][HN].{2}GLN.G.WDY[LIVM]F	PS00510	Malate synthase signature.
HMG_COA_LYASE	SVAGLGGCPY	PS01062	Hydroxymethylglutaryl-coenzyme A lyase active site.
HMG_COA_SYNTHASE	N.[DN][IV]EG[IV]D.{2}NAC[FY].G	PS01226	Hydroxymethylglutaryl-coenzyme A synthase active site.
CITRATE_SYNTHASE	G[FYAV][GA]H.[IV].{1,2}[RKTQ].{2}[DV][PS]R	PS00480	Citrate synthase signature.
AIPM_HOMOCIT_SYNTH_1	LR[DE]G.Q.{4}[^L].{5}K	PS00815	Alpha-isopropylmalate and homocitrate synthases signature 1.
AIPM_HOMOCIT_SYNTH_2	[LIVMFW].{2}H.H[DN]D.G.[GAS].[GASLI]	PS00816	Alpha-isopropylmalate and homocitrate synthases signature 2.
ALDOLASE_KDPG_KHG_1	G[LIVM].{3}E[LIV]T[LF]R	PS00159	KDPG and KHG aldolases active site.
ALDOLASE_KDPG_KHG_2	G.{3}[LIVMF]K[LF]FP[SA].{3}G	PS00160	KDPG and KHG aldolases Schiff-base forming residue.
ISOCITRATE_LYASE	K[KR]CGH[LMQR]	PS00161	Isocitrate lyase signature.
BETA_ELIM_LYASE	[YV].D.{3}MS[GA]KKD.[LIVMF][LIVMAG].[LIVM]GG	PS00853	Beta-eliminating lyases pyridoxal-phosphate attachment site.
DNA_PHOTOLYASES_1_1	TG.P[LIVM]{2}DA.M[RA].[LIVM]	PS00394	DNA photolyases class 1 signature 1.
DNA_PHOTOLYASES_1_2	[DN]R.R[LIVM][LIVMN].[STA][STAQ]F[LIVMFA].K.L.{2,3}W[KRQ]	PS00691	DNA photolyases class 1 signature 2.
DNA_PHOTOLYASES_2_1	F.EE.[LIVM]{2}RREL.{2}NF	PS01083	DNA photolyases class 2 signature 1.
DNA_PHOTOLYASES_2_2	G.HD.{2}W.ER.[LIVM]FGK[LIVM]R[FY]MN	PS01084	DNA photolyases class 2 signature 2.
ALPHA_CA_1	SE[HN].[LIVM].{4}[FYH].{2}E[LIVMGA]H[LIVMFA]{2}	PS00162	Alpha-carbonic anhydrases signature.
PROK_CO2_ANHYDRASE_1	C[SA]DSR[LIVM].[AP]	PS00704	Prokaryotic-type carbonic anhydrases signature 1.
PROK_CO2_ANHYDRASE_2	[EQ][YF]A[LIVM].{2}[LIVM].{4}[LIVMF]{3}.GH.{2}CG	PS00705	Prokaryotic-type carbonic anhydrases signature 2.
FUMARATE_LYASES	GS.{2}M.[^RS]K.N	PS00163	Fumarate lyases signature.
ACONITASE_1	[LIVM].{2}[GSACIVM].[LIV][GTIV][STP]C.{0,1}TN[GSTANI].{4}[LIVMA]	PS00450	Aconitase family signature 1.
ACONITASE_2	G.{2}[LIVWPQT].{3}[GACST]C[GSTAM][LIMPTA]C[LIMV][GA]	PS01244	Aconitase family signature 2.
ILVD_EDD_1	CDK.{2}P[GA].{3}[GA]	PS00886	Dihydroxy-acid and 6-phosphogluconate dehydratases signature 1.
ILVD_EDD_2	[SGALC][LIMF][LIVMF]TD[GA]R[LIVMFY]S[GA][GAV][ST]	PS00887	Dihydroxy-acid and 6-phosphogluconate dehydratases signature 2.
DEHYDROQUINASE_I	D[LIVM][DE][LIVMN].{18,20}[LIVM]{2}.[SC][NHY]H[DN]	PS01028	Dehydroquinase class I active site.
DEHYDROQUINASE_II	[LIVM][NQHS]GPN[LVI].{2}[LT]G.R[QED].{3}[FY]G	PS01029	Dehydroquinase class II signature.
ENOLASE	[LIVTMS][LIVP][LIV][KQ].[ND]Q[INV][GA][ST][LIVM][STL][DERKAQG][STA]	PS00164	Enolase signature.
DEHYDRATASE_SER_THR	[DESH].{4,5}[STVG][^EVKD][AS][FYI]K[DLIFSA][RLVMF][GA][LIVMGA]	PS00165	Serine/threonine dehydratases pyridoxal-phosphate attachment site.
ENOYL_COA_HYDRATASE	[LIVM][STAG].[LIVM][DENQRHSTA]G.{3}[AG]{3}.{4}[LIVMST].[CSTA][DQHP][LIVMFYA]	PS00166	Enoyl-CoA hydratase/isomerase signature.
IGP_DEHYDRATASE_1	[LIVMY][DE].HH.{2}E.{2}[GCA][LIVM][STAVCL][LIVMF]	PS00954	Imidazoleglycerol-phosphate dehydratase signature 1.
IGP_DEHYDRATASE_2	[GW].[DNIE].HH.{2}E[STAGC].[VMFYHS]K	PS00955	Imidazoleglycerol-phosphate dehydratase signature 2.
TRP_SYNTHASE_ALPHA	[LIVM]E[LIVM][GQ].{2}[FYCHTWP][STPK][DEKY][PA][LIVMYGK][SGALIMY][DE][GN]	PS00167	Tryptophan synthase alpha chain signature.
TRP_SYNTHASE_BETA	[LIVMYAHQ].[HPYNVF].G[STA]HK.N.{2}[LIVM].[QEH]	PS00168	Tryptophan synthase beta chain pyridoxal-phosphate attachment site.
D_ALA_DEHYDRATASE	G.D.[LIVM]{2}[IV]KP[GSA].{2}Y	PS00169	Delta-aminolevulinic acid dehydratase active site.
UROCANASE	RDH.D.[GS][GS].{2}SP.RET	PS01233	Urocanase signature.
PREPHENATE_DEHYDR_1	[FY].[LIVM].{2}[LIVM].{5}[DN].{5}TRF[LIVMW].[LIVM]	PS00857	Prephenate dehydratase signature 1.
PREPHENATE_DEHYDR_2	[LIVM][ST][KR][LIVMF]E[ST]RP	PS00858	Prephenate dehydratase signature 2.
DHDPS_1	[GSA][LIVM][LIVMFY].{2}G[ST][TG]GE[GASNF].{6}[EQ]	PS00665	Dihydrodipicolinate synthetase signature 1.
DHDPS_2	Y[DNSAH][LIVMFAN]P.{2}[STAV].{2,3}[LIVMFT].{13,14}[LIVMCF].[SGA][LIVMFNS]K[DEQAFYH][STACI]	PS00666	Dihydrodipicolinate synthetase signature 2.
PSI_RLU	[LIVCA][NHYTQ]R[LI][DG].{2}[TV][STAC]G[LIVAGC][LIVMF]{2}[LIVMFGCA][SGTACV]	PS01129	Rlu family of pseudouridine synthase signature.
PSI_RSU	GRLD.{2}[STA].G[LIVFA][LIVMF]{3}[ST][DNST]	PS01149	Rsu family of pseudouridine synthase signature.
CYS_SYNTHASE	K.E.{3,4}[PAF][STAGC].S[IVAPM]K.R.[STAG].{2}[LIVM]	PS00901	Cysteine synthase/cystathionine beta-synthase P-phosphate attachment site.
METHYLGLYOXAL_SYNTH	[SP]GP[LIVMWY]GGD.{0,1}Q	PS01335	Methylglyoxal synthase active site.
PAL_HISTIDASE	[GS][STG][LIVM][STG][SAC]SG[DH]L.[PN]L[SA].{2,3}[SAGVTL]	PS00488	Phenylalanine and histidine ammonia-lyases signature.
PORPHOBILINOGEN_DEAM	E[KR].[LIVMFAT].{3}[LIVMFAC].[GSALV][GSANHD]C.[IVTACS][PLA][LIVMF][GSA]	PS00533	Porphobilinogen deaminase cofactor-binding site.
CYS_MET_METAB_PP	[DQ][LIVMFY].{3}[STAGCN][STAGCIL]TK[FYWQI][LIVMF].G[HQD][SGNH]	PS00868	Cys/Met metabolism enzymes pyridoxal-phosphate attachment site.
GLYOXALASE_I_1	[HQ][IVT].[LIVFY].[IV].{4}[^E][STA].{2}F[YM].{2,3}[LMF]G[LMF]	PS00934	Glyoxalase I signature 1.
GLYOXALASE_I_2	G[NTKQ].{0,5}[GA][LVFY][GH]H[IVF][CGA].[STAGLE].{2}[DNC]	PS00935	Glyoxalase I signature 2.
CYTO_HEME_LYASE_1	HN.{2}NE.{2}W[NQKRS].{4}WE	PS00821	Cytochrome c and c1 heme lyases signature 1.
CYTO_HEME_LYASE_2	PFDRHDW	PS00822	Cytochrome c and c1 heme lyases signature 2.
ADENYLATE_CYCLASE_1_1	EYFG[SA]{2}LW.LYK	PS01092	Adenylate cyclase class-I signature 1.
ADENYLATE_CYCLASE_1_2	YRN.W[NS]E[LIVM]RTLHF.G	PS01093	Adenylate cyclase class-I signature 2.
GUANYLATE_CYCLASE_1	[GD][VI][LIVM].{0,1}[GS].{5}[FY].[LIVM][FYWL][GS][DNTHKWE][DNTAS][IV][DNTAY].{5}[DEC]	PS00452	Guanylate cyclase signature.
CHORISMATE_SYNTHASE_1	G[DES]SH[GC].{2}[LIVM][GTIVLAMS].[LIVTM][LIVM][DEST][GH].[PV]	PS00787	Chorismate synthase signature 1.
CHORISMATE_SYNTHASE_2	[GE].{2}S[AG]R.[ST].{3}[VT].{2}[GA][STAVY][LIVMF]	PS00788	Chorismate synthase signature 2.
CHORISMATE_SYNTHASE_3	R[SHF]D[PSV][CSAVT].{4}[SGAIVM].[IVGSTAPM][LIVM].E[STAHNCG][LIVMA]	PS00789	Chorismate synthase signature 3.
PTPS_1	CNN.{2}GHGHNY	PS00987	6-pyruvoyl tetrahydropterin synthase signature 1.
PTPS_2	DHKNLD.D	PS00988	6-pyruvoyl tetrahydropterin synthase signature 2.
FERROCHELATASE	[LIVMF][LIVMFC].[ST].H[GS][LIVM]P.{4,5}[DENQKRLHAFSTI].[GN][DPC].{1,4}[YA]	PS00534	Ferrochelatase signature.
ALANINE_RACEMASE	[SACVLG][AIPTV].{0,1}K[ADGS][DEN][GA]YG[HACILN][GD]	PS00395	Alanine racemase pyridoxal-phosphate attachment site.
DAP_EPIMERASE	N.[DN][GS][SENGFT].{4}C[GI]N[GA].R	PS01326	Diaminopimelate epimerase signature.
ASP_GLU_RACEMASE_1	[IVA][LIVM].C.{0,1}N[ST][MSA][STH][LIVFYSTANK]	PS00923	Aspartate and glutamate racemases signature 1.
ASP_GLU_RACEMASE_2	[LIVM]{2}.[AG]CT[DEH][LIVMFY][PNGRS].[LIVM]	PS00924	Aspartate and glutamate racemases signature 2.
MR_MLE_1	[AT].[SAGCN][SAGC][LIVM][DEQ].A[LA].[DE][LIA].[GA][KRQ].{4}[PSA][LIV].{2}L[LIVMF]G	PS00908	Mandelate racemase / muconate lactonizing enzyme family signature 1.
MR_MLE_2	[LIVF].{2}D.[NH].{7}[ACL].{6}[LIVMF].{7}[LIVM]E[DENQ]P	PS00909	Mandelate racemase / muconate lactonizing enzyme family signature 2.
RIBUL_P_3_EPIMER_1	[LIVMF]H[LIVMFY]D[LIVM].D.{1,2}[FY][LIVM].N.[STAV]	PS01085	Ribulose-phosphate 3-epimerase family signature 1.
RIBUL_P_3_EPIMER_2	[LIVMA].[LIVM]M[ST][VS].P.{3}[GN]Q.{0,1}[FMK].{6}[NKR][LIVMC]	PS01086	Ribulose-phosphate 3-epimerase family signature 2.
ALDOSE_1_EPIMERASE	[NS].TNH.Y[FW]N[LI]	PS00545	Aldose 1-epimerase putative active site.
CSA_PPIASE_1	[FY].{2}[STCNLVA].[FV]H[RH][LIVMNS][LIVM].{2}F[LIVM].Q[AGFT]G	PS00170	Cyclophilin-type peptidyl-prolyl cis-trans isomerase signature.
PPIC_PPIASE_1	F[GSADEI].[LVAQ]A.{3}[ST].{3,4}[STQ].{3,5}[GER]G.[LIVM][GS]	PS01096	PpiC-type peptidyl-prolyl cis-trans isomerase signature.
TIM	[AVG][YLV]EP[LIVMEPKST][WYEAS][SAL][IV][GN][TEKDVS][GKNAD]	PS00171	Triosephosphate isomerase active site.
XYLOSE_ISOMERASE_1	[LI]EPKP.{2}P	PS00172	Xylose isomerase signature 1.
XYLOSE_ISOMERASE_2	[FL]HD[^K]D[LIV].[PD].[GDE]	PS00173	Xylose isomerase signature 2.
PMI_I_1	Y.D.NHKPE	PS00965	Phosphomannose isomerase type I signature 1.
PMI_I_2	HAY[LIVM].G.{2}[LIVM]E.MA.SDN.[LIVM]RAG.TPK	PS00966	Phosphomannose isomerase type I signature 2.
P_GLUCOSE_ISOMERASE_1	[DENSA].[LIVM][GP]GR[FY][ST][LIVMFSTAP].[GSTA][PSTACM][LIVMSA][GSAN]	PS00765	Phosphoglucose isomerase signature 1.
P_GLUCOSE_ISOMERASE_2	[GSA].[LIVMCAYQS][LIVMFYWN].{4}[FY][DNTH]Q.[GA][IV][EQST].{2}K	PS00174	Phosphoglucose isomerase signature 2.
GLC_GALNAC_ISOMERASE	[LIVM].{3}[GNH].{0,1}[LITCRV].[LIVWF].[LIVMF].[GS][LIVM]G.[DENV]G[HN]	PS01161	Glucosamine/galactosamine-6-phosphate isomerases signature.
PG_MUTASE	[LIVM].RHG[EQ].[^Y].N	PS00175	Phosphoglycerate mutase family phosphohistidine signature.
PGM_PMM	[GSA][LIVMF].[LIVM][ST][PGA]SH[NIC]P	PS00710	Phosphoglucomutase and phosphomannomutase phosphoserine signature.
METMALONYL_COA_MUTASE	RIARN[TQ].{2}[LIVMFY]{2}.[EQH]E.{4}[KRN].{2}DP.[GSA]GS	PS00544	Methylmalonyl-CoA mutase signature.
TERPENE_SYNTHASES	[DE]GSW.[GE].W[GA][LIVM].[FY].Y[GA]	PS01074	Terpene synthases signature.
TOPOISOMERASE_I_EUK	[DEN].{6}[GS][IT]SK.{2}Y[LIVM].{3}[LIVM]	PS00176	Eukaryotic DNA topoisomerase I active site.
TOPOISOMERASE_I_PROK	[EQ].LY[DEQSTLM].{3,12}[LIVST][ST]Y.R[ST][DEQSN]	PS00396	Prokaryotic DNA topoisomerase I active site.
TOPOISOMERASE_II	[LIVMA][^R]EG[DN]SA[^F][STAG]	PS00177	DNA topoisomerase II signature.
AA_TRNA_LIGASE_I	P.{0,2}[GSTAN][DENQGAPK].[LIVMFP][HT][LIVMYAC]G[HNTG][LIVMFYSTAGPC]	PS00178	Aminoacyl-transfer RNA synthetases class-I signature.
WHEP_TRS_1	[QYR][GH][DNEAR].[LIV][KR].{2}K.{2}[KRNG][AS].{4}[LIV][DENKA].{2}[IV].{2}L.{3}K	PS00762	WHEP-TRS domain signature.
SUCCINYL_COA_LIG_1	S[KR]SG[GT][LIVM][GST].[EQ].{8,10}G.{4}[LIVM][GA][LIVM]GGD	PS01216	ATP-citrate lyase / succinyl-CoA ligases family signature 1.
SUCCINYL_COA_LIG_2	G.{2}A.{4,7}[RQT][LIVMF]GH[AS][GH]	PS00399	ATP-citrate lyase / succinyl-CoA ligases family active site.
SUCCINYL_COA_LIG_3	G.[IVT].{2}[LIVMF].[NAK][GS][GA]G[LMAI][STAV].{4}[DN].[LIVM].{3,4}[GD][GREAK]	PS01217	ATP-citrate lyase / succinyl-CoA ligases family signature 3.
GLNA_1	[FYWL]DGSS.{6,8}[DENQSTAK][SA][DE].{2}[LIVMFY]	PS00180	Glutamine synthetase signature 1.
GLNA_ATP	KP[LIVMFYA].{3,5}[NPAT][GA][GSTAN][GA].H.{3}S	PS00181	Glutamine synthetase putative ATP-binding region signature.
GLNA_ADENYLATION	K[LIVM].{5}[LIVMA]D[RK][DN][LI]Y	PS00182	Glutamine synthetase class-I adenylation site.
DALA_DALA_LIGASE_1	H[GN].{2}[GC]E[DNT]G.[LIVMAFT][QSAPH][GSA]	PS00843	D-alanine--D-alanine ligase signature 1.
DALA_DALA_LIGASE_2	[LIVAMSFT].{3}[GAHDVSI].[GSAIVCT]R[LIVMCAFST][DE][LIVMFAYGT][LIVMFAR].{7,12}[LIVWCAF].[EK][LIVAPMT]N[STPA].P[GA]	PS00844	D-alanine--D-alanine ligase signature 2.
SAICAR_SYNTHETASE_1	[LIVMRPA][LIVFY][PLNRKG][LIVMF]E.[IV][LVCATI]R.{3}[TAEYSI]G[ST]	PS01057	SAICAR synthetase signature 1.
SAICAR_SYNTHETASE_2	[LI][IVCAP]D.K[LIFY]E[FI]G	PS01058	SAICAR synthetase signature 2.
FOLYLPOLYGLU_SYNT_1	[LIVMFY].[LIVM][STAG]GT[NK]GK.[STG].{4}[^A].[^EAD][LIVM]{2}.{3,4}[GSKQT]	PS01011	Folylpolyglutamate synthase signature 1.
FOLYLPOLYGLU_SYNT_2	[LIVMFY]{2}[EK].G[LIVM][GA]G.{2}D.[GST].[LIVM]{2}	PS01012	Folylpolyglutamate synthase signature 2.
UBIQUITIN_ACTIVAT_1	K[AI][CL]SGK[FI].[PQ]	PS00536	Ubiquitin-activating enzyme signature 1.
UBIQUITIN_ACTIVAT_2	P[LIVMG]CT[LIVM][KRHA].[FTNM]P	PS00865	Ubiquitin-activating enzyme active site.
UBIQUITIN_CONJUGAT_1	[FYWLSP]H[PC][NHL][LIV].{3,4}G.[LIVP]C[LIV].{1,2}[LIVR]	PS00183	Ubiquitin-conjugating enzymes active site.
FTHFS_1	[GN][LIVMS]KG[GST][AG][AST]G[GAS]G[YLHRKF]	PS00721	Formate--tetrahydrofolate ligase signature 1.
FTHFS_2	V[ASV][TS][IVLA][RQ][AGS][LIM][KER].[HN][GAS][GLKD]	PS00722	Formate--tetrahydrofolate ligase signature 2.
ADENYLOSUCCIN_SYN_1	[QGF][WLCF]GDE[GA]K[GA]	PS01266	Adenylosuccinate synthetase GTP-binding site.
ADENYLOSUCCIN_SYN_2	GI[GR]P.Y.{2}K.{2}R	PS00513	Adenylosuccinate synthetase active site.
ARGININOSUCCIN_SYN_1	[ASL][FY]SGG[LV]DT[ST]	PS00564	Argininosuccinate synthase signature 1.
ARGININOSUCCIN_SYN_2	G.T.[KRM]GND.{2}RF	PS00565	Argininosuccinate synthase signature 2.
GARS	R[LF]GDPE.[EQIM]	PS00184	Phosphoribosylglycinamide synthetase signature.
CPSASE_1	[FYV][PS][LIVMC][LIVMA][LIVM][KR][PSA][STA].{3}[SG]G.[AG]	PS00866	Carbamoyl-phosphate synthase subdomain signature 1.
CPSASE_2	[LIVMF][LIMN]E[LIVMCA]N[PATLIVM][KR][LIVMSTAC]	PS00867	Carbamoyl-phosphate synthase subdomain signature 2.
DNA_LIGASE_A1	[EDQH][^K]K[^VEDI][DN]G[^GLYN]R[GACIVM]	PS00697	ATP-dependent DNA ligase AMP-binding site.
DNA_LIGASE_A2	EG[LIVMA][LIVM][LIVMA][KR].{5,8}[YW][QNEKTI].{2,6}[KRH].{3,5}K[LIVMFY]K	PS00333	ATP-dependent DNA ligase signature 2.
DNA_LIGASE_N1	K[LIVMF]DG[LIVMAS][SAG].{4}Y.{2}[GRD].[LF].{4}[ST]RG[DN]G.{2}G[DE][DENL]	PS01055	NAD-dependent DNA ligase signature 1.
DNA_LIGASE_N2	[IV]G[KR][ST]G.[LIVM][STNK].[VTLYF].{2}[LVMF].[PS][IV]	PS01056	NAD-dependent DNA ligase signature 2.
RTC	[RH]G.{2}P.G{3}.[LIV]	PS01287	RNA 3'-terminal phosphate cyclase signature.
LIPB	RGG.{2}T[FYWCAH]H.{2}[GHS]Q.[LIVMT].Y	PS01313	Lipoate-protein ligase B signature.
GATB	[LMFYCVI][DN]R.{3}[PGA]L[LIVMCA]E[LIVMT].[STL].[PA]	PS01234	Glutamyl-tRNA(Gln) amidotransferase subunit B signature.
RECOMBINASES_1	Y[LIVAC]R[VA]S[ST].{2}Q	PS00397	Site-specific recombinases active site.
RECOMBINASES_2	G[DE].{2}[LIVM][^E].[^V][LIVM][DT]R[LIVM][GSA]	PS00398	Site-specific recombinases signature 2.
TRANSPOSASE_MUTATOR	D.{3}G[LIVMF].{6}[STAV][LIVMFYW][PT].[STAV].{2}[QR].C.{2}H	PS01007	Transposases, Mutator family, signature.
TRANSPOSASE_IS30	RG.{2}EN.NG[LIVM]{2}R[QE][LIVMFY]{2}PK	PS01043	Transposases, IS30 family, signature.
AUTOINDUCER_SYNTH_1	[LMFYA]R.{3}F.{2}[KRQ].{2}W.[LIVM].{6,9}E.D.[FY]D	PS00949	Autoinducers synthetase family signature.
TPP_ENZYMES	[LIVMF][GSA].{5}P.{4}[LIVMFYW].[LIVMF].GD[GSA][GSAC]	PS00187	Thiamine pyrophosphate enzymes signature.
BIOTIN	[GDN][DEQTR].[LIVMFY].{2}[LIVM].[AIV]MK[LVMAT].{3}[LIVM].[SAV]	PS00188	Biotin-requiring enzymes attachment site.
LIPOYL	[GDN].{2}[LIVF].{3}[^VH][^M][LIVMFCA].{2}[LIVMFA][^LDFY][^KPE].K[GSTAIVW][STAIVQDN].{2}[LIVMFS].{5}[GCN].[LIVMFY]	PS00189	2-oxo acid dehydrogenases acyltransferase component lipoyl binding site.
AMP_BINDING	[LIVMFY][^E][^VES][STG][STAG]G[ST][STEI][SG].[PASLIVM][KR]	PS00455	Putative AMP-binding domain signature.
MOCF_BIOSYNTHESIS_1	[LIVMCA][LIVM]{2}[LITF][LITN]GGTG.{4}D	PS01078	Molybdenum cofactor biosynthesis proteins signature 1.
MOCF_BIOSYNTHESIS_2	S.[GS].{2}D.{5}[LIVW].{10,12}[LIV].{2}[KR]PG[KRL]P.{2}[LIVMF][GA]	PS01079	Molybdenum cofactor biosynthesis proteins signature 2.
MOAA_NIFB_PQQE	[LIV].{3}C[NDP][LIVMF][DNQRS]C.[FYM]C	PS01305	moaA / nifB / pqqE family signature.
RADICAL_ACTIVATING	[GVPS].[GKS].[KRS].{3}[FL].{2}G.{0,1}C.{3}C.{2}C.[NLF]	PS01087	Radical activating enzymes signature.
TPX	S.DLPF[AS].{2}[KRQ][FWI]C	PS01265	Tpx family signature.
ISPF	S[DN][GA]D[LIVAP][LIVAG].H[STAC].{2}[DNT][SAG].{2}[SGA]	PS01350	2C-methyl-D-erythritol 2,4-cyclodiphosphate synthase signature.
ELO	[LIV].F[LIV]H[VYWT][FY]HH	PS01188	ELO family signature.
PDXS_SNZ_1	[LV]P[VI][VTPI][NQLHT][FL][ATVS][AS]GG[LIV][AT]TP[AQS]D[AGVS][AS][LM]	PS01235	PdxS/SNZ family signature.
PDXT_SNO_1	[GARVS][LVI][ILAV][LIVF]PGGES[TS][STAV]	PS01236	PdxT/SNO family family signature.
CYTOCHROME_B5_1	[FY][LIVMK][^I][^Q]HP[GA]G	PS00191	Cytochrome b5 family, heme-binding domain signature.
CYTOCHROME_B559	[LIV].[ST][LIVF]R[FYW].{2}[IVL]H[STGAV][LIV][STGA][IV]P	PS00537	Cytochrome b559 subunits heme-binding site signature.
NI_HGENASE_CYTB_1	R[LIVMFYW].HW[LIVM].{2}[LIVMF][STAC][LIVM].{2}L.[LIVM]TG	PS00882	Nickel-dependent hydrogenases b-type cytochrome subunit signature 1.
NI_HGENASE_CYTB_2	[RH][STA][LIVMFYW]H[RH][LIVM].{2}W.[LIVMF].{2}F.{3}H	PS00883	Nickel-dependent hydrogenases b-type cytochrome subunit signature 2.
SDH_CYT_1	RP[LIVMT].{3}[LIVM].{6}[LIVMWPK].{4}S.{2}HR.[ST]	PS01000	Succinate dehydrogenase cytochrome b subunit signature 1.
SDH_CYT_2	H.{3}[GA][LIVMT]R[HF][LIVMF].[FYWM]D.[GVA]	PS01001	Succinate dehydrogenase cytochrome b subunit signature 2.
THIOREDOXIN	[LIVMF][LIVMSTA].[LIVMFYC][FYWSTHE].{2}[FYWGTN]C[GATPLVE][PHYWSTA]C[^I].[^A].{3}[LIVMFYWT]	PS00194	Thioredoxin family active site.
GLUTAREDOXIN	[LIVMD][FYSA].{4}C[PV][FYWH]C.{2}[TAV].{2,3}[LIV]	PS00195	Glutaredoxin active site.
COPPER_BLUE	[GA].{0,2}[YSA].{0,1}[VFY][^SEDT]C.{1,2}[PG].{0,1}H.{2,4}[MQ]	PS00196	Type-1 copper (blue) proteins signature.
2FE2S_FER_1	C[^C][^C][GA][^C]C[GAST][^CPDEKRHFYW]C	PS00197	2Fe-2S ferredoxins, iron-sulfur binding region signature.
ADX	C.{2}[STAQ].[STAMV]C[STA]TC[HR]	PS00814	Adrenodoxin family, iron-sulfur binding region signature.
4FE4S_FERREDOXIN	C.[^P]C[^C].C[^CP].[^C]C[PEG]	PS00198	4Fe-4S ferredoxins, iron-sulfur binding region signature.
HIPIP	C.{6,9}[LIVM].{3}G[YW]C.{2}[FYW]	PS00596	High potential iron-sulfur proteins signature.
RIESKE_1	C[TK]H[LV]GC[LIVSTP]	PS00199	Rieske iron-sulfur protein signature 1.
RIESKE_2	CPCH[^H][GSA]	PS00200	Rieske iron-sulfur protein signature 2.
FLAVODOXIN	[LIV][LIVFY][FY].[ST][^V].[AGC].T[^P].{2}A[^L].[LIV]	PS00201	Flavodoxin signature.
RUBREDOXIN	[LIVM].[^G][^R]W.CP.C[AGD]	PS00202	Rubredoxin signature.
ETF_ALPHA	[LI]Y[LIVM][AT].[GA][IV][SD]G.[IV]Q[HP].{2}G.{6}[IV].A[IV]N	PS00696	Electron transfer flavoprotein alpha-subunit signature.
ETF_BETA	[IVAG].[KR].{2}[DE][GDE]{2}.{1,2}[EQHF].[LIV].{4}P.[LIVM]{2}[TACS]	PS01065	Electron transfer flavoprotein beta-subunit signature.
METALLOTHIONEIN_VRT	C.C[GSTAP].{2}C.C.{2}C.C.{2}C.K	PS00203	Vertebrate metallothioneins signature.
FERRITIN_1	E.[KR]E.{2}E[KR][LF][LIVMA].{2}QN.R.GR	PS00540	Ferritin iron-binding regions signature 1.
FERRITIN_2	D.{2}[LIVMF][STACQV][DH][FYMI][LIV][EN].{2}[FYC]L.{6}[LIVMQ][KNER]	PS00204	Ferritin iron-binding regions signature 2.
BACTERIOFERRITIN	^M.G.{3}[IV][LIV].{2}[LM].{3}L.{3}L	PS00549	Bacterioferritin signature.
FRATAXIN_1	[IV][LIVF][NS][KRTAVSL][QH].[PAVS].{2}[EQ][LIVM]W.[STA][STAD]	PS01344	Frataxin family signature.
TRANSFERRIN_1	Y.{0,1}[VAS]V[IVAC][IVA][IVA][RKH][RKS][GDENSA]	PS00205	Transferrins signature 1.
TRANSFERRIN_2	[YI].GA[FLI][KRHNQS]CL.{3,4}G[DENQ]V[GAT][FYW]	PS00206	Transferrins signature 2.
TRANSFERRIN_3	[DENQK][YF].[LY]LC.[DN].{5,8}[LIV].{4,5}C.{2}A.{4}[HQR].[LIVMFYW][LIVM]	PS00207	Transferrins signature 3.
GLOBIN_FAM_2	F[LF].{4}[GE]G[PAT].{2}[YW].[GSE][KRQAE].{1,5}[LIVM].{3}H	PS01213	Protozoan/cyanobacterial globins signature.
PLANT_GLOBIN	[SN]P.[LV].{2}HA.{3}F	PS00208	Plant hemoglobins signature.
HEMERYTHRINS	HF.{2}[EQ][ENQ].{2}[LMF].{4,7}[FY].{5,6}H.{3}[HR]	PS00550	Hemerythrin family signature.
HEMOCYANIN_1	Y[FYW].ED[LIVM].{2}N.{6}H.{3}P	PS00209	Arthropod hemocyanins / insect LSPs signature 1.
HEMOCYANIN_2	T.{2}RDP.[FY][FYW]	PS00210	Arthropod hemocyanins / insect LSPs signature 2.
HMA_1	[LIVNS].[^L][LIVMFA].C.[STAGCDNH]C.{3}[LIVFG][^LV].{2}[LIV].{9,11}[IVA].[LVFYS]	PS01047	Heavy-metal-associated domain.
ABC_TRANSPORTER_1	[LIVMFYC][SA][SAPGLVFYKQH]G[DENQMW][KRQASPCLIMFW][KRNQSTAVM][KRACLVM][LIVMFYPAN][^PHY][LIVMFW][SAGCLIVP][^FYWHP][^KRHP][LIVMFYWSTA]	PS00211	ABC transporters family signature.
SBP_BACTERIAL_1	[GAP][LIVMFA][STAVDN].[^H].{2}[GSAV][LIVMFY]{2}Y[ND].{3}[LIVMF].[KNDE]	PS01037	Bacterial extracellular solute-binding proteins, family 1 signature.
SBP_BACTERIAL_3	G[FYIL][DE][LIVMT][DE][LIVMF][^PS][^YG].[LIVMA][VAGC][^TPRG][^GL][LIVMAGN]	PS01039	Bacterial extracellular solute-binding proteins, family 3 signature.
SBP_BACTERIAL_5	[AG].{6,7}[DNEG].{2}[STAIVE][LIVMFYWA].[LIVMFY].[LIVM][KR][KRHDE][GDN][LIVMA][KNGSP][FW]	PS01040	Bacterial extracellular solute-binding proteins, family 5 signature.
ALBUMIN	[FY].{6}CC.{2}[^C].{4}C[LFY].{6}[LIVMFYW]	PS00212	Serum albumin family signature.
TRANSTHYRETIN_1	[KH][IV]L[DN].{3}G.P[AG].{2}[LIVM].[IV]	PS00768	Transthyretin signature 1.
TRANSTHYRETIN_2	[YWF][TH][IVT][AP].{2}[LIVM][STA][PQ][FYWG][GS][FY][QST]	PS00769	Transthyretin signature 2.
AVIDIN	[DENY].{2}[KRI][STA].{2}VG.[DN].[FW]T[KR]	PS00577	Avidin / Streptavidin family signature.
COBALAMIN_BINDING	[SN][VT]DT[GAME]A[LIVM][AV].[LM]A[LIVMF][ST]C	PS00468	Eukaryotic cobalamin-binding proteins signature.
LIPOCALIN	[DENG][^A][DENQGSTARK].{0,2}[DENQARK][LIVFY][^CP]G[^C]W[FYWLRH][^D][LIVMTA]	PS00213	Lipocalin signature.
FABP	[GSAIVK][^FE][FYW].[LIVMF].{2}[^K].[NHG][FY][DE].[LIVMFY][LIVM][^N][^G][LIVMAKR]	PS00214	Cytosolic fatty-acid binding proteins signature.
ACB_1	[PTLV][GSTA].[DENQA].[LMFK].{2}[LIVMFY][YV][GSA].[FYH]KQ[GSAV][STL].G	PS00880	Acyl-CoA-binding (ACB) domain signature.
LBP_BPI_CETP	[PA][GA][LIVMC].{2}R[IV][ST].{3}L.{5}[EQAV].{4}[LIVM][EQK].{8}P	PS00400	LBP / BPI / CETP family signature.
PBP	[FYL].[LVM][LIVF].[TIVM][DC]PD.P[SNG].{10}H	PS01220	Phosphatidylethanolamine-binding protein family signature.
PLANT_LTP	[LIVM][PA].{2}C.{1,2}[LIVM].{1,2}[LIVMST].[LIVMFY].{1,2}[LIVMF][STRD].{3}[DN]C.{2}[LIVM]	PS00597	Plant lipid transfer proteins signature.
UTEROGLOBIN_1	[GA].{3}ICP.[LIVMF].{3}[LIVM][DE].[LIVMF]{2}	PS00403	Uteroglobin family signature 1.
UTEROGLOBIN_2	[DEQ].{4}[SN].{5}[DEQ].I.{2}S[PSE][LS]C	PS00404	Uteroglobin family signature 2.
SUGAR_TRANSPORT_1	[LIVMSTAG][LIVMFSAG][^SH][^RDE][LIVMSA][DE][^TD][LIVMFYWA]GR[RK].{4,6}[GSTA]	PS00216	Sugar transport proteins signature 1.
SUGAR_TRANSPORT_2	[LIVMF].G[LIVMFA][^V].G[^KP].{7}[LIFY].{2}[EQ].{6}[RK]	PS00217	Sugar transport proteins signature 2.
LACY_1	G[LIVM]{2}.D[RK]LGL[RK]{2}.[LIVM]{2}W	PS00896	LacY family proton/sugar symporters signature 1.
LACY_2	P.[LIVMF]{2}NR[LIVM]G.KN[STA][LIVM]{3}	PS00897	LacY family proton/sugar symporters signature 2.
PTR2_1	[GA][GAS][LIVMFYWA][LIVM][GAS]D.[LIVMFYWT][LIVMFYW]G.{3}[TAV][IV].{3}[GSTAV].[LIVMF].{3}[GA]	PS01022	PTR2 family proton/oligopeptide symporters signature 1.
PTR2_2	[FYT].{2}[LMFY][FYV][LIVMFYWA].[IVG]N[LIVMAG]G[GSA][LIMF]	PS01023	PTR2 family proton/oligopeptide symporters signature 2.
ASC	Y.{2}[EQTFPMSI].C.{3}C.[QTAVKS].{2}[LIVMT][LIVMSAQ].{2}C.C	PS01206	Amiloride-sensitive sodium channels signature.
NA_ALANINE_SYMP	GG.[GA]{2}[LIVM]FWMW[LIVM].[STAV][LIVMFA]{2}G	PS00873	Sodium:alanine symporter family signature.
NA_DICARBOXYL_SYMP_1	P.{0,1}[GP][DES].[LIVMF]{2}.[LIVMA][LIVM][KREQS][LIVMG][LIVM][LIVMF].[PS]	PS00713	Sodium:dicarboxylate symporter family signature 1.
NA_DICARBOXYL_SYMP_2	P.G.[STA].[NT][LIVMCP]D[GA][STANQF].[LIVM][FY].{2}[LIVM].{2}[LIVM][FY][LIV][SA][QH]	PS00714	Sodium:dicarboxylate symporter family signature 2.
NA_GALACTOSIDE_SYMP	[DG].{3}G.{3}[DN].{6,8}[GA][KRHQ][FSAR][KRL][PT][FYW][LIVMWQ][LIV].{0,1}[GAFV][GSTA]	PS00872	Sodium:galactoside symporter family signature.
NA_NEUROTRAN_SYMP_1	W[RK]F[GPA][YF].{4}[NYHS]GG[GCA].[FY]	PS00610	Sodium:neurotransmitter symporter family signature 1.
NA_NEUROTRAN_SYMP_2	[YF][LIVMFY].{2}[SC][LIVMFY][STQV].{2}[LVI]PW.{2}C.{3,4}[NWDS][GSTERHAK]	PS00754	Sodium:neurotransmitter symporter family signature 2.
NA_SOLUT_SYMP_1	[GS].{2}[LIY].{3}[LIVMFYWSTAG]{7}.{3}[LIY][STAV].{2}GG[LMF].[SAP]	PS00456	Sodium:solute symporter family signature 1.
NA_SOLUT_SYMP_2	[GAST][LIVM].{3}[KR].{4}GA.{2}[GAS][LIVMGS][LIVMW][LIVMGAT]G.[LIVMGA]	PS00457	Sodium:solute symporter family signature 2.
NA_SULFATE	[STACPI]S.{2}[FY].{2}P[LIVM][GSA].{3}N.[LIVM]V	PS01271	Sodium:sulfate symporter family signature.
GLPT	[QEK][RF]G.{3}[GSA][LIVF][WL][NS].[SA][HM]N[LIV][GA]G	PS00942	glpT family of transporters signature.
AMMONIUM_TRANSP	D[FYWS][AS]G[GSC].{2}[IV].{3}[SAG]{2}.{2}[SAG][LIVMF].{3}[LIVMFYWA]{2}.[GK].R	PS01219	Ammonium transporters signature.
BCCT	[GSDNA]WT[LIVM].[FY]W.WW	PS01303	BCCT family of transporters signature.
MOTA	A[LMF].[GAT]T[LIVMF].G.[LIVMF].{7}P	PS01307	Flagellar motor protein motA family signature.
MSCL	[KR]GN[LIVM]{2}D[LIVM]A[LIVM][GA][LIVM]{3}G	PS01327	Large-conductance mechanosensitive channels mscL family signature.
FORMATE_NITRITE_TP_1	[LIVMA][LIVMY].G[GSTA][DES]L[FI][TN][GS]	PS01005	Formate and nitrite transporters signature 1.
FORMATE_NITRITE_TP_2	[GA].{2}[CA]N[LIVMFYW]{2}VC[LV]A	PS01006	Formate and nitrite transporters signature 2.
PROK_SULFATE_BIND_1	K.[NQEK][GT]G[DQ].[LIVM].{3}QS	PS00401	Prokaryotic sulfate-binding proteins signature 1.
PROK_SULFATE_BIND_2	NPK[ST]SG.AR	PS00757	Prokaryotic sulfate-binding proteins signature 2.
SLC26A	[PAV].[FYLCVI][GS]LY[STAG][STAGL].{4}[LIVFYA][LIVMST][YI].{3}[GA][GST][SRV][KRNP]	PS01130	SLC26A transporters signature.
AMINO_ACID_PERMEASE_1	[STAGC]G[PAG].{2,3}[LIVMFYWA]{2}.[LIVMFYW].[LIVMFWSTAGC]{2}[STAGC].{3}[LIVMFYWT].[LIVMST].{3}[LIVMCTA][GA]E.{5}[PSAL]	PS00218	Amino acid permeases signature.
AROMATIC_AA_PERMEASE_1	IG[GA]GM[LF][SA].P.{3}[SA]G.{2}F	PS00594	Aromatic amino acids permeases signature.
XANTH_URACIL_PERMASE	[LIVM]P.[PASIF]V[LIVMG][GF][GA].{4}[LIVM][FY][GSA].[LIVM].{3}[GA]	PS01116	Xanthine/uracil permeases family signature.
ANION_EXCHANGER_1	FGG[LIVM]{2}[KR]D[LIVM][RK]RRY	PS00219	Anion exchangers family signature 1.
ANION_EXCHANGER_2	[FI]LISLIFIYETF.KL	PS00220	Anion exchangers family signature 2.
MIP	[HNQA][^D]NP[STA][LIVMF][ST][LIVMF][GSTAFY]	PS00221	MIP family signature.
GRAM_NEG_PORIN	[LIVMFY].{2}G.{2}Y.F.K.{2}[SN][STAV][LIVMFYW]V	PS00576	General diffusion Gram-negative porins signature.
OMPA_1	[LIVMA].[GT].[TA][DAN].{2,3}[DG][GSTPNKQ].{2}[LFYDEPAVI][NQS].{2}[LI][SG][QEA][KRQENAD]RA.{2}[LVAIT].{3}[LIVMF].{4,5}[LIVMF].{4}[LIVM].{3}[SGW].G	PS01068	OmpA-like domain.
EUKARYOTIC_PORIN	[YH].{2}D[SPCAD].[STA].{3}[TAG][KR][LIVMF][DNSTA][DNS].{4}[GSTAN][LIVMA].[LIVMY]	PS00558	Eukaryotic mitochondrial porin signature.
IGF_BINDING	[GP]C[GSET][CE][CA].{2}C[ALP].{6}C	PS00222	Insulin-like growth factor binding proteins signature.
GPR1_FUN34_YAAH	NP[AV]P[LF]GL.[GSA]F	PS01114	GPR1/FUN34/yaaH family signature.
43_KD_POSTSYNAPTIC	GQDQTKQQI	PS00405	43 Kd postsynaptic protein signature.
ACTINS_1	[FY][LIV][GV][DE]E[ARV][QLAH].{1,2}[RKQ]{2}[GD]	PS00406	Actins signature 1.
ACTINS_2	W[IVC][STAK][RK].[DE]Y[DNE][DE]	PS00432	Actins signature 2.
ACTINS_ACT_LIKE	[LM][LIVMA]TE[GAPQ].[LIVMFYWHQPK][NS][PSTAQ].{2}N[KR]	PS01132	Actins and actin-related proteins signature.
ANNEXIN	[TG][STV].{8}[LIVMF].{2}R.{3}[DEQNH].{2}[^S].{4}[IFY].{7}[LIVMF].{3}[LIVMF].{5}[^I].{5}[LIVMFA].{2}[LIVMF]	PS00223	Annexins repeated domain signature.
CAVEOLIN	FED[LV]IA[DE][PA]	PS01210	Caveolins signature.
CLATHRIN_LIGHT_CHN_1	FLA[QH][QE]ES	PS00224	Clathrin light chain signature 1.
CLATHRIN_LIGHT_CHN_2	[KR][DS].[SE][KR][LIVMF][KR].[LIVM][LIVMY][LIVM].L[KA]	PS00581	Clathrin light chain signature 2.
CLUSTERIN_1	CKPCLK.TC	PS00492	Clusterin signature 1.
CLUSTERIN_2	CL[RK]M[RK].[EQ]C[ED]KC	PS00493	Clusterin signature 2.
CONNEXINS_1	C[DNH][TL].[QT]PGC.{2}[VAIL]C[FY]D	PS00407	Connexins signature 1.
CONNEXINS_2	C.{3,4}PC.{2,3}[LIVMTA][DENT]C[FYN][LIVMQ][SA][KRH]P	PS00408	Connexins signature 2.
DYNAMIN	LP[RKT][GD][STNKEA][GND][LIVMG][VICA]TR	PS00410	Dynamin family signature.
DYNEIN_LIGHT_1	H.[IV].G[KR].F[GA]S.V[ST][HY]E	PS01239	Dynein light chain type 1 signature.
FTSZ_1	N[ST]D.[QS].L.{16,18}G.G[ATVS]G[GSAN].P.{2}G	PS01134	FtsZ protein signature 1.
FTSZ_2	[DNHKR][LIVMF].[LIVMF]{2}[VSTAC][STAC]G.G[GKN]GTG[ST]G[GSARC][STA]P[LIVMFT][LIVMF][SGAV]	PS01135	FtsZ protein signature 2.
HYDROPHOBIN	[GN][DNQPSA].C[GSTANK][GSTADNQ][STNQI][PTIV].CC[DENQKPST]	PS00956	Fungal hydrophobins signature.
IF	[IV][^K][TACI]Y[RKH][^E][LM]L[DE]	PS00226	Intermediate filaments signature.
INVOLUCRIN	^MS[QH]Q.T[LV]PVT[LV]	PS00795	Involucrin signature.
KINESIN_MOTOR_DOMAIN1	[GSAT][KRHPSTQVME][LIVMFY].[LIVMF][IVC][DN][LS][AH]G[SAN]E	PS00411	Kinesin motor domain signature.
KINESIN_LIGHT	[DEQR]AL.{3}[GEQ].{3}G.[DNS].P.VA.{3}N.L[AS].{5}[QR].[KR][FY].{2}[AV].{4}[HKNQ]	PS01160	Kinesin light chain repeat.
MYELIN_MBP	VVHFFKN	PS00569	Myelin basic protein signature.
MYELIN_P0	S[KR]S.K[AG].[SA]EKK[STA]K	PS00568	Myelin P0 protein signature.
MYELIN_PLP_1	G[MV]ALFCGCGH	PS00575	Myelin proteolipid protein signature 1.
MYELIN_PLP_2	C.[ST].[DE].{3}[ST][FY].L[FY]I.{4}GA	PS01004	Myelin proteolipid protein signature 2.
NEUROMODULIN_1	^MLCC[LIVM]RR	PS00412	Neuromodulin (GAP-43) signature 1.
NEUROMODULIN_2	SFRGHI.RKK[LIVM]	PS00413	Neuromodulin (GAP-43) signature 2.
OSTEOPONTIN	[KQ].[TA].{2}[GA]SSEEK	PS00884	Osteopontin signature.
RDS_ROM1	D[GS]VPF[ST]CCNP.SPRPC	PS00930	Peripherin / rom-1 signature.
PROFILIN	^.{0,1}[STA].{0,1}W[DENQH].[YI].[DEQ]	PS00414	Profilin signature.
SURFACT_PALMYTOYL	IPCCPV	PS00341	Surfactant associated polypeptide SP-C palmitoylation sites.
SYNAPSIN_1	LRRRLSDS	PS00415	Synapsins signature 1.
SYNAPSIN_2	GHAH[SA]GMGK[IV]K	PS00416	Synapsins signature 2.
SYNAPTOBREVIN	[NT][LIVMF][DENSTG][KLNRGS][VAI].[DEQA]R.{2}[KRN][LIVM][STDEA].[LIVM].[DEQGN][KR][TAS][DEAS]	PS00417	Synaptobrevin signature.
SYNAPTOP	LSV[DE]C.NKT	PS00604	Synaptophysin / synaptoporin signature.
TROPOMYOSIN	LK[EAD]AE.RA[ET]	PS00326	Tropomyosins signature.
TUBULIN	[SAG]GGTG[SA]G	PS00227	Tubulin subunits alpha, beta, and gamma signature.
TUBULIN_B_AUTOREG	^MR[DE][IL]	PS00228	Tubulin-beta mRNA autoregulation signal.
TAU_MAP	GS.{2}N.{2}H.[PA][AG]G{2}	PS00229	Tau and MAP proteins tubulin-binding domain signature.
MAP1B_NEURAXIN	[STAGDN]Y.YE[^AV][^L][DE][KR][STAGCI]	PS00230	Neuraxin and MAP1B proteins repeated region signature.
F_ACTIN_CAPPING_A_1	[VA]H[FY]{2}[ER][DEC][GV]N[VL]	PS00748	F-actin capping protein alpha subunit signature 1.
F_ACTIN_CAPPING_A_2	K.[LM]RR.LP[IV][NT]R	PS00749	F-actin capping protein alpha subunit signature 2.
F_ACTIN_CAPPING_BETA	C[DE][YF]NRD	PS00231	F-actin capping protein beta subunit signature.
VINCULIN_1	[KR].[LIVMF].{3}[LIVMA].{2}[LIVM].{6}RQQEL	PS00663	Vinculin family talin-binding region signature.
VINCULIN_2	[LIVM].[QA]A.{2}W[IL].[DN]P	PS00664	Vinculin repeated domain signature.
A4_EXTRA	G[VT][EK][FY]VCCP	PS00319	Amyloidogenic glycoprotein extracellular domain signature.
A4_INTRA	GYENPTY[KR]	PS00320	Amyloidogenic glycoprotein intracellular domain signature.
CADHERIN_1	[LIV].[LIV].D.ND[NH].P	PS00232	Cadherin domain signature.
GAS_VESICLE_A_1	[LIVM].[DE][LIVMFYT][LIVM][DE].[LIVM]{2}[DKR]{2}G.[LIVMA][LIVM]	PS00234	Gas vesicles protein GVPa signature 1.
GAS_VESICLE_A_2	R[LIVA]{3}A[GS][LIVMFY].[TK].{3}[YFI][AG]	PS00669	Gas vesicles protein GVPa signature 2.
GAS_VESICLE_C	FL.{2}T.{3}R.{3}A.{2}Q.{3}L.{2}F	PS00235	Gas vesicles protein GVPc repeated domain signature.
BACT_MICROCOMP	D.{0,1}M.K[SAG]{2}.[IV].[LIVM][LIVMA][GCSY].{4}[GDE][SGPDR][GA]	PS01139	Bacterial microcompartiments proteins signature.
FLAGELLA_BB_ROD	[GTARYQ].[^R].{7}[LIVMYSTA]{2}[GSTA][STADEN]N[LIVM][SAN]N.[SADENFR][STV]	PS00588	Flagella basal body rod proteins signature.
FLIP_1	[PA][AS][FY].[LIVT][STH][EQ][LI].{2}[GA]F[KREQ][IM][GV][LIF]	PS01060	Flagella transport protein fliP family signature 1.
FLIP_2	P[LIVMF]K[LIVMF]{5}.[LIVMA][DNGS]GW	PS01061	Flagella transport protein fliP family signature 2.
ICOSAH_VIR_COAT_S	[FYW].[PSTA].{7}G.[LIVM].[LIVM].[FYWI].{2}D.{5}P	PS00555	Plant viruses icosahedral capsid proteins 'S' region signature.
POTEX_CARLAVIRUS_COAT	[RK][FYW]A[GAP]FD.F.{2}[LV].{3}[GASTY][GASTV]	PS00418	Potexviruses and carlaviruses coat protein signature.
NEUROTR_ION_CHANNEL	C.[LIVMFQ].[LIVMF].{2}[FY]P.D.{3}C	PS00236	Neurotransmitter-gated ion-channels signature.
P2X_RECEPTOR	GG.[LIVM]G[LIVM].[IV].W.C[DN]LD.{5}C.P.Y.F	PS01212	ATP P2X receptors signature.
G_PROTEIN_RECEP_F1_1	[GSTALIVMFYWC][GSTANCPDE][^EDPKRH].[^PQ][LIVMNQGA][^RK][^RK][LIVMFT][GSTANC][LIVMFYWSTAC][DENH]R[FYWCSH][^PE].[LIVM]	PS00237	G-protein coupled receptors family 1 signature.
G_PROTEIN_RECEP_F2_1	C.{3}[FYWLIV]D.{3,4}C[FW].{2}[STAGV].{8,9}C[PF]	PS00649	G-protein coupled receptors family 2 signature 1.
G_PROTEIN_RECEP_F2_2	[QL]G[LMFCAV][LIVMFTA][LIV].[LIVFSTM][LIFHV][VFYHLG]C[LFYAVI].[NKRQDS].{2}[VAI]	PS00650	G-protein coupled receptors family 2 signature 2.
G_PROTEIN_RECEP_F3_1	[LV].N[LIVM]{2}.LF.I[PA]Q[LIVM][STA].[STA]{3}[STAN]	PS00979	G-protein coupled receptors family 3 signature 1.
G_PROTEIN_RECEP_F3_2	CC[FYW].C.{2}C.{4}[FYW].{2,5}[DNE].{2}[STAHENRI]C.{2}C	PS00980	G-protein coupled receptors family 3 signature 2.
G_PROTEIN_RECEP_F3_3	[FLY]N[ED][STA]K.[IV][STAG][FM][ST][MVL]	PS00981	G-protein coupled receptors family 3 signature 3.
OPSIN	[LIVMFWAC][PSGAC].[^G].[SAC]K[STALIMR][GSACPNV][STACP].{2}[DENF][AP].{2}[IY]	PS00238	Visual pigments (opsins) retinal binding site.
BACTERIAL_OPSIN_1	RY.[DT]W.[LIVMF][ST][TV]P[LIVM][LIVMNQ][LIVM]	PS00950	Bacterial rhodopsins signature 1.
BACTERIAL_OPSIN_RET	[FYIV][^ND][FYVG][LIVM]D[LIVMF].[STA]K.[^K][FY]	PS00327	Bacterial rhodopsins retinal binding site.
RECEPTOR_TYR_KIN_II	[DN][LIV]Y.{3}YYR	PS00239	Receptor tyrosine kinase class II signature.
RECEPTOR_TYR_KIN_III	G.H.N[LIVM]VNLLGACT	PS00240	Receptor tyrosine kinase class III signature.
RECEPTOR_TYR_KIN_V_1	F.[DN].[GAW][GAS]C[LIVM][SA][LIVM]{2}[SA][LV][KRHQ][LIVA].{3}[KREQT]C[PSAWR]	PS00790	Receptor tyrosine kinase class V signature 1.
RECEPTOR_TYR_KIN_V_2	C.{2}[DE]G[DEQKRG]W.{2,3}[PAQ][LIVMT][GT].C.C.{2}G[HFY][EQ]	PS00791	Receptor tyrosine kinase class V signature 2.
HEMATOPO_REC_L_F1	[LIV].[LIV].W.{12,16}Y[EQ][LIV].{25,27}L.{10}[RKH].[RKH].{5,9}[FYW].{2}[FYW].{5}[LIV]	PS01352	Long hematopoietin receptor, single chain family signature.
HEMATOPO_REC_L_F2	N.{4}S.{28,35}[LVIM].W.{0,3}P.{5,9}[YF].{1,2}[VILM].W	PS01353	Long hematopoietin receptor, gp130 family signature.
HEMATOPO_REC_L_F3	[LIV].PDP{2}.{2}[LIV].{8,11}[LVAM].{3}W.{2}P.[ST]W.{4,6}[FY].L.[FY].[LVI]	PS01354	Long hematopoietin receptor, soluble alpha chains family signature.
HEMATOPO_REC_S_F1	[LIVF].{9}[LIV][RK].{9,20}WS.WS.{4}[FYW]	PS01355	Short hematopoietin receptor family 1 signature.
HEMATOPO_REC_S_F2	[LIVM].C.W.{2}G.{5}D.{2}Y.[LIVM].{10,14}C	PS01356	Short hematopoietin receptor family 2 signature.
TNFR_NGFR_1	C.{4,6}[FYH].{5,10}C.{0,2}C.{2,3}C.{7,11}C.{4,6}[DNEQSKP][^PD][^CP]C	PS00652	TNFR/NGFR family cysteine-rich region signature.
INTEGRIN_ALPHA	[FYWS][RK].GFF.R	PS00242	Integrins alpha chain signature.
INTEGRIN_BETA	C.[GNQ].{1,3}G.C.C.{2}C.C	PS00243	Integrins beta chain cysteine-rich domain signature.
ANF_RECEPTORS	GP.C.Y.AA.V.R.{3}HW	PS00458	Natriuretic peptides receptors signature.
REACTION_CENTER	[NQH].{4}P.H.{2}[SAG].{11}[SAGC].H[SAG]{2}	PS00244	Photosynthetic reaction center proteins signature.
ANTENNA_COMP_ALPHA	[LIVFAG].[GASV][LIVFA].[IV]H.{3}[LIVM][GSTAE][STANH].{1,3}[STN]W[LIVMFYW]	PS00968	Antenna complexes alpha subunits signature.
ANTENNA_COMP_BETA	[EQ].{4}[HGQ].{5}[GSTA].{3}[FYV].{3}[AG].{2}[AV]H.{7}P	PS00969	Antenna complexes beta subunits signature.
PHOTOSYSTEM_I_PSAAB	CDGP[GE]RGGTC	PS00419	Photosystem I psaA and psaB proteins signature.
PHOTOSYSTEM_I_PSAGK	[GTND][FPMI].[LIVMH].[DEAT].{2}[GA].[GTAM][STA].GH.[LIVM][GAS]	PS01026	Photosystem I psaG and psaK proteins signature.
PHYTOCHROME_1	[RGS][GSA][PV]H.CH.{2}Y	PS00245	Phytochrome chromophore attachment site signature.
TONB_DEPENDENT_REC_1	^.{10,115}[DENF][ST][LIVMF][LIVSTEQ]V[^AGPN][AGP][STANEQPK]	PS00430	TonB-dependent receptor proteins signature 1.
TONB_DEPENDENT_REC_2	[LYGSTANEQ].{3}[GSTAENQ].[PGE]R.[LIVFYWA].[LIVMFTA][STAGNQ][LIVMFYGTA].[LIVMFYWGTADQ].F$	PS01156	TonB-dependent receptor proteins signature 2.
TM4_1	[GC].{3}[LIVMFS].{2}[GSA][LIVMFTC][LIVMFA]G[CLYI].[GA][STAPL].{2}[EGAR].{2}[CWNLF][LIVMGA][LIVM]	PS00421	Transmembrane 4 family signature.
CHEMOTAXIS_TRANSDUC_1	RTE[EQ]Q.{2}[SA][LIVM].[EQ]TAASMEQLTATV	PS00538	Bacterial chemotaxis sensory transducers signature.
ER_LUMEN_RECEPTOR_1	G[LIV]S.[KR].[QH].L[FY].[LIV]{2}[FYW].{2}RY	PS00951	ER lumen protein retaining receptor signature 1.
ER_LUMEN_RECEPTOR_2	LE[SA]VAI[LM]PQ[LI]	PS00952	ER lumen protein retaining receptor signature 2.
EPHRIN	[KRQ][LF][CST].K[IF]Q.[FY][ST][PA].{3}G.EF.{5}[FY]{2}.{2}[SA]	PS01299	Ephrins signature.
GRANULINS	C.D.{2}HCCP.{4}C	PS00799	Granulins signature.
HBGF_FGF	G.[LIM].[STAGP].{6,7}[DENA]C.[FLM].[EQ].{6}Y	PS00247	HBGF/FGF family signature.
PTN_MK_1	S[DE]C.[DE]W.W.{2}C.P.[SN].DCG[LIVMA]G.REG	PS00619	PTN/MK heparin-binding protein family signature 1.
PTN_MK_2	C[KR][LIVM]PCNWKK.FGA[DE]CKY.F[EQ].WG.C	PS00620	PTN/MK heparin-binding protein family signature 2.
NGF_1	[GSRE]C[KRL]G[LIVT][DE].{3}[YW].S.C	PS00248	Nerve growth factor family signature.
PDGF_1	P[PSR]CV.{3}RC[GSTA]GCC	PS00249	Platelet-derived growth factor (PDGF) family signature.
SMALL_CYTOKINES_CXC	C.C[LIVMS].{4,6}[LIVMFY].{2}[RKSEQNA].[LIVM].{2}[LIVMA].{5}[STAG].{2}C.{3}[EQ][LIVM]{2}.{9,10}C[LV][DN]	PS00471	Small cytokines (intercrine/chemokine) C-x-C subfamily signature.
SMALL_CYTOKINES_CC	CC[LIFYTRQ].{5,8}[LIR].{4}[LIVMFA].{2}[FYWECI].{5,8}C.{3,4}[SAG][LIVM]{2}[FL].{7,9}C[STAV]	PS00472	Small cytokines (intercrine/chemokine) C-C subfamily signature.
TGF_BETA_1	[LIVM].{2}P.{2}[FY].{4}C.G.C	PS00250	TGF-beta family signature.
TNF_1	[LV].[LIVM][^V].[^L]G[LIVMF]Y[LIVMFY]{2}.{2}[QEKHL][LIVMGT].[LIVMFY]	PS00251	TNF family signature.
WNT1	C[KR]CHG[LIVMT]SG.C	PS00246	Wnt-1 family signature.
INTERFERON_A_B_D	[FYH][FY].[GNRCDS][LIVM].{2}[FYL]L.{7}[CY][AT]W	PS00252	Interferon alpha, beta and delta family signature.
GM_CSF	CP[LP]T.E[ST].C	PS00702	Granulocyte-macrophage colony-stimulating factor signature.
INTERLEUKIN_1	[FC].S[ASLV].{2}P.{2}[FYLIV][LI][SCA]T.{7}[LIVM]	PS00253	Interleukin-1 signature.
INTERLEUKIN_2	TE[LF].{2}L.CL.{2}EL	PS00424	Interleukin-2 signature.
INTERLEUKIN_4_13	[LI].E[LIVM]{2}.{4,5}[LIVM][TL].{5,7}C.{4}[IVA].[DNS][LIVMA]	PS00838	Interleukins -4 and -13 signature.
INTERLEUKIN_6	C.{9}C.{6}GL.{2}[FY].{3}L	PS00254	Interleukin-6 / G-CSF / MGF signature.
INTERLEUKIN_7_9	N.[LAP][SCT]FLK.LL	PS00255	Interleukin-7 and -9 signature.
INTERLEUKIN_10	[KQS].{4}C[QYCS].{4}[LIVM]{2}.[FLR][FYT][LMVR].[DERTI][IV][LMF]	PS00520	Interleukin-10 family signature.
LIF_OSM	[PST].{4}F[NQ].K.{3}C.[LF]L.{2}Y[HK]	PS00590	LIF / OSM family signature.
MIF	[DE]P[CLV][APT].{3}[LIVM].S[IS][GT].[LIVM][GST]	PS01158	Macrophage migration inhibitory factor family signature.
AKH	Q[LV][NT][FY][ST].{2}W	PS00256	Adipokinetic hormone family signature.
BOMBESIN	WA.G[SH][LF]M	PS00257	Bombesin-like peptides family signature.
CALCITONIN	C[SAGDN][STN].{0,1}[SA]TC[VMA].{3}[LYF].{3}[LYF]	PS00258	Calcitonin / CGRP / IAPP family signature.
CRF	[PQA].[LIVM]S[LIVM].{2}[PST][LIVMF].[LIVM]LR.{2}[LIVMW]	PS00511	Corticotropin-releasing factor family signature.
CHH_MIH_GIH	[LIVM].{3}C[KR].[DENGRH]C[FY].[STN].{2}F.{2}C	PS01250	Arthropod CHH/MIH/GIH neurohormones family signature.
EPO_TPO	P.{4}CD.R[LIVM]{2}.[KR].{14}C	PS00817	Erythropoietin / thrombopoeitin signature.
GRANINS_1	[DE][SN]L[SAN][^EGI][^ELT][DE].EL	PS00422	Granins signature 1.
GRANINS_2	C[LIVM]{2}E[LIVM]{2}S[DN][STA]L.K.[SN].{3}[LIVM][STA].EC	PS00423	Granins signature 2.
GALANIN	GWTLNSAGYLLGP	PS00861	Galanin signature.
GASTRIN	Y.{0,1}[GD][WH]M[DR]F	PS00259	Gastrin / cholecystokinin family signature.
GLUCAGON	[YH][STAIVGD][DEQ][AGF][LIVMSTE][FYLR].[DENSTAK][DENSTA][LIVMFYG].{8}[^K][KREQL][KRDENQL][LVFYWG][LIVQ]	PS00260	Glucagon / GIP / secretin / VIP family signature.
GLYCO_HORMONE_ALPHA_1	C.GCC[FY]S[RQS]A[FY]PTP	PS00779	Glycoprotein hormones alpha chain signature 1.
GLYCO_HORMONE_ALPHA_2	NHT.C.C.TC.{2}HK	PS00780	Glycoprotein hormones alpha chain signature 2.
GLYCO_HORMONE_BETA_1	C[STAGM]G[HFYL]C.[ST]	PS00261	Glycoprotein hormones beta chain signature 1.
GLYCO_HORMONE_BETA_2	[PA]VA.{2}C.C.{2}C.{4}[STDAI][DEY]C.{6,8}[PGSTAVMI].{2}C	PS00689	Glycoprotein hormones beta chain signature 2.
GNRH	Q[HY][FYW]S.{4}PG	PS00473	Gonadotropin-releasing hormones signature.
INSULIN	CC[^P][^P].C[STDNEKPI].{3}[LIVMFS].{3}C	PS00262	Insulin family signature.
NATRIURETIC_PEPTIDE	CFG.{3}[DEA][RH]I.{3}S.{2}GC	PS00263	Natriuretic peptides signature.
NEUROHYPOPHYS_HORM	C[LIFY][LIFYV].N[CS]P.G	PS00264	Neurohypophysial hormones signature.
NMU	F[LIVMF]FRPRN	PS00967	Neuromedin U signature.
OPIOIDS_PRECURSOR	C.{3}C.{2}C.{2}[KRH].{6,7}[LIF][DNS].{3}C.[LIVM][EQ]C[EQ].{8}W.{2}C	PS01252	Endogenous opioids neuropeptides precursors signature.
PANCREATIC_HORMONE_1	[FY].{3}[LIVM].{2}Y.{3}[LIVMFY].R.R[YF]	PS00265	Pancreatic hormone family signature.
PARATHYROID	VSE.Q.{2}H.{2}G	PS00335	Parathyroid hormone family signature.
PYROKININ	F[GSTV]PRL[G>]	PS00539	Pyrokinins signature.
SOMATOTROPIN_1	C.[STN].{2}[LIVMFYS].[LIVMSTA]P.{5}[TALIV].{7}[LIVMFY].{6}[LIVMFY].{2}[STACV]W	PS00266	Somatotropin, prolactin and related hormones signature 1.
SOMATOTROPIN_2	C[LIVMFY][^PT].D[LIVMFYSTA].[^S][^RK][^A].[LIVMFY].{2}[LIVMFYT].{2}C	PS00338	Somatotropin, prolactin and related hormones signature 2.
TACHYKININ	F[IVFY]G[LM]M[G>]	PS00267	Tachykinin family signature.
THYMOSIN_B4	L[KR]KT[DENT]T.{2}KN[PT]L	PS00500	Thymosin beta-4 family signature.
UROTENSIN_II	CFWKYC	PS00984	Urotensin II signature.
CECROPIN	W.{0,2}[KDN][^Q][^L]K[KRE][LI]E[RKN]	PS00268	Cecropin family signature.
DEFENSIN	C.C.{3,5}C.{7}G.C.{9}CC	PS00269	Mammalian defensins signature.
ARTHROPOD_DEFENSINS	C.{2,3}[HNS]C.{3,4}[GR][^A].[GRQ][GA].C.{4,7}C.C	PS00425	Arthropod defensins signature.
CATHELICIDINS_1	Y.[ED].V.[RQ]A[LIVMA][DQG].[LIVMFY]N[EQ]	PS00946	Cathelicidins signature 1.
CATHELICIDINS_2	F.[LIVM]KET.C.{10}C.F[KR][KE]	PS00947	Cathelicidins signature 2.
ENDOTHELIN	C.C.{4}D.{2}C.{2}[FY]C	PS00270	Endothelin family signature.
PLANT_C6_AMP	C[IV]X{2,4}[RG]CX{2,6}[GP]XCCSX{2,4}C[^C]{6,10}C	PS60011	Plant C6 type antimicrobial peptide (AMP) signature.
THIONIN	CC.{5}R.{2}[FY].{2}C	PS00271	Plant thionins signature.
GAMMA_THIONIN	[KRGAQS].C.{3}[SVA].{2}[FYWH].{1,2}[GF].C.{5}C.{3}C	PS00940	Gamma-thionins family signature.
SNAKE_TOXIN	GC.{1,3}CP.{8,10}CC.{2}[PDEN]	PS00272	Snake toxins signature.
MYOTOXINS	K.CH.K.{2}HC.{2}K.{3}C.{8}K.{2}C.{2}[RK].KCCKK	PS00459	Myotoxins signature.
SCORP_SHORT_TOXIN	C.{3}C.{6,9}[GAS]KC[IMQT].{3}C.C	PS01138	Scorpion short toxins signature.
SCORPION_CALCINE	CX{6}CX{5}CCX{3}CX{9}RC	PS60028	Scorpion calcine family signature.
MU_AGATOXIN	C[LAV]X[DEK]X{3}CX{6,7}CCX{4}CXCX{5}CXC	PS60015	Mu-agatoxin family signature.
OMEGA_AGA_II_III	CQCC.{2}N[GA][FY]CS	PS60023	Omega-agatoxin type II and III signature.
DELTA_ACTX	CX{5}WCX{4}DCCCX{3}CX{2}AWYX{5}CX{10,11}C	PS60018	Delta-atracotoxin (ACTX) family signature.
OMEGA_ACTX_1	C[IT]PSGQPC	PS60016	Omega-atracotoxin (ACTX) type 1 family signature.
OMEGA_ACTX_2	CC[GE][ML]TPXC	PS60017	Omega-atracotoxin (ACTX) type 2 family signature.
J_ACTX	C[^C]{6}CX{2}CCXCCX{4}CX{9,10}C	PS60020	Janus-faced atracotoxin (J-ACTX) family signature.
HWTX_1	C[KALRVG]X[^M]X{1,3}CX{4,6}CCX{4,6}CX{4}[ERK]WC	PS60021	Huwentoxin-1 family signature.
HWTX_2	CX{1,4}[FLIV][SEP]C[DE][EIQ]X{4,7}CX{0,7}C[KST]X{4,18}C[YK]X{1,3}C	PS60022	Huwentoxin-2 family signature.
SPIDER_CSTX	C[^C]{6}C[^C]{6}CC[^C]{8}C[^C]C	PS60029	Spider toxin CSTX family signature.
ERGTX	CX{5}CXKX{6}CX{2}CCX{9}CX{4}CXC	PS60026	Ergtoxin family signature.
ASSASSIN_BUG_TOXIN	C[LI]X{2}G[SA]XCXGX{2}KXCCX{4,5}CX{2}YAN[RK]C	PS60010	Assassin bug toxin signature.
ALPHA_CONOTOXIN	CC[SHYN]X{0,1}[PRG][RPATV]C[ARMFTNHG]X{0,4}[QWHDGENLFYVP][RIVYLGSDW]C	PS60014	Alpha-conotoxin family signature.
MU_CONOTOXIN	CC[TGN][PFG][PRG]X{0,2}C[KRS][DS][RK][RQW]C[KR][PD][MLQH]X{0,1}[KR]CC	PS60013	Mu-conotoxin family signature.
OMEGA_CONOTOXIN	C[SREYKLIMQVN].{2}[DGWET].[FYSPKV]C[GNDSRHTP].{1,5}[NPGSMTAHF][GWPNIYRSKLQ].CC[STRHGD].{0,2}[NFLWSRYIT]C.{0,3}[VFGAITSNRKL][FLIKRNGH][VWIARKF]C	PS60004	Omega-conotoxin family signature.
DELTA_CONOTOXIN	C.{2}[EPSAGT].{3}C[GSNDL].{0,3}[PILV].[FPNDSG][GQ].CC.{3,4}C[FLVIA].{1,2}[FVIWA]C	PS60005	Delta-conotoxin family signature.
I_CONOTOXIN	C[^C]{6}C[^C]{5}CC[^C]{1,3}CC[^C]{2,4}C[^C]{3,10}C	PS60019	I-superfamily conotoxin signature.
CONANTOKIN	GEEEX{2}[KE][^E]{2}X{0,1}EX[ILA]RE	PS60025	Conantokin family signature.
CONTRYPHAN	G{0,1}C[PV][WL]XP[YW]CX{0,1}$	PS60027	Contryphan family signature.
ENTEROTOXIN_H_STABLE	CC.{2}CC.PAC.GC	PS00273	Heat-stable enterotoxins signature.
AEROLYSIN	[KT].{2}NW.{2}T[DN]T	PS00274	Aerolysin type toxins signature.
BACTERIOCIN_IIA	YGNG[VL]XCX{4}C	PS60030	Bacteriocin class IIa family signature
SHIGA_RICIN	[LIVMA].[LIVMSTA]{2}.E[SAGV][STAL]R[FY][RKNQST].[LIVM][EQS].{2}[LIVMF]	PS00275	Shiga/ricin ribosomal inactivating toxins active site signature.
CHANNEL_COLICIN	T.{2}W.P[LIVMFY]{3}.{2}E	PS00276	Channel forming colicins signature.
HOK_GEF	[LIVMA]{4}C[LIVMFA]T[LIVMA]{2}.{4}[LIVM].[RG].{2}L[CY]	PS00556	Hok/gef family cell toxic proteins signature.
STAPH_STREP_TOXIN_1	YGG[LIV]T[^I][^N].{2}N	PS00277	Staphylococcal enterotoxin/Streptococcal pyrogenic exotoxin signature 1.
STAPH_STREP_TOXIN_2	K.{2}[LIVF].{4}[LIVF]D.{3}R.{2}L.{5}[LIV]Y	PS00278	Staphyloccocal enterotoxin/Streptococcal pyrogenic exotoxin signature 2.
THIOL_CYTOLYSINS	[RK]ECTGL.WEWW[RK]	PS00481	Thiol-activated cytolysins signature.
MAC_PERFORIN	Y.{6}[FY]GTH[FY]	PS00279	Membrane attack complex components / perforin signature.
BPTI_KUNITZ_1	F.{2}[^I]GC.{6}[FY].{5}C	PS00280	Pancreatic trypsin inhibitor (Kunitz) family signature.
BOWMAN_BIRK	C.{5,6}[DENQKRHSTA]C[PASTDH][PASTDK][ASTDV]C[NDEKS][DEKRHSTA]C	PS00281	Bowman-Birk serine protease inhibitors family signature.
KAZAL	C.{4}[^C].{2}C.[^A].{4}Y.{3}C.{2,3}C	PS00282	Kazal serine protease inhibitors family signature.
SOYBEAN_KUNITZ	[LIVM].D[^EK][EDNTY][DG][RKHDENQ].[LIVM].[^E][^Q].{2}Y.[LIVM]	PS00283	Soybean trypsin inhibitor (Kunitz) protease inhibitors family signature.
SERPIN	[LIVMFY][^G][LIVMFYAC][DNQ][RKHQS][PST]F[LIVMFY][LIVMFYC].[LIVMFAH]	PS00284	Serpins signature.
POTATO_INHIBITOR	[FYW]P[EQH][LIV]{2}G.{2}[STAGV].{2}A	PS00285	Potato inhibitor I family signature.
SQUASH_INHIBITOR	CP.{5}C.{2}[DN].DC.{3}C.C	PS00286	Squash family of serine protease inhibitors signature.
SSI	C.P.{2,3}G.{0,1}H[PA].{4}AC[ATDE].L	PS00999	Streptomyces subtilisin-type inhibitors signature.
CYSTATIN	[GSTEQKRV]Q[LIVT][VAF][SAGQ]G[^DG][LIVMNK][^TK].[LIVMFY][^S][LIVMFYA][DENQKRHSIV]	PS00287	Cysteine proteases inhibitors signature.
TIMP	C.C.P.HPQ.{2}[FIV]C	PS00288	Tissue inhibitors of metalloproteinases signature.
CEREAL_TRYP_AMYL_INH	C.{4}[SAGDV].{4}[SPAL][LF].{2}C[RH].[LIVMFYA]{2}.{3,4}C	PS00426	Cereal trypsin/alpha-amylase inhibitors family signature.
ALPHA_2_MACROGLOBULIN	[PG].[GS]C[GA]E[EQ].[LIVM]	PS00477	Alpha-2-macroglobulin family thiolester region signature.
LAMBDA_PHAGE_CIII	ES.L.R.{2}[KR].L.{4}[KR]{2}.{2}[DE].L	PS00553	Lambdoid phages regulatory protein CIII signature.
CHAPERONINS_CPN60	A[AS][^L][DEQ]E[^A][^Q][^R].GG[GA]	PS00296	Chaperonins cpn60 signature.
CHAPERONINS_CPN10	[LIVMFY].P[ILT].[DEN][KR][LIVMFA]{3}[KREQS].{8,9}[SG].[LIVMFY]{3}	PS00681	Chaperonins cpn10 signature.
TCP1_1	[RKEL][ST].[LMFY]GP.[GSA]..K[LIVMF]{2}	PS00750	Chaperonins TCP-1 signature 1.
TCP1_2	[LIVM][TS][NK][DN][GA][AVNHK][TAVC][LIVM]{2}.{2}[LIVMA].[LIVM].[SNH][PQHA]	PS00751	Chaperonins TCP-1 signature 2.
TCP1_3	Q[DEK]..[LIVMGTA][GA]DGT	PS00995	Chaperonins TCP-1 signature 3.
HSP70_1	[IV]DLGT[ST].[SC]	PS00297	Heat shock hsp70 proteins family signature 1.
HSP70_2	[LIVMF][LIVMFY][DN][LIVMFS]G[GSH][GS][AST].{3}[ST][LIVM][LIVMFC]	PS00329	Heat shock hsp70 proteins family signature 2.
HSP70_3	[LIVMY].[LIVMF].GG.[ST][^LS][LIVM]P.[LIVM].[DEQKRSTA]	PS01036	Heat shock hsp70 proteins family signature 3.
HSP90	Y.[NQHD][KHR][DE][IVA]F[LM]R[ED]	PS00298	Heat shock hsp90 proteins family signature.
CLPAB_1	[DA][AI][SGA][NQS][LIVMF]{2}K[PT].[LM].{2}G	PS00870	Chaperonins clpA/B signature 1.
CLPAB_2	[RGT][LIVMFY][DN].[ST]E[LIVMFY].[ED][KRQEAS].[STA].[STAD][KRS][LIVM].G[STAP]	PS00871	Chaperonins clpA/B signature 2.
DNAJ_1	[FY][^GL].[LIVMA][^IP].{2}[FYWHNT][DENQSA].L.[DN].{3}[KR][^F][^P][FYI]	PS00636	Nt-dnaJ domain signature.
GRPE	[FYLV][DNST][PHEAYVS].{2}[HMACNQ].[ALV][LIVMTNSF].{16,21}[GYP][FY].{3,4}[DENGKS].{2,3}[LIV][KRIV].[STAG].V.{0,1}[IV]	PS01071	grpE protein signature.
T2SP_C	P.{6}F.{4}[LF].{3}D[LIVM]A[LIVM].[LIVM]N.[LIVMQ].[LF]	PS01141	Bacterial type II secretion system protein C signature.
T2SP_D	[GRH][DEQKG][STVM][LIVMA]{3}[GA]G[LIVMFY].{11}[LIVM]P[LIVMFYWGS][LIVMF][GSAE].[LIVMS]P[LIVMFYW][LIVMFYWS].{2,3}[LV][FK]	PS00875	Bacterial type II secretion system protein D signature.
T2SP_E	[LIVM]R.{2}PD.[LIVM]{3}GE[LIVM]RD	PS00662	Bacterial type II secretion system protein E signature.
T2SP_F	[KRQ][LIVMAW].{2}[SAIV][LIVM].[TY]P.{2}[LIVM].{3}[STAGV].{6}[LMY].{3}[LIVMF]{2}P	PS00874	Bacterial type II secretion system protein F signature.
T2SP_N	GTLW.G.{11}L.{4}W	PS01142	Bacterial type II secretion system protein N signature.
FHIPEP	R[LIVM][GSAT]EV[GSAR]ARF[STAIV]LD[GSA][LM]PGKQM[GSA]ID[GSA][DAE]	PS00994	Bacterial export FHIPEP family signature.
SECA	[IV].[IV][SA]T[NQ]MAGRG.DI.L	PS01312	SecA family signature.
SECY_1	[GSTL][LIVMFK][LIVMFCA].[LIVMF][GSAN][LIVM].P[LIVMFYN][LIVMFY].[AS][GSTQD][LIVMFAT]{3}[EQ][LIVMFA]{2}	PS00755	Protein secY signature 1.
SECY_2	[LIVMFYW]{2}.[DE].[LIVM][STDNQ].{2,3}[GK][LIVMF][GST][NST]G.[GST][LIV][LIVFP]	PS00756	Protein secY signature 2.
SECE_SEC61G	[LIVMFY].[^D][DENQGA].[^E].{2}[LIVMFTA].[KRV].{2}[KW]P.{3}[SEQ].{5}[^D][^CG][LIVT][LIVGA][LIVFGAST]	PS01067	Protein secE/sec61-gamma signature.
PILI_CHAPERONE	[LIVMFY][APN].[DNS][KREQ]E[STR][LIVMAR].[FYWTE].[NCS][LIVM].{2}[LIVM]P[PAS]	PS00635	Gram-negative pili assembly chaperone signature.
FIMBRIAL_USHER	[VL][PASQ][PAS]G[PAD][FY].[LI][DNQSTAP][DNH][LIVMFY]	PS01151	Fimbrial biogenesis outer membrane usher protein signature.
SRP54	P[LIVM].[FYL][LIVMAT][GS][^Q][GS][EQ].[^K].{2}[LIVMF]	PS00300	SRP54-type proteins GTP-binding domain signature.
CKS_1	YS.[KR]Y.[DE]{2}.[FY]EYRHV.[LV][PT][KRP]	PS00944	Cyclin-dependent kinases regulatory subunits signature 1.
CKS_2	H.PE.H[IV]LLF[KR]	PS00945	Cyclin-dependent kinases regulatory subunits signature 2.
PENTAXIN	H.C.[ST]W.[ST]	PS00289	Pentaxin family signature.
IG_MHC	[FY][^L]C[^PGAD][VA][^LC]H	PS00290	Immunoglobulins and major histocompatibility complex proteins signature.
PRION_1	AGAAAAGAVVGGLGGY	PS00291	Prion protein signature 1.
PRION_2	E.[ED].K[LIVM]{2}.[KR][LIVM]{2}.[QE]MC.{2}QY	PS00706	Prion protein signature 2.
CYCLINS	R.{2}[LIVMSA].{2}[FYWS][LIVM].{8}[LIVMFC].{4}[LIVMFYA].{2}[STAGC][LIVMFYQ].[LIVMFYC][LIVMFY]D[RKH][LIVMFYW]	PS00292	Cyclins signature.
PCNA_1	[GSTA][LIVMF].[LIVMAS].[GSAVI][LIVM][DS].[NSAED][HKRNS][VIT].[LMYF][VIGAL].[LIVMF].[LIVM].{4}F	PS01251	Proliferating cell nuclear antigen signature 1.
PCNA_2	[RKA]C[DE][RH].{3}[LIVMF].{3}[LIVM].[SGAN][LIVMF].K[LIVMF]{2}	PS00293	Proliferating cell nuclear antigen signature 2.
ACTIN_DEPOLYMERIZING	P[DES].[SA].[LIVMT][KR].[KR][LIVM]{2}[YA][STA]{3}.{3}[LIVMF][KRS]	PS00325	Actin-depolymerizing proteins signature.
BH1	[LVMENQ][FTLS].[GSDECQ][GLPCKH].{1,2}[NST][YW]G[RK][LIV][LIVC][GAT][LIVMF]{2}.F[GSAEC][GSARY]	PS01080	Apoptosis regulator, Bcl-2 family BH1 motif signature.
BH2	W[LIM].{3}[GR]G[WQ][DENSAV].[FLGA][LIVFTC]	PS01258	Apoptosis regulator, Bcl-2 family BH2 motif signature.
BH3	[LIVAT].{3}L[KARQ].[IVAL]GD[DESG][LIMFV][DENSHQ][LVSHRQ][NSR]	PS01259	Apoptosis regulator, Bcl-2 family BH3 motif signature.
BH4_1	[DS][NT]R[AE][LI]V.[KD][FY][LIV][GHS]YKL[SR]Q[RK]G[HY].[CW]	PS01260	Apoptosis regulator, Bcl-2 family BH4 motif signature.
BI1	G.{2}[LIVM][GC]P.[LI].{4}[SAGDT].{4,6}[LIVM]{2}.{2}A.{2}[MG]T.[LIVM].F	PS01243	Bax inhibitor-1 family signature.
ARRESTINS	[FY]RYG.[DE]{2}.[DE][LIVM]{2}G[LIVM].F.[RK][DEQ][LIVM]	PS00295	Arrestins signature.
AAA	[LIVMTR].[LIVMT][LIVMF].[GATMC][ST][NS].{4}[LIVM]D.[AS][LIFAV].{1,2}R	PS00674	AAA-protein family signature.
UBIQUITIN_1	K.{2}[LIVM].[DESAK].{3}[LIVM][PAQ].{3}Q.[LIVM][LIVMC][LIVMFY].G.{4}[DE]	PS00299	Ubiquitin domain signature.
ARF	[HRQT].[FYWI].[LIVM].{4}A.{2}G.{2}[LIVM].{2}[GSA][LIVMF].[WK][LIVM]	PS01019	ADP-ribosylation factors family signature.
RAN	DTAGQE[KR][LFY]GGLR[DE]GY[YF]	PS01115	GTP-binding nuclear protein ran signature.
SAR1	R.[LIVM]E[LV]F[MPT]CS[LIVM][LIVMY].[KRQ].GY.[DE][AG][FI].W[LIVM].[NQK]Y	PS01020	SAR1 family signature.
BAND_7	R.{2}[LIV][SAN].{6}[LIV]D.{2}T.{2}WG[LIVT][KRH][LIV].[KRA][LIV]E[LIV][KRQ]	PS01270	Band 7 protein family signature.
WD_REPEATS_1	[LIVMSTAC][LIVMFYWSTAGC][LIMSTAG][LIVMSTAGC].{2}[DN].[^P][LIVMWSTAC][^DP][LIVMFSTAG]W[DEN][LIVMFSTAGCN]	PS00678	Trp-Asp (WD) repeats signature.
RAS_GTPASE_ACTIV_1	[GSNA].[LIVMF][FYCI][LIVMFY]R[LIVMFY]{2}[GACNS][PAV][AV][LIV][LIVM][SGANT]P	PS00509	Ras GTPase-activating proteins domain profile.
DH_1	[LM].{2}[LIVMFYWGS][LI].{2}[PEQ][LIVMRF].{2}[LIVM].[KRS].{2}[LT].[LIVM].[DEQN][LIVM].{3}[STM]	PS00741	Dbl homology (DH) domain signature.
RASGEF	[VI]P[FYWVI].[GPSV].{2}[LIVMFYK].[DNE][LIVM].{13,35}[IVL]N[FYME].K	PS00720	Ras Guanine-nucleotide exchange factors domain signature.
MARCKS_1	GQENGHV[KR]	PS00826	MARCKS family signature 1.
MARCKS_2	ETPK{5}.{0,1}FSFKK.FKLSG.SFK[KR][NS][KR]KE	PS00827	MARCKS family phosphorylation site domain.
STATHMIN_1	P[KRQ][KR]{2}[DE].SL[EG]E	PS00563	Stathmin family signature 1.
STATHMIN_2	AE[KR]REHE[KR]EV	PS01041	Stathmin family signature 2.
EFACTOR_GTP	D[KRSTGANQFYW].{3}E[KRAQ].[RKQD][GC][IVMK][ST][IV].{2}[GSTACKRNQ]	PS00301	GTP-binding elongation factors signature.
EF1BD_1	[DEG]{2}[DEK][DE][LIVMF]DLFG	PS00824	Elongation factor 1 beta/beta'/delta chain signature 1.
EF1BD_2	[IV]QS.D[LIVM].A[FWM][DNQS]K[LIVM]	PS00825	Elongation factor 1 beta/beta'/delta chain signature 2.
EF_TS_1	LR.{2}[TS][GSDNQ].[GSA][LIVMF].{0,1}[DENKAC].K[KRNEQS][AV]L	PS01126	Elongation factor Ts signature 1.
EF_TS_2	[ELAS][LIVMF][NVCKGST][SCVA][QE]TD[FS][VLA][SAT][KRNLAQS]	PS01127	Elongation factor Ts signature 2.
EFP	K.[AV].{4}G.{2}[LIVT].VP.{2}[LIVC].{2}[GD]	PS01275	Elongation factor P signature.
IF1A	[IMVALH].G.[GSDENK][KRHFW].{4}[CL].DG.{2}[RY].{2}[RH][IL].G	PS01262	Eukaryotic initiation factor 1A signature.
IF4E	[DE][IFYL].{2}F[KRL].{2}[LIVM].P.WE[DVA].{5}GG[KR]W	PS00813	Eukaryotic initiation factor 4E signature.
IF5A_HYPUSINE	[PT]GKHG.AK	PS00302	Eukaryotic initiation factor 5A hypusine signature.
IF2	[GLES].[LIVM].{2}L[KR][KRHNS].K.{5}[LIVM].{2}[GNKADS].[DEN][CRG][GI]	PS01176	Initiation factor 2 signature.
IF3	[KR][LIVM]{2}[DN][FY][GSTN][KR][LIVMFYS].[FY][DEQTAHI].{2}[KRQ]	PS00938	Initiation factor 3 signature.
RF_PROK_I	[ARH][STA].G.GGQ[HNGCSY][VI]N.{3}[ST][AKG][IV]	PS00745	Prokaryotic-type class I peptide chain release factors signature.
RBFA	[LIVMF][QKRHSA][^E].[LIVMAC].{5,6}[LIVMW][RKAYF].[STACIVMF][PV][^LG][LIVMF].[FYI].{2}D	PS01319	Ribosome-binding factor A signature.
NUSG	[LIVM]FG[KRW].TP[IV].[LIVM]	PS01014	Transcription termination factor nusG signature.
CALPONIN_1	[LIVM].[LS]Q[MASY]G[STY][NT][KRQ].{2}[STN]Q.G.{3,4}G	PS01052	Calponin-like repeat signature.
CAP_1	[LIVM]{2}.RL[DE].{4}RLE	PS01088	CAP protein signature 1.
CAP_2	D[LIVMFY].E.[PA].PEQ[LIVMFY]K	PS01089	CAP protein signature 2.
CALRETICULIN_1	[KRHN].[DEQN][DEQNK].{3}CGG[AG][FY][LIVM][KN][LIVMFY]{2}	PS00803	Calreticulin family signature 1.
CALRETICULIN_2	[LIVM]{2}FGPD.C[AG]	PS00804	Calreticulin family signature 2.
CALRETICULIN_REPEAT	[IVM].[DV].[DENST].{2}KP[DEH]DW[DEN]	PS00805	Calreticulin family repeated motif signature.
CALSEQUESTRIN_1	[EQ][DE]GL[DN]FP.YDG.DRV	PS00863	Calsequestrin signature 1.
CALSEQUESTRIN_2	[DE]LEDW[LIVM]EDVL.G.[LIVM]NTEDDD	PS00864	Calsequestrin signature 2.
S100_CABP	[LIVMFYW]{2}.{2}[LKQ]D.{3}[DN].{3}[DNSG][FY].[ES][FYVC].{2}[LIVMFS][LIVMF]	PS00303	S-100/ICaBP type calcium binding protein signature.
HEMOLYSIN_CALCIUM	D.[LI].{4}G.D.[LI].GG.{3}D	PS00330	Hemolysin-type calcium-binding region signature.
HLYD_FAMILY	[LIVM].{2}G[LM].{3}[STGAV].[LIVMT].[LIVMTK][GE].[KR].[LIVMFYW]{2}.[LIVMFYW]{2}[LIVMFYWK]	PS00543	HlyD family secretion proteins signature.
PII_GLNB_UMP	Y[KR]G[AS][AE]Y	PS00496	P-II protein uridylation site.
PII_GLNB_CTER	[ST].{3}G[DY]G[KR][IV][FW][LIVM].{2}[LIVM]	PS00638	P-II protein C-terminal region signature.
1433_1	[RA]NL[LIV]S[VG][GA]Y[KN]N[IVA]	PS00796	14-3-3 proteins signature 1.
1433_2	YK[DE][SG]TLI[IML]QL[LF][RHC]DN[LF]T[LS]W[TANS][SAD]	PS00797	14-3-3 proteins signature 2.
BTG_1	Y.{2}[HP]W[FYH][APS][DE].P.KG.[GA][FY]RC[IV][RH][IV]	PS00960	BTG family signature 1.
BTG_2	[LV]P.[DE][LM][ST][LIVM]W[IV]DP.EV[SC].[RQ].G[EK]	PS01203	BTG family signature 2.
CULLIN_1	[LIV][KL].{2}[LIVM].{2}L[IL][DEQGVT][KRHNQ].[YT][LIVM].R.{5,7}[FYLV].Y.[SAT]	PS01256	Cullin family signature.
ER	YDI[SA].L[FY].F[IV]D.{3}D[LIV]S	PS01290	Enhancer of rudimentary signature.
FXYD	[DNSE].F.Y[DN].{2}[STNR][LIVM][RQ].{2}G	PS01310	FXYD family signature.
G10_1	LCC.[KR]C.{4}[DE].N.{4}C.CRVP	PS00997	G10 protein signature 1.
G10_2	C.HCGC[KRH]GC[SA]	PS00998	G10 protein signature 2.
GCKR	G[PA]E.[LIV][STAM]GS[ST]R[LIVM]K[STGA]{3}.{2}K	PS01272	Glucokinase regulatory protein family signature.
GTP1_OBG	D[LIVMA]PG[LIVM]{2}[DEYPKQV][GN]A.{2}G.G	PS00905	GTP1/OBG family signature.
HIT_1	[NQAR].{4}[GSAVY].[QFLPA].[LIVMY].[HWYRQ][LIVMFYST]H[LIVMFT]H[LIVMF][LIVMFPT][PSGAWN]	PS00892	HIT domain signature.
CASEIN_ALPHA_BETA	CL[LV]A.A[LVF]A	PS00306	Caseins alpha/beta signature.
CLAT_ADAPTOR_M_1	[IVT][GSP]WR.{2,3}[GAD].{2}[HY].{2}N.[LIVMAFY]{3}D[LIVM][LIVMT]E	PS00990	Clathrin adaptor complexes medium chain signature 1.
CLAT_ADAPTOR_M_2	[LIV].[FL][IQ]PP.G.[LIVMFY].[LV].{2}Y	PS00991	Clathrin adaptor complexes medium chain signature 2.
CLAT_ADAPTOR_S	[LIVMC][LIVM]Y[KR].{4}LYF	PS00989	Clathrin adaptor complexes small chain signature.
CORNICHON	[VI].{3}[DE]L.{2}D[FY].[NS][PS]I[DE]	PS01340	Cornichon family signature.
EPENDYMIN_1	[DE][SQLM][KAH][NT][QEK][SQ]C[SRKH].[EQKM][STM]L	PS00898	Ependymins signature 1.
EPENDYMIN_2	F.[PL]P[STA][FYT]C[DEQ][GAMI][LVMA].[TLF][DE][DEK]	PS00899	Ependymins signature 2.
ODC_AZ	[LT]LE[FY][AVC][DE][DE][KNQHT][LMT]	PS01337	Ornithine decarboxylase antizyme signature.
SYNTAXIN	[RQLKA].{3}[LIVMAW].{2}[LIVM][ESHLKAGQV].{2}[LIVMT].[DEVMNRAST][LIVMT].{2}[LIVMQ][FSAQGVM].{2}[LIVMF].{3}[LIVT].{2}Q[GADEQVST].{2}[LIVMA][DNQTEI].[LIVMF][DESVHAG].{2,3}[LIVM]	PS00914	Syntaxin / epimorphin family signature.
CRISP_1	[GDER][HR][FYWH][TVS][QA][LIVM][LIVMA]W.{2}[STN]	PS01009	CRISP family signature 1.
CRISP_2	[LIVMFYH][LIVMFY].C[NQRHS]Y.[PARH].[GL]N[LIVMFYWDN]	PS01010	CRISP family signature 2.
FETUIN_1	C[DN][DE].{54}CH.{9}C.{12,14}C.{17,19}C.{13}C.{2}C	PS01254	Fetuin family signature 1.
FETUIN_2	[ND].LET.CH.L	PS01255	Fetuin family signature 2.
LECTIN_LEGUME_BETA	[LIV][STAG]V[DEQV][FLI]D[ST]	PS00307	Legume lectins beta-chain signature.
LECTIN_LEGUME_ALPHA	[LIV][^LA][EDQ][FYWKR]V[^VF][LIVF]G[LF][ST]	PS00308	Legume lectins alpha-chain signature.
GALAPTIN	W[GEK].[EQ].[KRE].{3,6}[PCTF][LIVMF][NQEGSKV].[GH].{3}[DENKHS][LIVMFC]	PS00309	Galaptin signature.
LAMP_1	[STA]C[LIVM][LIVMFYW]A.[LIVMFYW].{3}[LIVMFYW].{3}Y	PS00310	Lysosome-associated membrane glycoproteins duplicated domain signature.
LAMP_2	C.{2}D.{3,4}[LIVM]{2}P[LIVM].[LIVM]G.{2}[LIVM].G[LIVM]{2}.[LIVM]{4}A[FY].[LIVM].{2}[KR][RH].{1,2}[STAG]{2}Y[EQ]	PS00311	LAMP glycoproteins transmembrane and cytoplasmic domain signature.
GLYCOPHORIN_A	II.[GAC]VMAG[LIVM]{2}	PS00312	Glycophorin A signature.
PMP22_1	[LIVMF][LIVMFC][LIVMF]{2}[SA][TL].{2}[DNKS].W.{9,13}[LIV]W.{2}[CG]	PS01221	PMP-22 / EMP / MP20 family signature 1.
PMP22_2	[RQ][AVS].[MC][IV]L[SA].[LI].{4}[GSA][LIVMF][LIVMFS][LIVMF]	PS01222	PMP-22 / EMP / MP20 family signature 2.
CLAUDIN	[GN]LW.{2}C.{7,9}[STDENQH]C	PS01346	Claudin family signature.
OSBP	E[KQ].[SC]H[HR][PG][PL].{1,2}[STACFI][ACGY]	PS01013	Oxysterol-binding protein family signature.
PIR_REPEAT_1	SQ[IV][STGNH]DGQ[LIV]Q[AIV][STA]	PS00929	Yeast PIR proteins repeats signature.
SVP_I	[IVM].GQD.VK.{5}[KN]G.{3}[STLV]	PS00313	Seminal vesicle protein I repeats signature.
SVP_II	[GSA]Q.KS[FY].Q.K[SA]	PS00515	Seminal vesicle protein II repeats signature.
SAA	ARGNY[ED]A.[QKR]RG.GG.WA	PS00992	Serum amyloid A proteins signature.
SPERMADHESIN_1	CG.{2}[LIY].{4}G.I.{9}C.WT	PS00985	Spermadhesins family signature 1.
SPERMADHESIN_2	C.KE.[LIVM]E[LIVM].[DE].{3}[GSE].{5}K.C	PS00986	Spermadhesins family signature 2.
SRP1_TIP1	PWY[ST]{2}RL	PS00724	Stress-induced proteins SRP1/TIP1 family signature.
SURF4	[KR]Y[DE]F[FY]Q.{2}S.[LIVM]GGLL	PS01339	SURF4 family signature.
GLYPICAN	C.{2}C.[GS][LIVM].{4}PC.{2}[FY]C.{2}[LIVM].{2}GC	PS01207	Glypicans signature.
SYNDECAN	[FY]R[IM][KR]K{2}DEGSY	PS00964	Syndecans signature.
TISSUE_FACTOR	WK.KC.{2}T.[DEN]TECD[LIVM]TDE	PS00621	Tissue factor signature.
TCTP_1	[IFAED][GA][GASF]N[PAK]S[GTA]E[GDEVCF][PAGEQV][DEQGAV]	PS01002	Translationally controlled tumor protein signature 1.
TCTP_2	[FLIV].{4}[FLVH][FY][MIVCT]GE.{4,7}[DENP][GAST].[LIVM][GAVI].{3}[FYWQ]	PS01003	Translationally controlled tumor protein signature 2.
TUB_1	F[KRHQ]GRV[ST].ASVKNFQ	PS01200	Tub family signature 1.
TUB_2	AF[AG]I[GSAC][LIVM][ST]SF.[GST]K.ACE	PS01201	Tub family signature 2.
HCP	HRHRGH.{2}[DE]{7}	PS00328	HCP repeats signature.
ICE_NUCLEATION	AGYGST.T	PS00314	Bacterial ice-nucleation proteins octamer repeat.
FTSW_RODA_SPOVE	[NV].{5}[GTR][LIVMA].P[PTLIVME].G[LIVM].{3}[LIVMFW]{2}S[YSAQ]GG[STN][SA]	PS00428	Cell cycle proteins ftsW / rodA / spoVE signature.
ENT_VIR_OMP_1	G[LIVMFY]N[LIVM]KYRYE	PS00694	Enterobacterial virulence outer membrane protein signature 1.
ENT_VIR_OMP_2	[FYW].{2}G.GY[KR]F$	PS00695	Enterobacterial virulence outer membrane protein signature 2.
HYPA	[GS].{4}[LIVMT].{4}[LIVMF].{2}[CSAM][LMFY].{6}[STC].{4,5}[PAC].[LIVMF].[LIVMF].{8}C.{1,2}[CH]	PS01249	Hydrogenases expression/synthesis hypA family signature.
HUPF_HYPC	^MC[LIV][GA][LIV]P.[QKR][LIV]	PS01097	Hydrogenases expression/synthesis hupF/hypC family signature.
STAPHYLOCOAGULASE	ARP.{3}K.S.TNAYNVTT.{2}[DN]G.{3}YG	PS00429	Staphylocoagulase repeat signature.
11S_SEED_STORAGE	NG.[DE]{2}.[LIVMF]C[ST].{11,12}[PAG]D	PS00305	11-S plant seed storage proteins signature.
CYCLOTIDE_BRACELET	CX{0,1}[ES]SC[AV][MFYW]I[PS]X{0,1}C	PS60008	Cyclotides bracelet subfamily signature.
CYCLOTIDE_MOEBIUS	C[GA]E[ST]C[FTV][GLTI]G[TSK]C	PS60009	Cyclotides Moebius subfamily signature.
DEHYDRIN_1	S{4}[SD][DE].[DE][GVE].{1,7}[GE].{0,2}[KR]{4}	PS00315	Dehydrins signature 1.
DEHYDRIN_2	[KR][LIM]K[DE]K[LIM]PG	PS00823	Dehydrins signature 2.
GERMIN	G.{4}H.HP.[AGS].E[LIVM]	PS00725	Germin family signature.
OLEOSINS	[AG][ST].{2}[AG].{2}[LIVM][SAD][TIF]P[LIVMF]{4}FSP[LIVM]{3}PA	PS00811	Oleosins signature.
SMALL_HYDR_PLANT_SEED	G[EQ]TVVPGGT	PS00431	Small hydrophilic plant seed proteins signature.
PATHOGENESIS_BETVI	G.{2}[LIVMF].{4}E.{2,3}[CSTAENV].{8,9}[GNDS][GS]{2}[CS].{2}[KT].{4}[FY]	PS00451	Pathogenesis-related proteins Bet v I family signature.
OLEEI	[EQT]G.VYCD[TNP]CR	PS00925	Pollen proteins Ole e I family signature.
THAUMATIN	G.[GF].C.T[GA]DC.{1,2}[GQ].{2,3}C	PS00316	Thaumatin family signature.
MRP	W.{2}[LIVM]D[VFY][LIVM]{3}D.PPGT[GS]D	PS01215	Mrp family signature.
GIDA_1	[GSA][PTAV].[YH]CPS[LIVMF][ED].K[LIVMFA].[KRNT][FY]	PS01280	Glucose inhibited division protein A family signature 1.
GIDA_2	[AC]G[QNV].[NTAI]G.{2}GY.[EA][SAG]{2}[SAGC][QSAGMVIT]G[LIVMWF][LIVMAF][AST][GA][LIVMTAR][NYAMF][ALVST]	PS01281	Glucose inhibited division protein A family signature 2.
NOL1_NOP2_SUN	[FV][DQ][KRA][LIVMA]L.D[AV]PC[ST][GA]	PS01153	NOL1/NOP2/sun family signature.
COF_1	[LIVFYAN][LIVMFA].{2}D[LIVMF][ND]GT[LIV][LVY][STANLM]	PS01228	Hypothetical cof family signature 1.
COF_2	[LIVMFC]GD[GSANQ].ND.{3}[LIMFY].{2}[AV].{2}[GSCP].{2}[LMP].{2}[GAS]	PS01229	Hypothetical cof family signature 2.
RIO1	[LIVMY][VI]H[GA]D[LF][SN]E[FY]N.[LIVM]	PS01245	RIO1/ZK632.3/MJ0444 family signature.
UPF0001	[FW]H[FM][IV]G.[LIV]Q.[NKRQ][KN].{3}[LIV]	PS01211	Uncharacterized protein family UPF0001 signature.
UPF0003	G[STIF]V.{2}[LIVM].{6}[LIVMF].{3}[DQT].{3}[LIVH].[LIV]P[NW].{2}[LIVMF][LIVFSTA].{5}[NV]	PS01246	Uncharacterized protein family UPF0003 signature.
UPF0004	[LIVM].[LIVMT].{2}GC.{3}C[STAN][FY]C.[LIVMT].{4}G	PS01278	Uncharacterized protein family UPF0004 signature.
UPF0011	S[DN][AS]G.P.[LIV][SNC]DPG	PS01296	Uncharacterized protein family UPF0011 signature.
UPF0012	[GTAL].{2}[IVT]CYD[LIVM].FP.{9}[GD]	PS01227	Uncharacterized protein family UPF0012 signature.
UPF0016	E[LIVM]GDKTF[LIVMF]{2}A	PS01214	Uncharacterized protein family UPF0016 signature.
UPF0017	D.{8}[GN][LFY].{4}[DET][LY]Y.{3}[ST].{7}[IV].{2}[PS].[LIVM].[LIVM].{3}[DN]D	PS01133	Uncharacterized protein family UPF0017 signature.
UPF0020	DP[LIVMF]CG[ST]G.{3}[LI]E	PS01261	Uncharacterized protein family UPF0020 signature.
UPF0021	CK.{2}F.{4}E.{22,23}SGGKD	PS01263	Uncharacterized protein family UPF0021 signature.
UPF0023	[DEN].{2}[LIVF][DE]{2}[LIV]L.{4}[IV][FY].{4}KG	PS01267	Uncharacterized protein family UPF0023 signature.
UPF0024	G.KD[KRAT].[AG][LVRSIT][TKS].Q.[LIVF][SGCYAT]	PS01268	Uncharacterized protein family UPF0024 signature.
UPF0025	DV[LIV].{2}GH[ST]H.{12}[LIVMF]NPG	PS01269	Uncharacterized protein family UPF0025 signature.
UPF0027	Q[LIVM].N.A.[LIVM]P.I.{6}[LIVM]PD.H.G.G.{2}[IV]G	PS01288	Uncharacterized protein family UPF0027 signature.
UPF0028	[GA][GS]G[GA]ARG.[SA]H.G.{9}[IVY].[IV][DV].{2}[GA]G.S.G	PS01237	Uncharacterized protein family UPF0028 signature.
UPF0029	G.{2}[LIVM]{2}.{2}[LIVM].{4}[LIVM].{5}[LIVM]{2}.R[FYW]{2}GG.{2}[LIVM]G	PS00910	Uncharacterized protein family UPF0029 signature.
UPF0031_1	[SAV][IVW][LVA][LIV]G[PNS]GL[GP].[DENQT]	PS01049	Uncharacterized protein family UPF0031 signature 1.
UPF0031_2	[GA]G.GD[TV][LT][STA]G.[LIVM]	PS01050	Uncharacterized protein family UPF0031 signature 2.
TATC	Y.{2}[FL][LIVMAFNT][LIVMAFT].[LVSI].{4}[GASF].{2}F[EQ][LIVMFC]P[LIVM]	PS01218	TatC family signature.
UPF0033	[LIV][DEN].{2}[TAG].{2}CP.[PT].[LIVMF].{11}[GN]	PS01148	Uncharacterized protein family UPF0033 signature.
UPF0034	[LIVM][DNG][LIVMF]N.GC[PS].{3,4}[LIVMASQ].{5,6}G[SACY]	PS01136	Uncharacterized protein family UPF0034 signature.
UPF0035	[LM][LF]T.R[SA].{3}[RK].{3}G.{3}FPGG	PS01293	Uncharacterized protein family UPF0035 signature.
UPF0036	H.SGH[GA].{3}[DE].{3}[LM].{5}P.{3}[LIVM]P.HG[DE]	PS01292	Uncharacterized protein family UPF0036 signature.
UPF0044	L[ST].{3}K.{3}[KR][SGA].[GA]H.L.P[LIV].{2}[LIV][GA].{2}G	PS01301	Uncharacterized protein family UPF0044 signature.
UPF0047	S.{2}[LIV].[LIV].{2}G.{4}GTWQ.[LIV]	PS01314	Uncharacterized protein family UPF0047 signature.
UPF0054	H[GSA].[LVCYT]H[LAI][LIMSANQVF]G[FYWMH].[HD]	PS01306	Uncharacterized protein family UPF0054 signature.
UPF0057	[LIVF].[STAC][LIVF]{3}P[PF][LIVA][GAV][IV].{4}[GKN]	PS01309	Uncharacterized protein family UPF0057 signature.
UPF0066	G[AV]F[STA].R[SA].{2}RPN	PS01318	Uncharacterized protein family UPF0066 signature.
UPF0067	DV.{5}HI[SA]CD.{4}SE	PS01320	Uncharacterized protein family UPF0067 signature.
UPF0076	[PA][ASTPV]R[SACVF].[LIVMFY].{2}[GSAKR].[LMVA].{5,8}[LIVM]E[MI]	PS01094	Uncharacterized protein family UPF0076 signature.
HESB	[FQ].[LIVMFY].[NH][PGT][NSKQR].{4}C.C[GSN].SF	PS01152	Hypothetical hesB/yadR/yfhF family signature.
