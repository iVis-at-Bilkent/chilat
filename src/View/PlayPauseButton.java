package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;

import Controller.EditorActions;

@SuppressWarnings("serial")
public class PlayPauseButton extends CircularAnimationControlButton implements ActionListener
{
	private boolean isPlayButton = true; // Means play button 

	public PlayPauseButton(Action action)
	{
		super(action);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent evt)
	{
		if (!isPlayButton) 
		{
			this.changeToPlayButton();
		}
		else
		{
			this.changeToPauseButton();
		}
	}
	
	public void changeToPlayButton()
	{
		this.setAction(new EditorActions.PlayAnimationAction());
		isPlayButton = true;
	}
	
	public void changeToPauseButton()
	{
		this.setAction(new EditorActions.PauseAnimationAction());
		isPlayButton = false;
	}
	
}
