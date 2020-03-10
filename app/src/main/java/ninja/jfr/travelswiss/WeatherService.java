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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WeatherService {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public WeatherService() throws IOException {
    }

    public Future<Weather> getWeather(City city) {
        return executorService.submit(() -> {
            String url = getUrl(city);
            HttpURLConnection apiConnection = getApiConnection(url);
            return createWeather(apiConnection, city);
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

    public Weather createWeather(HttpURLConnection httpConnection, City city) throws IOException, JSONException, ParseException {
        int respondeCode = httpConnection.getResponseCode();

        BufferedReader input = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        String inputLine = input.readLine();
        JSONObject object = new JSONObject(inputLine);
        JSONObject weatherObject = object.getJSONObject("weather");

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setId(weatherObject.getInt("id"));
        weather.setWeather(weatherObject.getString("main"));
        weather.setDescription(weatherObject.getString("description"));

        JSONObject mainObject = object.getJSONObject("main");
        weather.setTemperatur(mainObject.getDouble("temp"));

        JSONObject windObject = object.getJSONObject("wind");
        weather.setWindspeed(windObject.getDouble("speed"));

        JSONObject rainObject = object.getJSONObject("rain");
        weather.setRainPerHour(rainObject.getDouble("1h"));

        return weather;
    }
}
