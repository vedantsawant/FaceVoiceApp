package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.GLAudioVisualizationView;

public class RegisterVoice extends AppCompatActivity {
    //private AudioVisualization audioVisualization;
    Button rec_btn,fin_btn;
    ProgressBar progress_bar;
    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_voice);

        //audioVisualization = (AudioVisualization) GLAudioVisualizationView;

        rec_btn = findViewById(R.id.rec_btn);
        fin_btn = findViewById(R.id.fin_btn);
        progress_bar = findViewById(R.id.compl_bar);
        number = findViewById(R.id.num);

        //hide finish btn initially
        fin_btn.setVisibility(View.GONE);

        rec_btn.setOnLongClickListener(new View.OnLongClickListener() {
            int cnt = 0;
            @Override
            public boolean onLongClick(View v) {

                //record Audio

                //number changing
                String st =String.valueOf(cnt);
                number.setText(st);
                cnt+= 1;
                progress_bar.incrementProgressBy(10);

                if(cnt == 9){
                    rec_btn.setVisibility(View.GONE);
                    fin_btn.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        fin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(getApplicationContext(), "New user added /n Log in to test", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("USER_ID", sessionId);
                startActivity(intent);

            }
        });
    }
}