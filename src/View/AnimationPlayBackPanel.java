package View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Controller.EditorActions;

public class AnimationPlayBackPanel extends JPanel
{
	public AnimationPlayBackPanel()
	{
		super();
		this.setBorder(new TitledBorder("Animation Control"));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		AnimationControlButton rewindButton = new AnimationControlButton(new EditorActions.RewindAnimationAction());
		AnimationControlButton forwardButton = new AnimationControlButton(new EditorActions.ForwardAnimationAction());
		AnimationControlButton stopButton = new AnimationControlButton(new EditorActions.StopAction());
		AnimationControlButton playPauseButton = new AnimationControlButton(new EditorActions.PlayPauseAnimationAction());
		this.add(Box.createHorizontalGlue());
		this.add(rewindButton);
		this.add(stopButton);
		this.add(playPauseButton);
		this.add(forwardButton);
		this.add(Box.createHorizontalGlue());

		this.setAlignmentX(LEFT_ALIGNMENT);
	}
}
