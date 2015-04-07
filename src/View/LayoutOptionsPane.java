package View;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import org.ivis.layout.LayoutOptionsPack;
import View.ChiLATSliderPanels.ForceTuningSliderPanel;
import View.ChiLATConstants.ForceTuningParameterName;
import Controller.EditorActions.MultiLevelScalingCheckboxAction;
import Controller.EditorActions.SmartEdgeLengthCalculationCheckboxAction;
import Controller.EditorActions.SmartRepulsionRangeCheckboxAction;



public class LayoutOptionsPane extends JTabbedPane
{

	public LayoutOptionsPane()
	{
		//GUI components about force tuning
		super();
		
		JPanel mainContainerPanel = new JPanel();
		mainContainerPanel.setLayout(new BoxLayout(mainContainerPanel, BoxLayout.Y_AXIS));
		
		JPanel CoSEForceTuningPanel = new JPanel();
		CoSEForceTuningPanel.setLayout(new BoxLayout(CoSEForceTuningPanel, BoxLayout.Y_AXIS));
		CoSEForceTuningPanel.setBorder(new TitledBorder("Force Tuning"));
		
		JPanel compoundGravityPanel = new ForceTuningSliderPanel("Compound Gravity Strength: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityStrength, 
				ForceTuningParameterName.COMP_GRAVITY_FORCE);
		JPanel compoundGravityRangePanel = new ForceTuningSliderPanel("Compound Gravity Range: ", 
				LayoutOptionsPack.getInstance().getCoSE().compoundGravityRange, 
				ForceTuningParameterName.COMP_GRAVITY_RANGE);
		
		CoSEForceTuningPanel.add(compoundGravityPanel);
		CoSEForceTuningPanel.add(compoundGravityRangePanel);
		
		
		CoSEForceTuningPanel.setLayout(new BoxLayout(CoSEForceTuningPanel, BoxLayout.Y_AXIS));
		CoSEForceTuningPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		//GUI components about other components in this panel
		JPanel otherOptionsPanel = new JPanel();
		otherOptionsPanel.setLayout(new BoxLayout(otherOptionsPanel, BoxLayout.Y_AXIS));
		otherOptionsPanel.setBorder(new TitledBorder("Miscellaneous "));
		JCheckBox smartEdgeLengthCheckbox = new JCheckBox(new SmartEdgeLengthCalculationCheckboxAction());
		smartEdgeLengthCheckbox.setText("Smart Edge Length Calculation");
		JCheckBox multiLevelScalingCheckbox = new JCheckBox(new MultiLevelScalingCheckboxAction());
		multiLevelScalingCheckbox.setText("Multi Level Scaling");
		JCheckBox smartRepulsionRangeCalcCheckBox = new JCheckBox(new SmartRepulsionRangeCheckboxAction());
		smartRepulsionRangeCalcCheckBox.setText("Smart Repulsion Range Calculation");
		
		
		otherOptionsPanel.add(smartEdgeLengthCheckbox);
		otherOptionsPanel.add(smartRepulsionRangeCalcCheckBox);
		otherOptionsPanel.add(multiLevelScalingCheckbox);
		otherOptionsPanel.add(Box.createHorizontalGlue());
		otherOptionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
	
		
		mainContainerPanel.add(CoSEForceTuningPanel);
		mainContainerPanel.add(otherOptionsPanel);
		
		this.addTab("CoSE", mainContainerPanel);
	}
}
