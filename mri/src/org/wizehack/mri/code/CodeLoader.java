package org.wizehack.mri.code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.repo.Code;


/**
 * The CodeLoader reads source code files and writes all lines of codes to the database
 * This class is designed according to the rule of the template method pattern. 
 * If you add another code loader such as c++, c, etc, you should inherit this class and implement the abstract method.
 *  
 * @author hsyoun <wizehack@gmail.com>
 *
 */
public abstract class CodeLoader {
	private double statementId;
	
	public CodeLoader () {
		statementId = 1;
	}
	/**
	 * @param dirPath directory path 
	 * @return the list of file in the directory path
	 */
	public abstract ArrayList<String> loadFile(String dirPath);
	
	/**
	 * @param file file instance
	 * @return the code list of input file
	 */
	public abstract ArrayList<String> loadCode(File file);

	/**
	 * 
	 * writing the code of each file to database 
	 * @param dirPath directory path 
	 * @return This method returns true if the writing is completed or returns false if the writing can not be completed. </p>
	 * example to use </p>
	 * 		String dirPath = "./src/code/"; </p>
	 *		CodeLoader cLoader = new JavaCodeLoader(); </p>
	 *		cLoader.writeToDatabase(dirPath); </p>
	 * 
	 */
	public List<Code> getStatements(String dirPath){
		ArrayList<String> fList = loadFile(dirPath);
		List<Code> codeList = new ArrayList<Code>();
		for(int i=0; i<fList.size(); i++){
			List<String> cList = loadCode(new File(fList.get(i)));
			
			String fileLocation = fList.get(i);
			
			if (fileLocation != null){

				for (int j=0; j<cList.size(); j++){
					Code code = new Code();
					code.setFile(fileLocation);
					int number = j+1;
					code.setStatement(cList.get(j));
					code.setNumber(number);
					code.setStatementId(statementId);
					statementId++;
					codeList.add(code);
				} 
			} else {
				throw new NullPointerException("fileLocation is null");
			}

		} // end for i
		return codeList;
	}
	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		
//		String dirPath = "./src/";
//		CodeLoader cLoader = new JavaCodeLoader();
//		TestConfigurator tConf = TestConfigurator.getInstance();
//		tConf.setNumberOfTestSuite(4);
//		int[] numberOfTestCase = {6,8,4,7};
//		tConf.setNumberOfTestCase(numberOfTestCase);
//		
////		tConf isInitiated = cLoader.writeToDatabase(dirPath);
////		if(isInitiated){
////			System.out.println("The database is initialized");
////		}
//		
//	}

}
