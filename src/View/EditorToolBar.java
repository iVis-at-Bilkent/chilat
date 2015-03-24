package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import Controller.EditorActions;
import Controller.EditorActions.ZoomAction;

public class EditorToolBar extends JToolBar
{
	JButton loadButton, 
	 		saveButton,
	 		zoomInButton,
	 		zoomOutButton,
	 		zoomToFitButton;
	
	public EditorToolBar()
	{
		super();
	    loadButton = new JButton(new EditorActions.LoadGraphAction());
	    saveButton = new JButton(new EditorActions.SaveGraphAction());
	    zoomInButton = new JButton(new EditorActions.ZoomAction(ZoomAction.ZOOM_IN));
	    zoomOutButton = new JButton(new EditorActions.ZoomAction(ZoomAction.ZOOM_OUT));
	    zoomToFitButton = new JButton(new EditorActions.ZoomAction(ZoomAction.ZOOM_FIT));

	    
	    this.add(loadButton);
	    this.add(saveButton);
	    this.addSeparator();
	    this.add(zoomInButton);
	    this.add(zoomOutButton);
	    this.add(zoomToFitButton);
	    
	    this.setFloatable(false);
	}
}
