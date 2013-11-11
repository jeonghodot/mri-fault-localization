package org.wizehack.mri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.code.CodeLoader;
import org.wizehack.mri.code.JavaCodeLoader;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.io.FileIO;
import org.wizehack.mri.repo.Code;
import org.wizehack.mri.repo.DataManager;
import org.wizehack.mri.repo.Prober;
import org.wizehack.mri.repo.ProberManager;
import org.wizehack.mri.repo.Result;
import org.wizehack.mri.repo.Spectrum;
import org.wizehack.mri.repo.SuspiciousnessMap;


public abstract class SFL {
	private DataManager dManager;
	private String pName;
	private String ip;
	private String sourceFolder;
	private String exportFile;

	public abstract TestSuite setTestResult();
	
	public SFL(String projectName, String repositoryIp, String sourceFolder, String exportFile) {
		this.pName = projectName;
		this.ip = repositoryIp;
		this.sourceFolder = sourceFolder;
		this.exportFile = exportFile;
	}
	
	public DataManager setUpProject() {
		System.out.println("Setting up porject...");
		System.out.println("Project Name: " + this.pName);
		System.out.println("Repository IP: " + this.ip);
		System.out.println("Source Folder: " + this.sourceFolder);
		System.out.println("Report File: " + this.exportFile);
		
		if(this.pName == null || this.ip == null || this.sourceFolder == null || this.exportFile == null){
			try {
				throw new Exception("Invalid Parameters: projectName, repositoryIP, sourceFolder");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.dManager = new DataManager(this.pName, this.ip);
		CodeLoader cLoader = new JavaCodeLoader();
		List<Code> statements = cLoader.getStatements(this.sourceFolder);
		
		System.out.println("Inserting code to repository ");
		dManager.insertCode(statements);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed ");
		return dManager;
	}
	
	public void debug(LocalizationTechnique technique){
		//insert code to repository 
		DataManager dManager = this.setUpProject();
		TestSuite testSuite = this.setTestResult();
		
		//obtain test result
		System.out.println("Obtaining test result");
		this.probe(sourceFolder, testSuite);

//		dManager.viewDocuments("test");		
		
		//map reduce
		dManager.reduceCoverageMap();
		
		//compute suspiciousness
		this.computeSuspiciousness(technique);
		
		//documentation
		
		this.exportToTextDoc(this.exportFile);
		System.out.println("completed ");
		System.out.println("========================= ");
	}
	
	public void probe(String sourceFolder, TestSuite testSuite) {
		ProberManager pm = new ProberManager(testSuite.getChildren().size());
		System.out.println("The number of prober is " + pm.getNumberOfProbers());
		ExecutorService threadPool = pm.getThreadPool(); 
		for(int i=0; i<testSuite.getChildren().size(); i++){
			TestCase testCase = (TestCase)testSuite.getChildren().get(i);
			Thread thread = new Thread(new Prober(sourceFolder, this.dManager, testCase));
			threadPool.execute(thread);
		}
		pm.destroy();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("All probers are terminated");
	}
	
	public void computeSuspiciousness(LocalizationTechnique technique){
		List<Spectrum> spectrumList = dManager.getStatistics();
		List<Code> codeList = dManager.getStatements();
		boolean found = false; 
		System.out.println("Inserting suspiciousness");
		for(int i=0; i<codeList.size(); i++){
			Code code = codeList.get(i);
			
			for(int j=0; j<spectrumList.size(); j++){
				Spectrum spectrum = spectrumList.get(j);
				if(code.getStatementId() == spectrum.getStatementId()){
					
					technique.setPassed(spectrum.getPassed());
					technique.setFailed(spectrum.getFailed());
					double Suspiciousness = technique.getSuspiciousness();
					SuspiciousnessMap suspiciousnessMap = new SuspiciousnessMap();
					
					suspiciousnessMap.setStatementId(spectrum.getStatementId());
					suspiciousnessMap.setSuspiciousness(Suspiciousness);
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

	public void exportToTextDoc(String expFile) {
		List<Result> results = dManager.getLocalizationCollection();
		List<String> doc = new ArrayList<String>();
		FileIO fileIO = new FileIO();
		fileIO.setOutFile(expFile);
		
		char seperator = ';';
		String column = "id" +seperator+ "file" +seperator+ "line"+seperator+
				"suspciciousness"+seperator+"statement";
		doc.add(column);
		
		for(int i=0; i<results.size(); i++) {
			Result result = results.get(i);
			String id = result.getStatementId();
			String file = result.getFile();
			String line = result.getLine();
			String suspiciousness = result.getSuspiciousness();
			String statement = result.getStatement();
//			String statement = result.getStatement().replace("\t", "");

			String fields = id + seperator + file + seperator + line + seperator + 
					suspiciousness + seperator + statement;
//			System.out.println(fields);
			doc.add(fields);
		}
		fileIO.write(doc);
	}


	public String getCoverageFileName(String filePath) {
		int start = filePath.lastIndexOf("/");
		start = start+1;
		int end = filePath.lastIndexOf(".xml");

		String name = filePath.substring(start, end);
		return name;
	}
}