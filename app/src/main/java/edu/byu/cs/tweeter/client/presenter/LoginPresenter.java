package edu.byu.cs.tweeter.client.presenter;

import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.view.login.LoginFragment;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter implements UserService.LoginObserver{


    public interface View{
        void showInfoMessage(String message);
        void hideInfoMessage();
        void showErrorMessage(String message);
        void hideErrorMessage();
        void openMainView(User user);
    }
    private View view;

    public LoginPresenter(View view){
        this.view = view;
    }
    public void login(String alias, String password){
        if(validateLogin(alias, password)){
            view.hideErrorMessage();
            view.showInfoMessage("Loggin In...");
            var userService = new UserService();
            userService.login(alias,password, this);

        }
        else{

        }
    }
    public void getUser(String alias){
        view.hideErrorMessage();
        view.showInfoMessage("getting user task started..."); //Delete this later probably I think I created
        var userService = new UserService();
        userService.getUser(alias, Cache.getInstance().getCurrUserAuthToken(), this);
    }

    public boolean validateLogin(String alias, String password) {
        if (alias.length() > 0 && alias.charAt(0) != '@') {
            view.showErrorMessage("Alias must begin with @.");
            return false;
        }
        if (alias.length() < 2) {
            view.showErrorMessage("Alias must contain 1 or more characters after the @.");
            return false;
        }
        if (password.length() == 0) {
            view.showErrorMessage("Password cannot be empty.");
            return false;
        }
        return true;
    }

    @Override
    public void loginSucceeded(AuthToken authToken, User user) {
        view.hideErrorMessage();
        view.hideInfoMessage();
        view.showInfoMessage("Hello, " + user.getName());
        view.openMainView(user);

    }

    @Override
    public void loginFailed(String message) {
        view.showErrorMessage(message);
    }
    public void getUserSuccess(User user){
        view.hideErrorMessage();
        view.hideInfoMessage();
        view.showInfoMessage("Getting user's profile...");
        view.openMainView(user);

    }
    public void getUserFailed(String message){view.showErrorMessage(message);}
}
