package com.example.faizan.voxox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

TextView resetPass, editProfile,logout;
    SharedPreferences pref;

    SharedPreferences.Editor edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile , container , false);

        resetPass = (TextView) view.findViewById(R.id.resetPassword);
        editProfile = (TextView) view.findViewById(R.id.editProfile);
        logout = (TextView) view.findViewById(R.id.logout);

        pref = getContext().getSharedPreferences("pref", MODE_PRIVATE);
        edit = pref.edit();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit.remove("phone");
                edit.remove("userId");
                edit.apply();

                LoginManager.getInstance().logOut();

                Intent i = new Intent(getContext(), loginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();


            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),resetPassword.class);
                startActivity(i);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), editProfile.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
