package infrastructure;

public interface IControllerFactory<TController, TParam> {
	TController create();
    TController create(TParam param);
}
