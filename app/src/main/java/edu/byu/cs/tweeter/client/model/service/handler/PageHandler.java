package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PageTask;

public abstract class PageHandler<O, T extends ServiceObserver>  extends BackgroundHandler<T>  {
    public PageHandler(Looper looper, T observer) {
        super(looper, observer);
    }
    @Override
    protected void handleSuccess(Message msg) {
        List<O> list = (List<O>) msg.getData().getSerializable(PageTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(PageTask.MORE_PAGES_KEY);
        callObserver(list, hasMorePages);
    }
    protected abstract void callObserver(List<O> list, boolean hasMorePages);

}
