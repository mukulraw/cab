package com.example.faizan.voxox;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import github.nisrulz.easydeviceinfo.base.EasyLocationMod;

public class RateDriver extends AppCompatActivity {

    SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);


        //mSupportMapFragment = (SupportMapFragment) getApplicationContext().findFragmentById(R.id.bookRideFragment);
        /*if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.activi, mSupportMapFragment).commit();
        }*/

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);

                        EasyLocationMod easyLocationMod = new EasyLocationMod(RateDriver.this);

                        if (ActivityCompat.checkSelfPermission(RateDriver.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RateDriver.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

                        double[] l = easyLocationMod.getLatLong();
                        String lat = String.valueOf(l[0]);
                        String lon = String.valueOf(l[1]);

                        LatLng myLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        Geocoder geocoder = new Geocoder(RateDriver.this, Locale.getDefault());
                        try {
                            List<Address> listAdresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
                            if (null != listAdresses && listAdresses.size() > 0) {
                                String address = listAdresses.get(0).getAddressLine(0);
                                String state = listAdresses.get(0).getAdminArea();
                                String country = listAdresses.get(0).getCountryName();
                                String subLocality = listAdresses.get(0).getSubLocality();

                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)))
                                        .title("" + subLocality + ", " + state + ", " + country + "")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        googleMap.setMyLocationEnabled(false);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);


                    }

                }

            });


        }


    }
}
