package com.example.vapor.alarmproject.Alarm;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class AlarmElement {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "hour_of_day")
    private int hour_of_day;
    public int getHour_of_day(){return hour_of_day;}
    public void setHour_of_day(int value){this.hour_of_day=value;}

    @ColumnInfo(name = "minute")
    private int minute;
    public int getMinute(){return minute;}
    public void setMinute(int value){this.minute=value;}

    @ColumnInfo(name = "description")
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String value) {
        this.description = value;
    }

    @ColumnInfo(name="isSet")
    private boolean isSet;
    public boolean getIsSet(){return isSet;}
    public void setIsSet(boolean value){this.isSet=value;}

    public AlarmElement(int hour_of_day,int minute, String description, boolean isSet) {

        this.hour_of_day=hour_of_day;
        this.minute=minute;
        this.description = description;
        this.isSet=isSet;
    }


    public String toString() {
        return this.hour_of_day+":"+minute+"\n"+description;
    }

}
