package com.mxgraph.model;

import com.mxgraph.util.mxPoint;

public class ChiLATCell extends mxCell 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2746940854791812432L;
	
	public static double DEFAULT_WIDTH_SCALE = 15;
	public static double DEFAULT_HEIGHT_SCALE = 20;
	
	public static double HEIGHT_SCALE = DEFAULT_HEIGHT_SCALE;
	public static double WIDTH_SCALE = DEFAULT_WIDTH_SCALE;
	
	public static double MIN_TOTAL_FORCE = 0;
	public static double MAX_TOTAL_FORCE = 0;

	private double totalForce;
	private mxPoint totalForceVector;
	
	public ChiLATCell(Object value, mxGeometry geometry, String style)
	{
		super(value, geometry, style);
		
		this.totalForceVector = new mxPoint(0, 0);
		this.totalForce = 0;
	}
	
	public double getNormalizedForce()
	{
		return (this.totalForce - MIN_TOTAL_FORCE) / (MAX_TOTAL_FORCE - MIN_TOTAL_FORCE);
	}
	
	//Getter and Setters
	public double getTotalForce() {
		return totalForce;
	}

	public void setTotalForce(double totalForce) {
		this.totalForce = totalForce;
	}

	public mxPoint getTotalForceVector() {
		return this.totalForceVector;
	}

	public void setTotalForceVector(mxPoint totalForceVector) 
	{
		this.totalForceVector.setX(totalForceVector.getX());
		this.totalForceVector.setY(totalForceVector.getY());
	}
}