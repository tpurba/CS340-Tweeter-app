package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.handler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.handler.LogoutHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {
    public interface LoginObserver{
        void loginSucceeded(AuthToken authToken, User user);
        void loginFailed(String message);

    }
    public interface GetUserObserver{
        void getUserSucceeded(User user);
        void getUserFailed(String message);
    }
    public interface MainActivityObserver {
        void logOutSuccess();
        void logOutFailed(String message);
    }

    public void login(String alias, String password, LoginObserver observer){
        // Send the login request.
        LoginTask loginTask = new LoginTask(alias, password, new LoginHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(loginTask);
    }
    public void getUser(String alias,AuthToken currUserAuthToken, GetUserObserver observer){
        //grab handler first into the user service will go to user service
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                alias, new GetUserHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }
    public void logOut(AuthToken currUserAuthToken, MainActivityObserver observer){
        LogoutTask logoutTask = new LogoutTask(currUserAuthToken, new LogoutHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(logoutTask);
    }






}
