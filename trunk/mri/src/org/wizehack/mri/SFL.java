package org.wizehack.mri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.code.CodeLoader;
import org.wizehack.mri.code.JavaCodeLoader;
import org.wizehack.mri.fl.Localizer;
import org.wizehack.mri.io.FileIO;
import org.wizehack.mri.repo.Code;
import org.wizehack.mri.repo.DataManager;
import org.wizehack.mri.repo.Prober;
import org.wizehack.mri.repo.ProberManager;
import org.wizehack.mri.repo.Result;
import org.wizehack.mri.repo.Spectrum;
import org.wizehack.mri.repo.SuspiciousnessMap;

import com.mongodb.DBObject;


public class SFL {
	private DataManager dManager;
	
	public DataManager setUpProject(String pName, String ip, String sourceFolder) {
		
		this.dManager = new DataManager(pName, ip);
		CodeLoader cLoader = new JavaCodeLoader();
		List<Code> statements = cLoader.getStatements(sourceFolder);
		
		dManager.insertCode(statements);
		return dManager;
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
	
	public void computeSuspiciousness(Localizer localizer){
		List<Spectrum> spectrumList = dManager.getStatistics();
		List<Code> codeList = dManager.getStatements();
		boolean found = false; 
		
		for(int i=0; i<codeList.size(); i++){
			Code code = codeList.get(i);
			
			for(int j=0; j<spectrumList.size(); j++){
				Spectrum spectrum = spectrumList.get(j);
				if(code.getStatementId() == spectrum.getStatementId()){
					
					localizer.setPassed(spectrum.getPassed());
					localizer.setFailed(spectrum.getFailed());
					double Suspiciousness = localizer.getSuspiciousness();
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
		
		char seperator = '\t';
		String column = "id" +seperator+ "file" +seperator+ "line"+seperator+
				"suspciciousness"+seperator+"statement" ;
		doc.add(column);
		
		for(int i=0; i<results.size(); i++) {
			Result result = results.get(i);
			String id = result.getStatementId();
			String file = result.getFile();
			String line = result.getLine();
			String suspiciousness = result.getSuspiciousness();
			String statement = result.getStatement().replace('\t', '\0');
			
			String fields = id + seperator + file + seperator + line + seperator + 
					suspiciousness + seperator + statement;
			
			doc.add(fields);
		}
		fileIO.write(doc);
	}
}