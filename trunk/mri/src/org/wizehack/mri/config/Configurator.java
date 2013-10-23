package org.wizehack.mri.config;

public class Configurator {
	
	private static Configurator conf = null;
	private static String projectName = null;
	private static String ip = null;
	
	private Configurator(){
		
	}
	
	public synchronized static Configurator getInstance(){
		if(conf == null){
			conf = new Configurator();
		}
		
		return conf;
	}

	public String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		Configurator.projectName = projectName;
	}

	public static String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		Configurator.ip = ip;
	}
	
	
}
