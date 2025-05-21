package ch.janishuber.recipeswiper.adapter.rest;

import ch.janishuber.recipeswiper.adapter.persistence.GroupRepository;
import ch.janishuber.recipeswiper.adapter.persistence.RecipeRepository;
import ch.janishuber.recipeswiper.adapter.persistence.UserRepository;
import ch.janishuber.recipeswiper.adapter.rest.dto.RequestJoin;
import ch.janishuber.recipeswiper.domain.Group;
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

    @POST
    @Path("/new/recipe")
    public Response newRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return Response.ok("Recipe saved").build();
    }

    @POST
    @Path("/new/group")
    public Response newGroup(Group group) {
        // Logic to create a new group
        // This is a placeholder implementation
        System.out.println("Creating new group: " + group.groupToken());
        int groupId = groupRepository.save(group);
        return Response.ok("Group created with ID: " + groupId).build();
    }

    @POST
    @Path("/new/user")
    public Response newUser(User user) {
        // Logic to create a new user
        // This is a placeholder implementation
        int userId = userRepository.save(user);
        return Response.ok("User created with ID: " + userId).build();
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
}
