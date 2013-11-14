package org.wizehack.mri.fl;

public abstract class CoefficientMetric {
	private double notExecutedPass;
	private double executedPass;
	private double notExecutedFail;
	private double executedFail;
	
	public abstract double computeSuspiciousness();
	
	public double getNotExecutedPass() {
		return notExecutedPass;
	}
	public void setNotExecutedPass(double notExecutedPass) {
		this.notExecutedPass = notExecutedPass;
	}
	public double getExecutedPass() {
		return executedPass;
	}
	public void setExecutedPass(double executedPass) {
		this.executedPass = executedPass;
	}
	public double getNotExecutedFail() {
		return notExecutedFail;
	}
	public void setNotExecutedFail(double notExecutedFail) {
		this.notExecutedFail = notExecutedFail;
	}
	public double getExecutedFail() {
		return executedFail;
	}
	public void setExecutedFail(double executedFail) {
		this.executedFail = executedFail;
	}
	
	
}
