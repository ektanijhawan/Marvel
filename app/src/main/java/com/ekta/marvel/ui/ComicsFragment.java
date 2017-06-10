package com.ekta.marvel.ui;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekta.marvel.R;
import com.ekta.marvel.network.Endpoints;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {


    public ComicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comics, container, false);
        callApi();

        return view;
    }

    public void callApi() {
//        OkHttpClient client = new OkHttpClient();
        Long tsLong = System.currentTimeMillis() / 1000;
        long timeStamp = System.currentTimeMillis();

        String hash = md5(timeStamp + Endpoints.PRIVATE_API_KEY + Endpoints.PUBLIC_API_KEY);
//        Request request = new Request.Builder()
//                .url("http://gateway.marvel.com/v1/comics?ts="+ts+"&apikey="+ Endpoints.PUBLIC_API_KEY+"&hash="+hash)
//                .build();
//        Response response = client.newCall(request).execute();
        String url = "http://gateway.marvel.com/v1/public/comics?ts=" + timeStamp + "&apikey=" + Endpoints.PUBLIC_API_KEY + "&hash=" + hash+"&limit=10";
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(url);
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    class OkHttpHandler extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();






        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                Log.d("result", response.body().string());

                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
