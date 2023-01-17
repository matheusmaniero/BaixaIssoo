import services.authentication.OAuthAuthenticationManager;

public class Application {
    public static void main(String[] args) {

        String token = OAuthAuthenticationManager.getToken();
        System.out.println(token);



    }
}
