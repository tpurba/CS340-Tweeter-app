package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends HandlerTask<UserService.LoginObserver> {
    private UserService.LoginObserver observer;

    public LoginHandler(UserService.LoginObserver observer) {
        super(Looper.getMainLooper(),observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return LoginTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return LoginTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return LoginTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);
        //cache user session information
        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.loginSucceeded(authToken, loggedInUser);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(LoginTask.MESSAGE_KEY);
        //observer.loginFailed(message);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
        //observer.loginFailed("Failed to login because of exception: " + exception.getMessage());
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }
}
