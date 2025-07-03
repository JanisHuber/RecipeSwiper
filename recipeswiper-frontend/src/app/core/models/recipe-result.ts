import { VoteResult } from "./vote-result";

export interface RecipeResult {
    recipeId: number;
    title: string;
    description: string;
    ingredients: string;
    instructions: string;
    image_url: string;
    voteResults: VoteResult;
}