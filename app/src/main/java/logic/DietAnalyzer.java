package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Diet;
import model.Dish;
import model.Ingredient;

public class DietAnalyzer {

    private final static List<String> carnivorous = Arrays.asList("Meat");
    private final static List<String> vegetarian = Arrays.asList("Milk");

    public static Diet determineDiet(ArrayList<Ingredient> ingredients) {
        for (Ingredient ingredient: ingredients){
            if (carnivorous.contains(ingredient.getName())) return Diet.ALL;
        }

        for (Ingredient ingredient: ingredients){
            if (vegetarian.contains(ingredient.getName())) return Diet.VEGETARIAN;
        }

        return Diet.VEGAN;
    }

    public static Diet determineIncludedDietInDishes(ArrayList<Dish> dishes) {

        Diet result = Diet.ALL;

        for (Dish dish : dishes
        ) {
            Diet temp = determineDiet((ArrayList) dish.getIngredients());
            if (temp.ordinal() > result.ordinal()) {
                result = temp;
                if (result.ordinal() == Diet.VEGAN.ordinal()) {
                    return Diet.VEGAN;
                }
            }
        }

        return result;
    }

}
