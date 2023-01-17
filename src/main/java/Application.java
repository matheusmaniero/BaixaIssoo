import model.twitter.*;
import services.authentication.OAuthAuthenticationManager;

import java.util.Queue;

public class Application {
    public static void main(String[] args) {

        String token = OAuthAuthenticationManager.getToken();
        System.out.println(token);
        Queue<MentionData> mentionsQueue = MentionsGetter.getMentions(token);
        Queue<TweetObjectToReply> toans = ReplyGenerator.getVideoUrl(mentionsQueue,token);
        GetUsernames.getUsernames(toans,token);
        System.out.println(toans);





    }
}
