package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersHandler extends HandlerTask<FollowService.FollowerService.FollowerObserver> {
    private FollowService.FollowerService.FollowerObserver observer;

    public GetFollowersHandler(FollowService.FollowerService.FollowerObserver observer) {
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
        //observer.displayError("Failed to get followers: " + message);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowersTask.EXCEPTION_KEY);
        //observer.displayException(exception);
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }
}
