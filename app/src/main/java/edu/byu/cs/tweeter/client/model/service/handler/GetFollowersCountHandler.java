package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;

public class GetFollowersCountHandler extends HandlerTask<FollowService.FollowerService.MainActivityFollowerCountObserver> {
    private FollowService.FollowerService.MainActivityFollowerCountObserver observer;

    public GetFollowersCountHandler(FollowService.FollowerService.MainActivityFollowerCountObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetFollowersCountTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFollowersCountTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFollowersCountTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(CountTask.COUNT_KEY);// Move to super class
        observer.getFollowerCountSuccess(count);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(BackgroundTask.MESSAGE_KEY);//Can always go to lowest point and get the key
        observer.handleFailure(message); //Not Log key since it is redefined in each task
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }
}
