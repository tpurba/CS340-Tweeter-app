package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends HandlerTask<FollowService.FollowerService.MainActivityFollowerObserver> {
    private FollowService.FollowerService.MainActivityFollowerObserver observer;

    public IsFollowerHandler(FollowService.FollowerService.MainActivityFollowerObserver observer) {
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
        //observer.isFollowerFailed("Failed to determine following relationship: " + message);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(IsFollowerTask.EXCEPTION_KEY);
        //observer.isFollowerFailed("Failed to determine following relationship because of exception: " + exception.getMessage());
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }
}
