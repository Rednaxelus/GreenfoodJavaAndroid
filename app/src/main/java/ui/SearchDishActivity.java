package ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.ArrayList;

import database.DishTable;
import model.Allergy;

public class SearchDishActivity extends Activity {


    private static final int GET_FILTER_REQUEST = 0;
    ArrayList<Allergy> allergyFilter = null;

    public void goBack(View view) {
        startActivity(new Intent(this, UserHomeActivity.class));
    }

    public void searchDishName(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        TextView error = findViewById(R.id.errorLabel);
        error.setVisibility(View.INVISIBLE);
        SearchView searchView = findViewById(R.id.searchView);
        searchQuery(String.valueOf(searchView.getQuery()));
    }


    private void searchQuery(String query) {
        DishTable dishTable = new DishTable(this);
        ListView listView = findViewById(R.id.nameSearchList);
        if (query.equals("")) {
            TextView error = findViewById(R.id.errorLabel);
            error.setVisibility(View.VISIBLE);
            listView.setAdapter(new RestNameListAdapter(this, R.layout.dish_name_template, null, 0, 1));
        } else {
            Cursor cursor;
            if (allergyFilter == null) {
                cursor = dishTable.searchByName(query);
            } else {
                cursor = dishTable.searchByNameAndFilter(query, allergyFilter);
            }
            //System.out.println("Allergiesssss" + dishTable.getAllergiesOfDish(cursor.getInt(0)));
            RestNameListAdapter adapter = new RestNameListAdapter(this, R.layout.dish_name_template, cursor, 0, 1);
            listView.setAdapter(adapter);
        }
    }

    public void gotToFilterDishesActivity(View view) {
        startActivityForResult(new Intent(this, FilterDishesActivity.class), GET_FILTER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FILTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                allergyFilter = (ArrayList<Allergy>) data.getSerializableExtra("Allergies");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_search);
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
}
