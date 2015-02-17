package View;

import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import org.ivis.layout.LayoutOptionsPack;

import View.ChiAnimSliderPanels.ForceTuningSliderPanel;
import View.ChiLATConstants.ForceTuningParameterName;

public class LayoutOptionsPanel extends JPanel
{

	public LayoutOptionsPanel()
	{
		//GUI components about layout quality
		JPanel forcePanel = new JPanel();
		forcePanel.setLayout(new BoxLayout(forcePanel, BoxLayout.Y_AXIS));
		forcePanel.setBorder(new TitledBorder("Force Tuning"));
		
		JPanel springForcePanel = new ForceTuningSliderPanel("Spring Force: ", 
				LayoutOptionsPack.getInstance().getCoSE().springStrength, 
				ForceTuningParameterName.SPRING_FORCE);
		JPanel repulsionPanel = new ForceTuningSliderPanel("Repulsion Force: ", 
				LayoutOptionsPack.getInstance().getCoSE().repulsionStrength, 
				ForceTuningParameterName.REPULSION_FORCE);
		JPanel gravityPanel  = new ForceTuningSliderPanel("Gravity Force: ", 
				LayoutOptionsPack.getInstance().getCoSE().gravityStrength, 
				ForceTuningParameterName.GRAVITY_FORCE);
		JPanel compoundGravityPanel = new ForceTuningSliderPanel("Compound Gravity Force: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityStrength, 
				ForceTuningParameterName.COMP_GRAVITY_FORCE);
		JPanel compoundGravityRangePanel = new ForceTuningSliderPanel("Compound Gravity Range: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityRange, 
				ForceTuningParameterName.COMP_GRAVITY_RANGE);
		JPanel gravityRangePanel = new ForceTuningSliderPanel("Gravity Range: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityRange, 
				ForceTuningParameterName.GRAVITY_RANGE);

		forcePanel.add(springForcePanel);
		forcePanel.add(repulsionPanel);
		forcePanel.add(gravityPanel);
		forcePanel.add(compoundGravityPanel);
		forcePanel.add(gravityRangePanel);
		forcePanel.add(compoundGravityRangePanel);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(forcePanel);
		
	}
}
