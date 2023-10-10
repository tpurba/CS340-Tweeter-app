package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;

// PostStatusHandler
//GETRIDOF
public class PostStatusHandler extends HandlerTask<StatusService.MainActivityObserver> {
    private StatusService.MainActivityObserver observer;

    public PostStatusHandler(StatusService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return PostStatusTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return PostStatusTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return PostStatusTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.postSuccess("Successfully Posted!");
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.postFailed("Failed to post status: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.postFailed("Failed to post status because of exception: " + exception.getMessage());
    }
}
