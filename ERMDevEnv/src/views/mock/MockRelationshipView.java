package views.mock;

import controllers.IRelationshipController;
import controllers.RelationshipController;
import views.IRelationshipView;

public class MockRelationshipView implements IRelationshipView {

	public boolean visible;
	public IRelationshipController relController;

	public IRelationshipController getController() {
		return relController;
	}

	@Override
	public void setController(IRelationshipController relationshipController) {
		relController = relationshipController;
	}

}
