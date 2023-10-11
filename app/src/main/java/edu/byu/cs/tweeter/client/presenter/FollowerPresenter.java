package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowerPresenter
{
    private static final int PAGE_SIZE = 10;
    private boolean isLoading;
    private User lastFollower;
    private boolean hasMorePages;
    public interface View{
        void setLoadingFooter(boolean value);
        void displayMessage(String message);
        void addMoreFollowers(List<User> followers);
    }
    private View view;
    private FollowService.FollowerService followerService;
    public FollowerPresenter(View view){
        this.view = view;
        followerService = new FollowService.FollowerService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems(User user)
    {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            followerService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollower, new FollowerPresenter.FollowerServiceObserver());

        }
    }
    private class FollowerServiceObserver implements FollowService.FollowerService.FollowerObserver, ServiceObserver {

        @Override
        public void addMoreFollowers(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowerPresenter.this.hasMorePages = hasMorePages;
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
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
