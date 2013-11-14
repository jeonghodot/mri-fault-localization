import java.io.File;
import java.util.List;

import org.wizehack.mri.MRI2SFL;
import org.wizehack.mri.SFL;
import org.wizehack.mri.CoverageDataParser.CoverageFileReader;
import org.wizehack.mri.Test.Group;
import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.Test.TestSuite;
import org.wizehack.mri.fl.LocalizationTechnique;
import org.wizehack.mri.fl.MRI_II;
import org.wizehack.mri.fl.Tarantula;
import org.wizehack.mri.io.FileListReader;


public class MidMRI_II_Debugger extends MRI2SFL {

	public MidMRI_II_Debugger(String projectName, String repositoryIp,
			String sourceFolder, String exportFile) {
		super(projectName, repositoryIp, sourceFolder, exportFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TestSuite setTestResult() {
		float id = 1;
		TestSuite testSuite = new Group();
		testSuite.setId(id);
		String covDir = "/home/wizehack/develop/workspace/Mid/coverage/Mid";
		
		super.setCoverageFile(testSuite, covDir);

		return testSuite;
	}


	public static void main(String[] args) {
		String pName = "Mid_MRI_II_Debug";
		String expFile = "/home/wizehack/exp/report/Mid/report.csv";
		String ip = "127.0.0.1";
		String projectPath = "/home/wizehack/develop/workspace/Mid";
		
		MidMRI_II_Debugger debugger = new MidMRI_II_Debugger(pName, ip, projectPath, expFile);
		debugger.debug(6);
	}

}
