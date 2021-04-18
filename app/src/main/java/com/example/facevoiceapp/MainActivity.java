package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button reg_btn,log_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg_btn = findViewById(R.id.reg_btn);
        log_btn = findViewById(R.id.log_btn);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                //intent.putExtra("USER_ID", sessionId);
                startActivity(intent);
            }
        });

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //intent.putExtra("USER_ID", sessionId);
                startActivity(intent);
            }
        });
    }
}