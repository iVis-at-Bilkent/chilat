package View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
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
	
	private HashMap<String, mxCell> idToViewNode;
	private CoSELayoutManager layoutManager;
	private GraphMLParser parser;
	

	//Animation specific consants
	private int currentKeyFrameNumber = 0;
	float animationTotalTime = 0.0f;
	float interpolatedFrameRemainder = 0;
	float animationSpeed = 0.1f; //Animation makes progress by 0.1 frame per update
	
	private boolean isAnimateOn = false;
	private boolean isAnimationPaused = false;
	private boolean isAnimationRunning = false; 


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
		compoundNodeStyle.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
		compoundNodeStyle.put(mxConstants.STYLE_OPACITY, 50);
		compoundNodeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		compoundNodeStyle.put(mxConstants.STYLE_STROKEWIDTH, 3);

		this.nodeStyle = new HashMap<String, Object>();
		nodeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
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
		GridLayout menuAndToolbarLayout = new GridLayout(2, 1);
		menuAndToolbarPanel.setLayout(menuAndToolbarLayout);
		EditorToolBar toolBar = new EditorToolBar();
		EditorMenuBar menuBar = new EditorMenuBar();
		menuAndToolbarPanel.add(menuBar);
		menuAndToolbarPanel.add(toolBar);
		
		JPanel tabbedPanePanel = new JPanel();
		tabbedPanePanel.setLayout( new BorderLayout());
		tabbedPanePanel.add(new EditorTabbedPane(), BorderLayout.PAGE_START);
		
		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPanePanel, graphOutline);
		
		JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, verticalSplitPane, graphComponent);
		horizontalSplitPane.setMinimumSize(new Dimension(500, 0));
		horizontalSplitPane.setDividerLocation(370);
		horizontalSplitPane.setBorder(new TitledBorder(" "));
		
		this.add(menuAndToolbarPanel,BorderLayout.PAGE_START);
		this.add(horizontalSplitPane,BorderLayout.CENTER);
	}
	
	public void loadGraph(String path)
	{
		//Map for obtaining view nodes
		this.currentKeyFrameNumber = 0;
		idToViewNode = new HashMap<String, mxCell>();

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
		this.layoutManager.runLayout();
		
		if (!isAnimateOn) 
		{
			drawFinalLayoutState();
		}
	}

	public void createGraphNode(Object parent, NodeModel nodeToBeInserted, mxGraph graph  )
	{

		mxCell newViewNode = (mxCell) graph.insertVertex(
				parent, 
				null, 
				nodeToBeInserted.getLabel(), 
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
        ChilayLayoutAnimationToolMain frame = ChilayLayoutAnimationToolMain.getInstance();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setVisible(true);
	}
	
	public class GraphMouseListener implements MouseWheelListener
	{
		mxGraphComponent graphComponent;
		private double zoomScale = 1.0;
		
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
						
			/*double viewWidth = graphComponent.getGraph().getView().getGraphBounds().getWidth();
			double viewHeight = graphComponent.getGraph().getView().getGraphBounds().getHeight();
			double graphWidht = graphComponent.getWidth();
			double graphHeight = graphComponent.getHeight();
			
			double wRatio = graphWidht / viewWidth;
			double hRatio = graphHeight / viewHeight;
			
			graphComponent.zoom(Math.min(wRatio, hRatio));*/

		}
		
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
				mxCell cell = idToViewNode.get(tmpKey);
				
				RectangleD currentRect = this.layoutManager.getKeyFrameGeometry(tmpKey, this.currentKeyFrameNumber);
				RectangleD nextRect = this.layoutManager.getKeyFrameGeometry(tmpKey, this.currentKeyFrameNumber+1);
				
				double xNew = currentRect.getX() + (nextRect.getX() - currentRect.getX()) * (interpolatedFrameRemainder);
				double yNew = currentRect.getY() + (nextRect.getY() - currentRect.getY()) * (interpolatedFrameRemainder);
				double wNew = currentRect.getWidth() + (nextRect.getWidth() - currentRect.getWidth()) * (interpolatedFrameRemainder);
				double hNew = currentRect.getHeight() + (nextRect.getHeight() - currentRect.getHeight()) * (interpolatedFrameRemainder);
							
				cell.setGeometry(new mxGeometry(xNew, yNew, wNew, hNew));
			}
				
			this.graph.refresh();
		}
		else
		{
			isAnimationRunning = false;
			this.animationTotalTime = 0;
			this.interpolatedFrameRemainder = 0;
			this.currentKeyFrameNumber = 0;
			AnimationPlayBackPanel.getInstance().updateGUIAnimationEnd();
			this.timer.stop();
		}		
		
		if (!isAnimationPaused) 
		{
			animationTotalTime += animationSpeed;
			currentKeyFrameNumber = (int)(animationTotalTime);
			interpolatedFrameRemainder = (animationTotalTime) - currentKeyFrameNumber;
		}

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
		this.resetAnimationState();
	}
	
	public void drawFinalLayoutState()
	{
		Set<String> keys = this.idToViewNode.keySet();

		if (isAnimateOn) 
		{
			for (String tmpKey : keys)
			{
				mxCell cell = idToViewNode.get(tmpKey);
				RectangleD geometry = this.layoutManager.getKeyFrameGeometry(tmpKey, this.layoutManager.getTotalKeyFrameCount()-1);
				cell.setGeometry(new mxGeometry(geometry.getX(), geometry.getY(), geometry.getWidth(), geometry.getHeight()));
			}
		}
		else
		{
			for (String tmpKey : keys)
			{
				mxCell cell = idToViewNode.get(tmpKey);
				Rectangle geometry = this.layoutManager.getFinalGeometry(tmpKey);
				cell.setGeometry(new mxGeometry(geometry.getX(), geometry.getY(), geometry.getWidth(), geometry.getHeight()));
			}
		}
		this.graph.refresh();

	}
	
	public void resetAnimationState()
	{
		isAnimationRunning = false;
		this.animationTotalTime = 0;
		this.interpolatedFrameRemainder = 0;
		this.currentKeyFrameNumber = 0;
		//this.layoutManager.clearKeyFrames();
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

