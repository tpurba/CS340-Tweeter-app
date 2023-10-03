package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter {
    private static final int PAGE_SIZE = 10;
    private Status lastStatus;
    private boolean isLoading;
    private boolean hasMorePages;
    public interface View
    {
        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreStory(List<Status> statuses);

    }
    private View view;
    private StoryService storyService;
    public StoryPresenter(View view){
        this.view = view;
        storyService = new StoryService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems(User user){
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            storyService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastStatus, new StoryServiceObserver() );
        }
    }
    private class StoryServiceObserver implements StoryService.StoryObserver{
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
            view.displayMessage("Failed to get story because of exception: " + ex.getMessage());//decide to call display message to show the exception

        }
        @Override
        public void addMoreStory(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            StoryPresenter.this.hasMorePages = hasMorePages;
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addMoreStory(statuses);
        }

    }
}
