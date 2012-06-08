package views;

import controllers.IRelationshipController;

public interface IRelationshipView {

	IRelationshipController getController();

	void setController(IRelationshipController relationshipController);
	
}
