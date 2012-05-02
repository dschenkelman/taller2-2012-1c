package persistence;

import java.util.ArrayList;
import java.util.List;

import models.Relationship;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RelationshipCollectionXmlManager implements IXmlManager<List<Relationship>> {

	public Element getElementFromItem(List<Relationship> collection,
			Document document) {
		Element element = document.createElement("relationships");
		
		for (Relationship relationship : collection) {
			element.appendChild(new RelationshipXmlManager().getElementFromItem(relationship, document));
		}
		
		return element;
	}

	public List<Relationship> getItemFromXmlElement(Element relationshipsElement) throws Exception {
		List<Relationship> relationships = new ArrayList<Relationship>();
		
		if (relationshipsElement == null)
			return relationships;
		NodeList relationshipsList = relationshipsElement.getElementsByTagName("relationship");
		
		for (int i = 0; i < relationshipsList.getLength(); i++) {
			Element relationshipElement = (Element) relationshipsList.item(i);
			relationships.add(new RelationshipXmlManager().getItemFromXmlElement(relationshipElement));
		}
		
		return relationships;
	}

}
