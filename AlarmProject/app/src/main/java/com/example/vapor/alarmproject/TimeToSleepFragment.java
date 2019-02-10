package com.example.vapor.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.vapor.alarmproject.TimeToSleep.AlarmReceiver;
import com.example.vapor.alarmproject.TimeToSleep.TimeToSleepElement;
import com.example.vapor.alarmproject.TimeToSleep.TimeToSleepReceiver;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeToSleepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeToSleepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeToSleepFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppDatabase database;
    private boolean monday;
    private boolean tuesday;
    private boolean  wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private int time_to_sleep_hour;
    private int time_to_sleep_minute;
    private int alarm_time_hour;
    private int alarm_time_minute;
    private boolean isSet;
    private TimeToSleepElement timeToSleepElement;

    private String timeToSleepString;
    private String alarmTimeString;
    private String sleepDuration;
    private String daysOfTheWeek;

    private TextView daysOfTheWeekTextView;
    private Button mondayButton;
    private Button tuesdayButton;
    private Button wednesdayButton;
    private Button thursdayButton;
    private Button fridayButton;
    private Button saturdayButton;
    private Button sundayButton;
    private Button timeToSleepButton;
    private Button alarmTimeButton;
    private TextView sleepDurationTextView;
    private Switch isSetSwitch;

    private OnFragmentInteractionListener mListener;

    public TimeToSleepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeToSleepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeToSleepFragment newInstance(String param1, String param2) {
        TimeToSleepFragment fragment = new TimeToSleepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_time_to_sleep, container, false);

        Context appContext = getActivity().getApplicationContext();
        database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        database.timeToSleepElementDao().insertAll(new TimeToSleepElement(false,false,false,false,false,
                                                        false,false,22,0,
                                                            6,30,false));
        database.timeToSleepElementDao().deleteElement(1);

        timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
        loadData();

        daysOfTheWeekTextView=rootView.findViewById(R.id.days_of_the_week);
        mondayButton=rootView.findViewById(R.id.monday);
        tuesdayButton=rootView.findViewById(R.id.tuesday);
        wednesdayButton=rootView.findViewById(R.id.wednesday);
        thursdayButton=rootView.findViewById(R.id.thursday);
        fridayButton=rootView.findViewById(R.id.friday);
        saturdayButton=rootView.findViewById(R.id.saturday);
        sundayButton=rootView.findViewById(R.id.sunday);
        timeToSleepButton=rootView.findViewById(R.id.time_to_sleep);
        alarmTimeButton=rootView.findViewById(R.id.alarm_time);
        sleepDurationTextView=rootView.findViewById(R.id.sleep_duration);
        isSetSwitch=rootView.findViewById(R.id.isSet);

        loadElements();

        mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(monday==false)
                    monday=true;
                else
                    monday=false;
                isSet=false;
                database.timeToSleepElementDao().updateMonday(1,monday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tuesday==false)
                    tuesday=true;
                else
                    tuesday=false;
                isSet=false;
                database.timeToSleepElementDao().updateTuesday(1,tuesday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wednesday==false)
                    wednesday=true;
                else
                    wednesday=false;
                isSet=false;
                database.timeToSleepElementDao().updateWednesday(1,wednesday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thursday==false)
                    thursday=true;
                else
                    thursday=false;
                isSet=false;
                database.timeToSleepElementDao().updateThursday(1,thursday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friday==false)
                    friday=true;
                else
                    friday=false;
                isSet=false;
                database.timeToSleepElementDao().updateFriday(1,friday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        saturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saturday==false)
                    saturday=true;
                else
                    saturday=false;
                isSet=false;
                database.timeToSleepElementDao().updateSaturday(1,saturday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        sundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sunday==false)
                    sunday=true;
                else
                    sunday=false;
                isSet=false;
                database.timeToSleepElementDao().updateSunday(1,sunday);
                database.timeToSleepElementDao().updateIsSet(1,isSet);
                timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                loadData();
                loadElements();
                CancelAlarms();
                CancelTimeToSleep();
            }
        });

        timeToSleepButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Calendar myCalender = Calendar.getInstance();
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);


                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            myCalender.set(Calendar.MINUTE, minute);

                            database.timeToSleepElementDao().updateIsSet(1,false);
                            database.timeToSleepElementDao().updateTimeToSleep(1,hourOfDay,minute);
                            timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                            loadData();
                            loadElements();
                            CancelAlarms();
                            CancelTimeToSleep();
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                timePickerDialog.setTitle("Choose hour:");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        alarmTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Calendar myCalender = Calendar.getInstance();
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);


                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            myCalender.set(Calendar.MINUTE, minute);

                            database.timeToSleepElementDao().updateIsSet(1,false);
                            database.timeToSleepElementDao().updateAlarmTime(1,hourOfDay,minute);
                            timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                            loadData();
                            loadElements();
                            CancelAlarms();
                            CancelTimeToSleep();
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                timePickerDialog.setTitle("Choose hour:");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        isSetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    database.timeToSleepElementDao().updateIsSet(1,true);
                    timeToSleepElement=database.timeToSleepElementDao().getElementById(1);

                    SetTimeToSleepAlarm();
                    SetAlarm();
                }
                else{
                    database.timeToSleepElementDao().updateIsSet(1,false);
                    timeToSleepElement=database.timeToSleepElementDao().getElementById(1);
                    CancelAlarms();
                    CancelTimeToSleep();
                }
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void loadData(){
        monday=timeToSleepElement.getMonday();
        tuesday=timeToSleepElement.getTuesday();
        wednesday=timeToSleepElement.getWednesday();
        thursday=timeToSleepElement.getThursday();
        friday=timeToSleepElement.getFriday();
        saturday=timeToSleepElement.getSaturday();
        sunday=timeToSleepElement.getSunday();
        saturday=timeToSleepElement.getSaturday();
        time_to_sleep_hour=timeToSleepElement.getTime_to_sleep_hour_of_day();
        time_to_sleep_minute=timeToSleepElement.getTime_to_sleep_minute();
        alarm_time_minute=timeToSleepElement.getAlarm_minute();
        alarm_time_hour=timeToSleepElement.getAlarm_hour_of_day();

        daysOfTheWeek="";
        if(monday==true)
            daysOfTheWeek+=getResources().getString(R.string.monday)+",";
        if(tuesday==true)
            daysOfTheWeek+=getResources().getString(R.string.tuesday)+",";
        if(wednesday==true)
            daysOfTheWeek+=getResources().getString(R.string.wednesday)+",";
        if(thursday==true)
            daysOfTheWeek+=getResources().getString(R.string.thursday)+",";
        if(friday==true)
            daysOfTheWeek+=getResources().getString(R.string.friday)+",";
        if(saturday==true)
            daysOfTheWeek+=getResources().getString(R.string.saturday)+",";
        if(sunday==true)
            daysOfTheWeek+=getResources().getString(R.string.sunday)+",";
        if(monday==true&&tuesday==true&&wednesday==true&&thursday==true&&friday==true&&saturday==true&&sunday==true)
            daysOfTheWeek=getResources().getString(R.string.everyday);

        if(monday==false&&tuesday==false&&wednesday==false&&thursday==false&&friday==false&&saturday==false&&sunday==false){
            daysOfTheWeek=getResources().getString(R.string.tomorrow);
        }

        timeToSleepString=timeToSleepElement.timeToSleeptoString();
        alarmTimeString=timeToSleepElement.alarmTimeToString();
        sleepDuration=timeToSleepElement.sleepDurationToString();
    }

    public void loadElements(){
        daysOfTheWeekTextView.setText(daysOfTheWeek);
        mondayButton.setText("pon");
        if(monday==true)
            mondayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            mondayButton.setTextColor(getResources().getColor(R.color.grey));

        tuesdayButton.setText("wt");
        if(tuesday==true)
            tuesdayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            tuesdayButton.setTextColor(getResources().getColor(R.color.grey));

        wednesdayButton.setText("śr");
        if(wednesday==true)
            wednesdayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            wednesdayButton.setTextColor(getResources().getColor(R.color.grey));

        thursdayButton.setText("czw");
        if(thursday==true)
            thursdayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            thursdayButton.setTextColor(getResources().getColor(R.color.grey));

        fridayButton.setText("pt");
        if(friday==true)
            fridayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            fridayButton.setTextColor(getResources().getColor(R.color.grey));

        saturdayButton.setText("so");
        if(saturday==true)
            saturdayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            saturdayButton.setTextColor(getResources().getColor(R.color.grey));

        sundayButton.setText("nie");
        if(sunday==true)
            sundayButton.setTextColor(getResources().getColor(R.color.colorAccent));
        else
            sundayButton.setTextColor(getResources().getColor(R.color.grey));

        timeToSleepButton.setText(timeToSleepString);
        alarmTimeButton.setText(alarmTimeString);
        sleepDurationTextView.setText(sleepDuration);
        isSetSwitch.setChecked(isSet);
    }

    private void SetTimeToSleepAlarm(){
        if(monday==false&&tuesday==false&&wednesday==false&&thursday==false&&friday==false&&saturday==false&&sunday==false) {
            Calendar c=Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
            c.set(Calendar.MINUTE, time_to_sleep_minute);
            c.set(Calendar.SECOND,0);

            AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7284,intent,0);
            alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        }




        if(monday==true&&tuesday==true&&wednesday==true&&thursday==true&&friday==true&&saturday==true&&sunday==true){
            Calendar c=Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
            c.set(Calendar.MINUTE, time_to_sleep_minute);
            c.set(Calendar.SECOND,0);

            AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7284,intent,0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else{
            if(monday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,1); //monday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7285,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(tuesday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,2); //tuesday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7286,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(wednesday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,3); //wednesday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7287,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(thursday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,4); //thursday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7288,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(friday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,5); //friday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7289,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(saturday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,6); //saturday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7290,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(sunday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,7); //sunday
                c.set(Calendar.HOUR_OF_DAY, time_to_sleep_hour);
                c.set(Calendar.MINUTE, time_to_sleep_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7291,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }
        }


    }

    private void SetAlarm(){

        if(monday==false&&tuesday==false&&wednesday==false&&thursday==false&&friday==false&&saturday==false&&sunday==false) {
            Calendar c=Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
            c.set(Calendar.MINUTE, alarm_time_minute);
            c.set(Calendar.SECOND,0);

            AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent=new Intent(getActivity(),AlarmReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7264,intent,0);
            alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        }




        if(monday==true&&tuesday==true&&wednesday==true&&thursday==true&&friday==true&&saturday==true&&sunday==true){
            Calendar c=Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
            c.set(Calendar.MINUTE, alarm_time_minute);
            c.set(Calendar.SECOND,0);

            AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent=new Intent(getActivity(),AlarmReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7264,intent,0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else{
            if(monday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,2); //monday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7265,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(tuesday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,3); //tuesday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7266,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(wednesday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,4); //wednesday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);
                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7267,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(thursday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,5); //thursday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7268,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(friday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,6); //friday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7269,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(saturday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,7); //saturday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7270,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }

            if(sunday==true){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK,1); //sunday
                c.set(Calendar.HOUR_OF_DAY, alarm_time_hour);
                c.set(Calendar.MINUTE, alarm_time_minute);
                c.set(Calendar.SECOND,0);

                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent=new Intent(getActivity(),AlarmReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7271,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pendingIntent);
            }
        }
    }

    public void CancelTimeToSleep(){
        AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(getActivity(),TimeToSleepReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7284,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7285,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7286,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7287,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7288,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7289,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7290,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7291,intent,0);
        alarmManager.cancel(pendingIntent);

    }



    public void CancelAlarms(){
        AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(getActivity(),AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),7264,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7265,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7266,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7267,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7268,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7269,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7270,intent,0);
        alarmManager.cancel(pendingIntent);

        pendingIntent=PendingIntent.getBroadcast(getActivity(),7271,intent,0);
        alarmManager.cancel(pendingIntent);


    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
