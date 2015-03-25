package View;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.EditorActions;

@SuppressWarnings("serial")
public class AnimationPlayBackPanel extends JPanel implements ChangeListener
{	
	
	private CircularAnimationControlButton stopButton, forwardButton, rewindButton;
	private PlayPauseButton playPauseButton;
	private JSlider animationTimeLine;
	private static AnimationPlayBackPanel singletonInstance;
	
	public static AnimationPlayBackPanel getInstance()
	{
		if (singletonInstance == null) 
		{
			singletonInstance = new AnimationPlayBackPanel();
		}
		return singletonInstance;
	}
	
	
	private AnimationPlayBackPanel()
	{
		super();
		JPanel layoutAnimationButtonsPanel = new JPanel();
		this.setBorder(new TitledBorder("Animation Control"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		layoutAnimationButtonsPanel.setLayout(new BoxLayout(layoutAnimationButtonsPanel, BoxLayout.X_AXIS));
		rewindButton = new CircularAnimationControlButton(new EditorActions.RewindAnimationAction());
		forwardButton = new CircularAnimationControlButton(new EditorActions.ForwardAnimationAction());
		stopButton = new CircularAnimationControlButton(new EditorActions.StopAction());
		playPauseButton = new PlayPauseButton(new EditorActions.PlayAnimationAction());
		animationTimeLine = new JSlider(JSlider.HORIZONTAL, 0);

		this.animationTimeLine.addChangeListener(this);
		layoutAnimationButtonsPanel.add(Box.createHorizontalGlue());
		layoutAnimationButtonsPanel.add(rewindButton);
		layoutAnimationButtonsPanel.add(stopButton);
		layoutAnimationButtonsPanel.add(playPauseButton);
		layoutAnimationButtonsPanel.add(forwardButton);
		layoutAnimationButtonsPanel.add(Box.createHorizontalGlue());
		
		JPanel performLayoutPanel = new JPanel();
		//performLayoutPanel.setBorder(new TitledBorder(""));
		performLayoutPanel.setLayout(new BoxLayout(performLayoutPanel, BoxLayout.Y_AXIS));
		JButton performLayoutButton = new JButton(new EditorActions.PerformLayoutAction());
		performLayoutButton.setText("Perform Layout");
		performLayoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		performLayoutPanel.add(Box.createHorizontalGlue());
		performLayoutPanel.add(performLayoutButton);
		performLayoutPanel.add(Box.createHorizontalGlue());
		
		//
		this.add(animationTimeLine);
		this.add(layoutAnimationButtonsPanel);
		this.add(performLayoutPanel);
		

		this.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	public void updateGUIAnimationEnd()
	{
		this.rewindButton.setEnabled(false);
		this.forwardButton.setEnabled(false);
		this.stopButton.setEnabled(false);
		this.playPauseButton.changeToPlayButton();
	}
	
	public void updateGUIAnimationStart()
	{
		this.rewindButton.setEnabled(true);
		this.forwardButton.setEnabled(true);
		this.stopButton.setEnabled(true);
	}
	
	public void updateAnimationTimeLine(float value)
	{
		this.animationTimeLine.setValue((int)value);
	}
	
	public PlayPauseButton getPlayPauseButton() 
	{
		return playPauseButton;
	}


	public CircularAnimationControlButton getStopButton() {
		return stopButton;
	}


	public CircularAnimationControlButton getForwardButton() {
		return forwardButton;
	}


	public CircularAnimationControlButton getRewindButton() {
		return rewindButton;
	}


	public void setAnimationTimelineBounds(int totalKeyFrameCount) 
	{
		this.animationTimeLine.setValue(0);
		this.animationTimeLine.setMinimum(0);
		this.animationTimeLine.setMaximum(totalKeyFrameCount);
	}


	@Override
	//TODO refactor this to editor actions
	public void stateChanged(ChangeEvent arg0)
	{
		ChilayLayoutAnimationToolMain.getInstance().animationTotalTime = this.animationTimeLine.getValue();
		ChilayLayoutAnimationToolMain.getInstance().currentKeyFrameNumber = (int)(ChilayLayoutAnimationToolMain.getInstance().animationTotalTime);
		ChilayLayoutAnimationToolMain.getInstance().interpolatedFrameRemainder = (ChilayLayoutAnimationToolMain.getInstance().animationTotalTime) - ChilayLayoutAnimationToolMain.getInstance().currentKeyFrameNumber;
	}
	
	
}
