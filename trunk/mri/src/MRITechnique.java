import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.io.FileIO;
import org.wizehack.mri.repo.Code;
import org.wizehack.mri.repo.MongoManager;
import org.wizehack.mri.repo.Result;
import org.wizehack.mri.repo.Spectrum;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class MRITechnique {
	private static MongoManager mongoManager = null;
	private static String IP = "127.0.0.1";
	private static String STATISTICS = "statistics";
	private static String CODE = "code";
	private static String FAULT_LOCALIZATION = "localization";
	private static String COVERAGE = "coverage";
	

	public static void main(String[] args) {
		mongoManager = MongoManager.getInstance();
		mongoManager.createPool(IP);
		
		mri_DStar_35();
		mri_DStar_40();
		mri_DStar_55();

		mri_tarantula_35();
		
		mri_tarantula_40();

		mri_tarantula_55();
	}
	
	private static void mri_DStar_55() {
		String db_55_1 = "CodeTest_DStar_55_1";
		String db_55_2 = "CodeTest_DStar_55_2";
		String db_55_3 = "CodeTest_DStar_55_3";
		String db_55_4 = "CodeTest_DStar_55_4";
		String db_55_5 = "CodeTest_DStar_55_5";
		String db_55_6 = "CodeTest_DStar_55_6";

		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList4 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList5 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList6 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList7 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList8 = new ArrayList<TestCaseInfo>();

		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_DStar_55_1 = mongo.getDB(db_55_1);
			List<Result> resultList_55_1 = getLocalizationCollection(db_DStar_55_1);
			
			DB db_DStar_55_2 = mongo.getDB(db_55_2);
			List<Result> resultList_55_2 = getLocalizationCollection(db_DStar_55_2);

			DB db_DStar_55_3 = mongo.getDB(db_55_3);
			List<Result> resultList_55_3 = getLocalizationCollection(db_DStar_55_3);

			DB db_DStar_55_4 = mongo.getDB(db_55_4);
			List<Result> resultList_55_4 = getLocalizationCollection(db_DStar_55_4);
			
			DB db_DStar_55_5 = mongo.getDB(db_55_5);
			List<Result> resultList_55_5 = getLocalizationCollection(db_DStar_55_5);

			DB db_DStar_55_6 = mongo.getDB(db_55_6);
			List<Result> resultList_55_6 = getLocalizationCollection(db_DStar_55_6);

			double t1 = getThreshold(resultList_55_1);
			double t2 = getThreshold(resultList_55_2);
			double t3 = getThreshold(resultList_55_3);
			double t4 = getThreshold(resultList_55_4);
			double t5 = getThreshold(resultList_55_5);
			double t6 = getThreshold(resultList_55_6);

			for(int i=0; i<resultList_55_1.size(); i++){
				Result result1 = resultList_55_1.get(i);
				Result result2 = resultList_55_2.get(i);
				Result result3 = resultList_55_3.get(i);
				Result result4 = resultList_55_4.get(i);
				Result result5 = resultList_55_5.get(i);
				Result result6 = resultList_55_6.get(i);
				
				if(new Double(result1.getSuspiciousness()) < t1 ||
						new Double(result2.getSuspiciousness()) < t2 ||
						new Double(result3.getSuspiciousness()) < t3 ||
						new Double(result4.getSuspiciousness()) < t4 ||
						new Double(result5.getSuspiciousness()) < t5 ||
						new Double(result6.getSuspiciousness()) < t6
						) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) +
							new Double(result4.getSuspiciousness()) +
							new Double(result5.getSuspiciousness()) +
							new Double(result6.getSuspiciousness()) 
					) / 6;
					updateSuspiciousness(db_DStar_55_1, id, suspiciousness);
					updateSuspiciousness(db_DStar_55_2, id, suspiciousness);
					updateSuspiciousness(db_DStar_55_3, id, suspiciousness);
					updateSuspiciousness(db_DStar_55_4, id, suspiciousness);
					updateSuspiciousness(db_DStar_55_5, id, suspiciousness);
					updateSuspiciousness(db_DStar_55_6, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_55_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_55_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_55_3.csv";
			String docFile4 = "/home/wizehack/exp/mri/mri_55_4.csv";
			String docFile5 = "/home/wizehack/exp/mri/mri_55_5.csv";
			String docFile6 = "/home/wizehack/exp/mri/mri_55_6.csv";

			exportToTextDoc(db_DStar_55_1, docFile1);
			exportToTextDoc(db_DStar_55_2, docFile2);
			exportToTextDoc(db_DStar_55_3, docFile3);
			exportToTextDoc(db_DStar_55_4, docFile4);
			exportToTextDoc(db_DStar_55_5, docFile5);
			exportToTextDoc(db_DStar_55_6, docFile6);
		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}
		
	}

	private static void mri_DStar_40() {
		String db_40_1 = "CodeTest_DStar_40_1";
		String db_40_2 = "CodeTest_DStar_40_2";
		String db_40_3 = "CodeTest_DStar_40_3";
		String db_40_4 = "CodeTest_DStar_40_4";
		String db_40_5 = "CodeTest_DStar_40_5";
		String db_40_6 = "CodeTest_DStar_40_6";
		String db_40_7 = "CodeTest_DStar_40_7";

		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList4 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList5 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList6 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList7 = new ArrayList<TestCaseInfo>();

		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_DStar_40_1 = mongo.getDB(db_40_1);
			List<Result> resultList_40_1 = getLocalizationCollection(db_DStar_40_1);
			
			DB db_DStar_40_2 = mongo.getDB(db_40_2);
			List<Result> resultList_40_2 = getLocalizationCollection(db_DStar_40_2);

			DB db_DStar_40_3 = mongo.getDB(db_40_3);
			List<Result> resultList_40_3 = getLocalizationCollection(db_DStar_40_3);

			DB db_DStar_40_4 = mongo.getDB(db_40_4);
			List<Result> resultList_40_4 = getLocalizationCollection(db_DStar_40_4);
			
			DB db_DStar_40_5 = mongo.getDB(db_40_5);
			List<Result> resultList_40_5 = getLocalizationCollection(db_DStar_40_5);

			DB db_DStar_40_6 = mongo.getDB(db_40_6);
			List<Result> resultList_40_6 = getLocalizationCollection(db_DStar_40_6);

			DB db_DStar_40_7 = mongo.getDB(db_40_7);
			List<Result> resultList_40_7 = getLocalizationCollection(db_DStar_40_7);
			

			double t1 = getThreshold(resultList_40_1);
			double t2 = getThreshold(resultList_40_2);
			double t3 = getThreshold(resultList_40_3);
			double t4 = getThreshold(resultList_40_4);
			double t5 = getThreshold(resultList_40_5);
			double t6 = getThreshold(resultList_40_6);
			double t7 = getThreshold(resultList_40_7);

			for(int i=0; i<resultList_40_1.size(); i++){
				Result result1 = resultList_40_1.get(i);
				Result result2 = resultList_40_2.get(i);
				Result result3 = resultList_40_3.get(i);
				Result result4 = resultList_40_4.get(i);
				Result result5 = resultList_40_5.get(i);
				Result result6 = resultList_40_6.get(i);
				Result result7 = resultList_40_7.get(i);
				
				if(new Double(result1.getSuspiciousness()) < t1 ||
						new Double(result2.getSuspiciousness()) < t2 ||
						new Double(result3.getSuspiciousness()) < t3 ||
						new Double(result4.getSuspiciousness()) < t4 ||
						new Double(result5.getSuspiciousness()) < t5 ||
						new Double(result6.getSuspiciousness()) < t6 ||
						new Double(result7.getSuspiciousness()) < t7
						) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) +
							new Double(result4.getSuspiciousness()) +
							new Double(result5.getSuspiciousness()) +
							new Double(result6.getSuspiciousness()) +
							new Double(result7.getSuspiciousness())
					) / 7;
					updateSuspiciousness(db_DStar_40_1, id, suspiciousness);
					updateSuspiciousness(db_DStar_40_2, id, suspiciousness);
					updateSuspiciousness(db_DStar_40_3, id, suspiciousness);
					updateSuspiciousness(db_DStar_40_4, id, suspiciousness);
					updateSuspiciousness(db_DStar_40_5, id, suspiciousness);
					updateSuspiciousness(db_DStar_40_6, id, suspiciousness);
					updateSuspiciousness(db_DStar_40_7, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_40_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_40_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_40_3.csv";
			String docFile4 = "/home/wizehack/exp/mri/mri_40_4.csv";
			String docFile5 = "/home/wizehack/exp/mri/mri_40_5.csv";
			String docFile6 = "/home/wizehack/exp/mri/mri_40_6.csv";
			String docFile7 = "/home/wizehack/exp/mri/mri_40_7.csv";
		
			exportToTextDoc(db_DStar_40_1, docFile1);
			exportToTextDoc(db_DStar_40_2, docFile2);
			exportToTextDoc(db_DStar_40_3, docFile3);
			exportToTextDoc(db_DStar_40_4, docFile4);
			exportToTextDoc(db_DStar_40_5, docFile5);
			exportToTextDoc(db_DStar_40_6, docFile6);
			exportToTextDoc(db_DStar_40_7, docFile7);
		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}

	}

	private static void mri_DStar_35() {
		String db_35_1 = "CodeTest_DStar_35_1";
		String db_35_2 = "CodeTest_DStar_35_2";
		String db_35_3 = "CodeTest_DStar_35_3";
				
		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();

		
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_DStar_35_1 = mongo.getDB(db_35_1);
			List<Result> resultList_35_1 = getLocalizationCollection(db_DStar_35_1);
			
			DB db_DStar_35_2 = mongo.getDB(db_35_2);
			List<Result> resultList_35_2 = getLocalizationCollection(db_DStar_35_2);

			DB db_DStar_35_3 = mongo.getDB(db_35_3);
			List<Result> resultList_35_3 = getLocalizationCollection(db_DStar_35_3);
			
			double t1 = getThreshold(resultList_35_1);
			double t2 = getThreshold(resultList_35_2);
			double t3 = getThreshold(resultList_35_3);

			for(int i=0; i<resultList_35_1.size(); i++){
				Result result1 = resultList_35_1.get(i);
				Result result2 = resultList_35_2.get(i);
				Result result3 = resultList_35_3.get(i);
				
				if(new Double(result1.getSuspiciousness()) < t1 ||
						new Double(result2.getSuspiciousness()) < t2 ||
						new Double(result3.getSuspiciousness()) < t3) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness())) / 3;
					updateSuspiciousness(db_DStar_35_1, id, suspiciousness);
					updateSuspiciousness(db_DStar_35_2, id, suspiciousness);
					updateSuspiciousness(db_DStar_35_3, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_35_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_35_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_35_3.csv";
			
			exportToTextDoc(db_DStar_35_1, docFile1);
			exportToTextDoc(db_DStar_35_2, docFile2);
			exportToTextDoc(db_DStar_35_3, docFile3);

		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}
		
	}

	private static void mri_tarantula_55() {
		String db_55_1 = "CodeTest_Tarantula_55_1";
		String db_55_2 = "CodeTest_Tarantula_55_2";
		String db_55_3 = "CodeTest_Tarantula_55_3";
		String db_55_4 = "CodeTest_Tarantula_55_4";
		String db_55_5 = "CodeTest_Tarantula_55_5";
		String db_55_6 = "CodeTest_Tarantula_55_6";
		String db_55_7 = "CodeTest_Tarantula_55_7";
		String db_55_8 = "CodeTest_Tarantula_55_8";

		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList4 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList5 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList6 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList7 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList8 = new ArrayList<TestCaseInfo>();

		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_Tarantula_55_1 = mongo.getDB(db_55_1);
			List<Result> resultList_55_1 = getLocalizationCollection(db_Tarantula_55_1);
			
			DB db_Tarantula_55_2 = mongo.getDB(db_55_2);
			List<Result> resultList_55_2 = getLocalizationCollection(db_Tarantula_55_2);

			DB db_Tarantula_55_3 = mongo.getDB(db_55_3);
			List<Result> resultList_55_3 = getLocalizationCollection(db_Tarantula_55_3);

			DB db_Tarantula_55_4 = mongo.getDB(db_55_4);
			List<Result> resultList_55_4 = getLocalizationCollection(db_Tarantula_55_4);
			
			DB db_Tarantula_55_5 = mongo.getDB(db_55_5);
			List<Result> resultList_55_5 = getLocalizationCollection(db_Tarantula_55_5);

			DB db_Tarantula_55_6 = mongo.getDB(db_55_6);
			List<Result> resultList_55_6 = getLocalizationCollection(db_Tarantula_55_6);

			DB db_Tarantula_55_7 = mongo.getDB(db_55_7);
			List<Result> resultList_55_7 = getLocalizationCollection(db_Tarantula_55_7);
			
			DB db_Tarantula_55_8 = mongo.getDB(db_55_8);
			List<Result> resultList_55_8 = getLocalizationCollection(db_Tarantula_55_8);

			double t1 = getThreshold(resultList_55_1);
			double t2 = getThreshold(resultList_55_2);
			double t3 = getThreshold(resultList_55_3);
			double t4 = getThreshold(resultList_55_4);
			double t5 = getThreshold(resultList_55_5);
			double t6 = getThreshold(resultList_55_6);
			double t7 = getThreshold(resultList_55_7);
			double t8 = getThreshold(resultList_55_8);

			for(int i=0; i<resultList_55_1.size(); i++){
				Result result1 = resultList_55_1.get(i);
				Result result2 = resultList_55_2.get(i);
				Result result3 = resultList_55_3.get(i);
				Result result4 = resultList_55_4.get(i);
				Result result5 = resultList_55_5.get(i);
				Result result6 = resultList_55_6.get(i);
				Result result7 = resultList_55_7.get(i);
				Result result8 = resultList_55_8.get(i);
				
				if(new Double(result1.getSuspiciousness()) < t1 ||
						new Double(result2.getSuspiciousness()) < t2 ||
						new Double(result3.getSuspiciousness()) < t3 ||
						new Double(result4.getSuspiciousness()) < t4 ||
						new Double(result5.getSuspiciousness()) < t5 ||
						new Double(result6.getSuspiciousness()) < t6 ||
						new Double(result7.getSuspiciousness()) < t7 ||
						new Double(result8.getSuspiciousness()) < t8
						) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) +
							new Double(result4.getSuspiciousness()) +
							new Double(result5.getSuspiciousness()) +
							new Double(result6.getSuspiciousness()) +
							new Double(result7.getSuspiciousness()) +
							new Double(result8.getSuspiciousness())
					) / 8;
					updateSuspiciousness(db_Tarantula_55_1, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_2, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_3, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_4, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_5, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_6, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_7, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_55_8, id, suspiciousness);


				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_55_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_55_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_55_3.csv";
			String docFile4 = "/home/wizehack/exp/mri/mri_55_4.csv";
			String docFile5 = "/home/wizehack/exp/mri/mri_55_5.csv";
			String docFile6 = "/home/wizehack/exp/mri/mri_55_6.csv";
			String docFile7 = "/home/wizehack/exp/mri/mri_55_7.csv";
			String docFile8 = "/home/wizehack/exp/mri/mri_55_8.csv";

			exportToTextDoc(db_Tarantula_55_1, docFile1);
			exportToTextDoc(db_Tarantula_55_2, docFile2);
			exportToTextDoc(db_Tarantula_55_3, docFile3);
			exportToTextDoc(db_Tarantula_55_4, docFile4);
			exportToTextDoc(db_Tarantula_55_5, docFile5);
			exportToTextDoc(db_Tarantula_55_6, docFile6);
			exportToTextDoc(db_Tarantula_55_7, docFile7);
			exportToTextDoc(db_Tarantula_55_8, docFile8);
		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}
	}
	
	private static void mri_tarantula_40() {
		String db_40_1 = "CodeTest_Tarantula_40_1";
		String db_40_2 = "CodeTest_Tarantula_40_2";
		String db_40_3 = "CodeTest_Tarantula_40_3";
		String db_40_4 = "CodeTest_Tarantula_40_4";
		String db_40_5 = "CodeTest_Tarantula_40_5";
		String db_40_6 = "CodeTest_Tarantula_40_6";
		String db_40_7 = "CodeTest_Tarantula_40_7";

		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList4 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList5 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList6 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList7 = new ArrayList<TestCaseInfo>();

		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_Tarantula_40_1 = mongo.getDB(db_40_1);
			List<Result> resultList_40_1 = getLocalizationCollection(db_Tarantula_40_1);
			
			DB db_Tarantula_40_2 = mongo.getDB(db_40_2);
			List<Result> resultList_40_2 = getLocalizationCollection(db_Tarantula_40_2);

			DB db_Tarantula_40_3 = mongo.getDB(db_40_3);
			List<Result> resultList_40_3 = getLocalizationCollection(db_Tarantula_40_3);

			DB db_Tarantula_40_4 = mongo.getDB(db_40_4);
			List<Result> resultList_40_4 = getLocalizationCollection(db_Tarantula_40_4);
			
			DB db_Tarantula_40_5 = mongo.getDB(db_40_5);
			List<Result> resultList_40_5 = getLocalizationCollection(db_Tarantula_40_5);

			DB db_Tarantula_40_6 = mongo.getDB(db_40_6);
			List<Result> resultList_40_6 = getLocalizationCollection(db_Tarantula_40_6);

			DB db_Tarantula_40_7 = mongo.getDB(db_40_7);
			List<Result> resultList_40_7 = getLocalizationCollection(db_Tarantula_40_7);
			

			double t1 = getThreshold(resultList_40_1);
			double t2 = getThreshold(resultList_40_2);
			double t3 = getThreshold(resultList_40_3);
			double t4 = getThreshold(resultList_40_4);
			double t5 = getThreshold(resultList_40_5);
			double t6 = getThreshold(resultList_40_6);
			double t7 = getThreshold(resultList_40_7);

			for(int i=0; i<resultList_40_1.size(); i++){
				Result result1 = resultList_40_1.get(i);
				Result result2 = resultList_40_2.get(i);
				Result result3 = resultList_40_3.get(i);
				Result result4 = resultList_40_4.get(i);
				Result result5 = resultList_40_5.get(i);
				Result result6 = resultList_40_6.get(i);
				Result result7 = resultList_40_7.get(i);
				
				if(new Double(result1.getSuspiciousness()) < t1 ||
						new Double(result2.getSuspiciousness()) < t2 ||
						new Double(result3.getSuspiciousness()) < t3 ||
						new Double(result4.getSuspiciousness()) < t4 ||
						new Double(result5.getSuspiciousness()) < t5 ||
						new Double(result6.getSuspiciousness()) < t6 ||
						new Double(result7.getSuspiciousness()) < t7
						) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness()) +
							new Double(result4.getSuspiciousness()) +
							new Double(result5.getSuspiciousness()) +
							new Double(result6.getSuspiciousness()) +
							new Double(result7.getSuspiciousness())
					) / 7;
					updateSuspiciousness(db_Tarantula_40_1, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_40_2, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_40_3, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_40_4, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_40_5, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_40_6, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_40_7, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_40_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_40_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_40_3.csv";
			String docFile4 = "/home/wizehack/exp/mri/mri_40_4.csv";
			String docFile5 = "/home/wizehack/exp/mri/mri_40_5.csv";
			String docFile6 = "/home/wizehack/exp/mri/mri_40_6.csv";
			String docFile7 = "/home/wizehack/exp/mri/mri_40_7.csv";
		
			exportToTextDoc(db_Tarantula_40_1, docFile1);
			exportToTextDoc(db_Tarantula_40_2, docFile2);
			exportToTextDoc(db_Tarantula_40_3, docFile3);
			exportToTextDoc(db_Tarantula_40_4, docFile4);
			exportToTextDoc(db_Tarantula_40_5, docFile5);
			exportToTextDoc(db_Tarantula_40_6, docFile6);
			exportToTextDoc(db_Tarantula_40_7, docFile7);
		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}
		
	
	}
	
	private static void mri_tarantula_35() {
		String db_35_1 = "CodeTest_Tarantula_35_1";
		String db_35_2 = "CodeTest_Tarantula_35_2";
		String db_35_3 = "CodeTest_Tarantula_35_3";
				
		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();

		
		Mongo mongo = null;
		try {
			mongo = mongoManager.getConnection();
			DB db_Tarantula_35_1 = mongo.getDB(db_35_1);
			List<Result> resultList_35_1 = getLocalizationCollection(db_Tarantula_35_1);
			
			DB db_Tarantula_35_2 = mongo.getDB(db_35_2);
			List<Result> resultList_35_2 = getLocalizationCollection(db_Tarantula_35_2);

			DB db_Tarantula_35_3 = mongo.getDB(db_35_3);
			List<Result> resultList_35_3 = getLocalizationCollection(db_Tarantula_35_3);
/*			
			System.out.println("getting similarity...");
			for(int i=1; i<=45; i++){
				
				TestCaseInfo tcInfo1 = new TestCaseInfo();
				TestCaseInfo tcInfo2 = new TestCaseInfo();
				TestCaseInfo tcInfo3 = new TestCaseInfo();
				
				if(i == 12 || i == 29 || i == 34){
					continue;
				}
				
				tcInfo1.tcId = i;
				tcInfo1.wieght = getSimilerity(db_Tarantula_35_1, 12, i) + 1;
				
				tcInfo2.tcId = i;
				tcInfo2.wieght = getSimilerity(db_Tarantula_35_2, 29, i) + 1;
				
				tcInfo3.tcId = i;
				tcInfo3.wieght = getSimilerity(db_Tarantula_35_3, 34, i) + 1;
				
				testCaseInfoList1.add(tcInfo1);
				testCaseInfoList2.add(tcInfo2);
				testCaseInfoList3.add(tcInfo3);
				System.out.println(i);
			}
			
			insertWeight(db_Tarantula_35_1, testCaseInfoList1);
			insertWeight(db_Tarantula_35_2, testCaseInfoList2);
			insertWeight(db_Tarantula_35_3, testCaseInfoList3);

			reduceCoverageMap(db_Tarantula_35_1);
			reduceCoverageMap(db_Tarantula_35_1);
			reduceCoverageMap(db_Tarantula_35_1);
*/
			
			double t1 = getThreshold(resultList_35_1);
			double t2 = getThreshold(resultList_35_2);
			double t3 = getThreshold(resultList_35_3);

			for(int i=0; i<resultList_35_1.size(); i++){
				Result result1 = resultList_35_1.get(i);
				Result result2 = resultList_35_2.get(i);
				Result result3 = resultList_35_3.get(i);
				
				if(new Double(result1.getSuspiciousness()) < t1 ||
						new Double(result2.getSuspiciousness()) < t2 ||
						new Double(result3.getSuspiciousness()) < t3) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness())) / 3;
					updateSuspiciousness(db_Tarantula_35_1, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_35_2, id, suspiciousness);
					updateSuspiciousness(db_Tarantula_35_3, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_35_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_35_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_35_3.csv";
			
			exportToTextDoc(db_Tarantula_35_1, docFile1);
			exportToTextDoc(db_Tarantula_35_2, docFile2);
			exportToTextDoc(db_Tarantula_35_3, docFile3);

		} finally {
			System.out.println("Compeleted");
			mongoManager.closeConnection(mongo);
		}
	}
	
	public static void reduceCoverageMap(DB db){
		
	}
	
	public static void insertWeight(DB db, List<TestCaseInfo> testCaseInfoList){
		DBCollection coll = db.getCollection(COVERAGE);
		BasicDBObject query;
		
		for(int i=0; i<testCaseInfoList.size(); i++){
			query = new BasicDBObject("tcId", testCaseInfoList.get(i).tcId);
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("weight", testCaseInfoList.get(i).wieght);
			  
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$push", newDocument);
			 
			coll.update(query, updateObj);
		}
		
		DBObject dbObj = coll.findOne();
		System.out.println(dbObj);
	}
	
	public static double getThreshold(List<Result> resultList){
		double threshold = 0.0;
		return threshold;
	}
	
	public static double getSimilerity(DB db, int failedTCId, int successTCId){
		double equal = 0;
		double similarity = 0;
		DBCollection coll = db.getCollection(COVERAGE);
		
		BasicDBObject failedTCquery = new BasicDBObject("tcId", failedTCId);
//		BasicDBObject pssedTCquery = new BasicDBObject("tcId", successTCId);
		
		DBCursor cursor = coll.find(failedTCquery);
		

		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			double fId = (double)obj.get("statementId");
			
			BasicDBObject pssedTCquery = new BasicDBObject("tcId", successTCId).append("statementId", fId);
			DBObject o = coll.findOne(pssedTCquery); 
			if(o != null){
				System.out.println(o);
				equal++;
			}
		}
		
		BasicDBObject query = new BasicDBObject("tcId", successTCId);
//		DBCursor successCur = coll.find(query);
		
		similarity = equal / cursor.size();
		
		return similarity;
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

class TestCaseInfo { 
	int tcId;
	double wieght;
}
