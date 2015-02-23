package com.mxgraph.shape;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.view.mxCellState;

public class ChiLATForceArrowShape extends mxBasicShape
{
	/**
	 * 
	 */
	@Override
	public Shape createShape(mxGraphics2DCanvas canvas, mxCellState state)
	{
		Rectangle temp = state.getRectangle();
		int x = temp.x;
		int y = temp.y;
		int w = temp.width;
		int h = temp.height;
		
		Polygon triangle = new Polygon();

		return triangle;
	}
}
