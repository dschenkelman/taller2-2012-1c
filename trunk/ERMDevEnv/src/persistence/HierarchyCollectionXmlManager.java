package persistence;

import models.Hierarchy;
import models.HierarchyCollection;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


public class HierarchyCollectionXmlManager {

    public static String HIERARCHIES = "hierarchies";
    public static String HIERARCHY = "hierarchy";
    private static String SPECIFICENTITIES = "specificEntities";
    private static String ENTITYID = "entityId";
    private static String GENERALENTITYATTRIBUTE = "generalEntityId";
    private static String TOTALATTRIBUTE = "total";
    private static String EXCLUSIVEATTRIBUTE = "exclusive";
    private static String IDATTRIBUTE = "id";
    private static String TRUE = "true";
    private static String FALSE = "false";

    public static HierarchyCollection getHierarchyCollectionFromDocument(Document document) {
        HierarchyCollection hierarchyCollection = new HierarchyCollection();
        Element diagram = document.getDocumentElement();

        NodeList hierarchiesElements = diagram.getElementsByTagName(HIERARCHIES);

        if (hierarchiesElements.getLength() != 0) {
            Element hierarchiesElement = (Element) hierarchiesElements.item(0);
            NodeList hierarchies = hierarchiesElement.getElementsByTagName(HIERARCHY);
            addHierarchiesToHierarchyCollection(hierarchies, hierarchyCollection);
        }

        return hierarchyCollection;
    }

    public static void addHierarchiesElementsFromHierarchyCollection(HierarchyCollection hierarchyCollection, Document document, Element diagram) {

        Element hierarchiesElement = document.createElement(HIERARCHIES);
        diagram.appendChild(hierarchiesElement);

        Iterable<Hierarchy> hierarchies = hierarchyCollection.getHierarchies();
        for (Hierarchy hierarchy : hierarchies) {
            Node hierarchyNode = createHierarchyNode(hierarchy, document);
            hierarchiesElement.appendChild(hierarchyNode);
        }

    }

    private static Node createHierarchyNode(Hierarchy hierarchy, Document document) {
        Element hierarchyElement = document.createElement(HIERARCHY);

        Attr generalEntityIdAttribute = document.createAttribute(GENERALENTITYATTRIBUTE);
        generalEntityIdAttribute.setValue(hierarchy.getGeneralEntityUUID().toString());
        hierarchyElement.setAttributeNode(generalEntityIdAttribute);

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
                hierarchyCollection.addHierarchy(hierarchyId, generalEntityUUID, exclusive, total, entityIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
