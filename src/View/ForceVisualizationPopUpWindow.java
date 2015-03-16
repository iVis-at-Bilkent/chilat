package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

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
	final int W = 180;
	final int H = 230;
	final int CIRCLE_RADIUS = 60;
	
	private JLabel totalForceLabel;
	private JLabel springForceLabel;
	private JLabel repulsionForceLabel;
	private JLabel gravityForceLabel;
	private JPanel labelsPanel;
	
	private ForceVisualizationCirclePanel circlePanel;
	
	public ForceVisualizationPopUpWindow(int x, int y) 
	{
		super();
		
		
		this.setLocation(x, y);
		this.circlePanel = new ForceVisualizationCirclePanel();
		this.labelsPanel = this.createForceLabelsPanel();

		TitledBorder forceVisPanelBorder = new TitledBorder("Force Inspector");
		forceVisPanelBorder.setTitleJustification(TitledBorder.CENTER);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(forceVisPanelBorder);
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.add(labelsPanel);
		this.add(circlePanel);

		this.setSize(W,H);
		this.setVisible(true);

	}

	public void updateContents(ChiLATCell selectedCell) 
	{
		DecimalFormat formatter = new DecimalFormat();
		this.totalForceLabel.setText(""+formatter.format(selectedCell.getTotalForce()));
		this.repulsionForceLabel.setText(""+formatter.format(selectedCell.getRepulsionForce()));
		this.springForceLabel.setText("" + formatter.format(selectedCell.getSpringForce()));
		this.gravityForceLabel.setText("" + formatter.format(selectedCell.getGravityForce()));
		this.circlePanel.updateContents(selectedCell);
		this.revalidate();
		
	}
	
	public JPanel createForceLabelsPanel()
	{
		this.totalForceLabel = new JLabel();
		this.springForceLabel = new JLabel();
		this.repulsionForceLabel = new JLabel();
		this.gravityForceLabel = new JLabel();
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(createLabelPanel("Total Force:", totalForceLabel));
		labelPanel.add(createLabelPanel("Spring Force:", springForceLabel));
		labelPanel.add(createLabelPanel("Repulsion Force:", repulsionForceLabel));
		labelPanel.add(createLabelPanel("Gravity Force:", gravityForceLabel));
		labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


		return labelPanel;
	}
	
	public JPanel createLabelPanel(String firstLabelText, JLabel secondLabel )
	{
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
		JLabel firstLabel = new JLabel(firstLabelText);
		newPanel.add(firstLabel);
		newPanel.add(Box.createHorizontalGlue());
		newPanel.add(secondLabel);
		newPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		return newPanel;
	}


	public class ForceVisualizationCirclePanel extends JPanel 
	{
		ChiLATCell selectedCell;

		ForceVisualizationCirclePanel() 
		{
			this.setPreferredSize(new Dimension(200, 200));
			this.setAlignmentX(Component.LEFT_ALIGNMENT);
		}
		
		public void updateContents(ChiLATCell selectedCell)
		{
			this.selectedCell = selectedCell;
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
			Vector2D circleCenter = new Vector2D(getWidth()/2, getHeight()/2);
			
			Vector2D springForceVector = selectedCell.getSpringForceVector();
			Vector2D repulsionForceVector = selectedCell.getRepulsionForceVector();
			Vector2D gravityForceVector = selectedCell.getGravityForceVector();
			
			double springForceScale = selectedCell.getNormalizedSpringForce() * CIRCLE_RADIUS;
			double repulsionForceScale = selectedCell.getNormalizedRepulsionForce() * CIRCLE_RADIUS;
			double gravityForceScale = selectedCell.getNormalizedGravityForce() * CIRCLE_RADIUS;
				
			System.out.println(selectedCell.getSpringForce() + " " + selectedCell.getRepulsionForce() + " " + selectedCell.getGravityForce() + " " + ChiLATCell.MIN_OF_ALL_OTHER_FORCES + " " + ChiLATCell.MAX_OF_ALL_OTHER_FORCES);
			
			g.drawOval(getWidth()/2 - CIRCLE_RADIUS, getHeight()/2 - CIRCLE_RADIUS, 2*CIRCLE_RADIUS, 2*CIRCLE_RADIUS);
			g.setColor(Color.BLUE);
			g.drawLine(getWidth()/2, getHeight()/2, (int)(circleCenter.add(springForceVector.scale(springForceScale))).getX(), (int)(circleCenter.add(springForceVector.scale(springForceScale))).getY());
			g.setColor(Color.YELLOW);
			g.drawLine(getWidth()/2, getHeight()/2, (int)(circleCenter.add(repulsionForceVector.scale(repulsionForceScale))).getX(), (int)(circleCenter.add(repulsionForceVector.scale(repulsionForceScale))).getY());
			g.setColor(Color.GREEN);
			g.drawLine(getWidth()/2, getHeight()/2, (int)(circleCenter.add(gravityForceVector.scale(gravityForceScale))).getX(), (int)(circleCenter.add(gravityForceVector.scale(gravityForceScale))).getY());

		}
	}
}
