

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

		} else if (this.TYPE == 27){
			covDir = "/home/wizehack/exp/mri/55/8";
		} 		
		
		// heuristic III
		else if(this.TYPE == 28) {
			covDir = "/home/wizehack/exp/target/30";
		} else if (this.TYPE == 29) {
			covDir = "/home/wizehack/exp/target/35";
		} else if (this.TYPE == 30) {
			covDir = "/home/wizehack/exp/target/40";
		} else if (this.TYPE == 31) {
			covDir = "/home/wizehack/exp/target/55";
		}	else {
			System.out.println("error!!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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

			if(tcId == 12 || 
					tcId == 29 || 
					tcId == 34 ||
					tcId == 47 ||
					tcId == 70 ||
					tcId == 87 ||
					tcId == 88 ||
					tcId == 89 ){
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

//		dStar(ip, sourceFolder);
		
//		heuristicIII(ip, sourceFolder);
		
		mriDstar(ip, sourceFolder);
		
//		mriHeuristicIII(ip, sourceFolder);
		//MRI METHODOLOGY
//		mriTarantula(ip, sourceFolder);
	}
	
	private static void mriDstar(String ip, String sourceFolder) {
		String pName3_30 = "CodeTest_Dstar_30";
		String pName3_35_1 = "CodeTest_Dstar_35_1";
		String pName3_35_2 = "CodeTest_Dstar_35_2";
		String pName3_35_3 = "CodeTest_Dstar_35_3";

		String pName3_40_1 = "CodeTest_Dstar_40_1";
		String pName3_40_2 = "CodeTest_Dstar_40_2";
		String pName3_40_3 = "CodeTest_Dstar_40_3";
		String pName3_40_4 = "CodeTest_Dstar_40_4";
		String pName3_40_5 = "CodeTest_Dstar_40_5";
		String pName3_40_6 = "CodeTest_Dstar_40_6";
		String pName3_40_7 = "CodeTest_Dstar_40_7";

		String pName3_55_1 = "CodeTest_Dstar_55_1";
		String pName3_55_2 = "CodeTest_Dstar_55_2";
		String pName3_55_3 = "CodeTest_Dstar_55_3";
		String pName3_55_4 = "CodeTest_Dstar_55_4";
		String pName3_55_5 = "CodeTest_Dstar_55_5";
		String pName3_55_6 = "CodeTest_Dstar_55_6";
		String pName3_55_7 = "CodeTest_Dstar_55_7";
		String pName3_55_8 = "CodeTest_Dstar_55_8";

		String expFile3_30 = "/home/wizehack/exp/docFile_mri_30.csv";
		String expFile3_35_1 = "/home/wizehack/exp/docFile_mri_35_1.csv";
		String expFile3_35_2 = "/home/wizehack/exp/docFile_mri_35_2.csv";
		String expFile3_35_3 = "/home/wizehachttp://media.daum.net/foreign/?newsId=20131101092313702k/exp/docFile_mri_35_3.csv";

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

//		LocalizationTechnique mri_dStar_35_1 = new DStar(43,1,"mri_Dstar");
//		demo_mri_35_1.TYPE = 10;
//		demo_mri_35_1.debug(mri_dStar_35_1);
//		
//		LocalizationTechnique mri_dStar_35_2 = new DStar(43,1,"mri_DStar");
//		demo_mri_35_2.TYPE = 11;
//		demo_mri_35_2.debug(mri_dStar_35_2);
//		
//		LocalizationTechnique mri_dStar_35_3 = new DStar(43,1,"mri_DStar");
//		demo_mri_35_3.TYPE = 12;
//		demo_mri_35_3.debug(mri_dStar_35_3);
		
//		LocalizationTechnique mri_dStar_40_1 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_1.TYPE = 13;
//		demo_mri_40_1.debug(mri_dStar_40_1);
//		
//		LocalizationTechnique mri_dStar_40_2 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_2.TYPE = 14;
//		demo_mri_40_2.debug(mri_dStar_40_2);
//		
//		LocalizationTechnique mri_dStar_40_3 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_3.TYPE = 15;
//		demo_mri_40_3.debug(mri_dStar_40_3);
//		
//		LocalizationTechnique mri_dStar_40_4 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_4.TYPE = 16;
//		demo_mri_40_4.debug(mri_dStar_40_4);
//		
//		LocalizationTechnique mri_dStar_40_5 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_5.TYPE = 17;
//		demo_mri_40_5.debug(mri_dStar_40_5);
//		
//		LocalizationTechnique mri_dStar_40_6 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_6.TYPE = 18;
//		demo_mri_40_6.debug(mri_dStar_40_6);
//		
//		LocalizationTechnique mri_dStar_40_7 = new DStar(81,1,"mri_DStar");
//		demo_mri_40_7.TYPE = 19;
//		demo_mri_40_7.debug(mri_dStar_40_7);
		
		
//		LocalizationTechnique mri_dStar_55_1 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_1.TYPE = 20;
//		demo_mri_55_1.debug(mri_dStar_55_1);
//		
//		LocalizationTechnique mri_dStar_55_2 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_2.TYPE = 21;
//		demo_mri_55_2.debug(mri_dStar_55_2);
//	
//		LocalizationTechnique mri_dStar_55_3 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_3.TYPE = 22;
//		demo_mri_55_3.debug(mri_dStar_55_3);
//		
//		LocalizationTechnique mri_dStar_55_4 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_4.TYPE = 23;
//		demo_mri_55_4.debug(mri_dStar_55_4);
//		
//		LocalizationTechnique mri_dStar_55_5 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_5.TYPE = 24;
//		demo_mri_55_5.debug(mri_dStar_55_5);
		
		LocalizationTechnique mri_dStar_55_6 = new DStar(95,1,"mri_DStar");
		demo_mri_55_6.TYPE = 25;
		demo_mri_55_6.debug(mri_dStar_55_6);
//		
//		LocalizationTechnique mri_dStar_55_7 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_7.TYPE = 26;
//		demo_mri_55_7.debug(mri_dStar_55_7);
//		
//		LocalizationTechnique mri_dStar_55_8 = new DStar(95,1,"mri_DStar");
//		demo_mri_55_8.TYPE = 27;
//		demo_mri_55_8.debug(mri_dStar_55_8);	

	}
	
	private static void mriHeuristicIII(String ip, String sourceFolder) {
		String pName3_30 = "CodeTest_HeuristicIII_30";
		String pName3_35_1 = "CodeTest_HeuristicIII_35_1";
		String pName3_35_2 = "CodeTest_HeuristicIII_35_2";
		String pName3_35_3 = "CodeTest_HeuristicIII_35_3";

		String pName3_40_1 = "CodeTest_HeuristicIII_40_1";
		String pName3_40_2 = "CodeTest_HeuristicIII_40_2";
		String pName3_40_3 = "CodeTest_HeuristicIII_40_3";
		String pName3_40_4 = "CodeTest_HeuristicIII_40_4";
		String pName3_40_5 = "CodeTest_HeuristicIII_40_5";
		String pName3_40_6 = "CodeTest_HeuristicIII_40_6";
		String pName3_40_7 = "CodeTest_HeuristicIII_40_7";

		String pName3_55_1 = "CodeTest_HeuristicIII_55_1";
		String pName3_55_2 = "CodeTest_HeuristicIII_55_2";
		String pName3_55_3 = "CodeTest_HeuristicIII_55_3";
		String pName3_55_4 = "CodeTest_HeuristicIII_55_4";
		String pName3_55_5 = "CodeTest_HeuristicIII_55_5";
		String pName3_55_6 = "CodeTest_HeuristicIII_55_6";
		String pName3_55_7 = "CodeTest_HeuristicIII_55_7";
		String pName3_55_8 = "CodeTest_HeuristicIII_55_8";

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

		LocalizationTechnique mri_heuristicIII_35_1 = new HeuristicIII(43,1,"mri_HeuristicIII");
		demo_mri_35_1.TYPE = 10;
		demo_mri_35_1.debug(mri_heuristicIII_35_1);
		
		LocalizationTechnique mri_heuristicIII_35_2 = new HeuristicIII(43,1,"mri_HeuristicIII");
		demo_mri_35_2.TYPE = 11;
		demo_mri_35_2.debug(mri_heuristicIII_35_2);
		
		LocalizationTechnique mri_heuristicIII_35_3 = new HeuristicIII(43,1,"mri_HeuristicIII");
		demo_mri_35_3.TYPE = 12;
		demo_mri_35_3.debug(mri_heuristicIII_35_3);
		
		LocalizationTechnique mri_heuristicIII_40_1 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_1.TYPE = 13;
		demo_mri_40_1.debug(mri_heuristicIII_40_1);
		
		LocalizationTechnique mri_heuristicIII_40_2 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_2.TYPE = 14;
		demo_mri_40_2.debug(mri_heuristicIII_40_2);
		
		LocalizationTechnique mri_heuristicIII_40_3 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_3.TYPE = 15;
		demo_mri_40_3.debug(mri_heuristicIII_40_3);
		
		LocalizationTechnique mri_heuristicIII_40_4 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_4.TYPE = 16;
		demo_mri_40_4.debug(mri_heuristicIII_40_4);
		
		LocalizationTechnique mri_heuristicIII_40_5 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_5.TYPE = 17;
		demo_mri_40_5.debug(mri_heuristicIII_40_5);
		
		LocalizationTechnique mri_heuristicIII_40_6 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_6.TYPE = 18;
		demo_mri_40_6.debug(mri_heuristicIII_40_6);
		
		LocalizationTechnique mri_heuristicIII_40_7 = new HeuristicIII(81,1,"mri_HeuristicIII");
		demo_mri_40_7.TYPE = 19;
		demo_mri_40_7.debug(mri_heuristicIII_40_7);
		
		
		LocalizationTechnique mri_heuristicIII_55_1 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_1.TYPE = 20;
		demo_mri_55_1.debug(mri_heuristicIII_55_1);
		
		LocalizationTechnique mri_heuristicIII_55_2 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_2.TYPE = 21;
		demo_mri_55_2.debug(mri_heuristicIII_55_2);
	
		LocalizationTechnique mri_heuristicIII_55_3 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_3.TYPE = 22;
		demo_mri_55_3.debug(mri_heuristicIII_55_3);
		
		LocalizationTechnique mri_heuristicIII_55_4 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_4.TYPE = 23;
		demo_mri_55_4.debug(mri_heuristicIII_55_4);
		
		LocalizationTechnique mri_heuristicIII_55_5 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_5.TYPE = 24;
		demo_mri_55_5.debug(mri_heuristicIII_55_5);
		
		LocalizationTechnique mri_heuristicIII_55_6 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_6.TYPE = 25;
		demo_mri_55_6.debug(mri_heuristicIII_55_6);
		
		LocalizationTechnique mri_heuristicIII_55_7 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_7.TYPE = 26;
		demo_mri_55_7.debug(mri_heuristicIII_55_7);
		
		LocalizationTechnique mri_heuristicIII_55_8 = new HeuristicIII(95,1,"mri_HeuristicIII");
		demo_mri_55_8.TYPE = 27;
		demo_mri_55_8.debug(mri_heuristicIII_55_8);	
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

		/*
		LocalizationTechnique mri_tarantula_30 = new Tarantula(11,1,"mri_tarantula");
		demo_mri_30.TYPE = 9;
		demo_mri_30.debug(mri_tarantula);
		*/
		
		
		LocalizationTechnique mri_tarantula_35_1 = new Tarantula(43,1,"mri_tarantula");
		demo_mri_35_1.TYPE = 10;
		demo_mri_35_1.debug(mri_tarantula_35_1);
		
		LocalizationTechnique mri_tarantula_35_2 = new Tarantula(43,1,"mri_tarantula");
		demo_mri_35_2.TYPE = 11;
		demo_mri_35_2.debug(mri_tarantula_35_2);
		
		LocalizationTechnique mri_tarantula_35_3 = new Tarantula(43,1,"mri_tarantula");
		demo_mri_35_3.TYPE = 12;
		demo_mri_35_3.debug(mri_tarantula_35_3);
		
		LocalizationTechnique mri_tarantula_40_1 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_1.TYPE = 13;
		demo_mri_40_1.debug(mri_tarantula_40_1);
		
		LocalizationTechnique mri_tarantula_40_2 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_2.TYPE = 14;
		demo_mri_40_2.debug(mri_tarantula_40_2);
		
		LocalizationTechnique mri_tarantula_40_3 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_3.TYPE = 15;
		demo_mri_40_3.debug(mri_tarantula_40_3);
		
		LocalizationTechnique mri_tarantula_40_4 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_4.TYPE = 16;
		demo_mri_40_4.debug(mri_tarantula_40_4);
		
		LocalizationTechnique mri_tarantula_40_5 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_5.TYPE = 17;
		demo_mri_40_5.debug(mri_tarantula_40_5);
		
		LocalizationTechnique mri_tarantula_40_6 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_6.TYPE = 18;
		demo_mri_40_6.debug(mri_tarantula_40_6);
		
		LocalizationTechnique mri_tarantula_40_7 = new Tarantula(81,1,"mri_tarantula");
		demo_mri_40_7.TYPE = 19;
		demo_mri_40_7.debug(mri_tarantula_40_7);
		
		
		LocalizationTechnique mri_tarantula_55_1 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_1.TYPE = 20;
		demo_mri_55_1.debug(mri_tarantula_55_1);
		
		LocalizationTechnique mri_tarantula_55_2 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_2.TYPE = 21;
		demo_mri_55_2.debug(mri_tarantula_55_2);
		
		LocalizationTechnique mri_tarantula_55_3 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_3.TYPE = 22;
		demo_mri_55_3.debug(mri_tarantula_55_3);
		
		LocalizationTechnique mri_tarantula_55_4 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_4.TYPE = 23;
		demo_mri_55_4.debug(mri_tarantula_55_4);
		
		LocalizationTechnique mri_tarantula_55_5 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_5.TYPE = 24;
		demo_mri_55_5.debug(mri_tarantula_55_5);
		
		LocalizationTechnique mri_tarantula_55_6 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_6.TYPE = 25;
		demo_mri_55_6.debug(mri_tarantula_55_6);
		
		LocalizationTechnique mri_tarantula_55_7 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_7.TYPE = 26;
		demo_mri_55_7.debug(mri_tarantula_55_7);
		
		LocalizationTechnique mri_tarantula_55_8 = new Tarantula(95,1,"mri_tarantula");
		demo_mri_55_8.TYPE = 27;
		demo_mri_55_8.debug(mri_tarantula_55_8);
	}

	private static void heuristicIII(String ip, String sourceFolder) {
		String pName2_30 = "CodeTest_HeuristicIII_30";
		String pName2_35 = "CodeTest_HeuristicIII_35";
		String pName2_40 = "CodeTest_HeuristicIII_40";
		String pName2_55 = "CodeTest_HeuristicIII_55";
		
		String expFile2_30 = "/home/wizehack/exp/docFile_HeuristicIII_30.csv";
		String expFile2_35 = "/home/wizehack/exp/docFile_HeuristicIII_35.csv";
		String expFile2_40 = "/home/wizehack/exp/docFile_HeuristicIII_40.csv";
		String expFile2_55 = "/home/wizehack/exp/docFile_HeuristicIII_55.csv";

		Experiment demo_HeuristicIII_30 = new Experiment(pName2_30, ip, sourceFolder, expFile2_30);
		Experiment demo_HeuristicIII_35 = new Experiment(pName2_35, ip, sourceFolder, expFile2_35);
		Experiment demo_HeuristicIII_40 = new Experiment(pName2_40, ip, sourceFolder, expFile2_40);
		Experiment demo_HeuristicIII_55 = new Experiment(pName2_55, ip, sourceFolder, expFile2_55);
		
		LocalizationTechnique heuristicIII_30 = new HeuristicIII(11,1,"HeuristicIII");
		LocalizationTechnique heuristicII_35 = new HeuristicIII(42,3,"HeuristicIII");
		LocalizationTechnique heuristicII_40 = new HeuristicIII(80,7,"HeuristicIII");
		LocalizationTechnique heuristicII_55 = new HeuristicIII(95,8,"HeuristicIII");

		demo_HeuristicIII_30.TYPE = 28;
		demo_HeuristicIII_30.debug(heuristicIII_30);
		
		demo_HeuristicIII_35.TYPE = 29;
		demo_HeuristicIII_35.debug(heuristicII_35);
		
		demo_HeuristicIII_40.TYPE = 30;
		demo_HeuristicIII_40.debug(heuristicII_40);
		
		demo_HeuristicIII_55.TYPE = 31;
		demo_HeuristicIII_55.debug(heuristicII_55);
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
		
		LocalizationTechnique dStar_30 = new DStar(11,1,"dstar");
		LocalizationTechnique dStar_35 = new DStar(42,3,"dstar");
		LocalizationTechnique dStar_40 = new DStar(80,7,"dstar");
		LocalizationTechnique dStar_55 = new DStar(95,8,"dstar");

		demo_Dstar_30.TYPE = 5;
		demo_Dstar_30.debug(dStar_30);
		
		demo_Dstar_35.TYPE = 6;
		demo_Dstar_35.debug(dStar_35);
		
		demo_Dstar_40.TYPE = 7;
		demo_Dstar_40.debug(dStar_40);
		
		demo_Dstar_55.TYPE = 8;
		demo_Dstar_55.debug(dStar_55);
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
		
		LocalizationTechnique tarantula_30 = new Tarantula(11,1,"tarantula");
		LocalizationTechnique tarantula_35 = new Tarantula(42,3,"tarantula");
		LocalizationTechnique tarantula_40 = new Tarantula(80,7,"tarantula");
		LocalizationTechnique tarantula_55 = new Tarantula(95,8,"tarantula");

		
//		demo_tarantula_30.TYPE = 1;
//		demo_tarantula_30.debug(tarantula_30);
		
		demo_tarantula_35.TYPE = 2;
		demo_tarantula_35.debug(tarantula_35);

		demo_tarantula_40.TYPE = 3;
		demo_tarantula_40.debug(tarantula_40);
		
		demo_tarantula_55.TYPE = 4;
		demo_tarantula_55.debug(tarantula_55);
		
	}



}
