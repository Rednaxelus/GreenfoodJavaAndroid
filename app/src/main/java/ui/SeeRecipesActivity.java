package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.greenfoodjava.R;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import database.RecipeTable;
import model.Recipe;

public class SeeRecipesActivity extends Activity {
    private RecipeTable recipeTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_recipes);
        recipeTable = new RecipeTable(this);
        showRecipes();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
            }
        });
    }

    private void showRecipes() {
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        List<Recipe> recipes = getRecipiesForUser(sharedpreferences.getInt("id",-1));
        setScrollViewElements(recipes);
    }

    private List<Recipe> getRecipiesForUser(int userId) {
        System.out.println("user id = " + userId);
        return recipeTable.getRecipes(userId);
    }

    private void setScrollViewElements(List<Recipe> recipes) {
        ScrollView scrollView = findViewById(R.id.scroll);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);

        if (recipes.size() == 0){
            ll.addView(createTextView("You haven't created a recipe yet"));
        }else{
            for (Recipe recipe : recipes)
                ll.addView(createTextView(recipe.getName() + ": " + recipe.getDescription()));
        }
    }

    private TextView createTextView(String content) {
        TextView line = new TextView(this);
        line.setText(content);
        line.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        return line;
    }
}
