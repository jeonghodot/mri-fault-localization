package org.wizehack.mri.fl;

public class Tarantula extends LocalizationTechnique {

	
	public Tarantula(int totalPassed, int totalFailed, String techniqueName) {
		super(totalPassed, totalFailed, techniqueName);
	}

	@Override
	public double getSuspiciousness() {
		double suspiciousness = 0;
		double numerator = 0;
				
		if(super.getTotalFailed() == 0){
			return 0;
		} else if(super.getTotalPassed() == 0) {
			return 1;
		} else {
			numerator = ((double)super.getPassed()/(double)super.getTotalPassed()) 
					+ ((double)super.getFailed()/(double)super.getTotalFailed());
			if (numerator == 0){
				return 0;
			}
			else {
				suspiciousness = ((double)super.getFailed()/(double)super.getTotalFailed()) / (double)numerator;
			}
		}
//		System.out.println("totalPassed: " + super.getTotalPassed() + " totalFailed: " + 
//		super.getTotalFailed() + " passed: " + super.getPassed() + " failed: " + super.getFailed() + " suspiciousness: " + suspiciousness);

		return suspiciousness;
	}
	
	/*
	public static void main(String[] args){
		Tarantula t = new Tarantula();
		double s = t.getSuspiciousness(5, 1, 1, 1);
		System.out.println(s);
	}
	 */

}
