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

import database.EnterpriseTable;

public class RestaurantNameSearchActivity extends Activity {


    public void goBack(View view) {
        startActivity(new Intent(this, UserHomeActivity.class));
    }

    public void searchRestaurantName(View view) {
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
        TextView label = findViewById(R.id.diet_label);
        SearchView searchView = findViewById(R.id.searchView);
        restaurantSearchQuery(String.valueOf(searchView.getQuery()));
    }


    private void restaurantSearchQuery(String query){
        EnterpriseTable enterpriseTable = new EnterpriseTable(this);
        ListView listView = findViewById(R.id.nameSearchList);
        if(query.equals("")){
            TextView error = findViewById(R.id.errorLabel);
            error.setVisibility(View.VISIBLE);
            listView.setAdapter(new RestNameListAdapter(this, R.layout.restaurant_name_template, null, 0, 0));
        }else {
            Cursor restaurants = enterpriseTable.searchByRestaurantName(query);
            TextView label = findViewById(R.id.diet_label);
            RestNameListAdapter adapter = new RestNameListAdapter(this, R.layout.restaurant_name_template, restaurants, 0, 0);
            listView.setAdapter(adapter);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_name_search);
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
}
