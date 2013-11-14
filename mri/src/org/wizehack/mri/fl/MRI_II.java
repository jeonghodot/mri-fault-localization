package org.wizehack.mri.fl;

public class MRI_II extends CoefficientMetric{
	
	public MRI_II(double executedPass, double notExecutedPass, double executedFailed, double notExecutedFailed) {
		this.setExecutedFail(executedFailed);
		this.setNotExecutedFail(notExecutedFailed);
		this.setExecutedPass(executedPass);
		this.setNotExecutedPass(notExecutedPass);
		System.out.println("========================");

		System.out.println(executedPass + ": " + notExecutedPass);

	}

	@Override
	public double computeSuspiciousness() {

		double suspiciousness = (this.getNotExecutedPass() + this.getExecutedFail() + this.getNotExecutedFail()) /
				(this.getExecutedPass() + this.getNotExecutedPass() + this.getExecutedFail() + this.getNotExecutedFail());
		System.out.println(this.getExecutedPass() + ": " + this.getNotExecutedPass());
		System.out.println(suspiciousness);

		return suspiciousness;
	}
	
	
}
