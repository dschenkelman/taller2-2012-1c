package persistence;

import java.util.List;

import models.Relationship;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RelationshipCollectionXmlManager {

	public Element getElementFromItem(List<Relationship> collection,
			Document document) {
		Element element = document.createElement("relationships");
		
		for (Relationship relationship : collection) {
			element.appendChild(new RelationshipXmlManager().getElementFromItem(relationship, document));
		}
		
		return element;
	}

}
