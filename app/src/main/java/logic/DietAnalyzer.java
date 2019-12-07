package logic;

import java.util.ArrayList;

import model.Diet;
import model.Dish;
import model.Ingredient;

public class DietAnalyzer {

    private final static String[] carnivorous = {"Carne", "Pescado", "Meat"};
    private final static String[] vegetarianAnimalProducts = {"Milk"};

    public static Diet determineDiet(ArrayList<Ingredient> ingredients) {

            for (String test :
                    carnivorous) {
                for (Ingredient ingredient : ingredients
                ) {
                    if (ingredient.getName().contains(test)) {
                        return Diet.ALL;
                    }
                }
            }

            for (String test :
                    vegetarianAnimalProducts) {
                for (Ingredient ingredient : ingredients
                ) {
                    if (ingredient.getName().contains(test)) {
                        return Diet.VEGETARIAN;
                    }
                }
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
