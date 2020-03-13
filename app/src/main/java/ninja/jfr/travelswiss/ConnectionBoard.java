package ninja.jfr.travelswiss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.aksingh.owmjapis.api.APIException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConnectionBoard extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_board);

        Intent intent = getIntent();
        String json = intent.getStringExtra("connection");

        Gson gson = new Gson();
        List<Connection> listOfConnections = gson.fromJson(json, new TypeToken<List<Connection>>() {
        }.getType());
        Connection connection = null;
        listView = findViewById(R.id.listView);

        departureDestination = new String[listOfConnections.size()];
        departureTime = new String[listOfConnections.size()];
        departurePlatform = new String[listOfConnections.size()];
        arrivalDestination = new String[listOfConnections.size()];
        arrivalTime = new String[listOfConnections.size()];
        arrivalPlatform = new String[listOfConnections.size()];
        travelTime = new String[listOfConnections.size()];

        Connection lastConnection = null;

        for (int i = 0; i < listOfConnections.size(); i++) {
            connection = listOfConnections.get(i);

            departureDestination[i] = connection.getDepartureDestination().getName();
            departureTime[i] = connection.getDepartureDate();
            departurePlatform[i] = connection.getDeparturePlatform();

            arrivalDestination[i] = connection.getArrivalDestination().getName();
            arrivalTime[i] = connection.getArrivalDate();
            arrivalPlatform[i] = connection.getArrivalPlatform();
            lastConnection  = connection;
        }
        ConnectionBoard.MyAdapter myAdapter = new ConnectionBoard.MyAdapter(ConnectionBoard.this, departureDestination, departureTime, departurePlatform, arrivalDestination, arrivalTime, arrivalPlatform, travelTime);
        listView.setAdapter(myAdapter);

        destination = new String[1];
        mainWeather = new String[1];
        description = new String[1];
        temperatur = new String[1];
        wind = new String[1];
        rainPerHour = new String[1];
        weatherList = findViewById(R.id.weatherList);

        WeatherService weatherService = null;

        try {
            weatherService = new WeatherService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Weather weather = null;
        try {
            weather = weatherService.getWeather(lastConnection.getArrivalDestination()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        destination[0] = lastConnection.getArrivalDestination().getName();
        mainWeather[0] = weather.getWeather();
        description[0] = weather.getDescription();
        double unroundedTemperatur = weather.getTemperatur() - 273.15;
        temperatur[0] = String.format("%.2f", unroundedTemperatur) + "Grad";
        wind[0] = String.valueOf(weather.getWindspeed()) + "KM/H";
        rainPerHour[0] = String.valueOf(weather.getRainPerHour()) + "mm";

        WeatherAdapter weatherAdapter = new WeatherAdapter(ConnectionBoard.this, destination, mainWeather, description, temperatur, wind, rainPerHour);
        weatherList.setAdapter(weatherAdapter);



    }

    //TODO Objekte erstellen und mit werten befüllen
    ListView listView;
    String[] departureDestination;
    String[] departureTime;
    String[] departurePlatform;
    String[] arrivalDestination;
    String[] arrivalTime;
    String[] arrivalPlatform;
    String[] travelTime;

    ListView weatherList;

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rDepartureDestination[];
        String rDepartureTime[];
        String rDeparturePlatform[];
        String rArrivalDestination[];
        String rArrivalTime[];
        String rArrivalPlatform[];
        String rTravelTime[];


        MyAdapter(Context c, String departureDestination[], String departureTime[], String departurePlatform[], String arrivalDestination[], String arrivalTime[], String arrivalPlatform[], String travelTime[]) {
            super(c, R.layout.row, R.id.departureDestination, departureDestination);
            this.context = c;
            this.rDepartureDestination = departureDestination;
            this.rDepartureTime = departureTime;
            this.rDeparturePlatform = departurePlatform;
            this.rArrivalDestination = arrivalDestination;
            this.rArrivalTime = arrivalTime;
            this.rArrivalPlatform = arrivalPlatform;
            this.rTravelTime = travelTime;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView myDepartureDestination = row.findViewById(R.id.departureDestination);
            TextView myDepartureTime = row.findViewById(R.id.departureTime);
            TextView myDeparturePlatform = row.findViewById(R.id.departurePlatform);
            TextView myTravelTime = row.findViewById(R.id.travelTime);
            TextView myArrivalDestination = row.findViewById(R.id.arrivalDestination);
            TextView myArrivalTime = row.findViewById(R.id.arrivalTime);
            TextView myArrivalPlatform = row.findViewById(R.id.arrivalPlatform);

            myDepartureDestination.setText(rDepartureDestination[position]);
            myDepartureTime.setText(rDepartureTime[position]);
            myDeparturePlatform.setText(rDeparturePlatform[position]);
            myTravelTime.setText(rTravelTime[position]);
            myArrivalDestination.setText(rArrivalDestination[position]);
            myArrivalTime.setText(rArrivalTime[position]);
            myArrivalPlatform.setText(rArrivalPlatform[position]);

            return row;
        }
    }

    ListView wheaterView;
    String[] destination;
    String[] mainWeather;
    String[] description;
    String[] temperatur;
    String[] wind;
    String[] rainPerHour;

    class WeatherAdapter extends ArrayAdapter<String>{
        Context context;
        String rDestination[];
        String rWeather[];
        String rDescription[];
        String rTemperatur[];
        String rWind[];
        String rRainPerHour[];

        WeatherAdapter(Context c,String destination[], String mainWeather[],String description[], String temperatur[], String wind[], String rainPerHour[]){
            super(c, R.layout.weather, R.id.destination, destination);
            this.context = c;
            this.rDestination = destination;
            this.rWeather = mainWeather;
            this.rDescription = description;
            this.rTemperatur = temperatur;
            this.rWind = wind;
            this.rRainPerHour = rainPerHour;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View weather = layoutInflater.inflate(R.layout.weather, parent, false);
            TextView myDestination = weather.findViewById(R.id.destination);
            TextView myWeather = weather.findViewById(R.id.weather);
            TextView myDescription = weather.findViewById(R.id.description);
            TextView myTemperatur = weather.findViewById(R.id.temeratur);
            TextView myWind = weather.findViewById(R.id.wind);
            TextView myRainPerHour = weather.findViewById(R.id.rainPerHour);

            myDestination.setText(destination[position]);
            myWeather.setText(rWeather[position]);
            myDescription.setText(description[position]);
            myTemperatur.setText(temperatur[position]);
            myWind.setText(wind[position]);
            myRainPerHour.setText(rainPerHour[position]);


            return weather;
        }
    }
}