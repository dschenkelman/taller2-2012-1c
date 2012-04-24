package persistence;

import java.util.UUID;

import models.Entity;
import models.EntityType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EntityXmlManager {

	public Element getElementFromItem(Entity entity, Document document) {
		Element entityElement = document.createElement("entity");
		
		entityElement.setAttribute("name", entity.getName().toString());
		entityElement.setAttribute("id", entity.getId().toString());
		entityElement.setAttribute("type", entity.getType().toString());
		
		return entityElement;
	}

	public Entity getItemFromXmlElement(Element entityElement) throws Exception{
		String name = entityElement.getAttribute("name");
		EntityType type = EntityType.valueOf(entityElement.getAttribute("type"));
		UUID id = UUID.fromString(entityElement.getAttribute("id"));
		
		return new Entity(name, id, type);
	}
}
