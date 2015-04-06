package View;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ChiLATSliderPanel extends JPanel
{
	JLabel sliderLabel;
	JSlider slider;
	JLabel textLabel;
	
	public ChiLATSliderPanel(String label, int minSlider, int maxSlider, int initialValue)
	{
		super();
		this.setLayout(new GridLayout(1, 2));
		this.sliderLabel = new JLabel(label);
		this.slider = new JSlider(minSlider, maxSlider, initialValue);
		JPanel sliderAndTextAreaPanel = new JPanel();
		sliderAndTextAreaPanel.setLayout(new BoxLayout(sliderAndTextAreaPanel,BoxLayout.X_AXIS));
		sliderAndTextAreaPanel.add(slider);
		sliderAndTextAreaPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		sliderAndTextAreaPanel.add(Box.createHorizontalGlue());
		this.textLabel = new JLabel("" + initialValue);
		textLabel.setMaximumSize(new Dimension(20, 10));
		sliderAndTextAreaPanel.add(textLabel);
		sliderAndTextAreaPanel.add(Box.createHorizontalGlue());
		this.add(this.sliderLabel);
		this.add(sliderAndTextAreaPanel);
	}
}
