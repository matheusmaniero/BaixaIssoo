import model.dao.DBInsertionControlDao;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import model.entities.Video;
import model.twitter.*;
import services.authentication.OAuthAuthenticationManager;

import java.time.Instant;
import java.util.Queue;

public class Application {
    public static void main(String[] args) {

        String token = OAuthAuthenticationManager.getToken();
        Queue<MentionData> mentionsQueue = MentionsGetter.getMentions(token);
        Queue<TweetObjectToReply> toans = ReplyGenerator.getVideoUrl(mentionsQueue,token);
        GetUsernames.getUsernames(toans,token);
        UserDao userDao = DaoFactory.createUserDao();
        DBInsertionControlDao controlDao = DaoFactory.createDbControlDao();
        int i = 0;

        for (TweetObjectToReply to : toans){
            if (i == 0){
                Long lastInsertedTimestamp = controlDao.getLastInsertionTimeStamp();
                if (lastInsertedTimestamp == null ){
                    throw new RuntimeException("LAST INSERTED TIMESTAMP IN DB IS NULL");
                }
                Instant lastInsertedInstant = Instant.ofEpochMilli(lastInsertedTimestamp);
                Instant lastMentionedInstant = Instant.ofEpochMilli(to.getMentionDate().toInstant().toEpochMilli());
                if (lastInsertedInstant.isAfter(lastMentionedInstant) || lastMentionedInstant.equals(lastInsertedInstant)){
                    break;
                }
                controlDao.UpdateTimeStamp(to.getMentionDate().toInstant().toEpochMilli());
                    }

            Long timeStamp = to.getMentionDate().toInstant().toEpochMilli();
            User us = new User(to.getUserScreenName(),Long.valueOf(to.getUserToReplyId()));
            User userFromDB = userDao.userExists(us.getTwitterUserId());
            if (userFromDB == null){
                userDao.insertNewUser(us);
            }
            Video vid = new Video(to.getVideoUrl(),timeStamp,Long.valueOf(to.getUserToReplyId()));
            userDao.insertVideo(vid);
            i++;

        }
        userDao.findById(1403858370730278915L);

    }
}
