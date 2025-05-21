package ch.janishuber.recipeswiper.adapter.persistence.Entity;

public class RecipeEntity {
    private int recipeId;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private String imageUrl;

    public RecipeEntity() {}

    public RecipeEntity(String title, String description, String imageUrl, String ingredients, String instructions) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
