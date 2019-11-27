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

        TextView error = findViewById(R.id.errorLabel);
        error.setVisibility(View.INVISIBLE);
        SearchView searchView = findViewById(R.id.searchView);
        productSearchQuery(String.valueOf(searchView.getQuery()));
    }


    private void productSearchQuery(String query) {
        ProductTable productTable = new ProductTable(this);
        ListView listView = findViewById(R.id.nameSearchList);
        if (query.equals("")) {
            TextView error = findViewById(R.id.errorLabel);
            error.setVisibility(View.VISIBLE);
            listView.setAdapter(new RestNameListAdapter(this, R.layout.product_name_template, null, 0, 1));
        } else {
            ArrayList<Product> products = filterProducts(productTable.getProductWithName(query), allergyFilter, dietFilter);


            Cursor cursor = productTable.searchByName(query);
            RestNameListAdapter adapter = new RestNameListAdapter(this, R.layout.product_name_template, cursor, 0, 1);
            listView.setAdapter(adapter);
        }
    }

    private ArrayList<Product> filterProducts(ArrayList<Product> products, ArrayList<Allergy> allergies, Diet diet) {

        ListIterator litr = products.listIterator();

        while (litr.hasNext()) {
            Product tempProduct = (Product) litr.next();
            if (tempProduct.determineDietOfProduct().ordinal() < diet.ordinal()) {
                litr.remove();
            }
            if (allergies != null) {
                for (Allergy allergy : tempProduct.getAllergiesOfProduct()
                ) {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search);
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
