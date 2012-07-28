package views;

import java.awt.Point;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TooManyListenersException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.xml.parsers.ParserConfigurationException;

import models.Entity;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mxgraph.swing.mxGraphComponent;

import controllers.IDiagramController;

public class DiagramView extends JPanel implements IDiagramView, DropTargetListener{

	private IDiagramController diagramController;
	private final JButton btnEntity;
	private final JButton btnRelationship;
	private final JButton btnHierarchy;
	private final JButton btnSave;
	private final JButton btnSubdiagram;
	private mxGraphComponent graphComponent;
	private JPopupMenu entityMenu;
	private JPopupMenu existingEntitiesMenu;
	private JMenuItem existingEntitiesMenuItem;
    
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
		
		this.btnEntity = new JButton("Entity");
		add(this.btnEntity, "2, 2");
		
		this.btnRelationship = new JButton("Relationship");
		add(this.btnRelationship, "4, 2");
		
		this.btnHierarchy = new JButton("Hierarchy");
		add(this.btnHierarchy, "6, 2");
		
		this.btnSave = new JButton("Save");
		add(this.btnSave, "8, 2");
		
		this.btnSubdiagram = new JButton("Sub-Diagram");
		add(this.btnSubdiagram, "10, 2");
		
		this.entityMenu = new JPopupMenu();
		this.existingEntitiesMenu = new JPopupMenu();
		this.existingEntitiesMenuItem = new JMenuItem(new AbstractAction("Existing Entity") {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterable<Entity> entities = diagramController.getAvailableEntities();
				for (final Entity entity : entities) {
					existingEntitiesMenu.removeAll();
					existingEntitiesMenu.add(new JMenuItem(new AbstractAction(entity.getName()) {			
						@Override
						public void actionPerformed(ActionEvent e) {
							 diagramController.handleCreatedEvent(entity);
						}
					}));
				}
				existingEntitiesMenu.show(btnEntity,  
						btnEntity.getX(), btnEntity.getY() +  btnEntity.getHeight());
			}
		});
		

		this.entityMenu.add(new JMenuItem(new AbstractAction("New Entity") {			
			@Override
			public void actionPerformed(ActionEvent e) {
				 diagramController.createEntity();
			}
		}));
		
		this.entityMenu.add(this.existingEntitiesMenuItem);		
	}

	@Override
	public void setController(IDiagramController controller) {
		this.diagramController = controller;
		
		this.graphComponent = new mxGraphComponent(this.diagramController.getGraph());
		// cannot create new arrows clicking from entity
		this.graphComponent.getConnectionHandler().setCreateTarget(false);
		this.graphComponent.setConnectable(false);
		MouseListener listener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (diagramController.hasPendingEntity()){
					try {
						diagramController.addEntity(e.getPoint().x, e.getPoint().y);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		this.graphComponent.getGraphControl().addMouseListener(listener);
		
		try {
			this.graphComponent.getDropTarget().addDropTargetListener(this);
		} catch (TooManyListenersException e1) {
			// should not occur
			e1.printStackTrace();
		}
		
		this.add(this.graphComponent, "2, 4, 15, 1, fill, fill");
		
		this.btnEntity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				entityMenu.show(e.getComponent(), btnEntity.getX(), btnEntity.getY() +  btnEntity.getHeight());
				//diagramController.createEntity();
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
				String diagramName = JOptionPane.showInputDialog(null, "Provide the diagram's name", "New Diagram", JOptionPane.QUESTION_MESSAGE);
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

	@Override
	public void refreshGraphComponent() {
		this.graphComponent.refresh();
	}
}
