package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.twitter.MentionData;
import model.twitter.TweetObjectParser;
import model.twitter.TweetReplyToJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Json {



    private static Logger logger = LogManager.getLogger(Json.class);
    private static ObjectMapper objectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper() {
        return new ObjectMapper();

    }


    public static TweetObjectParser parse (String src){

        try {
            return objectMapper.readValue(src, TweetObjectParser.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }


    public static MentionData parseMention(String src){

        try {
            return objectMapper.readValue(src, MentionData.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }


    public static String replyToJson(TweetReplyToJson tweetReplyToJson){
        try {
            return objectMapper.writeValueAsString(tweetReplyToJson);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getField(String src, String field){

        try {
            JsonNode node = objectMapper.readTree(src);
            return node.path(field).asText();
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static Map<String,String> jsonToMap(String src){
        Map<String,String> ans = new HashMap<>();
        try {
            Map<String,Object> map = objectMapper.readValue(src,Map.class);
            ArrayList<LinkedHashMap<String,String>> list = (ArrayList<LinkedHashMap<String, String>>) map.get("data");
            for (LinkedHashMap<String,String> lhm : list){
                ans.put(lhm.get("id"),lhm.get("username"));
            }


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ans;
    }
}
