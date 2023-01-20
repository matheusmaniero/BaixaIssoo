package model.twitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.dao.DaoFactory;
import model.dao.LastRequestDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MentionData {

    private static Logger logger = LogManager.getLogger(MentionData.class);

    private String postParentId;

    private String postIdToReply;

    private String authorId;

    private ZonedDateTime mentionDate;

    private Queue<MentionData> queue = new LinkedList<>();

    private ZonedDateTime lastPostDate;

    private String lastDateToSave;

    public MentionData(){

    }
    public MentionData (String postParentId, String authorId, ZonedDateTime mentionDate, String postIdToReply){
        this.postParentId = postParentId;
        this.authorId = authorId;
        this.mentionDate = mentionDate;
        this.postIdToReply = postIdToReply;
    }

    @JsonProperty("data")
    private void unpackData(ArrayList<Map<String,Object>> result) throws ParseException {
        LastRequestDao dao = DaoFactory.createLastRequestDao();
        getLastPostDate(dao);

        for (int i = 0; i<result.size(); i++){
            ArrayList<Map<String,Object>> referenced = (ArrayList<Map<String, Object>>) result.get(i).get("referenced_tweets");
            String postIdToReply = (String) result.get(i).get("id");
             String authorId = (String) result.get(i).get("author_id");
            String postParentId = (String) referenced.get(0).get("id");
            String stringCreatedAt = (String) result.get(i).get("created_at");
            if (i == 0){
                this.lastDateToSave = stringCreatedAt;
            }
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(stringCreatedAt);
            if (zonedDateTime.isAfter(this.lastPostDate)){
                queue.add(new MentionData(postParentId,authorId,zonedDateTime,postIdToReply));

            }else {
                break;
            }

        }
        updateLastPostdate(dao);
    }


    private void updateLastPostdate(LastRequestDao dao) {
        dao.update(lastDateToSave);
    }
    private void getLastPostDate(LastRequestDao dao) {
        String dateString = dao.getLastRequest();
        this.lastPostDate = ZonedDateTime.parse(dateString);
    }


    public String getPostParentId() {
        return postParentId;
    }

    public String getPostIdToReply() {
        return postIdToReply;
    }

    public ZonedDateTime getMentionDate() {
        return mentionDate;
    }

    public String getLastDateToSave() {
        return lastDateToSave;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Queue<MentionData> getQueue() {
        return queue;
    }
}

