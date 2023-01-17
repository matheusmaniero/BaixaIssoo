package model.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String twitterUserId;
    private String screenName;
    private List<Video> videos;

    public User (){

    }
    public User (String screenName, String twitterUserId){
        this.screenName = screenName;
        this.twitterUserId = twitterUserId;
        this.videos = new ArrayList<>();
    }

    public String getTwitterUserId() {
        return twitterUserId;
    }

    public void setTwitterUserId(String twitterUserId) {
        this.twitterUserId = twitterUserId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public List<Video> getVideos() {
        return videos;
    }


}
