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
import java.util.LinkedList;
import java.util.Queue;

public class ReplyGenerator {

    private static Logger logger = LogManager.getLogger(ReplyGenerator.class);

    public static Queue<TweetObjectToReply> getVideoUrl(Queue<MentionData> mentionsQueue, String accessToken)  {

        Queue<TweetObjectToReply> tweetsToReply = new LinkedList<>();

        while(!mentionsQueue.isEmpty()){
            MentionData mentionData = mentionsQueue.poll();
            String postIdParentId = mentionData.getPostParentId();
            String postIdToReply = mentionData.getPostIdToReply();
            String authorId = mentionData.getAuthorId();
            String URL_GET_PATH = "https://api.twitter.com/2/tweets/"+postIdParentId+"?tweet.fields=attachments&expansions=attachments.media_keys&media.fields=variants";


            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(URL_GET_PATH))
                        .GET()
                        .headers("Authorization","Bearer "+accessToken)
                        .timeout(Duration.ofSeconds(10))
                        .build();
                HttpClient httpClient = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                TweetObjectParser tweetToReply = Json.parse(response.body());

                tweetsToReply.add(new TweetObjectToReply(postIdToReply,authorId,tweetToReply.getVideoUrl()));

            } catch (URISyntaxException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return tweetsToReply;

    }
}
