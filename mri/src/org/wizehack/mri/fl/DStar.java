package org.wizehack.mri.fl;

public class DStar extends LocalizationTechnique {

	public DStar(int totalPassed, int totalFailed, String techniqueName) {
		super(totalPassed, totalFailed, techniqueName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getSuspiciousness() {
		int ncf = super.getFailed();
		int nuf = super.getTotalFailed() - ncf;
		int ncs = super.getPassed();
		
		double sus = (ncf * ncf) / (nuf + ncs);
		return sus;
	}

}
