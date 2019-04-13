package feature;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author QiangCao
 *
 */
public class GetFeature {
	
	/**
	 * Cumulative GC content
	 * @param preSequence String: RNA sequence
	 * @param nt integer: window size
	 * @return num_GC StringBuffer: result
	 * 
	 */
	public StringBuffer num_GC_WinX(String preSequence, int nt) {
		StringBuffer num_GC = new StringBuffer();
		int count = 0;
		String suSeq = new String();
		for (int i = 9; i<=14; i++) {
			suSeq = preSequence.substring(i, i+nt);
			for(int j=0; j<nt; j++) {
				if(suSeq.charAt(j) == 'C' || suSeq.charAt(j) == 'G') {
					count++;
				}
			}
			num_GC.append((double)count/nt);
			num_GC.append(',');
			count=0;
		}
		return num_GC;
	}
	
	/**
	 * 
	 * @param preRNA
	 * @return
	 * @throws IOException
	 */
	public StringBuffer GetSeqStrKmer(RNA_Object preRNA) throws IOException {
		preRNA.setStructure(preRNA.getStructure().replace(')', '('));
		ArrayList<String> seqMark = new ArrayList<String>();
		seqMark.add("GGC");
		seqMark.add("GG");
		seqMark.add("G");
		ArrayList<String> strMark = new ArrayList<String>();
		strMark.add("(((");
		strMark.add("((");
		strMark.add("(");
		double[] result = new double[4];
		for(int i = 1; i < preRNA.getSequence().length()-3; i++) {
			String seq = preRNA.getSequence().substring(i, i+3);
			String str = preRNA.getStructure().substring(i, i+3);
			if(seq.equals(seqMark.get(0)) && str.equals(strMark.get(0))) {
				result[0]++;
			}
		}
		for(int i = 1; i < preRNA.getSequence().length()-2; i++) {
			String seq = preRNA.getSequence().substring(i, i+2);
			String str = preRNA.getStructure().substring(i, i+2);
			if(seq.equals(seqMark.get(1)) && str.equals(strMark.get(1))) {
				result[1]++;
			}
		}
		for(int i = 1; i < preRNA.getSequence().length()-1; i++) {
			String seq = preRNA.getSequence().substring(i, i+1);
			String str = preRNA.getStructure().substring(i, i+1);
			if(seq.equals(seqMark.get(2)) && str.equals(strMark.get(2))) {
				result[2]++;
			}
		}
		for(int i = 1; i < preRNA.getSequence().length()-1; i++) {
			String seq = preRNA.getSequence().substring(i, i+1);
			String str = preRNA.getStructure().substring(i-1, i+2);
			if(seq.equals(seqMark.get(2)) && str.equals(strMark.get(0))) {
				result[3]++;
			}
		}
		StringBuffer kmers_m = new StringBuffer();
		kmers_m.append(result[0]/(preRNA.getSequence().length()-4));
		kmers_m.append(',');
		kmers_m.append(result[1]/(preRNA.getSequence().length()-3));
		kmers_m.append(',');
		kmers_m.append(result[2]/(preRNA.getSequence().length()-2));
		kmers_m.append(',');
		kmers_m.append(result[3]/(preRNA.getSequence().length()-2));
		kmers_m.append(',');
		return kmers_m;
	}
	
	/**
	 * 
	 * @param seqFile
	 * @param a
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public double[][] GetPSSM(String seqFile, int a, int num) throws IOException {
		String fileKmer = "reference/kmers/seq_"+a+".txt";
		BufferedReader bufferedRead = new BufferedReader(new FileReader(fileKmer));
		String line = bufferedRead.readLine();
		ArrayList<String> seqMark = new ArrayList<String>();
		while(line != null) {
			seqMark.add(line);
			line = bufferedRead.readLine();
		}
		bufferedRead.close();
		bufferedRead = new BufferedReader(new FileReader(seqFile));
		line = bufferedRead.readLine();
		String preSeq = line;
		double[][] pssm;
		pssm = new double[seqMark.size()][preSeq.length()-a+1];
		String seq = new String();
		while(line != null) {
			for(int i = 0; i < preSeq.length()-a+1; i++ ) {
				seq = preSeq.substring(i, i+a);
				for(int j = 0; j < seqMark.size(); j++) {
					if(seq.equals(seqMark.get(j))) {
						double m = pssm[j][i];
						m++;
						pssm[j][i] = m;
						break;
					}
				}
			}
			line = bufferedRead.readLine();
			preSeq = line;
		}
		for(int i = 0; i < seqMark.size(); i++) {
			for(int j = 0; j < pssm[0].length; j++) {
				pssm[i][j] = pssm[i][j]/num;
			}
		}
		return pssm;
	}
	
	/**
	 * 
	 * @param m
	 * @param Padress
	 * @param Nadress
	 * @return
	 * @throws IOException
	 */
	public double[][] Pssm(int m, String Padress, String Nadress) throws IOException {
		GetFeature psmT = new GetFeature();
		double[][] psm1 = psmT.GetPSSM(Padress, m, 1196);
		double[][] psm2 = psmT.GetPSSM(Nadress, m, 11960);
		for(int i = 0; i < psm1.length; i++) {			
			for(int j = 0; j < psm1[0].length; j++) {
				psm1[i][j] = psm1[i][j] - psm2[i][j];
			}
		}
		return psm1;
	}
	
	/**
	 * 
	 * @param preSequence
	 * @param a
	 * @param pssm
	 * @return
	 * @throws IOException
	 */
	public StringBuffer GetPSSMnum(String preSequence, int a, double[][] pssm) throws IOException {
		String fileKmer = "reference/kmers/seq_"+a+".txt";
		BufferedReader bufferedRead = new BufferedReader(new FileReader(fileKmer));
		String line = bufferedRead.readLine();
		ArrayList<String> seqMark = new ArrayList<String>();
		while(line != null) {
			seqMark.add(line);
			line = bufferedRead.readLine();
		}
		bufferedRead.close();
		StringBuffer singleFre = new StringBuffer();
		for(int i=0; i<preSequence.length()-a+1; i++) {
			String seq = preSequence.substring(i, i+a);
			if(seq.contains("N")) {
				singleFre.append(0);
				singleFre.append(',');
			}else {
				for(int j=0; j<seqMark.size(); j++ ) {
					if(seq.equals(seqMark.get(j))) {
						singleFre.append(pssm[j][i]);
						singleFre.append(',');
					}
				}
			}
		}
		return singleFre;
	}
}
