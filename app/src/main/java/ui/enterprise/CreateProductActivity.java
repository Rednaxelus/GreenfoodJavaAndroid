package ui.enterprise;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.greenfoodjava.R;

import java.util.ArrayList;
import java.util.List;

import database.IngredientTable;
import database.ProductTable;
import model.Ingredient;
import ui.loginAndRegistration.LoginActivity;

public class CreateProductActivity extends Activity {
    private IngredientTable ingredientTable;
    private ProductTable productTable;
    private List<Ingredient> productIngredients;
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product);
        ingredientTable = new IngredientTable(this);
        productTable = new ProductTable(this);
        productIngredients = new ArrayList<>();
        setScrollViewElements();
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back_button);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EnterpriseHomeActivity.class)));
    }

    private void setIngredientErrorInvisible(){
        TextView textView = findViewById(R.id.ingredientsErrorLbl);
        textView.setVisibility(TextView.INVISIBLE);
    }

    private void setIngredientErrorVisible(){
        TextView textView = findViewById(R.id.ingredientsErrorLbl);
        textView.setVisibility(TextView.VISIBLE);
    }

    private void setScrollViewElements() {
        List<Ingredient> ingredients = ingredientTable.getIngredients();
        ScrollView scrollView = findViewById(R.id.scroll);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);

        for (Ingredient ingredient : ingredients){
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked){
                    productIngredients.add(ingredientTable.getIngredient(buttonView.getText().toString()));
                }else{
                    for (Ingredient dishIngredient : productIngredients){
                        if (dishIngredient.getName().equals(buttonView.getText().toString())){
                            productIngredients.remove(dishIngredient);
                            break;
                        }
                    }
                }
            });
            cb.setText(ingredient.getName());
            ll.addView(cb);
        }
    }

    public void createProduct(View view) {
        if(allFieldsAreCompleted()){
            createEntryInDB();
            startActivity(new Intent(this, EnterpriseHomeActivity.class));
        }
    }

    private void createEntryInDB() {
        String name = getField(R.id.productName);
        String description = getField(R.id.productDescription);
        double price = Double.parseDouble(getField(R.id.productPrice));
        int stock = Integer.parseInt(getField(R.id.productStock));
        if (productTable.addProduct(name, description, price, stock, productIngredients, sharedpreferences.getInt("id",-1)))
            System.out.println("SE HA CREADO EL PRODUCT:" + name + " LOLOLO");
    }

    private boolean allFieldsAreCompleted() {
        return isProductNameCompleted() & isProductDescriptionCompleted() &
                isPriceCompleted() & isStockCompleted() & areIngredientsSelected();
    }

    private boolean areIngredientsSelected() {
        if (productIngredients.size() == 0){
            setIngredientErrorVisible();
            return false;
        }
        setIngredientErrorInvisible();
        return true;
    }

    private boolean isPriceCompleted() {
        String price = getField(R.id.productPrice);
        if (price.isEmpty()){
            showErrorFor(R.id.productPrice, "Price is needed");
            return false;
        }
        return isNumber(price);
    }

    private boolean isStockCompleted() {
        String stock = getField(R.id.productStock);
        if (stock.isEmpty()){
            showErrorFor(R.id.productStock, "Stock is needed");
            return false;
        }
        return isNumber(stock);
    }

    private boolean isNumber(String price) {
        try{
            Double.parseDouble(price);
        }catch (Exception e){
            showErrorFor(R.id.productPrice, "Price must be a number");
            return false;
        }
        return true;
    }

    private boolean isProductNameCompleted() {
        String name = getField(R.id.productName);
        if (name.isEmpty()){
            showErrorFor(R.id.productName, "Name is needed");
            return false;
        }
        return true;
    }

    private boolean isProductDescriptionCompleted() {
        String description = getField(R.id.productDescription);
        if (description.isEmpty()){
            showErrorFor(R.id.productDescription, "Description is needed");
            return false;
        }
        return true;
    }


    private String getField(int id){
        TextView field = findViewById(id);
        return field.getText().toString();
    }

    private void showErrorFor(int id, String message){
        TextView field = findViewById(id);
        field.setError(message);
    }
}
