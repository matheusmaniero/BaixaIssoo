package model.twitter;

import java.time.ZonedDateTime;

public class TweetObjectToReply {

    private String postIdToReply;
    private String userToReplyId;
    private String videoUrl;
    private String userScreenName;
    private ZonedDateTime mentionDate;


    public TweetObjectToReply(String postIdToReply, String userToReplyId, String videoUrl, ZonedDateTime mentionDate) {
        this.postIdToReply = postIdToReply;
        this.userToReplyId = userToReplyId;
        this.videoUrl = videoUrl;
        this.mentionDate = mentionDate;
    }

    public String getPostIdToReply() {
        return postIdToReply;
    }

    public String getUserToReplyId() {
        return userToReplyId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public void setPostIdToReply(String postIdToReply) {
        this.postIdToReply = postIdToReply;
    }

    public void setUserToReplyId(String userToReplyId) {
        this.userToReplyId = userToReplyId;
    }

    public ZonedDateTime getMentionDate() {
        return mentionDate;
    }

    public void setMentionDate(ZonedDateTime mentionDate) {
        this.mentionDate = mentionDate;
    }
}
