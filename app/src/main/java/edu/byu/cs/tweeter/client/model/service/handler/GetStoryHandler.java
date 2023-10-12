package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PageTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends PageHandler<Status, StatusService.StoryObserver> {

    public GetStoryHandler(StatusService.StoryObserver observer) {
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
    protected void callObserver(List<Status> list, boolean hasMorePages) {
        observer.addMoreStory(list, hasMorePages);
    }
}
