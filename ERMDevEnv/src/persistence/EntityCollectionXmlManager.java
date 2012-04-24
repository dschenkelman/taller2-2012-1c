package persistence;

import models.Entity;
import models.EntityCollection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persistence.tests.TestUtilities;

public class EntityCollectionXmlManager {

	public Element getElementFromItem(EntityCollection entityCollection,
			Document document) {
		Element element = document.createElement("entities");
		
		for (Entity entity : entityCollection) {
			element.appendChild(new EntityXmlManager().getElementFromItem(entity, document));
		}
		
		return element;
	}

}
