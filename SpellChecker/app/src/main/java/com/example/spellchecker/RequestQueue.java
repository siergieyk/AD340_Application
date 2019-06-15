package com.example.spellchecker;

import android.content.Context;
import android.util.Log;

import com.android.volley.toolbox.Volley;


public class RequestQueue {


    String TAG = RequestQueue.class.getSimpleName();

    private RequestQueue myRequestQueue;
    private com.android.volley.RequestQueue requestQueue;
    private Context myContext;

    public RequestQueue(Context context) {
        this.myContext = context;
    }



    public synchronized RequestQueue getInstance(Context context) {
        if (myRequestQueue == null) {
            myRequestQueue = new RequestQueue(context);
        }
        return myRequestQueue;
    }


    public com.android.volley.RequestQueue requestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(myContext);
        }
        Log.d(TAG, "requestQueue: ");

        return requestQueue;

    }
}
