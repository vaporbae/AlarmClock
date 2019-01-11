package com.example.vapor.alarmproject.WakeMeUpHere;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.vapor.alarmproject.WakeMeUpHere.PlaceElement;

import java.util.List;

@Dao
public interface PlaceElementDao {
    @Insert
    void insertAll(PlaceElement... placeElements);

    @Query("UPDATE PlaceElement SET lat = :setLat,lang = :setLang,radius = :setRadius WHERE id= :givenID")
    void updatePlace(int givenID, Double setLat, Double setLang, String setRadius);

    @Query("UPDATE PlaceElement SET lat = :setLat,lang = :setLang WHERE id= :givenID")
    void updateLatLang(int givenID, Double setLat, Double setLang);

    @Query("UPDATE PlaceElement SET radius=:setRadius WHERE id= :givenID")
    void updateRadius(int givenID, String setRadius);

    @Query("DELETE FROM PlaceElement WHERE id != :ID")
    void deleteElement(int ID);

    @Query("SELECT * FROM PlaceElement")
    List<PlaceElement> getAllElements();

    @Query("SELECT * FROM PlaceElement WHERE id LIKE :ID")
    PlaceElement getElementById(Integer ID);
}