package View;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ChiAnimSliderPanel extends JPanel
{
	JLabel sliderLabel;
	JSlider slider;
	
	public ChiAnimSliderPanel(String label, int minSlider, int maxSlider, int initialValue)
	{
		super();
		this.setLayout(new GridLayout(1, 2));
		this.sliderLabel = new JLabel(label);
		this.slider = new JSlider(minSlider, maxSlider, initialValue);
		this.add(this.sliderLabel);
		this.add(this.slider);
	}
}
