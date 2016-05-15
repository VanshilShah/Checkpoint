package com.vanshil.checkpoint.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Vanshil on 2016-05-14.
 */
public interface ZeusService {

    @POST("/logs/939f983c/{locationID}")
    Call<Response> postLocation(@Path("locationID") String locationID, @Body LocationPost locationPost);

    @GET("/logs/939f983c/")
    Call<BusinessResponse> getBusinesses(@Query("log_name") String logName, @Query("attribute_name") String attributeName, @Query("format") String format);

}
