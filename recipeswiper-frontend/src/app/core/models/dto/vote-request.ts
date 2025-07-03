import { VoteType } from '../vote-type';

export interface VoteRequest {
  userToken: string;
  recipeId: number;
  voteType: VoteType;
}
