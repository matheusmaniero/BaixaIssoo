package model.twitter;

public class TweetObjectToReply {

    private String postIdToReply;
    private String userToReplyId;
    private String videoUrl;
    private String userScreenName;
    private String shortUrl;


    public TweetObjectToReply(String postIdToReply, String userToReplyId, String videoUrl) {
        this.postIdToReply = postIdToReply;
        this.userToReplyId = userToReplyId;
        this.videoUrl = videoUrl;
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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
