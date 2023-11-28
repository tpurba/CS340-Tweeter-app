package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.handler.FollowButtonHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetPageHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.client.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
//fetch data
public class FollowService extends BaseService{
    public interface CountServiceObserver extends ServiceObserver { //has both followers and followee count
        void getCountSuccess(int count);
    }
    public interface FollowButtonObserver extends ServiceObserver { //has both follow button and unfollow button case
        void buttonPressSuccess();
        void setFollowButton(boolean button);
    }

    public interface MainActivityFollowerObserver extends ServiceObserver {
        void isFollower();
        void notFollower();
    }

    public void followLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, FollowingPresenter.PageUserServiceObserver observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new GetPageHandler(observer));
        execute(getFollowingTask);
    }
    public void followerLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, FollowerPresenter.PageUserServiceObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new GetPageHandler(observer));
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
                selectedUser, new FollowButtonHandler(observer));
        execute(followTask);
    }
    public void unFollow(AuthToken currUserAuthToken, User selectedUser, FollowButtonObserver observer){
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new FollowButtonHandler(observer));
        execute(unfollowTask);
    }
    public void isFollower(AuthToken currUserAuthToken, User currUser, User selectedUser, MainActivityFollowerObserver observer){
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
                currUser, selectedUser, new IsFollowerHandler(observer));
        execute(isFollowerTask);
    }

}
