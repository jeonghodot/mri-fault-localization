package org.wizehack.mri.fl;

public class DStar extends LocalizationTechnique {

	public DStar(int totalPassed, int totalFailed, String techniqueName) {
		super(totalPassed, totalFailed, techniqueName);
	}

	@Override
	public double getSuspiciousness() {
		int ncf = super.getFailed();
		int nuf = super.getTotalFailed() - ncf;
		int ncs = super.getPassed();
		double sus = 0;
		int numerator = nuf + ncs;
		if(numerator == 0)
			sus = 0;
		else
			sus = (ncf * ncf) / (numerator);
		return sus;
	}

}
