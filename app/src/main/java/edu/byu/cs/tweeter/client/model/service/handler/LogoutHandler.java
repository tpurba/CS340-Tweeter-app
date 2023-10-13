package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;

public class LogoutHandler extends BackgroundHandler<UserService.MainActivityObserver> {

    public LogoutHandler(UserService.MainActivityObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        observer.logOutSuccess();
    }

}
