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
    TextView match_txt,user_name_txt;
    ProgressBar match_bar;
    String user_name,user_conf_face,user_conf_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        try_btn = findViewById(R.id.try_btn);
        match_txt = findViewById(R.id.match_txt);
        user_name_txt = findViewById(R.id.user_name_txt);
        match_bar = findViewById(R.id.match_bar);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("USER_NAME");
        user_conf_face = intent.getStringExtra("USER_CONF_FACE");
        user_conf_voice = intent.getStringExtra("USER_CONF_VOICE");

        user_name_txt.setText(user_name);
        //Weighted sum of confidence
        //match_txt.setText(user_conf_voice);


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