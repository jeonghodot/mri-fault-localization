import java.util.List;

import org.wizehack.mri.code.CodeLoader;
import org.wizehack.mri.code.JavaCodeLoader;
import org.wizehack.mri.repo.Code;


public class YamibeansAnalysis {

	public static void main(String[] args) {
		String srcDir = "/home/wizehack/develop/workspace/yamlbeans/src";
		CodeLoader cLoader = new JavaCodeLoader();
		List<Code> statements = cLoader.getStatements(srcDir);
		System.out.println("loc: " + statements.size());
	}

}
