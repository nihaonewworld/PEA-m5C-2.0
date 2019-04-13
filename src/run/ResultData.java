package run;

import feature.RNA_Object;

/**
 * 
 * @author QiangCao
 *
 */
public class ResultData extends RNA_Object{
	
	private String id;
	private int site;
	private String label;
	private double CC;
	private double[] allCC;
	private String[] allLabel;
	
	/**
	 * 
	 * @param id
	 * @param site
	 * @param label
	 * @param cC
	 * @param allCC
	 * @param allLabel
	 */
	public ResultData(String id, int site, String label, double cC, double[] allCC, String[] allLabel) {
		super();
		this.id = id;
		this.site = site;
		this.label = label;
		CC = cC;
		this.allCC = allCC;
		this.allLabel = allLabel;
	}
	
	public ResultData() {
		super();
		this.id = "";
		this.site = 0;
		this.label = "";
		CC = 0.0;
		this.allCC = new double[10];
		this.allLabel = new String[10];
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSite() {
		return site;
	}

	public void setSite(int site) {
		this.site = site;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getCC() {
		return CC;
	}

	public void setCC(double cC) {
		CC = cC;
	}

	public double[] getAllCC() {
		return allCC;
	}

	public void setAllCC(double[] allCC) {
		this.allCC = allCC;
	}

	public String[] getAllLabel() {
		return allLabel;
	}

	public void setAllLabel(String[] allLabel) {
		this.allLabel = allLabel;
	}
}