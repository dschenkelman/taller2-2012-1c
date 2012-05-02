package controllers;

public interface IRelationshipController {
	void create();
	
	void addCreateListener(IRelationshipEventListener listener);
}
