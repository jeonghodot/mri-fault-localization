package org.wizehack.mri.CoverageDataParser;

/**
 * The Line class represents a pair of coverage data that consists of a line number and a boolean variable. 
 * The line number is the integer variable. The boolean variable determines if the value is executed or not
 * @author hsyoun
 *
 */
public class Line {
	
	private int lineNumber;
	private boolean covered;
	
	/**
	 * getter of a line number
	 * @return line number
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	
	/**
	 * getter of a line number
	 * @param lineNumber 
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	/**
	 * identifer of coverage. 
	 * @return return true if the line is executed. return false if the line is not executed. 
	 */
	public boolean isCovered() {
		return covered;
	}
	
	/**
	 * setter of coverage 
	 * @param covered
	 */
	public void setCovered(boolean covered) {
		this.covered = covered;
	}
	
}
