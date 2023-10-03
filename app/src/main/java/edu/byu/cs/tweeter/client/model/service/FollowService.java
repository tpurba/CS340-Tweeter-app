package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UnfollowHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
//fetch data
public class FollowService {
    public interface FollowObserver {

        void displayError(String message);

        void displayException(Exception ex);

        void addMoreFollowees(List<User> followees, boolean hasMorePages);
    }
    public interface MainActivityObserver {

        void getFollowingCountSuccess(int count);
        void getFollowingCountFailed(String message);
        void followSuccess(boolean updateButton);
        void followFailed(String message);
        void setFollowButton(boolean button);
        void unFollowSuccess(boolean updateButton);
        void unFollowFailed(String message);
    }

    //service can communicate to and from multiple presenters
    //no accessing cache from service
    public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, FollowObserver observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }
    public void getFollowingCount(AuthToken currUserAuthToken, User selectedUser, MainActivityObserver observer){
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(currUserAuthToken,
                selectedUser, new GetFollowingCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followingCountTask);
    }
    public void follow(AuthToken currUserAuthToken, User selectedUser, MainActivityObserver observer){
        FollowTask followTask = new FollowTask(currUserAuthToken,
                selectedUser, new FollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }
    public void unFollow(AuthToken currUserAuthToken, User selectedUser, MainActivityObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new UnfollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }
}
