package org.wizehack.mri.code;

import java.io.File;
import java.util.ArrayList;

import org.wizehack.mri.io.FileListReader;


public class JavaCodeLoader extends CodeLoader{
	
	public JavaCodeLoader() {
		super();
	}

	@Override
	public ArrayList<String> loadFile(String dirPath) {
		
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType(".java");
		ArrayList<String> fList = fileListReader.readFiles(new File(dirPath));

		return fList;
	}

	@Override
	public ArrayList<String> loadCode(File file) {
		
		CodeScanner cs = new CodeScanner();
		ArrayList<String> cList = cs.scan(file);
		
		return cList;
	}

}
