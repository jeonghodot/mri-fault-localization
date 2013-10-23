package org.wizehack.mri.repo;

import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.code.CodeLoader;
import org.wizehack.mri.code.JavaCodeLoader;
import org.wizehack.mri.config.Configurator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;

public class DataManager {
	private static MongoManager mongoManager = null;
	private String DB_NAME;
	private static String TEST_RESULT = "test";
	private static String COVERAGE = "coverage";
	private static String CODE = "code";
	private static String STATISTICS = "statistics";
	private static String FAULT_LOCALIZATION = "localization";
	
	public DataManager(String dbName, String ip){
		this.DB_NAME = dbName;
//		Configurator.getInstance().setProjectName(dbName);
//		Configurator.getInstance().setIp(ip);
		
		mongoManager = MongoManager.getInstance();
		mongoManager.createPool(ip);
		
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);
			db.dropDatabase();
		} finally {
			mongoManager.closeConnection(mongo);
		}
		
		
	}
	
	public void insertTestResult(TestCase testCase){
		Mongo mongo = null;
		
		try {
			mongo = mongoManager.getConnection();			
			DB db = mongo.getDB(DB_NAME);
			
			DBCollection coll = db.getCollection(TEST_RESULT);
			BasicDBObject query = new BasicDBObject();
			
			query.put( "tcId", testCase.getTcId() );
			query.put( "isPassed", testCase.isPassed() );
			query.put( "coverageFile", testCase.getCoverageFile());

			coll.insert(query);
		} finally {
			mongoManager.closeConnection(mongo);
		}
	}
	
	public void insertCode(List<Code> codeList){
		Mongo mongo = null;
		
		try{
			mongo = mongoManager.getConnection();
			
			DB db = mongo.getDB(DB_NAME);
			DBCollection coll = db.getCollection(CODE);
			
			for(int i=0; i<codeList.size(); i++){
				Code code = codeList.get(i);
				BasicDBObject query = new BasicDBObject();
				query.put( "statementId", code.getStatementId() );
				query.put( "file", code.getFile() );
				query.put( "lineNumber", code.getNumber() );
				query.put( "statement", code.getStatement() );
				coll.insert(query);
			}
		} finally {
			mongoManager.closeConnection(mongo);
		}
	}
	
	public double getStatementId(String sourceFile, int lineNumber){
		double id=0;
		Mongo mongo = null;

		try{
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);
			DBCollection coll = db.getCollection(CODE);

			BasicDBObject query = new BasicDBObject("file", sourceFile).
	                append("lineNumber", lineNumber);
			DBObject obj = coll.findOne(query);
			id = (double) obj.get("statementId"); 
		} finally {
			mongoManager.closeConnection(mongo);
		}
		
		return id;
	}
	
	public void insertCoverage(List<Coverage> coverageList){
		Mongo mongo = null;

		try{
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);
			DBCollection coll = db.getCollection(COVERAGE);

			for(int i=0; i<coverageList.size(); i++){
				Coverage coverage = coverageList.get(i);
				BasicDBObject query = new BasicDBObject();
				query.put( "statementId", coverage.getStatementId() );
				query.put( "file", coverage.getFile() );
				query.put( "lineNumber", coverage.getNumber() );
				query.put( "tcId", coverage.getTcId() );
				query.put( "isPassed", coverage.isPassed() );
				coll.insert(query);
			}
		} finally{
			mongoManager.closeConnection(mongo);
		}		
	}
	
	public DBCollection reduceCoverageMap() {
		Mongo mongo = null;
		DBCollection collection = null;
		
		String map = "function() {"
					+ "var key = this.statementId;"
					+ "var value = {"
						+ "file: this.file,"
						+ "line_number: this.lineNumber,"
						+ "isPassed: this.isPassed,"
						+ "passed: 1,"
						+ "failed: 1"
						+ "};"
						+ "emit( key, value );"
					+ "};";
		
		String reduce ="function(key, values) {"
				+ "var reducedObject = {"
					+ "file: null,"
					+ "line_number: 0,"
					+ "passed: 0, "
					+ "failed: 0"
				+ "};"
				+ "values.forEach( function(value) {"
					+ "reducedObject.file = value.file,"
					+ "reducedObject.line_number = value.line_number;"
					+ "if(value.isPassed == true){"
						+ "reducedObject.passed += value.passed;"
					+ "} else {"
						+ "reducedObject.failed += value.failed;"
						+ "}"
					+ "}"
				+ ");"
				+ "return reducedObject;"
				+ "};";
		
		String outCollection = "statistics";
		
		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);
			DBCollection coll = db.getCollection(COVERAGE);
			
			MapReduceOutput output = coll.mapReduce(map,reduce,outCollection,MapReduceCommand.OutputType.REDUCE,null);
			output.getOutputCollection(); 
			
			collection = output.getOutputCollection(); 
			
		} finally {
			mongoManager.closeConnection(mongo);
		}
		
		return collection;
	}
	
	public void insertSuspiciousness (Code code, SuspiciousnessMap suspiciousnessMap){
		Mongo mongo = null;
		
		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);
			
			DBCollection coll = db.getCollection(FAULT_LOCALIZATION);
			BasicDBObject query = new BasicDBObject();
			
			query.put( "statementId", code.getStatementId() );
			query.put( "file", code.getFile() );
			query.put( "line", code.getNumber() );
			query.put( "statement", code.getStatement());
			query.put( "suspiciousness", suspiciousnessMap.getSuspiciousness());

			coll.insert(query);

		} finally {
			mongoManager.closeConnection(mongo);
		}
	}
	
	public void viewDocuments(String collection){
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);

			DBCollection testResultColl = db.getCollection(collection);
			DBCursor curTest = testResultColl.find();
			
			while(curTest.hasNext()){
				DBObject dbObject = (DBObject) curTest.next();
				System.out.println(dbObject);
			}
		} finally {
			mongoManager.closeConnection(mongo);
		}
	}

	public List<Spectrum> getStatistics() {
		List<Spectrum> spectrumList = new ArrayList<Spectrum>();
		
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);

			DBCollection coll = db.getCollection(STATISTICS);
			DBCursor cursor = coll.find();
			
			while(cursor.hasNext()){
				DBObject dbObject = (DBObject) cursor.next();			
				double statementId = (double) dbObject.get("_id");
				DBObject value = (DBObject) dbObject.get("value");
				double passed = (double) value.get("passed");
				double failed = (double) value.get("failed");
				Spectrum spectrum = new Spectrum((int)statementId, (int)passed, (int)failed);
				spectrumList.add(spectrum);
			}
		} finally {
			mongoManager.closeConnection(mongo);
		}
		return spectrumList;
	}

	public List<Code> getStatements() {
		List<Code> codeList = new ArrayList<Code>();
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);

			DBCollection coll = db.getCollection(CODE);
			DBCursor cursor = coll.find();
			
			while(cursor.hasNext()){
				DBObject dbObject = (DBObject) cursor.next();		
				Code code = new Code();
				code.setFile(dbObject.get("file").toString());
				code.setNumber((int)dbObject.get("lineNumber"));
				try{
					String statement = dbObject.get("statement").toString();
					code.setStatement(statement);
				} catch (NullPointerException ne){
					code.setStatement("");
				}
				code.setStatementId((double)dbObject.get("statementId"));
				codeList.add(code);
			}
		} finally {
			mongoManager.closeConnection(mongo);
		}
		return codeList;
	}

	public List<Result> getLocalizationCollection() {
		
		List<Result> resultList = new ArrayList<Result>();
		Mongo mongo = null;

		try {
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);

			DBCollection coll = db.getCollection(FAULT_LOCALIZATION);
			DBCursor cursor = coll.find();
			
			while(cursor.hasNext()){
				DBObject dbObject = (DBObject) cursor.next();		
				
				String statementId = dbObject.get("statementId").toString();
				String file = dbObject.get("file").toString();
				String line = dbObject.get("line").toString();
				String statement = dbObject.get("statement").toString();
				String suspiciousness = dbObject.get("suspiciousness").toString();
				
				Result result = new Result();
				result.setStatementId(statementId);
				result.setFile(file);
				result.setLine(line);
				result.setStatement(statement);
				result.setSuspiciousness(suspiciousness);
				
				resultList.add(result);
				
			}

		} finally {
			mongoManager.closeConnection(mongo);
		}

		return resultList;
	}

	
	
	
	
	
}
