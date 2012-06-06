package views;

import controllers.IRelationshipEntityController;
import controllers.RelationshipEntityController;

public interface IRelationshipEntityView {

	IRelationshipEntityController getController();

	void setController(RelationshipEntityController relationshipEntityController);

	void show();

	void hide();

}
