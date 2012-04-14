package persistence;

import models.Entity;
import models.Relationship;
import models.RelationshipEntity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RelationshipXmlManager {

	public Element getElementFromItem(Relationship relationship,
			Document document) {
		Element relationshipEntityElement = document.createElement("relationship");
		
		relationshipEntityElement.setAttribute("id", relationship.getId().toString());
		relationshipEntityElement.setAttribute("name", relationship.getName().toString());
		relationshipEntityElement.setAttribute("composition", relationship.isComposition().toString());
		
		Element entitiesElement = document.createElement("entities");
		
		for (RelationshipEntity relationshipEntity : relationship.getRelationshipEntities()) 
		{
			entitiesElement.appendChild(
					new RelationshipEntityXmlManager()
						.getElementFromItem(relationshipEntity, document));
		}
		
		relationshipEntityElement.appendChild(entitiesElement);
		
		return relationshipEntityElement;
	}
	
	private class RelationshipEntityXmlManager
	{
		public Element getElementFromItem(RelationshipEntity relationshipEntity,
				Document document) {
			Element entityElement = document.createElement("entity");
			
			entityElement.setAttribute("entityId", relationshipEntity.getEntity().getId().toString());
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

		private String getStringForCardinality(double value) {
			return value == Double.POSITIVE_INFINITY ? "*" : Integer.toString((int) value);
		}
	}

	
}
