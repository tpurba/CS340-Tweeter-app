package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends HandlerTask<RegisterService.RegisterObserver> {
    private RegisterService.RegisterObserver observer;

    public RegisterHandler(RegisterService.RegisterObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return RegisterTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return RegisterTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return RegisterTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);
        //cache data
        Cache.getInstance().setCurrUser(registeredUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.registerSucceeded(authToken, registeredUser);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(RegisterTask.MESSAGE_KEY);
        handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(RegisterTask.EXCEPTION_KEY);
        handleException(ex);
    }

    @Override
    protected void doTask() {

    }

    @Override
    public void handleFailure(String message) {
        observer.registerFailed("Failed to register: " + message);
    }

    @Override
    public void handleException(Exception exception) {
        observer.registerFailed("Failed to register because of exception: " + exception.getMessage());
    }
}
