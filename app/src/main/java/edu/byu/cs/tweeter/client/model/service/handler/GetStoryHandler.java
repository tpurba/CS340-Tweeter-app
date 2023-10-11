package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends HandlerTask<StatusService.StoryService.StoryObserver>{
    private StatusService.StoryService.StoryObserver observer;

    public GetStoryHandler(StatusService.StoryService.StoryObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetStoryTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetStoryTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetStoryTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
        observer.addMoreStory(statuses, hasMorePages);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }

}
