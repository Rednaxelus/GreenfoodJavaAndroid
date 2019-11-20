package ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.greenfoodjava.R;

import java.util.List;

import database.IngredientTable;
import model.Ingredient;

public class CreateDishActivity extends Activity {
    private IngredientTable ingredientTable;
    private List<Ingredient> ingredients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_dish);
        ingredientTable = new IngredientTable(this);
        ingredients = ingredientTable.getIngredients();

        setScrollViewElements();
    }

    private void setScrollViewElements() {
        ScrollView scrollView = findViewById(R.id.scroll);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);

        for (Ingredient ingredient: ingredients){
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(ingredient.getName());
            ll.addView(cb);
        }
    }
}
