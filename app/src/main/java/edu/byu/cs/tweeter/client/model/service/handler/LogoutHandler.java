package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;

public class LogoutHandler extends HandlerTask<UserService.MainActivityObserver> {
    private UserService.MainActivityObserver observer;

    public LogoutHandler(UserService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return LogoutTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return LogoutTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return LogoutTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        observer.logOutSuccess();
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }
}
