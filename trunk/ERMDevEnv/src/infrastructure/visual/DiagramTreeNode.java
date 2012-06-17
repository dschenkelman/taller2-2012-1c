package infrastructure.visual;

import javax.swing.text.html.parser.Entity;
import javax.swing.tree.DefaultMutableTreeNode;

import models.Diagram;
import models.Hierarchy;
import models.Relationship;

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
	
	public DiagramTreeNode(Object userObject) {
		super(userObject);
		this.addChildFolders();
	}
	
	public void addEntity(Entity entity){
		this.entitiesNode.add(new DefaultMutableTreeNode(entity));
	}
	
	public void addRelationship(Relationship relationship){
		this.relationshipsNode.add(new DefaultMutableTreeNode(relationship));
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
