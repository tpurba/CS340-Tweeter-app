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
import edu.byu.cs.tweeter.client.model.service.handler.GetCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowUsersHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UnfollowHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
//fetch data
public class FollowService extends BaseService{
    public interface userPageObserver extends ServiceObserver{//has both follower and followee page loaders
        void addMoreUsers(List<User> followees, boolean hasMorePages);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface CountServiceObserver extends ServiceObserver { //has both followers and followee count
        void getCountSuccess(int count);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface FollowButtonObserver extends ServiceObserver { //has both follow button and unfollow button case
        void buttonPressSuccess(boolean updateButton);
        void setFollowButton(boolean button);
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public interface MainActivityFollowerObserver extends ServiceObserver {
        void isFollower();
        void notFollower();
        void handleFailure(String message);
        void handleException(Exception exception);
    }

    public void followLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, userPageObserver observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetFollowUsersHandler(observer));
        execute(getFollowingTask);;
    }
    public void followerLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, userPageObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new GetFollowUsersHandler(observer));
        execute(getFollowersTask);
    }
    public void getFollowingCount(AuthToken currUserAuthToken, User selectedUser, CountServiceObserver observer){
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(currUserAuthToken,
                selectedUser, new GetCountHandler(observer));
        execute(followingCountTask);
    }
    public void getFollowerCount(AuthToken currUserAuthToken, User selectedUser, CountServiceObserver observer)
    {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken,
                selectedUser, new GetCountHandler(observer));
        execute(followersCountTask);
    }
    public void follow(AuthToken currUserAuthToken, User selectedUser, FollowButtonObserver observer){
        FollowTask followTask = new FollowTask(currUserAuthToken,
                selectedUser, new FollowHandler(observer));
        execute(followTask);
    }
    public void unFollow(AuthToken currUserAuthToken, User selectedUser, FollowButtonObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new UnfollowHandler(observer));
        execute(unfollowTask);
    }
    public void isFollower(AuthToken currUserAuthToken, User currUser, User selectedUser, MainActivityFollowerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        execute(isFollowerTask);
    }

}
