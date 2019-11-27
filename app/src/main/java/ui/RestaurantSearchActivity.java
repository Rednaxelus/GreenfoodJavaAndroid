package ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.ArrayList;
import java.util.ListIterator;

import database.EnterpriseTable;
import model.Allergy;
import model.Diet;
import model.Restaurant;

public class RestaurantSearchActivity extends Activity {

    private static final int GET_FILTER_REQUEST = 1;
    private ArrayList<Allergy> allergyFilter = null;
    private Diet dietFilter = Diet.ALL;

    public void goBack(View view) {
        startActivity(new Intent(this, UserHomeActivity.class));
    }

    public void searchRestaurant(View view) {
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

            ArrayList<Restaurant> restaurants = filterRestaurants(enterpriseTable.getRestaurantsWithName(query), allergyFilter, dietFilter);

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            for (Restaurant restaurant : restaurants
            ) {
                stringArrayAdapter.add(restaurant.getName() + " " + restaurant.getPhoneNumber() + " " + restaurant.getAddress());
            }
            listView.setAdapter(stringArrayAdapter);
        }
    }

    public void gotToFilterRestaurantsActivity(View view) {
        startActivityForResult(new Intent(this, FilterRestaurantsActivity.class), GET_FILTER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FILTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                allergyFilter = (ArrayList<Allergy>) data.getSerializableExtra("Allergies");
                dietFilter = (Diet) data.getSerializableExtra("Diet");
            }
        }
    }

    private ArrayList<Restaurant> filterRestaurants(ArrayList<Restaurant> restaurants, ArrayList<Allergy> allergies, Diet diet) {

        ListIterator litr = restaurants.listIterator();

        while (litr.hasNext()) {
            Restaurant temp = (Restaurant) litr.next();
            if (temp.hasDishesWithoutTheseAllergies(allergies) || temp.determineDietOfRestaurant().ordinal() < diet.ordinal()) {
                litr.remove();
            }
        }

        return restaurants;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_search);
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