package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerService {

    public interface FollowerObserver{
        void displayError(String message);

        void displayException(Exception ex);
        void addMoreFollowers(List<User> followers, boolean hasMorePages);
    }
    public interface MainActivityObserver {
        void getFollowerCountSuccess(int count);
        void getFollowerCountFailed(String message);
        void isFollower();
        void notFollower();
        void isFollowerFailed(String message);
    }
    public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, FollowerObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new GetFollowersHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFollowersTask);
    }
    public void getFollowerCount(AuthToken currUserAuthToken, User selectedUser, MainActivityObserver observer)
    {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken,
                selectedUser, new GetFollowersCountHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followersCountTask);
    }
    public void isFollower(AuthToken currUserAuthToken, User currUser, User selectedUser, MainActivityObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

}
