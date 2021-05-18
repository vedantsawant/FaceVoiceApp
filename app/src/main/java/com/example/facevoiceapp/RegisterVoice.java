package com.example.facevoiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.cleveroad.audiovisualization.GLAudioVisualizationView;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import omrecorder.*;

public class RegisterVoice extends AppCompatActivity {
    Button rec_btn,fin_btn,stop_btn;
    ProgressBar progress_bar;
    TextView number;
    Recorder recorder;
    static int cnt = 0;
    // ID to identify permission request
    private static final int MY_PERMISSION_REQUEST_ID = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;

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

        //recorder


        //hide finish btn initially
        fin_btn.setVisibility(View.GONE);


        rec_btn.setOnClickListener(new View.OnClickListener() {
            //int cnt = 0;
            @Override
            public void onClick(View v) {
                //record Audio
                // Check if permission is granted
                setupRecorder(cnt);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            // might throw error due to typecasting
                            RegisterVoice.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_ID);

                } else {
                    //number changing
                    String st =String.valueOf(cnt);
                    number.setText(st);

                    // already granted, start OmRecorder
                    recorder.startRecording();
                    Log.d("[INFO]", "onClick: Recording started");
                    stop_btn.setEnabled(true);
                    rec_btn.setEnabled(false);

                    //skipSilence.setEnabled(false);
                }



            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stop record Audio
                try {
                    recorder.stopRecording();
                    Log.d("[INFO]", "onClick: Recording stopped");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                stop_btn.setEnabled(false);
                rec_btn.setEnabled(true);
                Log.d("[INFO]", "onClick: Recording started");

                Toast.makeText(getApplicationContext(), "Recording Completed",
                        Toast.LENGTH_LONG).show();

                cnt+= 1;
                progress_bar.incrementProgressBy(10);

                if(cnt == 10){
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

    // Override in Activity to check the request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted, start OmRecorder
                    recorder.startRecording();
                    //skipSilence.setEnabled(false);
                }
                return;
            }
        }
    }

    private void setupRecorder(int num) {
        recorder = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file(num));

        Log.d("[INFO]","Recorded audio".concat(String.valueOf(num))+ file(num));
    }

//    private void setupNoiseRecorder() {
//        recorder = OmRecorder.wav(
//                new PullTransport.Noise(mic(),
//                        new PullTransport.OnAudioChunkPulledListener() {
//                            @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
//                                animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
//                            }
//                        },
//                        new WriteAction.Default(),
//                        new Recorder.OnSilenceListener() {
//                            @Override public void onSilence(long silenceTime) {
//                                Log.e("silenceTime", String.valueOf(silenceTime));
//                                Toast.makeText(getApplicationContext(), "silence of " + silenceTime + " detected",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }, 200
//                ), file()
//        );
//    }

    private void animateVoice(final float maxPeak) {
        rec_btn.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }

    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 44100
                )
        );
    }

    @NonNull private File file(int n) {
        String filename = String.valueOf(n).concat(".wav");
        Log.d("[INFO]","Saving File!!!");

        return new File(getExternalFilesDir(null), filename);

    }


}