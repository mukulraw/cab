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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faizan.voxox.SignInPOJO.SignInBean;
import com.example.faizan.voxox.SocialPOJO.SocialBean;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class loginActivity extends AppCompatActivity {
    Button login;
    TextView signupText;
    EditText phone;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    private CallbackManager mCallbackManager;
    ImageView facebook;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);

        mCallbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                //signInWithFacebook(loginResult.getAccessToken());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(final JSONObject object, GraphResponse response) {


                        String email, pid, name;

                        try {
                            email = object.getString("email");
                            pid = object.getString("id");
                            name = object.getString("name");
                            Log.d("fbname", name);
                            Log.d("fbemail", email);



                            final Bean b = (Bean) getApplicationContext();

                            progress.setVisibility(View.VISIBLE);


                            final Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseURL)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            final Allapi cr = retrofit.create(Allapi.class);
                            Call<SocialBean> call = cr.social(email, pid, name);

                            call.enqueue(new Callback<SocialBean>() {
                                @Override
                                public void onResponse(Call<SocialBean> call, Response<SocialBean> response) {

                                    if (Objects.equals(response.body().getStatus(), "1")) {


                                        Toast.makeText(loginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        Bean b = (Bean) getApplicationContext();
                                        b.userId = response.body().getData().getUserId();
                                        b.name = response.body().getData().getName();
                                        b.phone = response.body().getData().getPhone();
                                        b.email = response.body().getData().getEmail();

                                        edit.putString("userId", response.body().getData().getUserId());
                                        edit.apply();

                                        Intent s = new Intent(loginActivity.this, MainActivity.class);
                                        s.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(s);
                                        finish();
                                        progress.setVisibility(View.GONE);

                                    } else {
                                        Toast.makeText(loginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                    }

                                }

                                @Override
                                public void onFailure(Call<SocialBean> call, Throwable t) {

                                    progress.setVisibility(View.GONE);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,id,name,picture");
                request.setParameters(parameters);
                request.executeAsync();

            }


            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
            }
        });


        setContentView(R.layout.activity_login);


//        getSupportActionBar().hide();

        signupText = (TextView) findViewById(R.id.signupText);
        progress = (ProgressBar) findViewById(R.id.progress);
        phone = (EditText) findViewById(R.id.phn);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();

        login = findViewById(R.id.loginbtn);

        facebook = (ImageView) findViewById(R.id.fb);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(loginActivity.this, Arrays.asList("email", "public_profile"));


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String p = phone.getText().toString();

                if (!TextUtils.isEmpty(p)) {

                    progress.setVisibility(View.VISIBLE);
                    final Bean b = (Bean) getApplicationContext();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseURL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Allapi cr = retrofit.create(Allapi.class);
                    Call<SignInBean> call = cr.signin(p);
                    call.enqueue(new Callback<SignInBean>() {
                        @Override
                        public void onResponse(Call<SignInBean> call, Response<SignInBean> response) {

                            if (Objects.equals(response.body().getStatus(), "1")) {

                                b.phone = response.body().getData().getPhone();


                                edit.putString("userId", response.body().getData().getUserId());
                                edit.apply();

                                Intent i = new Intent(loginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                                Toast.makeText(loginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(loginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                progress.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onFailure(Call<SignInBean> call, Throwable t) {

                            progress.setVisibility(View.GONE);
                        }
                    });


                } else {
                    phone.setError("Field is Empty");
                    phone.requestFocus();
                }
            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
