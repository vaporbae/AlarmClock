package com.example.vapor.alarmproject.TimeToSleep;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.vapor.alarmproject.TimeToSleep.TimeToSleepElement;

import java.util.List;

@Dao
public interface TimeToSleepElementDao {
    @Insert
    void insertAll(TimeToSleepElement... timeToSleepElements);

    @Query("UPDATE TimeToSleepElement SET monday = :setMonday,tuesday=:setTuesday, wednesday = :setWednesday, thursday=:setThursday, wednesday=:setWednesday, thursday=:setThursday, friday=:setFriday, saturday=:setSaturday, sunday=:setSunday, time_to_sleep_hour_of_day=:setTime_to_sleep_hour_of_day, time_to_sleep_minute=:setTime_to_sleep_minute, alarm_hour_of_day=:setAlarm_hour_of_day, alarm_minute=:setAlarm_minute, isSet=:setIsSet WHERE id= :givenID")
    void updateTimeToSleepElement(int givenID, boolean setMonday, boolean setTuesday, boolean setWednesday, boolean setThursday,
                                  boolean setFriday, boolean setSaturday, boolean setSunday,
                                  int setTime_to_sleep_hour_of_day, int setTime_to_sleep_minute, int setAlarm_hour_of_day,
                                  int setAlarm_minute, boolean setIsSet);

    @Query("UPDATE TimeToSleepElement SET isSet=:setIsSet WHERE id= :givenID")
    void updateIsSet(int givenID, boolean setIsSet);

    @Query("UPDATE TimeToSleepElement SET monday=:setMonday WHERE id= :givenID")
    void updateMonday(int givenID, boolean setMonday);

    @Query("UPDATE TimeToSleepElement SET tuesday=:setTuesday WHERE id= :givenID")
    void updateTuesday(int givenID, boolean setTuesday);

    @Query("UPDATE TimeToSleepElement SET wednesday=:setWednesday WHERE id= :givenID")
    void updateWednesday(int givenID, boolean setWednesday);

    @Query("UPDATE TimeToSleepElement SET thursday=:setThursday WHERE id= :givenID")
    void updateThursday(int givenID, boolean setThursday);

    @Query("UPDATE TimeToSleepElement SET friday=:setFriday WHERE id= :givenID")
    void updateFriday(int givenID, boolean setFriday);

    @Query("UPDATE TimeToSleepElement SET saturday=:setSaturday WHERE id= :givenID")
    void updateSaturday(int givenID, boolean setSaturday);

    @Query("UPDATE TimeToSleepElement SET sunday=:setSunday WHERE id= :givenID")
    void updateSunday(int givenID, boolean setSunday);

    @Query("UPDATE TimeToSleepElement SET time_to_sleep_hour_of_day=:setTime_to_sleep_hour,time_to_sleep_minute=:setTime_to_sleep_minute WHERE id= :givenID")
    void updateTimeToSleep(int givenID, int setTime_to_sleep_hour, int setTime_to_sleep_minute);

    @Query("UPDATE TimeToSleepElement SET alarm_hour_of_day=:setAlarm_time_hour,alarm_minute=:setAlarm_time_minute WHERE id= :givenID")
    void updateAlarmTime(int givenID, int setAlarm_time_hour, int setAlarm_time_minute);

    @Query("DELETE FROM TimeToSleepElement WHERE id != :ID")
    void deleteElement(int ID);

    @Query("SELECT * FROM TimeToSleepElement")
    List<TimeToSleepElement> getAllElements();

    @Query("SELECT * FROM TimeToSleepElement WHERE id LIKE :ID")
    TimeToSleepElement getElementById(Integer ID);
}