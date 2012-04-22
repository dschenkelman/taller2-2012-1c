package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import models.Attribute;
import models.AttributeCollection;
import models.Hierarchy;
import models.HierarchyCollection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class AttributeCollectionXmlManager {

	private static String ATTRIBUTES = "attributes";
	private static String ATTRIBUTE = "attribute";
	private static String IDGROUPS = "idGroups";
	private static String NAME = "name";
	private static String TYPE = "type";
	private static String EXPRESSION = "expression";
	private static String MINCARDINALITY  = "minimumCardinality";
	private static String MAXCARDINALITY = "maximumCardinality";
	private static String OWNERID  = "ownerId";
	private static String ID = "id";
	private static String TRUE = "true";
	private static String FALSE = "false";
	

	
	public static Element getAttributesElementFromHierarchyCollection(
			AttributeCollection attCol, Document document) {
		
		Element attributesElement = document.createElement(ATTRIBUTES);

        Iterator<Attribute> attributesIte = attCol.iterator();
       while(attributesIte.hasNext()) {
    	   Attribute attribute = attributesIte.next();
    	   Node attributeNode = createAttributeNode(attribute, document);
           attributesElement.appendChild(attributeNode);
        }

		return attributesElement;
	}



	private static Node createAttributeNode(Attribute attribute,
			Document document) {
		 Element attributeElement = document.createElement(ATTRIBUTES);
		 
		 
		 
		return null;
	}
	
	
    private static Node createHierarchyNode(Attribute att, Document document) {
       

        

        Attr totalAttribute = document.createAttribute(TOTALATTRIBUTE);
        totalAttribute.setValue(hierarchy.isTotal() ? TRUE : FALSE);
        hierarchyElement.setAttributeNode(totalAttribute);

        Attr exclusiveAttribute = document.createAttribute(EXCLUSIVEATTRIBUTE);
        exclusiveAttribute.setValue(hierarchy.isExclusive() ? TRUE : FALSE);
        hierarchyElement.setAttributeNode(exclusiveAttribute);

        Attr idAttribute = document.createAttribute(IDATTRIBUTE);
        idAttribute.setValue(hierarchy.getUUID().toString());
        hierarchyElement.setAttributeNode(idAttribute);

        Element specificEntitiesElement = document.createElement(SPECIFICENTITIES);
        Iterable<UUID> specificEntities = hierarchy.getChildren();
        for (UUID entityChild : specificEntities) {
            Node hierarchyNode = createSpecificEntitiesNode(entityChild, document);
            specificEntitiesElement.appendChild(hierarchyNode);
        }

        hierarchyElement.appendChild(specificEntitiesElement);
        return hierarchyElement;
    }

    private static Node createSpecificEntitiesNode(UUID childHierarchy, Document document) {
        Element entityIdElement = document.createElement(ENTITYID);
        entityIdElement.appendChild(document.createTextNode(childHierarchy.toString()));
        return entityIdElement;
    }

    private static void addHierarchiesToHierarchyCollection(NodeList hierarchies, HierarchyCollection hierarchyCollection) {

        for (int i = 0; i < hierarchies.getLength(); i++) {
            Element hierarchyElement = (Element) hierarchies.item(i);

            boolean exclusive = hierarchyElement.getAttribute(EXCLUSIVEATTRIBUTE).equals(TRUE);
            boolean total = hierarchyElement.getAttribute(TOTALATTRIBUTE).equals(TRUE);
            UUID generalEntityUUID = UUID.fromString(hierarchyElement.getAttribute(GENERALENTITYATTRIBUTE));
            UUID hierarchyId = UUID.fromString(hierarchyElement.getAttribute(IDATTRIBUTE));
            ArrayList<UUID> entityIds = new ArrayList<UUID>();

            NodeList specificsHierarchies = hierarchyElement.getElementsByTagName(SPECIFICENTITIES);
            if (specificsHierarchies.getLength() != 0) {
                NodeList entityIdsElement = ((Element) specificsHierarchies.item(0)).getElementsByTagName(ENTITYID);
                for (int j = 0; j < entityIdsElement.getLength(); j++) {
                    Text id = (Text) (entityIdsElement.item(j)).getFirstChild();
                    entityIds.add(UUID.fromString(id.getNodeValue()));
                }
            }

            try {
                hierarchyCollection.createHierarchy(hierarchyId, generalEntityUUID, exclusive, total, entityIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	

}
