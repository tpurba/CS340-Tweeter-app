package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status, FeedPresenter.View> {
    private StatusService feedService;
    public FeedPresenter(View view){
        super(view);
        feedService = new StatusService();
    }

    @Override
    public Status getLastItem() {
        return lastItem;
    }

    @Override
    public void doService(AuthToken currUserAuthToken, User user, int pageSize, Status lastItem) {
        feedService.feedLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, this.lastItem, new StatusServiceObserver());
    }

    public class StatusServiceObserver extends PagedObserver{
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
