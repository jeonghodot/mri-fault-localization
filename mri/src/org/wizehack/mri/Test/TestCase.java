package org.wizehack.mri.Test;

public class TestCase extends TestSuite{
	private int tcId;
	private boolean passed;
	private String coverageFile;
	
	public TestCase() {
	}

	@Override
	public void add(TestSuite testSuite) {
        throw new UnsupportedOperationException();		
	}

	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public String getCoverageFile() {
		return coverageFile;
	}

	public void setCoverageFile(String coverageFile) {
		this.coverageFile = coverageFile;
	}

	
}
