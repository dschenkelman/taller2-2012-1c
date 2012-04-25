package persistence;

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

}
