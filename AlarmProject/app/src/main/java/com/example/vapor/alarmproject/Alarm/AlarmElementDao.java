package com.example.vapor.alarmproject.Alarm;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.vapor.alarmproject.Alarm.AlarmElement;

import java.util.List;

@Dao
public interface AlarmElementDao {
    @Insert
    void insertAll(AlarmElement... alarmElements);

    @Query("UPDATE AlarmElement SET hour_of_day = :setHourOfDay,minute=:setMinute, description = :setDescription WHERE id= :givenID")
    void updateAlarm(int givenID, int setHourOfDay, int setMinute, String setDescription);

    @Query("UPDATE AlarmElement SET isSet=:setIsSet WHERE id= :givenID")
    void updateIsSet(int givenID, boolean setIsSet);

    @Query("DELETE FROM AlarmElement WHERE id = :ID")
    void deleteElement(int ID);

    @Query("SELECT * FROM AlarmElement")
    List<AlarmElement> getAllElements();

    @Query("SELECT * FROM AlarmElement WHERE id LIKE :ID")
    AlarmElement getElementById(Integer ID);
}

