package org.wizehack.mri.repo;

import java.io.File;
import java.util.List;

import org.wizehack.mri.CoverageDataParser.CoverageFileReader;
import org.wizehack.mri.Test.TestCase;

public class Prober implements Runnable {
	private String sourceFolder;
	private DataManager dManager;
	private TestCase testCase;
	
	public Prober(String sourceFolder, DataManager dManager, TestCase testCase){
		this.sourceFolder = sourceFolder;
		this.dManager = dManager;
		this.testCase = testCase;
	}
	
//	public TestCase getTestCase() {
//		return testCase;
//	}
//
//
//
//	public void setTestCase(TestCase testCase) {
//		this.testCase = testCase;
//	}



	@Override
	public void run() {
		CoverageFileReader cfr = new CoverageFileReader(dManager);
		cfr.setSourceFolder(sourceFolder);
		cfr.setPassed(testCase.isPassed());
		cfr.setTcId(testCase.getTcId());
		List<Coverage> coverageList = cfr.getCoverage(new File(testCase.getCoverageFile()));
		
		dManager.insertTestResult(testCase);
		dManager.insertCoverage(coverageList); 	
	}
	
	
}