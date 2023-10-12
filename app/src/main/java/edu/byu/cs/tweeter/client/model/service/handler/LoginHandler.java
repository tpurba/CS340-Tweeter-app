package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends AuthenticateHandler<UserService.LoginObserver> {

    public LoginHandler(UserService.LoginObserver observer) {
        super(Looper.getMainLooper(),observer);
    }

    @Override
    protected String getSuccessKey() {
        return BackgroundTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return BackgroundTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return BackgroundTask.EXCEPTION_KEY;
    }


    @Override
    protected void authenticateObserver(User authenticatedUser, AuthToken authToken) {
        observer.loginSucceeded(authToken, authenticatedUser);
    }
}
