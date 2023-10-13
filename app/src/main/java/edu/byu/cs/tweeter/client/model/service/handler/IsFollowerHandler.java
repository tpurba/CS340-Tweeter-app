package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;

public class IsFollowerHandler extends BackgroundHandler<FollowService.MainActivityFollowerObserver> {

    public IsFollowerHandler(FollowService.MainActivityFollowerObserver observer) {
        super(Looper.getMainLooper(), observer);
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


}
