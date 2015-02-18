package View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Controller.EditorActions;

@SuppressWarnings("serial")
public class AnimationPlayBackPanel extends JPanel
{	
	
	private CircularAnimationControlButton stopButton, forwardButton, rewindButton;
	private PlayPauseButton playPauseButton;
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
		this.setBorder(new TitledBorder("Animation Control"));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		rewindButton = new CircularAnimationControlButton(new EditorActions.RewindAnimationAction());
		forwardButton = new CircularAnimationControlButton(new EditorActions.ForwardAnimationAction());
		stopButton = new CircularAnimationControlButton(new EditorActions.StopAction());
		playPauseButton = new PlayPauseButton(new EditorActions.PlayAnimationAction());
		this.add(Box.createHorizontalGlue());
		this.add(rewindButton);
		this.add(stopButton);
		this.add(playPauseButton);
		this.add(forwardButton);
		this.add(Box.createHorizontalGlue());

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
	
	
}
