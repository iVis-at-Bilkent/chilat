package com.mxgraph.model;

import Util.Vector2D;


public class ChiLATCell extends mxCell 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2746940854791812432L;
	
	public static double DEFAULT_WIDTH_SCALE = 20;
	public static double DEFAULT_HEIGHT_SCALE = 30;
	
	public static double HEIGHT_SCALE = DEFAULT_HEIGHT_SCALE;
	public static double WIDTH_SCALE = DEFAULT_WIDTH_SCALE;
	
	public static double MIN_TOTAL_FORCE = 0;
	public static double MAX_TOTAL_FORCE = 0;
	public static double MIN_OF_ALL_OTHER_FORCES = 0;
	public static double MAX_OF_ALL_OTHER_FORCES = 0;
	
	public static boolean IS_FORCE_DETAILS_VISIBLE = false;
	public static boolean IS_SHOW_ACTUAL_DISPLACEMENT = false;


	private Vector2D totalForceVector;
	private Vector2D springForceVector;
	private Vector2D repulsionForceVector;
	private Vector2D gravityForceVector;
	
	private double totalForce;
	private double springForce;
	private double repulsionForce;
	private double gravityForce;
	private double totalDisplacement;
	
	public ChiLATCell(Object value, mxGeometry geometry, String style)
	{
		super(value, geometry, style);
		
		this.totalForceVector = new Vector2D(0, 0);
		this.springForceVector = new Vector2D(0, 0);
		this.repulsionForceVector = new Vector2D(0, 0);
		this.gravityForceVector = new Vector2D(0, 0);
	}
	
	//Getter and Setters
	public double getNormalizedTotalForce()
	{
		if (IS_FORCE_DETAILS_VISIBLE) 
		{
			return 	getNormalizedForce(this.totalDisplacement, MIN_TOTAL_FORCE, MAX_TOTAL_FORCE);
		}
		else
			return 	getNormalizedForce(this.totalForce, MIN_TOTAL_FORCE, MAX_TOTAL_FORCE);
	}
	
	public double getNormalizedSpringForce()
	{
		return 	getNormalizedForce(this.springForce, MIN_OF_ALL_OTHER_FORCES, MAX_OF_ALL_OTHER_FORCES);
	}
	
	public double getNormalizedRepulsionForce()
	{
		return 	getNormalizedForce(this.repulsionForce, MIN_OF_ALL_OTHER_FORCES, MAX_OF_ALL_OTHER_FORCES);
	}
	
	public double getNormalizedGravityForce()
	{
		return 	getNormalizedForce(this.gravityForce, MIN_OF_ALL_OTHER_FORCES, MAX_OF_ALL_OTHER_FORCES);
	}
	
	public double getNormalizedForce(double force, double min, double max)
	{
		if (min == 0 && max == 0) 
		{
			return 0;
		}
		
		return (force - min) / (max - min);
	}

	//Getter and Setters
	public double getTotalForce() {
		return totalForce;
	}
	
	public double getSpringForce() {
		return springForce;
	}
	
	public double getRepulsionForce() {
		return repulsionForce;
	}
	
	public double getGravityForce() {
		return gravityForce;
	}

	public Vector2D getTotalForceVector() {
		return totalForceVector;
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
	
	public double getTotalDisplacement() {
		return totalDisplacement;
	}

	public void setTotalForce(double totalForce) {
		this.totalForce = totalForce;
	}
	
	public void setSpringForce(double springForce) {
		this.springForce = springForce;
	}
	public void setRepulsionForce(double repulsionForce) {
		this.repulsionForce = repulsionForce;
	}
	public void setGravityForce(double gravityForce) {
		this.gravityForce = gravityForce;
	}

	public void setTotalForceVector(Vector2D totalForceVector) {
		this.totalForceVector = totalForceVector;
	}

	public void setSpringForceVector(Vector2D springForceVector) {
		this.springForceVector = springForceVector;
	}

	public void setRepulsionForceVector(Vector2D repulsionForceVector) {
		this.repulsionForceVector = repulsionForceVector;
	}

	public void setGravityForceVector(Vector2D gravityForceVector) {
		this.gravityForceVector = gravityForceVector;
	}

	public void setTotalDisplacement(double totalDisplacement) {
		this.totalDisplacement = totalDisplacement;
	}

}