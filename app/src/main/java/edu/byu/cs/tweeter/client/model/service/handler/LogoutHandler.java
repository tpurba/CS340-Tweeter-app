package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class LogoutHandler extends BackgroundHandler<UserService.LogoutObserver> {

    public LogoutHandler(UserService.LogoutObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        observer.logOutSuccess();
    }

}
