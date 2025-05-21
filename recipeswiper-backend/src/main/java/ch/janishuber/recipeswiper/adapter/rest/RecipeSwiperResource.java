package ch.janishuber.recipeswiper.adapter.rest;

import ch.janishuber.recipeswiper.adapter.persistence.GroupRecipesRepository;
import ch.janishuber.recipeswiper.adapter.persistence.GroupRepository;
import ch.janishuber.recipeswiper.adapter.persistence.RecipeRepository;
import ch.janishuber.recipeswiper.adapter.persistence.UserRepository;
import ch.janishuber.recipeswiper.adapter.rest.dto.RequestJoin;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ch.janishuber.recipeswiper.domain.Recipe;
import ch.janishuber.recipeswiper.domain.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/recipeswiper")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeSwiperResource {

    @Inject
    private RecipeRepository recipeRepository;
    @Inject
    private GroupRepository groupRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private GroupRecipesRepository groupRecipesRepository;

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
        return Response.ok("Group created with ID: " + groupId + " & with token: " + token).build();
    }

    @POST
    @Path("/new/user")
    public Response newUser(String username) {
        String token = UUID.randomUUID().toString();
        int userId = userRepository.save(username, token);
        return Response.ok("User created with ID: " + userId + " & with token: " + token).build();
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
        int groupId = groupRepository.findGroupByToken(requestJoin.getGroupToken()).getId();
        int userId = userRepository.findUserByToken(requestJoin.getUserToken()).getId();

        boolean success = groupRepository.joinGroup(groupId, userId);
        if (success) {
            return Response.ok("User joined the group").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to join the group").build();
        }
    }

    @GET
    @Path("/{groupToken}/load/recipes")
    public Response loadRecipes(@PathParam("groupToken") String groupToken) {
        //todo implement dynamic recipe loading
        Optional<Recipe> recipe = recipeRepository.getRecipe(1);
        if (recipe.isPresent()) {
            groupRecipesRepository.addRecipeToGroup(recipe.get().recipeId(), groupToken);
            return Response.ok("Loaded Recipes successfully").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No Recipe found").build();
        }
    }

    @GET
    @Path("/{groupToken}/get/recipes")
    public Response getRecipes(@PathParam("groupToken") String groupToken) {
        List<Recipe> recipes = groupRecipesRepository.getAllRecipes(groupToken);
        if (recipes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No recipes found").build();
        }
        return Response.ok(recipes).build();
    }

    @POST
    @Path("/{groupToken}/like")
    public Response likeRecipe(@PathParam("groupToken") String groupToken, int recipeId) {
        groupRecipesRepository.likeRecipe(recipeId, groupToken);
        return Response.ok("Recipe liked").build();
    }
}
