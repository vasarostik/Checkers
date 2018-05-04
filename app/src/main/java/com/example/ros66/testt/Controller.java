package com.example.ros66.testt;

/**
 * Created by ros66 on 5/4/2018.
 */

import android.os.Build;
import android.util.Log;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<List<Message>> {

    static final String BASE_URL = "https://api.github.com";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GerritAPI gerritAPI = retrofit.create(GerritAPI.class);

       // Call<List<Message>> call = gerritAPI.loadChanges("status:open");
       // call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
        if(response.isSuccessful()) {
            List<Message> changesList = response.body();

          Log.i("Data--- ", String.valueOf(changesList));

        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Message>> call, Throwable t) {
        t.printStackTrace();
    }
}
