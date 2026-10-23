package com.example.smartpantrymanager;

import java.util.List;

public class Recipe {

    private int id;
    private final String name;
    private final List<String> requiredIngredients;
    private final String steps;
    private final String imageName;

    public Recipe(String name, List<String> requiredIngredients,
                  String steps, String imageName) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.steps = steps;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRequiredIngredients() {
        return requiredIngredients;
    }

    public String getSteps() {
        return steps;
    }

    public String getImageName() {
        return imageName;
    }
}