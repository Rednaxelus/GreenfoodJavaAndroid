package model;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private String name;
    private int amount;
    private String energeticValue;
    private double calories, proteins, carbohydrates, fiber, fat;
    private List<Vitamine> vitamines;

    public Ingredient(String name, int amount, String energeticValue, double calories,
                      double proteins, double carbohydrates, double fiber, double fat) {
        this.name = name;
        this.amount = amount;
        this.energeticValue = energeticValue;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fiber = fiber;
        this.fat = fat;
        vitamines = new ArrayList<>();
    }

    public void addVitamine(Vitamine vitamine){
        if (!vitamines.contains(vitamine)) vitamines.add(vitamine);
    }
}
