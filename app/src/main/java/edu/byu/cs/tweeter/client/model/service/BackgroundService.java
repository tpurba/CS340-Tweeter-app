package edu.byu.cs.tweeter.client.model.service;


public abstract class BackgroundService <T, O>{
//    public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, T lastItem, O observer, String taskType){
//        PageTask pageTask;
//        if("follow".equals(taskType)){
//            pageTask=  new GetFollowingTask(currUserAuthToken,
//                    user, pageSize, lastItem, new GetFollowingFollowHandler(observer));;
//
//        }
//        if("follower".equals(taskType)){
//            pageTask = new GetFollowersTask(currUserAuthToken,
//                    user, pageSize, lastItem, new GetFollowersFollowHandler(observer));
//
//        }
//        if("feed".equals(taskType)){
//            pageTask = new GetFeedTask(currUserAuthToken,
//                    user, pageSize, lastItem, new GetFeedHandler(observer));// Create Task
//        }
//        if("story".equals(taskType)){
//            pageTask = new GetStoryTask(currUserAuthToken,
//                    user, pageSize, lastItem, new GetStoryHandler(observer));
//        }
//        else{
//            throw new IllegalArgumentException("Invalid task type: " + taskType);
//        }
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(pageTask);
//    }
//    protected abstract
}
