package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {
    private static final int PAGE_SIZE = 10;
    private Status lastStatus;
    private boolean isLoading;
    private boolean hasMorePages;

    public interface View
    {
        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreFeed(List<Status> statuses);
    }
    private View view;
    private StatusService feedService;

    public FeedPresenter(View view){
        this.view = view;
        feedService = new StatusService();
    }


    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }
    public void loadMoreItems(User user){
        if (!isLoading) {
            isLoading = true;
            view.setLoadingFooter(true);
            feedService.feedLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new FeedServiceObserver());
        }
    }
    private class FeedServiceObserver implements  StatusService.FeedObserver, ServiceObserver {
        @Override
        public void addMoreFeed(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addMoreFeed(statuses);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed because of exception: " + exception.getMessage());//decide to call display message to show the exception
        }
    }

}
