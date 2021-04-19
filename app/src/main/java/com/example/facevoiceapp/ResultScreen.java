package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {
    Button try_btn;
    TextView match_txt;
    ProgressBar match_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        try_btn = findViewById(R.id.try_btn);
        match_txt = findViewById(R.id.match_txt);
        match_bar = findViewById(R.id.match_bar);

        //match details
        int mth = 70;
        //char mth_txt = ;

        //set progress
        match_bar.setProgress(mth);

        match_txt.setText("70 % Match");

        //return to home
        try_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("USER_ID", sessionId);
                startActivity(intent);
            }
        });
    }
}