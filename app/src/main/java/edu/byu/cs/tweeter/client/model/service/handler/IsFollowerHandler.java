package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowerService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends HandlerTask<FollowerService.MainActivityObserver> {
    private FollowerService.MainActivityObserver observer;

    public IsFollowerHandler(FollowerService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }
    @Override
    protected String getSuccessKey() {
        return IsFollowerTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return IsFollowerTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return IsFollowerTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        if (isFollower) {
            observer.isFollower();
        } else {
            observer.notFollower();
        }
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(IsFollowerTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.isFollowerFailed("Failed to determine following relationship: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.isFollowerFailed("Failed to determine following relationship because of exception: " + exception.getMessage());
    }
}
