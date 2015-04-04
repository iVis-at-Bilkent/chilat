package View;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

import org.ivis.layout.LayoutOptionsPack;

import Controller.EditorActions;
import Controller.EditorActions.IncrementalLayoutCheckboxAction;
import Controller.EditorActions.LayoutQualityRadioButtonAction;
import Controller.EditorActions.UniformLeafNodeSizesCheckboxAction;
import View.ChiAnimSliderPanels.ForceTuningSliderPanel;
import View.ChiLATConstants.ForceTuningParameterName;
import View.ChiLATConstants.LayoutQualityParameterName;

public class LayoutOptionsPanel extends JPanel
{

	public LayoutOptionsPanel()
	{
		//GUI components about force tuning
		JPanel forcePanel = new JPanel();
		forcePanel.setLayout(new BoxLayout(forcePanel, BoxLayout.Y_AXIS));
		forcePanel.setBorder(new TitledBorder("Force Tuning"));
		
		JPanel compoundGravityPanel = new ForceTuningSliderPanel("Compound Gravity Strength: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityStrength, 
				ForceTuningParameterName.COMP_GRAVITY_FORCE);
		JPanel compoundGravityRangePanel = new ForceTuningSliderPanel("Compound Gravity Range: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityRange, 
				ForceTuningParameterName.COMP_GRAVITY_RANGE);
		
		forcePanel.add(compoundGravityPanel);
		forcePanel.add(compoundGravityRangePanel);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(forcePanel);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(Box.createHorizontalGlue());
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		
	}
}
