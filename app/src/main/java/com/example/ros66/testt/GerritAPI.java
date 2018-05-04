package com.example.ros66.testt;

/**
 * Created by ros66 on 5/4/2018.
 */
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GerritAPI {
    @GET("/api/get")
    Call<List<Message>> getData(@Query("name") String resourceName, @Query("num") int count);
}