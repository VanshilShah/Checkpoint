package com.vanshil.checkpoint.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vanshil on 2016-05-14.
 */
public interface ZeusService {
    @FormUrlEncoded
    @POST("/logs/1ce4e873/{logName}")
    Call<LocationResponse> postLocation(@Path("logName") String locationID, @Field("geoip.location") String locationPost);

    @GET("/logs/1ce4e873/?log_name=businesses&attribute_name=name&format=json")
    Call<BusinessResponse> getBusinesses();

}
