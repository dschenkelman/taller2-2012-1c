package persistence;

import models.IdGroup;
import models.IdGroupCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class IdGroupCollectionXmlManager implements IXmlManager<IdGroupCollection> {

    private static String IDGROUPSTAG = "idGroups";
    private static String IDGROUPTAG = "idGroup";
    private static String NUMBERATTRIBUTE = "number";
    private static String ISKEYATTRIBUTE = "isKey";

    public IdGroupCollection getItemFromXmlElement(Element element) {
        IdGroupCollection idGroupCollection = new IdGroupCollection();
        NodeList idGroupList = element.getElementsByTagName(IDGROUPTAG);
        for (int i = 0; i < idGroupList.getLength(); i++) {
            Element idGroupElement = (Element) idGroupList.item(i);
            try {
                idGroupCollection.addIdGroup(new IdGroup(Integer.parseInt(idGroupElement.getAttribute(NUMBERATTRIBUTE)),Boolean.parseBoolean(idGroupElement.getAttribute(ISKEYATTRIBUTE))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idGroupCollection;
    }

    public Element getElementFromItem(IdGroupCollection idGroupCollection, Document document) {

        Element idGroupsElement = document.createElement(IDGROUPSTAG);

        Iterable<IdGroup> idGroupList = idGroupCollection.getIdGroups();
        for (IdGroup idGroup : idGroupList) {
            Element idGroupElement = document.createElement(IDGROUPTAG);
            idGroupElement.setAttribute(NUMBERATTRIBUTE, idGroup.getNumber().toString());
            idGroupElement.setAttribute(ISKEYATTRIBUTE, idGroup.isKey().toString());
            idGroupsElement.appendChild(idGroupElement);
        }

        return idGroupsElement;
    }
}
