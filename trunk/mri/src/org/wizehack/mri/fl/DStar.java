package org.wizehack.mri.fl;

public class DStar extends LocalizationTechnique {

	public DStar(int totalPassed, int totalFailed, String techniqueName) {
		super(totalPassed, totalFailed, techniqueName);
	}

	@Override
	public double getSuspiciousness() {
		double ncf = super.getFailed();
		double nuf = super.getTotalFailed() - ncf;
		double ncs = super.getPassed();
		double sus = 0;
		double numerator = nuf + ncs;
		
		if(numerator == 0){
			if(ncf != 0 && ncf==nuf)
				sus = 1;
			else 
				sus = 0;
		} else
			sus = (ncf * ncf) / (numerator);
		return sus;
	}

}
