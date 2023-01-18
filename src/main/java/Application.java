import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import model.entities.Video;
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
            long timeNow = System.currentTimeMillis();
            TweetObjectToReply to = toans.poll();
            User us = new User(to.getUserScreenName(),Long.valueOf(to.getUserToReplyId()));
            User userFromDB = dao.userExists(us.getTwitterUserId());
            if (userFromDB == null){
                dao.insertNewUser(us);
            }
            dao.insertVideo(new Video(to.getVideoUrl(),timeNow,Long.valueOf(to.getUserToReplyId())));


        }
        dao.findById(1403858370730278915L);

    }
}
