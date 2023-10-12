package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends StatusHandler<StatusService.FeedService.FeedObserver> {

    public GetFeedHandler(StatusService.FeedService.FeedObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected String getSuccessKey() {
        return BackgroundTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return BackgroundTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return BackgroundTask.EXCEPTION_KEY;
    }


    @Override
    protected void handleAddMoreStatus(List<Status> statuses, boolean hasMorePages) {
        observer.addMoreFeed(statuses, hasMorePages);
    }
}
