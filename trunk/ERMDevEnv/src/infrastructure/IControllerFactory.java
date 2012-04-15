package infrastructure;

public interface IControllerFactory<TController, TParam> {
	TController create();
}
