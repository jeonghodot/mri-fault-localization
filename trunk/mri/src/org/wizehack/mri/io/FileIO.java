package org.wizehack.mri.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileIO {
	
	private String outFile;
	private FileWriter fw;
	
	public String getOutFile() {
		return outFile;
	}

	public void setOutFile(String outFile) {
		this.outFile = outFile;
		File file = new File(outFile);
		file.delete();
	}

	public void write(List<String> content){
		try {
			fw = new FileWriter(outFile);
			
			for(int i=0; i<content.size(); i++){
				fw.write(content.get(i) + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
