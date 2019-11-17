package com.example.greenfoodjava.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.greenfoodjava.R;

public class EnterpriseRegistrationActivity extends Activity {
    private boolean isSwitchChecked;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_registration);
        initSwitchListener();
    }

    private void initSwitchListener() {
        Switch s = findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSwitchChecked = isChecked;
            }
        });
        isSwitchChecked = false;
    }

    public void register(View view) {
        EditText editText = findViewById(R.id.name);
        String message = editText.getText().toString();
        EditText edit2 = findViewById(R.id.address);
        if (isSwitchChecked) {
            edit2.setText("LOLOLO");
        } else {
            edit2.setText("NICE");
        }
        if (message.length() == 0) {
            editText.setText("Está feo");
            return;
        }
    }

    public void goBack(View view) {
        startActivity(new Intent(this, UserRegistrationActivity.class));
    }

}
