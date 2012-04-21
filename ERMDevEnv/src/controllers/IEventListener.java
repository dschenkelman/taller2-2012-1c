package controllers;

public interface IEventListener<TParam> {

    public void handleEvent(TParam... params);
}
