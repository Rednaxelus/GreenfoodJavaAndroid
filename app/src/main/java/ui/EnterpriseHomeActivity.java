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


}
