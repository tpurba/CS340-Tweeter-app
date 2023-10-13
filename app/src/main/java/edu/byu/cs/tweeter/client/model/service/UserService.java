package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handler.RegisterHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {
    public interface authenticateObserver extends ServiceObserver{ //has both login and register
        void authenticateSucceeded(AuthToken authToken, User user);
        void handleFailure(String message);
        void handleException(Exception exception);

    }
    public interface GetUserObserver extends ServiceObserver {
        void getUserSucceeded(User user);
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public interface MainActivityObserver  extends ServiceObserver{
        void logOutSuccess();
        void handleFailure(String message);
        void handleException(Exception exception);
    }
    public void login(String alias, String password, authenticateObserver observer){
        // Send the login request.
        LoginTask loginTask = new LoginTask(alias, password, new RegisterHandler(observer));
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

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, authenticateObserver
    observer){
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(observer));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(registerTask);
    }

}
