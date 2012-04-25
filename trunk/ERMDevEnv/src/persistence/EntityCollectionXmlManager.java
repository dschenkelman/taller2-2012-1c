package persistence;

import models.Entity;
import models.EntityCollection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class EntityCollectionXmlManager {

	public Element getElementFromItem(EntityCollection entityCollection,
			Document document) {
		Element element = document.createElement("entities");
		
		for (Entity entity : entityCollection) {
			element.appendChild(new EntityXmlManager().getElementFromItem(entity, document));
		}
		
		return element;
	}

	public EntityCollection getItemFromElement(Element entityCollectionElement) throws Exception {
		EntityCollection entityCollection = new EntityCollection();
		
		NodeList entities = entityCollectionElement.getElementsByTagName("entity");
		
		for (int i = 0; i < entities.getLength(); i++)
		{
			Element entityElement = (Element) entities.item(i);
			entityCollection.add(new EntityXmlManager().getItemFromXmlElement(entityElement));
		}
		
		return entityCollection;
	}

}