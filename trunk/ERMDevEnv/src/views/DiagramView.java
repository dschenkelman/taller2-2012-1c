package views;

import java.awt.Point;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mxgraph.swing.mxGraphComponent;

import controllers.IDiagramController;

public class DiagramView extends JPanel implements IDiagramView, DropTargetListener{

	private IDiagramController diagramController;
	private JButton btnEntity;
	private JButton btnRelationship;
	private JButton btnHierarchy;
	private JButton btnSave;
	private JButton btnSubdiagram;

	/**
	 * Create the panel.
	 */
	public DiagramView() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
		add(this.btnEntity, "2, 2");
		
		this.btnRelationship = new JButton("Relacion");
		add(this.btnRelationship, "4, 2");
		
		this.btnHierarchy = new JButton("Jerarquia");
		add(this.btnHierarchy, "6, 2");
		
		this.btnSave = new JButton("Grabar");
		add(this.btnSave, "8, 2");
		
		this.btnSubdiagram = new JButton("SubDiagrama");
		add(this.btnSubdiagram, "10, 2");
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
					try {
						diagramController.addEntity(e.getPoint().x, e.getPoint().y);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		
		graphComponent.getGraphControl().addMouseListener(listener);
		
		try {
			graphComponent.getDropTarget().addDropTargetListener(this);
		} catch (TooManyListenersException e1) {
			// should not occur
			e1.printStackTrace();
		}
		
		this.add(graphComponent, "2, 4, 15, 1, fill, fill");
		
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
		
		this.btnHierarchy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				diagramController.createHierarchy();
			}
		});
		
		this.btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					diagramController.save();
				} catch (ParserConfigurationException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		this.btnSubdiagram.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				String diagramName = JOptionPane.showInputDialog(null, "Ingrese el nombre del diagrama", "Nuevo Diagrama", JOptionPane.QUESTION_MESSAGE);
				if (diagramName != null){
					diagramController.createSubDiagram(diagramName);
				}
			}
		});
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		Point point = dtde.getLocation();
		this.diagramController.handleDragStart(point);
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		Point point = dtde.getLocation();
		this.diagramController.handleDrop(point);
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}
}
