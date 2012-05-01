package controllers.tests.mocks;

import controllers.IAttributeController;
import models.Attribute;
import models.AttributeType;
import models.Cardinality;
import models.IKey;
import models.INameable;
import views.IAttributeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockAttributeController implements IAttributeController {
    private IAttributeView attributeView;
    private List<Attribute> attributes;

    public MockAttributeController(){
        this.attributes = new ArrayList<Attribute>();
    }

    @Override
    public void selectKeys() {
    }

    @Override
    public Iterable<INameable> getPossibleAttributes() {
        return null;
    }

    @Override
    public void addAttribute(INameable attribute) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addNewAttribute(String name, boolean isKey, Cardinality cardinality, AttributeType attributeType, String expression) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<Attribute> getAttributesSelected() {
        return this.attributes;
    }

    @Override
    public IAttributeView getAttributeView() {
        return this.attributeView;
    }

    public void setAttributeView(IAttributeView attributeView){
        this.attributeView = attributeView;
    }

    public void setAttributes(List<Attribute> attributes){
        this.attributes = attributes;
    }

	@Override
	public void handleEvent(HashMap<Integer, List<IKey>> param) {
	}
}
