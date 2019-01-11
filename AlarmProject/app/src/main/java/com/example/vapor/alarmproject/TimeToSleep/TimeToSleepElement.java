package com.example.vapor.alarmproject.TimeToSleep;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TimeToSleepElement {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name="monday")
    private boolean monday;
    public boolean getMonday(){return monday;}
    public void setMonday(boolean value){this.monday=value;}

    @ColumnInfo(name="tuesday")
    private boolean tuesday;
    public boolean getTuesday(){return tuesday;}
    public void setTuesday(boolean value){this.tuesday=value;}

    @ColumnInfo(name="wednesday")
    private boolean wednesday;
    public boolean getWednesday(){return wednesday;}
    public void setWednesday(boolean value){this.wednesday=value;}

    @ColumnInfo(name="thursday")
    private boolean thursday;
    public boolean getThursday(){return thursday;}
    public void setThursday(boolean value){this.thursday=value;}

    @ColumnInfo(name="friday")
    private boolean friday;
    public boolean getFriday(){return friday;}
    public void setFriday(boolean value){this.friday=value;}

    @ColumnInfo(name="saturday")
    private boolean saturday;
    public boolean getSaturday(){return saturday;}
    public void setSaturday(boolean value){this.saturday=value;}

    @ColumnInfo(name="sunday")
    private boolean sunday;
    public boolean getSunday(){return sunday;}
    public void setSunday(boolean value){this.sunday=value;}



    @ColumnInfo(name = "time_to_sleep_hour_of_day")
    private int time_to_sleep_hour_of_day;
    public int getTime_to_sleep_hour_of_day(){return time_to_sleep_hour_of_day;}
    public void setTime_to_sleep_hour_of_day(int value){this.time_to_sleep_hour_of_day=value;}

    @ColumnInfo(name = "time_to_sleep_minute")
    private int time_to_sleep_minute;
    public int getTime_to_sleep_minute(){return time_to_sleep_minute;}
    public void setTime_to_sleep_minute(int value){this.time_to_sleep_minute=value;}

    @ColumnInfo(name = "alarm_hour_of_day")
    private int alarm_hour_of_day;
    public int getAlarm_hour_of_day(){return alarm_hour_of_day;}
    public void setAlarm_hour_of_day(int value){this.alarm_hour_of_day=value;}

    @ColumnInfo(name = "alarm_minute")
    private int alarm_minute;
    public int getAlarm_minute(){return alarm_minute;}
    public void setAlarm_minute(int value){this.alarm_minute=value;}

    @ColumnInfo(name="isSet")
    private boolean isSet;
    public boolean getIsSet(){return isSet;}
    public void setIsSet(boolean value){this.isSet=value;}

    public TimeToSleepElement(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday,
                              int time_to_sleep_hour_of_day, int time_to_sleep_minute, int alarm_hour_of_day, int alarm_minute, boolean isSet) {

        this.monday=monday;
        this.tuesday=tuesday;
        this.wednesday=wednesday;
        this.thursday=thursday;
        this.friday=friday;
        this.saturday=saturday;
        this.sunday=sunday;
        this.time_to_sleep_hour_of_day=time_to_sleep_hour_of_day;
        this.time_to_sleep_minute=time_to_sleep_minute;
        this.alarm_hour_of_day=alarm_hour_of_day;
        this.alarm_minute=alarm_minute;
        this.isSet=isSet;
    }


    public String timeToSleeptoString() {
        String result="";
        if(time_to_sleep_hour_of_day<10)
            result+="0"+time_to_sleep_hour_of_day;
        else
            result+=time_to_sleep_hour_of_day;
        if(time_to_sleep_minute<10)
            result+=":"+"0"+time_to_sleep_minute;
        else
            result+=":"+time_to_sleep_minute;
        return result;
    }

    public String alarmTimeToString() {
        String result="";
        if(alarm_hour_of_day<10)
            result+="0"+alarm_hour_of_day;
        else
            result+=alarm_hour_of_day;
        if(alarm_minute<10)
            result+=":"+"0"+alarm_minute;
        else
            result+=":"+alarm_minute;
        return result;
    }

    public String sleepDurationToString(){
        String result="";

        int longTimeToSleep=(time_to_sleep_hour_of_day*3600)+(time_to_sleep_minute*60);
        int longAlarmTime=(alarm_hour_of_day*3600)+(alarm_minute*60);
        int res;

        if(longTimeToSleep>longAlarmTime)
            res=((24*3600)-(longTimeToSleep))+longAlarmTime;
        else if(longTimeToSleep<longAlarmTime)
            res=(longAlarmTime-longTimeToSleep);
        else
            res=0;

        if(res==0)
            return "0h";

        int hoursOfSleep=res/3600;
        int minutesOfSleep=(res-(hoursOfSleep*3600))/60;

        if(minutesOfSleep==0){
            result=hoursOfSleep+"h";
        }

        else{
            result=hoursOfSleep+"h " + minutesOfSleep + "m";
        }

        return result;
    }

}
