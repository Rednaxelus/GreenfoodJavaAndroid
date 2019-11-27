package model;

import java.util.ArrayList;

public class DietAnalyzer {

    private final static String[] carnivorous = {"Carne", "Pescado"};
    private final static String[] vegetarianAnimalProducts = {"Carne", "Pescado"};

    public static Diet determineDiet(ArrayList<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients
        ) {

            for (String test :
                    carnivorous) {
                if (ingredient.getName().contains(test)) {
                    return Diet.ALL;
                }
            }

            for (String test :
                    vegetarianAnimalProducts) {
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
