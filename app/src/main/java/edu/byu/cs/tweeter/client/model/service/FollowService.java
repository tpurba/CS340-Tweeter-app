package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersFollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowingFollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UnfollowHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
//fetch data
public class FollowService {
    public interface FollowObserver extends ServiceObserver{
        void addMoreFollowees(List<User> followees, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface MainActivityObserver extends ServiceObserver {

        void followSuccess(boolean updateButton);
        void setFollowButton(boolean button);
        void handleFailure(String message);
        void handleException(Exception exception);


    }
    public interface MainActivityCountServiceObserver extends ServiceObserver {
        void getFollowingCountSuccess(int count);
        void handleFailure(String message);
        void handleException(Exception exception);

    }
    public interface MainActivityUnfollowService extends ServiceObserver
    {
        void unFollowSuccess(boolean updateButton);
        void setFollowButton(boolean button);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface FollowerObserver extends ServiceObserver{
        void addMoreFollowers(List<User> followers, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface MainActivityFollowerObserver extends ServiceObserver {
        void isFollower();
        void notFollower();
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface MainActivityFollowerCountObserver extends ServiceObserver{
        void getFollowerCountSuccess(int count);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public void followLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, FollowObserver observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowingFollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowingTask);
    }
    public void getFollowingCount(AuthToken currUserAuthToken, User selectedUser, MainActivityCountServiceObserver observer){
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
    public void unFollow(AuthToken currUserAuthToken, User selectedUser, MainActivityUnfollowService observer){
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new UnfollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(unfollowTask);
    }
    public void followerLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, FollowerObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new GetFollowersFollowHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }
    public void getFollowerCount(AuthToken currUserAuthToken, User selectedUser, MainActivityFollowerCountObserver observer)
    {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken,
                selectedUser, new GetFollowersCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followersCountTask);
    }
    public void isFollower(AuthToken currUserAuthToken, User currUser, User selectedUser, MainActivityFollowerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

}
