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

public class MentionsGetter {
    private static Logger logger = LogManager.getLogger(MentionsGetter.class);

    public static Queue<MentionData> getMentions(String accessToken) {
        final String BOT_ID = "1612386069177040896";
        final String  URL_GET_MENTIONS_PATH = "https://api.twitter.com/2/users/"+BOT_ID+"/mentions?expansions=author_id&tweet.fields=referenced_tweets,created_at";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_GET_MENTIONS_PATH))
                    .GET()
                    .headers("Authorization","Bearer "+accessToken)
                    .timeout(Duration.ofSeconds(10))
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            MentionData mentionData = Json.parseMention(response.body());

            return mentionData.getQueue();

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
}