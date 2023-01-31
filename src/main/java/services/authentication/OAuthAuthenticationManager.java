package services.authentication;

import db.DB;
import model.dao.CredentialsDao;
import model.dao.DaoFactory;
import model.entities.Credentials;
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

public class OAuthAuthenticationManager {

    private static Logger logger = LogManager.getLogger(OAuthAuthenticationManager.class);


    public static String getToken() {
        CredentialsDao dao = DaoFactory.createCredentialsDao();
        Credentials credentials = dao.instantiateCredentials();
        long timeNow = System.currentTimeMillis();
        long timeLapsed = 5400000;

        if (credentials != null && (timeNow-credentials.getCreatedAt() >= timeLapsed) ){
            credentials = getNewAccessToken(credentials.getRefreshToken());
            dao.update(credentials);
        }

        if (credentials.getAccessToken().isBlank() || credentials.getRefreshToken().isBlank()){
            logger.error("Access Token or Refresh Token is Blank!");
            throw new RuntimeException("Access Token or Refresh Token is Blank!");
        }

        return credentials.getAccessToken();

    }

    private static Credentials getNewAccessToken(String refreshToken) {
        final String CLIENT_ID = System.getenv("TWITTER_CLIENT_ID");
        final String REFRESH_TOKEN_ENDPOINT = "https://api.twitter.com/2/oauth2/token?refresh_token="+refreshToken+"&"+"grant_type=refresh_token&client_id="+CLIENT_ID;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(REFRESH_TOKEN_ENDPOINT))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .headers("Content-Type","application/x-www-form-urlencoded")
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String accessToken = Json.getField(response.body(),"access_token");
            String newRefreshToken = Json.getField(response.body(),"refresh_token");
            Long timeStamp = (System.currentTimeMillis());
            Credentials newCredentials = new Credentials(accessToken,newRefreshToken,timeStamp);

            return newCredentials;

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
