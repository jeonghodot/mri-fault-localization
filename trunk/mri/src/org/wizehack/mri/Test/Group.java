package org.wizehack.mri.Test;

public class Group extends TestSuite {

	@Override
	public void add(TestSuite testSuite) {
		children.add(testSuite);

	}

	@Override
	public void setPassed(boolean passed) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isPassed() {
		throw new UnsupportedOperationException();
	}

}
