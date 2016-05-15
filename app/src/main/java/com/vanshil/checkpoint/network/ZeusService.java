package com.vanshil.checkpoint.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vanshil on 2016-05-14.
 */
public interface ZeusService {

    @POST("/logs/1ce4e873/{logName}")
    Call<Response> postLocation(@Path("logName") String locationID, @Body LocationPost locationPost);

    @GET("/logs/1ce4e873/?log_name=businesses&attribute_name=name&format=json")
    Call<BusinessResponse> getBusinesses();

}
