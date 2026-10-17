package com.example.smartpantrymanager;

import java.util.List;

public class Recipe {
    private String title;
    private List<String> requiredIngredients;

    public Recipe(String title, List<String> requiredIngredients){
        this.title = title;
        this.requiredIngredients = requiredIngredients;
    }
    public String getTitle(){
        return title;
    }

    public List<String> getRequiredIngredients() {
        return requiredIngredients;
    }
}
