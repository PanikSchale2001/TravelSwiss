package ninja.jfr.travelswiss;

import android.util.Log;

import net.aksingh.owmjapis.api.APIException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConnectionService {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ConnectionService() {
    }

    public Future<List<List<Connection>>> getAllConnections(String from, String to, String date, String time) {
        return executorService.submit(() -> {
            String url = getUrl(from, to, date, time);
            HttpURLConnection apiConnection = getApiConnection(url);
            return createConnection(apiConnection);
        });
    }

    public String getUrl(String from, String to, String date, String time) throws ParseException {

        String url = "";

        if (date == null){
            if (time == null){
                url = "https://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to;
            } else{
                url = "https://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&time" + time;
            }
        } else if (time == null){
            url = "https://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&date" + date;
        } else {
            url = "https://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&date" + date + "&time" + time;
        }

        return  url;
    }

    public HttpURLConnection getApiConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
    Log.e("jdsfjf", urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        return httpConnection;
    }

    public List<List<Connection>> createConnection(HttpURLConnection httpConnection) throws IOException, JSONException, ParseException, APIException {
        int respondeCode = httpConnection.getResponseCode();

        BufferedReader input = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        String inputLine = input.readLine();
        JSONObject object = new JSONObject(inputLine);
        JSONArray trainConnections = object.getJSONArray("connections");

        List<List<Connection>> connections = new ArrayList<>();

        for (int i = 0; i < 4 && i < trainConnections.length() ; i++){

            List<Connection> listOfConnection = new ArrayList<>();

            JSONObject trainConnection = trainConnections.getJSONObject(i);
            JSONArray sections = trainConnection.getJSONArray("sections");

            for (int j = 0; j < sections.length(); j++){

                Connection connection = new Connection();

                JSONObject section = sections.getJSONObject(j);
                JSONObject departure = section.getJSONObject("departure");

                // Departure
                if (!departure.isNull("departure")){
                     connection.setDepartureDate(parseTime(departure.getString("departure")));

                }
                if (departure.isNull("platform")){
                    connection.setDeparturePlatform("N/A");
                } else {
                    connection.setDeparturePlatform(departure.getString("platform"));
                }

                JSONObject departureStation = departure.getJSONObject("station");

                if (departureStation != null) {
                    JSONObject coordinate = departureStation.getJSONObject("coordinate");

                    connection.setDepartureDestination(new City(departureStation.getString("name"), coordinate.getDouble("x"), coordinate.getDouble("y"), departureStation.getInt("id")));
                }

                //Arrival
                JSONObject arrival = section.getJSONObject("arrival");

                if (!arrival.isNull("arrival")){
                    connection.setArrivalDate(parseTime(arrival.getString("arrival")));
                }
                if (arrival.isNull("platform")){
                    connection.setArrivalPlatform("N/A");
                } else {
                    connection.setArrivalPlatform(arrival.getString("platform"));
                }

                JSONObject arrivalStation = arrival.getJSONObject("station");
                Log.e("ww", arrivalStation.toString());

                if (arrivalStation != null) {
                    JSONObject coordinate = arrivalStation.getJSONObject("coordinate");

                    connection.setArrivalDestination(new City(arrivalStation.getString("name"), coordinate.getDouble("x"), coordinate.getDouble("y"), arrivalStation.getInt("id")));
                }

                connection.setPosition(j);
                Log.e("dd", connection.toString());

                listOfConnection.add(connection);
            }

            connections.add(listOfConnection);

        }
        return connections;

    }


    public String parseTime(String date) throws ParseException {
        String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GTM"));
        Date fullFormatetTime = simpleDateFormat.parse(date);

        String timePattern = "HH:mm";
        SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);


        return timeFormatter.format(fullFormatetTime);

    }
}