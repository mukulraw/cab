package com.example.faizan.voxox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faizan.voxox.SignUpPOJO.SignupBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class registerActivity extends AppCompatActivity {

    TextView loginText;
    Button register;
    EditText email, phone;

    ProgressBar bar;

    SharedPreferences pref;

    SharedPreferences.Editor edit;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        register = (Button) findViewById(R.id.register);
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        //edit = pref.edit();

        bar = (ProgressBar) findViewById(R.id.progress);
        email = (EditText) findViewById(R.id.emailId);
        phone = (EditText) findViewById(R.id.phn);
        loginText = findViewById(R.id.loginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String e = email.getText().toString();
                final String p = phone.getText().toString();
                if (!TextUtils.isEmpty(e)) {


                    if (!TextUtils.isEmpty(p)) {


                        bar.setVisibility(View.VISIBLE);
                        final Bean b = (Bean) getApplicationContext();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseURL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Allapi cr = retrofit.create(Allapi.class);
                        Call<SignupBean> call = cr.signup(e, p);
                        call.enqueue(new Callback<SignupBean>() {
                            @Override
                            public void onResponse(Call<SignupBean> call, Response<SignupBean> response) {


                                if (Objects.equals(response.body().getStatus(), "1"))
                                {
                                    b.name = response.body().getData().getName();
                                    b.userId = response.body().getData().getUserId();
                                    b.phone = response.body().getData().getPhone();
                                    b.otp = response.body().getData().getOtp();

                                    try {
                                        Log.d("OTP", response.body().getData().getOtp());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    Intent i = new Intent(registerActivity.this, otpActivity.class);
                                    i.putExtra("OTP", response.body().getData().getOtp());
                                    startActivity(i);
                                    finish();

                                    Toast.makeText(registerActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(registerActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                                bar.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<SignupBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);
                            }
                        });


                    } else {
                        phone.setError("Field is Empty");
                        phone.requestFocus();
                    }

                } else {
                    email.setError("Field is Empty");
                    email.requestFocus();
                }
            }
        });
    }
}
