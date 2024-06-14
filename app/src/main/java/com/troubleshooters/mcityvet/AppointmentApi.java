package com.troubleshooters.mcityvet;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AppointmentApi {



    @GET("backend/appointment/all")
    Call<List<Appointment>> getAllAppointment();


    @POST("backend/appointment/create")
    Call<Appointment> createAppointment(@Body Appointment appointment);

    @DELETE("backend/Appointment/delete/{id}")
    Call<Void> deleteAppointment(@Path("id") String id);

    @PUT("backend/Appointment/{id}")
    Call<Void> updateAppointment(@Path("id") String id, @Body Map<String, Object> updateData);
}
