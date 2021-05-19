
package com.example.facevoiceapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.facevoiceapp.pojo.Faceres;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCam extends AppCompatActivity {
    Button nxt_btn, detect_btn;
    CameraView cameraView;
    APIInterface apiInterface;
    String user_name ;
    //private static final int MY_PERMISSION_REQUEST_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cam);
        Log.d("CAM", "Activity started");
        nxt_btn = findViewById(R.id.nxt_btn);
        detect_btn = findViewById(R.id.detect_btn);
        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("USER_NAME");


        nxt_btn.setVisibility(View.GONE);

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(VideoResult result) {
                // A Video was taken!

            }

        });

        detect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make sure you have write permissions.
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                            // might throw error due to typecasting
//                            RegisterCam.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_PERMISSION_REQUEST_ID);
//
//                }

                // already granted, start Recorder
                cameraView.setMode(Mode.VIDEO);
                File file = new File(getExternalFilesDir(null), "face_reg.mp4");
                cameraView.takeVideo(file, 5000000);

                // Later... stop recording. This will trigger onVideoTaken().
                if (cameraView.isTakingVideo()) {
                    cameraView.stopVideo();
                }

                Log.i("Camera", "Video Capturing" + file);

                detect_btn.setVisibility(View.GONE);

                Toast toast = Toast.makeText(getApplicationContext(), "Face Recording Started", Toast.LENGTH_SHORT);
                toast.show();

                nxt_btn.setVisibility(View.VISIBLE);

            }
        });

        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.stopVideo();
                Log.d("Debug","POSTING on API");
                uploadFile(user_name);
                Toast toast = Toast.makeText(getApplicationContext(), "Face Recording Completed", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(getApplicationContext(), RegisterVoice.class);
                intent.putExtra("USER_NAME", user_name);
                startActivity(intent);
                finish();
            }
        });
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("Debug","POSTING on API");
//        uploadFile(user_name);
//    }

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


    //---------------------API------------------------------------

    private void uploadFile(String user_name) {
        // create upload service client
        apiInterface = APIClient.getClient().create(APIInterface.class);

        // to get the actual file
        File file = new File(getExternalFilesDir(null), "face_reg.mp4");
        Log.d("Debug", "uploadFile: LOC "+file);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        file,
                        MediaType.parse("video/*")

                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("face", file.getName(), requestFile);

        // add another part within the multipart request
        RequestBody u_name =
                RequestBody.create(
                        user_name,
                        okhttp3.MultipartBody.FORM
                );

        // finally, execute the request
        Call<ResponseBody> call = apiInterface.uploadFace(body, u_name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                //Faceres faceres = response.body();
                Toast toast = Toast.makeText(getApplicationContext(), "Uploaded on server", Toast.LENGTH_SHORT);
                toast.show();
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast toast = Toast.makeText(getApplicationContext(), "Failed to upload on server", Toast.LENGTH_SHORT);
                toast.show();
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
}