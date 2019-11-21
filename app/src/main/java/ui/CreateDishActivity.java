package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.greenfoodjava.R;

import java.util.ArrayList;
import java.util.List;

import database.DishTable;
import database.IngredientTable;
import model.Ingredient;

public class CreateDishActivity extends Activity {
    private IngredientTable ingredientTable;
    private DishTable dishTable;
    private List<Ingredient> dishIngredients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_dish);
        ingredientTable = new IngredientTable(this);
        dishTable = new DishTable(this);
        dishIngredients = new ArrayList<>();
        setScrollViewElements();
    }

    private void setIngredientErrorInvisible(){
        TextView textView = findViewById(R.id.errorMess);
        textView.setVisibility(TextView.INVISIBLE);
    }

    private void setIngredientErrorVisible(){
        TextView textView = findViewById(R.id.errorMess);
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
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        dishIngredients.add(ingredientTable.getIngredient(buttonView.getText().toString()));
                    }else{
                        for (Ingredient dishIngredient : dishIngredients){
                            if (dishIngredient.getName().equals(buttonView.getText().toString())){
                                dishIngredients.remove(dishIngredient);
                                break;
                            }
                        }
                    }
                }
            });
            cb.setText(ingredient.getName());
            ll.addView(cb);
        }
    }

    public void goBack(View view) {
        startActivity(new Intent(this, EnterpriseHomeActivity.class));
    }

    public void createDish(View view) {
        if(allFieldsAreCompleted()){
            createEntryInDB();
            startActivity(new Intent(this, EnterpriseHomeActivity.class));
        }
    }

    private void createEntryInDB() {
        String name = getField(R.id.dishName);
        double price = Double.parseDouble(getField(R.id.price));
        dishTable.addDish(name, price, dishIngredients);
    }

    private boolean allFieldsAreCompleted() {
        return isDishNameCompleted() & isPriceCompleted() & areIngredientsSelected();
    }

    private boolean areIngredientsSelected() {
        if (dishIngredients.size() == 0){
            setIngredientErrorVisible();
            return false;
        }
        setIngredientErrorInvisible();
        return true;
    }

    private boolean isPriceCompleted() {
        String price = getField(R.id.price);
        if (price.isEmpty()){
            showErrorFor(R.id.price, "Price is needed");
            return false;
        }
        return isNumber(price);
    }

    private boolean isNumber(String price) {
        try{
            Double.parseDouble(price);
        }catch (Exception e){
            showErrorFor(R.id.price, "Price must be a number");
            return false;
        }
        return true;
    }

    private boolean isDishNameCompleted() {
        String name = getField(R.id.dishName);
        if (name.isEmpty()){
            showErrorFor(R.id.dishName, "Name is needed");
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
