package edu.byu.cs.tweeter.client.presenter;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthPresenter<AuthPresenter.View> {

    public RegisterPresenter(View view){
        super(view);
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64)
    {
        view.hideErrorMessage();
        view.showInfoMessage("Registering...");
        var registerService = new UserService();
        registerService.register(firstName, lastName, alias, password, imageBytesBase64, new AuthenticateObserver());
    }
    //can I have these throw? it works...
    public void validateRegistration(String firstName, String lastName, String alias, String password) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }


    private class AuthenticateObserver extends AuthObserver {
        @Override
        public void handleFailure(String message) {
            view.showErrorMessage("Failed to register: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showErrorMessage("Failed to register because of exception: " + exception.getMessage());
        }
    }
}

