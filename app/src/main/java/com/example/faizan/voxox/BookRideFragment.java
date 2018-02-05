package com.example.faizan.voxox;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faizan.voxox.EstimatedTimePOJO.TimeBean;
import com.example.faizan.voxox.NearByPOJO.Estimated;
import com.example.faizan.voxox.NearByPOJO.NearByBean;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import github.nisrulz.easydeviceinfo.base.EasyLocationMod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getOnProgressThreshold;
import static com.facebook.FacebookSdk.isDebugEnabled;


public class BookRideFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView cancel, searchBar1, searchBarTv, searchBar2;

    SupportMapFragment mSupportMapFragment;
    TextView searchBar;

    RelativeLayout searchBarMain, carLayout, confirmLayout;

    RecyclerView cabTypeList;

    LinearLayoutManager manager;

    List<Estimated> cabList;

    LinearLayout cabConfirm;


    int cabPosition = 0;
    String selectedId = "";


    Button continueBtn, continuebtnfirst;
    FloatingActionButton mylocationButton;
    private int PLACE_PICKER_REQUEST = 1;
    private int PLACE_PICKER_REQUEST2 = 2;


    String TAG = "TAG:VOXOX";


    private LatLngBounds bounds;
    private LatLngBounds.Builder builder;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    GoogleMap map;

    ImageButton backToType;

    String userId;


    Location currentLocation;
    Location pickUpLocation;
    Location dropLocation;


    String pickUpLat = "";
    String pickUpLng = "";


    GoogleApiClient googleApiClient;
    private int PLAY_SERVICES_REQUEST = 32;
    private int REQUEST_CHECK_SETTINGS = 33;


    CabAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_book_ride, container, false);

        cabList = new ArrayList<>();
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        adapter = new CabAdapter(getContext(), cabList);


        userId = getArguments().getString("userId");

        //Log.d("userId", userId);

        backToType = (ImageButton) view.findViewById(R.id.back_to_type);

        searchBarMain = (RelativeLayout) view.findViewById(R.id.searchBarMain);
        carLayout = (RelativeLayout) view.findViewById(R.id.carLayout);
        cabConfirm = (LinearLayout) view.findViewById(R.id.cabConfirm);
        //cabSelection = (LinearLayout) view.findViewById(R.id.cabSelection);
        continueBtn = (Button) view.findViewById(R.id.continuebtn);
        confirmLayout = (RelativeLayout) view.findViewById(R.id.confirmLayout);
        continuebtnfirst = (Button) view.findViewById(R.id.continuebtnFirst);
        searchBar1 = (TextView) view.findViewById(R.id.searchBar1);
        searchBar2 = (TextView) view.findViewById(R.id.searchBar2);

        cabTypeList = (RecyclerView) view.findViewById(R.id.cab_type_list);

        cabTypeList.setAdapter(adapter);
        cabTypeList.setLayoutManager(manager);

        mylocationButton = (FloatingActionButton) view.findViewById(R.id.fab);

        mylocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pickUpLocation == null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(14.0f).build();


                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    map.animateCamera(cameraUpdate);
                } else {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(pickUpLocation.getLatitude(), pickUpLocation.getLongitude())).zoom(14.0f).build();


                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    map.animateCamera(cameraUpdate);
                }


            }
        });

        if (checkPlayServices()) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();

            googleApiClient.connect();


            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult locationSettingsResult) {

                    final Status status = locationSettingsResult.getStatus();

                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location requests here

                            /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


                            pickUpLat = currentLocation.getLatitude();*/


                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });


        }


        // searchBarTv = (TextView) view.findViewById(R.id.sbt1);
