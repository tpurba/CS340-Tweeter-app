package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PageTask;
import edu.byu.cs.tweeter.client.presenter.PagedPresenter;
import edu.byu.cs.tweeter.model.domain.User;

public class GetPageHandler<T> extends BackgroundHandler<PagedPresenter.PagedObserver> {

    public GetPageHandler(PagedPresenter.PagedObserver observer) {
        super(Looper.getMainLooper(),observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        List<T> list = (List<T>) msg.getData().getSerializable(PageTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(PageTask.MORE_PAGES_KEY);
        observer.addMoreItems(list, hasMorePages);
    }
}

