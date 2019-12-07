package ui.user;

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

import model.Allergy;
import model.Diet;

public class FilterRestaurantsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Restaurant Filter");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
    }

    public void gotToSearchRestaurantsActivity(View view) {
        Intent replyIntent = new Intent(this, SearchRestaurantActivity.class);
        replyIntent.putExtra("Allergies", getSelectedAllergiesList());
        replyIntent.putExtra("Diet", getSelectedDiet());
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private Diet getSelectedDiet() {
        int result = ((Spinner) findViewById(R.id.spinner)).getSelectedItemPosition();
        switch (result) {
            case 1:
                return Diet.VEGETARIAN;
            case 2:
                return Diet.VEGAN;
            case 0:
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
        if (((CheckBox) findViewById(R.id.checkBoxEggs)).isChecked()) {
            result.add(Allergy.EGGS);
        }
        if (((CheckBox) findViewById(R.id.checkBoxGluten)).isChecked()) {
            result.add(Allergy.GLUTEN);
        }
        if (((CheckBox) findViewById(R.id.checkBoxSoy)).isChecked()) {
            result.add(Allergy.SOY);
        }
        if (((CheckBox) findViewById(R.id.checkBoxWheat)).isChecked()) {
            result.add(Allergy.WHEAT);
        }
        return result;
    }

    public void goBack(View view) {
        Intent replyIntent = new Intent(this, SearchRestaurantActivity.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

}
