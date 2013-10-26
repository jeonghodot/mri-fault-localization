import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.io.FileIO;
import org.wizehack.mri.repo.Code;
import org.wizehack.mri.repo.MongoManager;
import org.wizehack.mri.repo.Result;
import org.wizehack.mri.repo.Spectrum;
import org.wizehack.mri.repo.SuspiciousnessMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class MRITechnique35 {
	private static MongoManager mongoManager = null;
	private static String DB_NAME;
	private static String IP = "127.0.0.1";
	private static String STATISTICS = "statistics";
	private static String CODE = "code";
	private static String FAULT_LOCALIZATION = "localization";
	private static String FAULT_LOCALIZATION_MRI = "localization_mri";

	public static void main(String[] args) {
		String db_35_1 = "CodeTest_Tarantula_35_1";
		String db_35_2 = "CodeTest_Tarantula_35_2";
		String db_35_3 = "CodeTest_Tarantula_35_3";
				
		//spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		//suspiciousness
		
		mongoManager = MongoManager.getInstance();
		mongoManager.createPool(IP);
		
		List<Double> avgSusStatementList = new ArrayList<Double>();
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_Tarantula_35_1 = mongo.getDB(db_35_1);
//			List<Spectrum> spectrumList_35_1 = getStatistics(db_Tarantula_35_1);
//			List<Code> codeList_35_1 = getStatements(db_Tarantula_35_1);
			List<Result> resultList_35_1 = getLocalizationCollection(db_Tarantula_35_1);
			
			DB db_Tarantula_35_2 = mongo.getDB(db_35_2);
//			List<Spectrum> spectrumList_35_2 = getStatistics(db_Tarantula_35_2);
//			List<Code> codeList_35_2 = getStatements(db_Tarantula_35_2);
			List<Result> resultList_35_2 = getLocalizationCollection(db_Tarantula_35_2);

			DB db_Tarantula_35_3 = mongo.getDB(db_35_3);
//			List<Spectrum> spectrumList_35_3 = getStatistics(db_Tarantula_35_3);
//			List<Code> codeList_35_3 = getStatements(db_Tarantula_35_3);
			List<Result> resultList_35_3 = getLocalizationCollection(db_Tarantula_35_3);

			for(int i=0; i<resultList_35_1.size(); i++){
				Result result1 = resultList_35_1.get(i);
				Result result2 = resultList_35_2.get(i);
				Result result3 = resultList_35_3.get(i);
				
				if(new Double(result1.getSuspiciousness()) == 0) {
					double id = new Double(result1.getStatementId());
					if(isCovered(db_Tarantula_35_1, id)) {
						double suspiciousness = ( new Double(result1.getSuspiciousness()) +
								new Double(result2.getSuspiciousness()) +
								new Double(result3.getSuspiciousness())) / 3;
//						avgSusStatementList.add(id);
						updateSuspiciousness(db_Tarantula_35_1, id, suspiciousness);
					}
				} else if(new Double(result2.getSuspiciousness()) == 0) {
					double id = new Double(result2.getStatementId());
					if(isCovered(db_Tarantula_35_2, id)) {
						double suspiciousness = ( new Double(result1.getSuspiciousness()) +
								new Double(result2.getSuspiciousness()) +
								new Double(result3.getSuspiciousness())) / 3;
//						avgSusStatementList.add(id);
						updateSuspiciousness(db_Tarantula_35_2, id, suspiciousness);					}
				} else if(new Double(result3.getSuspiciousness()) == 0) {
					double id = new Double(result3.getStatementId());
					if(isCovered(db_Tarantula_35_3, id)) {
						double suspiciousness = ( new Double(result1.getSuspiciousness()) +
								new Double(result2.getSuspiciousness()) +
								new Double(result3.getSuspiciousness())) / 3;
//						avgSusStatementList.add(id);
						updateSuspiciousness(db_Tarantula_35_3, id, suspiciousness);					}
				}
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/docFile_mri_35_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/docFile_mri_35_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/docFile_mri_35_3.csv";
			
			exportToTextDoc(db_Tarantula_35_1, docFile1);
			exportToTextDoc(db_Tarantula_35_2, docFile2);
			exportToTextDoc(db_Tarantula_35_3, docFile3);

		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}
	}
	
	public static void exportToTextDoc(DB db, String expFile) {
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
	
	public static void updateSuspiciousness(DB db, double statementId, double suspiciousness){
		DBCollection coll = db.getCollection(FAULT_LOCALIZATION);
		BasicDBObject newDoc = new BasicDBObject();
		newDoc.append("$set", new BasicDBObject().append("suspiciousness", suspiciousness));
	 
		BasicDBObject searchQuery = new BasicDBObject().append("statementId", statementId);
	 
		coll.update(searchQuery, newDoc);
	}
	
	public static boolean isCovered(DB db, double statementId){
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
	
	public static List<Result> getLocalizationCollection(DB db) {
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
