package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends HandlerTask<FollowService.FollowObserver>{
    private FollowService.FollowObserver observer;

    public GetFollowingHandler(FollowService.FollowObserver observer) {
        super(Looper.getMainLooper(),observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowingTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowingTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowingTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<User> followees = (List<User>) msg.getData().getSerializable(GetFollowingTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);
        observer.addMoreFollowees(followees, hasMorePages);
    }

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

    @Override
    protected void doTask() {

    }

}
