package View;

import java.text.DecimalFormat;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.ivis.layout.LayoutOptionsPack;

import View.ChiLATConstants.ForceTuningParameterName;

public class ChiLATSliderPanels 
{
	public static class AnimationSpeedSliderPanel extends ChiLATSliderPanel implements ChangeListener
	{
		public AnimationSpeedSliderPanel(String label, int minSlider,int maxSlider, int initialValue)
		{
			super(label, minSlider, maxSlider, initialValue);
			float value = (((float)this.slider.getValue())/1000);
			DecimalFormat formatter = new DecimalFormat();
			formatter.setMaximumFractionDigits(2);
			this.textLabel.setText(""+ formatter.format(value));
			this.slider.setToolTipText("Animation progress at each update: " + (float)initialValue/1000 + " frames" );
			this.slider.addChangeListener(this);
		}
		
		public void stateChanged(ChangeEvent event) 
		{
			float value = (((float)this.slider.getValue())/1000);
			this.slider.setToolTipText("Animation progress at each update: " + value + " frames" );
			DecimalFormat formatter = new DecimalFormat();
			formatter.setMaximumFractionDigits(2);
			this.textLabel.setText(""+ formatter.format(value));
			ChiLATMain.getInstance().setAnimationSpeed(value);
		}
	}
	
	public static class ForceTuningSliderPanel extends ChiLATSliderPanel implements ChangeListener
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
			this.textLabel.setText(""+value);
			ChiLATMain.getInstance().setCoSEOption(chosenParameter, value);
		}
	}
	
	@SuppressWarnings("serial")
	public static class KeyframeCapturePeriodSliderPanel extends ChiLATSliderPanel implements ChangeListener
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
			this.textLabel.setText(""+value);

		}
	}
	
}
