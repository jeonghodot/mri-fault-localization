package org.wizehack.mri.fl;


public abstract class LocalizationTechnique {
	private double totalPassed;
	private double totalFailed;
	
	private String techniqueName;
	private double passed;
	private double failed;
		
	public LocalizationTechnique(double totalPassed, double totalFailed, String techniqueName){
		super();
		this.totalPassed = totalPassed;
		this.totalFailed = totalFailed;
		this.techniqueName = techniqueName;
	}
	
	
	public String getTechniqueName() {
		return techniqueName;
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



	public double getTotalPassed() {
		return totalPassed;
	}


	public void setTotalPassed(double totalPassed) {
		this.totalPassed = totalPassed;
	}


	public double getTotalFailed() {
		return totalFailed;
	}


	public void setTotalFailed(int totalFailed) {
		this.totalFailed = totalFailed;
	}


	public abstract double getSuspiciousness();
}