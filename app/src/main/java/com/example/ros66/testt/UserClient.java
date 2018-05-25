package com.example.ros66.testt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ros66 on 5/11/2018.
 */

public interface UserClient {
    @POST("user")
    Call<CheckersMove> parse(@Body CheckersMove move);
}
