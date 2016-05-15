package com.vanshil.checkpoint;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.vanshil.checkpoint.network.BusinessResponse;
import com.vanshil.checkpoint.network.LoggingInterceptor;
import com.vanshil.checkpoint.network.ZeusService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vanshil on 2016-05-15.
 */
public class ZeusManager {
    private static ZeusManager instance;

    private List<Listener> listeners;

    ZeusService zeusService;
    private List<BusinessResponse.BusinessResult> businesses;

    public static ZeusManager getInstance(){
        if(instance == null){
            instance = new ZeusManager();
        }
        return instance;
    }



    private ZeusManager(){
        listeners = new ArrayList<>();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        zeusService = new Retrofit.Builder()
                .baseUrl("http://api.site1.ciscozeus.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ZeusService.class);


    }

    public List<BusinessResponse.BusinessResult> getBusinesses(){
        if(businesses == null){
            businesses = new ArrayList<>();
            loadBusinesses();
        }
        return businesses;
    }

    public void loadBusinesses(){
        Callback<BusinessResponse> businessCallback = new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> businessResponse) {
                for(BusinessResponse.BusinessResult businessResult: businessResponse.body().getResult()){
                    businessResult.getLatLng();
                }
                notifyBusinessLoaded(businessResponse.body().getResult());
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {

            }
        };
        Call<BusinessResponse> zeusCall = zeusService.getBusinesses();
        zeusCall.enqueue(businessCallback);
    }

    public void postLocation(String logID, LatLng latlng){
        //LocationPostWrapper locationPostWrapper = new LocationPostWrapper(logID, latlng);
        /*Call<LocationResponse> locationCall = zeusService.postLocation(logID, latlng.latitude + "," + latlng.longitude);
        locationCall.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {

            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });*/
        String latlngStr = latlng.latitude + "," + latlng.longitude;
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = null;
        try {
            String encodedStr = URLEncoder.encode("[{\"geoip.location\":\"" + latlngStr + "\"}]", "UTF-8");
            Log.d("Location Post", encodedStr);
            body = RequestBody.create(mediaType, "logs=" + encodedStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder()
                        .url("http://api.site1.ciscozeus.io/logs/1ce4e873/" + logID)
                        .post(body)
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "21ed52f7-7740-23c2-de4f-eeba0af11ff3")
                        .addHeader("content-type", "application/x-www-form-urlencoded")
                        .build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Listener listener){
        if(listeners.indexOf(listener) != -1){
            listeners.remove(listener);
        }
    }
    public void register(Listener listener){
        if(listeners.indexOf(listener) == -1){
            listeners.add(listener);
        }
    }
    private void notifyBusinessLoaded(List<BusinessResponse.BusinessResult> businesses){
        for(Listener listener: listeners){
            listener.notifyBusinessLoaded(businesses);
        }
    }
    public interface Listener{
        void notifyBusinessLoaded(List<BusinessResponse.BusinessResult> businesses);
    }
}
