package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Util.Vector2D;

import com.mxgraph.model.ChiLATCell;


public class ForceVisualizationPopUpWindow extends JPanel 
{
	final int W = 200;
	final int H = 210;
	final int CIRCLE_RADIUS = 100;
	
	JLabel totalForceLabel;
	JLabel springforceLabel;
	JLabel repulsionForceLabel;
	JLabel gravityForceLabel;

	public ForceVisualizationPopUpWindow(int x, int y) {
		super();

		this.totalForceLabel = new JLabel();
		this.springforceLabel = new JLabel();
		this.repulsionForceLabel = new JLabel();
		this.gravityForceLabel = new JLabel();
		
		this.setLocation(x, y);
		this.add(totalForceLabel);
		this.add(springforceLabel);
		this.add(repulsionForceLabel);
		this.add(gravityForceLabel);
		this.add(Box.createVerticalGlue());
		this.add(new ForceVisualizationCirclePanel(null, null, null));

		TitledBorder forceVisPanelBorder = new TitledBorder("Force Inspector");
		forceVisPanelBorder.setTitleJustification(TitledBorder.CENTER);
		this.setBorder(forceVisPanelBorder);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setVisible(true);

	}

	public void updateContents(ChiLATCell cell) 
	{
		this.setSize(this.getPreferredSize());
		this.setSize(W, H);
		
		totalForceLabel.setText("Total Force " + cell.getTotalForce());
		repulsionForceLabel.setText("Repulsion Force " + cell.getRepulsionForce());
		springforceLabel.setText("Spring Force " + cell.getSpringForce());
		gravityForceLabel.setText("Gravity Force " + cell.getGravityForce());

	}


	public class ForceVisualizationCirclePanel extends JPanel {
		Vector2D springForce, gravityForce, repulsionForce;

		ForceVisualizationCirclePanel(Vector2D springForce, Vector2D gravityForce,
				Vector2D repulsionForce) {
			this.springForce = springForce;
			this.gravityForce = gravityForce;
			this.repulsionForce = repulsionForce;
			this.setAlignmentX(SwingConstants.CENTER);
			this.setPreferredSize(new Dimension(100, 100));
		}

		@Override
		public void paint(Graphics g0)
		{
			// Set anti aliasing on !
			Graphics2D g = (Graphics2D) g0;
			RenderingHints renderingHints = new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(renderingHints);
			
			super.paint(g);
			
			g.drawOval(getWidth()/2-CIRCLE_RADIUS/2, getHeight()/2-CIRCLE_RADIUS/2, CIRCLE_RADIUS, CIRCLE_RADIUS);
		}
		
		public void drawArrowEnd()
		{
			
		}
	}
}
