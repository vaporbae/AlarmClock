package com.example.vapor.alarmproject.WorldClock;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.vapor.alarmproject.WorldClock.ClockElement;

import java.util.List;

@Dao
public interface ClockElementDao {
    @Insert
    void insertAll(ClockElement... clockElements);

    @Query("DELETE FROM ClockElement WHERE id = :ID")
    void deleteElement(int ID);

    @Query("SELECT * FROM ClockElement")
    List<ClockElement> getAllElements();

    @Query("SELECT * FROM ClockElement WHERE id LIKE :ID")
    ClockElement getElementById(Integer ID);

    @Query("UPDATE ClockElement SET hourString=:setHourString, dayString=:setDayString WHERE id= :givenID")
    void updateStrings(int givenID, String setHourString, String setDayString);
}

