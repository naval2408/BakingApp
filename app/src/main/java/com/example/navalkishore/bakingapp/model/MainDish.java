package com.example.navalkishore.bakingapp.model;

import java.io.Serializable;
import java.util.List;

public class MainDish implements Serializable{
    private Double id;

    public Double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public Double getServings() {
        return servings;
    }

    private String name;
    private List<Ingredient> ingredients;
    private List<Steps> steps;
    private Double servings;

    public String getImage() {
        return image;
    }

    private String image;

}
