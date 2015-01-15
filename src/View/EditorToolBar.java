package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		animationCheckBox.setText("Animate During Layout");
	    loadButton = new JButton(new EditorActions.LoadGraphAction());
	    loadButton.setText("Load");
	    saveButton = new JButton(new EditorActions.SaveGraphAction());
	    saveButton.setText("Save");
	    performLayoutButton = new JButton(new EditorActions.PerformLayoutAction());
	    performLayoutButton.setText("Perform Layout");
	    
	    this.add(loadButton);
	    this.addSeparator();
	    this.add(saveButton);
	    this.addSeparator();
	    this.add(performLayoutButton);
	    this.addSeparator();
	    this.add(animationCheckBox);
	    
	    this.setFloatable(false);
	}
}
