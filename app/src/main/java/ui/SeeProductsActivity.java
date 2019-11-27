package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.example.greenfoodjava.R;
import java.util.List;
import database.ProductTable;
import model.Dish;
import model.Product;

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),EnterpriseHomeActivity.class));
            }
        });
    }

    private void showProducts() {
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        List<Product> products = getDishesForEnterprise(sharedpreferences.getInt("id",-1));
        setScrollViewElements(products);
    }

    private List<Product> getDishesForEnterprise(int enterpriseId) {
        return productTable.getProducts(enterpriseId);
    }

    private void setScrollViewElements(List<Product> products) {
        ScrollView scrollView = findViewById(R.id.scroll);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);

        if (products.size() == 0){
            ll.addView(createTextView("You haven't created a product yet"));
        }else{
            for (Product product : products)
                ll.addView(createTextView(product.getName() + " - " + product.getPrice() + "€"));
        }
    }

    private TextView createTextView(String content) {
        TextView line = new TextView(this);
        line.setText(content);
        line.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        return line;
    }
}
