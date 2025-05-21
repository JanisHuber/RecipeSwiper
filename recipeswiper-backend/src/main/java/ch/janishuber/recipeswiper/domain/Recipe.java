package ch.janishuber.recipeswiper.domain;

public record Recipe(int recipeId, String title, String description, String ingredients, String instructions, String image_url) {}