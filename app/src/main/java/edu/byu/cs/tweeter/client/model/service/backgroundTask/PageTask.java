package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PageTask<T> extends AuthenticatedTask{
    private static final String LOG_TAG = "GetFollowersTask";
    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";
    /**
     * The user whose data is being retrieved
     */
    private User targetUser;
    /**
     * Maximum number of followers to return (i.e., page size).
     */
    private int limit;
    /**
     * The last follower returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    private T lastItem;
    private List<T> items;
    private boolean hasMorePages;
    public PageTask(Handler messageHandler, AuthToken authToken, User targetUser, int limit, T lastItem) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }
    public User getTargetUser() {
        return targetUser;
    }

    public int getLimit() {
        return limit;
    }

    public T getLastItem() {
        return lastItem;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }


    @Override
    protected void doTask() {
        Pair<List<T>, Boolean> pageOfStatus = getItems();
        setItems(pageOfStatus.getFirst());
        hasMorePages = pageOfStatus.getSecond();
    }
    protected abstract Pair<List<T>, Boolean> getItems();
    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }
}
