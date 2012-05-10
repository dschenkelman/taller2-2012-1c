package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mxgraph.swing.mxGraphComponent;

import controllers.IDiagramController;

public class DiagramView extends JPanel implements IDiagramView{

	private IDiagramController diagramController;
	private JButton btnEntity;
	private JButton btnRelationship;

	/**
	 * Create the panel.
	 */
	public DiagramView() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		this.btnEntity = new JButton("Entidad");
		add(this.btnEntity, "4, 2");
		
		this.btnRelationship = new JButton("Relacion");
		add(this.btnRelationship, "6, 2");
		
		JButton btnHierarchy = new JButton("Jerarquia");
		add(btnHierarchy, "8, 2");
	}

	@Override
	public void setController(IDiagramController controller) {
		this.diagramController = controller;
		
		mxGraphComponent graphComponent = new mxGraphComponent(this.diagramController.getGraph());
		// cannot create new arrows clicking from entity
		graphComponent.getConnectionHandler().setCreateTarget(false);
		graphComponent.setConnectable(false);
		MouseListener listener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (diagramController.hasPendingEntity()){
					diagramController.addEntity(e.getPoint().x, e.getPoint().y);
				}
			}
		};
		
		graphComponent.getGraphControl().addMouseListener(listener);
		
		this.add(graphComponent, "4, 4, 14, 1, fill, fill");
		
		this.btnEntity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				diagramController.createEntity();
			}
		});
		
		this.btnRelationship.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				diagramController.createRelationship();
			}
		});
	}
}
