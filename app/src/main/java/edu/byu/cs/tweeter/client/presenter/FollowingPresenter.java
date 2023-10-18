package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
//none user interface logic
public class FollowingPresenter extends PagedPresenter<User, FollowingPresenter.View>
{
    private FollowService followService;
    public FollowingPresenter(View view){
        super(view);
        followService = new FollowService();
    }
    @Override
    public User getLastItem() {
        return lastItem;
    }
    @Override
    public void doService(AuthToken currUserAuthToken, User user, int pageSize, User lastItem) {
        followService.followLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, this.lastItem, new PageUserServiceObserver());
    }

    public class PageUserServiceObserver extends PagedObserver {
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