package com.example.spellchecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String API = "http://brisksoft.us/ad340/traffic_cameras_merged.json";



    private static final String TAG = "MapsActivity";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;


    private LatLng pointedLocation;
    private TextView myCurrentLocation;
    private ProgressDialog myProgressDialog;
    private ArrayList<TrafficCamera> trafficCams;
    private GoogleMap myGoogleMap;
    private GoogleMap.OnCameraIdleListener myOnCameraIdleListener;
    private MapFragment myMapFragment;
    private GoogleApiClient myGoogleApiClient;
    private Location myLocation;
    private boolean triggered = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        trafficCams = new ArrayList<>();
        loadData();
        displayLocation(getApplicationContext());
    }

    private void initializeWidgets() {
        myCurrentLocation = findViewById(R.id.currentLocation);
    }




    private void displayLocation(Context context) {
        myProgressDialog = new ProgressDialog(MapsActivity.this);
        myProgressDialog.setTitle(R.string.app_name);
        myProgressDialog.setIcon(R.mipmap.ic_launcher);
        myProgressDialog.setMessage("Loading...");
        myProgressDialog.setCancelable(false);
        myProgressDialog.show();

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);


        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result1) {
                final Status status = result1.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        MapsActivity.this.initializeWidgets();
                        myMapFragment = (MapFragment) MapsActivity.this.getFragmentManager().findFragmentById(R.id.fragment);
                        myMapFragment.getMapAsync(MapsActivity.this);
                        MapsActivity.this.setOnCameraIdleListener();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {

                            myProgressDialog.dismiss();
                            status.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                            if (status.isSuccess()) {
                                myMapFragment = (MapFragment) MapsActivity.this.getFragmentManager().findFragmentById(R.id.fragment);
                                myMapFragment.getMapAsync(MapsActivity.this);
                                MapsActivity.this.setOnCameraIdleListener();
                            }
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        myProgressDialog.dismiss();
                        MapsActivity.this.finish();
                        break;
                }
            }
        });
    }

    private void loadData() {
        Log.e(TAG, "loading");
        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.show();
        RequestQueue locationRequest = new RequestQueue(getApplicationContext());
        locationRequest.getInstance(getApplicationContext());
        String url = createGetRequest(getApi());

        StringRequest LocationQuery = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonReader reader = new JsonReader(new StringReader(response));
                        reader.setLenient(true);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JsonObject convertedObject = new Gson().fromJson(jsonArray.get(i).toString(), JsonObject.class);
                                Gson gson = new Gson();
                                TrafficCamera trafficCamera = gson.fromJson(convertedObject.toString(), TrafficCamera.class);
                                trafficCams.add(trafficCamera);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                        progressDialog.dismiss();
                    }
                });
        locationRequest.requestQueue().add(LocationQuery);

    }



    private void setOnCameraIdleListener() {
        myOnCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = myGoogleMap.getCameraPosition().target;
                pointedLocation = latLng;
                Geocoder geocoder = new Geocoder(MapsActivity.this);

                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses.size() > 0) {
                        String address = addresses.get(0).getAddressLine(0);
                        myCurrentLocation.setText(address);

                        for (int u = 0; u < trafficCams.size(); u++) {

                            LatLng markerLatLng = new LatLng(Double.parseDouble(trafficCams.get(u).getLocation().getLatitude()),
                                    Double.parseDouble(trafficCams.get(u).getLocation().getLongitude()));

                            MarkerOptions markerOptions2 = new MarkerOptions();
                            markerOptions2.position(markerLatLng);
                            markerOptions2.title(trafficCams.get(u).getCameralabel());
                            markerOptions2.snippet(trafficCams.get(u).getImageurl().getUrl());
                            myGoogleMap.addMarker(markerOptions2);

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle(R.string.app_name)
                        .setMessage("App needs to access the maps, please accept to use location functionality")
                        .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MapsActivity.this.finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        myGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        myGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(myGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        myGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                myGoogleMap.setMyLocationEnabled(true);
                myGoogleMap.setOnCameraIdleListener(myOnCameraIdleListener);
            } else {
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            myGoogleMap.setMyLocationEnabled(true);
            myGoogleMap.setOnCameraIdleListener(myOnCameraIdleListener);

        }
    }



    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {

        if (myLocation != null) {
            myLocation = location;
        }
        if (triggered) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            pointedLocation = latLng;

            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                myCurrentLocation.setText(address);

            } catch (IOException e) {
                e.printStackTrace();

            }
            myProgressDialog.dismiss();
            triggered = false;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (myGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        myGoogleMap.setMyLocationEnabled(true);
                        for (int u = 0; u < trafficCams.size(); u++) {

                            LatLng markerLatLng = new LatLng(Double.parseDouble(trafficCams.get(u).getLocation().getLatitude()),
                                    Double.parseDouble(trafficCams.get(u).getLocation().getLongitude()));

                            MarkerOptions markerOptions2 = new MarkerOptions();
                            markerOptions2.position(markerLatLng);
                            markerOptions2.title(trafficCams.get(u).getCameralabel());
                            markerOptions2.snippet(trafficCams.get(u).getImageurl().getUrl());
                            myGoogleMap.addMarker(markerOptions2);

                        }
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Permission Denied, " +
                            "Please turn on location permission manually from app settings", Toast.LENGTH_LONG).show();
                    finish();

                }
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        initializeWidgets();
                        myMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
                        myMapFragment.getMapAsync(MapsActivity.this);
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(), "GPS required", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (myGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(myGoogleApiClient, this);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static String getApi() {
        return API;
    }

    public static String createGetRequest(String url) {
        return url;
    }

}
