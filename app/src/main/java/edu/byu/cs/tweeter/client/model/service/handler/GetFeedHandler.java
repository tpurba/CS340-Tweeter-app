package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends HandlerTask<StatusService.FeedService.FeedObserver> {
    private StatusService.FeedService.FeedObserver observer;

    public GetFeedHandler(StatusService.FeedService.FeedObserver observer) {
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
    protected void handleSuccess(Message msg) {//Same as GetStory Handler
        List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
        observer.addMoreFeed(statuses, hasMorePages);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
        // observer.displayError("Failed to get feed: " + message);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
        //observer.displayException(exception);
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {
        //none here
    }
}
