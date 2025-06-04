import { VoteResult } from "./VoteResult";

export interface RecipeResult {
    id: number;
    title: string;
    description: string;
    ingredients: string;
    instructions: string[];
    imageUrl: string;
    voteResult: VoteResult;
}