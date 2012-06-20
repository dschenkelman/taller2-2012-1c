package views;

import controllers.IAttributeController;
import models.Attribute;
import models.AttributeType;
import models.Cardinality;

import java.util.List;

public interface IAttributeView {

    public void setController(IAttributeController attributeController);

    public void setAttributes(List<Attribute> attributes);

    public Object getInternalFrame();

    public String getName();

    public boolean isKey();

    public Cardinality getCardinality();

    public AttributeType getAttributeType();

    public String getExpression();
}
