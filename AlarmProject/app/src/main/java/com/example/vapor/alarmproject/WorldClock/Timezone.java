package com.example.vapor.alarmproject.WorldClock;

import com.google.gson.annotations.SerializedName;

public class Timezone {
    private int dstOffset;
    private int rawOffset;
    private String timeZoneId;
    private String timeZoneName;

    @SerializedName("body")
    private String text;

    public int getDstOffset(){
        return dstOffset;
    }

    public int getRawOffset(){
        return rawOffset;
    }

    public String getTimeZoneId(){
        return timeZoneId;
    }

    public String getTimeZoneName(){
        return timeZoneName;
    }
}
