package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract  class CountTask extends BackgroundTask {
    public static final String COUNT_KEY = "count";
    private AuthToken authToken;
    private User targetUser;
    private int count;
    public CountTask(Handler messageHandler, AuthToken authToken, User targetUser) {
        super(messageHandler);
        this.authToken = authToken;
        this.targetUser = targetUser;
    }
    @Override
    protected void doTask() {
        count = runCountTask();
    }
    protected abstract int runCountTask();
    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, count);
    }
}
