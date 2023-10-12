package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingFollowHandler extends PageHandler<User, FollowService.FollowObserver> {

    public GetFollowingFollowHandler(FollowService.FollowObserver observer) {
        super(Looper.getMainLooper(),observer);
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowingTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowingTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowingTask.EXCEPTION_KEY;
    }
    protected void callObserver(List<User> list, boolean hasMorePages ){
        observer.addMoreFollowees(list, hasMorePages);
    }


}
