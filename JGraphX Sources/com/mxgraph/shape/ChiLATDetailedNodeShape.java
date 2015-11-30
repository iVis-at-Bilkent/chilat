package com.mxgraph.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.tools.ToolProvider;

import Util.Vector2D;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.ChiLATCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;

public class ChiLATDetailedNodeShape extends mxRectangleShape 
{
	public static int FORCE_ARROW_STROKE_WIDTH = 2;

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

		if (ChiLATCell.IS_FORCE_DETAILS_VISIBLE) 
		{
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
			Vector2D totalForceVector = cell.getTotalForceVector().normalize();
			Vector2D springForceVector = cell.getSpringForceVector().normalize();
			Vector2D repulsionForceVector = cell.getRepulsionForceVector().normalize();
			Vector2D gravityForceVector = cell.getGravityForceVector().normalize();

			
			Vector2D totalForceArrowVector = new Vector2D(totalForceVector);
			Vector2D springForceArrowVector = new Vector2D(springForceVector);
			Vector2D repulsionForceArrowVector = new Vector2D(repulsionForceVector);

			totalForceArrowVector = totalForceArrowVector.scale(ChiLATCell.HEIGHT_SCALE);
			springForceArrowVector = springForceArrowVector.scale(ChiLATCell.HEIGHT_SCALE);
			repulsionForceArrowVector = repulsionForceArrowVector.scale(ChiLATCell.HEIGHT_SCALE);
			
			double fillStartX = x+w/2;
			double fillStartY = y+h/2;
			Vector2D lineStartPoint = new Vector2D(fillStartX, fillStartY);					
			
			// Set anti aliasing on !
			Graphics2D g = (Graphics2D) canvas.getGraphics();
			RenderingHints renderingHints = new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(renderingHints);
			
			g.setStroke(new BasicStroke(1));
			drawArrowAndLine(Color.BLUE, g, springForceArrowVector, 1, lineStartPoint);
			drawArrowAndLine(Color.YELLOW, g, repulsionForceArrowVector, 1, lineStartPoint);
			drawArrowAndLine(Color.GREEN, g, gravityForceVector, 1, lineStartPoint);
			drawArrowAndLine(Color.RED, g, totalForceArrowVector, 1, lineStartPoint);

		}
	}
	
	public void drawArrowAndLine(Color color, Graphics g, Vector2D direction, double forceScaleFactor, Vector2D lineStartPoint)
	{
		g.setColor(color);
		Vector2D circleCenter = lineStartPoint;
		Vector2D arrowStart = circleCenter.add(direction.scale(forceScaleFactor));

		g.drawLine((int)circleCenter.getX(), 
				(int)circleCenter.getY(), 
				(int)arrowStart.getX(), 
				(int)arrowStart.getY());

		Vector2D arrowHeight = direction.normalize().scale(8);
		Vector2D arrowWidth = direction.normalize().scale(4);
		Vector2D arrowEnd = arrowStart.add(arrowHeight);
		Vector2D sideVertexA = arrowStart.add(arrowWidth.rotateVector(Math.PI/2));
		Vector2D sideVertexB = arrowStart.add(arrowWidth.rotateVector(-Math.PI/2));

		Polygon triangle = new Polygon();
		triangle.addPoint((int)arrowEnd.getX(), (int)arrowEnd.getY());
		triangle.addPoint((int)sideVertexA.getX(), (int)sideVertexA.getY());
		triangle.addPoint((int)sideVertexB.getX(), (int)sideVertexB.getY());

		g.drawPolygon(triangle);
		g.fillPolygon(triangle);			
	}
	
	
	public mxPoint rotateVector(mxPoint vector, double radians)
	{
		 double x = (vector.getX() * Math.cos(radians)) - (vector.getY() * Math.sin(radians));
		 double y = (vector.getX() * Math.sin(radians)) + (vector.getY() * Math.cos(radians));
		 return new mxPoint(x, y);
	}
}
