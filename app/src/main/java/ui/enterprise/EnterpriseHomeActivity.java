package ui.enterprise;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.greenfoodjava.R;

import database.EnterpriseTable;
import ui.loginAndRegistration.LoginActivity;

public class EnterpriseHomeActivity extends Activity {

    private SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_home);
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt("id", -1);
        EnterpriseTable bd = new EnterpriseTable(this);
        if (bd.isEnterprise(id)) {
            findViewById(R.id.enterpriseCreateDish).setVisibility(View.INVISIBLE);
            findViewById(R.id.enterpriseSeeDish).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.enterpriseCreateProduct).setVisibility(View.INVISIBLE);
            findViewById(R.id.seeProductButton).setVisibility(View.INVISIBLE);
        }
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
