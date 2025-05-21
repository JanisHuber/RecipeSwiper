package ch.janishuber.recipeswiper.adapter.persistence.Entity;


import jakarta.persistence.*;


@Entity
@Table(name = "recipes")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "ingredients")
    private String ingredients;
    @Column(name = "instructions")
    private String instructions;
    @Column(name = "image_url")
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
        return id;
    }

    public void setRecipeId(int recipeId) {
        this.id = recipeId;
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
