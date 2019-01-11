package com.example.vapor.alarmproject.WorldClock;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.vapor.alarmproject.AppDatabase;
import com.example.vapor.alarmproject.R;

import java.util.ArrayList;

public class DeleteWorldClock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_world_clock);


        Context appContext = getApplicationContext();
        final int editableId = getIntent().getIntExtra("ID_TAG", 0);
        final AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();




        Button cancel = findViewById(R.id.CancelDeletion);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Button removeBtn = findViewById(R.id.DeleteClock);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.clockElementDao().deleteElement(editableId);
                finish();
            }
        });

    }
}