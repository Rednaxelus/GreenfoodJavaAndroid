package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.greenfoodjava.R;

public class EnterpriseHomeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_home);
    }

    public void gotToCreateDishActivity(View view) {
        startActivity(new Intent(this, CreateDishActivity.class));
    }

    public void goToSeeDishesActivity(View view) {
        startActivity(new Intent(this, SeeDishesActivity.class));
    }

    public void gotToCreateProductActivity(View view) {
        startActivity(new Intent(this, CreateProductActivity.class));
    }

    public void goToSeeProductActivity(View view) {
        startActivity(new Intent(this, SeeProductsActivity.class));
    }
}
