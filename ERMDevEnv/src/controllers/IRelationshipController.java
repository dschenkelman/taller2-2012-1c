package controllers;

public interface IRelationshipController {
	
	void create();
	void addCreateListener(IRelationshipEventListener listener);
	
	void setName(String name);
	String getName();
	
	boolean isComposition () ;
	void add();
	
	void setRealationshipView ();
	
	
}
