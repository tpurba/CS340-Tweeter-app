package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class  PagedPresenter<T, O extends PagedPresenter.View> extends BasePresenter<O>{
    protected boolean isLoading;
    protected boolean hasMorePages;
    protected T lastItem;
    protected static final int PAGE_SIZE = 10;

    public PagedPresenter(O view) {
        super(view);
    }


    public interface View<T> extends BasePresenter.View
    {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);
        void addMoreItems(List<T> items);
    }
    public void loadMoreItems(User user){
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            doService(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE, lastItem);
        }
    }
    public abstract void doService(AuthToken currUserAuthToken, User user, int pageSize, T lastItem);
    public abstract boolean isLoading();
    public abstract boolean hasMorePages();
    public abstract T getLastItem();

    public abstract class PagedObserver implements ServiceObserver {
        public void addMoreItems(List<T> items, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            PagedPresenter.this.hasMorePages = hasMorePages;
            lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
            view.addMoreItems(items);
        }

        public abstract void handleFailure(String message);
        public abstract void handleException(Exception exception);
    }
}
