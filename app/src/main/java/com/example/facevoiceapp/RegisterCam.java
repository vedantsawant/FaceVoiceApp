package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;

public class RegisterCam extends AppCompatActivity {
    Button nxt_btn,detect_btn;
    CameraView cameraView;
    //private static final int MY_PERMISSION_REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cam);
        Log.d("CAM","Activity started");
        nxt_btn = findViewById(R.id.nxt_btn);
        detect_btn = findViewById(R.id.detect_btn);
        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("USER_NAME");
        Integer user_id = intent.getIntExtra("USER_AGE",-1);

        nxt_btn.setVisibility(View.GONE);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(VideoResult result) {
                // A Video was taken!

            }

        });

        detect_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // do something
                // Make sure you have write permissions.
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                            // might throw error due to typecasting
//                            RegisterCam.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_PERMISSION_REQUEST_ID);
//
//                } else {

                    // already granted, start Recorder
                    cameraView.setMode(Mode.VIDEO);
                    File file = new File(getExternalFilesDir(null),"face_reg.mp4");
                    cameraView.takeVideo(file,5000000);

//                    try {

//                        wait(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    // Later... stop recording. This will trigger onVideoTaken().
                    if(cameraView.isTakingVideo()){
                        cameraView.stopVideo();
                    }

                    Log.i("Camera","Video Captured"+ file);

                    detect_btn.setVisibility(View.GONE);

                    Toast toast = Toast.makeText(getApplicationContext(), "Face Recording Started", Toast.LENGTH_SHORT);
                    toast.show();

                    nxt_btn.setVisibility(View.VISIBLE);


//                }


            }
        });

        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Face Recording Completed", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(getApplicationContext(), RegisterVoice.class);
                //intent.putExtra("USER_ID", sessionId);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }
}