package controllers.tests.mocks;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import models.Diagram;
import persistence.IXmlManager;

public class MockDiagramXmlManager implements IXmlManager<Diagram> {

	private String rootName;
	private Diagram diagram;

	@Override
	public Element getElementFromItem(Diagram item, Document document) {
		this.diagram = item;
		return document.createElement(this.rootName);
	}

	@Override
	public Diagram getItemFromXmlElement(Element element) throws Exception {
		return null;
	}

	public void setElementNameOfRoot(String value) {
		this.rootName = value;
	}

	public Diagram getDiagramRelatedToElement() {
		return this.diagram;
	}
}
