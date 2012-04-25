package persistence;

import infrastructure.StringExtensions;

import java.util.Iterator;
import java.util.UUID;

import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RelationshipXmlManager {

	public Element getElementFromItem(Relationship relationship,
			Document document) {
		Element relationshipElement = document.createElement("relationship");
		
		relationshipElement.setAttribute("id", relationship.getId().toString());
		relationshipElement.setAttribute("name", 
				StringExtensions.isNullOrEmpty(relationship.getName()) ? "" : relationship.getName());
		relationshipElement.setAttribute("composition", relationship.isComposition().toString());
		
		Element entitiesElement = document.createElement("entities");
		
		for (RelationshipEntity relationshipEntity : relationship.getRelationshipEntities()) 
		{
			entitiesElement.appendChild(
					new RelationshipEntityXmlManager()
						.getElementFromItem(relationshipEntity, document));
		}
		
		relationshipElement.appendChild(entitiesElement);
		
		return relationshipElement;
	}
	
	public Relationship getItemFromXmlElement(Element relationshipElement) throws Exception {
		UUID id = UUID.fromString(relationshipElement.getAttribute("id"));
		String name = XmlExtensions.getStringOrNull(relationshipElement, "name");
		Boolean composition = XmlExtensions.getBooleanOrDefault(relationshipElement, "composition", false);
		
		Relationship relationship = new Relationship(id, name, composition);
		
		NodeList entitiesNodeList = relationshipElement.getElementsByTagName("entities");
		
		Element entitiesElement = (Element) entitiesNodeList.item(0);
		
		NodeList entityNodeList = entitiesElement.getElementsByTagName("entity");
		
		for (int i = 0; i < entityNodeList.getLength(); i++) {
			Element entityElement = (Element) entityNodeList.item(i);
			relationship.addRelationshipEntity(
					new RelationshipEntityXmlManager().getItemFromXmlElement(entityElement));
		}
		
		return relationship;
	}
	
	private class RelationshipEntityXmlManager
	{
		public Element getElementFromItem(RelationshipEntity relationshipEntity,
				Document document) {
			Element entityElement = document.createElement("entity");
			
			entityElement.setAttribute("entityId", relationshipEntity.getEntityId().toString());
			if (relationshipEntity.getCardinality() != null)
			{
				String minimum = this.getStringForCardinality(relationshipEntity.getCardinality().getMinimum());
				String maximum = this.getStringForCardinality(relationshipEntity.getCardinality().getMaximum());
				
				entityElement.setAttribute("minimumCardinality", minimum);
				entityElement.setAttribute("maximumCardinality", maximum);
			}
			
			if (relationshipEntity.getRole() != null && !relationshipEntity.getRole().isEmpty()){
				entityElement.setAttribute("role", relationshipEntity.getRole());
			}
			
			
			return entityElement;
		}

		public RelationshipEntity getItemFromXmlElement(Element entityElement) throws Exception {
			UUID id = UUID.fromString(entityElement.getAttribute("entityId"));
			double minimum = this.getCardinalityFromString(
					XmlExtensions.getStringOrNull(entityElement, "minimumCardinality"));
			double maximum = this.getCardinalityFromString(
					XmlExtensions.getStringOrNull(entityElement, "maximumCardinality"));
			String role = XmlExtensions.getStringOrNull(entityElement, "role");
			
			return new RelationshipEntity(id, new Cardinality(minimum, maximum), role);
		}

		private double getCardinalityFromString(String attribute) {
			return attribute.equalsIgnoreCase("*") ? Double.POSITIVE_INFINITY : Double.parseDouble(attribute);
		}

		private String getStringForCardinality(double value) {
			return value == Double.POSITIVE_INFINITY ? "*" : Integer.toString((int) value);
		}
	}
}
