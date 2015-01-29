package View;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

public class LayoutOptionsPanel extends JPanel
{

	public LayoutOptionsPanel()
	{
		//GUI components about layout quality
		JPanel forcePanel = new JPanel();
		forcePanel.setLayout(new GridLayout(6, 1));
		forcePanel.setBorder(new TitledBorder("Force Tuning"));
		
		JPanel springForcePanel = createPanel("Spring");
		JPanel repulsionPanel = createPanel("Repulsion");
		JPanel gravityPanel  = createPanel("Gravity");
		JPanel compoundGravityPanel = createPanel("Compound Gravity");
		JPanel compoundGravityRangePanel = createPanel("Comp Gravity Range");
		JPanel gravityRangePanel = createPanel("Gravity Range");

		forcePanel.add(springForcePanel);
		forcePanel.add(repulsionPanel);
		forcePanel.add(gravityPanel);
		forcePanel.add(compoundGravityPanel);
		forcePanel.add(gravityRangePanel);
		forcePanel.add(compoundGravityRangePanel);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(forcePanel);
	}
	
	private JPanel createPanel(String label)
	{
		JPanel newPanel = new JPanel(new GridLayout(1, 2));
		JLabel newLabel = new JLabel(label);
		JSlider newSlider = new JSlider();
		newPanel.add(newLabel);
		newPanel.add(newSlider);
		return newPanel;
	}
	
}
