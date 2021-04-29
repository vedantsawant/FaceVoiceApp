package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.GLAudioVisualizationView;

import java.io.IOException;
import java.util.Random;

public class RegisterVoice extends AppCompatActivity {
    //private AudioVisualization audioVisualization;
    Button rec_btn,fin_btn,stop_btn;
    ProgressBar progress_bar;
    TextView number;
    // voice
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_voice);

        //audioVisualization = (AudioVisualization) GLAudioVisualizationView;

        rec_btn = findViewById(R.id.rec_btn);
        stop_btn = findViewById(R.id.stop_btn);
        fin_btn = findViewById(R.id.fin_btn);
        progress_bar = findViewById(R.id.compl_bar);
        number = findViewById(R.id.num);
        stop_btn.setEnabled(false);

        //hide finish btn initially
        fin_btn.setVisibility(View.GONE);

        rec_btn.setOnClickListener(new View.OnClickListener() {
            int cnt = 0;
            @Override
            public void onClick(View v) {
                //start record Audio


            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            int cnt = 0;
            @Override
            public void onClick(View v) {
                //stop record Audio
                stop_btn.setEnabled(false);
                rec_btn.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording Completed",
                        Toast.LENGTH_LONG).show();

                //number changing
                String st =String.valueOf(cnt);
                number.setText(st);
                cnt+= 1;
                progress_bar.incrementProgressBy(10);

                if(cnt == 9){
                    rec_btn.setVisibility(View.GONE);
                    fin_btn.setVisibility(View.VISIBLE);
                }
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