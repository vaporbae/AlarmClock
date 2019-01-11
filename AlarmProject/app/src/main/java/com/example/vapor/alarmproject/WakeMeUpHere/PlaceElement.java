package com.example.vapor.alarmproject.WakeMeUpHere;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.location.places.Place;

@Entity
public class PlaceElement {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "lat")
    private Double lat;
    public Double getLat(){return lat;}
    public void setLat(Double value){this.lat=value;}

    @ColumnInfo(name = "lang")
    private Double lang;
    public Double getLang(){return lang;}
    public void setLang(Double value){this.lang=value;}

    @ColumnInfo(name = "radius")
    private String radius;
    public String getRadius() {
        return radius;
    }
    public void setRadius(String value) {
        this.radius = value;
    }

    public PlaceElement(Double lat, Double lang, String radius) {

        this.lat=lat;
        this.lang=lang;
        this.radius=radius;
    }



}

