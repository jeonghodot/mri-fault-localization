package org.wizehack.mri.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;

/**
 * The CodeScanner reads a file and makes the ArrayList that includes the lines of code. </p>
 * 
 * example to use: </p>
 * 		CodeScanner cs = new CodeScanner(); </p>
 *		ArrayList<String> lineList = cs.scan(new File("src/code/CodeScanner.java")); </p>
 *		for(int i=0; i<lineList.size(); i++){ </p>
 *			System.out.println(lineList.get(i)); </p>
 *		} </p>
 *
 * @author hsyoun <wizehack@gmail.com>
 *	
 */
public class CodeScanner
{
	private ArrayList<String> codeList = null; 

	/**
	 * This method reads input file, and then returns the list of line. 
	 * @param file instance
	 * @return the list of line
	 */
	public ArrayList<String> scan(File file) {
		
		codeList = new ArrayList<String>();
    	
		try	{
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line = null;
			do {
				line = reader.readLine();                
				codeList.add(line);
			}while (line != null);
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
        }
		return codeList;
    }
//
//	public static void main(String[] args)
//    {
//		CodeScanner cs = new CodeScanner();
//		ArrayList<String> lineList = cs.scan(new File("src/code/CodeScanner.java"));
//		for(int i=0; i<lineList.size(); i++){
//			System.out.println(lineList.get(i));
//		}
//    }
}