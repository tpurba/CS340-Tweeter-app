package edu.byu.cs.tweeter.client.presenter;

public class BasePresenter <T>{
    protected T view;
    public BasePresenter(T view){
        this.view = view;
    }
    public interface View {
        // Define methods that all views should implement
    }
}
