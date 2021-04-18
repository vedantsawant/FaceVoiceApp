package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;

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
                cameraView.setMode(Mode.VIDEO);
                //cameraView.takeVideoSnapshot(file,5000000);

                // Later... stop recording. This will trigger onVideoTaken().
                //cameraView.stopVideo();
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