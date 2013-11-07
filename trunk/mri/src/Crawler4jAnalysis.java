import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.code.CodeInfo;
import org.wizehack.mri.code.CodeLoader;
import org.wizehack.mri.code.JavaCodeLoader;
import org.wizehack.mri.repo.Code;


public class Crawler4jAnalysis {

	public static void main(String[] args) {
		String srcDir = "/home/wizehack/develop/workspace/crawler4j/src";
		CodeLoader cLoader = new JavaCodeLoader();
		List<Code> statements = cLoader.getStatements(srcDir);
		System.out.println("loc: " + statements.size());
		
		CodeInfo codeInfo = new CodeInfo(statements);
		List<String> fileList = codeInfo.getFileList();
		System.out.println("nof: " + fileList.size());
	}

}
