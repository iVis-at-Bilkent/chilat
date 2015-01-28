package View;

import java.awt.Dimension;
import java.awt.GridLayout;

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
		layoutQualityPanel.setLayout(new GridLayout(3, 1));
		layoutQualityPanel.setBorder(new TitledBorder("LayoutQuality"));
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
		otherOptionsPanel.setLayout(new GridLayout(2, 1));
		otherOptionsPanel.setBorder(new TitledBorder("OtherOptions"));
		JCheckBox incrementalLayoutCheckBox = new JCheckBox("Incremental Layout");
		JCheckBox uniformLeafNodeSizesCheckBox = new JCheckBox("Uniform Leaf Node Sizes");
		otherOptionsPanel.add(incrementalLayoutCheckBox);
		otherOptionsPanel.add(uniformLeafNodeSizesCheckBox);

		
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLayout(new GridLayout(2, 1));
		this.add(layoutQualityPanel);
		this.add(otherOptionsPanel);

	}
}
