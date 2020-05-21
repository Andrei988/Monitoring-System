package com.example.monitoringsystem.API;
import com.example.monitoringsystem.model.Parameters;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/sensor")
    Call<List<Parameters>> getMeasurement();

}
