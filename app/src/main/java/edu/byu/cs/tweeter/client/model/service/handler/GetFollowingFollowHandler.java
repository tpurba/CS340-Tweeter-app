package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingFollowHandler extends PageHandler<User, FollowService.userPageObserver> {

    public GetFollowingFollowHandler(FollowService.userPageObserver observer) {
        super(Looper.getMainLooper(),observer);
    }

     protected void callObserver(List<User> list, boolean hasMorePages ){
        observer.addMoreUsers(list, hasMorePages);
    }


}
