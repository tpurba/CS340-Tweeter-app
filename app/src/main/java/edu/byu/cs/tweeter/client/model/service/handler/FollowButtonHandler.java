package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;

public class FollowButtonHandler extends BackgroundHandler<FollowService.FollowButtonObserver> {
    public FollowButtonHandler(FollowService.FollowButtonObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) { // send notification to observer observer will handle the button
        observer.buttonPressSuccess();
        observer.setFollowButton(true);
    }
}
