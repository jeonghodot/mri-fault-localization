package org.wizehack.mri.CoverageDataParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wizehack.mri.repo.Coverage;
import org.wizehack.mri.repo.DataManager;

/**
 * The XMLFileReader represents java code coverage from a xml document exported from the EclEmma. 
 * The instance of the class loads the xml document and assigns the code coverage data to the instance of LineList class.   
 * @author hsyoun (wizehack@gmail.com)
 */

public class CoverageFileReader {
	
	private String sourceFolder;
	private int tcId;
	private boolean passed;
	private DataManager dManager;
	
	public CoverageFileReader(DataManager dManager){
		this.dManager = dManager;
	}
	
	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		File file = new File(sourceFolder);
		this.sourceFolder = sourceFolder + file.separator;
	}
	
	
	public int getTcId() {
		return tcId;
	}

	public void setTcId(int tcId) {
		this.tcId = tcId;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	/**
	 * 
	 * @return the coverage of source code 
	 */
	public List<Coverage> getCoverage(File xmlFile){
		
		XMLTreeTraverser xtt = new XMLTreeTraverser(xmlFile);
		NodeList groupList = xtt.getNodeList("group");
		Element project = (Element) groupList.item(0);
		String projectName = project.getAttribute("name");	
		NodeList subGroupList = project.getElementsByTagName("group");	
		List<Coverage> coverageList = new ArrayList<Coverage>();
	
		int point = this.sourceFolder.lastIndexOf(projectName);
		String sourceFolderRoot = this.sourceFolder.substring(0, point);

		for(int x = 0; x < subGroupList.getLength(); x++){
			Element subGroup = (Element) subGroupList.item(x);
			String sourcePath = sourceFolderRoot + projectName + File.separator + subGroup.getAttribute("name");
			
//			NodeList nodeList = xtt.getNodeList("package");
			NodeList nodeList = subGroup.getElementsByTagName("package");	
			
			for(int i=0; i<nodeList.getLength(); i++){
				
				Element newRoot = (Element) nodeList.item(i);
//				String pkgPath = this.sourceFolder + newRoot.getAttribute("name");
				String pkgPath = sourcePath + File.separator + newRoot.getAttribute("name");
				
//				System.out.println("pkgPath: " + pkgPath);
				NodeList filteredNodeList = newRoot.getElementsByTagName("sourcefile");	
				
				for(int j=0; j<filteredNodeList.getLength(); j++){
					
					Element sourceFileElement = (Element) filteredNodeList.item(j);
//					System.out.println(sourceFileElement.getAttribute("name"));
					List<Coverage> covList = scanLine(sourceFileElement, pkgPath);
					
					for(int k=0; k<covList.size(); k++){
						Coverage cov = covList.get(k);
						double statementId=0;
						try {
							statementId = dManager.getStatementId(cov.getFile(), cov.getNumber());
						} catch (NullPointerException e1){
							e1.printStackTrace();
							k--;
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continue;
						}
						cov.setStatementId(statementId);
						coverageList.add(cov);
					}// for k					
				}// for j
			}// for i

		}// for x
		
		return coverageList;
	}
	
	private List<Coverage> scanLine(Element element, String pkgPath){
		File file = new File(pkgPath);
		String srcFile = pkgPath + file.separator + element.getAttribute("name");
		
		NodeList cNodeList = element.getChildNodes();
		List<Coverage> coverageList = new ArrayList<Coverage>();
		
		for(int j=0; j < cNodeList.getLength(); j++){
			Node cNode = cNodeList.item(j);
			if(cNode.getNodeName().matches("line")){
				Element cElement = (Element) cNode; 
				
				if(cElement.getAttribute("mi").matches("0") ){
					Coverage coverage = new Coverage();
					coverage.setFile(srcFile);
					coverage.setPassed(passed);
					coverage.setTcId(tcId);
					coverage.setNumber(new Integer(cElement.getAttribute("nr")));
					coverageList.add(coverage);
				} 
			} //end if
		}// end for i
		
		return coverageList;
	}
	
	/*
	public static void main(String[] args){
		
		File xmlFile = new File("/home/hsyoun/workspace/MRI-Demo/Data/6.xml");
		CoverageFileReader cfr = new CoverageFileReader(xmlFile);
				
		List <SourceFile> sd = cfr.getCoverage();
		
		for(int i=0; i<sd.size(); i++){
			SourceFile sf = sd.get(i);
			
//			System.out.println(sf.getName() + "---------------------------");
			for(int j=0; j<sf.size(); j++){
				System.out.println(sf.get(j).getLineNumber() + ": " + sf.get(j).isCovered());
			}
		}
		
	}
	*/
}
