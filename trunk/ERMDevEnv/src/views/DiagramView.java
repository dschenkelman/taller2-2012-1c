package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JMenuBar;

import com.mxgraph.swing.mxGraphComponent;

import controllers.IDiagramController;

public class DiagramView extends JFrame implements IDiagramView {

	private JPanel contentPane;
	private IDiagramController diagramController;

	/**
	 * Create the frame.
	 */
	public DiagramView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton btnEntity = new JButton("Entidad");
		menuBar.add(btnEntity);
		
		JButton btnRelationship = new JButton("Relacion");
		menuBar.add(btnRelationship);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		this.contentPane.setLayout(gbl_contentPane);
	}

	@Override
	public void setController(IDiagramController controller) {
		this.diagramController = controller;
		mxGraphComponent graphComponent = new mxGraphComponent(this.diagramController.getGraph());
		this.contentPane.add(graphComponent);
	}
}
