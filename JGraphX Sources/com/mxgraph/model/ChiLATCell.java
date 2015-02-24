package com.mxgraph.model;

import com.mxgraph.util.mxPoint;

public class ChiLATCell extends mxCell 
{
	double totalForce;
	mxPoint totalForceVector;
	
	public ChiLATCell(Object value, mxGeometry geometry, String style)
	{
		super(value, geometry, style);
		
		totalForceVector = new mxPoint(0, 0);
		totalForce = 0;
	}
	
	//Getter and Setters
	public double getTotalForce() {
		return totalForce;
	}

	public void setTotalForce(double totalForce) {
		this.totalForce = totalForce;
	}

	public mxPoint getTotalForceVector() {
		return totalForceVector;
	}

	public void setTotalForceVector(mxPoint totalForceVector) {
		this.totalForceVector = new mxPoint(totalForceVector);
	}
}