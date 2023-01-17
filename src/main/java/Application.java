import model.twitter.MentionData;
import model.twitter.MentionsGetter;
import services.authentication.OAuthAuthenticationManager;

import java.util.Queue;

public class Application {
    public static void main(String[] args) {

        String token = OAuthAuthenticationManager.getToken();
        System.out.println(token);
        Queue<MentionData> mentionsQueue = MentionsGetter.getMentions(token);



    }
}
