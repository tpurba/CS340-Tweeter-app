package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.view.login.RegisterFragment;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter implements RegisterService.RegisterObserver {


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
        var registerService = new RegisterService();
        registerService.register(firstName, lastName, alias, password, imageBytesBase64, this);


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

    @Override
    public void registerFailed(String message) {
        view.showErrorMessage(message);
    }

    @Override
    public void registerSucceeded(AuthToken authToken, User registeredUser) {
        view.hideErrorMessage();
        view.hideInfoMessage();
        view.showInfoMessage("Hello " + Cache.getInstance().getCurrUser().getName());
        view.openMainView(registeredUser);
    }
}
