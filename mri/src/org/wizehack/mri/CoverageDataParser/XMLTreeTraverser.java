package org.wizehack.mri.CoverageDataParser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author hsyoun <wizehack@gmail.com>
 *
 */
public class XMLTreeTraverser {

	private File file;

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	public XMLTreeTraverser(File file) {
		
		this.file = file;
		this.dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			this.dBuilder = this.dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.doc = this.dBuilder.parse(this.file);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Returns a NodeList of all the Elements order with a given node name 
	 * @param nodeName node name 
	 * @return node list
	 */
	public NodeList getNodeList(String nodeName) {
		NodeList nodeList = null;
		nodeList = this.doc.getElementsByTagName(nodeName);
		return nodeList;
	}
	

	/**
	 * gets a value of the attribute of node order with index in a given nodeList
	 * @param nodeList node list
	 * @param index index to the nodeList
	 * @param attName attribute name of node
	 * @return attribute value
	 */
	public String getAttribute(NodeList nodeList, int index, String attName) {
		Element element = (Element) nodeList.item(index);
		String attr = element.getAttribute(attName);
		return attr;
	}
	
}
