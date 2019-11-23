package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.greenfoodjava.R;
import java.util.List;

import database.DishTable;
import model.Dish;

public class SeeDishesActivity extends Activity {
    private DishTable dishTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_dishes);
        dishTable = new DishTable(this);
        showDishes();
    }

    private void showDishes() {
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        List<Dish> dishes = getDishesForEnterprise(sharedpreferences.getInt("id",-1));
        setScrollViewElements(dishes);
    }

    private List<Dish> getDishesForEnterprise(int enterpriseId) {
        return dishTable.getDishes(enterpriseId);
    }

    private void setScrollViewElements(List<Dish> dishes) {
        ScrollView scrollView = findViewById(R.id.scroll);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);

        if (dishes.size() == 0){
            ll.addView(createTextView("You haven't created a dish yet"));
        }else{
            for (Dish dish : dishes)
                ll.addView(createTextView(dish.getName() + " - " + dish.getPrice() + "€"));
        }
    }

    private TextView createTextView(String content) {
        TextView line = new TextView(this);
        line.setText(content);
        line.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        return line;
    }

    public void goBack(View view) {
        startActivity(new Intent(this, EnterpriseHomeActivity.class));
    }

}
