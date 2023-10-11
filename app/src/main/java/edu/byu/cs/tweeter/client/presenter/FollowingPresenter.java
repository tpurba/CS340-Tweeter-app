package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.User;
//none user interface logic
public class FollowingPresenter
{
    //one to one relation between ui and presenter
    private static final int PAGE_SIZE = 10;
    private User lastFollowee;

    private boolean isLoading;
    private boolean hasMorePages;

    //view obsevers the presenter
    public interface View
    {
        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreFollowees(List<User> followees);
    }
    private View view;
    private FollowService followService;
    public FollowingPresenter(View view){
        this.view = view;
        followService = new FollowService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            followService.loadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastFollowee, new FollowServiceObserver());
        }

    }
    private class FollowServiceObserver implements FollowService.FollowObserver, ServiceObserver {
        @Override
        public void addMoreFollowees(List<User> followees, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FollowingPresenter.this.hasMorePages = hasMorePages;
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            view.addMoreFollowees(followees);
        }

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get following because of exception: " + exception.getMessage());//decide to call display message to show the exception
        }
    }

}
