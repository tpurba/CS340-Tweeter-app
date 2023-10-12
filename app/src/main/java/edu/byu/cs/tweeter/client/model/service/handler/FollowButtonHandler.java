package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;

public abstract class FollowButtonHandler<T extends ServiceObserver> extends BackgroundHandler<T> {
    public FollowButtonHandler(Looper looper, T observer) {
        super(looper, observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        handleFollowButton();
        handleEnableButton();
    }
    protected abstract void handleFollowButton( );
    protected abstract void handleEnableButton();
    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(BackgroundTask.MESSAGE_KEY);
        observer.handleFailure(message);
        handleEnableButton();
    }
    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(BackgroundTask.EXCEPTION_KEY);
        observer.handleException(ex);
        handleEnableButton();
    }
}
