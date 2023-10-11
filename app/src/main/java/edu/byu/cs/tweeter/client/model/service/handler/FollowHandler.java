package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;

public class FollowHandler extends HandlerTask<FollowService.MainActivityObserver> {
    FollowService.MainActivityObserver observer;
    public FollowHandler(FollowService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
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
    }
    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(FollowTask.MESSAGE_KEY);//handle error
        observer.handleFailure(message);//send the failure to be handled
    }
    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(FollowTask.EXCEPTION_KEY);
        observer.handleException(ex);//send the exception to be handled
    }
    @Override
    protected void doTask() {
        observer.setFollowButton(true);
    }
}
