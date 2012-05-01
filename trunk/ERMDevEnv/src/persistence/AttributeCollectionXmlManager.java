package persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import models.Attribute;
import models.AttributeCollection;
import models.AttributeType;
import models.Cardinality;
import models.Hierarchy;
import models.HierarchyCollection;
import models.IdGroupCollection;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class AttributeCollectionXmlManager implements IXmlManager<AttributeCollection>{

	public AttributeCollection getItemFromXmlElement(Element attributesElem) throws NumberFormatException, Exception{
		NodeList attributes = attributesElem.getElementsByTagName("attribute");
		AttributeCollection attCollection  = new AttributeCollection();
		for (int i = 0 ; i < attributes.getLength() ; i++) {
			Element attribute  = (Element) attributes.item(i);		
			String name  = attribute.getAttribute("name");
			Cardinality cardinality = null;
			if (!attribute.getAttribute("minimumCardinality").isEmpty() && !attribute.getAttribute("maximumCardinality").isEmpty()) {
				cardinality  = new Cardinality (Double.valueOf(attribute.getAttribute("minimumCardinality")),
					Double.valueOf(attribute.getAttribute("maximumCardinality")));
			}
			
			String expression  = null;
			if (!attribute.getAttribute("expression").isEmpty())
				expression = attribute.getAttribute("expression");
			
			AttributeType type = null;
			if (!attribute.getAttribute("type").isEmpty())
				type = AttributeType.valueOf(attribute.getAttribute("type"));
			
			
			IdGroupCollection idGroup = null;
			
			if (attribute.getElementsByTagName("idGroups").getLength() > 0)
				idGroup = new IdGroupCollectionXmlManager().getItemFromXmlElement((Element)attribute.getElementsByTagName("idGroups").item(0));
			
			
			AttributeCollection attrCol = null;
			if (attribute.getElementsByTagName("attributes").getLength() > 0)
				attrCol = new AttributeCollectionXmlManager().getItemFromXmlElement((Element)attribute.getElementsByTagName("attributes").item(0));
			
			UUID myID = UUID.fromString(attribute.getAttribute("id"));

			attCollection.addAttribute(new Attribute(name,false,cardinality,idGroup,type,expression,attrCol,myID));
			
		}
		
		return attCollection;
	}

	public Element getElementFromItem (AttributeCollection attCol, Document doc) {
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
		 if (attribute.getAttributes()!= null) {
			 attributeElement.appendChild(new AttributeCollectionXmlManager().getElementFromItem(attribute.getAttributes()
					 , document));
		 }
		 if (attribute.getIdGroup()!= null) {
			 attributeElement.appendChild(new IdGroupCollectionXmlManager().getElementFromItem(attribute.getIdGroup(),
					 document));
		 }
		 
		 //seteo todos los atributos del elemento Attribute
		 attributeElement.setAttribute("name", attribute.getName().toString());
		 if (attribute.getType()!=null)   attributeElement.setAttribute("type", attribute.getType().toString());
		 if ((attribute.getType() == AttributeType.calculated || attribute.getType() == AttributeType.copy) && attribute.getExpression() != null ) 
			 attributeElement.setAttribute("expression",attribute.getExpression().toString());
		 if (attribute.getCardinality()!=null) {
			 attributeElement.setAttribute("minimumCardinality",String.valueOf(attribute.getCardinality().getMinimum()));
			 attributeElement.setAttribute("maximumCardinality",String.valueOf(attribute.getCardinality().getMaximum()));
		 }
		 attributeElement.setAttribute("id",attribute.getId().toString());
		return attributeElement;
	}
	

}
