package com.example.faizan.voxox;



import com.example.faizan.voxox.EstimatedTimePOJO.TimeBean;
import com.example.faizan.voxox.NearByPOJO.NearByBean;
import com.example.faizan.voxox.SignInPOJO.SignInBean;
import com.example.faizan.voxox.SignUpPOJO.SignupBean;
import com.example.faizan.voxox.SocialPOJO.SocialBean;
import com.example.faizan.voxox.VerifyOtpPOJO.VerifyBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by USER on 11/30/2017.
 */

public interface Allapi {

    @Multipart
    @POST("cab/api/sign_up.php")
    Call<SignupBean> signup(
            @Part("email") String m,
            @Part("phone") String P

    );

    @Multipart
    @POST("cab/api/varify_code.php")
    Call<VerifyBean> otp(
            @Part("otp") String mo,
            @Part("userId") String Po
    );


    @Multipart
    @POST("cab/api/mobile_signin.php")
    Call<SignInBean> signin(
            @Part("phone") String mo
    );


    @Multipart
    @POST("cab/api/socialsign_up.php")
    Call<SocialBean> social(
            @Part("email") String mo,
            @Part("pid") String mp,
            @Part("name") String mn

    );

    @Multipart
    @POST("cab/api/nearbycab.php")
    Call<NearByBean> nearBy(
            @Part("userId") String mo,
            @Part("latitude") String mp,
            @Part("longitude") String mn

    );

    @Multipart
    @POST("cab/api/estimateTime.php")
    Call<TimeBean> time(
            @Part("userId") String mo,
            @Part("pickupLat") String mp,
            @Part("pickupLong") String mn,
            @Part("cabType") String m

    );

}
