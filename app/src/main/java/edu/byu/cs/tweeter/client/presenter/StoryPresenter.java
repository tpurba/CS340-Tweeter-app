package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status, StoryPresenter.View>{
    private StatusService storyService;
    public StoryPresenter(View view){
        super(view);
        storyService = new StatusService();
    }
    @Override
    public Status getLastItem() {
        return lastItem;
    }


    @Override
    public void doService(AuthToken currUserAuthToken, User user, int pageSize, Status lastItem) {
        storyService.storyLoadMoreItems(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, this.lastItem, new StatusServiceObserver());

    }
    public class StatusServiceObserver extends PagedObserver {

        @Override
        public void handleFailure(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get story: " + message);//here we could move everything and instead of having view.displaymessage here have a functions makemessage(message)
            //implment makemessage in here and move both handlefailure and handle exception up.
        }

        @Override
        public void handleException(Exception exception) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get story because of exception: " + exception.getMessage());
        }


    }

}
