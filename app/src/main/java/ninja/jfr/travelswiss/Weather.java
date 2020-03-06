package ninja.jfr.travelswiss;

public class Weather {
    private int id;
    private City city;
    private String weather;
    private String description;
    private double temperatur;
    private double windspeed;
    private double rainPerHour;

    public Weather(int id, City city, String weather, String description, double temperatur, double windspeed, double rainPerHour) {
        this.id = id;
        this.city = city;
        this.weather = weather;
        this.description = description;
        this.temperatur = temperatur;
        this.windspeed = windspeed;
        this.rainPerHour = rainPerHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(double temperatur) {
        this.temperatur = temperatur;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getRainPerHour() {
        return rainPerHour;
    }

    public void setRainPerHour(double rainPerHour) {
        this.rainPerHour = rainPerHour;
    }
}
