package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenfoodjava.R;

import database.AllergyTable;
import database.DishIngredientTable;
import database.DishTable;
import database.EnterpriseTable;
import database.IngredientTable;
import database.ProductTable;
import database.UserTable;
import model.Allergy;
import model.Ingredient;
import model.Product;
import ui.loginAndRegistration.UserRegistrationActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        EnterpriseTable enterpriseTable = new EnterpriseTable(this);
        UserTable userTable = new UserTable(this);
        IngredientTable ingredientTable = new IngredientTable(this);
        AllergyTable allergyTable = new AllergyTable(this);
        ProductTable productTable = new ProductTable(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, UserRegistrationActivity.class));
            }
        }, 1000);
    }
}
