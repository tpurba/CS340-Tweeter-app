package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedHandler extends PageHandler<Status, FeedPresenter.FeedServiceObserver> {

    public GetFeedHandler(FeedPresenter.FeedServiceObserver observer) {
        super(Looper.getMainLooper(), observer);
    }

    @Override
    protected void callObserver(List<Status> list, boolean hasMorePages) {
        observer.addMoreStatus(list, hasMorePages);
    }
}
