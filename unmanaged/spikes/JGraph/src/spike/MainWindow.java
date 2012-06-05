package spike;

import java.awt.EventQueue;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;

import org.w3c.dom.Document;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.io.mxCodec;
import com.mxgraph.shape.mxIMarker;
import com.mxgraph.shape.mxMarkerRegistry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxXmlUtils;
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
			Object relationshipNode = graph.insertVertex(parent, null, "", 210, 50,
					0, 0);
			Object person = graph.insertVertex(parent, null, "Persona", 170, 0,
					80, 30);
			Object student = graph.insertVertex(parent, null, "Estudiante", 100, 100,
					80, 30);
			Object teacher = graph.insertVertex(parent, null, "Docente", 240, 100,
					80, 30);
			Object other = graph.insertVertex(parent, null, "No Docente", 330, 100,
					80, 30);
			
			graph.insertEdge(parent, null, "", student, relationshipNode, "endArrow=none;edgeStyle=orthogonalEdgeStyle;verticalAlign=bottom;align=left;exitX=0.5;exitY=0");
			graph.insertEdge(parent, null, "", teacher, relationshipNode, "endArrow=none;edgeStyle=orthogonalEdgeStyle;verticalAlign=bottom;align=left;exitX=0.5;exitY=0");
			graph.insertEdge(parent, null, "", other, relationshipNode, "endArrow=none;edgeStyle=orthogonalEdgeStyle;verticalAlign=bottom;align=left;exitX=0.5;exitY=0");
			graph.insertEdge(parent, null, "", relationshipNode, person, "endArrow=block;edgeStyle=orthogonalEdgeStyle;verticalAlign=bottom;align=left;");			
		} 
		finally
		{
			graph.getModel().endUpdate();
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
//		mxCodec codec = new mxCodec();
//		String xml = mxXmlUtils.getXml(codec.encode(graph.getModel()));
//			
//		
//		FileWriter writer = new FileWriter(new File("graph.xml"));
//		writer.write(xml);
//		writer.close();
		
//		byte[] buffer = new byte[(int) new File("graph.xml").length()];
//		FileInputStream f = new FileInputStream("graph.xml");
//		f.read(buffer);
//		String xml = new String(buffer);
//		
//		Document document = mxXmlUtils.parseXml(xml);
//		
//		mxCodec codec = new mxCodec(document);
//		codec.decode(document.getDocumentElement(), graph.getModel());
			
		frame.getContentPane().add(graphComponent);
	}

}
