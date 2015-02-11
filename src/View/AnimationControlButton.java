package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.Action;
import javax.swing.JButton;

public class AnimationControlButton extends JButton
{	
	Shape shape;
	
	public AnimationControlButton(Action action)
	{
		super(action);
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
		setPreferredSize(size);
		setContentAreaFilled(false);
	}
	
	protected void paintComponent(Graphics g)
	{
	     if (getModel().isArmed()) {
	           g.setColor(Color.lightGray);
	     } 
	     else 
	     {
	          g.setColor(getBackground());
	     }
	     g.fillOval(0, 0, getSize().width, getSize().height);
	     super.paintComponent(g);
	}
	protected void paintBorder(Graphics g) 
	{
	}
	
	public boolean contains(int x, int y) 
	{
	     if (shape == null || !shape.getBounds().equals(getBounds())) 
	     {
	          shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
	     }
	     return shape.contains(x, y);
	}
}
