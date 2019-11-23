package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.greenfoodjava.R;

public class UserHomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
    }

    public void gotToCreateRecipeActivity(View view) {
        startActivity(new Intent(this, CreateRecipeActivity.class));
    }
}

