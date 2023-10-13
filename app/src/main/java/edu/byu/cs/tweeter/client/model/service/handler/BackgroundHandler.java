package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;

public abstract class BackgroundHandler<T extends ServiceObserver> extends Handler   { //should Handler be extended and not implented? Should I also implement Service Observer?
    protected T observer;

    public BackgroundHandler(Looper looper, T observer) {
        super(looper);
        this.observer = observer;//could be redundant so maybe we dont actually touch this???
    }
    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(BackgroundTask.SUCCESS_KEY);
        if (success) {
            handleSuccess(msg);
        } else if (msg.getData().containsKey(BackgroundTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(BackgroundTask.MESSAGE_KEY);
            observer.handleFailure(message);
        } else if (msg.getData().containsKey(BackgroundTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(BackgroundTask.EXCEPTION_KEY);
            observer.handleException(ex);
        }
    }
    protected abstract void handleSuccess(Message msg);
}
