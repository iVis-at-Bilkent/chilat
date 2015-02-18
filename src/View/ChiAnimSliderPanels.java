package View;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ivis.layout.LayoutOptionsPack;

import View.ChiLATConstants.ForceTuningParameterName;

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
	
	public static class ForceTuningSliderPanel extends ChiAnimSliderPanel implements ChangeListener
	{
		String label = "";
		
		ForceTuningParameterName chosenParameter;

		public ForceTuningSliderPanel(String label, int initialValue, ForceTuningParameterName chosenParameter)
		{
			super(label, 0, 100, initialValue);
			this.label = label;
			this.slider.setToolTipText(label + initialValue );
			this.slider.addChangeListener(this);
			this.chosenParameter = chosenParameter;
		}
		
		public void stateChanged(ChangeEvent event) 
		{
			int value = this.slider.getValue();
			this.slider.setToolTipText(label + value );
			ChilayLayoutAnimationToolMain.getInstance().setCoSEOption(chosenParameter, value);
		}
	}
	
	@SuppressWarnings("serial")
	public static class KeyframeCapturePeriodSliderPanel extends ChiAnimSliderPanel implements ChangeListener
	{
		String label = "";
		
		ForceTuningParameterName chosenParameter;

		public KeyframeCapturePeriodSliderPanel(String label, int initialValue)
		{
			super(label, 1, 100, initialValue);
			this.label = label;
			this.slider.setToolTipText(label + initialValue );
			this.slider.addChangeListener(this);
		}
		
		public void stateChanged(ChangeEvent event) 
		{
			int value = this.slider.getValue();
			LayoutOptionsPack.getInstance().getGeneral().animationPeriod = value;
			this.slider.setToolTipText(label + value );

		}
	}
	
}
