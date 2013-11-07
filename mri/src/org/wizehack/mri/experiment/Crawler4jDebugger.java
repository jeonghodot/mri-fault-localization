package org.wizehack.mri.experiment;

import java.io.File;
import java.util.List;

import org.wizehack.mri.SFL;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.DStar;
import org.wizehack.mri.fl.HeuristicIII;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.io.FileListReader;

public class Crawler4jDebugger extends SFL{
	
	public int TYPE = 0;

	public Crawler4jDebugger(String projectName, String repositoryIp,
			String sourceFolder, String exportFile) {
		super(projectName, repositoryIp, sourceFolder, exportFile);
	}

	private static void tarantula(String ip, String sourceFolder) {
		String pName1_5 = "CodeTest_Tarantula_5";
		String pName1_10 = "CodeTest_Tarantula_10";
		String pName1_20 = "CodeTest_Tarantula_20";
		String pName1_30 = "CodeTest_Tarantula_30";
		String pName1_60 = "CodeTest_Tarantula_60";
		String pName1_75 = "CodeTest_Tarantula_75";
		
		String expFile1_5 = "/home/wizehack/exp/docFile_Tarantula_5.csv";
		String expFile1_10 = "/home/wizehack/exp/docFile_Tarantula_10.csv";
		String expFile1_20 = "/home/wizehack/exp/docFile_Tarantula_20.csv";
		String expFile1_30 = "/home/wizehack/exp/docFile_Tarantula_30.csv";
		String expFile1_60 = "/home/wizehack/exp/docFile_Tarantula_60.csv";
		String expFile1_75 = "/home/wizehack/exp/docFile_Tarantula_75.csv";

		Crawler4jDebugger demo_tarantula_5 = new Crawler4jDebugger(pName1_5, ip, sourceFolder, expFile1_5);
		Crawler4jDebugger demo_tarantula_10 = new Crawler4jDebugger(pName1_10, ip, sourceFolder, expFile1_10);
		Crawler4jDebugger demo_tarantula_20 = new Crawler4jDebugger(pName1_20, ip, sourceFolder, expFile1_20);
		Crawler4jDebugger demo_tarantula_30 = new Crawler4jDebugger(pName1_30, ip, sourceFolder, expFile1_30);
		Crawler4jDebugger demo_tarantula_60 = new Crawler4jDebugger(pName1_60, ip, sourceFolder, expFile1_60);
		Crawler4jDebugger demo_tarantula_75 = new Crawler4jDebugger(pName1_75, ip, sourceFolder, expFile1_75);
		
		LocalizationTechnique tarantula_5 = new Tarantula(2,1,"tarantula");
		LocalizationTechnique tarantula_10 = new Tarantula(28,1,"tarantula");
		LocalizationTechnique tarantula_20 = new Tarantula(29,2,"tarantula");
		LocalizationTechnique tarantula_30 = new Tarantula(33,3,"tarantula");
		LocalizationTechnique tarantula_60 = new Tarantula(42,5,"tarantula");
		LocalizationTechnique tarantula_75 = new Tarantula(61,6,"tarantula");
		
		demo_tarantula_5.TYPE = 1;
		demo_tarantula_5.debug(tarantula_5);
		
		demo_tarantula_10.TYPE = 2;
		demo_tarantula_10.debug(tarantula_10);

		demo_tarantula_20.TYPE = 3;
		demo_tarantula_20.debug(tarantula_20);
		
		demo_tarantula_30.TYPE = 4;
		demo_tarantula_30.debug(tarantula_30);
		
		demo_tarantula_60.TYPE = 5;
		demo_tarantula_60.debug(tarantula_60);
		
		demo_tarantula_75.TYPE = 6;
		demo_tarantula_75.debug(tarantula_75);
	}
	
	private static void dStar(String ip, String sourceFolder) {
		String pName1_5 = "CodeTest_Dstar_5";
		String pName1_10 = "CodeTest_Dstar_10";
		String pName1_20 = "CodeTest_Dstar_20";
		String pName1_30 = "CodeTest_Dstar_30";
		String pName1_60 = "CodeTest_Dstar_60";
		String pName1_75 = "CodeTest_Dstar_75";
		
		String expFile1_5 = "/home/wizehack/exp/docFile_Dstar_5.csv";
		String expFile1_10 = "/home/wizehack/exp/docFile_Dstar_10.csv";
		String expFile1_20 = "/home/wizehack/exp/docFile_Dstar_20.csv";
		String expFile1_30 = "/home/wizehack/exp/docFile_Dstar_30.csv";
		String expFile1_60 = "/home/wizehack/exp/docFile_Dstar_60.csv";
		String expFile1_75 = "/home/wizehack/exp/docFile_Dstar_75.csv";

		Crawler4jDebugger demo_Dstar_5 = new Crawler4jDebugger(pName1_5, ip, sourceFolder, expFile1_5);
		Crawler4jDebugger demo_Dstar_10 = new Crawler4jDebugger(pName1_10, ip, sourceFolder, expFile1_10);
		Crawler4jDebugger demo_Dstar_20 = new Crawler4jDebugger(pName1_20, ip, sourceFolder, expFile1_20);
		Crawler4jDebugger demo_Dstar_30 = new Crawler4jDebugger(pName1_30, ip, sourceFolder, expFile1_30);
		Crawler4jDebugger demo_Dstar_60 = new Crawler4jDebugger(pName1_60, ip, sourceFolder, expFile1_60);
		Crawler4jDebugger demo_Dstar_75 = new Crawler4jDebugger(pName1_75, ip, sourceFolder, expFile1_75);
		
		LocalizationTechnique Dstar_5 = new DStar(2,1,"Dstar");
		LocalizationTechnique Dstar_10 = new DStar(28,1,"Dstar");
		LocalizationTechnique Dstar_20 = new DStar(29,2,"Dstar");
		LocalizationTechnique Dstar_30 = new DStar(33,3,"Dstar");
		LocalizationTechnique Dstar_60 = new DStar(42,4,"Dstar");
		LocalizationTechnique Dstar_75 = new DStar(61,6,"Dstar");
		
		demo_Dstar_5.TYPE = 1;
		demo_Dstar_5.debug(Dstar_5);
		
		demo_Dstar_10.TYPE = 2;
		demo_Dstar_10.debug(Dstar_10);

		demo_Dstar_20.TYPE = 3;
		demo_Dstar_20.debug(Dstar_20);
		
		demo_Dstar_30.TYPE = 4;
		demo_Dstar_30.debug(Dstar_30);
		
		demo_Dstar_60.TYPE = 5;
		demo_Dstar_60.debug(Dstar_60);
		
		demo_Dstar_75.TYPE = 6;
		demo_Dstar_75.debug(Dstar_75);
	}
	
