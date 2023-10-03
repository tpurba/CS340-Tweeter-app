package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
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
    private FeedService feedService;

    public FeedPresenter(View view){
        this.view = view;
        feedService = new FeedService();
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
            feedService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new FeedServiceObserver());
        }
    }
    private class FeedServiceObserver implements  FeedService.FeedObserver{
        @Override
        public void displayError(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());//decide to call display message to show the exception

        }
        @Override
        public void addMoreFeed(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addMoreFeed(statuses);
        }


    }

}
