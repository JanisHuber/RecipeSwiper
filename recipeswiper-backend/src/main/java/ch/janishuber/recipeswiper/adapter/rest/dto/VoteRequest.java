package ch.janishuber.recipeswiper.adapter.rest.dto;

import ch.janishuber.recipeswiper.adapter.persistence.entity.VoteType;

public record VoteRequest(String userToken, int recipeId, VoteType voteType) {
}
