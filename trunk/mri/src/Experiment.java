

import java.io.File;
import java.util.List;

import org.wizehack.mri.SFL;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.DStar;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.io.FileListReader;

public class Experiment extends SFL{

	public int TYPE = 0;
	
	public Experiment(String projectName, String repositoryIp,
			String sourceFolder, String exportFile) {
		super(projectName, repositoryIp, sourceFolder, exportFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TestSuite setTestResult() {
		//define test
		float id = 1;
		TestSuite testSuite = new Group();
		testSuite.setId(id);
		
		String covDir = null;
		
		//target
		if(this.TYPE == 1){
			covDir = "/home/wizehack/exp/target/30";
		} else if(this.TYPE == 2) {
			covDir = "/home/wizehack/exp/target/35";
		} else if(this.TYPE == 3) {
			covDir = "/home/wizehack/exp/target/40";
		} else if(this.TYPE == 4) {
			covDir = "/home/wizehack/exp/target/55";
		} else if(this.TYPE == 5) {
			covDir = "/home/wizehack/exp/target/30";
		} else if (this.TYPE == 6) {
			covDir = "/home/wizehack/exp/target/35";
		} else if (this.TYPE == 7) {
			covDir = "/home/wizehack/exp/target/40";
		} else if (this.TYPE == 8) {
			covDir = "/home/wizehack/exp/target/55";
		} //mri 
		
		else if (this.TYPE == 9) {
			covDir = "/home/wizehack/exp/mri/30";
		} else if (this.TYPE == 10) { 
			covDir = "/home/wizehack/exp/mri/35/1";
		} else if (this.TYPE == 11) {
			covDir = "/home/wizehack/exp/mri/35/2";
		} else if (this.TYPE == 12) {
			covDir = "/home/wizehack/exp/mri/35/3";
		} else if (this.TYPE == 13) {
			covDir = "/home/wizehack/exp/mri/40/1";
		} else if (this.TYPE == 14) {
			covDir = "/home/wizehack/exp/mri/40/2";

		} else if (this.TYPE == 15) {
			covDir = "/home/wizehack/exp/mri/40/3";

		} else if (this.TYPE == 16) {
			covDir = "/home/wizehack/exp/mri/40/4";

		} else if (this.TYPE == 17) {
			covDir = "/home/wizehack/exp/mri/40/5";

		} else if (this.TYPE == 18) {
			covDir = "/home/wizehack/exp/mri/40/6";

		} else if (this.TYPE == 19) {
			covDir = "/home/wizehack/exp/mri/40/7";

		} else if (this.TYPE == 20) {
			covDir = "/home/wizehack/exp/mri/55/1";

		} else if (this.TYPE == 21) {
			covDir = "/home/wizehack/exp/mri/55/2";

		} else if (this.TYPE == 22) {
			covDir = "/home/wizehack/exp/mri/55/3";

		} else if (this.TYPE == 23) {
			covDir = "/home/wizehack/exp/mri/55/4";

		} else if (this.TYPE == 24) {
			covDir = "/home/wizehack/exp/mri/55/5";

		} else if (this.TYPE == 25) {
			covDir = "/home/wizehack/exp/mri/55/6";

		} else if (this.TYPE == 26) {
			covDir = "/home/wizehack/exp/mri/55/7";

		} else {
			covDir = "/home/wizehack/exp/mri/55/8";

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

			if(tcId == 1 || tcId == 2 || tcId == 3){
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
		String sourceFolder = "/home/wizehack/develop/workspace/jGraphX/src";

		//TARGET
//		tarantula(ip, sourceFolder);

		dStar(ip, sourceFolder);
		
		//MRI METHODOLOGY
		mriTarantula(ip, sourceFolder);
	}

	private static void mriTarantula(String ip, String sourceFolder) {
		String pName3_30 = "CodeTest_Tarantula_30";
		String pName3_35_1 = "CodeTest_Tarantula_35_1";
		String pName3_35_2 = "CodeTest_Tarantula_35_2";
		String pName3_35_3 = "CodeTest_Tarantula_35_3";

		String pName3_40_1 = "CodeTest_Tarantula_40_1";
		String pName3_40_2 = "CodeTest_Tarantula_40_2";
		String pName3_40_3 = "CodeTest_Tarantula_40_3";
		String pName3_40_4 = "CodeTest_Tarantula_40_4";
		String pName3_40_5 = "CodeTest_Tarantula_40_5";
		String pName3_40_6 = "CodeTest_Tarantula_40_6";
		String pName3_40_7 = "CodeTest_Tarantula_40_7";

		String pName3_55_1 = "CodeTest_Tarantula_55_1";
		String pName3_55_2 = "CodeTest_Tarantula_55_2";
		String pName3_55_3 = "CodeTest_Tarantula_55_3";
		String pName3_55_4 = "CodeTest_Tarantula_55_4";
		String pName3_55_5 = "CodeTest_Tarantula_55_5";
		String pName3_55_6 = "CodeTest_Tarantula_55_6";
		String pName3_55_7 = "CodeTest_Tarantula_55_7";
		String pName3_55_8 = "CodeTest_Tarantula_55_8";

		String expFile3_30 = "/home/wizehack/exp/docFile_mri_30.csv";
		String expFile3_35_1 = "/home/wizehack/exp/docFile_mri_35_1.csv";
		String expFile3_35_2 = "/home/wizehack/exp/docFile_mri_35_2.csv";
		String expFile3_35_3 = "/home/wizehack/exp/docFile_mri_35_3.csv";

		String expFile3_40_1 = "/home/wizehack/exp/docFile_mri_40_1.csv";
		String expFile3_40_2 = "/home/wizehack/exp/docFile_mri_40_2.csv";
		String expFile3_40_3 = "/home/wizehack/exp/docFile_mri_40_3.csv";
		String expFile3_40_4 = "/home/wizehack/exp/docFile_mri_40_4.csv";
		String expFile3_40_5 = "/home/wizehack/exp/docFile_mri_40_5.csv";
		String expFile3_40_6 = "/home/wizehack/exp/docFile_mri_40_6.csv";
		String expFile3_40_7 = "/home/wizehack/exp/docFile_mri_40_7.csv";

		String expFile3_55_1 = "/home/wizehack/exp/docFile_mri_55_1.csv";
		String expFile3_55_2 = "/home/wizehack/exp/docFile_mri_55_2.csv";
		String expFile3_55_3 = "/home/wizehack/exp/docFile_mri_55_3.csv";
		String expFile3_55_4 = "/home/wizehack/exp/docFile_mri_55_4.csv";
		String expFile3_55_5 = "/home/wizehack/exp/docFile_mri_55_5.csv";
		String expFile3_55_6 = "/home/wizehack/exp/docFile_mri_55_6.csv";
		String expFile3_55_7 = "/home/wizehack/exp/docFile_mri_55_7.csv";
		String expFile3_55_8 = "/home/wizehack/exp/docFile_mri_55_8.csv";

		Experiment demo_mri_30 = new Experiment(pName3_30, ip, sourceFolder, expFile3_30);
		Experiment demo_mri_35_1 = new Experiment(pName3_35_1, ip, sourceFolder, expFile3_35_1);
		Experiment demo_mri_35_2 = new Experiment(pName3_35_2, ip, sourceFolder, expFile3_35_2);
		Experiment demo_mri_35_3 = new Experiment(pName3_35_3, ip, sourceFolder, expFile3_35_3);

		Experiment demo_mri_40_1 = new Experiment(pName3_40_1, ip, sourceFolder, expFile3_40_1);
		Experiment demo_mri_40_2 = new Experiment(pName3_40_2, ip, sourceFolder, expFile3_40_2);
		Experiment demo_mri_40_3 = new Experiment(pName3_40_3, ip, sourceFolder, expFile3_40_3);
		Experiment demo_mri_40_4 = new Experiment(pName3_40_4, ip, sourceFolder, expFile3_40_4);
		Experiment demo_mri_40_5 = new Experiment(pName3_40_5, ip, sourceFolder, expFile3_40_5);
		Experiment demo_mri_40_6 = new Experiment(pName3_40_6, ip, sourceFolder, expFile3_40_6);
		Experiment demo_mri_40_7 = new Experiment(pName3_40_7, ip, sourceFolder, expFile3_40_7);

		Experiment demo_mri_55_1 = new Experiment(pName3_55_1, ip, sourceFolder, expFile3_55_1);
		Experiment demo_mri_55_2 = new Experiment(pName3_55_2, ip, sourceFolder, expFile3_55_2);
		Experiment demo_mri_55_3 = new Experiment(pName3_55_3, ip, sourceFolder, expFile3_55_3);
		Experiment demo_mri_55_4 = new Experiment(pName3_55_4, ip, sourceFolder, expFile3_55_4);
		Experiment demo_mri_55_5 = new Experiment(pName3_55_5, ip, sourceFolder, expFile3_55_5);
		Experiment demo_mri_55_6 = new Experiment(pName3_55_6, ip, sourceFolder, expFile3_55_6);
		Experiment demo_mri_55_7 = new Experiment(pName3_55_7, ip, sourceFolder, expFile3_55_7);
		Experiment demo_mri_55_8 = new Experiment(pName3_55_8, ip, sourceFolder, expFile3_55_8);

		LocalizationTechnique mri_tarantula = new Tarantula(3,3,"mri_tarantula");
		demo_mri_30.TYPE = 9;
		demo_mri_30.debug(mri_tarantula);
		
		demo_mri_35_1.TYPE = 10;
		demo_mri_35_1.debug(mri_tarantula);
		
		demo_mri_35_2.TYPE = 11;
		demo_mri_35_2.debug(mri_tarantula);
		
		demo_mri_35_3.TYPE = 12;
		demo_mri_35_3.debug(mri_tarantula);
		
		demo_mri_40_1.TYPE = 13;
		demo_mri_40_1.debug(mri_tarantula);
		
		demo_mri_40_2.TYPE = 14;
		demo_mri_40_2.debug(mri_tarantula);
		
		demo_mri_40_3.TYPE = 15;
		demo_mri_40_3.debug(mri_tarantula);
		
		demo_mri_40_4.TYPE = 16;
		demo_mri_40_4.debug(mri_tarantula);
		
		demo_mri_40_5.TYPE = 17;
		demo_mri_40_5.debug(mri_tarantula);
		
		demo_mri_40_6.TYPE = 18;
		demo_mri_40_6.debug(mri_tarantula);
		
		demo_mri_40_7.TYPE = 19;
		demo_mri_40_7.debug(mri_tarantula);
		
		demo_mri_55_1.TYPE = 20;
		demo_mri_55_1.debug(mri_tarantula);
		
		demo_mri_55_2.TYPE = 21;
		demo_mri_55_2.debug(mri_tarantula);
		
		demo_mri_55_3.TYPE = 22;
		demo_mri_55_3.debug(mri_tarantula);
		
		demo_mri_55_4.TYPE = 23;
		demo_mri_55_4.debug(mri_tarantula);
		
		demo_mri_55_5.TYPE = 24;
		demo_mri_55_5.debug(mri_tarantula);
		
		demo_mri_55_6.TYPE = 25;
		demo_mri_55_6.debug(mri_tarantula);
		
		demo_mri_55_7.TYPE = 26;
		demo_mri_55_7.debug(mri_tarantula);
		
		demo_mri_55_8.TYPE = 27;
		demo_mri_55_8.debug(mri_tarantula);
	}

	private static void dStar(String ip, String sourceFolder) {
		String pName2_30 = "CodeTest_Dstar_30";
		String pName2_35 = "CodeTest_Dstar_35";
		String pName2_40 = "CodeTest_Dstar_40";
		String pName2_55 = "CodeTest_Dstar_55";
		
		String expFile2_30 = "/home/wizehack/exp/docFile_Dstar_30.csv";
		String expFile2_35 = "/home/wizehack/exp/docFile_Dstar_35.csv";
		String expFile2_40 = "/home/wizehack/exp/docFile_Dstar_40.csv";
		String expFile2_55 = "/home/wizehack/exp/docFile_Dstar_55.csv";

		Experiment demo_Dstar_30 = new Experiment(pName2_30, ip, sourceFolder, expFile2_30);
		Experiment demo_Dstar_35 = new Experiment(pName2_35, ip, sourceFolder, expFile2_35);
		Experiment demo_Dstar_40 = new Experiment(pName2_40, ip, sourceFolder, expFile2_40);
		Experiment demo_Dstar_55 = new Experiment(pName2_55, ip, sourceFolder, expFile2_55);
		
		LocalizationTechnique dStar = new DStar(3,3,"dstar");
		
		demo_Dstar_30.TYPE = 5;
		demo_Dstar_30.debug(dStar);
		
		demo_Dstar_30.TYPE = 6;
		demo_Dstar_35.debug(dStar);
		
		demo_Dstar_30.TYPE = 7;
		demo_Dstar_40.debug(dStar);
		
		demo_Dstar_30.TYPE = 8;
		demo_Dstar_55.debug(dStar);
	}

	private static void tarantula(String ip, String sourceFolder) {
		String pName1_30 = "CodeTest_Tarantula_30";
		String pName1_35 = "CodeTest_Tarantula_35";
		String pName1_40 = "CodeTest_Tarantula_40";
		String pName1_55 = "CodeTest_Tarantula_55";

		String expFile1_30 = "/home/wizehack/exp/docFile_Tarantula_30.csv";
		String expFile1_35 = "/home/wizehack/exp/docFile_Tarantula_35.csv";
		String expFile1_40 = "/home/wizehack/exp/docFile_Tarantula_40.csv";
		String expFile1_55 = "/home/wizehack/exp/docFile_Tarantula_55.csv";

		Experiment demo_tarantula_30 = new Experiment(pName1_30, ip, sourceFolder, expFile1_30);
		Experiment demo_tarantula_35 = new Experiment(pName1_35, ip, sourceFolder, expFile1_35);
		Experiment demo_tarantula_40 = new Experiment(pName1_40, ip, sourceFolder, expFile1_40);
		Experiment demo_tarantula_55 = new Experiment(pName1_55, ip, sourceFolder, expFile1_55);
		
		LocalizationTechnique tarantula = new Tarantula(3,3,"tarantula");
		
		demo_tarantula_30.TYPE = 1;
		demo_tarantula_30.debug(tarantula);
		
		demo_tarantula_30.TYPE = 2;
		demo_tarantula_35.debug(tarantula);

		demo_tarantula_30.TYPE = 3;
		demo_tarantula_40.debug(tarantula);
		
		demo_tarantula_30.TYPE = 4;
		demo_tarantula_55.debug(tarantula);
	}



}
