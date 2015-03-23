package View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import org.ivis.util.RectangleD;

import Controller.CoSELayoutManager;
import Model.CompoundNodeModel;
import Model.EdgeModel;
import Model.GraphMLParser;
import Model.NodeModel;
import Util.Vector2D;
import View.ChiLATConstants.ForceTuningParameterName;

import com.mxgraph.model.ChiLATCell;
import com.mxgraph.model.ChiLATCell;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.swing.handler.mxGraphHandler;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphSelectionModel;
import com.mxgraph.view.mxGraphView;
import com.mxgraph.view.mxStylesheet;


public class ChilayLayoutAnimationToolMain extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;
	
	private static ChilayLayoutAnimationToolMain singletonInstance;

	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private mxRubberband rubberBand;
	private mxGraphOutline graphOutline;
	
	private HashMap<String, ChiLATCell> idToViewNode;
	private CoSELayoutManager layoutManager;
	private GraphMLParser parser;
	

	//Animation specific consants
	int currentKeyFrameNumber = 0;
	float animationTotalTime = 0.0f;
	float interpolatedFrameRemainder = 0;
	float animationSpeed = 0.1f; //Animation makes progress by 0.1 frame per update
	
	private boolean isAnimateOn;
	private boolean isAnimationPaused;
	private boolean isAnimationRunning; 
	
	private boolean isForceDetailsVisible; 


	private Timer timer;
	
	//Maps that store information about the style of graph entities
	private HashMap<String, Object> nodeStyle;
	private HashMap<String, Object> compoundNodeStyle;
	private HashMap<String, Object> edgeStyle;
	
	public static ChilayLayoutAnimationToolMain getInstance()
	{
		if (ChilayLayoutAnimationToolMain.singletonInstance == null) {
			ChilayLayoutAnimationToolMain.singletonInstance = new ChilayLayoutAnimationToolMain();		
		}
		return ChilayLayoutAnimationToolMain.singletonInstance;
	}

	private ChilayLayoutAnimationToolMain() 
	{
		super("ChiLay Animation Tool");
		this.setLayout(new BorderLayout());
		
		this.compoundNodeStyle = new HashMap<String, Object>();
		compoundNodeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CHILAT_NODE_SHAPE);
		compoundNodeStyle.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
		compoundNodeStyle.put(mxConstants.STYLE_OPACITY, 50);
		compoundNodeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		compoundNodeStyle.put(mxConstants.STYLE_STROKEWIDTH, 3);

		this.nodeStyle = new HashMap<String, Object>();
		nodeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CHILAT_NODE_SHAPE);
		nodeStyle.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(new Color(48, 175, 230)));
		nodeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		nodeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);
		
		this.edgeStyle = new HashMap<String, Object>();		
		edgeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.DARK_GRAY));
		// For undirected Graphs
		edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
		edgeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);
		
		//Create mxGraph object
		this.graph = new mxGraph();
		this.graph.setDisconnectOnMove(false);
		this.graph.setDropEnabled(false);
		this.graph.setCellsDisconnectable(false);
		this.graph.setLabelsVisible(true);	
		this.graph.setKeepEdgesInBackground(true);
		
		mxStylesheet styleSheet = this.graph.getStylesheet();
		styleSheet.putCellStyle("CompoundStyle", compoundNodeStyle);
		styleSheet.putCellStyle("NodeStyle", nodeStyle);
		styleSheet.putCellStyle("EdgeStyle", edgeStyle);
		
		
		this.graphComponent = new mxGraphComponent(this.graph)
		{ 
			@Override public boolean isPanningEvent(MouseEvent event) 
			{
			   return true;
			}
						
		};
		
		final ForceVisualizationPopUpWindow	fVis = new ForceVisualizationPopUpWindow(0, 0);
		fVis.setVisible(false);
		//Mouse double click for node inspector
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() 
		{
			@Override      
			public void mousePressed(MouseEvent e) 
			{
				mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
				mxCellState state = graphComponent.getGraph().getView().getState(cell);
				String label = graph.getLabel(cell);
				
				if (cell != null && cell.isVertex()) 
				{
					graphComponent.getGraphControl().add(fVis);
					fVis.setLocation((int)(state.getX()+state.getWidth() + 5), (int)state.getY());
					fVis.setSelectedCell((ChiLATCell) cell);
					fVis.setVisible(true);
					
				}
				else
				{
					fVis.setVisible(false);
					graphComponent.getGraphControl().remove(fVis);
				}
			}
		});
		
		this.graphComponent.getViewport().setOpaque(true);
		this.graphComponent.getViewport().setBackground(new Color(255, 255, 255));
		this.graphComponent.getGraphHandler().setRemoveCellsFromParent(false);
		this.graphComponent.setConnectable(false);
		this.graphComponent.setDoubleBuffered(false);	
		this.graphComponent.setFoldingEnabled(false);
		
		mxKeyboardHandler keyboardHandler = new mxKeyboardHandler(graphComponent);
		this.rubberBand = new mxRubberband(graphComponent);
		this.graphOutline = new mxGraphOutline(this.graphComponent);
		this.graphOutline.addMouseWheelListener(new GraphMouseListener(this.graphComponent));
		graphOutline.DEFAULT_ZOOMHANDLE_FILL = Color.red;
		
		JPanel menuAndToolbarPanel = new JPanel();
		GridLayout menuAndToolbarLayout = new GridLayout(1, 1);
		menuAndToolbarPanel.setLayout(menuAndToolbarLayout);
		EditorToolBar toolBar = new EditorToolBar();
		/*EditorMenuBar menuBar = new EditorMenuBar();
		menuAndToolbarPanel.add(menuBar);*/
		menuAndToolbarPanel.add(toolBar);
		
		JPanel tabbedPanePanel = new JPanel();
		tabbedPanePanel.setLayout( new BorderLayout());
		tabbedPanePanel.add(new EditorTabbedPane(), BorderLayout.PAGE_START);
		
		JSplitPane animationPanelSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphComponent, AnimationPlayBackPanel.getInstance());
		animationPanelSplitPane.setDividerLocation(1024);
		
		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPanePanel, graphOutline);
		
		JSplitPane overViewWindowSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, verticalSplitPane, animationPanelSplitPane);
		overViewWindowSplitPane.setMinimumSize(new Dimension(500, 0));
		overViewWindowSplitPane.setDividerLocation(370);
		overViewWindowSplitPane.setBorder(new TitledBorder(" "));
		
		
		this.add(menuAndToolbarPanel,BorderLayout.PAGE_START);
		this.add(overViewWindowSplitPane,BorderLayout.CENTER);
	}
	
	public void loadGraph(String path)
	{
		//Map for obtaining view nodes
		this.currentKeyFrameNumber = 0;
		idToViewNode = new HashMap<String, ChiLATCell>();

		//Read graph
		parser = new GraphMLParser(path);
		try {
			parser.readGraphML();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Create layout manager
		this.layoutManager = new CoSELayoutManager();
		this.layoutManager.createTopology(parser.getRootGraph(), parser.getEdges());
		this.layoutManager.setAnimateOn(this.isAnimateOn);	

		//Create respective views from the model objects
		((mxGraphModel)graph.getModel()).clear();
		graph.getModel().beginUpdate();
		try
		{
			createGraphNodes(this.graph.getCurrentRoot(), this.parser.getRootGraph(), this.graph);
			createGraphEdges(graph, parser.getEdges());
		}
		finally
		{
			graph.getModel().endUpdate();
		}
	}
	
	public void createGraphNodes(Object parent, CompoundNodeModel rootGraph, mxGraph graph  )
	{
		for (NodeModel childNode : rootGraph.getChildren())
		{
			createGraphNode(graph.getCurrentRoot(), childNode, graph);
		}
	}
	
	public void performLayout()
	{	
		this.resetAnimationState();
		this.layoutManager.runLayout();
		AnimationPlayBackPanel.getInstance().setAnimationTimelineBounds(this.layoutManager.getTotalKeyFrameCount());
		
		if (!isAnimateOn) 
		{
			drawFinalLayoutState();
		}
		else
		{
			AnimationPlayBackPanel.getInstance().updateGUIAnimationStart();
			AnimationPlayBackPanel.getInstance().getPlayPauseButton().changeToPauseButton();
			this.animate();
		}
	}

	public void createGraphNode(Object parent, NodeModel nodeToBeInserted, mxGraph graph  )
	{

		ChiLATCell newViewNode = (ChiLATCell) graph.insertChiLATVertex(
				parent, 
				null, 
				nodeToBeInserted.getId(), 
				nodeToBeInserted.getBounds().getX(), 
				nodeToBeInserted.getBounds().getY(), 
				nodeToBeInserted.getBounds().getWidth(),
				nodeToBeInserted.getBounds().getHeight());

		idToViewNode.put(nodeToBeInserted.getId(), newViewNode);

		if (nodeToBeInserted instanceof CompoundNodeModel) 
		{
			if (((CompoundNodeModel) nodeToBeInserted).getChildren().size() > 0) 
			{
				newViewNode.setStyle("CompoundStyle");
			}
			else
				newViewNode.setStyle("NodeStyle");
			
			for (NodeModel nodeModel : ((CompoundNodeModel)nodeToBeInserted).getChildren()) 
			{
				createGraphNode(newViewNode, nodeModel, graph);
			}
		}

	}

	public void createGraphEdges(mxGraph graph, List<EdgeModel> edgeList)
	{
		for (EdgeModel edgeModel : edgeList) 
		{
			graph.insertEdge(
					graph.getDefaultParent(), 
					null, edgeModel.getLabel(), 
					idToViewNode.get(edgeModel.getSourceID()), 
					idToViewNode.get(edgeModel.getTargetID()),
					"EdgeStyle");
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        ChilayLayoutAnimationToolMain chiLATMain = ChilayLayoutAnimationToolMain.getInstance();
		chiLATMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chiLATMain.setSize(1024, 768);
		chiLATMain.setVisible(true);
        chiLATMain.loadGraph(chiLATMain.getClass().getResource("/SampleGraphs/badlayout2c.graphml").getPath());
	}
	
	public class GraphMouseListener implements MouseWheelListener
	{
		mxGraphComponent graphComponent;
		
		public GraphMouseListener(mxGraphComponent component)
		{
			this.graphComponent = component;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) 
		{
			if (e.getWheelRotation() < 0)
			{
				graphComponent.zoomIn();
			}
			else
			{
				graphComponent.zoomOut();
			}		

		}
		
	}
	
	public void zoomIn()
	{
		graphComponent.zoomIn();
	}
	
	public void zoomOut()
	{
		graphComponent.zoomOut();
	}
	
	public void zoomToFit()
	{
		mxGraphView view = graphComponent.getGraph().getView();
		double viewWidth = graphComponent.getWidth();
		double viewHeight = graphComponent.getHeight();
		double actualWidth = this.graph.getGraphBounds().getWidth();
		double actualHeight = this.graph.getGraphBounds().getHeight();
		view.setScale(Math.min(viewWidth/actualWidth, viewHeight/actualHeight) * view.getScale()*0.95);
	}

	public void animate() 
	{	
		if (this.timer !=null) 
		{
			timer.stop();
		}
		
		if (this.isAnimateOn)
		{
			isAnimationRunning = true;
			AnimationPlayBackPanel.getInstance().updateGUIAnimationStart();
			this.timer = new Timer(1000/60, this);
			this.timer.start();
		}
		else
		{
			drawFinalLayoutState();
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{	
		Set<String> keys = this.idToViewNode.keySet();
		
		if (currentKeyFrameNumber < this.layoutManager.getTotalKeyFrameCount()-1) 
		{
			for (String tmpKey : keys)
			{
				ChiLATCell cell = idToViewNode.get(tmpKey);
				RectangleD currentRect = this.layoutManager.getKeyFrameGeometry(tmpKey, this.currentKeyFrameNumber);
				RectangleD nextRect = this.layoutManager.getKeyFrameGeometry(tmpKey, this.currentKeyFrameNumber+1);
				
				//Linear interpolation of sizes and positions
				Vector2D newPositionVector = Vector2D.lerp(new Vector2D(currentRect.getX(), currentRect.getY()), 
						new Vector2D(nextRect.getX(), nextRect.getY()),
						interpolatedFrameRemainder);
				Vector2D newSizeVector = Vector2D.lerp(new Vector2D(currentRect.getWidth(), currentRect.getHeight()),
						new Vector2D(nextRect.getWidth(), nextRect.getHeight()),
						interpolatedFrameRemainder);
				
				//Fetch total force vectors for current key frame and next keyframe
				Vector2D currentTotalForceVector = new Vector2D(this.layoutManager.getTotalForceVector(tmpKey, this.currentKeyFrameNumber));
				Vector2D nextTotalForceVector = new Vector2D(this.layoutManager.getTotalForceVector(tmpKey, this.currentKeyFrameNumber+1));
				
				Vector2D currentRepulsionForceVector = new Vector2D(this.layoutManager.getRepulsionForceVector(tmpKey, this.currentKeyFrameNumber));
				Vector2D nextRepulsionForceVector = new Vector2D(this.layoutManager.getRepulsionForceVector(tmpKey, this.currentKeyFrameNumber+1));
				
				Vector2D currentSpringForceVector = new Vector2D(this.layoutManager.getSpringForceVector(tmpKey, this.currentKeyFrameNumber));
				Vector2D nextSpringForceVector = new Vector2D(this.layoutManager.getSpringForceVector(tmpKey, this.currentKeyFrameNumber+1));
				
				Vector2D currentGravityForceVector = new Vector2D(this.layoutManager.getGravityForceVector(tmpKey, this.currentKeyFrameNumber));
				Vector2D nextGravityForceVector = new Vector2D(this.layoutManager.getGravityForceVector(tmpKey, this.currentKeyFrameNumber+1));
				
				//Store magnitude of the force vectors
				double currentTotalForce = currentTotalForceVector.length();
				double currentRepulsionForce = currentRepulsionForceVector.length();
				double currentSpringForce = currentSpringForceVector.length();
				double currentGravityForce = currentGravityForceVector.length();
				
				//Normalize force vectors
				currentTotalForceVector = currentTotalForceVector.normalize();
				nextTotalForceVector = nextTotalForceVector.normalize();
				currentRepulsionForceVector = currentRepulsionForceVector.normalize();
				nextRepulsionForceVector = nextRepulsionForceVector.normalize();
				currentSpringForceVector = currentSpringForceVector.normalize();
				nextSpringForceVector = nextSpringForceVector.normalize();	
				currentGravityForceVector = currentGravityForceVector.normalize();
				nextGravityForceVector = nextGravityForceVector.normalize();

				//Linear interpolation between total force vectors
				Vector2D newTotalForceVector = Vector2D.lerp(currentTotalForceVector, nextTotalForceVector, interpolatedFrameRemainder);
				Vector2D newSpringForceVector = Vector2D.lerp(currentSpringForceVector, nextSpringForceVector, interpolatedFrameRemainder);
				Vector2D newRepulsionForceVector = Vector2D.lerp(currentRepulsionForceVector, nextRepulsionForceVector, interpolatedFrameRemainder);
				Vector2D newGravityForceVector = Vector2D.lerp(currentGravityForceVector, nextGravityForceVector, interpolatedFrameRemainder);
				
				//Finally set new geometry and new force vectors and their magnitudes
				cell.setGeometry(new mxGeometry(newPositionVector.getX(), newPositionVector.getY(), newSizeVector.getX(), newSizeVector.getY()));
				cell.setTotalForceVector(newTotalForceVector);
				cell.setRepulsionForceVector(newRepulsionForceVector);
				cell.setSpringForceVector(newSpringForceVector);
				cell.setGravityForceVector(newGravityForceVector);
				
				cell.setTotalForce(currentTotalForce);
				cell.setRepulsionForce(currentRepulsionForce);
				cell.setSpringForce(currentSpringForce);
				cell.setGravityForce(currentGravityForce);
			}
				
			
			Vector2D minMaxTotalForceForThisKeyFrame = this.layoutManager.getMinMaxTotalForceForKeyFrame(currentKeyFrameNumber);
			Vector2D minMaxAllOtherForces = this.layoutManager.getMinMaxOtherForceForKeyFrame(currentKeyFrameNumber);
			ChiLATCell.MIN_TOTAL_FORCE = minMaxTotalForceForThisKeyFrame.getX();
			ChiLATCell.MAX_TOTAL_FORCE = minMaxTotalForceForThisKeyFrame.getY();
			ChiLATCell.MIN_OF_ALL_OTHER_FORCES = minMaxAllOtherForces.getX();
			ChiLATCell.MAX_OF_ALL_OTHER_FORCES = minMaxAllOtherForces.getY();
			
			AnimationPlayBackPanel.getInstance().updateAnimationTimeLine(animationTotalTime);
									
			this.graph.refresh();
			this.graphComponent.refresh();
		}
		else
		{
			AnimationPlayBackPanel.getInstance().updateGUIAnimationEnd();
			AnimationPlayBackPanel.getInstance().updateAnimationTimeLine(this.layoutManager.getTotalKeyFrameCount());
			this.resetAnimationState();
			this.timer.stop();
		}
		
		if (!isAnimationPaused) 
		{
			animationTotalTime += animationSpeed;
			currentKeyFrameNumber = (int)(animationTotalTime);
			interpolatedFrameRemainder = (animationTotalTime) - currentKeyFrameNumber;

			/*if (this.graph.getGraphBounds().getWidth() > this.graphComponent.getWidth() ||
				this.graph.getGraphBounds().getHeight() > this.graphComponent.getHeight()) 
			{
				this.zoomToFit();
			}*/
		}
		this.zoomToFit();
	
	}
	
	public boolean isAnimateOn() {
		return isAnimateOn;
	}

	public void setAnimateOn(boolean animateOn) 
	{
		this.isAnimateOn = animateOn;
		
		if (this.layoutManager != null) {
			this.layoutManager.setAnimateOn(this.isAnimateOn);	
		}
	}
	
	public float getAnimationSpeed()
	{
		return this.animationSpeed;
	}
	
	public void setAnimationSpeed(float animationSpeed)
	{
		this.animationSpeed = animationSpeed;
	}
	
	public void pauseAnimation()
	{
		isAnimationPaused = true;
	}
	
	public void resumeOrStartAnimation()
	{
		//If animation is not running currently, start it
		if (!this.isAnimationRunning) 
		{
			this.animate();
		}
		isAnimationPaused = false;
	}
	
	public void stopAnimation()
	{
		this.timer.stop();
		//Draw the final geometries of the graph entities
		drawFinalLayoutState();
		AnimationPlayBackPanel.getInstance().updateGUIAnimationEnd();
		AnimationPlayBackPanel.getInstance().updateAnimationTimeLine(this.layoutManager.getTotalKeyFrameCount());
		this.resetAnimationState();

	}
	
	public void drawFinalLayoutState()
	{
		Set<String> keys = this.idToViewNode.keySet();

		if (isAnimateOn) 
		{
			for (String tmpKey : keys)
			{
				ChiLATCell cell = idToViewNode.get(tmpKey);
				RectangleD geometry = this.layoutManager.getKeyFrameGeometry(tmpKey, this.layoutManager.getTotalKeyFrameCount()-1);
				cell.setGeometry(new mxGeometry(geometry.getX(), geometry.getY(), geometry.getWidth(), geometry.getHeight()));
			}
		}
		else
		{
			for (String tmpKey : keys)
			{
				ChiLATCell cell = idToViewNode.get(tmpKey);
				Rectangle geometry = this.layoutManager.getFinalGeometry(tmpKey);
				cell.setGeometry(new mxGeometry(geometry.getX(), geometry.getY(), geometry.getWidth(), geometry.getHeight()));
			}
		}
		this.graph.refresh();

	}
	
	public void resetAnimationState()
	{
		this.isAnimationRunning = false;
		this.animationTotalTime = 0;
		this.interpolatedFrameRemainder = 0;
		this.currentKeyFrameNumber = 0;
		this.isAnimationPaused = false;
	}
	
	public void fastForwardAnimation()
	{
		animationTotalTime += 2*animationSpeed;
		
		if (animationTotalTime >= this.layoutManager.getTotalKeyFrameCount()-1) 
		{
			animationTotalTime = this.layoutManager.getTotalKeyFrameCount()-1;
			interpolatedFrameRemainder = 0;
			return;
		}
		
		currentKeyFrameNumber = (int)(animationTotalTime);
		interpolatedFrameRemainder = (animationTotalTime) - currentKeyFrameNumber;
	}
	
	public void rewindAnimation()
	{
		animationTotalTime -= 2*animationSpeed;
		
		if (animationTotalTime <= 0) 
		{
			animationTotalTime = 0;
			interpolatedFrameRemainder = 0;
			return;
		}
		
		currentKeyFrameNumber = (int)(animationTotalTime);
		interpolatedFrameRemainder = (animationTotalTime) - currentKeyFrameNumber;
	}
	
	public void setCoSEOption(ForceTuningParameterName chosenParameter, int value )
	{
		switch (chosenParameter) 
		{
		case SPRING_FORCE:
			this.layoutManager.getCoseOptionsPack().springStrength = value;
			break;
		case REPULSION_FORCE:
			this.layoutManager.getCoseOptionsPack().repulsionStrength = value;
			break;
		case GRAVITY_FORCE:
			this.layoutManager.getCoseOptionsPack().gravityStrength = value;
			break;
		case COMP_GRAVITY_FORCE:
			this.layoutManager.getCoseOptionsPack().compoundGravityStrength = value;
			break;
		case GRAVITY_RANGE:
			this.layoutManager.getCoseOptionsPack().gravityRange = value;
			break;
		case COMP_GRAVITY_RANGE:
			this.layoutManager.getCoseOptionsPack().compoundGravityRange = value;
			break;
		default:
			break;
		}
	}
	
	public int getCoSEOption(ForceTuningParameterName chosenParameter)
	{
		switch (chosenParameter) 
		{
		case SPRING_FORCE:
			return this.layoutManager.getCoseOptionsPack().springStrength;
		case REPULSION_FORCE:
			return this.layoutManager.getCoseOptionsPack().repulsionStrength;
		case GRAVITY_FORCE:
			return this.layoutManager.getCoseOptionsPack().gravityStrength;
		case COMP_GRAVITY_FORCE:
			return this.layoutManager.getCoseOptionsPack().compoundGravityStrength;
		case GRAVITY_RANGE:
			return this.layoutManager.getCoseOptionsPack().gravityRange;
		case COMP_GRAVITY_RANGE:
			return this.layoutManager.getCoseOptionsPack().compoundGravityRange;
		default:
			return -1;
		}
	}
	
	public class OpenButtonListener implements ActionListener
	{
		ChilayLayoutAnimationToolMain animationToolMain;
		
		public OpenButtonListener(ChilayLayoutAnimationToolMain other)
		{
			this.animationToolMain = other;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Open File");
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			{
			  this.animationToolMain.loadGraph(chooser.getSelectedFile().getAbsolutePath());
			} 
		}
	}
	
}



