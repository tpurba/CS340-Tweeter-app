package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;

public abstract class CountHandler<T extends ServiceObserver> extends BackgroundHandler<T>  {
    public CountHandler(Looper looper, T observer) {
        super(looper, observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(CountTask.COUNT_KEY);
        handleObserverCount(count);
    }
    protected abstract void handleObserverCount(int count );

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
