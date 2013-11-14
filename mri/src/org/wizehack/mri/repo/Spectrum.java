package org.wizehack.mri.repo;

public class Spectrum {
	private double statementId;
	private double passed;
	private double failed;
	
	public Spectrum(double statementId, double passed, double failed) {
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

	public double getPassed() {
		return passed;
	}

	public void setPassed(double passed) {
		this.passed = passed;
	}

	public double getFailed() {
		return failed;
	}

	public void setFailed(double failed) {
		this.failed = failed;
	}
	
	

}
