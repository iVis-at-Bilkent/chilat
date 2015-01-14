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
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.ivis.util.RectangleD;

import Controller.CoSELayoutManager;
import Model.CompoundNodeModel;
import Model.EdgeModel;
import Model.GraphMLParser;
import Model.NodeModel;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;


public class ChilayLayoutAnimationToolMain extends JFrame implements ActionListener,KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;
	
	private static ChilayLayoutAnimationToolMain singletonInstance;

	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private HashMap<String, mxCell> idToViewNode;
	private CoSELayoutManager layoutManager;
	private GraphMLParser parser;
	
	private float animationSpeed = 0.1f;
	private float interpolatedFrame = 0;
	private int currentKeyFrameNumber = 0;
	
	private boolean animateOn = false;


	private Timer timer;
	
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
		compoundNodeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		compoundNodeStyle.put(mxConstants.STYLE_STROKEWIDTH, 3);

		this.nodeStyle = new HashMap<String, Object>();
		nodeStyle.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(new Color(48, 175, 230)));
		nodeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		nodeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);
		
		this.edgeStyle = new HashMap<String, Object>();		
		edgeStyle.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.DARK_GRAY));
		// For undirected Graphs
		edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
		edgeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2);
		
		JPanel menuAndToolbarPanel = new JPanel();
		GridLayout menuAndToolbarLayout = new GridLayout(2, 1);
		menuAndToolbarPanel.setLayout(menuAndToolbarLayout);
		EditorToolBar toolBar = new EditorToolBar();
		EditorMenuBar menuBar = new EditorMenuBar();
		menuAndToolbarPanel.add(menuBar);
		menuAndToolbarPanel.add(toolBar);
		this.add(menuAndToolbarPanel,BorderLayout.PAGE_START);
	}
	
	public void loadGraph(String path)
	{
		//Map for obtaining view nodes
		this.currentKeyFrameNumber = 0;
		idToViewNode = new HashMap<String, mxCell>();

		parser = new GraphMLParser(path);
		try {
			parser.readGraphML();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.layoutManager = new CoSELayoutManager();
		this.layoutManager.createTopology(parser.getRootGraph(), parser.getEdges());
		
		try 
		{
			parser.readGraphML();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.graph = new mxGraph();
		mxStylesheet styleSheet = this.graph.getStylesheet();
		styleSheet.putCellStyle("CompoundStyle", compoundNodeStyle);
		styleSheet.putCellStyle("NodeStyle", nodeStyle);
		styleSheet.putCellStyle("EdgeStyle", edgeStyle);
		
		graph.getModel().beginUpdate();
		try
		{
			createGraphNodes(graph.getCurrentRoot(), parser.getRootGraph(), graph);
			createGraphEdges(graph, parser.getEdges());
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		
		
		if (this.graphComponent != null) {
			this.remove(this.graphComponent);
		}
		
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
		this.graphComponent.addKeyListener(this);
		this.graphComponent.addMouseWheelListener(new GraphMouseListener(this.graphComponent));
		this.graphComponent.setDoubleBuffered(true);	
		mxKeyboardHandler keyboardHandler = new mxKeyboardHandler(graphComponent);
		this.add(graphComponent,BorderLayout.CENTER);
		this.validate();
	}
	
	public void createGraphNodes(Object parent, CompoundNodeModel rootGraph, mxGraph graph  )
	{
		for (NodeModel childNode : rootGraph.getChildren())
		{
			createGraphNode(graph.getCurrentRoot(), childNode, graph);
		}
	}
	
	public void runLayout()
	{
		if (this.timer != null) 
		{
			this.timer.stop();
		}

		currentKeyFrameNumber = 0;
		interpolatedFrame = 0;
		this.layoutManager.clearKeyFrames();
		
		this.layoutManager.runLayout();
		this.animate();
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
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ChilayLayoutAnimationToolMain frame = ChilayLayoutAnimationToolMain.getInstance();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setVisible(true);
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
			
			/*double viewWidth = graphComponent.getGraph().getView().getGraphBounds().getWidth();
			double viewHeight = graphComponent.getGraph().getView().getGraphBounds().getHeight();
			double graphWidht = graphComponent.getWidth();
			double graphHeight = graphComponent.getHeight();
			
			double wRatio = graphWidht / viewWidth;
			double hRatio = graphHeight / viewHeight;
			
			graphComponent.zoom(Math.min(wRatio, hRatio));
			System.out.println(wRatio);*/
			/*System.out.println(mxResources.get("scale") + ": "
					+ (int) (100 * graphComponent.getGraph().getView().getScale())
					+ "%");
			*/	
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void animate() 
	{	
		if (this.timer !=null) 
		{
			timer.stop();
		}
		
		if (this.animateOn)
		{
			this.timer = new Timer(1000/60, this);
			this.timer.start();
		}
		else
		{
			Set<String> keys = this.idToViewNode.keySet();
			for (String tmpKey : keys)
			{
				mxCell cell = idToViewNode.get(tmpKey);
				Rectangle geometry = this.layoutManager.getFinalGeometry(tmpKey);
				cell.setGeometry(new mxGeometry(geometry.getX(), geometry.getY(), geometry.getWidth(), geometry.getHeight()));
			}
			this.graph.refresh();
			this.graph.repaint();
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
				
				double xNew = currentRect.getX() + (nextRect.getX() - currentRect.getX()) * (interpolatedFrame);
				double yNew = currentRect.getY() + (nextRect.getY() - currentRect.getY()) * (interpolatedFrame) ;
				cell.setGeometry(new mxGeometry(xNew, yNew, currentRect.getWidth(), currentRect.getHeight()));
			}
				
	
			
			if ( ( interpolatedFrame += animationSpeed) >= 1 ) 
			{
				currentKeyFrameNumber++;
				interpolatedFrame = interpolatedFrame - 1;
			}
			
			this.graph.refresh();
			this.graph.repaint();
		}
		else
		{
			this.timer.stop();
			currentKeyFrameNumber = 0;
			interpolatedFrame = 0;
			this.layoutManager.clearKeyFrames();
		}
	}
	
	public boolean isAnimateOn() {
		return animateOn;
	}

	public void setAnimateOn(boolean animateOn) 
	{
		this.animateOn = animateOn;
		this.layoutManager.setAnimateOn(this.animateOn);
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

