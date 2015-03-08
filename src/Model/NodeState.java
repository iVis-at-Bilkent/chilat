package Model;

import org.ivis.util.RectangleD;

import Util.Vector2D;

public class NodeState
{
	private RectangleD nodeGeometry;
	private Vector2D totalForceVector;
	
	public NodeState(RectangleD nodeGeometry, Vector2D totalForceVector)
	{
		this.nodeGeometry = new RectangleD(nodeGeometry.x,
				nodeGeometry.y, 
				nodeGeometry.width,
				nodeGeometry.height);
		
		this.totalForceVector = new Vector2D(totalForceVector.getX(), totalForceVector.getY());
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

	public Vector2D getTotalForceVector() {
		return totalForceVector;
	}

	public void setTotalForceVector(Vector2D totalForceVector) {
		this.totalForceVector.setX(totalForceVector.getX());
		this.totalForceVector.setY(totalForceVector.getY());
	}
}
