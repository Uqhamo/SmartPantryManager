package com.example.smartpantrymanager;

import java.util.List;

public class Recipe {
    private final String name;
    private final List<String> requiredIngredients;
    private final String steps;

    public Recipe(String name, List<String> requiredIngredients, String steps) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.steps = steps;
    }

    public String getName() { return name; }
    public List<String> getRequiredIngredients() { return requiredIngredients; }
    public String getSteps() { return steps; }
}
