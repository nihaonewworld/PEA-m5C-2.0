package feature;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author QiangCao
 *
 */
public class CutOutSequence {
	
	/**
	 * Obtaining RNA fragment from full-length transcripts
	 * @param preRNA 
	 * @param lnt integer: C-site upstream length
	 * @param rnt integer: C-site downstream length
	 * @return RNA String: RNA fragment
	 */
	public RNA_Object cutOutRNA(RNA_Object preRNA, int lnt, int rnt) {
		RNA_Object RNA = new RNA_Object();
		RNA.setAccessionId(preRNA.getAccessionId());
		RNA.setLocus(preRNA.getLocus());
		RNA.setRnaClass(preRNA.getRnaClass());
		
		int a = RNA.getLocus()-1;
		if(!preRNA.getSequence().equals("")) {
			if(!preRNA.getStructure().equals("")) {
				if(a>=lnt && a<preRNA.getSequence().length()-rnt) {
					RNA.setSequence(preRNA.getSequence().substring(a-lnt, a+rnt+1));
					RNA.setStructure(preRNA.getStructure().substring(a-lnt, a+rnt+1));
				}else if(a<lnt && a<preRNA.getSequence().length()-rnt) {
					RNA.setSequence(preRNA.getSequence().substring(0, a+rnt+1));
					RNA.setStructure(preRNA.getStructure().substring(0, a+rnt+1));
					while(RNA.getSequence().length() < (lnt+rnt+1)) {
						RNA.setSequence('N'+RNA.getSequence());
						RNA.setStructure('.'+RNA.getStructure());
					}
				}else if(a>=lnt && a>=preRNA.getSequence().length()-rnt) {
					RNA.setSequence(preRNA.getSequence().substring(a-lnt));
					RNA.setStructure(preRNA.getStructure().substring(a-lnt));
					while(RNA.getSequence().length() < (lnt+rnt+1)) {
						RNA.setSequence(RNA.getSequence()+'N');
						RNA.setStructure(RNA.getStructure()+'.');
					}
				}else if(a<lnt && a>=preRNA.getSequence().length()-rnt) {
					String lSeq = new String();
					String lStr = new String();
					String rSeq = new String();
					String rStr = new String();
					lSeq = preRNA.getSequence().substring(0, a);
					lStr = preRNA.getStructure().substring(0, a);
					rSeq = preRNA.getSequence().substring(a);
					rStr = preRNA.getStructure().substring(a);
					while(lSeq.length()<lnt) {
						lSeq = 'N'+lSeq;
						lStr = '.'+lStr;
					}
					while(rSeq.length()<=rnt) {
						rSeq = rSeq+'N';
						rStr = rStr+'.';
					}
					RNA.setSequence(lSeq + rSeq);
					RNA.setStructure(lStr + rStr);
				}
			}else {
				if(a>=lnt && a<preRNA.getSequence().length()-rnt) {
					RNA.setSequence(preRNA.getSequence().substring(a-lnt, a+rnt+1));
				}else if(a<lnt && a<preRNA.getSequence().length()-rnt) {
					RNA.setSequence(preRNA.getSequence().substring(0, a+rnt+1));
					while(RNA.getSequence().length() < (lnt+rnt+1)) {
						RNA.setSequence('N'+RNA.getSequence());
					}
				}else if(a>=lnt && a>=preRNA.getSequence().length()-rnt) {
					RNA.setSequence(preRNA.getSequence().substring(a-lnt));
					while(RNA.getSequence().length() < (lnt+rnt+1)) {
						RNA.setSequence(RNA.getSequence()+'N');
					}
				}else if(a<lnt && a>=preRNA.getSequence().length()-rnt) {
					String lSeq = new String();
					String rSeq = new String();
					lSeq = preRNA.getSequence().substring(0, a);
					rSeq = preRNA.getSequence().substring(a);
					while(lSeq.length()<lnt) {
						lSeq = 'N'+lSeq;
					}
					while(rSeq.length()<=rnt) {
						rSeq = rSeq+'N';
					}
					RNA.setSequence(lSeq + rSeq);
				}
			}
		}else{
			System.out.println("null");
		}
		return RNA;
	}
	
	/**
	 * Obtaining the C sites in the full transcript
	 * @param allRNA ArrayList<RNA_Object>: RNA list
	 * @return markMap HashMap<String, Integer>: RNA.id and C-sites
	 */
	public HashMap<String, Integer> cSiteMarks(ArrayList<RNA_Object> allRNA) {
		HashMap<String, Integer> markMap = new HashMap<>();
		for(int i=0; i<allRNA.size(); i++) {
			for(int j=0; j<allRNA.get(i).getSequence().length(); j++) {
				if(allRNA.get(i).getSequence().toUpperCase().charAt(j) == 'C') {
					markMap.put(allRNA.get(i).getAccessionId()+"_"+j, (j+1));
				}
			}
		}
		return markMap;
	}
	
	/**
	 * Obtaining the C sites in the full transcript
	 * @param RNA Single RNA
	 * @return markMap HashMap<String, Integer>: RNA.id and C-sites
	 */
	public HashMap<String, Integer> cSiteMarks(RNA_Object RNA) {
		HashMap<String, Integer> markMap = new HashMap<>();
		for(int i=0; i<RNA.getSequence().length(); i++) {
			if(RNA.getSequence().toUpperCase().charAt(i) == 'C') {
				markMap.put(RNA.getAccessionId()+"_"+i, (i+1));
			}
		}
		return markMap;
	}
	
	
}
