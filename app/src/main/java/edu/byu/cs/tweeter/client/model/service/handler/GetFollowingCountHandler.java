package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;

public class GetFollowingCountHandler extends HandlerTask<FollowService.MainActivityObserver> {
    private FollowService.MainActivityObserver observer;

    public GetFollowingCountHandler(FollowService.MainActivityObserver observer) {
        super(Looper.getMainLooper(),observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowingCountTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowingCountTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowingCountTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
        observer.getFollowingCountSuccess(count);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetFollowingCountTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowingCountTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.getFollowingCountFailed("Failed to get following count: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.getFollowingCountFailed("Failed to get following count because of exception: " + exception.getMessage());
    }
}
