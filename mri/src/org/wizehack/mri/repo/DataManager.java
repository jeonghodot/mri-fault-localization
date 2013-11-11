package org.wizehack.mri.repo;

import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.Test.TestCase;
import org.wizehack.mri.io.FileIO;

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
	
	public DataManager() {
		mongoManager = MongoManager.getInstance();
		mongoManager.createPool("127.0.0.1");
	}
	
	public DataManager(String ip){
		mongoManager = MongoManager.getInstance();
		mongoManager.createPool(ip);
	}
	
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
			DBCollection testColl = db.getCollection(TEST_RESULT);
			DBCollection covColl = db.getCollection(COVERAGE);
			DBCollection codeColl = db.getCollection(CODE);
			DBCollection statColl = db.getCollection(STATISTICS);
			DBCollection flColl = db.getCollection(FAULT_LOCALIZATION);
			
			testColl.drop();
			covColl.drop();
			codeColl.drop();
			statColl.drop();
			flColl.drop();
			
			db.dropDatabase();
		} finally {
			mongoManager.closeConnection(mongo);
		}
	}
	
	public Mongo getConnection(){
		return mongoManager.getConnection();
	}
	
	public void closeConnection(Mongo mongo){
		mongoManager.closeConnection(mongo);
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
		DBObject obj = null;
		BasicDBObject query = null;
		DBCollection coll = null;
		
		try{
			mongo = mongoManager.getConnection();
			DB db = mongo.getDB(DB_NAME);
			coll = db.getCollection(CODE);

			query = new BasicDBObject("file", sourceFile).
	                append("lineNumber", lineNumber);
			obj = coll.findOne(query);
			while(obj == null){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("getting statementId....failed.....retrying...");
				obj = coll.findOne(query);
//				id = (double) obj.get("statementId"); 
			}
			id = (double) obj.get("statementId"); 
//			System.out.println("id: " + id);
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
						+ "isPassed: this.isPassed"
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
						+ "reducedObject.passed = reducedObject.passed + 1;"
					+ "} else {"
						+ "reducedObject.failed = reducedObject.failed + 1;"
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
				double passed;
				double failed;
				
				try {
					passed = (double) value.get("passed");
					failed = (double) value.get("failed");
				} catch (NullPointerException ne) {
					
					if(value.get("isPassed").toString().matches("true")){
						passed = 1;
						failed = 0;
						
					} else {
						passed = 0;
						failed = 1;
//						System.out.println("isPassed: false" + statementId);
					}
				}
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

	
	
	
	
	
	public void exportToTextDoc(DB db, String expFile) {
		List<Result> results = getLocalizationCollection(db);
		List<String> doc = new ArrayList<String>();
		FileIO fileIO = new FileIO();
		fileIO.setOutFile(expFile);
		
		char seperator = '\t';
		String column = "id" +seperator+ "file" +seperator+ "line"+seperator+
				"suspciciousness"+seperator+"statement" ;
		doc.add(column);
		
		for(int i=0; i<results.size(); i++) {
			Result result = results.get(i);
			String id = result.getStatementId();
			String file = result.getFile();
			String line = result.getLine();
			String suspiciousness = result.getSuspiciousness();
			String statement = result.getStatement().replace('\t', '\0');
			
			String fields = id + seperator + file + seperator + line + seperator + 
					suspiciousness + seperator + statement;
			
			doc.add(fields);
		}
		fileIO.write(doc);
	}
	
	public void updateSuspiciousness(DB db, double statementId, double suspiciousness){
		DBCollection coll = db.getCollection(FAULT_LOCALIZATION);
		
		// get documents by query
	    BasicDBObject query = new BasicDBObject("statementId", statementId);
	 
//	    cursor = coll.find(query);
	     
	  
	    /**** Update ****/
	    //update documents found by query 
	    BasicDBObject newDocument = new BasicDBObject();
	    newDocument.put("suspiciousness", suspiciousness);
	  
	    BasicDBObject updateObj = new BasicDBObject();
	    updateObj.put("$set", newDocument);
	  
	    coll.update(query, updateObj,false,true);
	}
	
	public boolean isCovered(DB db, double statementId){
		DBCollection coll = db.getCollection(STATISTICS);
		BasicDBObject query = new BasicDBObject("_id", statementId);
		DBObject obj = coll.findOne(query);
		
		if(obj != null){
			return true;
		} else {
			return false;
		}
	}
	
	public static List<Spectrum> getStatistics(DB db) {
		List<Spectrum> spectrumList = new ArrayList<Spectrum>();
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
		return spectrumList;
	}
	
	public static List<Code> getStatements(DB db) {
		List<Code> codeList = new ArrayList<Code>();
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
		return codeList;
	}
	
	public void insertSuspiciousness(DB db, Result result){
		DBCollection coll = db.getCollection(FAULT_LOCALIZATION);
		BasicDBObject query = new BasicDBObject();
		
		query.put( "statementId", result.getStatementId() );
		query.put( "file", result.getFile() );
		query.put( "line", result.getLine() );
		query.put( "statement", result.getStatement());
		query.put( "suspiciousness", result.getSuspiciousness());

		coll.insert(query);
	}
	
	public List<Result> getLocalizationCollection(DB db) {
		List<Result> resultList = new ArrayList<Result>();
		
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
		
		return resultList;
	}

	
}
