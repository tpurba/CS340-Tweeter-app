package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryHandler extends PageHandler<Status, StoryPresenter.StoryServiceObserver> {

    public GetStoryHandler(StoryPresenter.StoryServiceObserver observer) {
        super(Looper.getMainLooper(), observer);
    }
    @Override
    protected void callObserver(List<Status> list, boolean hasMorePages) {
        observer.addMoreStatus(list, hasMorePages);
    }
}
