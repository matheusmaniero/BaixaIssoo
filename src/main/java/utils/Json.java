package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.twitter.MentionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Json {



    private static Logger logger = LogManager.getLogger(Json.class);
    private static ObjectMapper objectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();

    }
    /*

    public static TweetObjectParser parse (String src){

        try {
            return objectMapper.readValue(src, TweetObjectParser.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
    */

    public static MentionData parseMention(String src){

        try {
            return objectMapper.readValue(src, MentionData.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
    /*

    public static String replyToJson(TweetReplyToJson tweetReplyToJson){
        try {
            return objectMapper.writeValueAsString(tweetReplyToJson);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
*/
    public static String getField(String src, String field){

        try {
            JsonNode node = objectMapper.readTree(src);
            return node.path(field).asText();
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
