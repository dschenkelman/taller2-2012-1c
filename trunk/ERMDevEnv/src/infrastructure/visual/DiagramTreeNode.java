package infrastructure.visual;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import models.Diagram;
import models.Hierarchy;
import models.Relationship;
import models.Entity;

public class DiagramTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5605938989721186879L;
	
	private static String SubDiagramsNodeName = "Sub-Diagramas";
	private static String EntitiesNodeName = "Entidades";
	private static String RelationshipsNodeName = "Relaciones";
	private static String HierarchiesNodeName = "Jerarquias";

	private DefaultMutableTreeNode subdiagramsNode;

	private DefaultMutableTreeNode entitiesNode;

	private DefaultMutableTreeNode relationshipsNode;

	private DefaultMutableTreeNode hierarchiesNode;

	private Diagram diagram;
	
	public DiagramTreeNode(Diagram diagram) {
		super(diagram);
		this.diagram = diagram;
		this.addChildFolders();
		this.populateEntities();
		this.populateRelationships();
	}
	
	private void populateEntities() {
		for (Entity entity : this.diagram.getEntities()) {
			this.entitiesNode.add(new DefaultMutableTreeNode(entity));
		}
	}
	
	private void populateRelationships() {
		for (Relationship relationship : this.diagram.getRelationships()) {
			this.relationshipsNode.add(new DefaultMutableTreeNode(relationship));
		}
	}

	public void addEntity(Entity entity, DefaultTreeModel tree){
		this.entitiesNode.add(new DefaultMutableTreeNode(entity));
		int index = this.entitiesNode.getChildCount() - 1;
		tree.nodesWereInserted(this.entitiesNode, new int[]{index});
	}
	
	public void addRelationship(Relationship relationship, DefaultTreeModel tree){
		this.relationshipsNode.add(new DefaultMutableTreeNode(relationship));
		int index = this.relationshipsNode.getChildCount() - 1;
		tree.nodesWereInserted(this.relationshipsNode, new int[]{index});
	}
	
	public void addHierarchy(Hierarchy hierarchy){
		this.hierarchiesNode.add(new DefaultMutableTreeNode(hierarchy));
	}
	
	public DiagramTreeNode addSubdiagram(Diagram diagram){
		DiagramTreeNode diagramNode = new DiagramTreeNode(diagram);
		this.subdiagramsNode.add(diagramNode);
		return diagramNode;
	}

	private void addChildFolders() {
		this.entitiesNode = new DefaultMutableTreeNode(EntitiesNodeName);
		this.relationshipsNode = new DefaultMutableTreeNode(RelationshipsNodeName);
		this.hierarchiesNode = new DefaultMutableTreeNode(HierarchiesNodeName);
		this.subdiagramsNode = new DefaultMutableTreeNode(SubDiagramsNodeName);
		this.add(this.entitiesNode);
		this.add(this.relationshipsNode);
		this.add(this.hierarchiesNode);
		this.add(this.subdiagramsNode);
	}

}
