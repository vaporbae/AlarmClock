package com.example.vapor.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vapor.alarmproject.Alarm.AlertReceiver;
import com.example.vapor.alarmproject.WorldClock.AddWorldClock;
import com.example.vapor.alarmproject.WorldClock.ClockElement;
import com.example.vapor.alarmproject.WorldClock.DeleteWorldClock;
import com.example.vapor.alarmproject.WorldClock.TimeZoneApi;
import com.example.vapor.alarmproject.WorldClock.Timezone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorldClocksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorldClocksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorldClocksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayAdapter<ClockElement> itemsAdapter;
    private ArrayList<ClockElement> clockElements;
    private AppDatabase database;
    private ListView lvItems;

    private Retrofit retrofit;
    private TimeZoneApi timeZoneApi;
    private OnFragmentInteractionListener mListener;

    public WorldClocksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorldClocksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorldClocksFragment newInstance(String param1, String param2) {
        WorldClocksFragment fragment = new WorldClocksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        clockElements = (ArrayList<ClockElement>) database.clockElementDao().getAllElements();

        itemsAdapter.clear();
        itemsAdapter.addAll(clockElements);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            //to jest powtarzane co sekunde
            //to w jaki sposob sa tworzone stringi jest niewazne
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //Jesli masz tylko 1 element w listview wszystko ladnie dziala
            //kiedy jest wiecej wydaje sie jakby wszystkie elementy skupialy sie w ostatnim row
            //stringi z czasem i opoznieniem są zapisywane w bazie wiec po wlaczeniu aplikacji powinno wczytywac je takie jakie
            //zostaly zapisane przy wylaczeniu programu
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            @Override
            public void run() {

                if (clockElements.size() > 0) {
                    Calendar rightNoww;
                    rightNoww = Calendar.getInstance();
                    int nowMinute = rightNoww.get(Calendar.MINUTE);


                        if (InternetConnection.checkConnection((getActivity().getApplicationContext()))) {
                            final ProgressDialog dialog;


                            for (int iterator = 0; iterator < clockElements.size(); iterator++) {
                                Long tsLong = System.currentTimeMillis() / 1000;
                                String ts = tsLong.toString();
                                final int idd = clockElements.get(iterator).getId();
                                String loc = database.clockElementDao().getElementById(idd).getLocation();
                                //tu po prostu pobieram z api timezone asynchronicznie
                                Call<Timezone> call = timeZoneApi.getTimeZone(loc, ts, "AIzaSyBgK5kCA04PmV0TgPlBgFBY2Hg6MGewwxQ");

                                call.enqueue(new Callback<Timezone>() {
                                    @Override
                                    public void onResponse(Call<Timezone> call, Response<Timezone> response) {
                                        int dstOffSet;
                                        int rawOffSet;
                                        String timeZoneId;
                                        String timeZoneName;
                                        String dayString;
                                        String hourString;


                                        int thisHour;
                                        Calendar rightNow;

                                        final int thisRawOffset = (TimeZone.getDefault().getRawOffset() / 1000);
                                        rightNow = Calendar.getInstance();
                                        thisHour = rightNow.get(Calendar.HOUR_OF_DAY);

                                        Timezone timezone = response.body();
                                        dstOffSet = timezone.getDstOffset();
                                        rawOffSet = timezone.getRawOffset() - thisRawOffset;
                                        timeZoneId = timezone.getTimeZoneId();
                                        timeZoneName = timezone.getTimeZoneName();

                                        currentMinute = rightNow.get(Calendar.MINUTE);

                                        //hourString = Integer.toString((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) + ":" + Integer.toString(rightNow.get(Calendar.MINUTE));

                                        hourString = "";
                                        if (((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) < 10)
                                            hourString += "0" + ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24);
                                        else
                                            hourString += ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24);
                                        if ((rightNow.get(Calendar.MINUTE)) < 10)
                                            hourString += ":" + "0" + (rightNow.get(Calendar.MINUTE));
                                        else
                                            hourString += ":" + (rightNow.get(Calendar.MINUTE));

                                        dayString = "";

                                        if ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24 < 12 && thisHour < 24 && thisHour > 12
                                                && ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) - thisHour > 12) {
                                            dayString += getResources().getString(R.string.tomorrow);
                                        } else if ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24 > 12 && thisHour > 0 && thisHour < 12
                                                && ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) - thisHour > 12)
                                            dayString += getResources().getString(R.string.yesterday);
                                        else
                                            dayString += getResources().getString(R.string.today);

                                        if (rawOffSet < 0) {
                                            dayString += ", -" + rawOffSet / 3600 + "H";
                                        }

                                        if (rawOffSet >= 0) {
                                            dayString += ", +" + rawOffSet / 3600 + "H";
                                        }
                                        //updatuje dane o godzinie i przesunieciu w bazie
                                        database.clockElementDao().updateStrings(idd, dayString, hourString);

                                    }

                                    @Override
                                    public void onFailure(Call<Timezone> call, Throwable t) {
                                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            clockElements = (ArrayList<ClockElement>) database.clockElementDao().getAllElements();
                            //refresh listy
                            itemsAdapter.clear();
                            itemsAdapter.addAll(clockElements);
                            itemsAdapter.notifyDataSetChanged();

                    }
                }
                            handler.postDelayed(this, 10000);



            }
        }, 10000);

    }

    public int currentMinute=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_world_clocks, container, false);

        Context appContext = getActivity().getApplicationContext();
        database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/timezone/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        if (InternetConnection.checkConnection((getActivity().getApplicationContext()))) {
            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */

            timeZoneApi = retrofit.create(TimeZoneApi.class);

            clockElements = (ArrayList<ClockElement>) database.clockElementDao().getAllElements();

            //to jest to samo co w powtarzajacej sie co sekunde petli
            for (int iterator = 0; iterator < clockElements.size(); iterator++) {
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                final int idd = clockElements.get(iterator).getId();
                String loc = database.clockElementDao().getElementById(idd).getLocation();
                Call<Timezone> call = timeZoneApi.getTimeZone(loc, ts, "AIzaSyBgK5kCA04PmV0TgPlBgFBY2Hg6MGewwxQ");



                call.enqueue(new Callback<Timezone>() {
                                 @Override
                                 public void onResponse(Call<Timezone> call, Response<Timezone> response) {
                                     int dstOffSet;
                                     int rawOffSet;
                                     String timeZoneId;
                                     String timeZoneName;
                                     String dayString;
                                     String hourString;

                                     int thisHour;
                                     Calendar rightNow;

                                     final int thisRawOffset = (TimeZone.getDefault().getRawOffset() / 1000);
                                     rightNow = Calendar.getInstance();
                                     thisHour = rightNow.get(Calendar.HOUR_OF_DAY);

                                     currentMinute=rightNow.get(Calendar.MINUTE);

                                     Timezone timezone = response.body();
                                     dstOffSet = timezone.getDstOffset();
                                     rawOffSet = timezone.getRawOffset() - thisRawOffset;
                                     timeZoneId = timezone.getTimeZoneId();
                                     timeZoneName = timezone.getTimeZoneName();


                                     //hourString = Integer.toString((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) + ":" + Integer.toString(rightNow.get(Calendar.MINUTE));
                                     hourString = "";
                                     if (((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) < 10)
                                         hourString += "0" + ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24);
                                     else
                                         hourString += ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24);
                                     if ((rightNow.get(Calendar.MINUTE)) < 10)
                                         hourString += ":" + "0" + (rightNow.get(Calendar.MINUTE));
                                     else
                                         hourString += ":" + (rightNow.get(Calendar.MINUTE));

                                     dayString = "";

                                     if ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24 < 12 && thisHour < 24 && thisHour > 12
                                             && ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) - thisHour > 12) {
                                         dayString += getResources().getString(R.string.tomorrow);
                                     } else if ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24 > 12 && thisHour > 0 && thisHour < 12
                                             && ((thisHour + (rawOffSet / 3600) + (rawOffSet % 3600)) % 24) - thisHour > 12)
                                         dayString += getResources().getString(R.string.yesterday);
                                     else
                                         dayString += getResources().getString(R.string.today);

                                     if (rawOffSet < 0) {
                                         dayString += ", -" + rawOffSet / 3600 + "H";
                                     }

                                     if (rawOffSet >= 0) {
                                         dayString += ", +" + rawOffSet / 3600 + "H";
                                     }

                                     database.clockElementDao().updateStrings(idd, dayString, hourString);

                                     clockElements = (ArrayList<ClockElement>) database.clockElementDao().getAllElements();
                                     //dayStrings.put(String.valueOf(idd),dayString);
                                     //hourStrings.put(String.valueOf(idd),hourString);

                                 }

                                 @Override
                                 public void onFailure(Call<Timezone> call, Throwable t) {
                                     Log.e("DOC_",t.getMessage());
                                     Log.e("DOC_", call.request().toString());
                                     Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                                 }

                             }
                );

            }
        }
        else {
                Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
            }


        itemsAdapter = new WorldClocksFragment.MyListAdapter(getActivity(), R.layout.alarm_row, clockElements);
        lvItems = rootView.findViewById(R.id.clock_list);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setLongClickable(true);

        ImageButton addAlarm = rootView.findViewById(R.id.add_clock);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddWorldClock.class);
                startActivity(intent);
                //lvItems.invalidateViews();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int ID = clockElements.get(position).getId();

                Intent intent = new Intent(getActivity(), DeleteWorldClock.class);
                intent.putExtra("ID_TAG", ID);

                startActivity(intent);
                return false;
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

    class refreshClocks extends TimerTask {
        public void run() {
            itemsAdapter.clear();
            itemsAdapter.addAll(clockElements);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        TextView day;
        TextView city;
        TextView time;
    }

    //LISTAAAAAAAAAAA ALARMOW
    private class MyListAdapter extends ArrayAdapter<ClockElement> {
        ViewHolder holder;
        View convertView;
        private int layout;
        private MyListAdapter(Context context, int resource, ArrayList<ClockElement> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, final View convertVieww, ViewGroup parent) {
            convertView = convertVieww;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = LayoutInflater.from(this.getContext());
                convertView = vi.inflate(R.layout.world_clock_row, null);

                holder = new ViewHolder();

                holder.city = convertView.findViewById(R.id.city);
                holder.day = convertView.findViewById(R.id.day);
                holder.time = convertView.findViewById(R.id.timeNow);

                convertView.setTag(holder);

                //on long item click delete

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                int id = clockElements.get(position).getId();
                //tu po prostu pobieram z bazy dane do wyswietlenia
                holder.time.setText(database.clockElementDao().getElementById(id).getDayString());
                holder.city.setText(database.clockElementDao().getElementById(id).getCity());
                holder.day.setText(database.clockElementDao().getElementById(id).getHourString());
            } catch (Exception e) {

            }
            return convertView;
        }
    }
}
