package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersFollowHandler extends PageFollowHandler<FollowService.FollowerObserver> {

    public GetFollowersFollowHandler(FollowService.FollowerObserver observer) {
        super(Looper.getMainLooper(),observer);
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowersTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowersTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowersTask.EXCEPTION_KEY;
    }


    @Override
    protected void handleAddMoreUser(List<User> userList, boolean hasMorePages) {
        observer.addMoreFollowers(userList, hasMorePages);
    }

}
