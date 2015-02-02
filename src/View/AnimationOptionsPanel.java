package View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class AnimationOptionsPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnimationOptionsPanel()
	{
		//GUI components about layout quality
		JPanel animationPanel = new JPanel();
		animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.Y_AXIS));
		animationPanel.setBorder(new TitledBorder("Animation"));
		JCheckBox animationDuringLayoutCheckBox = new JCheckBox("Animation During Layout");
		animationPanel.add(animationDuringLayoutCheckBox);
		animationPanel.add(Box.createHorizontalGlue());
		
		JPanel animationTypePanel = new JPanel();
		animationTypePanel.setLayout(new BoxLayout(animationTypePanel, BoxLayout.Y_AXIS));
		animationTypePanel.setBorder(new TitledBorder("Animation Type"));
		ButtonGroup animationTypeButtonGroup = new ButtonGroup();
		JRadioButton keyFrameAnimationRadioButton = new JRadioButton("Key Frame Animation");
		JRadioButton realTimeAnimationRadioButton = new JRadioButton("Real Time Animation");
		animationTypeButtonGroup.add(keyFrameAnimationRadioButton);
		animationTypeButtonGroup.add(realTimeAnimationRadioButton);
		animationTypePanel.add(keyFrameAnimationRadioButton);
		animationTypePanel.add(realTimeAnimationRadioButton);
		animationTypePanel.add(Box.createHorizontalGlue());
		
		JPanel keyFrameAnimationOptionsPanel = new JPanel();
		keyFrameAnimationOptionsPanel.setLayout(new BoxLayout(keyFrameAnimationOptionsPanel, BoxLayout.Y_AXIS));
		keyFrameAnimationOptionsPanel.setBorder(new TitledBorder("Key Frame Animation Properties"));
		JPanel animationCapturePeriodPanel = createPanel("Animation Capture Period");
		keyFrameAnimationOptionsPanel.add(animationCapturePeriodPanel);
		keyFrameAnimationOptionsPanel.add(Box.createHorizontalGlue());
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(animationPanel);
		this.add(animationTypePanel);
		this.add(Box.createHorizontalGlue());
		this.add(keyFrameAnimationOptionsPanel);
		this.add(Box.createVerticalGlue());
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
