import java.io.File;
import java.util.List;

import org.wizehack.mri.SFL;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.io.FileListReader;


public class MidDebugger extends SFL{

	public MidDebugger(String projectName, String repositoryIp,
			String sourceFolder, String exportFile) {
		super(projectName, repositoryIp, sourceFolder, exportFile);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String pName = "Mid_Debug";
		String expFile = "/home/wizehack/develop/workspace/Mid/Report/report.csv";
		String ip = "127.0.0.1";
		String projectPath = "/home/wizehack/develop/workspace/Mid";
		LocalizationTechnique tarantula = new Tarantula(5,1,"tarantula");

		MidDebugger midDebugger = new MidDebugger(pName, ip, projectPath, expFile);
		midDebugger.debug(tarantula);
	}

	@Override
	public TestSuite setTestResult() {
		float id = 1;
		TestSuite testSuite = new Group();
		testSuite.setId(id);
		String covDir = "/home/wizehack/develop/workspace/Mid/coverage/Mid";
		
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType("xml");
		
		List<String> fList = fileListReader.readFiles(new File(covDir));
		
		for(int i=0; i<fList.size(); i++){
			
			String name = fList.get(i);
			name = super.getCoverageFileName(name);
			
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

		return testSuite;
	}

}
