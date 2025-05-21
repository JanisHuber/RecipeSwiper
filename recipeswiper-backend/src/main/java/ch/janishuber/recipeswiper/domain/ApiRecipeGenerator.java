package ch.janishuber.recipeswiper.domain;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRecipeGenerator {

    private static final HttpClient client = HttpClient.newHttpClient();

    public Recipe getRandomRecipe() {
        //todo add Interface for diffrent API-Calls
        String url = " www.themealdb.com/api/json/v1/1/random.php ";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                //todo Mapping
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }
}
