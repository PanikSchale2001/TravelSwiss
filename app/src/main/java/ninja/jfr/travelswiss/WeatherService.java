package ninja.jfr.travelswiss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

public class WeatherService {
    private ArrayList<Connection> ListOfConnections;
    private String url;

    public WeatherService() throws IOException {
        createUrl();
        HttpURLConnection httpURLConnection = getApiConnection();

    }

    public void createUrl(){
        int sizeOfList = ListOfConnections.size();
        Connection lastConnection = ListOfConnections.get(sizeOfList);

        double xPos = lastConnection.getArrivalDestination().getxPos();
        double yPos = lastConnection.getArrivalDestination().getyPos();

        this.url = "api.openweathermap.org/data/2.5/weather?lat=" + xPos + "&lon=" + yPos + "&appid=f15635c569c4a58137e4977960782cfc";
    }

    public HttpURLConnection getApiConnection() throws IOException {
        URL url = new URL(this.url);

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        return httpConnection;
    }

    public void createWeather(HttpURLConnection httpConnection) throws IOException, JSONException, ParseException {
        int respondeCode = httpConnection.getResponseCode();

        BufferedReader input = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        String inputLine = input.readLine();
        JSONObject object = new JSONObject(inputLine);
        JSONArray trainConnections = object.getJSONArray("connections");

    }

    public void setConnectionService(ArrayList<Connection> ListOfConnections) {
        this.ListOfConnections = ListOfConnections;
    }
}
