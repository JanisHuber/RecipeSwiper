package ch.janishuber.recipeswiper.domain;

import ch.janishuber.recipeswiper.adapter.persistence.RecipeRepository;

public class AddTestingRecipes {

    public void addRecipes(RecipeRepository repository) {
        //todo remove description add Tags, Country etc.
        Recipe recipe = new Recipe(0, "Beef Wellington",
                "A classic British dish", "mushrooms-400g, English Mustard-1-2tbsp, Olive Oil-Dash, Beef Fillet-750g piece, Parma ham-6-8 slices, Puff Pastry-500g, Flour-Dusting, Egg Yolks-2 Beaten",
                "Put the mushrooms into a food processor with some seasoning and pulse to a rough paste. Scrape the paste into a pan and cook over a high heat for about 10 mins, tossing frequently, to cook out the moisture from the mushrooms. Spread out on a plate to cool.\r\nHeat in a frying pan and add a little olive oil. Season the beef and sear in the hot pan for 30 secs only on each side. (You don't want to cook it at this stage, just colour it). Remove the beef from the pan and leave to cool, then brush all over with the mustard.\r\nLay a sheet of cling film on a work surface and arrange the Parma ham slices on it, in slightly overlapping rows. With a palette knife, spread the mushroom paste over the ham, then place the seared beef fillet in the middle. Keeping a tight hold of the cling film from the edge, neatly roll the Parma ham and mushrooms around the beef to form a tight barrel shape. Twist the ends of the cling film to secure. Chill for 15-20 mins to allow the beef to set and keep its shape.\r\nRoll out the puff pastry on a floured surface to a large rectangle, the thickness of a £1 coin. Remove the cling film from the beef, then lay in the centre. Brush the surrounding pastry with egg yolk. Fold the ends over, the wrap the pastry around the beef, cutting off any excess. Turn over, so the seam is underneath, and place on a baking sheet. Brush over all the pastry with egg and chill for about 15 mins to let the pastry rest.\r\nHeat the oven to 200C, 400F, gas 6.\r\nLightly score the pastry at 1cm intervals and glaze again with beaten egg yolk. Bake for 20 minutes, then lower the oven setting to 180C, 350F, gas 4 and cook for another 15 mins. Allow to rest for 10-15 mins before slicing and serving with the side dishes of your choice. The beef should still be pink in the centre when you serve it.",
                "https://www.themealdb.com/images/media/meals/vvpprx1487325699.jpg");
        repository.save(recipe);
    }
}

