package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.FollowerService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;

public class GetFollowersCountHandler extends Handler {
    private FollowerService.MainActivityObserver observer;

    public GetFollowersCountHandler(FollowerService.MainActivityObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(GetFollowersCountTask.SUCCESS_KEY);
        if (success) {
            int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
            observer.getFollowerCountSuccess(count);
        } else if (msg.getData().containsKey(GetFollowersCountTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
            observer.getFollowerCountFailed("Failed to get followers count: " + message);
        } else if (msg.getData().containsKey(GetFollowersCountTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(GetFollowersCountTask.EXCEPTION_KEY);
            observer.getFollowerCountFailed("Failed to get followers count because of exception: " + ex.getMessage());
        }
    }
}
