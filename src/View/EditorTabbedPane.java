package View;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditorTabbedPane extends JTabbedPane implements ChangeListener
{
	public EditorTabbedPane()
	{
		super();
		this.addTab("General", new GeneralOptionsPanel());
		this.addTab("Layout", new LayoutOptionsPanel());
		this.addTab("Animation", new AnimationOptionsPanel());
		this.addChangeListener(this);
		
		//Temporary hack to trigger state changed event :/
		this.setSelectedIndex(1);
		this.setSelectedIndex(0);
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		Dimension preferredSize = this.getSelectedComponent().getPreferredSize();
		Dimension newSize = new Dimension(preferredSize.width, preferredSize.height + 35);
		this.setPreferredSize(newSize);	
	}
	
}
