package edu.byu.cs.tweeter.client.presenter;

import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.FollowerService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainActivityPresenter  {


    public interface View{
        void showInfoMessage(String message);
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
    private View view;
    public MainActivityPresenter(View view){
        this.view = view;
    }
    public void logOut(){
        view.showInfoMessage("Logging Out...");
        var userService = new UserService();
        userService.logOut(Cache.getInstance().getCurrUserAuthToken(), new MainActivityUserServiceObserver());
    }
    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }
    //MOVEOUT
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
    //MOVEOUT
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
    public void getFollowerCount(User selectedUser)
    {
        var followerService = new FollowerService();
        followerService.getFollowerCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityFollowerServiceObserver());
    }
    public void getFollowingCount(User selectedUser){
        var followService = new FollowService();
        followService.getFollowingCount(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityFollowServiceObserver());
    }
    public void isFollower(User selectedUser){
        var followerService = new FollowerService();
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
        followService.unFollow(Cache.getInstance().getCurrUserAuthToken(), selectedUser, new MainActivityFollowServiceObserver());
    }
    public void postStatus(Status newStatus){
        var statusService = new StatusService();
        statusService.postStatus(Cache.getInstance().getCurrUserAuthToken(), newStatus, new MainActivityStatusServiceObserver());
    }
    private class MainActivityUserServiceObserver implements UserService.MainActivityObserver{

        @Override
        public void logOutSuccess() {
            view.hideInfoMessage();
            view.logoutUser();
        }

        @Override
        public void logOutFailed(String message) {
            view.showInfoMessage(message);
        }
    }
    private class MainActivityFollowerServiceObserver implements FollowerService.MainActivityObserver{

        @Override
        public void getFollowerCountSuccess(int count) {
            view.getFollowerCountSuccess(count);
        }
        public void getFollowerCountFailed(String message){
            view.showInfoMessage(message);
        }
        public void isFollower(){
            view.isFollower();
        }
        public void notFollower(){
            view.notFollower();
        }
        public void isFollowerFailed(String message){
            view.showInfoMessage(message);
        }
    }
    private class MainActivityFollowServiceObserver implements FollowService.MainActivityObserver{

        @Override
        public void getFollowingCountSuccess(int count) {
            view.getFollowingCountSuccess(count);
        }

        @Override
        public void getFollowingCountFailed(String message) {
            view.showInfoMessage(message);
        }
        public void followSuccess(boolean updateButton)
        {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(updateButton);
        }

        @Override
        public void followFailed(String message) {
            view.showInfoMessage(message);
        }

        @Override
        public void setFollowButton(boolean button) {
            view.setFollowButton(true);
        }

        @Override
        public void unFollowSuccess(boolean updateButton) {
            view.updateSelectedUserFollowingAndFollowers();
            view.updateFollowButton(updateButton);
        }

        @Override
        public void unFollowFailed(String message) {
            view.showInfoMessage(message);
        }
    }
    public class MainActivityStatusServiceObserver implements StatusService.MainActivityObserver{

        @Override
        public void postSuccess(String message) {
            view.hideInfoMessage();
            view.showInfoMessage(message);
        }

        @Override
        public void postFailed(String message) {
            view.showInfoMessage(message);
        }
    }
}
