package run;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import feature.CutOutSequence;
import feature.GetFeature;
import feature.RNA_Object;
import weka.core.Instances;

/**
 * 
 * @author QiangCao
 *
 */
public class RunProgram {
	
	/**
	 * 
	 * @param RNAlist
	 * @param resultFeaturePath
	 * @throws IOException
	 */
	public void runGetFeature(ArrayList<RNA_Object> RNAlist, String resultFeaturePath) throws IOException {
		HashMap<String, Integer> CsitesMap = new HashMap<>();
		RNA_Object preRNA = new RNA_Object();
		CutOutSequence cos = new CutOutSequence();
		RNA_Object rna1 = new RNA_Object();
		RNA_Object rna2 = new RNA_Object();
		GetFeature gf = new GetFeature();
		
		File featureResultFileWrite = new File(resultFeaturePath);
		featureResultFileWrite.createNewFile();
		PrintWriter resultOut = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(resultFeaturePath))));
		
		resultOut.print("id,");
		for(int i=-15; i<=20; i++) {
			resultOut.print("seq1_"+i+",");
		}for(int i=-15; i<=19; i++) {
			resultOut.print("seq2_"+i+",");
		}for(int i=-15; i<=18; i++) {
			resultOut.print("seq3_"+i+",");
		}
		resultOut.print("GGC_(((,");
		resultOut.print("GG_((,");
		resultOut.print("G_(,");
		resultOut.print("G_(((,");
		resultOut.println("class");
		
		double[][] psm1 = gf.Pssm(1, "reference/seq_15_20_1.txt", "reference/seq_15_20_0.txt");
		double[][] psm2 = gf.Pssm(2, "reference/seq_15_20_1.txt", "reference/seq_15_20_0.txt");
		double[][] psm3 = gf.Pssm(3, "reference/seq_15_20_1.txt", "reference/seq_15_20_0.txt");
		
		for(int i=0; i<RNAlist.size(); i++) {
			CsitesMap = new HashMap<>();
			CsitesMap = cos.cSiteMarks(RNAlist.get(i));

			preRNA = new RNA_Object();
			preRNA.setAccessionId(RNAlist.get(i).getAccessionId());
			preRNA.setSequence(RNAlist.get(i).getSequence());
			preRNA.setStructure(RNAlist.get(i).getStructure());
			
			for(Map.Entry<String, Integer> entry: CsitesMap.entrySet()) {
				preRNA.setLocus(entry.getValue());
				rna1 = new RNA_Object();
				rna2 = new RNA_Object();
				rna2 = cos.cutOutRNA(preRNA, 16, 21);
//				rna1 = rna2;
//				rna1.setSequence(rna1.getSequence().substring(1, rna1.getSequence().length()));
//				rna1.setStructure(rna1.getStructure().substring(1, rna1.getStructure().length()));
				rna1 = cos.cutOutRNA(preRNA, 15, 20);
				rna1.setRnaClass("?");
				resultOut.print(rna1.getAccessionId()+"_"+rna1.getLocus()+",");
				resultOut.print(gf.GetPSSMnum(rna1.getSequence(), 1, psm1));
				resultOut.print(gf.GetPSSMnum(rna1.getSequence(), 2, psm2));
				resultOut.print(gf.GetPSSMnum(rna1.getSequence(), 3, psm3));
				resultOut.print(gf.GetSeqStrKmer(rna2));
				resultOut.println(rna1.getRnaClass());
	        }
			CsitesMap.clear();
		}
		resultOut.close();
	}
	
	/**
	 * 
	 * @param csvFilePath
	 * @param arffFilePath
	 * @param arffFilePath2
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ResultData> runWeka(String csvFilePath, String arffFilePath, String arffFilePath2) throws Exception {
		WekaRun wr = new WekaRun();
		wr.CSV_ARFF(csvFilePath, arffFilePath,arffFilePath2);
		Instances data1 = wr.readData(arffFilePath2);
		Instances data2 = wr.GetTestAttribute(data1);		
		ArrayList<ResultData> forecastRes = wr.precisionModel(data2);
		return forecastRes;
	}

}
