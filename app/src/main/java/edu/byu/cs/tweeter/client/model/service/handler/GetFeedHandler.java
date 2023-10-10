package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends HandlerTask<FeedService.FeedObserver> {
    private FeedService.FeedObserver observer;

    public GetFeedHandler(FeedService.FeedObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }



    @Override
    protected String getSuccessKey() {
        return GetFeedTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetFeedTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetFeedTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
        observer.addMoreFeed(statuses, hasMorePages);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {
        //none here
    }

    @Override
    public void handleFailure(String message) {
        observer.displayError("Failed to get feed: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.displayException(exception);
    }
}
