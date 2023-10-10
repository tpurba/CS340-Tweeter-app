package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.ServiceObserver;

public abstract class HanlderTask<T> extends Handler implements ServiceObserver  { //should Handler be extended and not implented? Should I also implement Service Observer?
    private T observer;
    public HanlderTask(Looper looper, T observer) {
        super(looper);
        this.observer = observer;//could be redundant so maybe we dont actually touch this???
    }
    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(getSuccessKey());
        if (success) {
            handleSuccess(msg);
        } else if (msg.getData().containsKey(getMessageKey())) {
            createFailureMessage(msg);
        } else if (msg.getData().containsKey(getExceptionKey())) {
            createExceptionMessage(msg);
        }
        doTask();
    }

    protected abstract String getSuccessKey();
    protected abstract String getMessageKey();
    protected abstract String getExceptionKey();
    protected abstract void handleSuccess(Message msg);
    protected abstract void createFailureMessage(Message msg);
    protected abstract void createExceptionMessage(Message msg);
    protected abstract void doTask();
}
