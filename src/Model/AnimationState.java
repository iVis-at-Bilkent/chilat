package Model;

import org.ivis.util.RectangleD;

import Util.Vector2D;

public class AnimationState
{
	RectangleD nodeGeometry;
	Vector2D totalForceVector;
	
	public AnimationState(RectangleD nodeGeometry, Vector2D totalForceVector)
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
		this.nodeGeometry = nodeGeometry;
	}

	public Vector2D getTotalForceVector() {
		return totalForceVector;
	}

	public void setTotalForceVector(Vector2D totalForceVector) {
		this.totalForceVector = new Vector2D(totalForceVector.getX(), totalForceVector.getY());
	}
}
