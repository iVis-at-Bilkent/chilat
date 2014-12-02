package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

public class EditorToolBar extends JToolBar implements ActionListener
{
	JButton loadButton; 
	JButton saveButton;
	JButton performLayoutButton; 
	JCheckBox animationCheckBox;
	
	public EditorToolBar()
	{
		super();
		
		animationCheckBox = new JCheckBox("Animate During Layout");
		animationCheckBox.addActionListener(this);
	    loadButton = new JButton("Load");
	    loadButton.addActionListener(this);
	    saveButton = new JButton("Save");
	    saveButton.addActionListener(this);
	    performLayoutButton = new JButton("Perform Layout");
	    performLayoutButton.addActionListener(this);
	    
	    this.add(loadButton);
	    this.addSeparator();
	    this.add(saveButton);
	    this.addSeparator();
	    this.add(performLayoutButton);
	    this.addSeparator();
	    this.add(animationCheckBox);
	    
	    this.setFloatable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getSource() instanceof JButton) 
		{
			if(((JButton)arg0.getSource()).getText().equals("Load"))
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Open File");
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
				  ChilayLayoutAnimationToolMain.getInstance().loadGraph(chooser.getSelectedFile().getAbsolutePath());
				} 
			}
			else if(((JButton)arg0.getSource()).getText().equals("Perform Layout"))
			{
				ChilayLayoutAnimationToolMain.getInstance().runLayout();
			}
		}
		else
		{
			if(((JCheckBox)arg0.getSource()).getText().equals("Animate During Layout"))
			{
				ChilayLayoutAnimationToolMain.getInstance().setAnimateOn(((JCheckBox)arg0.getSource()).isSelected());
			}
		}
	}
}
