package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;

public class UnfollowHandler extends BackgroundHandler<FollowService.FollowButtonObserver> {

    public UnfollowHandler(FollowService.FollowButtonObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        observer.buttonPressSuccess(true);
        observer.setFollowButton(true);
    }
}
