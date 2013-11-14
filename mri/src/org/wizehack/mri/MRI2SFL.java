package org.wizehack.mri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.CoverageDataParser.CoverageFileReader;
import org.wizehack.mri.CoverageDataParser.SimEvaluator;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestCaseWeight;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.CoefficientMetric;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.MRI_II;
import org.wizehack.mri.io.FileListReader;
import org.wizehack.mri.repo.Code;
import org.wizehack.mri.repo.DataManager;
import org.wizehack.mri.repo.Spectrum;
import org.wizehack.mri.repo.SuspiciousnessMap;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;

public abstract class MRI2SFL extends SFL {
	private DataManager dManager;
	private String pName;
	private String ip;
	private String sourceFolder;
	private String exportFile;
	
	public MRI2SFL(String projectName, String repositoryIp,
			String sourceFolder, String exportFile) {
		super(projectName, repositoryIp, sourceFolder, exportFile);
		this.pName = projectName;
		this.ip = repositoryIp;
		this.sourceFolder = sourceFolder;
		this.exportFile = exportFile;

	}

	@Override
	public abstract TestSuite setTestResult();
	
	public void debug(LocalizationTechnique technique){
		throw new UnsupportedOperationException();		
	}
	
	private String coverageFileDir;
	public void debug(int failedTCId){
		//insert code to repository 
		dManager = this.setUpProject();
		TestSuite testSuite = this.setTestResult();
		
		//obtain test result
		System.out.print("Obtaining test result........");
		this.probe(sourceFolder, testSuite);
		System.out.println("ok");
		
		//similarity analysis
		System.out.print("similarity analysis.........");
		SimEvaluator sim = new SimEvaluator();

		List<Double> failedStatementIdList = sim.getStatementIdList(pName, failedTCId);
		List<Integer> passedTCIdList = sim.getPassedTCIdList(coverageFileDir, failedTCId);
		
		List<TestCaseWeight> weightList = sim.computeWeight(pName, failedStatementIdList, passedTCIdList);
		
		double totalPassed = 0;
		for(int i=0; i<weightList.size(); i++){
			totalPassed = totalPassed + weightList.get(i).getWeight();
		}
//		System.out.println("totalPassed: " + totalPassed);
		//update coverage collection 
		dManager.updateWeight(weightList);
		System.out.println("ok");		
		
		//map reduce
		Mongo mongo = null;
		DBCollection spectrums = null;
		List<Spectrum> spectrumList = new ArrayList<Spectrum>();
		try {
			mongo = dManager.getConnection();
			DB db = mongo.getDB(pName);
			spectrums = this.reduceCoverageMap(db, weightList);
//			System.out.println("spectrumList =============");
			DBCursor cur = spectrums.find();
			while(cur.hasNext()){
				DBObject obj = (DBObject) cur.next();
				double statementId = (double) obj.get("_id");
				DBObject value = (DBObject) obj.get("value");
				double passed;
				double failed;

				try {
					passed = (double) value.get("passed");

				} catch (NullPointerException ne1){
					try{
						passed = (double) value.get("weight");
					} catch (NullPointerException ne2){
						passed = 0;
					}
					
				}
				
				try {
					failed = (double) value.get("failed");
				} catch (NullPointerException ne3) {
					failed = 0;
				}
				
				System.out.println(statementId + ": " + passed + " : " + failed);

				Spectrum spectrum = new Spectrum(statementId, passed, failed);
				spectrumList.add(spectrum);

			}
			
			
		} finally {
			dManager.closeConnection(mongo);
		}
		
		
		//compute suspiciousness
		this.computeSuspiciousness(spectrumList, totalPassed);
		
		//documentation
		
		this.exportToTextDoc(this.exportFile);
		System.out.println("completed ");
		System.out.println("========================= ");
	}
	
