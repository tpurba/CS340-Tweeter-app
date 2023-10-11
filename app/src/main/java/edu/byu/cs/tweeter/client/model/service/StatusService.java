package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.handler.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PostStatusHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {
    public interface MainActivityObserver extends ServiceObserver {
        void postSuccess(String message);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public void postStatus(AuthToken currAuthToken, Status newStatus, MainActivityObserver observer){
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    public static class FeedService {
        public interface FeedObserver extends ServiceObserver {
            void addMoreFeed(List<Status> statuses, boolean hasMorePages);

            void handleFailure(String message);

            void handleException(Exception exception);
        }
        public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, FeedObserver observer){
            GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken,
                    user, pageSize, lastStatus, new GetFeedHandler(observer));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(getFeedTask);
        }
    }

    public static class StoryService {
        public interface StoryObserver extends ServiceObserver{
            void addMoreStory(List<Status> statuses, boolean hasMorePages);

            void handleFailure(String message);

            void handleException(Exception exception);
        }

        public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, StoryObserver observer){
            GetStoryTask getStoryTask = new GetStoryTask(currUserAuthToken,
                    user, pageSize, lastStatus, new GetStoryHandler(observer));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(getStoryTask);
        }
    }
}
