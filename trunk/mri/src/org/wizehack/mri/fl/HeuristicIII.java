package org.wizehack.mri.fl;

public class HeuristicIII extends LocalizationTechnique{

	public HeuristicIII(double totalPassed, double totalFailed, String techniqueName) {
		super(totalPassed, totalFailed, techniqueName);
	}

	@Override
	public double getSuspiciousness() {
		double suspiciousness = 0;
		double firstFailed = 0;
		double secondFailed = 0;
		double thiredFailed = 0;
		
		double firstPassed = 0;
		double secondPassed = 0;
		double thiredPassed = 0;
		
		double a=0.001;
		double firstWeight = 1;
		double secondWeight = 0.1;
		double thiredFailedWeight = 0.01;
		double thiredPassedWeight = 0;
		
		if(super.getPassed() >0)
			thiredPassedWeight = a * (super.getFailed()/super.getPassed());
		else
			thiredPassedWeight = 0;
		
		if(super.getFailed() > 4){
			firstFailed = 2;
			secondFailed = 4;
			thiredFailed = super.getFailed() - 6;
			
			if(super.getPassed() > 4){
				firstPassed = 1;
				secondPassed = 4;
				thiredPassed = super.getPassed() - 5;
			}
			else if(super.getPassed() > 1){
				firstPassed = 1;
				secondPassed = super.getPassed() - 1;
			}
			else {
				firstPassed = super.getPassed();
			}
		} //if(failedNum > 4)
		else if(super.getFailed() > 2){
			firstFailed = 2;
			secondPassed = super.getFailed() - firstFailed;
			
			if(super.getPassed() > 4){
				firstPassed = 1;
				secondPassed = 4;
				thiredPassed = super.getPassed() - 5;
			}
			else if(super.getPassed() > 1){
				firstPassed = 1;
				secondPassed = super.getPassed() - 1;
			}
			else {
				firstPassed = super.getPassed();
			}
		} //else if(failedNum > 2)
		else if(super.getFailed() == 0){
			return 0;
		}
		else {
			firstFailed = super.getFailed();
			
			if(super.getPassed() > 4){
				secondPassed = 4;
				thiredPassed = super.getPassed() - secondPassed;
			}
			else if(super.getPassed() > 1){
				secondPassed = 1;
				thiredPassed = super.getPassed() - secondPassed;
			}
			else {
				secondPassed = super.getPassed();
			}
		}
		
		double failedSum = (firstFailed * firstWeight ) + (secondFailed * secondWeight) + (thiredFailed * thiredFailedWeight);
		double passedSum = (firstPassed * firstWeight) + (secondPassed * secondWeight) + (thiredPassed * thiredPassedWeight);
		
		suspiciousness = failedSum - passedSum;
		
		return suspiciousness;
	}


}
