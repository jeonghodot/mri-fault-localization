package org.wizehack.mri.repo;

public class Result {
	private String statementId;
	private String file;
	private String line;
	private String statement;
	private String suspiciousness;
	public String getStatementId() {
		return statementId;
	}
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public String getSuspiciousness() {
		return suspiciousness;
	}
	public void setSuspiciousness(String suspiciousness) {
		this.suspiciousness = suspiciousness;
	}
	
	
	
}
