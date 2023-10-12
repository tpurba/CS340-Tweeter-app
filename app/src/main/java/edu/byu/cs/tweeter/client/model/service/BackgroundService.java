package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetFeedHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class BackgroundService <T>{
//    public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, T lastItem, O observer){
//        GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken,
//                user, pageSize, lastItem, new GetFeedHandler(observer));// Create Task
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(getFeedTask);
//    }
//    protected abstract
}
