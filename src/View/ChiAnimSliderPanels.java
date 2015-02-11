package View;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChiAnimSliderPanels 
{
	public static class AnimationSpeedSliderPanel extends ChiAnimSliderPanel implements ChangeListener
	{
		public AnimationSpeedSliderPanel(String label, int minSlider,int maxSlider, int initialValue)
		{
			super(label, minSlider, maxSlider, initialValue);
			this.slider.setToolTipText("Animation progress at each update: " + (float)initialValue/1000 + " frames" );
			this.slider.addChangeListener(this);
		}
		
		public void stateChanged(ChangeEvent event) 
		{
			float value = (((float)this.slider.getValue())/1000);
			this.slider.setToolTipText("Animation progress at each update: " + value + " frames" );
			ChilayLayoutAnimationToolMain.getInstance().setAnimationSpeed(value);
		}
	}
}
