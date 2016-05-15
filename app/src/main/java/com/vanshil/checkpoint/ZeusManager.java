package com.vanshil.checkpoint;

import com.vanshil.checkpoint.network.BusinessResponse;
import com.vanshil.checkpoint.network.LoggingInterceptor;
import com.vanshil.checkpoint.network.ZeusService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
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
        Call<BusinessResponse> zeusCall = zeusService.getBusinesses("location-2016-05-15", "geoip.location", "json");
        zeusCall.enqueue(businessCallback);
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
