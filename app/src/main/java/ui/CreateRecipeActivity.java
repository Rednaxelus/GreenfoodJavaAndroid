package ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.greenfoodjava.R;

import java.util.ArrayList;
import java.util.List;

import database.IngredientTable;
import database.RecipeTable;
import model.Ingredient;

public class CreateRecipeActivity extends Activity {

    private IngredientTable ingredientTable;
    private RecipeTable recipeTable;
    private List<Ingredient> recipeIngredients;
    private static final int PICK_PHOTO = 0;
    private String imgPath = "";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_recipe);
        ingredientTable = new IngredientTable(this);
        recipeTable = new RecipeTable(this);
        recipeIngredients = new ArrayList<>();
        setScrollViewElements();
        sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
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
                        recipeIngredients.add(ingredientTable.getIngredient(buttonView.getText().toString()));
                    }else{
                        for (Ingredient dishIngredient : recipeIngredients){
                            if (dishIngredient.getName().equals(buttonView.getText().toString())){
                                recipeIngredients.remove(dishIngredient);
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

    public void createRecipe(View view) {
        if(allFieldsAreCompleted()){
            createEntryInDB();
            startActivity(new Intent(this, EnterpriseHomeActivity.class));
        }
    }

    private void createEntryInDB() {
        String name = getField(R.id.dishName);
        double price = Double.parseDouble(getField(R.id.price));
        if (recipeTable.addRecipe(sharedpreferences.getInt("id",-1),
                getField(R.id.dishName),getField(R.id.description),Integer.parseInt(getField(R.id.durationText)),
                getField(R.id.steps),getField(R.id.pickImage),recipeIngredients))
            System.out.println("SE HA CREADO LOLOLO");
    }

    private boolean allFieldsAreCompleted() {
        return  recipeNameTyped() & descriptionTyped() & durationTyped() &
                stepsAreTyped() & imageIsPicked() & areIngredientsSelected();
    }

    private boolean stepsAreTyped() {
        String steps = getField(R.id.steps);
        if(steps.isEmpty()){
            showErrorFor(R.id.steps, "Steps are needed");
            return false;
        }
        return true;
    }

    private boolean durationTyped() {
        String duration = getField(R.id.durationText);
        if (duration.isEmpty()){
            showErrorFor(R.id.durationText, "Duration is needed");
            return false;
        }
        return isNumber(duration);
    }

    private boolean isNumber(String duration) {
        try{
            Double.parseDouble(duration);
        }catch (Exception e){
            showErrorFor(R.id.durationText, "Duration must be a number");
            return false;
        }
        return true;
    }

    private boolean descriptionTyped() {
        String description = getField(R.id.description);
        if(description.isEmpty()){
            showErrorFor(R.id.description, "Description is needed");
            return false;
        }
        if(description.length()>100){
            showErrorFor(R.id.description, "Description too long");
            return false;
        }
        return true;
    }

    private boolean recipeNameTyped() {
        String name = getField(R.id.recipeName);
        if (name.isEmpty()){
            showErrorFor(R.id.recipeName, "Name is needed");
            return false;
        }
        return true;
    }

    private boolean imageIsPicked() {
        if (recipeIngredients.size() == 0){
            setErrorVisible(R.id.errorMess2);
            return false;
        }
        setErrorInvisible(R.id.errorMess2);
        return true;
    }

    private boolean areIngredientsSelected() {
        if (recipeIngredients.size() == 0){
            setErrorVisible(R.id.errorMess);
            return false;
        }
        setErrorInvisible(R.id.errorMess);
        return true;
    }

    private void setErrorInvisible(int error){
        TextView textView = findViewById(error);
        textView.setVisibility(TextView.INVISIBLE);
    }

    private void setErrorVisible(int error){
        TextView textView = findViewById(error);
        textView.setVisibility(TextView.VISIBLE);
    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent, PICK_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            //Get the column index of MediaStore.Images.Media.DATA
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //Gets the String value in the column
            imgPath = cursor.getString(columnIndex);
            cursor.close();
        }
    }

    public void goBack(View view) {
        startActivity(new Intent(this, EnterpriseHomeActivity.class));
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
