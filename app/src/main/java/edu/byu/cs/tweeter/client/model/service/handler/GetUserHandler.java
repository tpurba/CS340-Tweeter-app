package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends BackgroundHandler<UserService.GetUserObserver> {

    public GetUserHandler(UserService.GetUserObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
        observer.getUserSucceeded(user);
    }

}
