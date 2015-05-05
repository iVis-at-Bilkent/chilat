package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;

import org.ivis.layout.LayoutOptionsPack;

import View.AnimationControlsPane;
import View.ChiLATConstants.LayoutQualityParameterName;
import View.ChiLATConstants.ZoomPolicyDuringLayout;
import View.ChiLATMain;


public class EditorActions 
{
	@SuppressWarnings("serial")
	public static class DeleteAction extends AbstractAction
	{
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
		}
	}
	
	@SuppressWarnings("serial")
	public static class LoadGraphAction extends AbstractAction
	{
		public LoadGraphAction()
		{
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/open.gif")));
			this.putValue(Action.SHORT_DESCRIPTION, "Load graph from a file");
		}
		
		/**
		 * 
		 */
		private static String lastDirectory = "";
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File(lastDirectory));
			chooser.setDialogTitle("Load File");
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			{
				lastDirectory = chooser.getSelectedFile().getAbsolutePath();
				ChiLATMain.getInstance().loadGraph(chooser.getSelectedFile().getAbsolutePath());
			} 
		}
	}
	
	@SuppressWarnings("serial")
	public static class SaveGraphAction extends AbstractAction
	{
		public SaveGraphAction()
		{
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/save.gif")));
			this.putValue(Action.SHORT_DESCRIPTION, "Save graph to a file");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
		}
	}
	
	@SuppressWarnings("serial")
	public static class PerformLayoutAction extends AbstractAction
	{
		public PerformLayoutAction()
		{
			//this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/layout-cose.gif")));
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/layout-cose-2.png")));
			this.putValue(Action.SHORT_DESCRIPTION, "Perform layout");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain.getInstance().performLayout();
		}
	}
	
	@SuppressWarnings("serial")
	public static class AnimateOnLayoutCheckBoxAction extends AbstractAction
	{
		public AnimateOnLayoutCheckBoxAction()
		{
			this.putValue(Action.SHORT_DESCRIPTION, "Animate during layout");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain.getInstance().setAnimateOn(((JCheckBox)e.getSource()).isSelected());
		}
	}
	
	@SuppressWarnings("serial")
	public static class PlayAnimationAction extends AbstractAction
	{
		public PlayAnimationAction()
		{
			//Initial icon and description
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/playIcon.png")));
			this.putValue(Action.SHORT_DESCRIPTION, "Play Animation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain.getInstance().resumeOrStartAnimation();
		}
	}
	
	@SuppressWarnings("serial")
	public static class PauseAnimationAction extends AbstractAction
	{
		public PauseAnimationAction()
		{
			//Initial icon and description
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/pauseIcon.png")));
			this.putValue(Action.SHORT_DESCRIPTION, "Pause Animation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain.getInstance().pauseAnimation();
		}
	}
	
	
	
	@SuppressWarnings("serial")
	public static class ForwardAnimationAction extends AbstractAction
	{		
		public ForwardAnimationAction()
		{
			//Initial icon and description
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/forwardIcon.png")));
			this.putValue(Action.SHORT_DESCRIPTION, "Fast Forward Animation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain.getInstance().fastForwardAnimation();
		}
	}
	
	@SuppressWarnings("serial")
	public static class RewindAnimationAction extends AbstractAction
	{		
		public RewindAnimationAction()
		{
			//Initial icon and description
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/rewindIcon.png")));
			this.putValue(Action.SHORT_DESCRIPTION, "Rewind Animation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain.getInstance().rewindAnimation();
		}
	}
	
	@SuppressWarnings("serial")
	public static class StopAction extends AbstractAction
	{		
		public StopAction()
		{
			//Initial icon and description
			this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/stopIcon.png")));
			this.putValue(Action.SHORT_DESCRIPTION, "Stop Animation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			AnimationControlsPane.getInstance().updateGUIAnimationEnd();
			ChiLATMain.getInstance().stopAnimation();
		}
	}
	
	@SuppressWarnings("serial")
	public static class LayoutQualityRadioButtonAction extends AbstractAction
	{		
		LayoutQualityParameterName chosenParameter;
		
		public LayoutQualityRadioButtonAction(LayoutQualityParameterName chosenParameter)
		{
			this.putValue(Action.SHORT_DESCRIPTION, "Layout Quality");
			this.chosenParameter = chosenParameter;
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JRadioButton radioButton = (JRadioButton) e.getSource();
			
			if (radioButton.isSelected())
			{
				switch (chosenParameter) 
				{
				case DRAFT:
					LayoutOptionsPack.getInstance().getGeneral().layoutQuality = 2;
					break;
				case DEFAULT:
					LayoutOptionsPack.getInstance().getGeneral().layoutQuality = 1;				
					break;
				case POOR:
					LayoutOptionsPack.getInstance().getGeneral().layoutQuality = 0;
					break;
				default:
					break;
				}
			}

		}
	}
	
	@SuppressWarnings("serial")
	public static class IncrementalLayoutCheckboxAction extends AbstractAction
	{		
		public IncrementalLayoutCheckboxAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Incremental Layout");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			if (checkbox.isSelected()) 
			{
				LayoutOptionsPack.getInstance().getGeneral().incremental = true;
			}
			else
				LayoutOptionsPack.getInstance().getGeneral().incremental = false;
		}
	}
	
	@SuppressWarnings("serial")
	public static class ZoomToFitDuringLayoutRadioButtonAction extends AbstractAction
	{		
		public ZoomToFitDuringLayoutRadioButtonAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Automatically zoom to fit during layout");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JRadioButton radioButton = (JRadioButton) e.getSource();
			if (radioButton.isSelected()) {
				ChiLATMain.getInstance().setZoomPolicyDuringLayout(ZoomPolicyDuringLayout.ZOOM_TO_FIT_DURING_LAYOUT);	
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static class ZoomToSelectedNodeDuringLayoutRadioButtonAction extends AbstractAction
	{		
		public ZoomToSelectedNodeDuringLayoutRadioButtonAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Automatically zoom to selected node and its close neighborhood");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JRadioButton radioButton = (JRadioButton) e.getSource();
			if (radioButton.isSelected()) {
				ChiLATMain.getInstance().setZoomPolicyDuringLayout(ZoomPolicyDuringLayout.ZOOM_TO_SELECTED_NODE_DURING_LAYOUT);
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static class FreeZoomPolicyDuringLayoutRadioButtonAction extends AbstractAction
	{		
		public FreeZoomPolicyDuringLayoutRadioButtonAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Free zoom policy");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JRadioButton radioButton = (JRadioButton) e.getSource();
			if (radioButton.isSelected()) {
				ChiLATMain.getInstance().setZoomPolicyDuringLayout(ZoomPolicyDuringLayout.FREE_ZOOM_POLICY_DURING_LAYOUT);
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static class ForceDetailsVisibleChecboxAction extends AbstractAction
	{		
		public ForceDetailsVisibleChecboxAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Set force details visible during animation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			ChiLATMain.getInstance().setForceDetailsVisible(checkbox.isSelected());
		}
	}
	
	@SuppressWarnings("serial")
	public static class UniformLeafNodeSizesCheckboxAction extends AbstractAction
	{		
		public UniformLeafNodeSizesCheckboxAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Uniform Leaf Node Sizes");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			LayoutOptionsPack.getInstance().getGeneral().uniformLeafNodeSizes = checkbox.isSelected();
		}
	}
	
	@SuppressWarnings("serial")
	public static class SmartEdgeLengthCalculationCheckboxAction extends AbstractAction
	{		
		public SmartEdgeLengthCalculationCheckboxAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Smart Edge Length Calculation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			LayoutOptionsPack.getInstance().getCoSE().smartEdgeLengthCalc = checkbox.isSelected();

		}
	}
	
	@SuppressWarnings("serial")
	public static class MultiLevelScalingCheckboxAction extends AbstractAction
	{		
		public MultiLevelScalingCheckboxAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Multi Level Scaling");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			LayoutOptionsPack.getInstance().getCoSE().multiLevelScaling = checkbox.isSelected();

		}
	}
	
	@SuppressWarnings("serial")
	public static class SmartRepulsionRangeCheckboxAction extends AbstractAction
	{		
		public SmartRepulsionRangeCheckboxAction()
		{
			//Initial icon and description
			this.putValue(Action.SHORT_DESCRIPTION, "Smart Repulsion Range Calculation");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			LayoutOptionsPack.getInstance().getCoSE().smartRepulsionRangeCalc = checkbox.isSelected();

		}
	}
	
	@SuppressWarnings("serial")
	public static class ShowActualDisplacementCheckboxAction extends AbstractAction
	{
		public ShowActualDisplacementCheckboxAction()
		{
			this.putValue(Action.SHORT_DESCRIPTION, "Visualize Actual Displacement");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			ChiLATMain.getInstance().setShowActualDisplacement(checkbox.isSelected());
		}
	}
	
	@SuppressWarnings("serial")
	public static class NormalizeValuesCheckboxAction extends AbstractAction
	{
		public NormalizeValuesCheckboxAction()
		{
			this.putValue(Action.SHORT_DESCRIPTION, "Normalize Visualized Total Force or Displacement");
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			JCheckBox checkbox = (JCheckBox) e.getSource();
			ChiLATMain.getInstance().setShowNormalizedValues(checkbox.isSelected());
		}
	}
	
	@SuppressWarnings("serial")
	public static class ZoomAction extends AbstractAction
	{
		public static int ZOOM_IN = 0;
		public static int ZOOM_OUT = 1;
		public static int ZOOM_FIT = 2;
		
		private int zoomPolicy;
		
		public ZoomAction(int zoomPolicy)
		{
			this.zoomPolicy = zoomPolicy;
			
			if (zoomPolicy == ZOOM_IN) 
			{
				this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/zoom-in.gif")));
				this.putValue(Action.SHORT_DESCRIPTION, "Zoom In");
			}
			else if(zoomPolicy == ZOOM_OUT)
			{
				this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/zoom-out.gif")));
				this.putValue(Action.SHORT_DESCRIPTION, "Zoom Out");
			}
			else if (zoomPolicy == ZOOM_FIT) 
			{
				this.putValue(SMALL_ICON, new ImageIcon(this.getClass().getResource("/Icons/zoom-fit.gif")));
				this.putValue(Action.SHORT_DESCRIPTION, "Zoom to Fit");
			}
		}
		
		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			ChiLATMain chiLATMain = ChiLATMain.getInstance();
			if (zoomPolicy == ZOOM_IN) 
			{
				chiLATMain.zoomIn();
			}
			else if(zoomPolicy == ZOOM_OUT)
			{
				chiLATMain.zoomOut();
			}
			else if (zoomPolicy == ZOOM_FIT) 
			{
				chiLATMain.zoomToFit();
			}
		}
	}
}
