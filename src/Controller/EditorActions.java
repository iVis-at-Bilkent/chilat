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

import View.AnimationPlayBackPanel;
import View.ChiLATConstants.LayoutQualityParameterName;
import View.ChilayLayoutAnimationToolMain;


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
				ChilayLayoutAnimationToolMain.getInstance().loadGraph(chooser.getSelectedFile().getAbsolutePath());
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
			ChilayLayoutAnimationToolMain.getInstance().performLayout();
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
			ChilayLayoutAnimationToolMain.getInstance().setAnimateOn(((JCheckBox)e.getSource()).isSelected());
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
			ChilayLayoutAnimationToolMain.getInstance().resumeOrStartAnimation();
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
			ChilayLayoutAnimationToolMain.getInstance().pauseAnimation();
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
			ChilayLayoutAnimationToolMain.getInstance().fastForwardAnimation();
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
			ChilayLayoutAnimationToolMain.getInstance().rewindAnimation();
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
			AnimationPlayBackPanel.getInstance().updateGUIAnimationEnd();
			ChilayLayoutAnimationToolMain.getInstance().stopAnimation();
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
			if (checkbox.isSelected()) 
			{
				LayoutOptionsPack.getInstance().getGeneral().uniformLeafNodeSizes = true;
			}
			else
				LayoutOptionsPack.getInstance().getGeneral().uniformLeafNodeSizes = false;
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
			ChilayLayoutAnimationToolMain chiLATMain = ChilayLayoutAnimationToolMain.getInstance();
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
