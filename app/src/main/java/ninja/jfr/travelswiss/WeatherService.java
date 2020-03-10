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
import java.util.List;
import java.util.concurrent.Future;

public class WeatherService {
    public WeatherService() throws IOException {
    }

    public Future<List<Weather>> getWeather(final City city) {
        return executorService.submit(() -> {
            String url = getUrl(city);
            HttpURLConnection apiConnection = getApiConnection(url);
            return createWeather(apiConnection);
        });
    }

    public String getUrl(City city){
        double xPos = city.getxPos();
        double yPos = city.getyPos();

        return "api.openweathermap.org/data/2.5/weather?lat=" + xPos + "&lon=" + yPos + "&appid=f15635c569c4a58137e4977960782cfc";
    }

    public HttpURLConnection getApiConnection(String urlString) throws IOException {
        URL url = new URL(urlString);

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
}
