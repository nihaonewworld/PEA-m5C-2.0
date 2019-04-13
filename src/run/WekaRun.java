package run;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.JFrame;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

/**
 * 
 * @author QiangCao
 *
 */
public class WekaRun {
	
	/**
	 * 
	 * @param dataPath
	 * @return
	 * @throws Exception
	 */
	public Instances readData(String dataPath) throws Exception {
		DataSource source = new DataSource(dataPath);
		Instances data = source.getDataSet();
		data.setClass(data.attribute("class"));
//		if(data.classIndex() == -1)
//            data.setClassIndex(data.numAttributes()-1);
		return data;
	}
	
	/**
	 * 
	 * @param csvPath
	 * @param arffPath
	 * @throws IOException
	 */
	public void CSV_ARFF(String csvPath, String arffPath) throws IOException {
		// load CSV
	    CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(csvPath));
	    Instances data = loader.getDataSet();
		data.setClass(data.attribute("class"));
//	    data.setClassIndex(data.numAttributes()-1);
	    // save ARFF
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(arffPath));
	    saver.writeBatch();
	}

	/**
	 * 
	 * @param csvPath
	 * @param arffPath1
	 * @param arffPath2
	 * @throws IOException
	 */
	public void CSV_ARFF(String csvPath, String arffPath1, String arffPath2) throws IOException {
		// load CSV
	    CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(csvPath));
	    Instances data = loader.getDataSet();
		data.setClass(data.attribute("class"));
	    // save ARFF
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(arffPath1));
	    saver.writeBatch();
	    
	    BufferedReader bufferedRead = new BufferedReader(new FileReader(arffPath1));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(arffPath2)));
	    String line = bufferedRead.readLine();
	    while(line != null) {
	    	if(line.contains("class")){
	    		out.println(line.replace("@attribute class string", "@attribute class {Y,N}"));
	    	}else{
	    		out.println(line);
	    	}
	    	line = bufferedRead.readLine();
	    }
	    bufferedRead.close();
        out.close();
	}
	
	/**
	 * 
	 * @param trainPreData
	 * @param AttributeSelectionPath
	 * @param numAtt
	 * @return
	 * @throws Exception
	 */
	public Instances RunAttributeSelection(Instances trainPreData, 
			String AttributeSelectionPath, int numAtt) throws Exception {
		trainPreData.setClass(trainPreData.attribute("class"));
		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker rank = new Ranker();
		eval.buildEvaluator(trainPreData);
		rank.setNumToSelect(numAtt);
		filter.setEvaluator(eval);
		filter.setSearch(rank);
		filter.setInputFormat(trainPreData);
		Instances newData = Filter.useFilter(trainPreData, filter);
		
		int[] attrIndex = rank.search(eval, trainPreData);
		StringBuffer attrIndexInfo = new StringBuffer();
		StringBuffer attrInfoGainInfo = new StringBuffer();
		attrIndexInfo.append("Selected attributes:");
		for(int i = 0; i < numAtt; i++) {
			attrIndexInfo.append(attrIndex[i]);
			attrIndexInfo.append(",");
			attrInfoGainInfo.append((trainPreData.attribute(attrIndex[i]).name()));
			attrInfoGainInfo.append(",");
			attrInfoGainInfo.append(eval.evaluateAttribute(attrIndex[i]));
			attrInfoGainInfo.append("\n");
		}
		
		String resultFeaturePath = AttributeSelectionPath;
		File featureResultFileWrite = new File(resultFeaturePath);
		featureResultFileWrite.createNewFile();
		PrintWriter resultOut = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(resultFeaturePath))));
		resultOut.print(attrInfoGainInfo.toString());
		resultOut.close();
		return newData;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> asM() {
		ArrayList<String> asMark = new ArrayList<String>();		
		asMark.add("seq3_-3");
		asMark.add("seq3_-2");
		asMark.add("seq2_-2");
		asMark.add("seq3_-1");
		asMark.add("seq3_-4");
		asMark.add("seq2_-1");
		asMark.add("seq1_-1");
		asMark.add("seq2_-3");
		asMark.add("seq1_-2");
		asMark.add("seq3_1");
		asMark.add("seq3_2");
		asMark.add("seq3_-5");
		asMark.add("seq2_1");
		asMark.add("seq3_0");
		asMark.add("seq2_-4");
		asMark.add("seq3_-8");
		asMark.add("seq3_-7");
		asMark.add("seq3_-6");
		asMark.add("seq3_-9");
		asMark.add("seq2_2");
		asMark.add("seq2_-7");
		asMark.add("seq3_3");
		asMark.add("seq1_-3");
		asMark.add("seq3_-10");
		asMark.add("seq1_1");
		asMark.add("seq2_0");
		asMark.add("seq2_-6");
		asMark.add("GGC_(((");
		asMark.add("GG_((");
		asMark.add("seq2_-8");
		asMark.add("seq3_4");
		asMark.add("seq2_-9");
		asMark.add("seq1_-6");
		asMark.add("seq1_2");
		asMark.add("seq3_-11");
		asMark.add("seq2_3");
		asMark.add("G_(");
		asMark.add("seq2_-5");
		asMark.add("seq1_-7");
		asMark.add("seq3_5");
		asMark.add("seq2_-10");
		asMark.add("seq3_6");
		asMark.add("seq3_10");
		asMark.add("seq1_-4");
		asMark.add("G_(((");
		asMark.add("seq3_-15");
		asMark.add("seq3_18");
		asMark.add("seq3_17");
		asMark.add("seq3_7");
		asMark.add("seq1_-9");
		return asMark;
	}
	
	/**
	 * 
	 * @param testData
	 * @return
	 * @throws Exception
	 */
	public Instances GetTestAttribute(Instances testData) throws Exception {
		ArrayList<String> asMark = new ArrayList<String>();
		asMark = asM();
		
		for(int i=1; i<testData.numAttributes()-1; i++) {
			int exist=0;
			for(int j=0; j<asMark.size(); j++) {
				if(testData.attribute(i).name().equals(asMark.get(j))) {
					exist=1;
					asMark.remove(j);
					break;
				}
			}
			if(exist == 0) {
				testData.deleteAttributeAt(i);
				i--;
			}
		}

		Instances newData = new Instances(testData);
		return newData;
	}
	
	/**
	 * 
	 * @param trainPreData
	 * @param newPreData
	 * @param testPreData
	 * @return
	 * @throws Exception
	 */
	public Instances GetTestAttribute(Instances trainPreData, 
			Instances newPreData, Instances testPreData) throws Exception {
		Enumeration<Attribute> oldDataAttributes = trainPreData.enumerateAttributes();
		int index=0;
		while(oldDataAttributes.hasMoreElements()){
			Attribute oldAttribute = oldDataAttributes.nextElement();
			String thisAttributeString = oldAttribute.toString();
			Enumeration<Attribute> newDataAttributes = newPreData.enumerateAttributes();	
			int exist=0;
			while(newDataAttributes.hasMoreElements()){
				Attribute newAttribute = newDataAttributes.nextElement();
				String newString = newAttribute.toString();
				if(newString.equals(thisAttributeString))
					exist=1;
			}
			if(exist==0){
				testPreData.deleteAttributeAt(index);
				index--;
			}
			index++;
		}
		return testPreData;
	}
	
	/**
	 * 
	 * @param trainPreData
	 * @param modelPath
	 * @param modelInfoPath
	 * @throws Exception
	 */
	public void buildModel(Instances trainPreData, String modelPath, 
			String modelInfoPath) throws Exception {
		RandomForest forest = new RandomForest();
		forest.setNumIterations(500);
		forest.buildClassifier(trainPreData);
        SerializationHelper.write(modelPath, forest);
		// 10-fold cross-validation
	    Evaluation evaluation = new Evaluation(trainPreData);
	    evaluation.crossValidateModel(forest, trainPreData, 10, new Random(1));
	    //leave one lost
	    //evaluation.crossValidateModel(forest, trainPreData, 1196, new Random(1));
	    //ROC
		ThresholdCurve tc = new ThresholdCurve();
		int classIndex = 0;
		Instances curve = tc.getCurve(evaluation.predictions(), classIndex);
		PlotData2D plotData = new PlotData2D(curve);
		plotData.setPlotName(curve.relationName());
		plotData.addInstanceNumberAttribute();
		ThresholdVisualizePanel tvp = new ThresholdVisualizePanel();
		tvp.setROCString("(Area under ROC = "
				+Utils.doubleToString(ThresholdCurve.getROCArea(curve), 4)+")");
		tvp.addPlot(plotData);
		final JFrame jf = new JFrame("WEKA ROC:"+tvp.getName());
		jf.setSize(500, 400);
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(tvp, BorderLayout.CENTER);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
		//
		File featureResultFileWrite = new File(modelInfoPath);
		featureResultFileWrite.createNewFile();
		PrintWriter resultOut = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(modelInfoPath))));
		resultOut.println("Summary:"+evaluation.toSummaryString());
		resultOut.println(evaluation.toClassDetailsString());
		resultOut.close();  
	}
	
	/**
	 * 
	 * @param testPreData
	 * @param modelPath
	 * @param modelInfoPath
	 * @throws Exception
	 */
	public void testModel(Instances testPreData, String modelPath, String modelInfoPath) throws Exception {
		testPreData.setClassIndex(testPreData.numAttributes()-1);
		Classifier classifier8 = (Classifier) weka.core.SerializationHelper.read(modelPath);
		Evaluation evaluation = new Evaluation(testPreData);
		evaluation.evaluateModel(classifier8, testPreData);
		
		File featureResultFileWrite = new File(modelInfoPath);		
		featureResultFileWrite.createNewFile();
		PrintWriter resultOut = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(modelInfoPath))));
		resultOut.println("Summary:"+evaluation.toSummaryString());
		resultOut.println(evaluation.toClassDetailsString());
		resultOut.close();
	}
	
	/**
	 * 
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ResultData> precisionModel(Instances dataList) throws Exception {
		Instances data3 = new Instances(dataList);
		for(int i=1; i<data3.numAttributes()-1;) {
			data3.deleteAttributeAt(i);
		}
		dataList.deleteAttributeAt(0);
		
		ArrayList<ResultData> reult = new ArrayList<>();
		for(int i=0; i<data3.numInstances(); i++) {
			ResultData rd = new ResultData();
			reult.add(rd);
		}
		String[][] classMark = new String[data3.numInstances()][10];
		double[][] classPMark = new double[data3.numInstances()][10];
		
		for(int i=1; i<=10; i++) {		
			String modelPath = "model/RF_"+i+".model";
			Classifier classModel = (Classifier) weka.core.SerializationHelper.read(modelPath);
			for (int j=0; j<dataList.numInstances(); j++) {
				double pred = classModel.classifyInstance(dataList.instance(j));
				double[] dist = classModel.distributionForInstance(dataList.instance(j));
				String[] idLocus = new String[2];
				idLocus = data3.instance(0).attribute(0).value(j).split("_");
				reult.get(j).setId(idLocus[0]);
				reult.get(j).setSite(Integer.parseInt(idLocus[1]));
				classMark[j][i-1] = dataList.classAttribute().value((int) pred);
				classPMark[j][i-1] = dist[0];
	        }	
		}
		for (int j=0; j<dataList.numInstances(); j++) {
			reult.get(j).setAllCC(classPMark[j]);
			reult.get(j).setAllLabel(classMark[j]);
			double sum = 0;
		    double avr = 0.0;
		    for (int i=0; i<reult.get(j).getAllCC().length; i++) {
		        sum+=reult.get(j).getAllCC()[i];
		    }
		    avr=sum/reult.get(j).getAllCC().length;
		    reult.get(j).setCC(avr);
		    if(avr>=0.5) {
		    	reult.get(j).setLabel("Y");
		    }else {
		    	reult.get(j).setLabel("N");
		    }
		}
		return reult;
    }
	
}
