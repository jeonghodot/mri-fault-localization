package org.wizehack.mri.CoverageDataParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.Test.TestCaseWeight;
import org.wizehack.mri.io.FileListReader;
import org.wizehack.mri.repo.DataManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class SimEvaluator {
	private static String COVERAGE = "coverage";
	private static DataManager dManager = null;
	
	public List<Double> getStatementIdList(String dbName, int tcId){
		List<Double> statementIdList = new ArrayList<Double>();
		dManager = new DataManager();		
		Mongo mongo = null;

		try{
			mongo = dManager.getConnection();
			DB db = mongo.getDB(dbName);
			DBCollection coll = db.getCollection(COVERAGE);
			BasicDBObject fquery = new BasicDBObject();
			fquery.append("tcId", tcId);
			DBCursor cur = coll.find(fquery);
			
			while(cur.hasNext()){
				double statementId = (double)cur.next().get("statementId");
				statementIdList.add(statementId);
			}
			
		} finally {
			dManager.closeConnection(mongo);
		}
		
		return statementIdList;
	}
	
	
	public List<TestCaseWeight> computeWeight(String dbName, List<Double> failedStatementIdList, List<Integer>passedTCIdList){
		
		dManager = new DataManager();		
		Mongo mongo = null;
		List<Double> statementIdList = null;
		List<TestCaseWeight> weightList = new ArrayList<TestCaseWeight>();
		try{
			mongo = dManager.getConnection();
			DB db = mongo.getDB(dbName);
			DBCollection coll = db.getCollection(COVERAGE);
			BasicDBObject fquery = new BasicDBObject();
			
			for(int i=0; i<passedTCIdList.size(); i++){
				fquery.append("tcId", passedTCIdList.get(i));
				DBCursor cur = coll.find(fquery);
				statementIdList = new ArrayList<Double>();

//				System.out.println("=====================");
				while(cur.hasNext()){
					double statementId = (double)cur.next().get("statementId");
					statementIdList.add(statementId);
//					System.out.println(statementId);
				}
				
				// compare
				TestCaseWeight weight = getWeight(failedStatementIdList, statementIdList, passedTCIdList.get(i));
//				System.out.println(weight.getTestCaseId() + " : " + weight.getWeight());
				statementIdList.clear();
				weightList.add(weight);
			}		
			
		} finally {
			dManager.closeConnection(mongo);
		}
		return weightList;
	}
	
	private TestCaseWeight getWeight(List<Double> failedStatementIdList,
			List<Double> passedStatementIdList, int passedTestCaseId) {
		double similarity=0;
		double count=0;

		for(int i=0; i<failedStatementIdList.size(); i++){
			double failedStatement = failedStatementIdList.get(i);
			for(int j=0; j<passedStatementIdList.size(); j++){
				if(failedStatement == passedStatementIdList.get(j)){
					count++;
					break;
				}
			}
		}
		similarity = count / failedStatementIdList.size();
		
		return new TestCaseWeight(passedTestCaseId, similarity * 2);
	}
	
	public List<Integer> getPassedTCIdList(String path, int failedTCId){
		List<Integer> passedTCIdList = new ArrayList<Integer>();
				
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType("xml");
		
		List<String> fList = fileListReader.readFiles(new File(path));
		CoverageFileReader cfr = new CoverageFileReader();

		for(int i=0; i<fList.size(); i++){
			String name = fList.get(i);
			int tcId = new Integer(cfr.getCoverageFileName(name));
			
			if(tcId != failedTCId){
				passedTCIdList.add(tcId);
			}
		}
		
		return passedTCIdList;
	}


	public static void main(String[] args) {
		// Start time
		double startTime = System.currentTimeMillis();
		
		SimEvaluator sim = new SimEvaluator();
		String dbName = "CodeTest_Tarantula_55_6";
		String tcPath = "/home/wizehack/exp/mri/jGraphX/55/6";
		int failedTCId = 87;
		List<Double> failedStatementIdList = sim.getStatementIdList(dbName, 87);
		List<Integer> passedTCIdList = sim.getPassedTCIdList(tcPath, failedTCId);
		
		List<TestCaseWeight> weightList = sim.computeWeight(dbName, failedStatementIdList, passedTCIdList);

		for(int i=0; i<weightList.size(); i++){
			int tcId = i+1;
			System.out.println(weightList.get(i).getTestCaseId() + ": " + weightList.get(i).getWeight() );
		}
		
		// End time
		double endTime = System.currentTimeMillis();

		// Total time
		double elapsedTime = endTime - startTime;
		System.out.println("TIME : " + elapsedTime/1000 + "(sec)");
	}

}
