package View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Controller.EditorActions;

public class AnimationPlayBackPanel extends JPanel
{	
	private AnimationControlButton playPauseButton, stopButton, forwardButton, rewindButton;
	private static AnimationPlayBackPanel singletonInstance;
	
	public static AnimationPlayBackPanel getInstance()
	{
		if (singletonInstance == null) 
		{
			return new AnimationPlayBackPanel();
		}
		else
			return singletonInstance;
	}
	
	
	private AnimationPlayBackPanel()
	{
		super();
		this.setBorder(new TitledBorder("Animation Control"));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		rewindButton = new AnimationControlButton(new EditorActions.RewindAnimationAction());
		forwardButton = new AnimationControlButton(new EditorActions.ForwardAnimationAction());
		stopButton = new AnimationControlButton(new EditorActions.StopAction());
		playPauseButton = new AnimationControlButton(new EditorActions.PlayPauseAnimationAction());
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
	}
	
	public void updateGUIAnimationStart()
	{
		this.rewindButton.setEnabled(true);
		this.forwardButton.setEnabled(true);
		this.stopButton.setEnabled(true);
	}
	
	
}
