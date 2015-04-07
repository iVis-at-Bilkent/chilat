package View;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import View.ChiLATConstants.ForceTuningParameterName;

public class ChiLATSpinner extends JPanel implements ChangeListener
{
	ForceTuningParameterName chosenParameter;
	JSpinner spinner;
	
	public ChiLATSpinner(int initialValue, int min, int max, int stepSize, String label, ForceTuningParameterName chosenParameter)
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		SpinnerModel spinnerModel = new SpinnerNumberModel(initialValue,min,max, stepSize);
		this.spinner = new JSpinner(spinnerModel);
		this.add(new JLabel(label));
		this.add(Box.createHorizontalGlue());
		this.add(spinner);
		this.chosenParameter = chosenParameter;
		this.spinner.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) 
	{
		ChiLATMain.getInstance().setCoSEOption(this.chosenParameter, (int)spinner.getValue());
		
	}

}
