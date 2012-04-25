package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import models.Attribute;
import models.AttributeCollection;
import models.Hierarchy;
import models.HierarchyCollection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class AttributeCollectionXmlManager {

	public static AttributeCollection getItemFromXmlElement(Element attributesElem){
		
		
		return null;
	}

	public static Element getXMLElementFromItem (AttributeCollection attCol, Document doc) {
		Element attColElement = doc.createElement("attributes");
		Iterator<Attribute> ite = attCol.iterator();
		while (ite.hasNext()) {
			Attribute att = ite.next();
			attColElement.appendChild(AttributeCollectionXmlManager.createAttributeNode(att,doc));
		}		
		return attColElement;
	}
	

	private static Node createAttributeNode(Attribute attribute,
			Document document) {
		 Element attributeElement = document.createElement("attribute");
		 if (attribute.isComposite()) {
			 attributeElement.appendChild(AttributeCollectionXmlManager.getXMLElementFromItem(attribute.getAttributes()
					 , document));
		 }
		 if (attribute.getIdGroup()!= null) {
			 attributeElement.appendChild(IdGroupCollectionXmlManager.getIdGroupCollectionToAttribute(attribute.getIdGroup(),
					 document));
		 }
		 
		 //seteo todos los atributos del elemento Attribute
		 attributeElement.setAttribute("name", attribute.getName().toString());
		 if (attribute.getType()!=null)   attributeElement.setAttribute("type", attribute.getType().toString());
		 if (attribute.getExpression()!=null) attributeElement.setAttribute("expression",attribute.getExpression().toString());
		 if (attribute.getCardinality()!=null) {
			 attributeElement.setAttribute("minimumCardinality",String.valueOf(attribute.getCardinality().getMinimum()));
			 attributeElement.setAttribute("maximumCardinality",String.valueOf(attribute.getCardinality().getMaximum()));
		 }
		 if (attribute.getOwnerId() != null) attributeElement.setAttribute("ownerId",attribute.getOwnerId().toString());
		 attributeElement.setAttribute("id",attribute.getId().toString());
		return attributeElement;
	}
	

}
