package org.wizehack.mri.repo;

public class Spectrum {
	private double statementId;
	private int passed;
	private int failed;
	
	public Spectrum(double statementId, int passed, int failed) {
		this.statementId = statementId;
		this.passed = passed;
		this.failed = failed;
	}

	public double getStatementId() {
		return statementId;
	}

	public void setStatementId(double statementId) {
		this.statementId = statementId;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}
	
	

}
