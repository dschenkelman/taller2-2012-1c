package views.mock;

import controllers.IRelationshipEntityController;
import controllers.RelationshipEntityController;
import views.IRelationshipEntityView;

public class MockRelationshipEntityView implements IRelationshipEntityView{

	public boolean visible;
	public RelationshipEntityController controller;
	
	public MockRelationshipEntityView () {
		visible = false;
		controller = null;
	}
	
	@Override
	public IRelationshipEntityController getController() {
		return controller;
	}

	@Override
	public void setController(
			RelationshipEntityController relationshipEntityController) {
		controller = relationshipEntityController;
		
	}

	@Override
	public void show() {
		visible = true;		
	}

	@Override
	public void hide() {
		visible = false;
	}

}
