import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
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
        UserDao dao = DaoFactory.createUserDao();
        while(!toans.isEmpty()){
            TweetObjectToReply to = toans.poll();
            User us = new User(to.getUserScreenName(),to.getUserToReplyId());
            User userFromDB = dao.findById(us.getTwitterUserId());
            if (userFromDB == null){
                dao.insert(us);
            }

        }







    }
}
