package edu.byu.cs.tweeter.client.presenter;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {


    public interface View{
        void showInfoMessage(String message);
        void hideInfoMessage();
        void showErrorMessage(String message);
        void hideErrorMessage();
        void openMainView(User user);
    }
    private View view;
    private String message = null;
    public RegisterPresenter(View view){this.view = view;}
    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64)
    {
        view.hideErrorMessage();
        view.showInfoMessage("Registering...");
        var registerService = new UserService.RegisterService();
        registerService.register(firstName, lastName, alias, password, imageBytesBase64, new RegisterServiceObserver());


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


    private class RegisterServiceObserver implements UserService.RegisterService.RegisterObserver, ServiceObserver {
        @Override
        public void registerSucceeded(AuthToken authToken, User registeredUser) {
            view.hideErrorMessage();
            view.hideInfoMessage();
            view.showInfoMessage("Hello " + Cache.getInstance().getCurrUser().getName());
            view.openMainView(registeredUser);
        }
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
