package persistence;

import java.util.UUID;

import models.Diagram;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DiagramXmlManager implements IXmlManager<Diagram> {

	public Element getElementFromItem(Diagram diagram, Document document) {
		Element diagramElement = document.createElement("diagram");
		
		diagramElement.setAttribute("id", diagram.getId().toString());
		diagramElement.appendChild(new EntityCollectionXmlManager().getElementFromItem(diagram.getEntities(), document));
		diagramElement.appendChild(new RelationshipCollectionXmlManager().getElementFromItem(diagram.getRelationships(), document));
		diagramElement.appendChild(new HierarchyCollectionXmlManager().getElementFromItem(diagram.getHierarchies(), document));
		
		Element element = document.createElement("subDiagrams");
		for (Diagram subDiagram : diagram) {
				element.appendChild(this.getElementFromItem(subDiagram, document));
		}
		diagramElement.appendChild(element);
	
		return diagramElement;
	}

	public Diagram getItemFromXmlElement(Element diagramElement) throws Exception {
		Diagram diagram = new Diagram(UUID.fromString(diagramElement.getAttribute("id")));
		
		Element entitiesElement = (Element) diagramElement.getElementsByTagName("entities").item(0);
		Element relationshipsElement = (Element) diagramElement.getElementsByTagName("relationships").item(0);
		Element hierarchiesElement = (Element) diagramElement.getElementsByTagName("hierarchies").item(0);
		Element diagramsElement = (Element) diagramElement.getElementsByTagName("diagrams").item(0);
		
		diagram.setEntities(new EntityCollectionXmlManager().getItemFromXmlElement(entitiesElement));
		diagram.setRelationships(new RelationshipCollectionXmlManager().getItemFromXmlElement(relationshipsElement));
		diagram.setHierarchies(new HierarchyCollectionXmlManager().getItemFromXmlElement(hierarchiesElement));
		
		for (int i = 0; i < diagramsElement.getChildNodes().getLength(); i++) {
			diagram.addSubDiagram(new DiagramXmlManager().getItemFromXmlElement((Element) diagramsElement.getChildNodes().item(i)));
		}
		return diagram;
	}

}
