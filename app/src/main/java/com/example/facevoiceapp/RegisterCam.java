package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cam);
        nxt_btn = findViewById(R.id.reg_btn);
        detect_btn = findViewById(R.id.detect_btn);
        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(VideoResult result) {
                // A Video was taken!
            }

        });

        nxt_btn.setVisibility(View.GONE);

        detect_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // do something
                // Select output file. Make sure you have write permissions.
                File file = new File(getExternalFilesDir(null),"video_file_name.mp4");
                cameraView.setMode(Mode.VIDEO);
                cameraView.takeVideoSnapshot(file,5000000);
                Log.i("Camera","Video Captured"+ file);
                try {
                    wait(5000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Later... stop recording. This will trigger onVideoTaken().
                //cameraView.stopVideo();

                Toast toast = Toast.makeText(getApplicationContext(), "Face Registration Complete", Toast.LENGTH_SHORT);
                toast.show();

                nxt_btn.setVisibility(View.VISIBLE);
            }
        });

        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterVoice.class);
                //intent.putExtra("USER_ID", sessionId);
                startActivity(intent);
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