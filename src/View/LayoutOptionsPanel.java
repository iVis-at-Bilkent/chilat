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
		
		//GUI components about layout quality
		JPanel layoutQualityPanel = new JPanel();
		layoutQualityPanel.setLayout(new BoxLayout(layoutQualityPanel, BoxLayout.Y_AXIS));
		layoutQualityPanel.setBorder(new TitledBorder("Layout Quality"));
		ButtonGroup layoutQualityButtonGroup = new ButtonGroup();
		JRadioButton draftButton = new JRadioButton(new LayoutQualityRadioButtonAction(LayoutQualityParameterName.DRAFT));
		draftButton.setText("Draft");
		JRadioButton defaultButton = new  JRadioButton(new LayoutQualityRadioButtonAction(LayoutQualityParameterName.DEFAULT));
		defaultButton.setText("Default");
		JRadioButton poorButton = new JRadioButton(new LayoutQualityRadioButtonAction(LayoutQualityParameterName.POOR));
		poorButton.setText("Poor");
		layoutQualityButtonGroup.add(draftButton);
		layoutQualityButtonGroup.add(defaultButton);
		layoutQualityButtonGroup.add(poorButton);
		layoutQualityPanel.add(draftButton);
		layoutQualityPanel.add(Box.createVerticalGlue());
		layoutQualityPanel.add(defaultButton);
		layoutQualityPanel.add(Box.createVerticalGlue());

		layoutQualityPanel.add(poorButton);
		layoutQualityPanel.add(Box.createHorizontalGlue());
		layoutQualityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		
		//GUI components about other components in this panel
		JPanel otherOptionsPanel = new JPanel();
		otherOptionsPanel.setLayout(new BoxLayout(otherOptionsPanel, BoxLayout.Y_AXIS));
		otherOptionsPanel.setBorder(new TitledBorder("Other Options"));
		JCheckBox incrementalLayoutCheckBox = new JCheckBox(new IncrementalLayoutCheckboxAction());
		incrementalLayoutCheckBox.setText("Incremental Layout");
		JCheckBox uniformLeafNodeSizesCheckBox = new JCheckBox(new UniformLeafNodeSizesCheckboxAction());
		uniformLeafNodeSizesCheckBox.setText("Uniform Leaf Node Sizes");
		otherOptionsPanel.add(incrementalLayoutCheckBox);
		otherOptionsPanel.add(Box.createVerticalGlue());
		otherOptionsPanel.add(uniformLeafNodeSizesCheckBox);
		otherOptionsPanel.add(Box.createHorizontalGlue());
		otherOptionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
						
		defaultButton.doClick();
		
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(layoutQualityPanel);
		this.add(otherOptionsPanel);
		this.add(forcePanel);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(Box.createHorizontalGlue());
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		
	}
}
