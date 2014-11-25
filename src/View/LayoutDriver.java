package View;

import Controller.CoSELayoutManager;
import Model.GraphMLParser;

public class LayoutDriver 
{
	public static void main(String [] args) throws Exception
	{
		String path = "C:\\Users\\Administrator\\Desktop\\4NodeTest.xml";
		GraphMLParser parser = new GraphMLParser(path);
		parser.readGraphML();
		CoSELayoutManager layoutManager = new CoSELayoutManager();
		layoutManager.createTopology(parser.getRootGraph(), parser.getEdges());
		layoutManager.runLayout();
	}
}
