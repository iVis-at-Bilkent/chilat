package Model;

import org.ivis.util.RectangleD;

import Util.Vector2D;

public class NodeState
{
	private RectangleD nodeGeometry;
	private Vector2D springForceVector;
	private Vector2D repulsionForceVector;
	private Vector2D gravityForceVector;
	
	
	public NodeState(RectangleD nodeGeometry, Vector2D springForceVector, Vector2D repulsionForceVector, Vector2D gravityForceVector)
	{
		this.nodeGeometry = new RectangleD(nodeGeometry.x,
				nodeGeometry.y, 
				nodeGeometry.width,
				nodeGeometry.height);
		
		this.springForceVector = springForceVector;
		this.repulsionForceVector = repulsionForceVector;
		this.gravityForceVector = gravityForceVector;
		
	}

	public RectangleD getNodeGeometry() {
		return nodeGeometry;
	}

	public void setNodeGeometry(RectangleD nodeGeometry) {
		this.nodeGeometry.x = nodeGeometry.x;
		this.nodeGeometry.y = nodeGeometry.y;
		this.nodeGeometry.width = nodeGeometry.width;
		this.nodeGeometry.height = nodeGeometry.height;
	}

	public Vector2D getTotalForceVector() 
	{
		return new Vector2D(this.springForceVector.getX() + this.repulsionForceVector.getX() + this.gravityForceVector.getX(),
							this.springForceVector.getY() + this.repulsionForceVector.getY() + this.gravityForceVector.getY());
	}

	public Vector2D getSpringForceVector() {
		return springForceVector;
	}

	public Vector2D getRepulsionForceVector() {
		return repulsionForceVector;
	}

	public Vector2D getGravityForceVector() {
		return gravityForceVector;
	}
}
