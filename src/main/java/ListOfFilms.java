import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ListOfFilms {
    public static void main(String[] args) throws URISyntaxException {

        final String password = "k_cp505m6c";

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(new URI("https://imdb-api.com/en/API/Top250Movies/"+ password))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        client
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }
}
