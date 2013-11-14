package org.wizehack.mri.experiment;
import java.util.ArrayList;
import java.util.List;

import org.wizehack.mri.fl.MRI_I;
import org.wizehack.mri.repo.DataManager;
import org.wizehack.mri.repo.Result;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class JGraphXMRIDebugger {
	private static String COVERAGE = "coverage";
	private static DataManager dManager = null;
	
		
	public static void main(String[] args) {
		dManager = new DataManager();
		
		mri_Dstar_35();
		mri_Dstar_40();
		mri_Dstar_55();
		
//		mri_tarantula_35();
//		mri_tarantula_40();
//		mri_tarantula_55();
	}
	
	private static void mri_Dstar_55() {
		MRI_I wizeFlII = new MRI_I("dstar", 8);
		
		String db_55_1 = "CodeTest_Dstar_55_1";
		String db_55_2 = "CodeTest_Dstar_55_2";
		String db_55_3 = "CodeTest_Dstar_55_3";
		String db_55_4 = "CodeTest_Dstar_55_4";
		String db_55_5 = "CodeTest_Dstar_55_5";
		String db_55_6 = "CodeTest_Dstar_55_6";
		String db_55_7 = "CodeTest_Dstar_55_7";
		String db_55_8 = "CodeTest_Dstar_55_8";

		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		//suspiciousness

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Dstar_55_1 = mongo.getDB(db_55_1);
			List<Result> resultList_55_1 = dManager.getLocalizationCollection(db_Dstar_55_1);
			
			DB db_Dstar_55_2 = mongo.getDB(db_55_2);
			List<Result> resultList_55_2 = dManager.getLocalizationCollection(db_Dstar_55_2);

			DB db_Dstar_55_3 = mongo.getDB(db_55_3);
			List<Result> resultList_55_3 = dManager.getLocalizationCollection(db_Dstar_55_3);

			DB db_Dstar_55_4 = mongo.getDB(db_55_4);
			List<Result> resultList_55_4 = dManager.getLocalizationCollection(db_Dstar_55_4);
			
			DB db_Dstar_55_5 = mongo.getDB(db_55_5);
			List<Result> resultList_55_5 = dManager.getLocalizationCollection(db_Dstar_55_5);

			DB db_Dstar_55_6 = mongo.getDB(db_55_6);
			List<Result> resultList_55_6 = dManager.getLocalizationCollection(db_Dstar_55_6);

			DB db_Dstar_55_7 = mongo.getDB(db_55_7);
			List<Result> resultList_55_7 = dManager.getLocalizationCollection(db_Dstar_55_7);
			
			DB db_Dstar_55_8 = mongo.getDB(db_55_8);
			List<Result> resultList_55_8 = dManager.getLocalizationCollection(db_Dstar_55_8);

			double t1 = wizeFlII.getThreshold(resultList_55_1);
			double t2 = wizeFlII.getThreshold(resultList_55_2);
			double t3 = wizeFlII.getThreshold(resultList_55_3);
			double t4 = wizeFlII.getThreshold(resultList_55_4);
			double t5 = wizeFlII.getThreshold(resultList_55_5);
			double t6 = wizeFlII.getThreshold(resultList_55_6);
			double t7 = wizeFlII.getThreshold(resultList_55_7);
			double t8 = wizeFlII.getThreshold(resultList_55_8);

			for(int i=0; i<resultList_55_1.size(); i++){
				Result result1 = resultList_55_1.get(i);
				Result result2 = resultList_55_2.get(i);
				Result result3 = resultList_55_3.get(i);
				Result result4 = resultList_55_4.get(i);
				Result result5 = resultList_55_5.get(i);
				Result result6 = resultList_55_6.get(i);
				Result result7 = resultList_55_7.get(i);
				Result result8 = resultList_55_8.get(i);
				
				if((dManager.isCovered(db_Dstar_55_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Dstar_55_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Dstar_55_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 ) ||
						(dManager.isCovered(db_Dstar_55_4, new Double(result4.getStatementId())) && new Double(result4.getSuspiciousness()) < t4 ) ||
						(dManager.isCovered(db_Dstar_55_5, new Double(result5.getStatementId())) && new Double(result5.getSuspiciousness()) < t5 ) ||
						(dManager.isCovered(db_Dstar_55_6, new Double(result6.getStatementId())) && new Double(result6.getSuspiciousness()) < t6 ) ||
						(dManager.isCovered(db_Dstar_55_7, new Double(result7.getStatementId())) && new Double(result7.getSuspiciousness()) < t7 ) ||
						(dManager.isCovered(db_Dstar_55_8, new Double(result8.getStatementId())) && new Double(result8.getSuspiciousness()) < t8 )
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
					dManager.updateSuspiciousness(db_Dstar_55_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_3, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_4, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_5, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_6, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_7, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_55_8, id, suspiciousness);


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

			dManager.exportToTextDoc(db_Dstar_55_1, docFile1);
			dManager.exportToTextDoc(db_Dstar_55_2, docFile2);
			dManager.exportToTextDoc(db_Dstar_55_3, docFile3);
			dManager.exportToTextDoc(db_Dstar_55_4, docFile4);
			dManager.exportToTextDoc(db_Dstar_55_5, docFile5);
			dManager.exportToTextDoc(db_Dstar_55_6, docFile6);
			dManager.exportToTextDoc(db_Dstar_55_7, docFile7);
			dManager.exportToTextDoc(db_Dstar_55_8, docFile8);
		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
		
	}

	private static void mri_Dstar_40() {
		MRI_I wizeFlII = new MRI_I("dstar", 7);

		String db_40_1 = "CodeTest_Dstar_40_1";
		String db_40_2 = "CodeTest_Dstar_40_2";
		String db_40_3 = "CodeTest_Dstar_40_3";
		String db_40_4 = "CodeTest_Dstar_40_4";
		String db_40_5 = "CodeTest_Dstar_40_5";
		String db_40_6 = "CodeTest_Dstar_40_6";
		String db_40_7 = "CodeTest_Dstar_40_7";

		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		//suspiciousness

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Dstar_40_1 = mongo.getDB(db_40_1);
			List<Result> resultList_40_1 = dManager.getLocalizationCollection(db_Dstar_40_1);
			
			DB db_Dstar_40_2 = mongo.getDB(db_40_2);
			List<Result> resultList_40_2 = dManager.getLocalizationCollection(db_Dstar_40_2);

			DB db_Dstar_40_3 = mongo.getDB(db_40_3);
			List<Result> resultList_40_3 = dManager.getLocalizationCollection(db_Dstar_40_3);

			DB db_Dstar_40_4 = mongo.getDB(db_40_4);
			List<Result> resultList_40_4 = dManager.getLocalizationCollection(db_Dstar_40_4);
			
			DB db_Dstar_40_5 = mongo.getDB(db_40_5);
			List<Result> resultList_40_5 = dManager.getLocalizationCollection(db_Dstar_40_5);

			DB db_Dstar_40_6 = mongo.getDB(db_40_6);
			List<Result> resultList_40_6 = dManager.getLocalizationCollection(db_Dstar_40_6);

			DB db_Dstar_40_7 = mongo.getDB(db_40_7);
			List<Result> resultList_40_7 = dManager.getLocalizationCollection(db_Dstar_40_7);
			

			double t1 = wizeFlII.getThreshold(resultList_40_1);
			double t2 = wizeFlII.getThreshold(resultList_40_2);
			double t3 = wizeFlII.getThreshold(resultList_40_3);
			double t4 = wizeFlII.getThreshold(resultList_40_4);
			double t5 = wizeFlII.getThreshold(resultList_40_5);
			double t6 = wizeFlII.getThreshold(resultList_40_6);
			double t7 = wizeFlII.getThreshold(resultList_40_7);

			for(int i=0; i<resultList_40_1.size(); i++){
				Result result1 = resultList_40_1.get(i);
				Result result2 = resultList_40_2.get(i);
				Result result3 = resultList_40_3.get(i);
				Result result4 = resultList_40_4.get(i);
				Result result5 = resultList_40_5.get(i);
				Result result6 = resultList_40_6.get(i);
				Result result7 = resultList_40_7.get(i);
				
				if((dManager.isCovered(db_Dstar_40_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Dstar_40_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Dstar_40_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 ) ||
						(dManager.isCovered(db_Dstar_40_4, new Double(result4.getStatementId())) && new Double(result4.getSuspiciousness()) < t4 ) ||
						(dManager.isCovered(db_Dstar_40_5, new Double(result5.getStatementId())) && new Double(result5.getSuspiciousness()) < t5 ) ||
						(dManager.isCovered(db_Dstar_40_6, new Double(result6.getStatementId())) && new Double(result6.getSuspiciousness()) < t6 ) ||
						(dManager.isCovered(db_Dstar_40_7, new Double(result7.getStatementId())) && new Double(result7.getSuspiciousness()) < t7 )
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
					dManager.updateSuspiciousness(db_Dstar_40_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_40_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_40_3, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_40_4, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_40_5, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_40_6, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_40_7, id, suspiciousness);

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
		
			dManager.exportToTextDoc(db_Dstar_40_1, docFile1);
			dManager.exportToTextDoc(db_Dstar_40_2, docFile2);
			dManager.exportToTextDoc(db_Dstar_40_3, docFile3);
			dManager.exportToTextDoc(db_Dstar_40_4, docFile4);
			dManager.exportToTextDoc(db_Dstar_40_5, docFile5);
			dManager.exportToTextDoc(db_Dstar_40_6, docFile6);
			dManager.exportToTextDoc(db_Dstar_40_7, docFile7);
		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
		
	}

	private static void mri_Dstar_35() {
		MRI_I wizeFlII = new MRI_I("dstar", 3);

		String db_35_1 = "CodeTest_Dstar_35_1";
		String db_35_2 = "CodeTest_Dstar_35_2";
		String db_35_3 = "CodeTest_Dstar_35_3";
				
		//failed group spectrum 수집 - 완료
		//similarity 분석 - mapReduce with weight
		
		
		//suspiciousness
		List<TestCaseInfo> testCaseInfoList1 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList2 = new ArrayList<TestCaseInfo>();
		List<TestCaseInfo> testCaseInfoList3 = new ArrayList<TestCaseInfo>();

		
		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Dstar_35_1 = mongo.getDB(db_35_1);
			List<Result> resultList_35_1 = dManager.getLocalizationCollection(db_Dstar_35_1);
			
			DB db_Dstar_35_2 = mongo.getDB(db_35_2);
			List<Result> resultList_35_2 = dManager.getLocalizationCollection(db_Dstar_35_2);

			DB db_Dstar_35_3 = mongo.getDB(db_35_3);
			List<Result> resultList_35_3 = dManager.getLocalizationCollection(db_Dstar_35_3);
			
			double t1 = wizeFlII.getThreshold(resultList_35_1);
			double t2 = wizeFlII.getThreshold(resultList_35_2);
			double t3 = wizeFlII.getThreshold(resultList_35_3);

			for(int i=0; i<resultList_35_1.size(); i++){
				Result result1 = resultList_35_1.get(i);
				Result result2 = resultList_35_2.get(i);
				Result result3 = resultList_35_3.get(i);
				
				
				if(	(dManager.isCovered(db_Dstar_35_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Dstar_35_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Dstar_35_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 )
					) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness())) / 3;
					dManager.updateSuspiciousness(db_Dstar_35_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_35_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Dstar_35_3, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_35_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_35_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_35_3.csv";
			
			dManager.exportToTextDoc(db_Dstar_35_1, docFile1);
			dManager.exportToTextDoc(db_Dstar_35_2, docFile2);
			dManager.exportToTextDoc(db_Dstar_35_3, docFile3);

		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
		
	}

	private static void mri_tarantula_55() {
		MRI_I wizeFlII = new MRI_I("tarantula", 8);

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

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Tarantula_55_1 = mongo.getDB(db_55_1);
			List<Result> resultList_55_1 = dManager.getLocalizationCollection(db_Tarantula_55_1);
			
			DB db_Tarantula_55_2 = mongo.getDB(db_55_2);
			List<Result> resultList_55_2 = dManager.getLocalizationCollection(db_Tarantula_55_2);

			DB db_Tarantula_55_3 = mongo.getDB(db_55_3);
			List<Result> resultList_55_3 = dManager.getLocalizationCollection(db_Tarantula_55_3);

			DB db_Tarantula_55_4 = mongo.getDB(db_55_4);
			List<Result> resultList_55_4 = dManager.getLocalizationCollection(db_Tarantula_55_4);
			
			DB db_Tarantula_55_5 = mongo.getDB(db_55_5);
			List<Result> resultList_55_5 = dManager.getLocalizationCollection(db_Tarantula_55_5);

			DB db_Tarantula_55_6 = mongo.getDB(db_55_6);
			List<Result> resultList_55_6 = dManager.getLocalizationCollection(db_Tarantula_55_6);

			DB db_Tarantula_55_7 = mongo.getDB(db_55_7);
			List<Result> resultList_55_7 = dManager.getLocalizationCollection(db_Tarantula_55_7);
			
			DB db_Tarantula_55_8 = mongo.getDB(db_55_8);
			List<Result> resultList_55_8 = dManager.getLocalizationCollection(db_Tarantula_55_8);

			double t1 = wizeFlII.getThreshold(resultList_55_1);
			double t2 = wizeFlII.getThreshold(resultList_55_2);
			double t3 = wizeFlII.getThreshold(resultList_55_3);
			double t4 = wizeFlII.getThreshold(resultList_55_4);
			double t5 = wizeFlII.getThreshold(resultList_55_5);
			double t6 = wizeFlII.getThreshold(resultList_55_6);
			double t7 = wizeFlII.getThreshold(resultList_55_7);
			double t8 = wizeFlII.getThreshold(resultList_55_8);

			for(int i=0; i<resultList_55_1.size(); i++){
				Result result1 = resultList_55_1.get(i);
				Result result2 = resultList_55_2.get(i);
				Result result3 = resultList_55_3.get(i);
				Result result4 = resultList_55_4.get(i);
				Result result5 = resultList_55_5.get(i);
				Result result6 = resultList_55_6.get(i);
				Result result7 = resultList_55_7.get(i);
				Result result8 = resultList_55_8.get(i);
				
				if((dManager.isCovered(db_Tarantula_55_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_55_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Tarantula_55_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 ) ||
						(dManager.isCovered(db_Tarantula_55_4, new Double(result4.getStatementId())) && new Double(result4.getSuspiciousness()) < t4 ) ||
						(dManager.isCovered(db_Tarantula_55_5, new Double(result5.getStatementId())) && new Double(result5.getSuspiciousness()) < t5 ) ||
						(dManager.isCovered(db_Tarantula_55_6, new Double(result6.getStatementId())) && new Double(result6.getSuspiciousness()) < t6 ) ||
						(dManager.isCovered(db_Tarantula_55_7, new Double(result7.getStatementId())) && new Double(result7.getSuspiciousness()) < t7 ) ||
						(dManager.isCovered(db_Tarantula_55_8, new Double(result8.getStatementId())) && new Double(result8.getSuspiciousness()) < t8 )
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
					dManager.updateSuspiciousness(db_Tarantula_55_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_3, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_4, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_5, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_6, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_7, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_55_8, id, suspiciousness);


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

			dManager.exportToTextDoc(db_Tarantula_55_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_55_2, docFile2);
			dManager.exportToTextDoc(db_Tarantula_55_3, docFile3);
			dManager.exportToTextDoc(db_Tarantula_55_4, docFile4);
			dManager.exportToTextDoc(db_Tarantula_55_5, docFile5);
			dManager.exportToTextDoc(db_Tarantula_55_6, docFile6);
			dManager.exportToTextDoc(db_Tarantula_55_7, docFile7);
			dManager.exportToTextDoc(db_Tarantula_55_8, docFile8);
		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
	}
	
	private static void mri_tarantula_40() {
		MRI_I wizeFlII = new MRI_I("tarantula", 7);

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

		Mongo mongo = null;
		try {
			mongo = dManager.getConnection();
			DB db_Tarantula_40_1 = mongo.getDB(db_40_1);
			List<Result> resultList_40_1 = dManager.getLocalizationCollection(db_Tarantula_40_1);
			
			DB db_Tarantula_40_2 = mongo.getDB(db_40_2);
			List<Result> resultList_40_2 = dManager.getLocalizationCollection(db_Tarantula_40_2);

			DB db_Tarantula_40_3 = mongo.getDB(db_40_3);
			List<Result> resultList_40_3 = dManager.getLocalizationCollection(db_Tarantula_40_3);

			DB db_Tarantula_40_4 = mongo.getDB(db_40_4);
			List<Result> resultList_40_4 = dManager.getLocalizationCollection(db_Tarantula_40_4);
			
			DB db_Tarantula_40_5 = mongo.getDB(db_40_5);
			List<Result> resultList_40_5 = dManager.getLocalizationCollection(db_Tarantula_40_5);

			DB db_Tarantula_40_6 = mongo.getDB(db_40_6);
			List<Result> resultList_40_6 = dManager.getLocalizationCollection(db_Tarantula_40_6);

			DB db_Tarantula_40_7 = mongo.getDB(db_40_7);
			List<Result> resultList_40_7 = dManager.getLocalizationCollection(db_Tarantula_40_7);
			

			double t1 = wizeFlII.getThreshold(resultList_40_1);
			double t2 = wizeFlII.getThreshold(resultList_40_2);
			double t3 = wizeFlII.getThreshold(resultList_40_3);
			double t4 = wizeFlII.getThreshold(resultList_40_4);
			double t5 = wizeFlII.getThreshold(resultList_40_5);
			double t6 = wizeFlII.getThreshold(resultList_40_6);
			double t7 = wizeFlII.getThreshold(resultList_40_7);

			for(int i=0; i<resultList_40_1.size(); i++){
				Result result1 = resultList_40_1.get(i);
				Result result2 = resultList_40_2.get(i);
				Result result3 = resultList_40_3.get(i);
				Result result4 = resultList_40_4.get(i);
				Result result5 = resultList_40_5.get(i);
				Result result6 = resultList_40_6.get(i);
				Result result7 = resultList_40_7.get(i);
				
				if((dManager.isCovered(db_Tarantula_40_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_40_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Tarantula_40_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 ) ||
						(dManager.isCovered(db_Tarantula_40_4, new Double(result4.getStatementId())) && new Double(result4.getSuspiciousness()) < t4 ) ||
						(dManager.isCovered(db_Tarantula_40_5, new Double(result5.getStatementId())) && new Double(result5.getSuspiciousness()) < t5 ) ||
						(dManager.isCovered(db_Tarantula_40_6, new Double(result6.getStatementId())) && new Double(result6.getSuspiciousness()) < t6 ) ||
						(dManager.isCovered(db_Tarantula_40_7, new Double(result7.getStatementId())) && new Double(result7.getSuspiciousness()) < t7 )
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
					dManager.updateSuspiciousness(db_Tarantula_40_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_40_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_40_3, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_40_4, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_40_5, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_40_6, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_40_7, id, suspiciousness);

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
		
			dManager.exportToTextDoc(db_Tarantula_40_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_40_2, docFile2);
			dManager.exportToTextDoc(db_Tarantula_40_3, docFile3);
			dManager.exportToTextDoc(db_Tarantula_40_4, docFile4);
			dManager.exportToTextDoc(db_Tarantula_40_5, docFile5);
			dManager.exportToTextDoc(db_Tarantula_40_6, docFile6);
			dManager.exportToTextDoc(db_Tarantula_40_7, docFile7);
		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
		}
		
	
	}
	
	private static void mri_tarantula_35() {
		MRI_I wizeFlII = new MRI_I("tarantula", 3);

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
			mongo = dManager.getConnection();
			DB db_Tarantula_35_1 = mongo.getDB(db_35_1);
			List<Result> resultList_35_1 = dManager.getLocalizationCollection(db_Tarantula_35_1);
			
			DB db_Tarantula_35_2 = mongo.getDB(db_35_2);
			List<Result> resultList_35_2 = dManager.getLocalizationCollection(db_Tarantula_35_2);

			DB db_Tarantula_35_3 = mongo.getDB(db_35_3);
			List<Result> resultList_35_3 = dManager.getLocalizationCollection(db_Tarantula_35_3);
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
			
			double t1 = wizeFlII.getThreshold(resultList_35_1);
			double t2 = wizeFlII.getThreshold(resultList_35_2);
			double t3 = wizeFlII.getThreshold(resultList_35_3);

			for(int i=0; i<resultList_35_1.size(); i++){
				Result result1 = resultList_35_1.get(i);
				Result result2 = resultList_35_2.get(i);
				Result result3 = resultList_35_3.get(i);
				
				
				if(	(dManager.isCovered(db_Tarantula_35_1, new Double(result1.getStatementId())) && new Double(result1.getSuspiciousness()) < t1 ) ||
						(dManager.isCovered(db_Tarantula_35_2, new Double(result2.getStatementId())) && new Double(result2.getSuspiciousness()) < t2 ) ||
						(dManager.isCovered(db_Tarantula_35_3, new Double(result3.getStatementId())) && new Double(result3.getSuspiciousness()) < t3 )
					) {
					
					double id = new Double(result1.getStatementId());

					double suspiciousness = ( new Double(result1.getSuspiciousness()) +
							new Double(result2.getSuspiciousness()) +
							new Double(result3.getSuspiciousness())) / 3;
					dManager.updateSuspiciousness(db_Tarantula_35_1, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_35_2, id, suspiciousness);
					dManager.updateSuspiciousness(db_Tarantula_35_3, id, suspiciousness);

				}
				
			}
			System.out.println("reporting...");
			
			String docFile1 = "/home/wizehack/exp/mri/mri_35_1.csv";
			String docFile2 = "/home/wizehack/exp/mri/mri_35_2.csv";
			String docFile3 = "/home/wizehack/exp/mri/mri_35_3.csv";
			
			dManager.exportToTextDoc(db_Tarantula_35_1, docFile1);
			dManager.exportToTextDoc(db_Tarantula_35_2, docFile2);
			dManager.exportToTextDoc(db_Tarantula_35_3, docFile3);

		} finally {
			System.out.println("Compeleted");
			dManager.closeConnection(mongo);
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

}

class TestCaseInfo { 
	int tcId;
	double wieght;
}
