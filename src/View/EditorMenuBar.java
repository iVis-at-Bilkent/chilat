package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class EditorMenuBar extends JMenuBar implements ActionListener
{
	//Main File Menu
	private JMenu fileMenu;
    // Layout Menu
    private JMenu layoutMenu;
    // File Menu contents
    private JMenuItem saveItem;
    private JMenuItem loadItem;   
    
	public EditorMenuBar()
	{
	    // File Menu
	    fileMenu = new JMenu("File");
	    this.add(fileMenu);
	    
	    // Layout Menu
	    layoutMenu = new JMenu("Layout");
	    this.add(layoutMenu);

	    // File Menu contents
	    saveItem = new JMenuItem("Save");
	    saveItem.addActionListener(this);
	    loadItem = new JMenuItem("Load");
	    loadItem.addActionListener(this);
	    fileMenu.add(saveItem);
	    fileMenu.add(loadItem);
	    
	    //Layout Menu contents
	    JMenuItem coseLayoutItem = new JMenuItem("CoSE Layout");
	    coseLayoutItem.addActionListener(this);
	    JMenuItem layoutPropertiesItem = new JMenuItem("Layout Properties");
	    layoutPropertiesItem.addActionListener(this);
	    layoutMenu.add(coseLayoutItem);
	    layoutMenu.add(layoutPropertiesItem);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(((JMenuItem)arg0.getSource()).getText().equals("Load"))
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
		else if(((JMenuItem)arg0.getSource()).getText().equals("CoSE Layout"))
		{
			ChilayLayoutAnimationToolMain.getInstance().performLayout();
		}
	}
}
