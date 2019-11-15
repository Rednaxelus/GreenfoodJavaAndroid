package com.example.greenfoodjava.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.greenfoodjava.R;

public class EnterpriseRegistrationActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterprise_registration);
    }

    public void register(View view) {
        EditText editText = (EditText) findViewById(R.id.name);
        String message = editText.getText().toString();
        if (message.length() == 0) {
            editText.setText("Está feo");
            return;
        }
    }

}
