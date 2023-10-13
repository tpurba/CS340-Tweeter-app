package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;

public class GetCountHandler extends BackgroundHandler<FollowService.CountServiceObserver> {

    public GetCountHandler(FollowService.CountServiceObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(CountTask.COUNT_KEY);
        observer.getCountSuccess(count);
    }

}
