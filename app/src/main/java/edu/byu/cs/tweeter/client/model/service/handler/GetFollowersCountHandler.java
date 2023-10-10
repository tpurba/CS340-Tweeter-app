package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowerService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;

public class GetFollowersCountHandler extends HandlerTask<FollowerService.MainActivityObserver> {
    private FollowerService.MainActivityObserver observer;

    public GetFollowersCountHandler(FollowerService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowersCountTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowersCountTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowersCountTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
        observer.getFollowerCountSuccess(count);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.getFollowerCountFailed("Failed to get followers count: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.getFollowerCountFailed("Failed to get followers count because of exception: " + exception.getMessage());
    }
}
