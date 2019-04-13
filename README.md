# **PEA-2.0**: A java graphical interface for plant transcriptome m5C analysis. </br>
![](https://hoursandseconds.files.wordpress.com/2014/09/java8-logo.png?w=281&h=300 "Java logo")
![](https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSvCvZWbl922EJkjahQ5gmTpcvsYr3ujQBpMdyX-YG99vGWfTAmfw "linux logo")
![](https://tctechcrunch2011.files.wordpress.com/2014/06/apple_topic.png?w=220)
<br>

We developed PEA-m5C-2.0, an accurate transcriptome-wide m5C modification predictor under machine learning framework with the random forest algorithm, which is an extension of the PEA-m5C method. PEA-m5C-2.0 was trained with features from the flanking sequences of m5C modifications. Feature information was extracted using a position-specific matrix combined with RNA secondary structure, where the secondary structure was calculated by RNAfold.
<br>
Programming a cross-platform graphical interface by Java language. This tool is available for Windows, Mac, and Ubuntu. If you are using another operating system, please use the RNAfold Web server nor ViennaRNA Package to get RNA second structure, and run. In addition, we also deposited all the candidate m5C modification sites in the [Ara-m5C](http://bioinfo.nwafu.edu.cn/software/Ara-m5C.html) database for follow-up functional mechanism researches.
<br>

## Version and download <br>
*  [Version 2.0--JAVA](https://github.com/nihaonewworld/PEA-m5C-2.0) -First version released on April, 10th, 2019<br>

## Depends
#### Global software environment <br>
* [JAVA1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) Environmentally dependent <br>

## Contents <br>
#### Predicting m5C sites <br>
* Read FASTA file and motif scanning <br>
* RNA secondary structure calculation by [RNAfold](http://rna.tbi.univie.ac.at/cgi-bin/RNAWebSuite/RNAfold.cgi) <br>
* Feature encoding of sequences and structure <br>
* m5C prediction using Random Forest models <br>

## Quick start <br>
#### Predicting m5C sites <br>
```Java
java -jar PEA2.0.jar
```
![graphical interface](https://github.com/nihaonewworld/PEA-m5C-2.0/blob/master/reference/graphic%20interface.JPG)

#### 1.Running RNAfold to calculated RNA secondary structure
```Java
RunRNAfold rrf = new RunRNAfold();
rrf.runRNAfold("result/seq.fasta", "result/str.txt");
//RNAfold --noLP --noPS <result/seq.fasta >result/str.txt
```
#### 2.Feature encoding of sequences
```Java
RunProgram run = new RunProgram();
  try {
    run.runGetFeature(RNAlist, "result/feature.csv");
  } catch (IOException e1) {
    e1.printStackTrace();
  }
```
#### 3.m5C prediction using Random Forest models
```Java
RunProgram run = new RunProgram();
ArrayList<ResultData> forecastResult = run.runWeka("result/feature.csv", 
        "result/feature.arff", "result/feature2.arff");
```
## Ask questions
Please use [PEAm5C/issues](https://github.com/nihaonewworld/PEA-m5C-2.0/issues) for how to use PEAm5C and reporting bugs.
