package org.wizehack.mri.repo;

public class Coverage {
	private String file;
	private double statementId;
	private int number;
	private int tcId;
	private boolean passed;

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public double getStatementId() {
		return statementId;
	}
	public void setStatementId(double statementId) {
		this.statementId = statementId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTcId() {
		return tcId;
	}
	public void setTcId(int tcId) {
		this.tcId = tcId;
	}
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	} 
	
}
