package com.example.vapor.alarmproject.WorldClock;

import com.example.vapor.alarmproject.WorldClock.Timezone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TimeZoneApi {
    @GET("json?")
    Call<Timezone> getTimeZone(@Query(value="location", encoded = true) String location, @Query("timestamp") String timestamp, @Query("key") String key);

}
