package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenfoodjava.R;

import database.DatabaseManager;
import database.DishTable;
import database.IngredientTable;
import database.UserTable;
import ui.loginAndRegistration.UserRegistrationActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        DatabaseManager.createAllTables(new DishTable(this), new UserTable(this), new IngredientTable(this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, UserRegistrationActivity.class));
            }
        }, 1000);
    }
}
