package ninja.jfr.travelswiss;

import android.util.Log;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

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

    public void getLastCity(List<List<Connection>> connections) throws APIException {
        for (int i = 0;i<connections.size();i++){
            List<Connection> connection = connections.get(i);
            Connection lastConnection = null;
            for (int j = 0; j< connection.size(); j++){
                lastConnection= connection.get(j);
            }
            Weather weather = getWeather(lastConnection.getArrivalDestination());
        }

    }

    public Weather getWeather(City city) throws APIException {

            OWM owm = new OWM("f15635c569c4a58137e4977960782cfc");
            owm.setUnit(OWM.Unit.METRIC);
            CurrentWeather currentWeather = owm.currentWeatherByCoords(city.getxPos(), city.getyPos());
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setId(currentWeather.getWeatherList().get(0).getConditionId());
            weather.setWeather(currentWeather.getWeatherList().get(0).getMainInfo());
            weather.setDescription(currentWeather.getWeatherList().get(0).getDescription());
            weather.setTemperatur(currentWeather.getMainData().getTemp());
            weather.setWindspeed(currentWeather.getWindData().getSpeed());

            if (currentWeather.getRainData() != null){
                weather.setRainPerHour(currentWeather.getRainData().getPrecipVol3h());
            } else{
                weather.setRainPerHour(0);
            }

            Log.d("SoySauce", weather.toString());
            return weather;
    }
}
