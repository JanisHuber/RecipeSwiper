package ch.janishuber.recipeswiper.domain;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.VoteType;
import ch.janishuber.recipeswiper.adapter.rest.dto.RecipeResult;

import java.util.Comparator;
import java.util.List;

public class ResultHelpers {

    public static List<RecipeResult> sortMatchesFromResults(List<RecipeResult> recipeResultList) {
        return recipeResultList.stream()
                .sorted(Comparator.comparingInt((RecipeResult recipeResult) -> recipeResult.voteResults().stream()
                        .mapToInt(voteResult -> switch (voteResult.vote()) {
                            case VoteType.LIKE -> 1;
                            case VoteType.DISLIKE -> -1;
                            default -> 0;
                        })
                        .sum()).reversed())
                .toList();
    }
}
