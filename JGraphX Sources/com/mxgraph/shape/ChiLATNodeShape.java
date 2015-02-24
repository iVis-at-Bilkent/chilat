package com.mxgraph.shape;

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
		ChiLATCell cell = (ChiLATCell) state.getCell();
		
		//normalized total force vector
		mxPoint totalForceVector = cell.getTotalForceVector();
		
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
		mxPoint topVertex = cell.getTotalForceVector();
		//Translate the total force vector to the center of the node
		mxPoint vertexB = this.rotateVector(topVertex, 90);
		mxPoint vertexC = this.rotateVector(topVertex, -90);

		triangle.addPoint((int)vertexB.getX()+ x + w/2, (int)vertexB.getY() + y + h/2);
		triangle.addPoint((int)topVertex.getX()+ x + w/2, (int)topVertex.getY() + y + h/2);
		triangle.addPoint((int)vertexC.getX()+ x + w/2, (int)vertexC.getY() + y + h/2);

		canvas.getGraphics().drawPolygon(triangle);
		
	}
	
	public mxPoint rotateVector(mxPoint vector, double radians)
	{
		 double x = (vector.getX() * Math.cos(radians)) - (vector.getY() * Math.sin(radians));
		 double y = (vector.getX() * Math.sin(radians)) + (vector.getY() * Math.cos(radians));
		 return new mxPoint(x, y);
	}
}
