package model.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long twitterUserId;
    private String screenName;
    private List<Video> videos;

    public User (){
        this.videos = new ArrayList<>();
    }
    public User (String screenName, Long twitterUserId){
        this.screenName = screenName;
        this.twitterUserId = twitterUserId;
        this.videos = new ArrayList<>();
    }

    public Long getTwitterUserId() {
        return twitterUserId;
    }

    public void setTwitterUserId(Long twitterUserId) {
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
