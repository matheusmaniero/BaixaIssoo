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
import java.util.Queue;
import java.util.Random;

public class PostReplier {

    private static Logger logger = LogManager.getLogger(PostReplier.class);

    public static void answerTweets(Queue<TweetObjectToReply> queueToReply, String accessToken) {
        int numberOfPosts = 0;

        final String URL_POST_REPLY = "https://api.twitter.com/2/tweets";
        final String urlSite = "http://baixaissoo.up.railway.app/";
        final String standardMessage = "Juntei este vídeo com os outros que você já me pediu, estão aqui -> ";

        while(!queueToReply.isEmpty()){

            TweetObjectToReply to = queueToReply.poll();


            if (to.getVideoUrl() == null || to.getPostIdToReply() == null){
                continue;
            }
            TweetReplyToJson trj = new TweetReplyToJson(standardMessage+urlSite+to.getUserScreenName(),to.getPostIdToReply());
            String replyJson = Json.replyToJson(trj);

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(URL_POST_REPLY))
                        .POST(HttpRequest.BodyPublishers.ofString(replyJson))
                        .headers("Authorization","Bearer "+accessToken)
                        .headers("Content-Type","application/json")
                        .timeout(Duration.ofSeconds(10))
                        .build();

                HttpClient httpClient = HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                numberOfPosts++;


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

        logger.info(numberOfPosts+" messsages have been successfully posted");


    }
}