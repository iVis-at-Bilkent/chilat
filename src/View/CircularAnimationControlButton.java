package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.Action;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class CircularAnimationControlButton extends JButton
{	
	Shape shape;

	public CircularAnimationControlButton(Action action)
	{
		super(action);
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
		setPreferredSize(size);
		setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g0)
	{
		//Set anti aliasing on !
		Graphics2D g = (Graphics2D)g0;
		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(renderingHints);

		if (getModel().isArmed()) 
		{
			//Set bluish color when pressed
			g.setColor(new Color(35, 147, 217));
		} 
		else 
		{
			g.setColor(getBackground());
		}
		g.fillOval(0, 0, getSize().width, getSize().height);
		super.paintComponent(g);
	}

	@Override
	public boolean contains(int x, int y) 
	{
		if (shape == null || !shape.getBounds().equals(getBounds())) 
		{
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
}
