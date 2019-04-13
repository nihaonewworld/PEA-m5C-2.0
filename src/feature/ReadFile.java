package feature;

import java.io.*;
import java.util.ArrayList;

/**
 * 
 * @author QiangCao
 *
 */
public class ReadFile {
	
	/**
	 * 
	 * @param seqTxt
	 * @return
	 */
	public ArrayList<RNA_Object> readFasta(String[] seqTxt) {
		ArrayList<RNA_Object> allRNA = new ArrayList<RNA_Object>();
		RNA_Object RNA = new RNA_Object();
		for(int i=0; i<seqTxt.length;) {
			if(seqTxt[i].startsWith(">")) {
				RNA = new RNA_Object();
				RNA.setAccessionId(seqTxt[i].substring(1).split(" ")[0]);
				i++;
			}else{
				while(i<seqTxt.length && !seqTxt[i].startsWith(">") && seqTxt[i].matches("[a-zA-Z]+")) {
					RNA.setSequence(RNA.getSequence()+seqTxt[i]);
					i++;
				}
			allRNA.add(RNA);
			}
		}
		return allRNA;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public ArrayList<RNA_Object> readFasta(String filePath) throws IOException {
		ArrayList<RNA_Object> allRNA = new ArrayList<RNA_Object>();
		RNA_Object RNA = new RNA_Object();
		BufferedReader rFasta = new BufferedReader(new FileReader(filePath));
		String line = rFasta.readLine();
		while(line != null ) {
			if(line.startsWith(">")) {
				RNA = new RNA_Object();
				RNA.setAccessionId(line.substring(1).split(" ")[0]);
				line = rFasta.readLine();
			}else{
				while(line != null && !line.startsWith(">")) {
					RNA.setSequence(RNA.getSequence()+line);
					line = rFasta.readLine();
				}
			allRNA.add(RNA);
			}
		}
		rFasta.close();
		return allRNA;
	}
	
	/**
	 * 
	 * @param allRNA
	 * @param filePath
	 * @throws IOException
	 */
	public void writeFasta(ArrayList<RNA_Object> allRNA, String filePath) throws IOException {
		File featureResultFileWrite = new File(filePath);
		featureResultFileWrite.createNewFile();
		PrintWriter resultOut = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(filePath))));
		for(int i=0; i<allRNA.size(); i++) {
			resultOut.println(">"+allRNA.get(i).getAccessionId());
			int length = 60;
			int n = (allRNA.get(i).getSequence().length()+length-1) / length;
			//获取整个字符串可以被切割成字符子串的个数
			String[] split = new String[n];
			for (int j=0; j<n; j++) {
				if (j < (n-1)) {
					split[j] = allRNA.get(i).getSequence().substring(j*length, (j+1)*length);
					resultOut.println(split[j]);
				} else {
					split[j] = allRNA.get(i).getSequence().substring(j*length);
					resultOut.println(split[j]);
				}
			}
			
		}
		resultOut.close();
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public ArrayList<RNA_Object> readSeqStr(String filePath) throws IOException {
		ArrayList<RNA_Object> allRNA = new ArrayList<RNA_Object>();
		RNA_Object RNA = new RNA_Object();
		BufferedReader rFasta = new BufferedReader(new FileReader(filePath));
		String line = rFasta.readLine();
		while(line != null ) {
			if(line.startsWith(">")) {
				RNA = new RNA_Object();
				RNA.setAccessionId(">"+line.substring(1).split(" ")[0]);
				line = rFasta.readLine();
				while(line != null && line.matches("[a-zA-Z]+")) {
					RNA.setSequence(RNA.getSequence()+line);
					line = rFasta.readLine();
				}
				while(line != null && line.matches("[(.)]+")) {
					RNA.setStructure(RNA.getStructure()+line);
					line = rFasta.readLine();
				}
				if(line != null && !line.contains(">") && line.contains(" ")) {
					RNA.setStructure(line.substring(0, line.indexOf(" ")));
					line = rFasta.readLine();
				}
				allRNA.add(RNA);
			}
		}
		rFasta.close();
		return allRNA;
	}
	
	/**
	 * 
	 * @param allRNA
	 * @param filePath
	 * @throws IOException
	 */
	public void writeSeqStr(ArrayList<RNA_Object> allRNA, String filePath) throws IOException {
		File featureResultFileWrite = new File(filePath);
		featureResultFileWrite.createNewFile();
		PrintWriter resultOut = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(filePath))));
		for(int i=0; i<allRNA.size(); i++) {
			resultOut.println(allRNA.get(i).getAccessionId());
			resultOut.println(allRNA.get(i).getSequence());
			resultOut.println(allRNA.get(i).getStructure());
		}
		resultOut.close();
	}

}
