package persistence;

import infrastructure.StringExtensions;

import java.util.Iterator;
import java.util.UUID;

import models.AttributeCollection;
import models.Cardinality;
import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.hamcrest.core.IsNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import persistence.tests.mocks.MockAttributeCollectionXmlManager;

public class RelationshipXmlManager implements IXmlManager<Relationship> {

	private IXmlManager<AttributeCollection> attributeCollectionXmlManager;

	public RelationshipXmlManager(){
		this(new AttributeCollectionXmlManager());
	}
	
	public RelationshipXmlManager(
			IXmlManager<AttributeCollection> attributeCollectionXmlManager) {
		this.attributeCollectionXmlManager = attributeCollectionXmlManager;
	}

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
		
		Element attributes = this.attributeCollectionXmlManager.getElementFromItem(relationship.getAttributes(), document);
		relationshipElement.appendChild(attributes);
		
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
		
		Element attributesElement = (Element) relationshipElement.getElementsByTagName("attributes").item(0);
		
		AttributeCollection attributes = this.attributeCollectionXmlManager.getItemFromXmlElement(attributesElement);
		relationship.setAttributes(attributes);
		
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
			String minimumCard = XmlExtensions.getStringOrNull(entityElement, "minimumCardinality");
			String maximumCard = XmlExtensions.getStringOrNull(entityElement, "maximumCardinality");
			String role = XmlExtensions.getStringOrNull(entityElement, "role");
			double minimum;
			double maximum;
			if (minimumCard == null && maximumCard == null)
				return new RelationshipEntity(id, null, role);
			if (minimumCard == null) {
				maximum = this.getCardinalityFromString(maximumCard);
				return new RelationshipEntity(id, new Cardinality(0, maximum), role);
			}
			if (maximumCard == null) {
				minimum = this.getCardinalityFromString(minimumCard);
				return new RelationshipEntity(id, new Cardinality(minimum, Double.POSITIVE_INFINITY), role);
			}
			minimum = this.getCardinalityFromString(minimumCard);		
			maximum = this.getCardinalityFromString(maximumCard);
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
