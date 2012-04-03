package spike;

import java.awt.EventQueue;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import javax.swing.JFrame;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.shape.mxIMarker;
import com.mxgraph.shape.mxMarkerRegistry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. 
	 */
	public MainWindow()
	{
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
//		String fileName = MainWindow.class
//		.getResource("/resources").getPath();
		
//		for (File f : new File(fileName).listFiles(
//				new FilenameFilter()
//				{
//					public boolean accept(File dir,
//							String name)
//					{
//						return name.toLowerCase().endsWith(
//								".shape");
//					}
//				}))
//		{
//			String nodeXml = mxUtils.readFile(f.getAbsolutePath());
//			mxStencilShape stencilShape = new mxStencilShape(nodeXml);
//			String name = stencilShape.getName();
//			mxGraphics2DCanvas.putShape(name, stencilShape);
//		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mxMarkerRegistry.registerMarker("emptyCircle", new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				double cx = pe.getX() - nx / 2;
				double cy = pe.getY() - ny / 2;
				double a = size / 2;
				Shape shape = new Ellipse2D.Double(cx - a, cy - a, size, size);

				//canvas.fillShape(shape);
				canvas.getGraphics().draw(shape);

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
		
		mxGraph graph = new CustomGraph();
	
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try 
		{
			Object billEntry = graph.insertVertex(parent, null, "BillEntry", 170, 0,
					80, 30);
			Object product = graph.insertVertex(parent, null, "Product", 240, 150,
					60, 30);
			Object productName = graph.insertVertex(parent, null, "Name", 320, 190,
					0, 0,"fontSize=9;strokeColor=white;fillColor=white;gradientColor=white;verticalAlign=top;align=center");
			Object productPrice = graph.insertVertex(parent, null, "Price", 320, 160,
					0, 0,"fontSize=9;strokeColor=white;fillColor=white;gradientColor=white;verticalAlign=top;align=center");
			Object bill = graph.insertVertex(parent, null, "Bill", 100, 150,
					60, 30);
			Object linkProductEntry = graph.insertVertex(parent, null, "", 240, 70,
					30, 30, "shape=rhombus;perimeter=rhombusPerimeter");	
			Object linkBillEntry = graph.insertVertex(parent, null, "", 100, 70,
					30, 30, "shape=rhombus;perimeter=rhombusPerimeter");
			
			graph.insertEdge(parent, null, "(1,n)", bill, linkBillEntry, "endArrow=none;edgeStyle=elbowEdgeStyle;verticalAlign=bottom;align=left");
			graph.insertEdge(parent, null, "(0,n)", product, linkProductEntry, "endArrow=none;edgeStyle=elbowEdgeStyle;verticalAlign=bottom;align=left");
			graph.insertEdge(parent, null, "(1,1)", billEntry, linkProductEntry, "endArrow=none;edgeStyle=elbowEdgeStyle;verticalAlign=bottom;align=left");
			graph.insertEdge(parent, null, "(1,1)", billEntry, linkBillEntry, "endArrow=none;edgeStyle=elbowEdgeStyle;verticalAlign=bottom;align=left");
			graph.insertEdge(parent, null, "", product, productName, "endArrow=oval");
			graph.insertEdge(parent, null, "", product, productPrice, "endArrow=emptyCircle");
			Object keyEdge1 = graph.insertEdge(parent, null, "", billEntry, bill, "endArrow=none;");
			Object keyEdge2 = graph.insertEdge(parent, null, "", billEntry, product, "endArrow=none;");
			graph.insertEdge(parent, null, "", keyEdge1, keyEdge2, "endArrow=oval;startArrow=oval");
		} 
		finally
		{
			graph.getModel().endUpdate();
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
		frame.getContentPane().add(graphComponent);
	}

}
