package org.wizehack.mri.fl;

import java.util.List;

import org.wizehack.mri.repo.Result;

public class MRI_I {
	private LocalizationTechnique flTechnique = null;
	private int numberOfGroups;
	
	public MRI_I(String name, int numberOfGroups) {
		super();
//		this.flTechnique = flTechnique;
		this.numberOfGroups = numberOfGroups;
	}
	
	public double getSuspiciousness(int ith, boolean condition, double[] suspiciousnessGroup){
		double suspiciousness = 0;
		double sum=0;
		if(condition){
			
			for(int i=0; i<suspiciousnessGroup.length; i++){
				sum = sum + suspiciousnessGroup[i];
			}
			
			suspiciousness = sum / suspiciousnessGroup.length;
			
		} else {
			suspiciousness = suspiciousnessGroup[ith];
		}
		
		return suspiciousness;
	}
	
	public double getThreshold(List<Result> resultList){
		double threshold = 0.0;
		
		double sum = 0;
		for(int i=0; i<resultList.size(); i++){
			sum = sum + new Double(resultList.get(i).getSuspiciousness());
		}
		
		threshold = sum/resultList.size();
		
		return threshold;
	}
}
