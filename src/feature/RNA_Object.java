package feature;

/**
 * 
 * @author QiangCao
 *
 */
public class RNA_Object {
	
	private String accessionId = new String();
	private String sequence = new String();
	private String structure = new String();
	private int locus;
	private String rnaClass;
	
	/**
	 * 
	 * @param accessionId
	 * @param sequence
	 * @param structure
	 * @param locus
	 * @param rnaClass
	 */
	public RNA_Object(String accessionId, String sequence, String structure, int locus, String rnaClass) {
		super();
		this.accessionId = accessionId;
		this.sequence = sequence;
		this.structure = structure;
		this.locus = locus;
		this.rnaClass = rnaClass;
	}
	public RNA_Object() {
		super();
		this.accessionId = null;
		this.sequence = "";
		this.structure = "";
		this.locus = 0;
		this.rnaClass = "N";
	}
	
	public String getAccessionId() {
		return accessionId;
	}
	public void setAccessionId(String accessionId) {
		this.accessionId = accessionId;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public int getLocus() {
		return locus;
	}
	public void setLocus(int locus) {
		this.locus = locus;
	}
	public String getRnaClass() {
		return rnaClass;
	}
	public void setRnaClass(String rnaClass) {
		this.rnaClass = rnaClass;
	}
	
	public boolean isSequence(String sequence) {
		boolean seqR = sequence.matches("[a-zA-Z]+");
		return seqR;
	}
	public boolean isStructure(String structure) {
		boolean strR = structure.matches("[(.)]+");
		return strR;
	}
	
}
