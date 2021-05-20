package com.example.facevoiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facevoiceapp.pojo.Loginres;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button auth_btn,upload_btn,result_btn;
    CameraView cameraView;
    TextView otp_text,prompt_text;
    ProgressBar loading_bar;
    APIInterface apiInterface;
    int AUTH_STATUS;
    String RESULT_NAME_FACE;
    String RESULT_NAME_VOICE;
    String RESULT_CONF_FACE;
    String RESULT_CONF_VOICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth_btn = findViewById(R.id.authenticate);
        upload_btn = findViewById(R.id.upload_btn);
        result_btn = findViewById(R.id.result_btn);
        otp_text = findViewById(R.id.otp_text);
        prompt_text = findViewById(R.id.prompt_text);
        loading_bar = findViewById(R.id.loading_bar);

        // Hiding buttons and progress bar
        upload_btn.setVisibility(View.GONE);
        result_btn.setVisibility(View.GONE);
        loading_bar.setVisibility(View.GONE);

        //OTP generation
        Random random = new Random();
        String generatedOTP = String.format("%04d", random.nextInt(10000));
        Log.d("Debug", "Generated OTP : " + generatedOTP);
        otp_text.setText(generatedOTP);


        //Camera
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

                prompt_text.setText("Recording");
                // Select output file
                cameraView.setMode(Mode.VIDEO);
                File file = new File(getExternalFilesDir(null),"login.mp4");
                cameraView.takeVideo(file,5000000);

                // Later... stop recording. This will trigger onVideoTaken().
                if (cameraView.isTakingVideo()) {
                    cameraView.stopVideo();
                }

                Log.i("Camera","Video Capturing"+ file);
                auth_btn.setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), "Face Recording Started", Toast.LENGTH_SHORT);
                toast.show();

                upload_btn.setVisibility(View.VISIBLE);


            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.stopVideo();
                Log.d("Debug","POSTING on API");
                uploadLoginFile(generatedOTP);
                Toast toast = Toast.makeText(getApplicationContext(), "Face Recording Completed", Toast.LENGTH_SHORT);
                toast.show();
                upload_btn.setVisibility(View.GONE);
                loading_bar.setVisibility(View.VISIBLE);
                prompt_text.setText("Please Wait");

                //Upload successful show result button

                //Upload fails send back to main activity
                if(AUTH_STATUS == -1){
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Login Failed Try Again", Toast.LENGTH_SHORT);
                    toast2.show();
                    Intent intentfail = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentfail);
                }

            }
        });

        result_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Send to result screen
                    Intent intent = new Intent(getApplicationContext(), ResultScreen.class);
                    intent.putExtra("USER_NAME", RESULT_NAME_FACE);
                    intent.putExtra("USER_CONF_FACE", RESULT_CONF_FACE);
                    intent.putExtra("USER_CONF_VOICE", RESULT_CONF_VOICE);
                    startActivity(intent);

            }
        });

    }

    private void uploadLoginFile(String generatedOTP) {
        // create upload service client
        apiInterface = APIClient.getClient().create(APIInterface.class);


        // to get the actual file
        File file = new File(getExternalFilesDir(null), "login.mp4");
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
        RequestBody post_otp =
                RequestBody.create(
                        generatedOTP,
                        okhttp3.MultipartBody.FORM
                );

        // finally, execute the request
        Call<ResponseBody> call = apiInterface.uploadLogin(body, post_otp);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                //String responseString = response.body().toString();
                Gson gson = new Gson();
                Loginres loginres = gson.fromJson(response.body().charStream(),Loginres.class);
                RESULT_NAME_FACE = loginres.getFace().getName();
                RESULT_CONF_FACE = loginres.getFace().getConf();
                RESULT_NAME_VOICE = loginres.getVoice().getName();
                RESULT_CONF_VOICE = loginres.getVoice().getConf();

                Toast toast = Toast.makeText(getApplicationContext(), "Uploaded on server", Toast.LENGTH_SHORT);
                toast.show();
                Log.v("Upload", "success");
                AUTH_STATUS = 1;
                loading_bar.setVisibility(View.GONE);
                prompt_text.setVisibility(View.GONE);
                result_btn.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

//                Toast toast = Toast.makeText(getApplicationContext(), "Failed to upload on server", Toast.LENGTH_SHORT);
//                toast.show();
                Log.e("Upload error:", t.getMessage());
                AUTH_STATUS = -1;
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