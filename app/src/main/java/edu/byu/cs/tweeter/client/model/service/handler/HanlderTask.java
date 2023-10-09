package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;

public abstract class HanlderTask<T> extends Handler {
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
            handleFailure(msg);
        } else if (msg.getData().containsKey(getExceptionKey())) {
            handleException(msg);
        }
        doTask();
    }

    protected abstract String getSuccessKey();
    protected abstract String getMessageKey();
    protected abstract String getExceptionKey();
    protected abstract void handleSuccess(Message msg);
    protected abstract void handleFailure(Message msg);
    protected abstract void handleException(Message msg);
    protected abstract void doTask();
}
