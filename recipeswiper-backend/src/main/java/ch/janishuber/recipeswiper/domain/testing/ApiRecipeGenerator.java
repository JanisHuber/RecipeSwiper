package ch.janishuber.recipeswiper.domain.testing;

import ch.janishuber.recipeswiper.domain.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiRecipeGenerator {

    private static final HttpClient client = HttpClient.newHttpClient();

    public List<Recipe> getRandomRecipe(int amount) {
        List<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Recipe recipe = getRandomRecipe();
            if (recipe != null) {
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public Recipe getRandomRecipe() {
        String url = "https://www.themealdb.com/api/json/v1/1/random.php";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                MealResponse mealResponse = mapper.readValue(response.body(), MealResponse.class);
                MealDbDto meal = mealResponse.meals().getFirst();
                return mapMealToRecipe(meal);
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return null;
    }


    //todo: change ingredients to a list
    private static Recipe mapMealToRecipe(MealDbDto meal) {
        return new Recipe(
                Integer.parseInt(meal.idMeal()),
                meal.strMeal(),
                meal.strCategory(),
                meal.strMeasure1() + ": " + meal.strIngredient1() + ", " +
                        meal.strMeasure2() + ": " + meal.strIngredient2() + ", " +
                        meal.strMeasure3() + ": " + meal.strIngredient3() + ", " +
                        meal.strMeasure4() + ": " + meal.strIngredient4() + ", " +
                        meal.strMeasure5() + ": " + meal.strIngredient5() + ", " +
                        meal.strMeasure6() + ": " + meal.strIngredient6() + ", " +
                        meal.strMeasure7() + ": " + meal.strIngredient7() + ", " +
                        meal.strMeasure8() + ": " + meal.strIngredient8() + ", " +
                        meal.strMeasure9() + ": " + meal.strIngredient9() + ", " +
                        meal.strMeasure10() + ": " + meal.strIngredient10() + ", " +
                        meal.strMeasure11() + ": " + meal.strIngredient11() + ", " +
                        meal.strMeasure12() + ": " + meal.strIngredient12() + ", " +
                        meal.strMeasure13() + ": " + meal.strIngredient13() + ", " +
                        meal.strMeasure14() + ": " + meal.strIngredient14() + ", " +
                        meal.strMeasure15() + ": " + meal.strIngredient15() + ", " +
                        meal.strMeasure16() + ": " + meal.strIngredient16() + ", " +
                        meal.strMeasure17() + ": " + meal.strIngredient17() + ", " +
                        meal.strMeasure18() + ": " + meal.strIngredient18() + ", " +
                        meal.strMeasure19() + ": " + meal.strIngredient19() + ", " +
                        meal.strMeasure20() + ": " + meal.strIngredient20(),
                meal.strInstructions(),
                meal.strMealThumb()
        );

    }
}
