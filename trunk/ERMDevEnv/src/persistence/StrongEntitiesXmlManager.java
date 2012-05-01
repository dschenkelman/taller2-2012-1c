package persistence;

import models.IStrongEntity;
import models.StrongEntityCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.UUID;

public class StrongEntitiesXmlManager implements IXmlManager<StrongEntityCollection> {
    private static final String STRONG_ENTITIES_TAG = "strongEntities";
    private static final String STRONG_ENTITY_TAG = "strongEntity";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String ID_ATTRIBUTE = "id";

    public StrongEntityCollection getItemFromXmlElement(Element element) {
        StrongEntityCollection strongEntityCollection = new StrongEntityCollection();
        NodeList nodeList = element.getElementsByTagName(STRONG_ENTITIES_TAG);
        if (nodeList.getLength() != 0) {
            NodeList strongEntityTags = ((Element) nodeList.item(0)).getElementsByTagName(STRONG_ENTITY_TAG);
            for (int item = 0; item < strongEntityTags.getLength(); item++) {
                Element strongEntityElement = ((Element) strongEntityTags.item(item));
                try {
                    strongEntityCollection.addStrongEntity(new StrongEntity(strongEntityElement.getAttribute(NAME_ATTRIBUTE), UUID.fromString(strongEntityElement.getAttribute(ID_ATTRIBUTE))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return strongEntityCollection;
    }

    public Element getElementFromItem(StrongEntityCollection strongEntityCollection, Document document) {
        Element strongEntitiesTag = document.createElement(STRONG_ENTITIES_TAG);
        for (IStrongEntity strongEntity : strongEntityCollection) {
            Element strongEntityTag = document.createElement(STRONG_ENTITY_TAG);
            strongEntityTag.setAttribute(NAME_ATTRIBUTE, strongEntity.getName());
            strongEntityTag.setAttribute(ID_ATTRIBUTE, strongEntity.getId().toString());
            strongEntitiesTag.appendChild(strongEntityTag);
        }
        return strongEntitiesTag;
    }


    private static class StrongEntity implements IStrongEntity {
        private String name;
        private UUID id;

        public StrongEntity(String name, UUID id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public UUID getId() {
            return this.id;
        }
    }
}
