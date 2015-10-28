package com.mxgraph.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
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

public class ChiLATNodeShape extends mxRectangleShape 
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
			Vector2D totalForceVector = cell.getTotalForceVector();
			Vector2D topVertex = new Vector2D(totalForceVector);
			
			//Translate the total force vector to the center of the node
			Vector2D vertexB = topVertex.rotateVector(Math.PI/2);
			vertexB = vertexB.scale(ChiLATCell.WIDTH_SCALE/2);

			Vector2D vertexC = topVertex.rotateVector(-Math.PI/2);
			vertexC = vertexC.scale(ChiLATCell.WIDTH_SCALE/2);
			
			topVertex = topVertex.scale(ChiLATCell.HEIGHT_SCALE);

			triangle.addPoint((int)vertexB.getX()+ x + w/2, (int)vertexB.getY() + y + h/2);
			triangle.addPoint((int)topVertex.getX()+ x + w/2, (int)topVertex.getY() + y + h/2);
			triangle.addPoint((int)vertexC.getX()+ x + w/2, (int)vertexC.getY() + y + h/2);
			
			double normalizedForce = cell.getNormalizedTotalForce();
			double fillStartX = x+w/2;
			double fillStartY = y+h/2;
					
			double fillEndX = fillStartX + (topVertex.getX()+x+w/2 - fillStartX) * normalizedForce;
			double fillEndY = fillStartY + (topVertex.getY()+y+h/2 - fillStartY) * normalizedForce;
			GradientPaint redtowhite;
		
			if (Math.floor(fillEndX) == fillStartX && Math.floor(fillEndY) == fillStartY)
			{
				redtowhite = new GradientPaint((float)fillStartX,(float)fillStartY,Color.WHITE,
						(float)fillEndX,
						(float)fillEndY,
						Color.WHITE);
			}
			else
			{
				redtowhite = new GradientPaint((float)fillStartX,(float)fillStartY,Color.RED,
						(float)fillEndX,
						(float)fillEndY,
						Color.WHITE);
			}
			
			// Set anti aliasing on !
			Graphics2D g = (Graphics2D) canvas.getGraphics();
			RenderingHints renderingHints = new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(renderingHints);
			
			g.setStroke(new BasicStroke(FORCE_ARROW_STROKE_WIDTH));
			g.setPaint(Color.BLACK);
			g.draw(triangle);
			g.setPaint(redtowhite);
			/*double totalForceScale = 1-cell.getNormalizedTotalForce();
			g.setColor(new Color(255,(int)(255*totalForceScale), (int)(255*totalForceScale)));*/
			g.fill(triangle);
		}

	}
	
	
	public mxPoint rotateVector(mxPoint vector, double radians)
	{
		 double x = (vector.getX() * Math.cos(radians)) - (vector.getY() * Math.sin(radians));
		 double y = (vector.getX() * Math.sin(radians)) + (vector.getY() * Math.cos(radians));
		 return new mxPoint(x, y);
	}
}
