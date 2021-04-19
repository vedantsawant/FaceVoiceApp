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
        Editable user_name = name.getText();
        Editable user_age = age.getText();
        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterCam.class);
                intent.putExtra("Name", user_name);
                intent.putExtra("Age", user_age);
                startActivity(intent);
            }
        });
    }

}