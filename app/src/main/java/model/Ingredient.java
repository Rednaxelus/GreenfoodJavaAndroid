package model;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private String name;
    private int amount, ID;
    private String energeticValue;
    private double calories, proteins, carbohydrates, fiber, fat;
    private List<Vitamin> vitamins;
    private List<Allergy> allergies;

    public Ingredient(String name, int amount, int ID, String energeticValue, double calories,
                      double proteins, double carbohydrates, double fiber, double fat) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
        this.energeticValue = energeticValue;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
        this.fat = fat;
        vitamins = new ArrayList<>();
        allergies = new ArrayList<>();
    }

    public void addVitamin(List<Vitamin> vitamin){
        vitamins=vitamin;
    }

    public void addAllergy(List<Allergy> allergy){
        allergies = allergy;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return ID;
    }
}
