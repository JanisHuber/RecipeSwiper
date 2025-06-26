package ch.janishuber.recipeswiper.adapter.mealdb;

import ch.janishuber.recipeswiper.domain.Recipe;
import ch.janishuber.recipeswiper.domain.api.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.Dependent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Dependent
public class MealDbRecipe implements RecipeApi {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final List<Tags> DEFAULT_TAGS = List.of(Tags.BEEF, Tags.CHICKEN, Tags.LAMB, Tags.PASTA, Tags.PORK, Tags.SEAFOOD, Tags.VEGETARIAN, Tags.VEGAN);

    private static Recipe mapMealToRecipe(MealDetailsDbDto meal) {
        return new Recipe(
                Integer.parseInt(meal.idMeal()),
                meal.strMeal(),
                meal.strCategory(),
                convertIngredientsToString(meal),
                meal.strInstructions(),
                meal.strMealThumb()
        );
    }

    private static String convertIngredientsToString(MealDetailsDbDto meal) {
        StringBuilder ingredients = new StringBuilder();
        try {
            for (int i = 1; i <= 20; i++) {
                Method measureMethod = meal.getClass().getMethod("strMeasure" + i);
                Method ingredientMethod = meal.getClass().getMethod("strIngredient" + i);
                String measure = (String) measureMethod.invoke(meal);
                String ingredient = (String) ingredientMethod.invoke(meal);

                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredients.append(measure).append(": ").append(ingredient).append(", ");
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return ingredients.toString();
    }

    private static List<Tags> reduceTagListWithProportionalFrequency(List<Tags> tags) {
        Map<Tags, Integer> counts = new HashMap<>();
        for (Tags tag : tags) {
            counts.put(tag, counts.getOrDefault(tag, 0) + 1);
        }

        int totalToKeep = Math.min(7, tags.size());
        List<Tags> reducedList = new ArrayList<>();

        List<Tags> weightedPool = new ArrayList<>();
        for (Map.Entry<Tags, Integer> entry : counts.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                weightedPool.add(entry.getKey());
            }
        }

        Collections.shuffle(weightedPool);
        for (int i = 0; i < totalToKeep && i < weightedPool.size(); i++) {
            reducedList.add(weightedPool.get(i));
        }

        return reducedList;
    }


    @Override
    public Recipe getRandomRecipe() {
        String url = "https://www.themealdb.com/api/json/v1/1/random.php";
        String response = sendHttpRequest(url);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MealDetailsResponse mealDetailsResponse = mapper.readValue(response, MealDetailsResponse.class);
            MealDetailsDbDto meal = mealDetailsResponse.meals().getFirst();
            return mapMealToRecipe(meal);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse random meal response", e);
        }
    }

    @Override
    public List<Recipe> getRecipesByTags(List<Tags> tags) {
        List<Recipe> recipes = new ArrayList<>();
        Random rand = new Random();
        ObjectMapper mapper = new ObjectMapper();

        if (tags.isEmpty()) {
            tags = DEFAULT_TAGS;
        }

        tags = reduceTagListWithProportionalFrequency(tags);
        tags.forEach(tag -> {
            String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=" + tag.toString().toLowerCase();
            String response = sendHttpRequest(url);
            try {
                MealCategoryResponse mealCategoryResponse = mapper.readValue(response, MealCategoryResponse.class);
                int randomIndex = rand.nextInt(mealCategoryResponse.meals().size());
                MealCategoryDbDto mealCategoryDbDto = mealCategoryResponse.meals().get(randomIndex);
                recipes.add(getRecipeDetails(mealCategoryDbDto.idMeal()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse mealCategory response", e);
            }
        });
        int randomCount = (int) Math.ceil(recipes.size() * 0.3);
        for (int i = 0; i < randomCount; i++) {
            recipes.add(getRandomRecipe());
        }
        return recipes;
    }

    private Recipe getRecipeDetails(String id) {
        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id;
        String response = sendHttpRequest(url);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MealDetailsResponse mealDetailsResponse = mapper.readValue(response, MealDetailsResponse.class);
            MealDetailsDbDto meal = mealDetailsResponse.meals().getFirst();
            return mapMealToRecipe(meal);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse meal response", e);
        }
    }

    private String sendHttpRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
