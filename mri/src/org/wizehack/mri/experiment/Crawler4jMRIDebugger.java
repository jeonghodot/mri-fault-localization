package org.wizehack.mri.experiment;

import java.io.File;
import java.util.List;

import org.wizehack.mri.SFL;
import org.wizehack.mri.CoverageDataParser.CoverageFileReader;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.fl.MRI_I;
import org.wizehack.mri.io.FileListReader;
import org.wizehack.mri.repo.DataManager;
import org.wizehack.mri.repo.Result;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class Crawler4jMRIDebugger extends SFL {
	public int TYPE = 0;
	private static DataManager dManager = null;

	public Crawler4jMRIDebugger(String projectName, String repositoryIp,
			String sourceFolder, String exportFile) {
		super(projectName, repositoryIp, sourceFolder, exportFile);
	}

	@Override
	public TestSuite setTestResult() {
		//define test
		float id = 1;
		TestSuite testSuite = new Group();
		testSuite.setId(id);
		
		String rootDir = "/home/wizehack/exp/mri/Crawler4j/";
		String covDir = null;

		//target
		if(this.TYPE == 1){
			covDir = rootDir + "30/1";
		} else if(this.TYPE == 2) {
			covDir = rootDir + "30/2";
		} else if(this.TYPE == 3) {
			covDir = rootDir + "50/1";
		} else if(this.TYPE == 4) {
			covDir = rootDir + "50/2";
		} else if(this.TYPE == 5) {
			covDir = rootDir + "50/3";
		} else if (this.TYPE == 6) {
			covDir = rootDir + "60/1";
		} else if (this.TYPE == 7) {
			covDir = rootDir + "60/2";
		} else if (this.TYPE == 8) {
			covDir = rootDir + "60/3";
		} else if (this.TYPE == 9) {
			covDir = rootDir + "60/4";
		} else if (this.TYPE == 10) {
			covDir = rootDir + "60/5";
		} else if (this.TYPE == 11) {
			covDir = rootDir + "75/1";
		} else if (this.TYPE == 12) {
			covDir = rootDir + "75/2";
		} else if (this.TYPE == 13) {
			covDir = rootDir + "75/3";
		} else if (this.TYPE == 14) {
			covDir = rootDir + "75/4";
		} else if (this.TYPE == 15) {
			covDir = rootDir + "75/5";
		} else if (this.TYPE == 16) {
			covDir = rootDir + "75/6";
		} else if (this.TYPE == 17) {
			covDir = rootDir + "75/7";
		} 
		
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType("xml");
		
		List<String> fList = fileListReader.readFiles(new File(covDir));
		CoverageFileReader cfr = new CoverageFileReader();

		for(int i=0; i<fList.size(); i++){

			String name = fList.get(i);
			name = cfr.getCoverageFileName(name);
			
			int tcId = new Integer(name);
			TestCase testCase = new TestCase();
			testCase.setTcId(tcId);

			if(tcId == 1 || 
					tcId == 29 || 
					tcId == 32 ||
					tcId == 38 ||
					tcId == 42 ||
					tcId == 54 ||
					tcId == 59 ){
				testCase.setPassed(false);
			} else {
				testCase.setPassed(true);
			}
			
			testCase.setCoverageFile(fList.get(i));
			testSuite.add(testCase);
		}
		return testSuite;
	}

	public static void main(String[] args) {
		String ip = "127.0.0.1";
		String projectPath = "/home/wizehack/develop/workspace/crawler4j";
		dManager = new DataManager();

		mriTarantulaFristPhase(ip, projectPath);
		mriTarantulaSecondPhase();
	}
	
	private static void mriTarantulaSecondPhase(){
		mri_30(2);
		mri_50(3);
		mri_60(5);
		mri_75(7);
	}
	
	private static void mri_30(int numberOfTestGroup){
		MRI_I wizeFlII = new MRI_I("tarantula", 2);

		String db_30_1 = "CodeTest_Tarantula_30_1";
		String db_30_2 = "CodeTest_Tarantula_30_2";		
		
		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Tarantula_30_1 = mongo.getDB(db_30_1);
			List<Result> resultList_30_1 = dManager.getLocalizationCollection(db_Tarantula_30_1);
			
			DB db_Tarantula_30_2 = mongo.getDB(db_30_2);
			List<Result> resultList_30_2 = dManager.getLocalizationCollection(db_Tarantula_30_2);
			
			double t1 = wizeFlII.getThreshold(resultList_30_1);
			double t2 = wizeFlII.getThreshold(resultList_30_2);

			for(int i=0; i<resultList_30_1.size(); i++){
				Result result1 = resultList_30_1.get(i);
				Result result2 = resultList_30_2.get(i);
				
				
				if(	(dManager.isCovered(db_Tarantula_30_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_30_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 )
					) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness())) / numberOfTestGroup;
					dManager.updateSuspiciousness(db_Tarantula_30_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_30_2, id, suspiciousness);
				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_30_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_30_2.csv";
			
			dManager.exportToTextDoc(db_Tarantula_30_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_30_2, docFile2);

		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
	}
	
	private static void mri_50(int numberOfTestGroup){
		MRI_I wizeFlII = new MRI_I("tarantula", 3);

		String db_50_1 = "CodeTest_Tarantula_50_1";
		String db_50_2 = "CodeTest_Tarantula_50_2";		
		String db_50_3 = "CodeTest_Tarantula_50_3";		

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Tarantula_50_1 = mongo.getDB(db_50_1);
			List<Result> resultList_50_1 = dManager.getLocalizationCollection(db_Tarantula_50_1);
			
			DB db_Tarantula_50_2 = mongo.getDB(db_50_2);
			List<Result> resultList_50_2 = dManager.getLocalizationCollection(db_Tarantula_50_2);
	
			DB db_Tarantula_50_3 = mongo.getDB(db_50_3);
			List<Result> resultList_50_3 = dManager.getLocalizationCollection(db_Tarantula_50_3);

			
			double t1 = wizeFlII.getThreshold(resultList_50_1);
			double t2 = wizeFlII.getThreshold(resultList_50_2);
			double t3 = wizeFlII.getThreshold(resultList_50_3);

			for(int i=0; i<resultList_50_1.size(); i++){
				Result result1 = resultList_50_1.get(i);
				Result result2 = resultList_50_2.get(i);
				Result result3 = resultList_50_3.get(i);

				if(	(dManager.isCovered(db_Tarantula_50_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_50_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Tarantula_50_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 )
					) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) ) / numberOfTestGroup;
					dManager.updateSuspiciousness(db_Tarantula_50_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_50_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_50_3, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_50_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_50_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_50_3.csv";

			dManager.exportToTextDoc(db_Tarantula_50_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_50_2, docFile2);
			dManager.exportToTextDoc(db_Tarantula_50_3, docFile3);

		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
	}
	
	private static void mri_60(int numberOfTestGroup) {
		MRI_I wizeFlII = new MRI_I("tarantula", 5);

		String db_60_1 = "CodeTest_Tarantula_60_1";
		String db_60_2 = "CodeTest_Tarantula_60_2";		
		String db_60_3 = "CodeTest_Tarantula_60_3";		
		String db_60_4 = "CodeTest_Tarantula_60_4";		
		String db_60_5 = "CodeTest_Tarantula_60_5";		

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Tarantula_60_1 = mongo.getDB(db_60_1);
			List<Result> resultList_60_1 = dManager.getLocalizationCollection(db_Tarantula_60_1);
			
			DB db_Tarantula_60_2 = mongo.getDB(db_60_2);
			List<Result> resultList_60_2 = dManager.getLocalizationCollection(db_Tarantula_60_2);
	
			DB db_Tarantula_60_3 = mongo.getDB(db_60_3);
			List<Result> resultList_60_3 = dManager.getLocalizationCollection(db_Tarantula_60_3);

			DB db_Tarantula_60_4 = mongo.getDB(db_60_4);
			List<Result> resultList_60_4 = dManager.getLocalizationCollection(db_Tarantula_60_4);
			
			DB db_Tarantula_60_5 = mongo.getDB(db_60_5);
			List<Result> resultList_60_5 = dManager.getLocalizationCollection(db_Tarantula_60_5);
		
			double t1 = wizeFlII.getThreshold(resultList_60_1);
			double t2 = wizeFlII.getThreshold(resultList_60_2);
			double t3 = wizeFlII.getThreshold(resultList_60_3);
			double t4 = wizeFlII.getThreshold(resultList_60_4);
			double t5 = wizeFlII.getThreshold(resultList_60_5);

			for(int i=0; i<resultList_60_1.size(); i++){
				Result result1 = resultList_60_1.get(i);
				Result result2 = resultList_60_2.get(i);
				Result result3 = resultList_60_3.get(i);
				Result result4 = resultList_60_4.get(i);
				Result result5 = resultList_60_5.get(i);

				if(	(dManager.isCovered(db_Tarantula_60_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_60_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Tarantula_60_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 ) ||
						(dManager.isCovered(db_Tarantula_60_4, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t4 ) ||
						(dManager.isCovered(db_Tarantula_60_5, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t5 )
					) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) +
							new Double(result4.getSuspiciousness()) +
							new Double(result5.getSuspiciousness())
					) / numberOfTestGroup;
					dManager.updateSuspiciousness(db_Tarantula_60_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_60_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_60_3, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_60_4, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_60_5, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_60_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_60_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_60_3.csv";
			String docFile4 = "/home/wizehack/exp/mri/mri_60_4.csv";
			String docFile5 = "/home/wizehack/exp/mri/mri_60_5.csv";

			dManager.exportToTextDoc(db_Tarantula_60_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_60_2, docFile2);
			dManager.exportToTextDoc(db_Tarantula_60_3, docFile3);
			dManager.exportToTextDoc(db_Tarantula_60_4, docFile4);
			dManager.exportToTextDoc(db_Tarantula_60_5, docFile5);


		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
	}
	
	private static void mri_75(int numberOfTestGroup){
		MRI_I wizeFlII = new MRI_I("tarantula", 7);

		String db_75_1 = "CodeTest_Tarantula_75_1";
		String db_75_2 = "CodeTest_Tarantula_75_2";		
		String db_75_3 = "CodeTest_Tarantula_75_3";		
		String db_75_4 = "CodeTest_Tarantula_75_4";		
		String db_75_5 = "CodeTest_Tarantula_75_5";		
		String db_75_6 = "CodeTest_Tarantula_75_6";		
		String db_75_7 = "CodeTest_Tarantula_75_7";		

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Tarantula_75_1 = mongo.getDB(db_75_1);
			List<Result> resultList_75_1 = dManager.getLocalizationCollection(db_Tarantula_75_1);
			
			DB db_Tarantula_75_2 = mongo.getDB(db_75_2);
			List<Result> resultList_75_2 = dManager.getLocalizationCollection(db_Tarantula_75_2);
	
			DB db_Tarantula_75_3 = mongo.getDB(db_75_3);
			List<Result> resultList_75_3 = dManager.getLocalizationCollection(db_Tarantula_75_3);

			DB db_Tarantula_75_4 = mongo.getDB(db_75_4);
			List<Result> resultList_75_4 = dManager.getLocalizationCollection(db_Tarantula_75_4);
			
			DB db_Tarantula_75_5 = mongo.getDB(db_75_5);
			List<Result> resultList_75_5 = dManager.getLocalizationCollection(db_Tarantula_75_5);

			DB db_Tarantula_75_6 = mongo.getDB(db_75_6);
			List<Result> resultList_75_6 = dManager.getLocalizationCollection(db_Tarantula_75_6);

			DB db_Tarantula_75_7 = mongo.getDB(db_75_7);
			List<Result> resultList_75_7 = dManager.getLocalizationCollection(db_Tarantula_75_7);

			double t1 = wizeFlII.getThreshold(resultList_75_1);
			double t2 = wizeFlII.getThreshold(resultList_75_2);
			double t3 = wizeFlII.getThreshold(resultList_75_3);
			double t4 = wizeFlII.getThreshold(resultList_75_4);
			double t5 = wizeFlII.getThreshold(resultList_75_5);
			double t6 = wizeFlII.getThreshold(resultList_75_6);
			double t7 = wizeFlII.getThreshold(resultList_75_7);

			for(int i=0; i<resultList_75_1.size(); i++){
				Result result1 = resultList_75_1.get(i);
				Result result2 = resultList_75_2.get(i);
				Result result3 = resultList_75_3.get(i);
				Result result4 = resultList_75_4.get(i);
				Result result5 = resultList_75_5.get(i);
				Result result6 = resultList_75_6.get(i);
				Result result7 = resultList_75_7.get(i);

				if(	(dManager.isCovered(db_Tarantula_75_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_75_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Tarantula_75_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 ) ||
						(dManager.isCovered(db_Tarantula_75_4, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t4 ) ||
						(dManager.isCovered(db_Tarantula_75_5, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t5 ) ||
						(dManager.isCovered(db_Tarantula_75_6, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t6 ) ||
						(dManager.isCovered(db_Tarantula_75_7, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t7 )
					) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) +
							new Double(result4.getSuspiciousness()) +
							new Double(result5.getSuspiciousness()) +
							new Double(result6.getSuspiciousness()) +
							new Double(result7.getSuspiciousness())

					) / numberOfTestGroup;
					dManager.updateSuspiciousness(db_Tarantula_75_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_75_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_75_3, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_75_4, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_75_5, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_75_6, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_75_7, id, suspiciousness);


				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_75_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_75_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_75_3.csv";
			String docFile4 = "/home/wizehack/exp/mri/mri_75_4.csv";
			String docFile5 = "/home/wizehack/exp/mri/mri_75_5.csv";
			String docFile6 = "/home/wizehack/exp/mri/mri_75_6.csv";
			String docFile7 = "/home/wizehack/exp/mri/mri_75_7.csv";

			dManager.exportToTextDoc(db_Tarantula_75_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_75_2, docFile2);
			dManager.exportToTextDoc(db_Tarantula_75_3, docFile3);
			dManager.exportToTextDoc(db_Tarantula_75_4, docFile4);
			dManager.exportToTextDoc(db_Tarantula_75_5, docFile5);
			dManager.exportToTextDoc(db_Tarantula_75_6, docFile6);
			dManager.exportToTextDoc(db_Tarantula_75_7, docFile7);



		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
	}

	private static void mriTarantulaFristPhase(String ip, String sourceFolder){
		String pName1_30_1 = "CodeTest_Tarantula_30_1";
		String pName1_30_2 = "CodeTest_Tarantula_30_2";

		String pName1_50_1 = "CodeTest_Tarantula_50_1";
		String pName1_50_2 = "CodeTest_Tarantula_50_2";
		String pName1_50_3 = "CodeTest_Tarantula_50_3";

		String pName1_60_1 = "CodeTest_Tarantula_60_1";
		String pName1_60_2 = "CodeTest_Tarantula_60_2";
		String pName1_60_3 = "CodeTest_Tarantula_60_3";
		String pName1_60_4 = "CodeTest_Tarantula_60_4";
		String pName1_60_5 = "CodeTest_Tarantula_60_5";

		String pName1_75_1 = "CodeTest_Tarantula_75_1";
		String pName1_75_2 = "CodeTest_Tarantula_75_2";
		String pName1_75_3 = "CodeTest_Tarantula_75_3";
		String pName1_75_4 = "CodeTest_Tarantula_75_4";
		String pName1_75_5 = "CodeTest_Tarantula_75_5";
		String pName1_75_6 = "CodeTest_Tarantula_75_6";
		String pName1_75_7 = "CodeTest_Tarantula_75_7";

		
		String expFile1_30_1 = "/home/wizehack/exp/docFile_Tarantula_30_!.csv";
		String expFile1_30_2 = "/home/wizehack/exp/docFile_Tarantula_30_2.csv";

		String expFile1_50_1 = "/home/wizehack/exp/docFile_Tarantula_50_1.csv";
		String expFile1_50_2 = "/home/wizehack/exp/docFile_Tarantula_50_2.csv";
		String expFile1_50_3 = "/home/wizehack/exp/docFile_Tarantula_50_3.csv";

		String expFile1_60_1 = "/home/wizehack/exp/docFile_Tarantula_60_1.csv";
		String expFile1_60_2 = "/home/wizehack/exp/docFile_Tarantula_60_2.csv";
		String expFile1_60_3 = "/home/wizehack/exp/docFile_Tarantula_60_3.csv";
		String expFile1_60_4 = "/home/wizehack/exp/docFile_Tarantula_60_4.csv";
		String expFile1_60_5 = "/home/wizehack/exp/docFile_Tarantula_60_5.csv";

		String expFile1_75_1 = "/home/wizehack/exp/docFile_Tarantula_75_1.csv";
		String expFile1_75_2 = "/home/wizehack/exp/docFile_Tarantula_75_2.csv";
		String expFile1_75_3 = "/home/wizehack/exp/docFile_Tarantula_75_3.csv";
		String expFile1_75_4 = "/home/wizehack/exp/docFile_Tarantula_75_4.csv";
		String expFile1_75_5 = "/home/wizehack/exp/docFile_Tarantula_75_5.csv";
		String expFile1_75_6 = "/home/wizehack/exp/docFile_Tarantula_75_6.csv";
		String expFile1_75_7 = "/home/wizehack/exp/docFile_Tarantula_75_7.csv";

		Crawler4jMRIDebugger demo_tarantula_30_1 = new Crawler4jMRIDebugger(pName1_30_1, ip, sourceFolder, expFile1_30_1);
		Crawler4jMRIDebugger demo_tarantula_30_2 = new Crawler4jMRIDebugger(pName1_30_2, ip, sourceFolder, expFile1_30_2);

		Crawler4jMRIDebugger demo_tarantula_50_1 = new Crawler4jMRIDebugger(pName1_50_1, ip, sourceFolder, expFile1_50_1);
		Crawler4jMRIDebugger demo_tarantula_50_2 = new Crawler4jMRIDebugger(pName1_50_2, ip, sourceFolder, expFile1_50_2);
		Crawler4jMRIDebugger demo_tarantula_50_3 = new Crawler4jMRIDebugger(pName1_50_3, ip, sourceFolder, expFile1_50_3);

		Crawler4jMRIDebugger demo_tarantula_60_1 = new Crawler4jMRIDebugger(pName1_60_1, ip, sourceFolder, expFile1_60_1);
		Crawler4jMRIDebugger demo_tarantula_60_2 = new Crawler4jMRIDebugger(pName1_60_2, ip, sourceFolder, expFile1_60_2);
		Crawler4jMRIDebugger demo_tarantula_60_3 = new Crawler4jMRIDebugger(pName1_60_3, ip, sourceFolder, expFile1_60_3);
		Crawler4jMRIDebugger demo_tarantula_60_4 = new Crawler4jMRIDebugger(pName1_60_4, ip, sourceFolder, expFile1_60_4);
		Crawler4jMRIDebugger demo_tarantula_60_5 = new Crawler4jMRIDebugger(pName1_60_5, ip, sourceFolder, expFile1_60_5);

		Crawler4jMRIDebugger demo_tarantula_75_1 = new Crawler4jMRIDebugger(pName1_75_1, ip, sourceFolder, expFile1_75_1);
		Crawler4jMRIDebugger demo_tarantula_75_2 = new Crawler4jMRIDebugger(pName1_75_2, ip, sourceFolder, expFile1_75_2);
		Crawler4jMRIDebugger demo_tarantula_75_3 = new Crawler4jMRIDebugger(pName1_75_3, ip, sourceFolder, expFile1_75_3);
		Crawler4jMRIDebugger demo_tarantula_75_4 = new Crawler4jMRIDebugger(pName1_75_4, ip, sourceFolder, expFile1_75_4);
		Crawler4jMRIDebugger demo_tarantula_75_5 = new Crawler4jMRIDebugger(pName1_75_5, ip, sourceFolder, expFile1_75_5);
		Crawler4jMRIDebugger demo_tarantula_75_6 = new Crawler4jMRIDebugger(pName1_75_6, ip, sourceFolder, expFile1_75_6);
		Crawler4jMRIDebugger demo_tarantula_75_7 = new Crawler4jMRIDebugger(pName1_75_7, ip, sourceFolder, expFile1_75_7);

		LocalizationTechnique tarantula_30_1 = new Tarantula(28,1,"tarantula");
		LocalizationTechnique tarantula_30_2 = new Tarantula(28,1,"tarantula");

		LocalizationTechnique tarantula_50_1 = new Tarantula(31,1,"tarantula");
		LocalizationTechnique tarantula_50_2 = new Tarantula(31,1,"tarantula");
		LocalizationTechnique tarantula_50_3 = new Tarantula(31,1,"tarantula");

		LocalizationTechnique tarantula_60_1 = new Tarantula(38,1,"tarantula");
		LocalizationTechnique tarantula_60_2 = new Tarantula(38,1,"tarantula");
		LocalizationTechnique tarantula_60_3 = new Tarantula(38,1,"tarantula");
		LocalizationTechnique tarantula_60_4 = new Tarantula(38,1,"tarantula");
		LocalizationTechnique tarantula_60_5 = new Tarantula(38,1,"tarantula");

		LocalizationTechnique tarantula_75_1 = new Tarantula(55,1,"tarantula");
		LocalizationTechnique tarantula_75_2 = new Tarantula(55,1,"tarantula");
		LocalizationTechnique tarantula_75_3 = new Tarantula(55,1,"tarantula");
		LocalizationTechnique tarantula_75_4 = new Tarantula(55,1,"tarantula");
		LocalizationTechnique tarantula_75_5 = new Tarantula(55,1,"tarantula");
		LocalizationTechnique tarantula_75_6 = new Tarantula(55,1,"tarantula");
		LocalizationTechnique tarantula_75_7 = new Tarantula(55,1,"tarantula");

		demo_tarantula_30_1.TYPE = 1;
		demo_tarantula_30_1.debug(tarantula_30_1);
		
		demo_tarantula_30_2.TYPE = 2;
		demo_tarantula_30_2.debug(tarantula_30_2);

		demo_tarantula_50_1.TYPE = 3;
		demo_tarantula_50_1.debug(tarantula_50_1);
		
		demo_tarantula_50_2.TYPE = 4;
		demo_tarantula_50_2.debug(tarantula_50_2);

		demo_tarantula_50_3.TYPE = 5;
		demo_tarantula_50_3.debug(tarantula_50_3);

		demo_tarantula_60_1.TYPE = 6;
		demo_tarantula_60_1.debug(tarantula_60_1);
		
		demo_tarantula_60_2.TYPE = 7;
		demo_tarantula_60_2.debug(tarantula_60_2);

		demo_tarantula_60_3.TYPE = 8;
		demo_tarantula_60_3.debug(tarantula_60_3);

		demo_tarantula_60_4.TYPE = 9;
		demo_tarantula_60_4.debug(tarantula_60_4);

		demo_tarantula_60_5.TYPE = 10;
		demo_tarantula_60_5.debug(tarantula_60_5);

		demo_tarantula_75_1.TYPE = 11;
		demo_tarantula_75_1.debug(tarantula_75_1);
		
		demo_tarantula_75_2.TYPE = 12;
		demo_tarantula_75_2.debug(tarantula_75_2);

		demo_tarantula_75_3.TYPE = 13;
		demo_tarantula_75_3.debug(tarantula_75_3);

		demo_tarantula_75_4.TYPE = 14;
		demo_tarantula_75_4.debug(tarantula_75_4);

		demo_tarantula_75_5.TYPE = 15;
		demo_tarantula_75_5.debug(tarantula_75_5);

		demo_tarantula_75_6.TYPE = 16;
		demo_tarantula_75_6.debug(tarantula_75_6);

		demo_tarantula_75_7.TYPE = 17;
		demo_tarantula_75_7.debug(tarantula_75_7);
		
	}
}
