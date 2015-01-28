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

public class EditorToolBar extends JToolBar
{
	JButton loadButton; 
	JButton saveButton;
	JButton performLayoutButton; 
	JCheckBox animationCheckBox;
	
	public EditorToolBar()
	{
		super();
		
		animationCheckBox = new JCheckBox(new EditorActions.AnimateOnLayoutCheckBoxAction());
		animationCheckBox.setText("Animate on layout");
	    loadButton = new JButton(new EditorActions.LoadGraphAction());
	    saveButton = new JButton(new EditorActions.SaveGraphAction());
	    performLayoutButton = new JButton(new EditorActions.PerformLayoutAction());
	    
	    this.add(loadButton);
	    this.add(saveButton);
	    this.addSeparator();
	    this.add(performLayoutButton);
	    this.add(animationCheckBox);
	    
	    this.setFloatable(false);
	}
}
