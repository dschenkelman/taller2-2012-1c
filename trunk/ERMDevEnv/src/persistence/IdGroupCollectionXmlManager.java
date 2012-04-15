package persistence;

import models.IdGroupCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class IdGroupCollectionXmlManager {

    private static String IDGROUPSTAG = "idGroups";
    private static String IDGROUPTAG = "idGroup";
    private static String NUMBERATTRIBUTE = "number";

    public static IdGroupCollection getIdGroupCollectionFromElement(Element element) {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        NodeList idGroupsList = element.getElementsByTagName(IDGROUPSTAG);
        if (idGroupsList.getLength() != 0) {
            Element idGroups = (Element) idGroupsList.item(0);
            NodeList idGroupList = idGroups.getElementsByTagName(IDGROUPTAG);
            for (int i = 0; i < idGroupList.getLength(); i++) {
                Element idGroupElement = (Element) idGroupList.item(i);
                try {
                    idGroupCollection.addIdGroup(Integer.parseInt(idGroupElement.getAttribute(NUMBERATTRIBUTE)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return idGroupCollection;
    }

    public static Element getIdGroupCollectionToAttribute(IdGroupCollection idGroupCollection, Document document){

        Element idGroupsElement = document.createElement(IDGROUPSTAG);

        Iterable<Integer> idGroupList = idGroupCollection.getIdGroups();
        for(Integer number : idGroupList){
            Element idGroupElement = document.createElement(IDGROUPTAG);
            idGroupElement.setAttribute(NUMBERATTRIBUTE,number.toString());
            idGroupsElement.appendChild(idGroupElement);
        }

        return idGroupsElement;
    }
}