package com.example.vapor.alarmproject.WorldClock;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ClockElement {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "city")
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String value) {
        this.city = value;
    }

    @ColumnInfo(name = "country")
    private String country;
    public String getCountry() {
        return country;
    }
    public void setCountry(String value) {
        this.country = value;
    }

    @ColumnInfo(name = "location")
    private String location;
    public String getLocation() {
        return location;
    }
    public void setLocation(String value) {
        this.location = value;
    }

    @ColumnInfo(name = "dayString")
    private String dayString;
    public String getDayString() {
        return dayString;
    }
    public void setDayString(String value) {
        this.dayString = value;
    }

    @ColumnInfo(name = "hourString")
    private String hourString;
    public String getHourString() {
        return hourString;
    }
    public void setHourString(String value) {
        this.hourString = value;
    }


    public ClockElement(String city, String country, String location, String dayString, String hourString) {

        this.city=city;
        this.country=country;
        this.location=location;
        this.dayString=dayString;
        this.hourString=hourString;
    }


    public String toString() {
        return this.city+ " " + this.country + " " + location;
    }

}
