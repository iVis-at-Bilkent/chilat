package Controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ivis.layout.LEdge;
import org.ivis.layout.LGraph;
import org.ivis.layout.LGraphManager;
import org.ivis.layout.LNode;
import org.ivis.layout.Layout;
import org.ivis.layout.LayoutOptionsPack;
import org.ivis.layout.LayoutOptionsPack.CoSE;
import org.ivis.layout.LayoutOptionsPack.General;
import org.ivis.layout.cose.CoSELayout;
import org.ivis.util.RectangleD;

import Model.CompoundNodeModel;
import Model.EdgeModel;
import Model.NodeModel;
import Model.NodeState;
import Util.Vector2D;

public class CoSELayoutManager 
{
	private HashMap<String, LNode> v_To_l_Map;
	private HashMap<String, NodeModel> l_To_v_Map;
	private ArrayList<double []> minMaxTotalForceList;
	
	private CoSELayout layout;
	private LGraph lRoot;
	private CompoundNodeModel vRoot;
	private boolean animateOn = false;
	private General generalOptions;
	private CoSE coseOptions;
	
	public CoSELayoutManager()
	{
		this.v_To_l_Map = new HashMap<>();
		this.l_To_v_Map = new HashMap<>();
		this.minMaxTotalForceList = new ArrayList<double[]>();
		
		//init the layout
		this.layout = new CoSELayout();
		
		//Set animation on layout to true
		generalOptions = LayoutOptionsPack.getInstance().getGeneral();
		coseOptions = LayoutOptionsPack.getInstance().getCoSE();
		generalOptions.animationDuringLayout = this.animateOn;
	}
	
	public void createTopology(CompoundNodeModel rootGraph, List<EdgeModel> edgeList)
	{
		LGraphManager graphMgr = this.layout.getGraphManager();
		lRoot = graphMgr.addRoot();
		this.vRoot = rootGraph;
		
		//Create corresponding lNodes and lEdges
		this.createLNode(rootGraph, null, layout);
		this.createLEdges(edgeList, layout);
	}
	
	public void createLNode(NodeModel vNode, NodeModel vParent, Layout layout )
	{
		LNode lNode = layout.newNode(vNode);
		lNode.label = vNode.getId();
		LGraph rootLGraph = layout.getGraphManager().getRoot();
		
		//Copy geometry
		lNode.setLocation(vNode.getBounds().getX(), vNode.getBounds().getY());
		
		//Create mapping between l and v level nodes
		this.v_To_l_Map.put(vNode.getId(), lNode);
		this.l_To_v_Map.put(lNode.label, vNode);
		
		if (vParent != null) 
		{
			LNode lParent = this.v_To_l_Map.get(vParent.getId());
			lParent.getChild().add(lNode);
		}
		else
		{
			rootLGraph.add(lNode);
		}
		
		if (vNode instanceof CompoundNodeModel && ((CompoundNodeModel) vNode).getChildren().size() > 0 ) 
		{
			layout.getGraphManager().add(layout.newGraph(null), lNode);
			for (NodeModel childNode : ((CompoundNodeModel)vNode).getChildren()) 
			{
				this.createLNode(childNode, vNode, layout);
			}
		}

		lNode.setWidth(vNode.getBounds().width);
		lNode.setHeight(vNode.getBounds().height);
		
	}
	
	public void createLEdges(List<EdgeModel> edges, Layout layout)
	{
		for (EdgeModel edge : edges ) 
		{
			LEdge lEdge = layout.newEdge(null);
			LNode sourceLNode = v_To_l_Map.get(edge.getSourceID());
			LNode targetLNode = v_To_l_Map.get(edge.getTargetID());
			this.layout.getGraphManager().add(lEdge, sourceLNode, targetLNode);
		}
	}
	
	public RectangleD getKeyFrameGeometry(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getNodeGeometry();
	}
	
	public Vector2D getTotalForce(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getTotalForceVector();
	}
	
	public double[] getMinMaxTotalForceForKeyFrame(int keyFrameIndex)
	{
		return this.minMaxTotalForceList.get(keyFrameIndex);
	}
	
	public NodeModel getParent(String id)
	{
		return this.l_To_v_Map.get(id);
	}
	
	public Rectangle getFinalGeometry(String id)
	{
		return this.l_To_v_Map.get(id).getBounds();
	}
	
	public int getTotalKeyFrameCount()
	{
		Set<String> keySet = l_To_v_Map.keySet();
		return this.l_To_v_Map.get(keySet.toArray()[0]).getAnimationStates().size();
	}
	
	public void clearKeyFrames()
	{
		Set<String> keySet = l_To_v_Map.keySet();
		for (String key : keySet) 
		{
			this.l_To_v_Map.get(key).getAnimationStates().clear();
		}
		this.minMaxTotalForceList.clear();
	}
	
	public void updateAllRelativePositions()
	{
		for (NodeModel node : this.vRoot.getChildren()) 
		{
			updateRelativePositionOfANode(node);
		}
	}
	
	public void updateRelativePositionOfANode(NodeModel node)
	{
		if (node instanceof CompoundNodeModel) 
		{
			for (NodeModel childNode : ((CompoundNodeModel) node).getChildren()) 
			{
				updateRelativePositionOfANode(childNode);
			}
			
			if (!this.animateOn) 
			{
				Rectangle parentRect = node.getParent().getBounds();
				Rectangle nodeRect = node.getBounds();
				double newX = -parentRect.getX() + nodeRect.getX();
				double newY = -parentRect.getY() + nodeRect.getY();
				node.getBounds().x = (int) newX;
				node.getBounds().y = (int) newY;
			}
			else
			{
				for (int i = 0; i < this.getTotalKeyFrameCount(); i++) 
				{
					RectangleD parentRect = node.getParent().getAnimationStates().get(i).getNodeGeometry();
					RectangleD nodeRect = node.getAnimationStates().get(i).getNodeGeometry();
					double newX = -parentRect.getX() + nodeRect.getX();
					double newY = -parentRect.getY() + nodeRect.getY();
					node.getAnimationStates().get(i).getNodeGeometry().setX(newX);
					node.getAnimationStates().get(i).getNodeGeometry().setY(newY);
				}
			}
		}
	}
	
	public void generateMinMaxTotalForceList()
	{
		for (int i = 0; i < this.getTotalKeyFrameCount(); i++) 
		{
			double [] minMax = new double[2];
			minMax[0] = Integer.MAX_VALUE;
			minMax[1] = Integer.MIN_VALUE;
					
			for (String key : this.l_To_v_Map.keySet()) 
			{
				NodeState tmpState = this.l_To_v_Map.get(key).getAnimationStates().get(i);
				
				//Maximum
				if (minMax[1] < tmpState.getTotalForceVector().length()) 
				{
					minMax[1] = tmpState.getTotalForceVector().length();
				}
				
				//Minimum
				if (minMax[0] > tmpState.getTotalForceVector().length()) 
				{
					minMax[0] = tmpState.getTotalForceVector().length();
				}
			}
			this.minMaxTotalForceList.add(minMax);
		}
	}
	
	public void runLayout()
	{
		this.clearKeyFrames();
		this.layout.runLayout();
		updateAllRelativePositions();
		this.generateMinMaxTotalForceList();
		
	}

	public boolean isAnimateOn() {
		return animateOn;
	}

	public void setAnimateOn(boolean animateOn) {
		this.animateOn = animateOn;
		this.generalOptions.animationDuringLayout = this.animateOn;
	}
	
	public CoSE getCoseOptionsPack()
	{
		return this.coseOptions;
	}
}
