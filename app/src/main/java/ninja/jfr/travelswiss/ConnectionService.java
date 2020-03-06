package ninja.jfr.travelswiss;

import android.net.UrlQuerySanitizer;

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

public class ConnectionService {

    private ArrayList<ArrayList> allConnections = new ArrayList<>();
    private ArrayList<Connection> listOfConnection;

    public ConnectionService(String from, String to, String date, String time) throws ParseException, IOException, JSONException {
        String url = getUrl(from, to, date, time);
        HttpURLConnection apiConnection = getApiConnection(url);
        createConnection(apiConnection);

    }

    public String getUrl(String from, String to, String date, String time) throws ParseException {

        String url = "";
        Date formatedDate = parseDate(date);
        Date formatedTime = parseTime(time);

        if (date == null){
            if (time == null){
                url = "http://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to;
            } else{
                url = "http://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&time" + formatedTime;
            }
        } else if (time == null){
            url = "http://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&date" + formatedDate;
        } else {
            url = "http://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&date" + formatedDate + "&time" + formatedTime;
        }

        return  url;
    }

    public HttpURLConnection getApiConnection(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        return httpConnection;
    }

    public void createConnection(HttpURLConnection httpConnection) throws IOException, JSONException, ParseException {
        int respondeCode = httpConnection.getResponseCode();

        BufferedReader input = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        String inputLine = input.readLine();
        JSONObject object = new JSONObject(inputLine);
        JSONArray trainConnections = object.getJSONArray("connections");

        for (int i = 0; i < 4 ; i++){

            listOfConnection = new ArrayList<>();

            JSONObject trainConnection = trainConnections.getJSONObject(i);
            JSONArray sections = trainConnection.getJSONArray("sections");

            for (int j = 0; j < sections.length(); j++){

                Connection connection = new Connection();

                JSONObject section = sections.getJSONObject(i);
                JSONObject departure = section.getJSONObject("departure");

                // Departure
                if (!departure.isNull("departureTimestamp")){
                     connection.setDepartureDate(parseDateTimestamp(departure.getLong("departureTimestamp")));
                     connection.setDepartureTime(parseTimeTimestamp(departure.getLong("departureTimestamp")));
                }
                if (departure.isNull("platform")){
                    connection.setDeparturePlatform("N/A");
                } else {
                    connection.setDeparturePlatform(departure.getString("platform"));
                }

                JSONObject departureStation = departure.getJSONObject("station");
                connection.setDepartureDestination(new City(departureStation.getString("name"), departureStation.getDouble("x"), departureStation.getDouble("y"), departureStation.getInt("id")));

                //Arrival
                JSONObject arrival = section.getJSONObject("arrival");

                // Departure
                if (!departure.isNull("arrivalTimestamp")){
                    connection.setArrivalDate(parseDateTimestamp(departure.getLong("arrivalTimestamp")));
                    connection.setArrivalTime(parseTimeTimestamp(departure.getLong("arrivalTimestamp")));
                }
                if (arrival.isNull("platform")){
                    connection.setArrivalPlatform("N/A");
                } else {
                    connection.setArrivalPlatform(arrival.getString("platform"));
                }

                JSONObject arrivalStation = departure.getJSONObject("station");
                connection.setDepartureDestination(new City(arrivalStation.getString("name"), arrivalStation.getDouble("x"), arrivalStation.getDouble("y"), arrivalStation.getInt("id")));
                listOfConnection.add(connection);
            }
            allConnections.add(listOfConnection);
        }


    }

    private Date parseTimeTimestamp(Long time) throws ParseException {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date formatetTime = simpleDateFormat.parse(String.valueOf(new Date(time)));

        return formatetTime;
    }

    public Date parseDateTimestamp(Long date) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date formatetDate = simpleDateFormat.parse(String.valueOf(new Date(date)));

        return formatetDate;
    }

    private Date parseTime(String time) throws ParseException {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date formatetTime = simpleDateFormat.parse(time);

        return formatetTime;
    }

    public Date parseDate(String date) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date formatetDate = simpleDateFormat.parse(date);

        return formatetDate;
    }
}
