package views;

import java.util.List;

import controllers.IRelationshipEntityController;
import controllers.RelationshipEntityController;

public interface IRelationshipEntityView {

	IRelationshipEntityController getController();

	void setController(IRelationshipEntityController relationshipEntityController);

	void show();

	void hide();

	List<Object[]> getModelList();

}
