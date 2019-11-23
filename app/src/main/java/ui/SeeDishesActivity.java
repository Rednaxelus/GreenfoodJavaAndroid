package ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.greenfoodjava.R;

public class SeeDishesActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_dishes);
    }

    public void goBack(View view) {
        startActivity(new Intent(this, EnterpriseHomeActivity.class));
    }

}
