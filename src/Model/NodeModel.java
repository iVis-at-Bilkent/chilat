package Model;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.ivis.layout.LGraphObject;
import org.ivis.layout.LNode;
import org.ivis.layout.Updatable;
import org.ivis.layout.cose.CoSEGraph;
import org.ivis.layout.cose.CoSENode;
import org.ivis.layout.fd.FDLayoutNode;
import org.ivis.util.RectangleD;

import Util.Vector2D;


public class NodeModel extends BaseModel implements Updatable
{
	private String nodeLabel;
	private Rectangle bounds;
	private CompoundNodeModel parent;
	
	
	protected ArrayList<KeyFrameNodeState> animationStates;
	
	public NodeModel(String id)
	{
		super(id);
		this.bounds = new Rectangle();
		this.animationStates = new ArrayList<KeyFrameNodeState>();
	}

	public String getNodeLabel() {
		return nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public void setX(int x)
	{
		this.bounds.x = x;
	}
	
	public void setY(int y)
	{
		this.bounds.y = y;
	}
	
	public void setWidth(int w)
	{
		this.bounds.width = w;
	}
	
	public void setHeight(int h)
	{
		this.bounds.height = h;
	}
	
	public ArrayList<KeyFrameNodeState> getAnimationStates() {
		return animationStates;
	}

	public void setAnimationStates(ArrayList<KeyFrameNodeState> animationStates) {
		this.animationStates = animationStates;
	}

	public CompoundNodeModel getParent() {
		return parent;
	}

	public void setParent(CompoundNodeModel parent) {
		this.parent = parent;
	}

	public String toString()
	{
		return  "id: " + this.id + " label: " + this.label + " bounds: " + this.bounds.toString();
	}

	@Override
	public void update(LGraphObject lGraphObj) 
	{
        if (lGraphObj instanceof CoSEGraph)
        {
            return;
        }

        //Update node information
        CoSENode lNode = (CoSENode)lGraphObj;
        this.bounds.x = (int) lNode.getRect().x;
        this.bounds.y = (int) lNode.getRect().y;
        this.bounds.width = (int) lNode.getRect().width;
        this.bounds.height = (int) lNode.getRect().height;
        
        Vector2D springForceVector = new Vector2D(lNode.springForceX, lNode.springForceY);
        Vector2D repulsionForceVector = new Vector2D(lNode.repulsionForceX, lNode.repulsionForceY);
        Vector2D gravityForceVector = new Vector2D(lNode.gravitationForceX, lNode.gravitationForceY);
        Vector2D totalDisplacementVector = new Vector2D(lNode.displacementX, lNode.displacementY);
        
        RectangleD nodeGeometry = new RectangleD(lNode.getRect().getX(), 
        		lNode.getRect().getY(), 
        		lNode.getRect().getWidth(), 
        		lNode.getRect().getHeight());
        
        this.animationStates.add(new KeyFrameNodeState(nodeGeometry, 
        		springForceVector,
        		repulsionForceVector,
        		gravityForceVector,
        		totalDisplacementVector));
	}
}
