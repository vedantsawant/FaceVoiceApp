package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    Button cont_btn;
    EditText name,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        cont_btn = findViewById(R.id.cont_btn);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);

        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterCam.class);
                String user_name = name.getText().toString();
                int user_age = Integer.parseInt(age.getText().toString());
                intent.putExtra("USER_NAME", user_name);
                intent.putExtra("USER_AGE", user_age);
                startActivity(intent);
            }
        });
    }

}