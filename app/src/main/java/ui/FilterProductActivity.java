package ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.greenfoodjava.R;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import model.Allergy;
import model.Diet;

public class FilterProductActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
    }

    private Diet getSelectedDiet() {
        int result = 0;//((Spinner) findViewById(R.id.spinner)).getSelectedItemPosition();
        Diet diet;
        if(result == 1) diet = Diet.VEGETARIAN;
        else if( result == 2) diet = Diet.VEGAN;
        else diet = Diet.ALL;

        return diet;
    }

    private ArrayList<Allergy> getSelectedAllergiesList() {
        ArrayList<Allergy> result = new ArrayList<>();
        /*

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
        }*/
        return result;
    }

    public void goBack(View view) {
        Intent replyIntent = new Intent(this, SearchProductActivity.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}
