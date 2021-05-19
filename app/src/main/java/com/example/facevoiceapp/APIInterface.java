package com.example.facevoiceapp;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    //Voice registration
    @Multipart
    @POST("regvoice")
    Call<ResponseBody> uploadVoice(

            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3,
            @Part MultipartBody.Part file4,
            @Part MultipartBody.Part file5,
            @Part MultipartBody.Part file6,
            @Part MultipartBody.Part file7,
            @Part MultipartBody.Part file8,
            @Part MultipartBody.Part file9,
            @Part MultipartBody.Part file0,
            @Part("name") RequestBody name

    );

    //Face registration
    @Multipart
    @POST("regface")
    Call<ResponseBody> uploadFace(
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name);

    //Login
    @Multipart
    @POST("voice_url_here")
    Call<ResponseBody> uploadLogin(
            @Part MultipartBody.Part face_vid);

}
