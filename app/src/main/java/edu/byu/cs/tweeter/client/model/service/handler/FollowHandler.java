package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;

public class FollowHandler extends BackgroundHandler<FollowService.FollowButtonObserver> {
    public FollowHandler(FollowService.FollowButtonObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        observer.buttonPressSuccess(false);
        observer.setFollowButton(true);
    }
}
