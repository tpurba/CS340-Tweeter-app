package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowerService;
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
    private FollowerService followerService;
    public FollowerPresenter(View view){
        this.view = view;
        followerService = new FollowerService();
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
    private class FollowerServiceObserver implements FollowerService.FollowerObserver{

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
            view.displayMessage("Failed to get follower because of exception: " + ex.getMessage());//decide to call display message to show the exception
        }

        @Override
        public void addMoreFollowers(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowerPresenter.this.hasMorePages = hasMorePages;
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            view.addMoreFollowers(followers);
        }
    }
}
