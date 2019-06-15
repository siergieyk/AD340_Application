package com.example.spellchecker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CamsLocation extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "CamsLocation";
    private static final String API = "http://brisksoft.us/ad340/traffic_cameras_merged.json";


    private RecyclerView myRecyclerView;
    private ViewAdapter myAdapter;
    private ArrayList<TrafficCamera> myTrafficCameras;




    private boolean checkNetwork() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cams_location);

        myTrafficCameras = new ArrayList<>();

        if(checkNetwork())
            loadData();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }



    private void loadData() {
        Log.e(TAG, "loading");
        final ProgressDialog progressDialog = new ProgressDialog(CamsLocation.this);
        progressDialog.show();
        RequestQueue cameraLocationRequest = new RequestQueue(getApplicationContext());
        cameraLocationRequest.getInstance(getApplicationContext());
        String url = createGetRequest(getApi());

        StringRequest cameraLocationQuery = new StringRequest(Request.Method.GET, url,
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
                                myTrafficCameras.add(trafficCamera);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                        myRecyclerView = findViewById(R.id.cameraRecycleView);
                        myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        myAdapter = new ViewAdapter(getApplicationContext(), myTrafficCameras);
                        myRecyclerView.setAdapter(myAdapter);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                        progressDialog.dismiss();
                    }
                });
        cameraLocationRequest.requestQueue().add(cameraLocationQuery);
    }

    public ArrayList<TrafficCamera> sortList(ArrayList<TrafficCamera> trafficCameraArrayList) {
        Collections.sort(trafficCameraArrayList, new Comparator<TrafficCamera>() {
            @Override
            public int compare(TrafficCamera o1, TrafficCamera o2) {
                return o1.getOwnershipcd().compareToIgnoreCase(o2.getOwnershipcd());
            }
        });
        return trafficCameraArrayList;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            myTrafficCameras = sortList(myTrafficCameras);
            for (int i = 0; i < myTrafficCameras.size(); i++) {
                Log.e(TAG, myTrafficCameras.get(i).toString());
            }
            myAdapter.notifyDataSetChanged();
        }
    }

    public static String getApi() {
        return API;
    }

    public static String createGetRequest(String url) {
        return url;
    }

}
