/**
 * 
 */
package org.wizehack.mri.CoverageDataParser;

import java.util.ArrayList;

/**
 * The SourceFile is an the ArrayList class that represents the list of the Line objects.  </p>
 * example to use: </p>
 * for(int i=0; i<lineList.size(); i++){  </p>
 *		System.out.println(lineList.get(i).getLineNumber() + ": " + lineList.get(i).isCovered());  </p>
 *	}  </p>
 * @author hsyoun <wizehack@gmail.com> 
 *
 */
public class SourceFile extends ArrayList<Line> {

	private String name;

	/**
	 * getter of file location 
	 * @return file location 
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter of file location
	 * @param name file location
	 */
	public void setName(String name) {
		this.name = name;
	}

}
