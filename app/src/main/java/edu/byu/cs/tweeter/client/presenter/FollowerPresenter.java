package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerPresenter extends PagedPresenter<User>
{
    private FollowService followerService;
    public FollowerPresenter(View view){
        super(view);
        followerService = new FollowService();
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
        followerService.followerLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, this.lastItem, new FollowerPresenter.FollowerServiceObserver());
    }

    private class FollowerServiceObserver implements FollowService.userPageObserver, ServiceObserver {

        @Override
        public void addMoreUsers(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowerPresenter.this.hasMorePages = hasMorePages;
            lastItem = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            view.addMoreFollowers(followers);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get followers: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get follower because of exception: " + exception.getMessage());//decide to call display message to show the exception
        }
    }
}
