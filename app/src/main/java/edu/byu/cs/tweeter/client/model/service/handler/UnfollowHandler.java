package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;

public class UnfollowHandler extends FollowButtonHandler<FollowService.MainActivityUnfollowService> {

    public UnfollowHandler(FollowService.MainActivityUnfollowService observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected String getSuccessKey() {
        return BackgroundTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return BackgroundTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return BackgroundTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleFollowButton() {
        observer.unFollowSuccess(true);
    }

    @Override
    protected void handleEnableButton() {
        observer.setFollowButton(true);
    }

}
