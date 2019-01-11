package com.example.vapor.alarmproject.Alarm;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.vapor.alarmproject.AppDatabase;
import com.example.vapor.alarmproject.R;

public class AddAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);


        Context appContext = getApplicationContext();
        final AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button save=findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePicker picker=findViewById(R.id.timePicker1);
                EditText descriptionText=findViewById(R.id.editText3);
                int hour=picker.getCurrentHour();
                int minute=picker.getCurrentMinute();
                String description=descriptionText.getText().toString();

                database.alarmElementDao().insertAll(new AlarmElement(hour,minute, description,false));
                finish();
            }
        });

    }
}

