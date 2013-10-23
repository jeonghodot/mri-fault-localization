package org.wizehack.mri.repo;

import java.util.NoSuchElementException;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.mongodb.Mongo;

public class MongoManager {
	
	private static MongoManager mongoManager = null;
	
	private static ObjectPool<Mongo> pool;
	
	private MongoManager(){
		
	}
	
	public synchronized static MongoManager getInstance(){
		if(mongoManager == null)
			mongoManager = new MongoManager();
		return mongoManager;
	}
	
	public void createPool(final String host){
		PoolableObjectFactory factory = new BasePoolableObjectFactory() {
			@Override
			public Object makeObject() throws Exception {
				Mongo mongo = new Mongo(host);
				return mongo;
			}

			@Override
			public void activateObject(Object arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void destroyObject(Object arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void passivateObject(Object arg0) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean validateObject(Object arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		pool = new GenericObjectPool(factory);
	}
	
	public Mongo getConnection(){
		Mongo mongo = null;
		try {
			mongo = (Mongo) pool.borrowObject();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mongo;
	}
	
	public void closeConnection(Mongo mongo){
		try {
			if(mongo != null)
				pool.returnObject(mongo);
		} catch (Exception e2){
			e2.getStackTrace();
		}
	}
	
	public void destroyPool(){
		mongoManager = null;
		pool = null;
		System.gc();
	}

}
