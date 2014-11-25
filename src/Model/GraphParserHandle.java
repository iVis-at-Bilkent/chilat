package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Istemi Bahceci
 *
 */
public class GraphParserHandle extends DefaultHandler 
{
	//GraphML  specific constants
	private final String GRAPH = "graph";
	private final String NODE = "node";
	private final String EDGE = "edge";
	private final String X = "x";
	private final String Y = "y";
	private final String WIDTH = "width";
	private final String HEIGHT = "height";
	private final String SOURCE = "source";
	private final String TARGET = "target";
	private final String DATA = "data";
	private final String TEXT = "text";
	private final String ID = "id";
	private final String KEY = "key";

	//Data specific flags and lists for parsing
	private boolean isX,isY,isWidth,isHeight,isText;
	private BaseModel currentModel;
	private CompoundNodeModel rootGraph;
	private Stack<CompoundNodeModel> graphStack;
	private List<EdgeModel> edges;

	
	public GraphParserHandle()
	{
		super();
		this.graphStack = new Stack<CompoundNodeModel>();
		this.edges = new ArrayList<EdgeModel>();
		this.rootGraph = new CompoundNodeModel("root");
		this.graphStack.push(rootGraph);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		if (qName.equalsIgnoreCase(GRAPH)) 
		{
			/*CompoundNodeModel subGraph = new CompoundNodeModel(attributes.getValue("id"));
			this.graphStack.push(subGraph);*/
		}
		else if (qName.equalsIgnoreCase(NODE)) 
		{
			currentModel = new CompoundNodeModel(attributes.getValue("id"));
			this.graphStack.push(((CompoundNodeModel)currentModel));
		}
		else if (qName.equalsIgnoreCase(EDGE))
		{
			currentModel = new EdgeModel(attributes.getValue("id"));
			((EdgeModel)currentModel).setSourceID(attributes.getValue(SOURCE));
			((EdgeModel)currentModel).setTargetID(attributes.getValue(TARGET));
		}
		else if(qName.equalsIgnoreCase(DATA))
		{
			switch (attributes.getValue(KEY)) 
			{
				case X: isX = true; break;
				case Y: isY = true; break;
				case WIDTH: isWidth = true; break;
				case HEIGHT: isHeight = true; break;
				case TEXT: isText = true; break;
				default:
					break;
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if (qName.equalsIgnoreCase(GRAPH)) 
		{
			//pop last graph
			/*CompoundNodeModel subGraph = this.graphStack.pop();
			if (!this.graphStack.isEmpty()) {
				this.graphStack.peek().addChildren(subGraph);
			}
			else
			{
				this.rootGraph = subGraph;
				this.rootGraph.setId("root");
			}*/
		}
		else if (qName.equalsIgnoreCase(NODE)) 
		{
			CompoundNodeModel subGraph = this.graphStack.pop();
			subGraph.setParent((CompoundNodeModel)this.graphStack.peek());
			this.graphStack.peek().addChildren(subGraph);
		}
		else if (qName.equalsIgnoreCase(EDGE))
		{
			this.edges.add((EdgeModel)(currentModel));
		}   
	}
	
	
    @Override
    public void characters(char ch[], int start, int length) throws SAXException 
    {
    	if (currentModel instanceof NodeModel) 
    	{
        	if (isX) 
        	{
        		((NodeModel)currentModel).setX(Integer.parseInt(new String(ch,start,length)));
    		}
        	else if (isY) 
        	{
        		((NodeModel)currentModel).setY(Integer.parseInt(new String(ch,start,length)));
			}
        	else if (isWidth) 
        	{
        		((NodeModel)currentModel).setWidth(Integer.parseInt(new String(ch,start,length)));
			}
        	else if (isHeight) 
        	{
        		((NodeModel)currentModel).setHeight(Integer.parseInt(new String(ch,start,length)));
			}
		}
    	if (isText) 
    	{
        	currentModel.setLabel(new String(ch,start,length));
		}
    	this.resetFlags();
    }
    
    //Reset all flags
    public void resetFlags()
    {
    	this.isX = this.isY = this.isWidth = this.isHeight = this.isText = false;
    }

    /**************************	Getter and Setters *******************************************/
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