	public void setCoverageFile(TestSuite testSuite, String covDir) {
		this.coverageFileDir = covDir;
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

			if(tcId == 6){
				testCase.setPassed(false);
			} else {
				testCase.setPassed(true);
			}
			
			testCase.setCoverageFile(fList.get(i));
			testSuite.add(testCase);
		}
	}
	
	private DBCollection reduceCoverageMap(DB db, List<TestCaseWeight> weightList) {
		
		String map = "function() {"
				+ "var key = this.statementId;"
				+ "var value = {"
					+ "file: this.file,"
					+ "line_number: this.lineNumber,"
					+ "isPassed: this.isPassed,"
					+ "weight: this.weight"
					+ "};"
					+ "emit( key, value );"
				+ "};";
	
		String reduce ="function(key, values) {"
				+ "var reducedObject = {"
					+ "file: null,"
						+ "line_number: 0,"
						+ "passed: 0, "
						+ "failed: 0"
				+ "};"
				+ "values.forEach( function(value) {"
					+ "reducedObject.file = value.file,"
					+ "reducedObject.line_number = value.line_number;"
					+ "if(value.isPassed == true){"
						+ "reducedObject.passed = reducedObject.passed + value.weight;"
					+ "} else {"
						+ "reducedObject.failed = reducedObject.failed + 1;"
						+ "}"
					+ "}"
				+ ");"
				+ "return reducedObject;"
			+ "};";
	
		String outCollection = "mri_II_statistics";
		
		Mongo mongo = null;
		DBCollection collection = null;
		
		try {
			DBCollection coll = db.getCollection("coverage");
			
			MapReduceOutput output = coll.mapReduce(map,reduce,outCollection,MapReduceCommand.OutputType.REDUCE,null);
			output.getOutputCollection(); 
			
			collection = output.getOutputCollection(); 
			
		} finally {
			dManager.closeConnection(mongo);
		}
		
		return collection;
	}
	
	public void computeSuspiciousness(List<Spectrum> spectrumList, double totalPassed){
		List<Code> codeList = dManager.getStatements();
		boolean found = false; 
		
		
//		System.out.println("spectrumList =============");

//		for(int i=0; i<spectrumList.size(); i++){
//			System.out.println(spectrumList.get(i).getStatementId() + " : " + spectrumList.get(i).getPassed());
//
//		}
		System.out.println("Inserting suspiciousness");


		for(int i=0; i<codeList.size(); i++){
			Code code = codeList.get(i);
			
			for(int j=0; j<spectrumList.size(); j++){
				Spectrum spectrum = spectrumList.get(j);
				if(code.getStatementId() == spectrum.getStatementId()){
					double executedPassed = spectrum.getPassed();
					double notExecutedPassed = totalPassed - executedPassed;
					double executedFailed = spectrum.getFailed();
					double notExecutedFailed = 1-spectrum.getFailed();
					
					System.out.println(spectrum.getStatementId() + " - " 
							+ executedPassed + " - " + notExecutedPassed + " - " + executedFailed + " - " + notExecutedFailed);
					MRI_II technique = new MRI_II(executedPassed, notExecutedPassed, executedFailed, notExecutedFailed);
					double suspiciousness = technique.computeSuspiciousness();
					SuspiciousnessMap suspiciousnessMap = new SuspiciousnessMap();
					
					suspiciousnessMap.setStatementId(spectrum.getStatementId());
					suspiciousnessMap.setSuspiciousness(suspiciousness);
					System.out.println(spectrum.getStatementId() + " : " + spectrum.getPassed() + " : " + totalPassed);
					dManager.insertSuspiciousness(code, suspiciousnessMap);
					found = true;
					break;
				}
			}
			
			if(found == false){
				SuspiciousnessMap suspiciousnessMap = new SuspiciousnessMap();
				suspiciousnessMap.setStatementId(code.getStatementId());
				suspiciousnessMap.setSuspiciousness(0);
				dManager.insertSuspiciousness(code, suspiciousnessMap);
			}
		} //for i
	}
}
