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

public class ForceVisualizationPopUpWindow extends JPanel {
	private double scale = 1.0;
	final int W = 200;
	final int H = 100;

	public ForceVisualizationPopUpWindow(int x, int y) {
		super();
		JLabel springforce = new JLabel("Spring Force: " + x);
		JLabel repulsionForce = new JLabel("Repulsion Force: " + x);
		JLabel gravityForce = new JLabel("Gravity Force: " + x);

		this.setLocation(x, y);
		// this.setSize(new Dimension((int)(W*scale), (int)(H*scale)));

		this.add(springforce);
		this.add(repulsionForce);
		this.add(gravityForce);
		this.add(Box.createVerticalGlue());
		this.add(new ForceVisualizationCirclePanel(null, null, null));

		TitledBorder forceVisPanelBorder = new TitledBorder("Force Inspector");
		forceVisPanelBorder.setTitleJustification(TitledBorder.CENTER);
		this.setBorder(forceVisPanelBorder);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setVisible(true);

	}

	public void updateSize() {
		this.setSize(this.getPreferredSize());
		this.setSize(150, 210);
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
			
			int radius = 100;
			g.drawOval(getWidth()/2-radius/2, getHeight()/2-radius/2, radius, radius);
		}
		
		public void drawArrowEnd()
		{
			
		}
	}
}
