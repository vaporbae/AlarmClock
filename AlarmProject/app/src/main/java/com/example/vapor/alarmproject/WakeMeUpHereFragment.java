package com.example.vapor.alarmproject;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vapor.alarmproject.WakeMeUpHere.PlaceElement;
import com.example.vapor.alarmproject.WakeMeUpHere.ProximityIntentReceiver;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WakeMeUpHereFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WakeMeUpHereFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WakeMeUpHereFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATE = 1000; // in Milliseconds
    private static long POINT_RADIUS = 1000; // in Meters
    private static final long PROX_ALERT_EXPIRATION = -1;
    private static final String POINT_LATITUDE_KEY = "POINT_LATITUDE_KEY";
    private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY";
    private static final String PROX_ALERT_INTENT = "com.javacodegeeks.android.lbs.ProximityAlert";
    private static final NumberFormat nf = new DecimalFormat("##.########");
    private LocationManager locationManager;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private Button findCoordinatesButton;
    private Button savePointButton;

    private AppDatabase database;

    private OnFragmentInteractionListener mListener;

    public WakeMeUpHereFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WakeMeUpHereFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WakeMeUpHereFragment newInstance(String param1, String param2) {
        WakeMeUpHereFragment fragment = new WakeMeUpHereFragment();
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

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    public static final int LOCATION_REQUEST_CODE = 1001;
    private GoogleMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wake_me_up_here,container,false);
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_wake_me_up_here, container, false);




        //return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context appContext = getActivity().getApplicationContext();
        database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);



        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATE,
                    MINIMUM_DISTANCECHANGE_FOR_UPDATE,
                    new MyLocationListener()
            );
            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        }



        //latitudeEditText = (EditText) rootView.findViewById(R.id.point_latitude);
        //longitudeEditText = (EditText) rootView.findViewById(R.id.point_longitude);
        //findCoordinatesButton = (Button) rootView.findViewById(R.id.find_coordinates_button);
        //savePointButton = (Button) rootView.findViewById(R.id.save_point_button);

        /*findCoordinatesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                populateCoordinatesFromLastKnownLocation();
            }
        });
        savePointButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProximityAlertPoint();
            }
        });
        */



        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();


        database.placeElementDao().insertAll(new PlaceElement(-34.0, 151.0,"1000"));
        database.placeElementDao().deleteElement(1);

        View mapFragment = (MapView) getView().findViewById(R.id.map);
        //SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
        //        .findFragmentById(R.id.map);
        if(mapFragment!=null)
        {
            ((MapView) mapFragment).onCreate(null);
            ((MapView) mapFragment).onResume();
            ((MapView) mapFragment).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    LatLng position = new LatLng(database.placeElementDao().getElementById(1).getLat(),
                            database.placeElementDao().getElementById(1).getLang());
                    mMap.addMarker(new MarkerOptions().position(position).title("Pos"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                    if(database.placeElementDao().getElementById(1).getLat()!=-34.0&&
                            database.placeElementDao().getElementById(1).getLang()!=151.0)
                    {
                        String r = database.placeElementDao().getElementById(1).getRadius();
                        POINT_RADIUS=new Long(r).longValue();
                        Circle circle = mMap.addCircle(new CircleOptions()
                                .center(new LatLng(database.placeElementDao().getElementById(1).getLat(),
                                        database.placeElementDao().getElementById(1).getLang()))
                                .radius(POINT_RADIUS)
                                .strokeColor(Color.RED));
                    }

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
                    } else {
                        mMap.setMyLocationEnabled(true);
                    }
                }
            });
        }


        final EditText radius = (EditText) getView().findViewById(R.id.radius);

        radius.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String str=radius.getText().toString();
                double radiuss;
                try{
                    radiuss=new Double(str).doubleValue();
                    POINT_RADIUS=new Long(str).longValue();
                    if(database.placeElementDao().getElementById(1).getLat()!=-34.0&&
                            database.placeElementDao().getElementById(1).getLang()!=151.0) {
                        database.placeElementDao().getElementById(1).setRadius(str);
                        Circle circle = mMap.addCircle(new CircleOptions()
                                .center(new LatLng(database.placeElementDao().getElementById(1).getLat(),
                                        database.placeElementDao().getElementById(1).getLang()))
                                .radius(POINT_RADIUS)
                                .strokeColor(Color.RED));
                    }
                }
                catch (Exception e){
                    radiuss=new Double(POINT_RADIUS).doubleValue();
                }
                //zrobic zeby odrazu sie radius zmienial
                //dodac baze zeby przechowywac te lokalizacje co byla wczesniej wklepana
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //SupportPlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
          //      getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
                } else {
                    mMap.setMyLocationEnabled(true);
                }

                database.placeElementDao().updateLatLang(1,place.getLatLng().latitude,place.getLatLng().longitude);

                String str=radius.getText().toString();
                double radiuss;
                try{
                    radiuss=new Double(str).doubleValue();
                    POINT_RADIUS=new Long(str).longValue();
                    database.placeElementDao().updateRadius(1,str);
                }
                catch (Exception e){
                    radiuss=new Double(POINT_RADIUS).doubleValue();
                }

                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                        .radius(radiuss)
                        .strokeColor(Color.RED));

                addProximityAlert(place.getLatLng().latitude,place.getLatLng().longitude); //tak
            }

            @Override
            public void onError(Status status) {

            }
        });
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



    Location location;
    private void saveProximityAlertPoint() {
        Location location;
        if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            //tu zmienilam
            location =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            saveCoordinatesInPreferences((float)location.getLatitude(), //to przenioslam 3 linijki spoza ifa
                    (float)location.getLongitude());
            addProximityAlert(location.getLatitude(), location.getLongitude());
            if (location == null) {
                Toast.makeText(getActivity(), "No last known location. Aborting...", //tu zmienilam
                        Toast.LENGTH_LONG).show();
                return;
            }
        }


    }
    private void addProximityAlert(double latitude, double longitude) {

        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0); //tu zmienilam

        if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            //tu zmienilam
            locationManager.addProximityAlert(
                    latitude, // the latitude of the central point of the alert region
                    longitude, // the longitude of the central point of the alert region
                    POINT_RADIUS, // the radius of the central point of the alert region, in meters
                    PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration
                    proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
            );
        }

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        getActivity().registerReceiver(new ProximityIntentReceiver(), filter); //tu zmienilam
    }

    private void populateCoordinatesFromLastKnownLocation() {
        if ( ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            //tu zmienilam
            Location location =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitudeEditText.setText(nf.format(location.getLatitude()));
                longitudeEditText.setText(nf.format(location.getLongitude()));
            }
        }
    }

    private void saveCoordinatesInPreferences(float latitude, float longitude) {
        SharedPreferences prefs =
        getActivity().getSharedPreferences(getClass().getSimpleName(), //tu zmienilam
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(POINT_LATITUDE_KEY, latitude);
        prefsEditor.putFloat(POINT_LONGITUDE_KEY, longitude);
        prefsEditor.commit();
    }
    private Location retrievelocationFromPreferences() {
        SharedPreferences prefs =
        getActivity().getSharedPreferences(getClass().getSimpleName(), //tu zmienilam
                Context.MODE_PRIVATE);
        Location location = new Location("POINT_LOCATION");
        location.setLatitude(prefs.getFloat(POINT_LATITUDE_KEY, 0));
        location.setLongitude(prefs.getFloat(POINT_LONGITUDE_KEY, 0));
        return location;
    }

    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Location pointLocation = retrievelocationFromPreferences();
            float distance = location.distanceTo(pointLocation);
            Toast.makeText(getActivity(), //tu zmienilam
                    "Distance from Point:"+distance, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {
        }
        public void onProviderDisabled(String s) {
        }
        public void onProviderEnabled(String s) {
        }
    }

}
