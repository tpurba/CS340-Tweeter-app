package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
//none user interface logic
public class FollowingPresenter extends PagedPresenter<User>
{
    private FollowService followService;
    public FollowingPresenter(View view){
        super(view);
        followService = new FollowService();
    }
    @Override
    public boolean hasMorePages() {
        return hasMorePages;
    }
    @Override
    public User getLastItem() {
        return lastItem;
    }
    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void doService(AuthToken currUserAuthToken, User user, int pageSize, User lastItem) {
        followService.followLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, this.lastItem, new FollowServiceObserver());
    }

    private class FollowServiceObserver implements FollowService.userPageObserver, ServiceObserver {
        @Override
        public void addMoreUsers(List<User> followees, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowingPresenter.this.hasMorePages = hasMorePages;
            lastItem = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            view.addMoreFollowees(followees);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get following: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get following because of exception: " + exception.getMessage());//decide to call display message to show the exception
        }
    }

}