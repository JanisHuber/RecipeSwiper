package ch.janishuber.recipeswiper.domain.api;

import java.util.List;

public record MealCategoryResponse(List<MealCategoryDbDto> meals) {}
