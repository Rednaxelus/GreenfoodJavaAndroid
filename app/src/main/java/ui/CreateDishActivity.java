package ui;

import android.app.Activity;
import android.os.Bundle;

import com.example.greenfoodjava.R;

public class CreateDishActivity extends Activity {
    /**
     * Añadir receta
     * List <Ingredientes>
     *
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_dish);
    }
}
