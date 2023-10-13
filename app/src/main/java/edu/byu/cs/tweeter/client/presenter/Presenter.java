package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public interface Presenter<T> {

    void setLoadingFooter(boolean value);
    void displayMessage(String message);
    void addMoreItems(List<T> items);

}
