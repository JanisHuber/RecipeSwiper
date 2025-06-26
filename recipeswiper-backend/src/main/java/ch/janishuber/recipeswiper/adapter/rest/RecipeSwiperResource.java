package ch.janishuber.recipeswiper.adapter.rest;

import ch.janishuber.recipeswiper.adapter.mealdb.RecipeApi;
import ch.janishuber.recipeswiper.adapter.persistence.*;
import ch.janishuber.recipeswiper.adapter.persistence.entity.GroupEntity;
import ch.janishuber.recipeswiper.adapter.persistence.entity.UserEntity;
import ch.janishuber.recipeswiper.adapter.rest.dto.RecipeResult;
import ch.janishuber.recipeswiper.adapter.rest.dto.RequestCreateUser;
import ch.janishuber.recipeswiper.adapter.rest.dto.RequestJoin;
import ch.janishuber.recipeswiper.adapter.rest.dto.VoteRequest;
import ch.janishuber.recipeswiper.application.RecipeService;
import ch.janishuber.recipeswiper.domain.*;
import ch.janishuber.recipeswiper.domain.api.Tags;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@Path("/recipeswiper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipeSwiperResource {
    private static final Logger LOGGER = Logger.getLogger(RecipeSwiperResource.class.getName());

    @Inject
    RecipeService recipeService;
    @Inject
    private RecipeApi recipeApi;
    @Inject
    private RecipeRepository recipeRepository;
    @Inject
    private GroupRepository groupRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private GroupRecipesRepository groupRecipesRepository;
    @Inject
    private RecipeVotesRepository recipeVotesRepository;

    @POST
    @Path("/new/recipe")
    public Response newRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return Response.ok("Recipe saved").build();
    }

    @GET
    @Path("/recipes/{recipeId}")
    public Response getRecipe(@PathParam("recipeId") int recipeId) {
        Optional<Recipe> recipe = recipeRepository.getRecipe(recipeId);
        if (recipe.isPresent()) {
            return Response.ok(recipe.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Recipe not found").build();
        }
    }

    @POST
    @Path("/new/group")
    @Consumes("text/plain")
    public Response newGroup(String name) {
        String token = UUID.randomUUID().toString();
        int groupId = groupRepository.save(token, name);
        Group group = new Group(groupId, token, name);
        return Response.ok(group).build();
    }

    @POST
    @Path("/new/user")
    public Response newUser(RequestCreateUser requestCreateUser) {
        String token = UUID.randomUUID().toString();
        int userId = userRepository.save(requestCreateUser.username(), token);
        User user = new User(userId, requestCreateUser.username(), token);
        return Response.ok(user).build();
    }

    @GET
    @Path("/{userToken}/user")
    public Response getUser(@PathParam("userToken") String userToken) {
        Optional<User> user = userRepository.getUser(userToken);
        if (user.isPresent()) {
            return Response.ok(user.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }

    @POST
    @Path("/join")
    public Response joinGroup(RequestJoin requestJoin) {
        GroupEntity group = groupRepository.findGroupByToken(requestJoin.getGroupToken());
        UserEntity user = userRepository.findUserByToken(requestJoin.getUserToken());
        if (group == null || user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Group not found").build();
        }

        boolean success = groupRepository.joinGroup(group.getId(), user.getId());
        if (success) {
            return Response.ok("User joined the group").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to join the group").build();
        }
    }

    @GET
    @Path("/{groupToken}/load/recipes")
    public Response loadRecipes(@PathParam("groupToken") String groupToken) {
        List<Tags> tags = ResultHelpers.getFavoriteCategories(groupToken, groupRecipesRepository, recipeVotesRepository);
        recipeApi.getRecipesByTags(tags).forEach(recipe -> {
            int recipeId;
            recipeId = recipeService.save(recipe);
            groupRecipesRepository.addRecipeToGroup(recipeId, groupToken);
        });
        return Response.ok("Recipes loaded into group").build();
    }

    @GET
    @Path("/groups/{groupToken}/get/results")
    public Response getRecipes(@PathParam("groupToken") String groupToken) {
        LOGGER.info("fetching results for group: " + groupToken);
        List<Recipe> recipes = groupRecipesRepository.getAllRecipes(groupToken);
        if (recipes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No recipes found").build();
        }

        List<RecipeResult> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            List<VoteResult> voteOfRecipe = recipeVotesRepository.getRecipeVoteFromGroup(groupToken, recipe.recipeId());
            System.out.println("Votes for recipe " + recipe.title() + ": " + voteOfRecipe);
            if (voteOfRecipe.isEmpty()) {
                System.out.println("Recipe got skipped due to no votes");
                continue;
            }
            RecipeResult recipeResult = RecipeResult.ofRecipe(recipe, voteOfRecipe);
            results.add(recipeResult);
        }

        List<RecipeResult> sortedResults = ResultHelpers.sortMatchesFromResults(results);
        return Response.ok(sortedResults).build();
    }

    @GET
    @Path("/groups/{groupToken}/{userToken}/get/recipes")
    public Response getRecipesByGroup(@PathParam("groupToken") String groupToken,
                                      @PathParam("userToken") String userToken) {
        List<Recipe> recipes = groupRecipesRepository.getAllRecipesForUser(groupToken, userToken);
        if (recipes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No recipes found").build();
        }
        return Response.ok(recipes).build();
    }

    @GET
    @Path("/get/recipes")
    public Response getAllRecipes() {
        List<Recipe> recipes = recipeRepository.getAllRecipes();
        if (recipes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No recipes found").build();
        }
        return Response.ok(recipes).build();
    }

    @POST
    @Path("/{groupToken}/vote")
    public Response voteRecipe(@PathParam("groupToken") String groupToken, VoteRequest voteRequest) {
        try {
            recipeVotesRepository.saveVote(voteRequest.recipeId(), voteRequest.userToken(), groupToken,
                    voteRequest.voteType());
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok("Recipe " + voteRequest.voteType().toString().toLowerCase() + "d").build();
    }

    @GET
    @Path("/{userToken}/get/groups")
    public Response getGroups(@PathParam("userToken") String userToken) {
        Optional<User> user = userRepository.getUser(userToken);
        if (user.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        List<Group> groups = groupRepository.getAllGroupsForUser(user.get().id());
        if (groups.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No groups found for user").build();
        }
        return Response.ok(groups).build();
    }

    @GET
    @Path("/groups/{groupToken}")
    public Response getGroup(@PathParam("groupToken") String groupToken) {
        GroupEntity groupEntity = groupRepository.findGroupByToken(groupToken);
        if (groupEntity != null) {
            Group group = new Group(groupEntity.getId(), groupEntity.getGroup_token(), groupEntity.getName());
            return Response.ok(group).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Group not found").build();
        }
    }
}