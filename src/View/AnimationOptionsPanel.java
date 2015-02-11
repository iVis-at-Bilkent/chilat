package View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.event.ChangeListener;

import View.ChiAnimSliderPanels.AnimationSpeedSliderPanel;

import Controller.EditorActions;

public class AnimationOptionsPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnimationOptionsPanel()
	{
		super();
		
		//
		JPanel animationPanel = new JPanel();
		animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.Y_AXIS));
		animationPanel.setBorder(new TitledBorder("Animation"));
		JCheckBox animationDuringLayoutCheckBox = new JCheckBox(new EditorActions.AnimateOnLayoutCheckBoxAction());
		JButton performLayoutButton = new JButton(new EditorActions.PerformLayoutAction());
		performLayoutButton.setText("Perform Layout");
		
		animationDuringLayoutCheckBox.setText("Animation During Layout");
		animationPanel.add(animationDuringLayoutCheckBox);
		animationPanel.add(Box.createHorizontalGlue());
		animationPanel.add(performLayoutButton);
		animationPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		//
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
		animationTypePanel.setAlignmentX(LEFT_ALIGNMENT);
		
		//
		JPanel keyFrameAnimationOptionsPanel = new JPanel();
		keyFrameAnimationOptionsPanel.setLayout(new BoxLayout(keyFrameAnimationOptionsPanel, BoxLayout.Y_AXIS));
		keyFrameAnimationOptionsPanel.setBorder(new TitledBorder("Key Frame Animation Properties"));
		AnimationSpeedSliderPanel animationCapturePeriodPanel = new AnimationSpeedSliderPanel("Key Frame Capture Period",0,0,0);
		AnimationSpeedSliderPanel animationSpeedPanel = new AnimationSpeedSliderPanel("Animation Speed", 10, 200, 100);
		keyFrameAnimationOptionsPanel.add(animationCapturePeriodPanel);
		keyFrameAnimationOptionsPanel.add(animationSpeedPanel);
		keyFrameAnimationOptionsPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		//
		JPanel realTimeAnimationPanel = new JPanel();
		realTimeAnimationPanel.setLayout(new BoxLayout(realTimeAnimationPanel, BoxLayout.Y_AXIS));
		realTimeAnimationPanel.setBorder(new TitledBorder("Real Time Animation Properties"));
		AnimationSpeedSliderPanel simulationTimeStepPanel = new AnimationSpeedSliderPanel("Simulation Timestep", 0, 0, 0);
		realTimeAnimationPanel.add(simulationTimeStepPanel);
		realTimeAnimationPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		//
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(animationPanel);
		this.add(animationTypePanel);
		this.add(keyFrameAnimationOptionsPanel);
		this.add(realTimeAnimationPanel);
		this.add(new AnimationPlayBackPanel());
		this.setAlignmentX(LEFT_ALIGNMENT);

	}
	
}
