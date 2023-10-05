package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthenticatedTask extends BackgroundTask{
    /**
     * Auth token for logged-in user.
     * This user is the "follower" in the relationship.
     */
    private AuthToken authToken; //needs to be done so that when we dont want to authenticate we dont have to edit everything

    public AuthenticatedTask(Handler messageHandler, AuthToken authToken) {
        super(messageHandler);
        this.authToken = authToken;
    }
}
//authenticateTask between Login and reigster
//Count Task
//Page Task
