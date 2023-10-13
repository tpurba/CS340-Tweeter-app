package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends BackgroundHandler<UserService.authenticateObserver> {

    public RegisterHandler(UserService.authenticateObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        User authenticatedUser = (User) msg.getData().getSerializable(AuthenticateTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);
        //cache user session information
        Cache.getInstance().setCurrUser(authenticatedUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.authenticateSucceeded(authToken, authenticatedUser);
    }
}
