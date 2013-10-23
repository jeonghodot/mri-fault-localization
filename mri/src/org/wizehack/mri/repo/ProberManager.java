package org.wizehack.mri.repo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProberManager {
	private ExecutorService threadPool;
	private static int MAX_POOL_SIZE = 8;
	private int numberOfProbers;
	public ProberManager(int poolSize){
		
		if(poolSize > MAX_POOL_SIZE){
			threadPool = Executors.newFixedThreadPool(MAX_POOL_SIZE);
			numberOfProbers = MAX_POOL_SIZE;
		} else {
			threadPool = Executors.newFixedThreadPool(poolSize);
			numberOfProbers = poolSize;
		}
	}

	public int getNumberOfProbers() {
		// TODO Auto-generated method stub
		return numberOfProbers;
	}

	public ExecutorService getThreadPool() {
		// TODO Auto-generated method stub
		return this.threadPool;
	}

	public void destroy() {
		threadPool.shutdown();
	}
}
