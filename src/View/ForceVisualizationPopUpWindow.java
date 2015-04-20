package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
import com.mxgraph.model.mxCell;


public class ForceVisualizationPopUpWindow extends JPanel 
{
	final int W = 0;
	final int H = 0;
	final int CIRCLE_RADIUS = 60;
	final int ARROW_HEIGHT = 5;
	final int ARROW_WIDTH = 2;

	private JLabel totalForceLabel;
	private JLabel springForceLabel;
	private JLabel repulsionForceLabel;
	private JLabel gravityForceLabel;
	private JLabel displacementLabel;
	private JPanel labelsPanel;
	private JPanel mainContainerPanel;

	private ForceVisualizationCirclePanel circlePanel;

	private static Color totalForceColor = Color.RED.darker();
	private static Color springForceColor = Color.BLUE.darker();
	private static Color repulsionForceColor = Color.YELLOW.darker();
	private static Color gravityForceColor = Color.GREEN.darker();
	
	private ChiLATCell selectedCell;

	

	public ForceVisualizationPopUpWindow(ChiLATCell selectedCell) 
	{                
		this.selectedCell = selectedCell;
		this.circlePanel = new ForceVisualizationCirclePanel(this.selectedCell);
		this.labelsPanel = this.createForceLabelsPanel();

		TitledBorder forceVisPanelBorder = new TitledBorder("Force Inspector");
		forceVisPanelBorder.setTitleJustification(TitledBorder.CENTER);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(forceVisPanelBorder);
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		mainContainerPanel = new JPanel();
		mainContainerPanel.setLayout(new BoxLayout(mainContainerPanel, BoxLayout.X_AXIS));
		mainContainerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		mainContainerPanel.add(labelsPanel);
		mainContainerPanel.add(circlePanel);
		this.add(mainContainerPanel);

		this.setVisible(true);
		this.setPreferredSize(new Dimension(W,H));

	}

	public void updateContents(ChiLATCell selectedCell)
	{
		this.selectedCell = selectedCell;
		this.totalForceLabel.setText(""+this.formatForceValue(this.selectedCell.getTotalForce()));
		this.repulsionForceLabel.setText(""+this.formatForceValue(this.selectedCell.getRepulsionForce()));
		this.springForceLabel.setText("" + this.formatForceValue(this.selectedCell.getSpringForce()));
		this.gravityForceLabel.setText("" + this.formatForceValue(this.selectedCell.getGravityForce()));
		this.displacementLabel.setText("" + this.formatForceValue(this.selectedCell.getTotalDisplacement()));
		this.circlePanel.updateContents(this.selectedCell);
		
		if (this.selectedCell.getValue()!= null) 
		{
			TitledBorder forceVisPanelBorder = new TitledBorder(selectedCell.getValue().toString());
			forceVisPanelBorder.setTitleJustification(TitledBorder.CENTER);
			this.setBorder(forceVisPanelBorder);
		}

		this.revalidate();
	}

	public String formatForceValue(double forceValue)
	{
		DecimalFormat formatter = new DecimalFormat("0.0");

		if (forceValue == 0) 
		{
			return "-";
		}

		return formatter.format(forceValue);
	}

	@Override
	public void paint(Graphics g0)
	{       
		updateContents(this.selectedCell);
		super.paint(g0);
	}
	
	public JPanel createForceLabelsPanel()
	{
		this.totalForceLabel = new JLabel();
		totalForceLabel.setForeground(totalForceColor);
		this.springForceLabel = new JLabel();
		springForceLabel.setForeground(springForceColor);
		this.repulsionForceLabel = new JLabel();
		repulsionForceLabel.setForeground(repulsionForceColor);
		this.gravityForceLabel = new JLabel();
		gravityForceLabel.setForeground(gravityForceColor);
		this.displacementLabel = new JLabel();  


		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(createLabelPanel("Spring Force:", springForceLabel, false));
		labelPanel.add(createLabelPanel("Repulsion Force:", repulsionForceLabel, false));
		labelPanel.add(createLabelPanel("Gravity Force:", gravityForceLabel, false));
		labelPanel.add(createLabelPanel("Total Force:", totalForceLabel, true));
		labelPanel.add(Box.createRigidArea(new Dimension(0,5)));
		labelPanel.add(createLabelPanel("Total Displacement: ", displacementLabel, true));

		labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		return labelPanel;
	}

	public JPanel createLabelPanel(String firstLabelText, JLabel secondLabel, boolean isFontsBold )
	{
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
		JLabel firstLabel = new JLabel(firstLabelText);
		newPanel.add(firstLabel);
		newPanel.add(Box.createHorizontalGlue());
		newPanel.add(secondLabel);
		newPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		if (isFontsBold) 
		{
			//Set  labels bold
			Font firstFont = firstLabel.getFont();
			Font newFirstFont = new Font(firstFont.getName(), Font.BOLD, firstFont.getSize());
			firstLabel.setFont(newFirstFont);

			Font secondFont = secondLabel.getFont();
			Font newSecondFont = new Font(secondFont.getName(), Font.BOLD, secondFont.getSize());
			secondLabel.setFont(newSecondFont);
		}


		return newPanel;
	}


	public class ForceVisualizationCirclePanel extends JPanel 
	{
		ChiLATCell selectedCell;

		ForceVisualizationCirclePanel(ChiLATCell selectedCell) 
		{
			this.setPreferredSize(new Dimension(200, 200));
			this.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.selectedCell = selectedCell;
		}
		
		public void updateContents(ChiLATCell selectedCell)
		{
			this.selectedCell = selectedCell;
			this.revalidate();
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

			Vector2D springForceVector = selectedCell.getSpringForceVector();
			Vector2D repulsionForceVector = selectedCell.getRepulsionForceVector();
			Vector2D gravityForceVector = selectedCell.getGravityForceVector();

			double springForceScale = selectedCell.getNormalizedSpringForce() * CIRCLE_RADIUS;
			double repulsionForceScale = selectedCell.getNormalizedRepulsionForce() * CIRCLE_RADIUS;
			double gravityForceScale = selectedCell.getNormalizedGravityForce() * CIRCLE_RADIUS;

			final float [] dash_line = {8.0f};
			Stroke forceLineStroke = new BasicStroke(2.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND);

			g.drawOval(getWidth()/2 - CIRCLE_RADIUS, getHeight()/2 - CIRCLE_RADIUS, 2*CIRCLE_RADIUS, 2*CIRCLE_RADIUS);
			g.setStroke(forceLineStroke);
			drawArrowAndLine(springForceColor, g, springForceVector, springForceScale);
			drawArrowAndLine(repulsionForceColor, g, repulsionForceVector, repulsionForceScale);
			drawArrowAndLine(gravityForceColor, g, gravityForceVector, gravityForceScale);
		}

		public void drawArrowAndLine(Color color, Graphics g, Vector2D direction, double forceScaleFactor)
		{
			g.setColor(color);
			Vector2D circleCenter = new Vector2D(getWidth()/2, getHeight()/2);
			Vector2D arrowStart = circleCenter.add(direction.scale(forceScaleFactor));

			g.drawLine((int)circleCenter.getX(), 
					(int)circleCenter.getY(), 
					(int)arrowStart.getX(), 
					(int)arrowStart.getY());

			Vector2D arrowHeight = direction.scale(8);
			Vector2D arrowWidth = direction.scale(4);
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
	}
}