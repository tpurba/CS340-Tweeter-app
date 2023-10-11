package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;

public class FollowHandler extends HandlerTask<FollowService.MainActivityObserver> {
    public FollowHandler(FollowService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected String getSuccessKey() {
        return FollowTask.SUCCESS_KEY;
    }
    @Override
    protected String getMessageKey() {
        return FollowTask.MESSAGE_KEY;
    }
    @Override
    protected String getExceptionKey() {
        return FollowTask.EXCEPTION_KEY;
    }
    @Override
    protected void handleSuccess(Message msg) {
        observer.followSuccess(false);//handle success
        observer.setFollowButton(true);
    }
    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(FollowTask.MESSAGE_KEY);
        observer.handleFailure(message);
        observer.setFollowButton(true);
    }
    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
        observer.handleException(ex);
        observer.setFollowButton(true);
    }
    @Override
    protected void doTask() {

    }
}
