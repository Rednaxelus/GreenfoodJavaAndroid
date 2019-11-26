package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.greenfoodjava.R;

public class UserHomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
    }

    public void searchRestaurantName(View view) {
        startActivity(new Intent(this, RestaurantNameSearchActivity.class));
    }

    public void gotToCreateRecipeActivity(View view) {
        startActivity(new Intent(this, CreateRecipeActivity.class));
    }

    public void gotToSearchDishActivity(View view) {
        startActivity(new Intent(this, SearchDishActivity.class));
    }

    public void gotToSearchProductActivity(View view) {
        startActivity(new Intent(this, SearchProductActivity.class));
    }

    public void gotToSeeRecipesActivity(View view) {
        startActivity(new Intent(this, SeeRecipesActivity.class));
    }
}

