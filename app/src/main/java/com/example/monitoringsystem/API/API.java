package com.example.monitoringsystem.API;

import com.example.monitoringsystem.model.Parameters;
import com.example.monitoringsystem.model.Reports;

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

    @GET("/reports")
    Call<List<Reports>> getReports();

    @GET("/reports")
    Call<List<Reports>> getReports(@Query("timestampFrom") String timestampFrom,
                                   @Query("timestampTo") String timestampTo);
}
