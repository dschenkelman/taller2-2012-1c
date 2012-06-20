package infrastructure.tests;

import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;

import models.Diagram;
import models.Entity;
import models.EntityCollection;
import models.Hierarchy;
import models.HierarchyCollection;

import org.junit.Before;
import org.junit.Test;

import controllers.HierarchyController;

import infrastructure.ProjectContext;


public class ProjectContextTestCase {

	private ProjectContext projectContext;
	private Diagram diagram;
	private EntityCollection entities;
	private HierarchyCollection hierarchies;
	
	@Before
	public void setUp() {
		this.projectContext = new ProjectContext();
		this.diagram = new Diagram();
		this.entities = new EntityCollection();
		this.hierarchies = new HierarchyCollection();
	}
	

	@Test
	public void testShouldReturnZeroContent() {
		Assert.assertEquals(0, ((HashSet<Entity>) this.projectContext.getAllEntities(null)).size());
		Assert.assertEquals(0, ((HashSet<Hierarchy>) this.projectContext.getAllHierarchies()).size());
		Assert.assertEquals(0, ((HashSet<Entity>) this.projectContext.getContextEntities(null)).size());
		Assert.assertEquals(0, ((HashSet<Hierarchy>) this.projectContext.getContextHierarchies()).size());
	}
	
	@Test
	public void testShouldReturnEntityAndHierarchyContent() {
		this.entities.add("entity1");
		this.entities.add("entity2");
		Hierarchy hierarchy = new Hierarchy();
		hierarchy.setGeneralEntityId(this.entities.get("entity1").getId());
		try {
			hierarchy.addChildEntity(this.entities.get("entity2").getId());
		} catch (Exception e) {
		}
		this.hierarchies.add(hierarchy);
		this.diagram.setEntities(this.entities);
		this.diagram.setHierarchies(this.hierarchies);
		
		this.projectContext.addProjectDiagram(this.diagram);
		
		Assert.assertEquals(2, ((HashSet<Entity>) this.projectContext.getAllEntities(null)).size());
		Assert.assertEquals(1, ((HashSet<Hierarchy>) this.projectContext.getAllHierarchies()).size());
		Assert.assertEquals(1, ((HashSet<Entity>) this.projectContext.getAllEntities(new Entity("entity1"))).size());
		Assert.assertTrue(((HashSet<Entity>) this.projectContext.getAllEntities(null)).contains(this.entities.get("entity1")));
		Assert.assertTrue(((HashSet<Entity>) this.projectContext.getAllEntities(null)).contains(this.entities.get("entity2")));
		Assert.assertTrue(((HashSet<Hierarchy>) this.projectContext.getAllHierarchies()).contains(hierarchy));
		Assert.assertSame(this.entities.get("entity1"), this.projectContext.getEntity(this.entities.get("entity1").getId()));
		Assert.assertSame(hierarchy, this.projectContext.getHierarchy(hierarchy.getId()));
	}
}
