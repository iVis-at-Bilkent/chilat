package Model;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.ivis.layout.LGraphObject;
import org.ivis.layout.LNode;
import org.ivis.layout.Updatable;
import org.ivis.layout.cose.CoSEGraph;
import org.ivis.layout.fd.FDLayoutNode;
import org.ivis.util.RectangleD;

import Util.Vector2D;

public class NodeModel extends BaseModel implements Updatable
{
	private String nodeLabel;
	private Rectangle bounds;
	private CompoundNodeModel parent;
	private Vector2D totalForceVector;
	
	protected ArrayList<AnimationState> animationStates;
	
	public NodeModel(String id)
	{
		super(id);
		this.bounds = new Rectangle();
		this.animationStates = new ArrayList<AnimationState>();
		this.totalForceVector = new Vector2D();
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
	
	public ArrayList<AnimationState> getAnimationStates() {
		return animationStates;
	}

	public void setAnimationStates(ArrayList<AnimationState> animationStates) {
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

        //Update positions
        FDLayoutNode lNode = (FDLayoutNode)lGraphObj;
        this.bounds.x = (int) lNode.getRect().x;
        this.bounds.y = (int) lNode.getRect().y;
        this.bounds.width = (int) lNode.getRect().width;
        this.bounds.height = (int) lNode.getRect().height;
        this.totalForceVector.setX(lNode.displacementX);
        this.totalForceVector.setY(lNode.displacementY);
        
        RectangleD nodeGeometry = new RectangleD(lNode.getRect().getX(), lNode.getRect().getY(), lNode.getRect().getWidth(), lNode.getRect().getHeight());
        this.animationStates.add(new AnimationState(nodeGeometry, this.totalForceVector));
	}
}
