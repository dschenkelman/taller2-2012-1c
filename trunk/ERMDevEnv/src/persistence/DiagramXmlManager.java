package persistence;

import java.util.UUID;

import models.DiagramType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DiagramXmlManager {

	public Element getElementFromItem(DiagramType diagram, Document document) {
		Element diagramElement = document.createElement("diagram");
		
		diagramElement.setAttribute("id", diagram.getId().toString());
		diagramElement.appendChild(new EntityCollectionXmlManager().getElementFromItem(diagram.getEntities(), document));
		diagramElement.appendChild(new RelationshipCollectionXmlManager().getElementFromItem(diagram.getRelationships(), document));
		diagramElement.appendChild(HierarchyCollectionXmlManager.getHierarchiesElementFromHierarchyCollection(diagram.getHierarchies(), document));
		
		Element element = document.createElement("subDiagrams");
		for (DiagramType subDiagram : diagram) {
				element.appendChild(this.getElementFromItem(subDiagram, document));
		}
		diagramElement.appendChild(element);
	
		return diagramElement;
	}

	public DiagramType getItemFromElement(Element diagramElement) throws Exception {
		DiagramType diagram = new DiagramType(UUID.fromString(diagramElement.getAttribute("id")));
		
		Element entitiesElement = (Element) diagramElement.getElementsByTagName("entities").item(0);
		Element relationshipsElement = (Element) diagramElement.getElementsByTagName("relationships").item(0);
		Element hierarchiesElement = (Element) diagramElement.getElementsByTagName("hierarchies").item(0);
		Element diagramsElement = (Element) diagramElement.getElementsByTagName("diagrams").item(0);
		
		diagram.setEntities(new EntityCollectionXmlManager().getItemFromElement(entitiesElement));
		diagram.setRelationships(new RelationshipCollectionXmlManager().getItemFromElement(relationshipsElement));
		diagram.setHierarchies(HierarchyCollectionXmlManager.getHierarchyCollectionFromElement(hierarchiesElement));
		
		for (int i = 0; i < diagramsElement.getChildNodes().getLength(); i++) {
			diagram.addSubDiagram(new DiagramXmlManager().getItemFromElement((Element) diagramsElement.getChildNodes().item(i)));
		}
		return diagram;
	}

}
