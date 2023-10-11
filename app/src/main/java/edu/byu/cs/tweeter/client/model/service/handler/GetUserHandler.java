package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends HandlerTask<UserService.GetUserObserver> {
    private UserService.GetUserObserver observer;

    public GetUserHandler(UserService.GetUserObserver observer) {
        super(Looper.getMainLooper(), observer);
        this.observer = observer;
    }

    @Override
    protected String getSuccessKey() {
        return GetUserTask.SUCCESS_KEY;
    }

    @Override
    protected String getMessageKey() {
        return GetUserTask.MESSAGE_KEY;
    }

    @Override
    protected String getExceptionKey() {
        return GetUserTask.EXCEPTION_KEY;
    }

    @Override
    protected void handleSuccess(Message msg) {
        User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
        observer.getUserSucceeded(user);
    }

    @Override
    protected void createFailureMessage(Message msg) {
        String message = msg.getData().getString(GetUserTask.MESSAGE_KEY);
        observer.handleFailure(message);
    }

    @Override
    protected void createExceptionMessage(Message msg) {
        Exception ex = (Exception) msg.getData().getSerializable(GetUserTask.EXCEPTION_KEY);
        observer.handleException(ex);
    }

    @Override
    protected void doTask() {

    }
}
