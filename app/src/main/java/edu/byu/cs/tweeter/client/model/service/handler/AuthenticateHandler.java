package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticateHandler<T extends ServiceObserver> extends BackgroundHandler<T> {
    public AuthenticateHandler(Looper looper, T observer) {
        super(looper, observer);
    }

    @Override
    protected void handleSuccess(Message msg) {
        User authenticatedUser = (User) msg.getData().getSerializable(AuthenticateTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);
        //cache user session information
        Cache.getInstance().setCurrUser(authenticatedUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        authenticateObserver(authenticatedUser, authToken);
    }
    protected  abstract void authenticateObserver(User authenticatedUser, AuthToken authToken);
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
