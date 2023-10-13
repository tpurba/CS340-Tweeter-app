package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status>{
    private StatusService storyService;
    public StoryPresenter(View view){
        super(view);
        storyService = new StatusService();
    }
    @Override
    public boolean hasMorePages() {
        return hasMorePages;
    }

    @Override
    public Status getLastItem() {
        return null;
    }
    @Override
    public boolean isLoading() {
        return isLoading;
    }


    @Override
    public void doService(AuthToken currUserAuthToken, User user, int pageSize, Status lastItem) {
        storyService.storyLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, this.lastItem, new StoryServiceObserver());

    }
    public class StoryServiceObserver implements ServiceObserver {
        public void addMoreStatus(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            StoryPresenter.this.hasMorePages = hasMorePages;
            lastItem = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addMoreStory(statuses);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get story: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get story because of exception: " + exception.getMessage());
        }
    }
//    private class StoryServiceObserver implements StatusService.statusPageObserver, ServiceObserver {
//        @Override
//        public void addMoreStatus(List<Status> statuses, boolean hasMorePages) {
//            isLoading = false;
//            view.setLoadingFooter(false);
//            StoryPresenter.this.hasMorePages = hasMorePages;
//            lastItem = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
//            view.addMoreStory(statuses);
//        }
//
//        @Override
//        public void handleFailure(String message) {
//            isLoading = false;
//            view.setLoadingFooter(false);
//            view.displayMessage("Failed to get story: " + message);
//        }
//
//        @Override
//        public void handleException(Exception exception) {
//            isLoading = false;
//            view.setLoadingFooter(false);
//            view.displayMessage("Failed to get story because of exception: " + exception.getMessage());
//        }
//    }
}
