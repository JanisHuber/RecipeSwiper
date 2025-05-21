package ch.janishuber.recipeswiper.adapter.persistence.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_recipes")
public class GroupRecipesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "recipe_id")
    private int recipe_id;

    @Column(name = "group_id")
    private int group_id;

    @Column(name = "likes")
    private int likes;

    public GroupRecipesEntity() {
    }

    public GroupRecipesEntity(int recipe_id, int group_id, int likes) {
        this.recipe_id = recipe_id;
        this.group_id = group_id;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
