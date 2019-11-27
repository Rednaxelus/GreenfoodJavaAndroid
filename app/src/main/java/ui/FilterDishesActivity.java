package ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.ArrayList;

import database.DishTable;
import model.Allergy;
import model.Diet;

public class FilterDishesActivity extends Activity {
    private DishTable dishTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_filter);
        dishTable = new DishTable(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
            }
        });
    }

    public void gotToSearchDishesActivity(View view) {
        Intent replyIntent = new Intent(this, SearchDishActivity.class);
        replyIntent.putExtra("Allergies", getSelectedAllergiesList());
        replyIntent.putExtra("Diet", getSelectedDiet());
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private Diet getSelectedDiet() {
        int result = ((Spinner) findViewById(R.id.spinner)).getSelectedItemPosition();
        switch (result) {
            case 0:
                return Diet.ALL;
            case 1:
                return Diet.VEGETARIAN;
            case 2:
                return Diet.VEGAN;
            default:
                return Diet.ALL;
        }
    }

    private ArrayList<Allergy> getSelectedAllergiesList() {
        ArrayList<Allergy> result = new ArrayList<>();
        if (((CheckBox) findViewById(R.id.checkBoxMilk)).isChecked()) {
            result.add(Allergy.MILK);
        }
        if (((CheckBox) findViewById(R.id.checkBoxPeanuts)).isChecked()) {
            result.add(Allergy.PEANUTS);
        }
        return result;
    }

    public void goBack(View view) {
        startActivity(new Intent(this, EnterpriseHomeActivity.class));
    }

}
