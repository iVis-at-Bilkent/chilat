package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class GraphMLParser 
{
	private String path;
	private CompoundNodeModel rootGraph;
	private List<EdgeModel> edges;
	
	public GraphMLParser(String path)
	{
		this.path = path;
	}
	
	public void readGraphML() throws Exception
	{
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser graphMLParser = parserFactory.newSAXParser();
		GraphParserHandle parserHandle = new GraphParserHandle();
		graphMLParser.parse(path, parserHandle);
		this.rootGraph = parserHandle.getRootGraph();
		this.edges = parserHandle.getEdges();
	}
	

	public CompoundNodeModel getRootGraph() {
		return rootGraph;
	}

	public void setRootGraph(CompoundNodeModel rootGraph) {
		this.rootGraph = rootGraph;
	}

	public List<EdgeModel> getEdges() {
		return edges;
	}

	public void setEdges(List<EdgeModel> edges) {
		this.edges = edges;
	}
	
}
