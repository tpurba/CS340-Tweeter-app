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
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(LogoutTask.SUCCESS_KEY);
        if (success) {
            observer.logOutSuccess();
        } else if (msg.getData().containsKey(LogoutTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(LogoutTask.MESSAGE_KEY);
            observer.logOutFailed("Failed to logout: " + message);
        } else if (msg.getData().containsKey(LogoutTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
            observer.logOutFailed("Failed to logout because of exception: " + ex.getMessage());
        }
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
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(LogoutTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.logOutFailed("Failed to logout: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.logOutFailed("Failed to logout because of exception: " + exception.getMessage());
    }
}
