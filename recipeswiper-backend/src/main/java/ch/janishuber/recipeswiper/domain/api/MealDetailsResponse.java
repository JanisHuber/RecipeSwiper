package ch.janishuber.recipeswiper.domain.api;

import java.util.List;

public record MealDetailsResponse(List<MealDetailsDbDto> meals) {}
