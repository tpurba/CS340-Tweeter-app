package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseService {
    public <T> void execute(T task){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute((Runnable) task);
    }
}
