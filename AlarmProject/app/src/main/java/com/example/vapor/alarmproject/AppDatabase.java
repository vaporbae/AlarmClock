package com.example.vapor.alarmproject;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.vapor.alarmproject.Alarm.AlarmElement;
import com.example.vapor.alarmproject.Alarm.AlarmElementDao;
import com.example.vapor.alarmproject.TimeToSleep.TimeToSleepElementDao;
import com.example.vapor.alarmproject.WakeMeUpHere.PlaceElement;
import com.example.vapor.alarmproject.WakeMeUpHere.PlaceElementDao;
import com.example.vapor.alarmproject.WorldClock.ClockElement;
import com.example.vapor.alarmproject.WorldClock.ClockElementDao;
import com.example.vapor.alarmproject.TimeToSleep.TimeToSleepElement;

@Database(entities = {AlarmElement.class, ClockElement.class, TimeToSleepElement.class, PlaceElement.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AlarmDB";

    public abstract AlarmElementDao alarmElementDao();
    public abstract ClockElementDao clockElementDao();
    public abstract TimeToSleepElementDao timeToSleepElementDao();
    public abstract PlaceElementDao placeElementDao();

}
