package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingHandler extends Handler {
    private FollowService.FollowObserver observer;

    public GetFollowingHandler(FollowService.FollowObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetFollowingTask.SUCCESS_KEY);
        if (success) {
            List<User> followees = (List<User>) msg.getData().getSerializable(GetFollowingTask.ITEMS_KEY);
            boolean hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);
            observer.addMoreFollowees(followees, hasMorePages);
        } else if (msg.getData().containsKey(GetFollowingTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetFollowingTask.MESSAGE_KEY);
            observer.displayError("Failed to get following: " + message);


        } else if (msg.getData().containsKey(GetFollowingTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowingTask.EXCEPTION_KEY);
            observer.displayException(ex); //want to keep exceptions in presenter

        }
    }
}
