package View;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.text.LayoutQueue;

import View.ChiLATConstants;
import View.ChiLATConstants.LayoutQualityParameterName;
import Controller.EditorActions.*;

public class GeneralOptionsPanel extends JPanel
{
	public GeneralOptionsPanel()
	{
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
						
		defaultButton.doClick();

		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(layoutQualityPanel);
		this.add(otherOptionsPanel);

	}
}
