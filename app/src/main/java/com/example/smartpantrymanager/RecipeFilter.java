package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class RecipeFilter {

    public static void fiterRecipes(List<Recipe> masterRecipeList, List<String> userIngredients){

        List<Recipe> strictMatches = new ArrayList<>();
        List<Recipe> almostThereMatches = new ArrayList<>();

        for(Recipe recipe : masterRecipeList){
            List<String> requiredIngredient = recipe.getRequiredIngredients();

            if(userIngredients.containsAll(requiredIngredient)){
                strictMatches.add(recipe);
            }
            else{
                int missingCount =0;
                for(String ingrendient: requiredIngredient){
                    if(!userIngredients.contains(ingrendient)){
                        missingCount++;
                    }
                    if(missingCount>1){
                        break;
                    }
                }
                if(missingCount ==1){
                    almostThereMatches.add(recipe);
                }
            }
        }
        displayResults(strictMatches,almostThereMatches);
    }
    private static displayResults(List<Recipe>strict,List<Recipe> almostThere){

    }
}
