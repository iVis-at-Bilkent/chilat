package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.view.mxGraph;

public class ZoomSelectedAreaTest {

    public ZoomSelectedAreaTest() {

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setLocation(300, 200);

        final mxGraph graph = new mxGraph();

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            Object v1 = graph.insertVertex(parent, null, "node1", 100, 100, 80, 30);
            Object v2 = graph.insertVertex(parent, null, "node2", 1000, 100, 80, 30);
            Object v3 = graph.insertVertex(parent, null, "node3", 100, 1000, 80, 30);

            graph.insertEdge(parent, null, "Edge", v1, v2);
            graph.insertEdge(parent, null, "Edge", v2, v3);

        } finally {
            graph.getModel().endUpdate();
        }

        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        frame.getContentPane().add(graphComponent, BorderLayout.CENTER);

        // create toolbar (graphOutline and toolbar Buttons
        JPanel toolBar = new JPanel();
        toolBar.setLayout(new BorderLayout());

        // graphOutline
        final mxGraphOutline graphOutline = new mxGraphOutline(graphComponent);
        graphOutline.setPreferredSize(new Dimension(100, 100));
        toolBar.add(graphOutline, BorderLayout.WEST);

        // toolbar Buttons
        JPanel buttonBar = new JPanel();
        buttonBar.setLayout(new FlowLayout());

        // zoom to fit
        JButton btZoomToFit = new JButton("Zoom To Fit ViewPort");
        btZoomToFit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                double newScale = 1;

                Dimension graphSize = graphComponent.getGraphControl().getSize();
                Dimension viewPortSize = graphComponent.getViewport().getSize();

                int gw = (int) graphSize.getWidth();
                int gh = (int) graphSize.getHeight();

                if (gw > 0 && gh > 0) {
                    int w = (int) viewPortSize.getWidth();
                    int h = (int) viewPortSize.getHeight();

                    newScale = Math.min((double) w / gw, (double) h / gh);
                }

                graphComponent.zoom(newScale);

            }
        });
        buttonBar.add(btZoomToFit);

        // center graph
        JButton btCenter = new JButton("Center Graph in ViewPort");
        btCenter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                Dimension graphSize = graphComponent.getGraphControl().getSize();
                Dimension viewPortSize = graphComponent.getViewport().getSize();

                int x = graphSize.width/2 - viewPortSize.width/2;
                int y = graphSize.height/2 - viewPortSize.height/2;
                int w = viewPortSize.width;
                int h = viewPortSize.height;

                graphComponent.getGraphControl().scrollRectToVisible( new Rectangle( x, y, w, h));

            }
        });
        buttonBar.add(btCenter);

        // add rubberband zoom
        new mxRubberband(graphComponent) {

            public void mouseReleased(MouseEvent e)
            {
                // get bounds before they are reset
                Rectangle rect = bounds;

                // invoke usual behaviour
                super.mouseReleased(e);

                if( rect != null) {

                    double newScale = 1;

                    Dimension graphSize = new Dimension( rect.width, rect.height);
                    Dimension viewPortSize = graphComponent.getViewport().getSize();

                    int gw = (int) graphSize.getWidth();
                    int gh = (int) graphSize.getHeight();

                    if (gw > 0 && gh > 0) {
                        int w = (int) viewPortSize.getWidth();
                        int h = (int) viewPortSize.getHeight();

                        newScale = Math.min((double) w / gw, (double) h / gh);
                    }

                    // zoom to fit selected area
                    graphComponent.zoom(newScale);

                    // make selected area visible 
                    graphComponent.getGraphControl().scrollRectToVisible( new Rectangle( (int) (rect.x * newScale), (int) (rect.y * newScale),  (int) (rect.width * newScale),  (int) (rect.height * newScale)));

                }

            }

        };

        // put components on frame
        toolBar.add(buttonBar, BorderLayout.CENTER);
        frame.getContentPane().add(toolBar, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        ZoomSelectedAreaTest t = new ZoomSelectedAreaTest();

    }

}