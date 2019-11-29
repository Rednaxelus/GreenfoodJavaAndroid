package ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.ArrayList;
import java.util.ListIterator;

import database.DishTable;
import model.Allergy;
import model.Diet;
import model.Dish;

public class SearchDishActivity extends Activity {


    private static final int GET_FILTER_REQUEST = 0;
    private ArrayList<Allergy> allergyFilter = null;
    private Diet dietFilter = Diet.ALL;

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

        searchQuery(getNameQuery());
    }

    private String getNameQuery() {
        SearchView searchView = findViewById(R.id.searchView);
        return String.valueOf(searchView.getQuery());
    }

    private void searchQuery(String query) {
        DishTable dishTable = new DishTable(this);

            ArrayList<Dish> dishes = filterDishes(dishTable.getDishesWithName(query), allergyFilter, dietFilter);
            updateListView(dishes);
    }

    private void updateListView(ArrayList<Dish> dishes) {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Dish dish : dishes
        ) {
            stringArrayAdapter.add(dish.getName() + "       " + dish.getPrice() + "€");
        }
        ListView listView = findViewById(R.id.nameSearchList);
        listView.setAdapter(stringArrayAdapter);
    }

    private ArrayList<Dish> filterDishes(ArrayList<Dish> dishes, ArrayList<Allergy> allergies, Diet diet) {

        ListIterator litr = dishes.listIterator();

        while (litr.hasNext()) {
            Dish tempDish = (Dish) litr.next();
            if (tempDish.determineDietOfDish().ordinal() < diet.ordinal()) {
                litr.remove();
            }
            if (allergies != null) {
                for (Allergy allergy : tempDish.getAllergiesOfDish()
                ) {
                    if (allergies.contains(allergy)) {
                        litr.remove();
                        break;
                    }
                }
            }
        }
        return dishes;
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
                dietFilter = (Diet) data.getSerializableExtra("Diet");
                searchQuery(getNameQuery());
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
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserHomeActivity.class)));
        searchQuery("");
    }
}
