package Util;

/** 
 *  ========================================================
 *  Vector2D.java: Source code for two-dimensional vectors
 * 
 *  Written by: Mark Austin                   November, 2005
 *  ========================================================
 */

import java.io.Serializable;
import java.lang.Math;

public class Vector2D implements Serializable
{
	protected double x;
	protected double y;

	// Constructor methods ....
	public Vector2D() 
	{
		x = y = 0.0;
	}
	
	public Vector2D(Vector2D other) 
	{
		this.x = other.x;
		this.y = other.y;
	}

	public Vector2D( double dX, double dY ) 
	{
		this.x = dX;
		this.y = dY;
	}

	// Convert vector to a string ...
	public String toString() 
	{
		return "Vector2D(" + x + ", " + y + ")";
	}

	// Compute magnitude of vector ....
	public double length() 
	{
		return Math.sqrt ( x*x + y*y );
	}

	// Sum of two vectors ....
	public Vector2D add( Vector2D v1 ) 
	{
		Vector2D v2 = new Vector2D( this.x + v1.x, this.y + v1.y );
		return v2;
	}

	// Subtract vector v1 from v .....
	public Vector2D sub( Vector2D v1 ) 
	{
		Vector2D v2 = new Vector2D( this.x - v1.x, this.y - v1.y );
		return v2;
	}

	// Scale vector by a constant ...
	public Vector2D scale( double scaleFactor ) 
	{
		Vector2D v2 = new Vector2D( this.x*scaleFactor, this.y*scaleFactor );
		return v2;
	}
	
	public Vector2D rotateVector(double radians)
	{
		 double x = (this.getX() * Math.cos(radians)) - (this.getY() * Math.sin(radians));
		 double y = (this.getX() * Math.sin(radians)) + (this.getY() * Math.cos(radians));
		 return new Vector2D(x, y);
	}

	// Normalize a vectors length....
	public Vector2D normalize() {
		Vector2D v2 = new Vector2D();

		double length = this.length();
		if (length != 0) {
			v2.x = this.x/length;
			v2.y = this.y/length;
		}

		return v2;
	}   

	// Dot product of two vectors .....
	public double dotProduct ( Vector2D v1 ) 
	{
		return this.x*v1.x + this.y*v1.y;
	}

	public double getX() {
		return x;
	}

	public void setX(double dX) {
		this.x = dX;
	}

	public double getY() {
		return y;
	}

	public void setY(double dY) {
		this.y = dY;
	}
	
	//Linear interpolation between two given vectors by interpolation amount
	public static Vector2D lerp(Vector2D startVector, Vector2D endVector, double interpolationAmount)
	{
		return (startVector.scale(1.0-interpolationAmount)).add(endVector.scale(interpolationAmount));
	}
}
