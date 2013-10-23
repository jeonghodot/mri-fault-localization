package org.wizehack.mri.fl;


public abstract class LocalizationTechnique {
	private int totalPassed;
	private int totalFailed;
	
	private String techniqueName;
	private int passed;
	private int failed;
		
	public LocalizationTechnique(int totalPassed, int totalFailed, String techniqueName){
		super();
		this.totalPassed = totalPassed;
		this.totalFailed = totalFailed;
		this.techniqueName = techniqueName;
	}
	
	
	public String getTechniqueName() {
		return techniqueName;
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



	public int getTotalPassed() {
		return totalPassed;
	}


	public void setTotalPassed(int totalPassed) {
		this.totalPassed = totalPassed;
	}


	public int getTotalFailed() {
		return totalFailed;
	}


	public void setTotalFailed(int totalFailed) {
		this.totalFailed = totalFailed;
	}


	public abstract double getSuspiciousness();
}