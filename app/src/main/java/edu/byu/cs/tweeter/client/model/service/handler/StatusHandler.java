package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PageTask;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class StatusHandler<T extends ServiceObserver> extends BackgroundHandler<T>{
    public StatusHandler(Looper looper, T observer) {
        super(looper, observer);
    }

    @Override
    protected void handleSuccess(Message msg) {//Same as GetStory Handler
        List<Status> statuses = (List<Status>) msg.getData().getSerializable(PageTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(PageTask.MORE_PAGES_KEY);
        handleAddMoreStatus(statuses,hasMorePages);
    }

    protected abstract void handleAddMoreStatus(List<Status> statuses, boolean hasMorePages );
    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(BackgroundTask.MESSAGE_KEY);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(BackgroundTask.EXCEPTION_KEY);
        observer.handleException(ex);
    }
}
