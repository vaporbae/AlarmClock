package com.example.vapor.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.vapor.alarmproject.Alarm.AddAlarm;
import com.example.vapor.alarmproject.Alarm.AlarmElement;
import com.example.vapor.alarmproject.Alarm.AlertReceiver;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmClockFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmClockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmClockFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayAdapter<AlarmElement> itemsAdapter;
    private ArrayList<AlarmElement> alarmElements;
    private AppDatabase database;

    @Override
    public void onResume() {
        super.onResume();
        alarmElements =(ArrayList<AlarmElement>) database.alarmElementDao().getAllElements();

        itemsAdapter.clear();
        itemsAdapter.addAll(alarmElements);
        itemsAdapter.notifyDataSetChanged();
    }

    private OnFragmentInteractionListener mListener;

    public AlarmClockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmClockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmClockFragment newInstance(String param1, String param2) {
        AlarmClockFragment fragment = new AlarmClockFragment();
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

    //TUTAJ ZMIENIAC WYGLAD TEKST I DUPE CALA OGOLEM PRZEPRASZAM ZE TAK POWIEDZIALEM
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_alarm_clock, container, false);


        Context appContext = getActivity().getApplicationContext();
        database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        alarmElements =(ArrayList<AlarmElement>) database.alarmElementDao().getAllElements();
        itemsAdapter = new MyListAdapter(getActivity(), R.layout.alarm_row, alarmElements);
        final ListView lvItems = rootView.findViewById(R.id.alarm_list);
        lvItems.setAdapter(itemsAdapter);

        ImageButton addAlarm = rootView.findViewById(R.id.add_alarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAlarm.class);
                startActivity(intent);
                lvItems.invalidateViews();
            }
        });




        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ID = alarmElements.get(position).getId();

                Intent intent = new Intent(getActivity(), EditAlarm.class);
                intent.putExtra("ID_TAG", ID);

                database.alarmElementDao().updateIsSet(alarmElements.get(position).getId(),false);
                AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intentt=new Intent(getActivity(),AlertReceiver.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),ID,intentt,0);
                alarmManager.cancel(pendingIntent);

                startActivity(intent);
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

    private class ViewHolder {
        Switch mySwitch;
        TextView hour;
        TextView desc;
    }

    //LISTAAAAAAAAAAA ALARMOW
    private class MyListAdapter extends ArrayAdapter<AlarmElement> {
        private int layout;
        private MyListAdapter(Context context, int resource, ArrayList<AlarmElement> objects){
            super(context,resource,objects);
            layout=resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi =  LayoutInflater.from(this.getContext());
                convertView = vi.inflate(R.layout.alarm_row, null);

                holder = new ViewHolder();

                holder.mySwitch = convertView.findViewById(R.id.task_switch);
                holder.hour = convertView.findViewById(R.id.task_title);
                holder.desc=convertView.findViewById(R.id.description);

                convertView.setTag(holder);

                holder.mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            database.alarmElementDao().updateIsSet(alarmElements.get(position).getId(),true);
                            Calendar c=Calendar.getInstance();
                            int id=alarmElements.get(position).getId();
                            c.set(Calendar.HOUR_OF_DAY, database.alarmElementDao().getElementById(id).getHour_of_day());
                            c.set(Calendar.MINUTE,database.alarmElementDao().getElementById(id).getMinute());
                            c.set(Calendar.SECOND,0);

                            AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                            Intent intent=new Intent(getActivity(),AlertReceiver.class);
                            PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),id,intent,0);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                        }
                        else{
                            database.alarmElementDao().updateIsSet(alarmElements.get(position).getId(),false);
                            int id=alarmElements.get(position).getId();
                            AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                            Intent intent=new Intent(getActivity(),AlertReceiver.class);
                            PendingIntent pendingIntent=PendingIntent.getBroadcast(getActivity(),id,intent,0);
                            alarmManager.cancel(pendingIntent);
                        }
                    }
                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int id=alarmElements.get(position).getId();
            String s;

            int hour=database.alarmElementDao().getElementById(id).getHour_of_day();
            if (hour<10){
                s="0" + hour;
            }
            else{
                s=""+hour;
            }

            int minute=database.alarmElementDao().getElementById(id).getMinute();
            if(minute<10){
                s+=":"+"0"+minute;
            }
            else{
                s+=":"+minute;
            }

            holder.hour.setText(s);
            String s1=database.alarmElementDao().getElementById(id).getDescription();
            holder.desc.setText(s1);
            boolean switchState=database.alarmElementDao().getElementById(id).getIsSet();
            holder.mySwitch.setChecked(switchState);
            holder.mySwitch.setFocusable(false);
            return convertView;
        }
    }

    public void updateTimeText(Calendar c){
        String timeText="Alarm set for: ";
        timeText+=DateFormat.getTimeInstance(DateFormat.SHORT).format(c);
        //mTextView.setText(timeText);
    }

    public static class EditAlarm extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_alarm);

            TimePicker picker=findViewById(R.id.timePicker1);
            EditText descriptionText=findViewById(R.id.editText3);

            Context appContext = getApplicationContext();
            final int editableId = getIntent().getIntExtra("ID_TAG", 0);
            final AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

            AlarmElement elementToEdit = database.alarmElementDao().getElementById(editableId);
            picker.setCurrentHour(elementToEdit.getHour_of_day());
            picker.setCurrentMinute(elementToEdit.getMinute());
            descriptionText.setText(elementToEdit.getDescription());


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

                    database.alarmElementDao().updateAlarm(editableId,hour,minute, description);

                    finish();
                }
            });

            Button removeBtn = findViewById(R.id.removeButton);
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database.alarmElementDao().deleteElement(editableId);
                    finish();
                }
            });

        }
    }
}
