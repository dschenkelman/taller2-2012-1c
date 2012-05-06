package controllers.tests.mocks;

import org.w3c.dom.Document;

import persistence.IXmlFileManager;

public class MockXmlFileManager implements IXmlFileManager {

	private Document documentToSave;
	private String pathToSave;
	private Document document;
	private boolean createDocumentCalled;

	@Override
	public Document read(String filePath) {
		return null;
	}

	@Override
	public void write(Document document, String filePath) {
		this.setDocumentToSave(document);
		this.setPathToSave(filePath);
	}

	public void setDocumentToSave(Document documentToSave) {
		this.documentToSave = documentToSave;
	}

	public Document getDocumentToSave() {
		return documentToSave;
	}

	public void setPathToSave(String pathToSave) {
		this.pathToSave = pathToSave;
	}

	public String getPathToSave() {
		return pathToSave;
	}

	public void setDocumentToCreate(Document document) {
		this.document = document;
	}

	@Override
	public Document createDocument() {
		this.createDocumentCalled = true;
		return this.document;
	}

	public boolean wasCreateDocumentCalled() {
		return this.createDocumentCalled;
	}

}
