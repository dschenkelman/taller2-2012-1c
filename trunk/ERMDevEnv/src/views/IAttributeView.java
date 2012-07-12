package views;

import controllers.IAttributeController;
import models.Attribute;
import models.AttributeType;
import models.Cardinality;

import java.util.List;

import javax.swing.JPanel;

public interface IAttributeView {

    public void setController(IAttributeController attributeController);

    public void setAttributes(List<Attribute> attributes);

    public Object getInternalFrame();

    public String getName();

    public Cardinality getCardinality();

    public AttributeType getAttributeType();

    public String getExpression();

	public JPanel getFrame();
}
