package ch.janishuber.recipeswiper.domain;

import ch.janishuber.recipeswiper.adapter.persistence.Entity.VoteType;

public record VoteResult(int userId, int recipeId, int groupId, VoteType vote) {}
