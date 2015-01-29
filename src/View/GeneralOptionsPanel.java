package View;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

public class GeneralOptionsPanel extends JPanel
{
	public GeneralOptionsPanel()
	{
		//GUI components about layout quality
		JPanel layoutQualityPanel = new JPanel();
		layoutQualityPanel.setLayout(new BoxLayout(layoutQualityPanel, BoxLayout.Y_AXIS));
		layoutQualityPanel.setBorder(new TitledBorder("Layout Quality"));
		ButtonGroup layoutQualityButtonGroup = new ButtonGroup();
		JRadioButton draftButton = new JRadioButton("Draft");
		JRadioButton defaultButton = new JRadioButton("Default");
		JRadioButton poorButton = new JRadioButton("Poor");
		layoutQualityButtonGroup.add(draftButton);
		layoutQualityButtonGroup.add(defaultButton);
		layoutQualityButtonGroup.add(poorButton);
		layoutQualityPanel.add(draftButton);
		layoutQualityPanel.add(defaultButton);
		layoutQualityPanel.add(poorButton);
		
		//GUI components about other components in this panel
		JPanel otherOptionsPanel = new JPanel();
		otherOptionsPanel.setLayout(new BoxLayout(otherOptionsPanel, BoxLayout.Y_AXIS));
		otherOptionsPanel.setBorder(new TitledBorder("Other Options"));
		JCheckBox incrementalLayoutCheckBox = new JCheckBox("Incremental Layout");
		JCheckBox uniformLeafNodeSizesCheckBox = new JCheckBox("Uniform Leaf Node Sizes");
		otherOptionsPanel.add(incrementalLayoutCheckBox);
		otherOptionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		otherOptionsPanel.add(uniformLeafNodeSizesCheckBox);
		
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new GridLayout(2,1));
		this.add(layoutQualityPanel);
		this.add(otherOptionsPanel);

	}
}
