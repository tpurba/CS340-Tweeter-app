package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;

public class UnfollowHandler extends HandlerTask<FollowService.MainActivityUnfollowService> {
    private FollowService.MainActivityUnfollowService observer;

    public UnfollowHandler(FollowService.MainActivityUnfollowService observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
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
        observer.setFollowButton(true);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(UnfollowTask.MESSAGE_KEY);
        observer.handleFailure(message);
        observer.setFollowButton(true);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(UnfollowTask.EXCEPTION_KEY);
        observer.handleException(ex);
        observer.setFollowButton(true);
    }

    @Override
    protected void doTask() {

    }


}
