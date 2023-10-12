package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;

public abstract class BackgroundHandler<T extends ServiceObserver> extends Handler   { //should Handler be extended and not implented? Should I also implement Service Observer?
    protected T observer;
    public BackgroundHandler(Looper looper, T observer) {
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
    }

    protected abstract String getSuccessKey();
    protected abstract String getMessageKey();
    protected abstract String getExceptionKey();
    protected abstract void handleSuccess(Message msg);
    protected abstract void createFailureMessage(Message msg);
    /*
    Since code is like this for all can we make it in the handler task? Same as the CreateExceptionMessage
    String message = msg.getData().getString(GetFollowersTask.MESSAGE_KEY);
        handleFailure(message);
     */
    protected abstract void createExceptionMessage(Message msg);
}
