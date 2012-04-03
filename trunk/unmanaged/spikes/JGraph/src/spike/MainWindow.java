package spike;

import java.awt.EventQueue;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JFrame;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.shape.mxStencilShape;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxLayoutManager;

public class MainWindow {

	private JFrame frame;
	private mxOrthogonalLayout graphLayout;

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
		
		mxGraph graph = new CustomGraph();
	
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try 
		{
			Object billEntry = graph.insertVertex(parent, null, "BillEntry", 170, 20,
					80, 30);
			Object product = graph.insertVertex(parent, null, "Product", 240, 150,
					60, 30);
			Object productName = graph.insertVertex(parent, null, "Name", 320, 190,
					24, 10,"fontSize=9;strokeColor=white;fillColor=white;gradientColor=white");
			Object bill = graph.insertVertex(parent, null, "Bill", 100, 150,
					60, 30);
			Object linkProductEntry = graph.insertVertex(parent, null, "", 240, 70,
					30, 30, "shape=rhombus;perimeter=rhombusPerimeter");	
			Object linkBillEntry = graph.insertVertex(parent, null, "", 100, 70,
					30, 30, "shape=rhombus;perimeter=rhombusPerimeter");
			
			graph.insertEdge(parent, null, "", bill, linkBillEntry, "endArrow=none;edgeStyle=elbowEdgeStyle");
			graph.insertEdge(parent, null, "", product, linkProductEntry, "endArrow=none;edgeStyle=elbowEdgeStyle");
			graph.insertEdge(parent, null, "", billEntry, linkProductEntry, "endArrow=none;edgeStyle=elbowEdgeStyle");
			graph.insertEdge(parent, null, "", billEntry, linkBillEntry, "endArrow=none;edgeStyle=elbowEdgeStyle");
			graph.insertEdge(parent, null, "", product, productName, "endArrow=oval");
		} 
		finally
		{
			graph.getModel().endUpdate();
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
		frame.getContentPane().add(graphComponent);
	}

}
