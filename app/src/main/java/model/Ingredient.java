package model;

import java.util.ArrayList;

public class Ingredient {
    private String name;
    private int amount, ID;
    private String energeticValue;
    private double calories, proteins, carbohydrates, fiber, fat;
    private ArrayList<Vitamin> vitamins;
    private ArrayList<Allergy> allergies;

    public Ingredient(String name, int amount, int ID, String energeticValue, double calories,
                      double proteins, double carbohydrates, double fiber, double fat, ArrayList<Vitamin> vitamins, ArrayList<Allergy> allergies) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
        this.energeticValue = energeticValue;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
        this.fat = fat;
        this.vitamins = vitamins;
        this.allergies = allergies;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return ID;
    }

    public ArrayList<Allergy> getAllergies() {
        return allergies;
    }
}
