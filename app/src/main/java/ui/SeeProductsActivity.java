package ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.greenfoodjava.R;

import database.ProductTable;

public class SeeProductsActivity extends Activity {
    private ProductTable productTable;
    private SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_products);
        //ingredientTable = new IngredientTable(this);
        productTable = new ProductTable(this);
        //productIngredients = new ArrayList<>();
        //setScrollViewElements();
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);

    }
}
