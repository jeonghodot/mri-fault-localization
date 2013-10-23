package org.wizehack.mri.io;

import java.io.File;
import java.util.ArrayList;

public class FileListReader {
	private static ArrayList<String> fileList = null;
	private String extensionType = null;
	
	
	public FileListReader(){
		fileList = new ArrayList<String>();
	}
	
	public String getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(String extensionType) {
		if(extensionType.indexOf(".") > -1){
			this.extensionType = extensionType.substring(1);
		} else {
			this.extensionType = extensionType;
		}
	}

	public ArrayList<String> readFiles(File file) {
		
		if (file == null || !file.exists()) {
			return null;
		}

		if (file.isDirectory()) {
			String[] files = file.list();
			
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					readFiles(new File(file, files[i]));
				}
			}
			
		} else {
			try {
				String name = file.getName().toLowerCase();
				if(name.endsWith("." + extensionType)) {
					String fileCanonicalPath = file.getCanonicalPath();
					fileList.add(fileCanonicalPath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return fileList;
	}
	
	public static void main(String[] args) {
		FileListReader fileListReader = new FileListReader();
		fileListReader.setExtensionType(".xml");
//		ArrayList<String> fList = new ArrayList<String>();
//		String path = "/home/hsyoun/workspace/MRI-Demo/Data";
//		
//		fList = fileListReader.readFiles(new File(path));
//		
//		for(int i=0; i<fList.size(); i++){
//			System.out.println(fList.get(i));
//		}
	}
	
}
