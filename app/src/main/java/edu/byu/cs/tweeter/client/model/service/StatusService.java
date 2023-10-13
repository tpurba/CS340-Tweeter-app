package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetPageHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends BaseService{
    public interface PostObserver extends ServiceObserver {
        void postSuccess(String message);
    }
    public void postStatus(AuthToken currAuthToken, Status newStatus, PostObserver observer){
        PostStatusTask statusTask = new PostStatusTask(currAuthToken,
                newStatus, new PostStatusHandler(observer));
        execute(statusTask);
    }
    public void feedLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, FeedPresenter.StatusServiceObserver observer){
        GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetPageHandler(observer));
        execute(getFeedTask);
    }
    public void storyLoadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, StoryPresenter.StatusServiceObserver observer){
        GetStoryTask getStoryTask = new GetStoryTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetPageHandler(observer));
        execute(getStoryTask);
    }

}