/*        searchBar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build((Activity) getContext()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });*/

        searchBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder2 = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder2.build((Activity) getContext()), PLACE_PICKER_REQUEST2);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.booking_dialog);
                dialog.setCancelable(true);
                dialog.show();

                TextView cancel = dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });


            }
        });

        backToType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carLayout.setVisibility(View.GONE);
                searchBarMain.setVisibility(View.VISIBLE);

                searchBar1.setClickable(true);
                searchBar2.setClickable(true);

                searchBar1.setFocusable(true);
                searchBar2.setFocusable(true);

            }
        });

        continuebtnfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carLayout.setVisibility(View.VISIBLE);
                searchBarMain.setVisibility(View.GONE);


                searchBar1.setClickable(false);
                searchBar2.setClickable(false);

                searchBar1.setFocusable(false);
                searchBar2.setFocusable(false);


            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carLayout.setVisibility(View.GONE);
                confirmLayout.setVisibility(View.VISIBLE);

            }
        });

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.bookRideFragment);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.bookRideFragment, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(this);
        }


        return view;

    }


    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(), resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }


    Call<NearByBean> nearbyCall;


    private void getNearbyData(final String latitude, final String longitude) {

        try {
            final Bean b = (Bean) getContext().getApplicationContext();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Allapi cr = retrofit.create(Allapi.class);


            nearbyCall = cr.nearBy(userId, latitude, longitude);

            nearbyCall.enqueue(new Callback<NearByBean>() {
                @Override
                public void onResponse(Call<NearByBean> call, Response<NearByBean> response) {

                    try {
                        if (Objects.equals(response.body().getStatus(), "1")) {


                            //Log.d("messasd", response.body().getMessage());
                            //Log.d("driver", response.body().getData().get(1).getLatitude());
                            //builder = new LatLngBounds.Builder();

                            map.clear();


                            if (pickUpLocation == null) {
                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                        .icon(bitmapDescriptorFromVector(getContext(), R.drawable.pin)));
                            } else {
                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(pickUpLocation.getLatitude(), pickUpLocation.getLongitude()))
                                        .icon(bitmapDescriptorFromVector(getContext(), R.drawable.pin)));
                            }

                            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                final String lat1 = response.body().getData().get(i).getLatitude();
                                final String lon1 = response.body().getData().get(i).getLongitude();
                                LatLng driver1 = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lon1));


                                final View pin = inflater.inflate(R.layout.marker , null);
                                pin.layout(0 , 0 , 100 , 100);

                                final ImageView image = (ImageView) pin.findViewById(R.id.marker_image);

                                Ion.with(getContext()).load(response.body().getData().get(i).getIconImage()).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
                                    @Override
                                    public void onCompleted(Exception e, Bitmap result) {

                                        try {

                                            image.setImageBitmap(result);

                                            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat1), Double.parseDouble(lon1)))
                                                    .icon(BitmapDescriptorFactory.fromBitmap(getViewBitmap(pin))));

                                            //markers.put(mar.getId() , item);


                                        }catch (NullPointerException e1)
                                        {
                                            e1.printStackTrace();
                                        }


                                    }
                                });

                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(lat1), Double.parseDouble(lon1)))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi)));
                                //builder.include(driver1);

                            }


                            //bounds = builder.build();
                            //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 250);
                            //map.animateCamera(cu);


                            //Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        } else {
                            //Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            //Log.d("msg", response.body().getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (pickUpLocation == null) {
                        getNearbyData(pickUpLat, pickUpLng);
                    } else {
                        getNearbyData(pickUpLat, pickUpLng);
                    }


                    //Log.d("cabData" , String.valueOf(response.body().getEstimated().size()));

                    //cabCount = response.body().getEstimated().size();

                    adapter.setGridData(response.body().getEstimated());

                    //cabCount = response.body().getEstimated().size();

                }


                @Override
                public void onFailure(Call<NearByBean> call, Throwable t) {

                    nearbyCall.clone().enqueue(this);

                    t.printStackTrace();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.pin);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        //Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        //vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        //vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }










    /*public void onClick(View v) {

        switch (v.getId()) {
            case R.id.miniLin:
                mini.setImageResource(R.drawable.mini32);
                mini.setBackgroundResource(R.drawable.backcar);
                micro.setImageResource(R.drawable.microsvg);
                micro.setBackgroundResource(R.drawable.backcarwhite);
                sedan.setImageResource(R.drawable.sedansvg);
                sedan.setBackgroundResource(R.drawable.backcarwhite);
                break;

            case R.id.microLin:
                micro.setImageResource(R.drawable.micro32);
                micro.setBackgroundResource(R.drawable.backcar);
                mini.setImageResource(R.drawable.minisvg);
                mini.setBackgroundResource(R.drawable.backcarwhite);
                sedan.setImageResource(R.drawable.sedansvg);
                sedan.setBackgroundResource(R.drawable.backcarwhite);
                break;

            case R.id.sedanLin:
                sedan.setImageResource(R.drawable.sedan32);
                sedan.setBackgroundResource(R.drawable.backcar);
                mini.setImageResource(R.drawable.minisvg);
                mini.setBackgroundResource(R.drawable.backcarwhite);
                micro.setImageResource(R.drawable.microsvg);
                micro.setBackgroundResource(R.drawable.backcarwhite);
                break;

        }

    }*/

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getContext());
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append(address);
                searchBar1.setText(stBuilder.toString());
                placeMarkerOnMap(place.getLatLng());


            }
        } else if (requestCode == PLACE_PICKER_REQUEST2) {
            if (resultCode == RESULT_OK) {
                Place place2 = PlacePicker.getPlace(data, getContext());
                StringBuilder stBuilder2 = new StringBuilder();
                String placename = String.format("%s", place2.getName());
                String latitude = String.valueOf(place2.getLatLng().latitude);
                String longitude = String.valueOf(place2.getLatLng().longitude);

                //Log.d("dropLat", latitude);
                //Log.d("dropLat", longitude);
                String address2 = String.format("%s", place2.getAddress());
                //Log.d("Dest", "lisa");
                stBuilder2.append(address2);
                searchBar2.setText(stBuilder2.toString());
                placeMarkerOnMap2(place2.getLatLng());
            }
        }
    }

    protected void placeMarkerOnMap(LatLng location) {
        //MarkerOptions markerOptions = new MarkerOptions().position(location);

        map.clear();
        map.addMarker(new MarkerOptions()
                .position(new LatLng(location.latitude, location.longitude)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(15.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);


    }


    protected void placeMarkerOnMap2(LatLng location) {
        //MarkerOptions markerOptions = new MarkerOptions().position(location);

        // map.clear();
        map.addMarker(new MarkerOptions()
                .position(new LatLng(location.latitude, location.longitude)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(15.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        //map.setMyLocationEnabled(true);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


        if (pickUpLocation == null) {
            pickUpLat = String.valueOf(currentLocation.getLatitude());
            pickUpLng = String.valueOf(currentLocation.getLongitude());


            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {
                searchBar1.setText(addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getSubLocality() + ", " + addresses.get(0).getLocality());
            } else {
                // do your stuff
            }


            getNearbyData(pickUpLat, pickUpLng);


            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(15.0f).build();


            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            map.animateCamera(cameraUpdate);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

        googleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }


    public class CabAdapter extends RecyclerView.Adapter<CabAdapter.ViewHolder> {

        List<Estimated> cabList = new ArrayList<>();
        Context context;

        public CabAdapter(Context context, List<Estimated> cabList) {
            this.context = context;
            this.cabList = cabList;
        }

        public void setGridData(List<Estimated> cabList) {
            this.cabList = cabList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cab_type_list_model, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.setIsRecyclable(false);

            final Estimated item = cabList.get(position);


            holder.time.setText(item.getEstimateTime());


            if (cabPosition == position)
            {
                holder.icon.setBackgroundResource(R.drawable.backcar);
                selectedId = item.getTypeId();
            }
            else
            {
                holder.icon.setBackgroundResource(R.drawable.backcarwhite);
            }


            //if (cabList.size() > cabCount)
            //{

            Log.d("asdasd", "asasd");

            holder.type.setText(item.getCabType());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(false).build();

            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getIcon(), holder.icon, options);

            //cabCount = cabList.size();

            //}

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cabPosition = position;
                    selectedId = item.getTypeId();
                    notifyDataSetChanged();

                }
            });


        }

        @Override
        public int getItemCount() {
            return cabList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView time, type;
            ImageView icon;

            public ViewHolder(View itemView) {
                super(itemView);

                time = (TextView) itemView.findViewById(R.id.time);
                type = (TextView) itemView.findViewById(R.id.type);
                icon = (ImageView) itemView.findViewById(R.id.icon);

            }
        }

    }


}