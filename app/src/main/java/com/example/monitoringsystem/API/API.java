package com.example.monitoringsystem.API;

import com.example.monitoringsystem.model.Parameters;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("/parameters")
    Call<List<Parameters>> getParameters();

    @GET("/parameters")
    Call<List<Parameters>> getParameters(@Query("timestampFrom") String timestampFrom,
                                         @Query("timestampTo") String timestampTo);
}
