package model;

import java.util.List;

public class Recipe {

    private int ID;
    private String name;
    private String description;
    private String steps;
    private int duration;
    private List<Ingredient> ingredients;

    public Recipe(int ID, String name, String description, String steps, int duration, List<Ingredient> ingredients) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.steps = steps;
        this.duration = duration;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSteps() {
        return steps;
    }

    public int getDuration() {
        return duration;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
