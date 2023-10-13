package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthPresenter<V extends AuthPresenter.View> extends BasePresenter<V> {
    public interface View extends BasePresenter.View {
        void showInfoMessage(String message);
        void hideInfoMessage();
        void showErrorMessage(String message);
        void hideErrorMessage();
        void openMainView(User user);
    }

    protected AuthPresenter(V view) {
        super(view);
    }



    public abstract class AuthObserver implements ServiceObserver {
        public final void authenticateSucceeded(AuthToken authToken, User user) {
            view.hideErrorMessage();
            view.hideInfoMessage();
            view.showInfoMessage("Hello " + user.getName());
            view.openMainView(user);
        }
        public abstract void handleFailure(String message);
        public abstract void handleException(Exception exception);

    }
}

