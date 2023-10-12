package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PageTask;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PageFollowHandler<T extends ServiceObserver> extends BackgroundHandler<T> {
    public PageFollowHandler(Looper looper, T observer) {
        super(looper, observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        List<User> userList = (List<User>) msg.getData().getSerializable(PageTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(PageTask.MORE_PAGES_KEY);
        handleAddMoreUser(userList, hasMorePages);
    }
    protected abstract void handleAddMoreUser(List<User> userList, boolean hasMorePages );
    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetFollowingTask.MESSAGE_KEY);
        observer.handleFailure(message);
    }
    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowingTask.EXCEPTION_KEY);
        observer.handleException(ex);
    }
}
