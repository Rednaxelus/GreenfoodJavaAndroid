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

import database.ProductTable;
import model.Allergy;
import model.Diet;
import model.Product;

public class SearchProductActivity extends Activity {

    private static final int GET_FILTER_REQUEST = 0;
    private ArrayList<Allergy> allergyFilter = null;
    private Diet dietFilter = Diet.ALL;

    public void searchProductName(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        SearchView searchView = findViewById(R.id.searchView);
        searchQuery(String.valueOf(searchView.getQuery()));
    }

    private void searchQuery(String query) {
        ProductTable productTable = new ProductTable(this);
        ListView listView = findViewById(R.id.nameSearchList);
        ArrayList<Product> products = filterProducts(productTable.getProductWithName(query), allergyFilter, dietFilter);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Product product : products) {
            stringArrayAdapter.add(product.getName() + "        " + product.getPrice() + "€     " + product.getStock());
        }
        listView.setAdapter(stringArrayAdapter);

    }

    private ArrayList<Product> filterProducts(ArrayList<Product> products, ArrayList<Allergy> allergies, Diet diet) {
        ListIterator litr = products.listIterator();
        while (litr.hasNext()) {
            Product tempProduct = (Product) litr.next();
            System.out.println(tempProduct.getName() + " - " + tempProduct.determineDietOfProduct().name());
            if (tempProduct.determineDietOfProduct().ordinal() < diet.ordinal()) {
                litr.remove();
            }
            if (allergies != null) {
                for (Allergy allergy : tempProduct.getAllergiesOfProduct()) {
                    if (allergies.contains(allergy)) {
                        litr.remove();
                        break;
                    }
                }
            }
        }
        return products;
    }

    public void goToFilterProductActivity(View view) {
        startActivityForResult(new Intent(this, FilterProductActivity.class), GET_FILTER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FILTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                allergyFilter = (ArrayList<Allergy>) data.getSerializableExtra("Allergies");
                dietFilter = (Diet) data.getSerializableExtra("Diet");
                SearchView searchView = findViewById(R.id.searchView);
                searchQuery(String.valueOf(searchView.getQuery()));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserHomeActivity.class)));
        searchQuery("");
    }
}