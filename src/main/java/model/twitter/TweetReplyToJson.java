package model.twitter;

import java.util.HashMap;
import java.util.Map;

public class TweetReplyToJson {
    public String text;
    public Map<String,Object> reply = new HashMap<>();

    public TweetReplyToJson(String text, String postIdToReply){
        this.text = text;
        this.reply.put("in_reply_to_tweet_id",postIdToReply);
    }

}
