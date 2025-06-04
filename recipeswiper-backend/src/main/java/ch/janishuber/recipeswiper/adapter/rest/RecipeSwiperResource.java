package ch.janishuber.recipeswiper.adapter.rest;

import ch.janishuber.recipeswiper.adapter.persistence.*;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.GroupEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.UserEntity;
import ch.janishuber.recipeswiper.adapter.persistence.Entity.VoteType;
import ch.janishuber.recipeswiper.adapter.rest.dto.RecipeResult;
import ch.janishuber.recipeswiper.adapter.rest.dto.RequestCreateUser;
import ch.janishuber.recipeswiper.adapter.rest.dto.RequestJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ch.janishuber.recipeswiper.domain.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/recipeswiper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipeSwiperResource {

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

    @POST
    @Path("/new/group")
    public Response newGroup() {
        String token = UUID.randomUUID().toString();
        int groupId = groupRepository.save(token);
        Group group = new Group(groupId, token);
        return Response.ok(group).build();
    }

    @POST
    @Path("/new/user")
    public Response newUser(RequestCreateUser requestCreateUser) {
        String token = UUID.randomUUID().toString();
        int userId = userRepository.save(requestCreateUser.username(), token);

        Optional<Group> userGroup = groupRepository.getGroupFromUser(userId);
        User user = userGroup.map(group -> new User(userId, requestCreateUser.username(), token, group.groupToken()))
                .orElseGet(() -> new User(userId, requestCreateUser.username(), token, null));
        return Response.ok(user).build();
    }

    @GET
    @Path("/{userToken}/user")
    public Response getUser(@PathParam("userToken") String userToken) {
        Optional<User> user = userRepository.getUser(userToken);
        if (user.isPresent()) {
            User finalUser = new User(user.get().id(), user.get().username(), user.get().userToken(), null);
            Optional<Group> userGroup = groupRepository.getGroupFromUser(user.get().id());
            if (userGroup.isPresent()) {
                finalUser = new User(user.get().id(), user.get().username(), user.get().userToken(), userGroup.get().groupToken());
            }
            return Response.ok(finalUser).build();
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
        // todo implement dynamic recipe loading
        Optional<Recipe> recipe = recipeRepository.getRecipe(1);
        if (recipe.isPresent()) {
            groupRecipesRepository.addRecipeToGroup(recipe.get().recipeId(), groupToken);
            return Response.ok("Loaded Recipes successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No Recipe found").build();
        }
    }

    @GET
    @Path("/groups/{groupToken}/get/results")
    // todo implement dynamic summary
    public Response getRecipes(@PathParam("groupToken") String groupToken) {
        List<Recipe> recipes = groupRecipesRepository.getAllRecipes(groupToken);
        if (recipes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No recipes found").build();
        }
        List<RecipeResult> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            VoteResult voteOfRecipe = recipeVotesRepository.getRecipeVoteFromGroup(groupToken, recipe.recipeId());
            RecipeResult recipeResult = RecipeResult.ofRecipe(recipe, voteOfRecipe);
            results.add(recipeResult);
        }
        return Response.ok(results).build();
    }

    @GET
    @Path("/groups/{groupToken}/get/recipes")
    public Response getRecipesByGroup(@PathParam("groupToken") String groupToken) {
        List<Recipe> recipes = groupRecipesRepository.getAllRecipes(groupToken);
        if (recipes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No recipes found").build();
        }
        return Response.ok(recipes).build();
    }

    @POST
    @Path("/{groupToken}/{userToken}/vote")
    public Response voteRecipe(@PathParam("groupToken") String groupToken, @PathParam("userToken") String userToken,
            @QueryParam("vote") String voteType, int recipeId) {
        VoteType vote = "DISLIKE".equalsIgnoreCase(voteType) ? VoteType.DISLIKE : VoteType.LIKE;
        recipeVotesRepository.saveVote(recipeId, userToken, groupToken, vote);
        return Response.ok("Recipe " + voteType.toLowerCase() + "d").build();
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
}