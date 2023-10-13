package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.ServiceObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter extends BasePresenter<MainActivityPresenter.View> {


    public interface View{
        void showInfoMessage(String message);//may change this to display message later to match other functions
        void hideInfoMessage();
        void logoutUser();
        void getFollowerCountSuccess(int count);
        void getFollowingCountSuccess(int count);
        void isFollower();
        void notFollower();
        void updateSelectedUserFollowingAndFollowers();
        void updateFollowButton(boolean removed);
        void setFollowButton(boolean button);
    }

    public MainActivityPresenter(View view){
        super(view);
    }
    public void logOut(){
        view.showInfoMessage("Logging Out...");
        var userService = new UserService();
        userService.logOut(Cache.getInstance().getCurrUserAuthToken(), new MainActivityUserServiceObserver());
    }
    public void getFollowerCount(User selectedUser)
    {
        var followerService = new FollowService();
        followerService.getFollowerCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityFollowerCountServiceObserver());
    }
    public void getFollowingCount(User selectedUser){
        var followService = new FollowService();
        followService.getFollowingCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityFollowCountServiceObserver());
    }
    public void isFollower(User selectedUser){
        var followerService = new FollowService();
        followerService.isFollower(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(), selectedUser, new MainActivityFollowerServiceObserver() );
    }
    public void follow(User selectedUser){
        view.showInfoMessage( "Adding " + selectedUser.getName() + "...");
        var followService = new FollowService();
        followService.follow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityFollowServiceObserver());
    }
    public void unFollow(User selectedUser){
        view.showInfoMessage("Removing " + selectedUser.getName() + "...");
        var followService = new FollowService();
        followService.unFollow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityUnFollowServiceObserver());
    }
    public void postStatus(Status newStatus){
        var statusService = new StatusService();
        statusService.postStatus(Cache.getInstance().getCurrUserAuthToken(), newStatus, new MainActivityStatusServiceObserver());
    }
    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }
    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }
    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }
    private class MainActivityUserServiceObserver implements UserService.MainActivityObserver, ServiceObserver{

        @Override
        public void logOutSuccess() {
            view.hideInfoMessage();
            view.logoutUser();
        }
        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to logout: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to logout because of exception: " + exception.getMessage());
        }
    }
    private class MainActivityFollowerServiceObserver implements FollowService.MainActivityFollowerObserver, ServiceObserver{

        public void isFollower(){
            view.isFollower();
        }
        public void notFollower(){
            view.notFollower();
        }
        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to determine following relationship: " + message);
        }
        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to determine following relationship because of exception: " + exception.getMessage());
        }
    }
    private class MainActivityFollowerCountServiceObserver implements FollowService.CountServiceObserver, ServiceObserver{
        @Override
        public void getCountSuccess(int count) {
            view.getFollowerCountSuccess(count);
        }
        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to get followers count: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to get followers count because of exception: " + exception.getMessage());
        }
    }
    private class MainActivityFollowCountServiceObserver implements FollowService.CountServiceObserver, ServiceObserver { //merge the interfaces
        @Override
        public void getCountSuccess(int count) {
            view.getFollowingCountSuccess(count);
        }
        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to get following count: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to get following count because of exception: " + exception.getMessage());
        }
    }
    private class MainActivityFollowServiceObserver implements FollowService.FollowButtonObserver {
        public void buttonPressSuccess()
        {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(false);
        }

        @Override
        public void setFollowButton(boolean button) {
            view.setFollowButton(button);
        }

        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to follow: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to follow because of exception: " + exception.getMessage());
        }
    }
    private class MainActivityUnFollowServiceObserver implements FollowService.FollowButtonObserver, ServiceObserver{
        @Override
        public void buttonPressSuccess() {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(true);
        }
        @Override
        public void setFollowButton(boolean button) {
            view.setFollowButton(button);
        }
        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to unfollow: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to unfollow because of exception: " + exception.getMessage());
        }
    }


    private class MainActivityStatusServiceObserver implements StatusService.PostObserver, ServiceObserver{

        @Override
        public void postSuccess(String message) {
            view.hideInfoMessage();
            view.showInfoMessage(message);
        }
        @Override
        public void handleFailure(String message) {
            view.showInfoMessage("Failed to post status: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.showInfoMessage("Failed to post status because of exception: " + exception.getMessage());
        }
    }
}
