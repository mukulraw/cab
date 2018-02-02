package com.example.faizan.voxox;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity {
    AHBottomNavigation bottom;
    Toolbar toolbar;

    SharedPreferences pref;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        userId = pref.getString("userId" , "");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.cab_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem("Profile", R.drawable.user_32_white);

        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem("Support", R.drawable.message_32_color);

        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem("Book Ride", R.drawable.location_32_white);

        AHBottomNavigationItem item4 =
                new AHBottomNavigationItem("Ride History", R.drawable.history_32_white);

        AHBottomNavigationItem item5 =
                new AHBottomNavigationItem("Notification", R.drawable.notification_32_white);

        bottom.addItem(item1);
        bottom.addItem(item2);
        bottom.addItem(item3);
        bottom.addItem(item4);
        bottom.addItem(item5);

        bottom.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottom.setBehaviorTranslationEnabled(false);

        bottom.setDefaultBackgroundColor(Color.parseColor("#222222"));
        bottom.setAccentColor(Color.parseColor("#ffcd2e"));
        bottom.setInactiveColor(Color.parseColor("#ffffff"));
        bottom.setCurrentItem(2);

        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:


                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        ProfileFragment frag1 = new ProfileFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("userId" , userId);

                        frag1.setArguments(bundle);

                        ft.replace(R.id.replace, frag1);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();

                        return true;
                    case 1:


                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        SupportFragment frag3 = new SupportFragment();

                        Bundle bundle1 = new Bundle();
                        bundle1.putString("userId" , userId);

                        frag3.setArguments(bundle1);

                        ft2.replace(R.id.replace, frag3);
                        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft2.commit();

                        return true;
                    case 2:


                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        BookRideFragment frag2 = new BookRideFragment();

                        Bundle bundle2 = new Bundle();
                        bundle2.putString("userId" , userId);

                        frag2.setArguments(bundle2);

                        ft1.replace(R.id.replace, frag2);
                        ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft1.commit();


                        return true;
                    case 3:


                        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        RideHistoryFragment frag4 = new RideHistoryFragment();

                        Bundle bundle4 = new Bundle();
                        bundle4.putString("userId" , userId);

                        frag4.setArguments(bundle4);

                        ft3.replace(R.id.replace, frag4);
                        ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft3.commit();

                        return true;
                    case 4:


                        FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        NotificationFragment frag5 = new NotificationFragment();

                        Bundle bundle5 = new Bundle();
                        bundle5.putString("userId" , userId);

                        frag5.setArguments(bundle5);

                        ft5.replace(R.id.replace, frag5);
                        ft5.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft5.commit();

                        return true;
                }

                return false;
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        BookRideFragment frag2 = new BookRideFragment();

        Bundle bundle2 = new Bundle();
        bundle2.putString("userId" , userId);

        frag2.setArguments(bundle2);


        ft1.replace(R.id.replace, frag2);
        //ft.addToBackStack(null);
        ft1.commit();

    }
}

