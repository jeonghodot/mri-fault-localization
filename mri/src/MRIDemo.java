

import java.io.File;
import java.util.List;

import org.wizehack.mri.SFL;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.io.FileListReader;

public class MRIDemo extends SFL{

	public MRIDemo(String projectName, String repositoryIp,
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
		
		String covDir = "/home/wizehack/develop/workspace/jGraphX/Coverage";
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
		String pName = "CodeTest";
		String ip = "127.0.0.1";
		String sourceFolder = "/home/wizehack/develop/workspace/jGraphX/src";
		String expFile = "/home/wizehack/exp/docFile.csv";

		MRIDemo demo = new MRIDemo(pName, ip, sourceFolder, expFile);
		LocalizationTechnique tarantula = new Tarantula(3,3,"tarantula");
		demo.debug(tarantula);
	}



}
