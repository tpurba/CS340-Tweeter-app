package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handler.AuthenticateHandler;
import edu.byu.cs.tweeter.client.presenter.AuthPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends BaseService{
    public interface GetUserObserver extends ServiceObserver {
        void getUserSucceeded(User user);
    }
    public interface MainActivityObserver  extends ServiceObserver{
        void logOutSuccess();
    }
    public void login(String alias, String password, AuthPresenter.AuthObserver observer){
        // Send the login request.
        LoginTask loginTask = new LoginTask(alias, password, new AuthenticateHandler(observer));
        execute(loginTask);
    }
    public void getUser(String alias,AuthToken currUserAuthToken, GetUserObserver observer){
        //grab handler first into the user service will go to user service
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                alias, new GetUserHandler(observer));
        execute(getUserTask);
    }
    public void logOut(AuthToken currUserAuthToken, MainActivityObserver observer){
        LogoutTask logoutTask = new LogoutTask(currUserAuthToken, new LogoutHandler(observer));
        execute(logoutTask);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, AuthPresenter.AuthObserver
    observer){
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new AuthenticateHandler(observer));
        execute(registerTask);
    }

}
