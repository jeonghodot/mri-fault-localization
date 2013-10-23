package org.wizehack.mri.repoTest;

import java.io.File;
import java.util.List;

import org.wizehack.mri.SFL;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.io.FileListReader;
import org.wizehack.mri.repo.DataManager;

public class DataManagerTest {

	public static void main(String[] args) {
		SFL sfl = new SFL();

		String pName = "CodeTest";
		String ip = "127.0.0.1";
		String sourceFolder = "/media/volume/workspace/jGraphX/src";

		//insert code to repository 
		System.out.println("insert code to repository ");
		DataManager dManager = sfl.setUpProject(pName, ip, sourceFolder);

		//define test
		float id = 1;
		TestSuite testSuite = new Group();
		testSuite.setId(id);
		
		String covDir = "/media/volume/workspace/jGraphX/Coverage";
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType("xml");
		List<String> fList = fileListReader.readFiles(new File(covDir));

		for(int i=0; i<fList.size(); i++){
			int tcId = i+1;
			TestCase testCase = new TestCase();
			testCase.setTcId(tcId);
			
			if(tcId == 1 || tcId == 2 || tcId == 3){
				testCase.setPassed(false);
			} else {
				testCase.setPassed(true);
			}
			
			testCase.setCoverageFile(fList.get(i));
			testSuite.add(testCase);
			
		}
		
		//obtain test result
		System.out.println("obtain test result");
		sfl.probe(sourceFolder, testSuite);

//		dManager.viewDocuments("test");		
		
		//map reduce
		dManager.reduceCoverageMap();
		
		//compute suspiciousness
		sfl.computeSuspiciousness(new Tarantula(3, 3, "Tarantula"));
		
		//documentation
		String expFile = "/home/wizehack/exp/docFile.csv";
		sfl.exportToTextDoc(expFile);
		System.out.println("completed ");

	}
}
