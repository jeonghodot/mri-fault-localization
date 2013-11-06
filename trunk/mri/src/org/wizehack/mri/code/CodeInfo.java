package org.wizehack.mri.code;

import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.repo.Code;

public class CodeInfo {
	private List<Code> statements = null;
	
	public CodeInfo(List<Code> statements){
		this.statements = statements;
	}
	
	public void printStatements() {
		for(int i =0; i< statements.size(); i++){
			System.out.println(statements.get(i).getStatement());
		}
	}

	public void printFileList() {
		List<String> fileList = getFileList();
		for(int i =0; i<fileList.size(); i++){
			System.out.println(fileList.get(i));
		}
	}
	
	public List<String> getFileList(){
		String currentFile = ""; 
		List<String> list = new ArrayList<String>();
		for(int i =0; i<statements.size(); i++){
			String file = statements.get(i).getFile();
			if(currentFile.equals(file)==false){
				list.add(file);
				currentFile = file;
			}
		}
		return list;
	}

	
	public static void main(String[] args) {
		String srcDir = "/home/wizehack/develop/workspace/crawler4j/src";
		CodeLoader cLoader = new JavaCodeLoader();
		List<Code> statements = cLoader.getStatements(srcDir);
		System.out.println("loc: " + statements.size());
		
		CodeInfo codeInfo = new CodeInfo(statements);
		codeInfo.printStatements();
		codeInfo.printFileList();
	}

}
