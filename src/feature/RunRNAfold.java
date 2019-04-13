package feature;

/**
 * 
 * @author QiangCao
 *
 */
public class RunRNAfold {
	
	/**
	 * 
	 * @return
	 */
	public String DetermineSystemType() {
		String systemType = System.getProperty("os.name");
		return systemType;
	}
	
	/**
	 * 
	 * @param seqFile
	 * @param strFile
	 */
	public void runRNAfold(String seqFile, String strFile) {
		if(DetermineSystemType().contains("Windows")) {
			String command2 = "RNAfold.exe --noLP --noPS <"+seqFile+" >"+strFile;
			String command = "cmd /c cd RNAfold && "+command2;
			try {
				Process process = Runtime.getRuntime().exec(command);
				process.waitFor();
				System.out.println("exitValue:"+process.exitValue());
				System.out.println("waitFor:"+process.waitFor());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(DetermineSystemType().contains("Linux")) {
			
			String command1 = "./RNAfold_ubuntu --noLP --noPS <"+seqFile+" >"+strFile;
			String[] command = {"/bin/sh", "-c", "cd RNAfold;chmod 777 RNAfold_ubuntu;"+command1};
			try {
				Process process = Runtime.getRuntime().exec(command);
				process.waitFor();
				System.out.println("exitValue:"+process.exitValue());
				System.out.println("waitFor:"+process.waitFor());
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		else if(DetermineSystemType().contains("Mac")) {
			String command1 = "./RNAfold_mac --noLP --noPS <"+seqFile+" >"+strFile;
			String[] command = {"/bin/sh", "-c", "cd RNAfold && chmod 777 RNAfold_mac && "+command1};
			try {
				Process process = Runtime.getRuntime().exec(command);
				process.waitFor();
				System.out.println("exitValue:"+process.exitValue());
				System.out.println("waitFor:"+process.waitFor());
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		RunRNAfold rrf = new RunRNAfold();
		System.out.println(rrf.DetermineSystemType());
		rrf.runRNAfold("result/seq.fasta", "result/str.txt");
	}

}
