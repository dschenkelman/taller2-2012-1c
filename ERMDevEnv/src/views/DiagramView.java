package views;

import javax.swing.JPanel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMouseAdapter;

import controllers.IDiagramController;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;

import com.mxgraph.util.mxEvent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;

public class DiagramView extends JPanel implements IDiagramView{

	private IDiagramController diagramController;
	private JButton btnEntity;

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
		
		JButton btnRelationship = new JButton("Relacion");
		add(btnRelationship, "6, 2");
		
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
		MouseListener listener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
					diagramController.addEntity(e.getPoint().x, e.getPoint().y);
			}
		};
		
		graphComponent.getGraphControl().addMouseListener(listener);
		
		this.add(graphComponent, "4, 4, 14, 1, fill, fill");
		
		btnEntity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				diagramController.createEntity();
			}
		});
	}
}
