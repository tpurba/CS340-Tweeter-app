package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;

public class UnfollowHandler extends HandlerTask<FollowService.MainActivityObserver> {
    private FollowService.MainActivityObserver observer;

    public UnfollowHandler(FollowService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(UnfollowTask.SUCCESS_KEY);
        if (success) {
            observer.unFollowSuccess(true);
        } else if (msg.getData().containsKey(UnfollowTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
            observer.unFollowFailed("Failed to unfollow: " + message);
        } else if (msg.getData().containsKey(UnfollowTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
            observer.unFollowFailed("Failed to unfollow because of exception: " + ex.getMessage());
        }

        observer.setFollowButton(true);
    }

    @Override
    protected String getSuccessKey() {
        return UnfollowTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return UnfollowTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return UnfollowTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.unFollowSuccess(true);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.unFollowFailed("Failed to unfollow: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.unFollowFailed("Failed to unfollow because of exception: " + exception.getMessage());
    }
}
