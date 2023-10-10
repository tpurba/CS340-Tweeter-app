package edu.byu.cs.tweeter.client.model.service.backgroundTask;

public interface ServiceObserver {
    void handleFailure(String message);
    void handleException(Exception exception);
}
