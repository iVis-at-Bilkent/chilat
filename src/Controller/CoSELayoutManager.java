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
import Model.KeyFrameNodeState;
import Util.Vector2D;

public class CoSELayoutManager 
{
	private HashMap<String, LNode> v_To_l_Map;
	private HashMap<String, NodeModel> l_To_v_Map;
	private ArrayList<Vector2D[]> minMaxTotalForceList;
	
	private CoSELayout layout;
	private LGraph lRoot;
	private CompoundNodeModel vRoot;
	private boolean animateOn = false;
	private General generalOptions;
	private CoSE coseOptions;
	
	//Index for maximum and minimum values of total force and other forces
	private int TOTAL_FORCE_INDEX = 0;
	private int OTHER_FORCE_INDEX = 1;

	
	public CoSELayoutManager()
	{
		this.v_To_l_Map = new HashMap<>();
		this.l_To_v_Map = new HashMap<>();
		this.minMaxTotalForceList = new ArrayList<Vector2D[]>();
		
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
	
	public Vector2D getTotalForceVector(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getTotalForceVector();
	}
	
	public Vector2D	getRepulsionForceVector(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getRepulsionForceVector();
	}
	
	public Vector2D getSpringForceVector(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getSpringForceVector();
	}
	
	public Vector2D getGravityForceVector(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getGravityForceVector();
	}
	
	public Vector2D getDisplVector(String id, int index)
	{
		return this.l_To_v_Map.get(id).getAnimationStates().get(index).getTotalDisplacementVector();
	}
	
	public Vector2D getMinMaxTotalForceForKeyFrame(int keyFrameIndex)
	{
		return this.minMaxTotalForceList.get(keyFrameIndex)[TOTAL_FORCE_INDEX];
	}
	
	public Vector2D getMinMaxOtherForceForKeyFrame(int keyFrameIndex)
	{
		return this.minMaxTotalForceList.get(keyFrameIndex)[OTHER_FORCE_INDEX];
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
			Vector2D [] minMax = new Vector2D[2];
			minMax[TOTAL_FORCE_INDEX] = getMinMaxTotalForceValues(i);
			minMax[OTHER_FORCE_INDEX] = getMinMaxOtherForces(i);
			this.minMaxTotalForceList.add(minMax);
		}
	}
	
	public Vector2D getMinMaxTotalForceValues(int keyFrameIndex)
	{
		//x attribute is minimum, y attribute is maximum
		Vector2D minMax = new Vector2D(Integer.MAX_VALUE, Integer.MIN_VALUE);
		
		for (String key : this.l_To_v_Map.keySet()) 
		{
			KeyFrameNodeState tmpState = this.l_To_v_Map.get(key).getAnimationStates().get(keyFrameIndex);
			
			//Maximum
			if (minMax.getY() < tmpState.getTotalForceVector().length()) 
			{
				minMax.setY(tmpState.getTotalForceVector().length());
			}
			
			//Minimum
			if (minMax.getX() > tmpState.getTotalForceVector().length()) 
			{
				minMax.setX(tmpState.getTotalForceVector().length());
			}
		}
		return minMax;
	}
	
	public Vector2D getMinMaxOtherForces(int keyFrameIndex)
	{
		//x attribute is minimum, y attribute is maximum
		Vector2D minMax = new Vector2D(Integer.MAX_VALUE, Integer.MIN_VALUE);

		for (String key : this.l_To_v_Map.keySet()) 
		{
			KeyFrameNodeState tmpState = this.l_To_v_Map.get(key).getAnimationStates().get(keyFrameIndex);
			
			double repulsionForceMagnitude = tmpState.getRepulsionForceVector().length();
			double springForceMagnitude = tmpState.getSpringForceVector().length();
			double gravityForceMagnitude = tmpState.getGravityForceVector().length();

			//Maximum
			double min,max= 0;
			if (minMax.getY() < (max=Math.max(repulsionForceMagnitude, Math.max(springForceMagnitude, gravityForceMagnitude)))) 
			{
				minMax.setY(max);
			}
			
			//Minimum
			if (minMax.getX() > (min=Math.min(repulsionForceMagnitude, Math.min(springForceMagnitude, gravityForceMagnitude))))
			{
				minMax.setX(min);
			}
		}
		return minMax;
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
