package model.twitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Json;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Queue;

public class GetUsernames {

    private static Logger logger = LogManager.getLogger(GetUsernames.class);

    public static void getUsernames(Queue<TweetObjectToReply> queue, String accessToken){

        String usersIds = getUsersIds(queue);
        Map<String,String> userNamesById = makeGetUsernames(usersIds,accessToken);

        for (TweetObjectToReply to : queue){
            to.setUserScreenName(userNamesById.get(to.getUserToReplyId()));
        }
    }
    private static String getUsersIds(Queue<TweetObjectToReply> queue) {

        StringBuilder sb = new StringBuilder();
        for (TweetObjectToReply to : queue){
            sb.append(to.getUserToReplyId());
            sb.append(",");
        }

        String str = sb.toString();
        if (!str.isBlank()){
            return str.substring(0,str.length()-1);
        }
        return str;

    }

    private static Map<String,String> makeGetUsernames(String userIds, String accessToken){

        final String URL_PATH = "https://api.twitter.com/2/users?ids="+userIds;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_PATH))
                    .GET()
                    .headers("Authorization","Bearer "+accessToken)
                    .timeout(Duration.ofSeconds(10))
                    .build();
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

            Map<String,String> map = Json.jsonToMap(response.body());

            return map;

        } catch (URISyntaxException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
