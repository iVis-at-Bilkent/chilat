package Model;

import java.util.ArrayList;

public class KeyFrameState 
{
	private ArrayList<NodeState> nodeStates;
	
	private double minTotalForce;
	private double maxTotalForce;
	
	public KeyFrameState()
	{
		this.nodeStates = new ArrayList<NodeState>();
		minTotalForce = 0;
		maxTotalForce = 0;
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
