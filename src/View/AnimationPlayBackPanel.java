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
		JButton rewindButton = new JButton(new EditorActions.PlayPauseAction());
		JButton forwardButton = new JButton(new EditorActions.PlayPauseAction());
		JButton playPauseButton = new JButton(new EditorActions.PlayPauseAction());
		this.add(Box.createHorizontalGlue());
		this.add(rewindButton);
		this.add(playPauseButton);
		this.add(forwardButton);
		this.add(Box.createHorizontalGlue());

		this.setAlignmentX(LEFT_ALIGNMENT);
	}
}