	private static void heuristicIII(String ip, String sourceFolder) {
		String pName1_5 = "CodeTest_HeuristicIII_5";
		String pName1_10 = "CodeTest_HeuristicIII_10";
		String pName1_20 = "CodeTest_HeuristicIII_20";
		String pName1_30 = "CodeTest_HeuristicIII_30";
		String pName1_60 = "CodeTest_HeuristicIII_60";
		String pName1_75 = "CodeTest_HeuristicIII_75";
		
		String expFile1_5 = "/home/wizehack/exp/docFile_HeuristicIII_5.csv";
		String expFile1_10 = "/home/wizehack/exp/docFile_HeuristicIII_10.csv";
		String expFile1_20 = "/home/wizehack/exp/docFile_HeuristicIII_20.csv";
		String expFile1_30 = "/home/wizehack/exp/docFile_HeuristicIII_30.csv";
		String expFile1_60 = "/home/wizehack/exp/docFile_HeuristicIII_60.csv";
		String expFile1_75 = "/home/wizehack/exp/docFile_HeuristicIII_75.csv";

		Crawler4jDebugger demo_HeuristicIII_5 = new Crawler4jDebugger(pName1_5, ip, sourceFolder, expFile1_5);
		Crawler4jDebugger demo_HeuristicIII_10 = new Crawler4jDebugger(pName1_10, ip, sourceFolder, expFile1_10);
		Crawler4jDebugger demo_HeuristicIII_20 = new Crawler4jDebugger(pName1_20, ip, sourceFolder, expFile1_20);
		Crawler4jDebugger demo_HeuristicIII_30 = new Crawler4jDebugger(pName1_30, ip, sourceFolder, expFile1_30);
		Crawler4jDebugger demo_HeuristicIII_60 = new Crawler4jDebugger(pName1_60, ip, sourceFolder, expFile1_60);
		Crawler4jDebugger demo_HeuristicIII_75 = new Crawler4jDebugger(pName1_75, ip, sourceFolder, expFile1_75);
		
		LocalizationTechnique HeuristicIII_5 = new HeuristicIII(2,1,"HeuristicIII");
		LocalizationTechnique HeuristicIII_10 = new HeuristicIII(28,1,"HeuristicIII");
		LocalizationTechnique HeuristicIII_20 = new HeuristicIII(29,2,"HeuristicIII");
		LocalizationTechnique HeuristicIII_30 = new HeuristicIII(33,3,"HeuristicIII");
		LocalizationTechnique HeuristicIII_60 = new HeuristicIII(42,4,"HeuristicIII");
		LocalizationTechnique HeuristicIII_75 = new HeuristicIII(61,6,"HeuristicIII");
		
		demo_HeuristicIII_5.TYPE = 1;
		demo_HeuristicIII_5.debug(HeuristicIII_5);
		
		demo_HeuristicIII_10.TYPE = 2;
		demo_HeuristicIII_10.debug(HeuristicIII_10);

		demo_HeuristicIII_20.TYPE = 3;
		demo_HeuristicIII_20.debug(HeuristicIII_20);
		
		demo_HeuristicIII_30.TYPE = 4;
		demo_HeuristicIII_30.debug(HeuristicIII_30);
		
		demo_HeuristicIII_60.TYPE = 5;
		demo_HeuristicIII_60.debug(HeuristicIII_60);
		
		demo_HeuristicIII_75.TYPE = 6;
		demo_HeuristicIII_75.debug(HeuristicIII_75);
	}
	
	
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		String projectPath = "/home/wizehack/develop/workspace/crawler4j";
		
		tarantula(ip, projectPath);

		dStar(ip, projectPath);
		
		heuristicIII(ip, projectPath);
	}

	@Override
	public TestSuite setTestResult() {
		//define test
		float id = 1;
		TestSuite testSuite = new Group();
		testSuite.setId(id);
		
		String rootDir = "/home/wizehack/exp/target/Crawler4j/";
		String covDir = null;

		//target
		if(this.TYPE == 1){
			covDir = rootDir + "5";
		} else if(this.TYPE == 2) {
			covDir = rootDir + "10";
		} else if(this.TYPE == 3) {
			covDir = rootDir + "20";
		} else if(this.TYPE == 4) {
			covDir = rootDir + "50";
		} else if(this.TYPE == 5) {
			covDir = rootDir + "60";
		} else if (this.TYPE == 6) {
			covDir = rootDir + "75";
		} 
		
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType("xml");
		
		List<String> fList = fileListReader.readFiles(new File(covDir));

		for(int i=0; i<fList.size(); i++){

			String name = fList.get(i);
			name = super.getCoverageFileName(name);
			
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

}
