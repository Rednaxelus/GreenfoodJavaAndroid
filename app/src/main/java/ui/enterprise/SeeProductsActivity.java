package ui.enterprise;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.List;

import database.ProductTable;
import model.Product;
import ui.loginAndRegistration.LoginActivity;

public class SeeProductsActivity extends Activity {
    private ProductTable productTable;
    private SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_products);
        productTable = new ProductTable(this);
        showProducts();
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EnterpriseHomeActivity.class)));
    }

    private void showProducts() {

        setScrollViewElements();
    }

    private List<Product> getDishesForEnterprise(int enterpriseId) {
        return productTable.getProducts(enterpriseId);
    }

    private void setScrollViewElements() {
        ScrollView scrollView = findViewById(R.id.scroll);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);
        loadScrollView(scrollView,ll);
    }

    private void loadScrollView(ScrollView scrollView, final LinearLayout ll){
        System.out.println("Loading...");
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        List<Product> products = getDishesForEnterprise(sharedpreferences.getInt("id",-1));
        ll.removeAllViews();
        if (products.size() == 0){
            ll.addView(createTextView("You haven't created a product yet"));
        }else{
            for (Product product : products) {
                ll.addView(createTextView(product.getName() + " - " + product.getPrice() + "€"));
                ll.addView(createButtonView(product, scrollView, ll));
            }
        }
    }


    private Button createButtonView(Product product, ScrollView scrollView, LinearLayout ll) {
        Button button = new Button(this);
        button.setText("Delete");
        button.setOnClickListener(e-> {
            deleteProduct(product);
            loadScrollView(scrollView,ll);
        });
        return button;
    }

    private void deleteProduct(Product product) {
        productTable.deleteFromDatabase(product);
    }

    private TextView createTextView(String content) {
        TextView line = new TextView(this);
        line.setText(content);
        line.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        return line;
    }
}
