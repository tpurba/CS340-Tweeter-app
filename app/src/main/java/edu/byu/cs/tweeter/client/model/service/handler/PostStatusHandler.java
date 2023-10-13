package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;

// PostStatusHandler
//GETRIDOF
public class PostStatusHandler extends BackgroundHandler<StatusService.PostObserver> {
    public PostStatusHandler(StatusService.PostObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        observer.postSuccess("Successfully Posted!");
    }
}
