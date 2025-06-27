import { VoteType } from "../VoteType";

export interface VoteRequest {
    userToken: string;
    recipeId: number;
    voteType: VoteType;
}

