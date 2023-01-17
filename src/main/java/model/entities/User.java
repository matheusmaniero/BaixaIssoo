package model.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private Long twitterUserId;
    private String screenName;
    private List<Video> videos;
    public User (String screenName, Long twitterUserId){
        this.screenName = screenName;
        this.twitterUserId = twitterUserId;
        this.videos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Long getTwitterUserId() {
        return twitterUserId;
    }

    public String getScreenName() {
        return screenName;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
