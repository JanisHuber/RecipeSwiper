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
                convertIngredientsToString(meal),
                meal.strInstructions(),
                meal.strMealThumb()
        );
    }

    private static String convertIngredientsToString(MealDbDto meal) {
        StringBuilder ingredients = new StringBuilder();
        if (meal.strIngredient1() != null && !meal.strIngredient1().isEmpty()) {
            ingredients.append(meal.strMeasure1()).append(": ").append(meal.strIngredient1()).append(", ");
        }
        if (meal.strIngredient2() != null && !meal.strIngredient2().isEmpty()) {
            ingredients.append(meal.strMeasure2()).append(": ").append(meal.strIngredient2()).append(", ");
        }
        if (meal.strIngredient3() != null && !meal.strIngredient3().isEmpty()) {
            ingredients.append(meal.strMeasure3()).append(": ").append(meal.strIngredient3()).append(", ");
        }
        if (meal.strIngredient4() != null && !meal.strIngredient4().isEmpty()) {
            ingredients.append(meal.strMeasure4()).append(": ").append(meal.strIngredient4()).append(", ");
        }
        if (meal.strIngredient5() != null && !meal.strIngredient5().isEmpty()) {
            ingredients.append(meal.strMeasure5()).append(": ").append(meal.strIngredient5()).append(", ");
        }
        if (meal.strIngredient6() != null && !meal.strIngredient6().isEmpty()) {
            ingredients.append(meal.strMeasure6()).append(": ").append(meal.strIngredient6()).append(", ");
        }
        if (meal.strIngredient7() != null && !meal.strIngredient7().isEmpty()) {
            ingredients.append(meal.strMeasure7()).append(": ").append(meal.strIngredient7()).append(", ");
        }
        if (meal.strIngredient8() != null && !meal.strIngredient8().isEmpty()) {
            ingredients.append(meal.strMeasure8()).append(": ").append(meal.strIngredient8()).append(", ");
        }
        if (meal.strIngredient9() != null && !meal.strIngredient9().isEmpty()) {
            ingredients.append(meal.strMeasure9()).append(": ").append(meal.strIngredient9()).append(", ");
        }
        if (meal.strIngredient10() != null && !meal.strIngredient10().isEmpty()) {
            ingredients.append(meal.strMeasure10()).append(": ").append(meal.strIngredient10()).append(", ");
        }
        if (meal.strIngredient11() != null && !meal.strIngredient11().isEmpty()) {
            ingredients.append(meal.strMeasure11()).append(": ").append(meal.strIngredient11()).append(", ");
        }
        if (meal.strIngredient12() != null && !meal.strIngredient12().isEmpty()) {
            ingredients.append(meal.strMeasure12()).append(": ").append(meal.strIngredient12()).append(", ");
        }
        if (meal.strIngredient13() != null && !meal.strIngredient13().isEmpty()) {
            ingredients.append(meal.strMeasure13()).append(": ").append(meal.strIngredient13()).append(", ");
        }
        if (meal.strIngredient14() != null && !meal.strIngredient14().isEmpty()) {
            ingredients.append(meal.strMeasure14()).append(": ").append(meal.strIngredient14()).append(", ");
        }
        if (meal.strIngredient15() != null && !meal.strIngredient15().isEmpty()) {
            ingredients.append(meal.strMeasure15()).append(": ").append(meal.strIngredient15()).append(", ");
        }
        if (meal.strIngredient16() != null && !meal.strIngredient16().isEmpty()) {
            ingredients.append(meal.strMeasure16()).append(": ").append(meal.strIngredient16()).append(", ");
        }
        if (meal.strIngredient17() != null && !meal.strIngredient17().isEmpty()) {
            ingredients.append(meal.strMeasure17()).append(": ").append(meal.strIngredient17()).append(", ");
        }
        if (meal.strIngredient18() != null && !meal.strIngredient18().isEmpty()) {
            ingredients.append(meal.strMeasure18()).append(": ").append(meal.strIngredient18()).append(", ");
        }
        if (meal.strIngredient19() != null && !meal.strIngredient19().isEmpty()) {
            ingredients.append(meal.strMeasure19()).append(": ").append(meal.strIngredient19()).append(", ");
        }
        if (meal.strIngredient20() != null && !meal.strIngredient20().isEmpty()) {
            ingredients.append(meal.strMeasure20()).append(": ").append(meal.strIngredient20());
        }
        return ingredients.toString();
    }
}
