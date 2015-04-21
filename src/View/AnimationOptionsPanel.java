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

import org.ivis.layout.LayoutOptionsPack;

import com.sun.org.apache.bcel.internal.generic.D2F;

import View.ChiLATSliderPanels.AnimationSpeedSliderPanel;
import View.ChiLATSliderPanels.KeyframeCapturePeriodSliderPanel;
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
		/*JPanel animationPanel = new JPanel();
		animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.Y_AXIS));
		animationPanel.setBorder(new TitledBorder("Animation"));
		
		animationPanel.add(animationDuringLayoutCheckBox);
		animationPanel.add(Box.createHorizontalGlue());
		animationPanel.setAlignmentX(LEFT_ALIGNMENT);*/
		

		
		//
		JPanel duringAnimationOptionsPanel = new JPanel();
		duringAnimationOptionsPanel.setLayout(new BoxLayout(duringAnimationOptionsPanel, BoxLayout.Y_AXIS));
		duringAnimationOptionsPanel.setBorder(new TitledBorder("During Layout"));
		JCheckBox forceDetailsVisibleCheckbox = new JCheckBox(new EditorActions.ForceDetailsVisibleChecboxAction());
		forceDetailsVisibleCheckbox.setText("Force Details Visible");
		JCheckBox zoomToFitCheckbox = new JCheckBox(new EditorActions.AutoZoomToFitCheckboxAction());
		zoomToFitCheckbox.setText("Zoom to Fit During Layout");
		JCheckBox animationDuringLayoutCheckBox = new JCheckBox(new EditorActions.AnimateOnLayoutCheckBoxAction());		
		animationDuringLayoutCheckBox.setText("Animate");
		JCheckBox showActualDisplacement = new JCheckBox(new EditorActions.ShowActualDisplacementCheckboxAction());
		showActualDisplacement.setText("Show Actual Displacement");
		duringAnimationOptionsPanel.add(animationDuringLayoutCheckBox);
		duringAnimationOptionsPanel.add(forceDetailsVisibleCheckbox);
		duringAnimationOptionsPanel.add(zoomToFitCheckbox);
		duringAnimationOptionsPanel.add(Box.createHorizontalGlue());
		duringAnimationOptionsPanel.add(showActualDisplacement);
		duringAnimationOptionsPanel.setAlignmentX(LEFT_ALIGNMENT);

		
		//
		JPanel animationTypePanel = new JPanel();
		animationTypePanel.setLayout(new BoxLayout(animationTypePanel, BoxLayout.Y_AXIS));
		animationTypePanel.setBorder(new TitledBorder("Animation Type"));
		ButtonGroup animationTypeButtonGroup = new ButtonGroup();
		JRadioButton keyFrameAnimationRadioButton = new JRadioButton("Key Frame Animation");
		keyFrameAnimationRadioButton.setSelected(true);
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
		KeyframeCapturePeriodSliderPanel animationCapturePeriodPanel = new KeyframeCapturePeriodSliderPanel("Key Frame Capture Period: ", 
				LayoutOptionsPack.getInstance().getGeneral().animationPeriod);
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
		this.add(duringAnimationOptionsPanel);
		this.add(animationTypePanel);
		this.add(keyFrameAnimationOptionsPanel);
		this.add(realTimeAnimationPanel);
		this.setAlignmentX(LEFT_ALIGNMENT);
	}
	
}
