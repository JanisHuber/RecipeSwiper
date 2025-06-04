package ch.janishuber.recipeswiper.adapter.persistence.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_votes")
public class RecipeVotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "recipe_id")
    private int recipe_id;

    @Column(name = "group_id")
    private int group_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote", nullable = false)
    private VoteType vote;

    public RecipeVotesEntity() {
    }

    public RecipeVotesEntity(int recipe_id, int user_id, int group_id, VoteType vote) {
        this.recipe_id = recipe_id;
        this.user_id = user_id;
        this.group_id = group_id;
        this.vote = vote;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
    public VoteType getVote() {
        return vote;
    }
    public void setVote(VoteType vote) {
        this.vote = vote;
    }
}
