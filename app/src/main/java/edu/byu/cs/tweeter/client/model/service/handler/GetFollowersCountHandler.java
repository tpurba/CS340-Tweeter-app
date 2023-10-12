package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;

public class GetFollowersCountHandler extends CountHandler<FollowService.MainActivityFollowerCountObserver> {

    public GetFollowersCountHandler(FollowService.MainActivityFollowerCountObserver observer) {
        super(Looper.getMainLooper(), observer);
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
    protected void handleObserverCount(int count) {
        observer.getFollowerCountSuccess(count);
    }
}
