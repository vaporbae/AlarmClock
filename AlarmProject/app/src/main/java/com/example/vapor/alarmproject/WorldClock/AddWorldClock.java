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

public class AddWorldClock extends AppCompatActivity {

    private ArrayAdapter<Place> itemsAdapter;
    private ArrayList<Place> placeElements;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_world_clock);

        Context appContext = getApplicationContext();
        final AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        placeElements =loadPlaces();
        itemsAdapter = new MyListAdapter(this, R.layout.add_world_clock_row, placeElements);
        lvItems = findViewById(R.id.places_list);
        lvItems.setAdapter(itemsAdapter);

        Button button = findViewById(R.id.buttonCancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SearchView searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                placeElements =loadPlaces();
                placeElements=searchPlaces(query,placeElements);
                itemsAdapter.clear();
                itemsAdapter.addAll(placeElements);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeElements =loadPlaces();
                placeElements=searchPlaces(newText,placeElements);
                itemsAdapter.clear();
                itemsAdapter.addAll(placeElements);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = placeElements.get(position).city;
                String country = placeElements.get(position).country;
                String location = placeElements.get(position).location;

                database.clockElementDao().insertAll(new ClockElement(city,country, location, "", ""));
                finish();
            }
        });
    }

    private ArrayList<Place> searchPlaces(String word, ArrayList<Place> list){
        ArrayList<Place> result=new ArrayList<>();
        if(word==""||word==null) return loadPlaces();
        for(Place place:list){
            if(place.city.toUpperCase().startsWith(word.toUpperCase())||place.country.toUpperCase().startsWith(word.toUpperCase())){
                result.add(place);
            }
        }
        return result;
    }

    private ArrayList<Place> loadPlaces(){
        ArrayList<Place> places=new ArrayList<>();

        //location=39.6034810,-119.6822510
        places.add(new Place("Aberdeen","Scotland","57.149715,-2.094278"));
        places.add(new Place("Adelaide","Australia","-34.928497,138.600739"));
        places.add(new Place("Algiers", "Algeria", "36.753769,3.058756"));
        places.add(new Place("Amsterdam","Netherlands", "52.370216,4.895168"));
        places.add(new Place("Ankara", "Turkey", "39.933365,32.859741"));
        places.add(new Place("Asuncion","Paraguay","-25.263741,-57.575928"));
        places.add(new Place("Athens","Greece","37.983810,23.727539"));
        places.add(new Place("Auckland", "New Zealand","-36.848461,174.763336"));
        places.add(new Place("Bangkok", "Thailand", "13.756331,100.501762"));
        places.add(new Place("Barcelona", "Spain", "41.385063,2.173404"));
        places.add(new Place("Beijing", "China", "39.904202,116.407394"));
        places.add(new Place("Belem", "Brazil", "-1.454980,-48.502270"));
        places.add(new Place("Belfast", "Northern Ireland","54.597286,-5.930120"));
        places.add(new Place("Belgrade", "Serbia", "44.786568,20.448921"));
        places.add(new Place("Berlin", "Germany", "52.520008,13.404954"));
        places.add(new Place("Birmingham","England","33.518589,-86.810356"));
        places.add(new Place("Bogota", "Colombia","4.710989,-74.072090"));
        places.add(new Place("Bombay", "India","19.075983,72.877655"));
        places.add(new Place("Bordeaux","France","44.837788,-0.579180"));
        places.add(new Place("Bremen", "Germany","53.079296,8.801694"));
        places.add(new Place("Brisbane", "Australia", "-27.469770,153.025131"));
        places.add(new Place("Bristol", "England","36.595104,-82.188744"));
        places.add(new Place("Brussels","Belgium","50.850346,4.351721"));
        places.add(new Place("Bucharest", "Romania", "44.426765,26.102537"));
        places.add(new Place("Budapest", "Hungary", "47.497913,19.040236"));
        places.add(new Place("Buenos Aires", "Argentina", "-34.603683,-58.381557"));
        places.add(new Place("Cairo", "Egypt", "30.044420,31.235712"));
        places.add(new Place("Calcutta", "India", "22.572645,88.363892"));
        places.add(new Place("Canton", "China", "38.386120,-97.428085"));
        places.add(new Place("Cape Town", "South Africa", "-33.924870,18.424055"));
        places.add(new Place("Caracas", "Venezuela", "10.480594,-66.903603"));
        places.add(new Place("Cayenne", "French Guiana", "4.922420,-52.313454"));
        places.add(new Place("Chihuahua", "Mexico", "28.485447,-105.782066"));
        places.add(new Place("Chongqing", "China", "29.566920,106.593040"));
        places.add(new Place("Copenhagen", "Denmark", "55.676098,12.568337"));
        places.add(new Place("Cordoba", "Argentina", "37.888176,-4.779383"));
        places.add(new Place("Dakar", "Senegal", "14.716677,-17.467686"));
        places.add(new Place("Darwin", "Australia", "37.095142,-95.582413"));
        places.add(new Place("Dijbouti","Dijbouti","11.600470,43.150829"));
        places.add(new Place("Dublin","Ireland","53.349804,-6.260310"));
        places.add(new Place("Durban", "South Africa", "-29.858681,31.021839"));
        places.add(new Place("Edinburgh","Germany","55.953251,-3.188267"));
        places.add(new Place("Frankfurt", "Germany", "50.110924,8.682127"));
        places.add(new Place("Georgetown", "Guyana", "38.907608,-77.072258"));
        places.add(new Place("Glasgow", "Scotland","55.864239,-4.251806"));
        places.add(new Place("Guetemala City", "Guetemala","14.727590,121.005140"));
        places.add(new Place("Guayaquil", "Ecuador", "-2.170998,-79.922356"));
        places.add(new Place("Hamburg", "Germany", "53.551086,9.993682"));
        places.add(new Place("Hammerfest", "Norway", "70.6636,23.6791"));
        places.add(new Place("Havana", "Cuba", "23.135305,-82.3589631"));
        places.add(new Place("Helsinki", "Finland", "60.1674086,24.9425683"));
        places.add(new Place("Hobart", "Tasmania", "-42.8825088,147.3281233"));
        places.add(new Place("Hong Kong", "China", "22.350627,114.1849161"));
        places.add(new Place("Iquique", "Chile", "-20.2140657,-70.1524646"));
        places.add(new Place("Irkutsk", "Russia", "52.289597,104.280586"));
        places.add(new Place("Jakarta", "Indonesia", "-6.1753942,106.827183"));
        places.add(new Place("Johannesburg", "South Africa", "-26.205,28.049722"));
        places.add(new Place("Kingston", "Jamaica", "17.9712148,-76.7928128"));
        places.add(new Place("Kinshasa", "Congo", "-4.3217055,15.3125974"));
        places.add(new Place("Kuala Lumpur", "Malaysia", "3.1516636,101.6943028"));
        places.add(new Place("La Paz", "Bolivia", "-16.4956371,-68.1336346"));
        places.add(new Place("Leeds", "England", "53.7974185,-1.5437941"));
        places.add(new Place("Lima", "Peru", "-12.0621065,-77.0365256"));
        places.add(new Place("Lisbon", "Portugal", "38.7077507,-9.1365919"));
        places.add(new Place("Liverpool", "England", "53.4054719,-2.9805392"));
        places.add(new Place("London", "England", "51.5073219,-0.1276474"));
        places.add(new Place("Lyons", "France", "49.3989972,1.4768236"));
        places.add(new Place("Madrid", "Spain", "40.4167047,-3.7035825"));
        places.add(new Place("Manchester", "England", "53.4791301,-2.2441009"));
        places.add(new Place("Manila","Philippines", "14.5906216,120.9799696"));
        places.add(new Place("Marseilles", "France","47.0678886,3.013274"));
        places.add(new Place("Mazaltan", "Mexico", "23.2234374,-106.4161919"));
        places.add(new Place("Mecca", "Saudi Arabia","21.4209763,39.8270873"));
        places.add(new Place("Melbourne", "Australia", "-37.8142176,144.9631608"));
        places.add(new Place("Mexico City", "Mexico","19.4326009,-99.1333416"));
        places.add(new Place("Milan", "Italy", "45.4667971,9.1904984"));
        places.add(new Place("Montevideo", "Uruguay", "-34.9059039,-56.1913569"));
        places.add(new Place("Moscow", "Russia", "55.7504461,37.6174943"));
        places.add(new Place("Munich", "Germany", "48.1371079,11.5753822"));
        places.add(new Place("Nagasaki", "Japan", "32.7501611,129.8781002"));
        places.add(new Place("Nagoya", "Japan", "35.15792,136.90448"));
        places.add(new Place("Naorobi", "Kenya","-1.2832533,36.8172449"));
        places.add(new Place("Nanjing", "China","32.0609736,118.7916458"));
        places.add(new Place("Naples", "Italy","40.8359336,14.2487826"));
        places.add(new Place("New Delhi", "India", "28.6141793,77.2022662"));
        places.add(new Place("Newcastle-on-Tyne","England","55.0026092,-1.7746654"));
        places.add(new Place("Odessa", "Ukraine", "46.4852419,30.7433894"));
        places.add(new Place("Osaka", "Japan","34.6937569,135.5014539"));
        places.add(new Place("Oslo", "Norway", "59.9133301,10.7389701"));
        places.add(new Place("Panama City", "Panama", "8.9714493,-79.5341802"));
        places.add(new Place("Paramaribo", "Suriname", "5.8216198,-55.1771974"));
        places.add(new Place("Paris", "France", "48.8566101,2.3514992"));
        places.add(new Place("Prague", "Czech Republic", "50.0874654,14.4212535"));
        places.add(new Place("Rejyjavik", "Iceland", "64.145981,-21.9422367"));
        places.add(new Place("Rio De Janeiro", "Brazil", "-22.9110137,-43.2093727"));
        places.add(new Place("Rome", "Italy", "41.894802,12.4853384"));
        places.add(new Place("Salvador", "Brazil", "13.8000382,-88.9140683"));
        places.add(new Place("Santiago", "Chile", "-33.4377968,-70.6504451"));
        places.add(new Place("Shanghai", "China", "31.2253441,121.4888922"));
        places.add(new Place("Singapore", "Singapore","1.3408528,103.87844686373558"));
        places.add(new Place("Sofia", "Bulgaria", "42.6978634,23.3221789"));
        places.add(new Place("Stockholm", "Sweden","59.3251172,18.0710935"));
        places.add(new Place("Sydney", "Australia", "-33.8548157,151.2164539"));
        places.add(new Place("Tananarive", "Madagascar", "-14.9012472,50.2789542"));
        places.add(new Place("Tokyo", "Japan", "35.6828387,139.7594549"));
        places.add(new Place("Tripoli","Libya","32.896672,13.1777923"));
        places.add(new Place("Venice", "Italy", "45.4371908,12.3345898"));
        places.add(new Place("Veracruz", "Mexico", "19.2002198,-96.1385688"));
        places.add(new Place("Vienna", "Austria", "48.2083537,16.3725042"));
        places.add(new Place("Warsaw", "Poland", "52.2319237,21.0067265"));
        places.add(new Place("Wellington","New Zealand", "-41.2887467,174.7772092"));
        places.add(new Place("Zurich", "Switzerland","47.3723957,8.5423216"));
        return places;
    }

    private class Place{
        String city;
        String country;
        String location;

        public Place(String city, String country, String location){
            this.city=city;
            this.country=country;
            this.location=location;
        }
    }

    private class ViewHolder {
        TextView info;
    }

    private class MyListAdapter extends ArrayAdapter<Place> {
        private int layout;
        private MyListAdapter(Context context, int resource, ArrayList<Place> objects){
            super(context,resource,objects);
            layout=resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;


            if (convertView == null) {
                LayoutInflater vi = LayoutInflater.from(this.getContext());
                convertView = vi.inflate(R.layout.add_world_clock_row, null);

                holder = new ViewHolder();

                holder.info = convertView.findViewById(R.id.textClockRow);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

                String s=placeElements.get(position).city+", "+placeElements.get(position).country;
                holder.info.setText(s);
            return convertView;
        }
    }
}
