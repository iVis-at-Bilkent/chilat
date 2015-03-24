package View;

import java.awt.Component;
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
import Controller.EditorActions;
import Controller.EditorActions.*;

public class GeneralOptionsPanel extends JPanel
{
	public GeneralOptionsPanel()
	{
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		JCheckBox forceDetailsVisibleCheckbox = new JCheckBox(new EditorActions.ForceDetailsVisibleChecboxAction());
		forceDetailsVisibleCheckbox.setText("Force Details Visible");
		JCheckBox zoomToFitCheckbox = new JCheckBox(new EditorActions.AutoZoomToFitCheckboxAction());
		zoomToFitCheckbox.setText("Zoom to Fit During Layout");
		optionsPanel.add(forceDetailsVisibleCheckbox);
		optionsPanel.add(zoomToFitCheckbox);
		optionsPanel.setBorder(new TitledBorder("Options"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(optionsPanel);
		this.add(Box.createHorizontalGlue());
	}
}
