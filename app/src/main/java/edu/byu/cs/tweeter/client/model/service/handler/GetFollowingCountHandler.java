package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;

public class GetFollowingCountHandler extends CountHandler<FollowService.MainActivityCountServiceObserver> {
    public GetFollowingCountHandler(FollowService.MainActivityCountServiceObserver observer) {
        super(Looper.getMainLooper(),observer);
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowingCountTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowingCountTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowingCountTask.EXCEPTION_KEY;
    }


    @Override
    protected void handleObserverCount(int count) {
        observer.getFollowingCountSuccess(count);
    }
}
