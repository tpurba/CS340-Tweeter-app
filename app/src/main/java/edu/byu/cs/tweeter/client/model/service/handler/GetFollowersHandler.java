package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowerService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends HandlerTask<FollowerService.FollowerObserver> {
    private FollowerService.FollowerObserver observer;

    public GetFollowersHandler(FollowerService.FollowerObserver observer) {
        super(Looper.getMainLooper(),observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowersTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowersTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowersTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<User> followers = (List<User>) msg.getData().getSerializable(GetFollowersTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFollowersTask.MORE_PAGES_KEY);
        observer.addMoreFollowers(followers, hasMorePages);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.displayError("Failed to get followers: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.displayException(exception);
    }
}
