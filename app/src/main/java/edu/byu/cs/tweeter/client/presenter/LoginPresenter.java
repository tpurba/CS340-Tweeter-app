package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthPresenter<AuthPresenter.View> {



    public LoginPresenter(View view){
        super(view);
    }
    public void login(String alias, String password){
        if(validateLogin(alias, password)){
            view.hideErrorMessage();
            view.showInfoMessage("Loggin In...");
            var userService = new UserService();
            userService.login(alias,password, new AuthenticateObserver());
        }
        else{
            //possibly throw error???
        }
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
    public void getUser(String alias){
        view.hideErrorMessage();
        view.showInfoMessage("getting user task started..."); //Delete this later probably I think I created
        var userService = new UserService();
        userService.getUser(alias, Cache.getInstance().getCurrUserAuthToken(), new getUserServiceObserver());
    }
    private class getUserServiceObserver implements UserService.GetUserObserver {

        @Override
        public void handleFailure(String message) {
            view.showErrorMessage("Failed to get user's profile:" + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showErrorMessage("Failed to get user's profile because of exception: " + exception.getMessage());
        }

        @Override
        public void getUserSucceeded(User user) {
            view.openMainView(user);
        }
    }
    private class AuthenticateObserver extends AuthObserver {

        @Override
        public void handleFailure(String message) {
            view.showErrorMessage("Failed to login:" + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showErrorMessage("Failed to login because of exception: " + exception.getMessage());
        }
    }


}


