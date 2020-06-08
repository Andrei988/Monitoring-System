package com.example.monitoringsystem.API;

import com.example.monitoringsystem.model.Parameter;
import com.example.monitoringsystem.repository.Database.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @GET("/parameters")
    Call<List<Parameter>> getLastParameters();

    @GET("/parametersFiltered")
    Call<List<Parameter>> getParameters(@Query("timestampFrom") String timestampFrom,
                                         @Query("timestampTo") String timestampTo);

    @GET("/defaultValue")
    Call<List<Report>> getReports();

    @GET("/reports")
    Call<List<Report>> getReports(@Query("timestampFrom") String timestampFrom,
                                  @Query("timestampTo") String timestampTo);

    @POST("/report")
    Call<Report> createReport(@Body Report report);

    @FormUrlEncoded
    @POST("/report")
    Call<Report> createReport(
            @Field("avg_co2") double avg_co2,
            @Field("avg_humidity") double avg_humidity,
            @Field("avg_temperature") double avg_temperature,
            @Field("timestamp") String timestamp
    );

}