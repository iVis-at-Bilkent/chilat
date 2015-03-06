package Model;

import org.ivis.util.RectangleD;

import Util.Vector2D;

public class NodeState
{
	private RectangleD nodeGeometry;
	private Vector2D totalForceVector;
	private double minTotalForce;
	private double maxTotalForce;
	
	public NodeState(RectangleD nodeGeometry, Vector2D totalForceVector)
	{
		this.nodeGeometry = new RectangleD(nodeGeometry.x,
				nodeGeometry.y, 
				nodeGeometry.width,
				nodeGeometry.height);
		
		this.totalForceVector = new Vector2D(totalForceVector.getX(), totalForceVector.getY());
		this.minTotalForce = 0;
		this.maxTotalForce = 0;
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

	public double getMinTotalForce() {
		return minTotalForce;
	}

	public void setMinTotalForce(double minTotalForce) {
		this.minTotalForce = minTotalForce;
	}

	public double getMaxTotalForce() {
		return maxTotalForce;
	}

	public void setMaxTotalForce(double maxTotalForce) {
		this.maxTotalForce = maxTotalForce;
	}
}
