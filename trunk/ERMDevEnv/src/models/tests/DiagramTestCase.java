package models.tests;


import junit.framework.Assert;
import models.DiagramType;
import models.EntityCollection;
import models.HierarchyCollection;
import models.RelationshipCollection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DiagramTestCase {
	
	@Test
	public void testCreatingDiagramProvidesContentsIsNone()
	{
		DiagramType diagram = new DiagramType();
		Assert.assertNull(diagram.getEntities());
		Assert.assertNull(diagram.getRelationships());
		Assert.assertNull(diagram.getHierarchies());
		Assert.assertNull(diagram.getSubDiagram());
	}
	
	@Test
	public void testTwoDiagramProvidesDifferentsIdNumbers()
	{
		DiagramType diagram = new DiagramType();
		DiagramType diagram1 = new DiagramType();
		Assert.assertTrue(diagram.getId().toString() != diagram1.getId().toString());
	}
	
	
	@Test
	public void testCreateAnCompleteDiagram()
	{
		DiagramType diagram = new DiagramType(new EntityCollection(), 
				new RelationshipCollection(), new HierarchyCollection(), 
				new DiagramType());
		
		Assert.assertEquals(0, diagram.getEntities().count());
//		Assert.assertEquals(0, diagram.getRelationships());			// can't do anything until somebody implements relationships
//		Assert.assertEquals(0, diagram.getHierarchies().); 			// can't do anything until somebody implements hierarchies
		Assert.assertEquals(0, diagram.getEntities().count());
		Assert.assertNotNull(diagram.getSubDiagram());
		Assert.assertTrue(diagram.getId().toString() != diagram.getSubDiagram().getId().toString());
		
	}
	
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
