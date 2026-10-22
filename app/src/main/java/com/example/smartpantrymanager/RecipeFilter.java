package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeFilter {

    // Holds both result lists so the caller can use them
    public static class Result {
        public final List<Recipe> strictMatches = new ArrayList<>();
        public final List<Recipe> almostThereMatches = new ArrayList<>();
    }

    public static Result filterRecipes(List<Recipe> masterRecipeList, List<String> userIngredients) {

        // Normalise the pantry once: lowercase and trimmed
        Set<String> have = new HashSet<>();
        for (String s : userIngredients) have.add(s.trim().toLowerCase());

        Result result = new Result();

        for (Recipe recipe : masterRecipeList) {
            int missingCount = 0;
            for (String ingredient : recipe.getRequiredIngredients()) {
                if (!have.contains(ingredient.trim().toLowerCase())) {
                    missingCount++;
                    if (missingCount > 1) break;
                }
            }

            if (missingCount == 0) {
                result.strictMatches.add(recipe);
            } else if (missingCount == 1) {
                result.almostThereMatches.add(recipe);
            }
        }
        return result;
    }
}