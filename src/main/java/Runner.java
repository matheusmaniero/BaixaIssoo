import model.twitter.*;
import services.authentication.OAuthAuthenticationManager;

import java.util.Queue;

public class Runner {

    public static void run(){
        String token = OAuthAuthenticationManager.getToken();
        Queue<MentionData> mentionsQueue = MentionsGetter.getMentions(token);
        Queue<TweetObjectToReply> toans = ReplyGenerator.getVideoUrl(mentionsQueue,token);
        GetUsernames.getUsernames(toans,token);
        PersistData.persist(toans);
        PostReplier.answerTweets(toans,token);
    }

}
