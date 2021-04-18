package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton auth_btn;
    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth_btn = findViewById(R.id.authenticate);
        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(VideoResult result) {
                // A Video was taken!
            }

        });

        auth_btn.setOnClickListener(new View.OnClickListener(){
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

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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