package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PageTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends StatusHandler<StatusService.StoryService.StoryObserver> {

    public GetStoryHandler(StatusService.StoryService.StoryObserver observer) {
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
        observer.addMoreStory(statuses, hasMorePages);
    }




}
