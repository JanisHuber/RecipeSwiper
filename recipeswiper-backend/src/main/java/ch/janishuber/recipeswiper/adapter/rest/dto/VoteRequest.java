package ch.janishuber.recipeswiper.adapter.rest.dto;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.VoteType;

public record VoteRequest(String userToken, int recipeId, VoteType voteType) {}
