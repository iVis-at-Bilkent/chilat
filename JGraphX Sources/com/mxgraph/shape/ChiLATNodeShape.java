package com.mxgraph.shape;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.ChiLATCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

public class ChiLATNodeShape extends mxRectangleShape 
{
	@Override
	public Shape createShape(mxGraphics2DCanvas canvas, mxCellState state) 
	{
		return null;
		
	};
	
	@Override
	public void paintShape(mxGraphics2DCanvas canvas, mxCellState state)
	{
		// TODO Auto-generated method stub
		super.paintShape(canvas, state);

		Rectangle temp = state.getRectangle();
		int x = temp.x;
		int y = temp.y;
		int w = temp.width;
		int h = temp.height;
				
		Polygon triangle = new Polygon();
		
		/* Generate a triangle that is directed to the total force vector
		 * here we just rotate the total force vector by 90 and -90 degrees
		 * to obtain the other two vertices of resulting triangle shape
		*/
		ChiLATCell cell = (ChiLATCell) state.getCell();
		//normalized total force vector
		mxPoint totalForceVector = cell.getTotalForceVector();
		mxPoint topVertex = new mxPoint(totalForceVector);
		
		//Translate the total force vector to the center of the node
		mxPoint vertexB = this.rotateVector(topVertex, Math.PI/2);
		vertexB.setX(vertexB.getX() * ChiLATCell.WIDTH_SCALE/2);
		vertexB.setY(vertexB.getY() * ChiLATCell.WIDTH_SCALE/2);

		mxPoint vertexC = this.rotateVector(topVertex, -Math.PI/2);
		vertexC.setX(vertexC.getX() * ChiLATCell.WIDTH_SCALE/2);
		vertexC.setY(vertexC.getY() * ChiLATCell.WIDTH_SCALE/2);
		
		topVertex.setX(topVertex.getX() * ChiLATCell.HEIGHT_SCALE);
		topVertex.setY(topVertex.getY() * ChiLATCell.HEIGHT_SCALE);

		triangle.addPoint((int)vertexB.getX()+ x + w/2, (int)vertexB.getY() + y + h/2);
		triangle.addPoint((int)topVertex.getX()+ x + w/2, (int)topVertex.getY() + y + h/2);
		triangle.addPoint((int)vertexC.getX()+ x + w/2, (int)vertexC.getY() + y + h/2);
		
		double normalizedForce = cell.getNormalizedForce();
		double fillStartX = x+w/2;
		double fillStartY = y+h/2;
				
		double fillEndX = fillStartX + (((int)topVertex.getX()+x+w/2) - fillStartX) * normalizedForce;
		double fillEndY = fillStartY + (((int)topVertex.getY()+y+h/2) - fillStartY) * normalizedForce;
		GradientPaint redtowhite;
		
		/*if (Math.sqrt(Math.pow((fillEndX-fillStartX), 2) + Math.pow((fillEndY-fillStartY), 2)) > 2.0 )
		{

			redtowhite = new GradientPaint((int)fillStartX,(int)fillStartY,Color.RED,
					(int)fillEndX,
					(int)fillEndY,
					Color.WHITE);
		}
		else
		{
			redtowhite = new GradientPaint((int)fillStartX,(int)fillStartY,Color.WHITE,
					(int)fillEndX,
					(int)fillEndY,
					Color.WHITE);
		}	
		*/
		
		redtowhite = new GradientPaint((int)fillStartX,(int)fillStartY,Color.RED,
				(int)fillEndX,
				(int)fillEndY,
				Color.WHITE);
		
		System.out.println( cell.getId() + " " + fillStartX + " " + fillStartY + " " + fillEndX + " " + fillEndY + " " + normalizedForce);

		canvas.getGraphics().setPaint(Color.red);
		canvas.getGraphics().draw(triangle);
		canvas.getGraphics().setPaint(redtowhite);
		canvas.getGraphics().fill(triangle);
	}
	
	
	public mxPoint rotateVector(mxPoint vector, double radians)
	{
		 double x = (vector.getX() * Math.cos(radians)) - (vector.getY() * Math.sin(radians));
		 double y = (vector.getX() * Math.sin(radians)) + (vector.getY() * Math.cos(radians));
		 return new mxPoint(x, y);
	}
}
