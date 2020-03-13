package ninja.jfr.travelswiss;

public class Weather {
    private int id;
    private City city;
    private String weather;
    private String description;
    private double temperatur;
    private double windspeed;
    private double rainPerHour;

    public Weather() {
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

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", city=" + city +
                ", weather='" + weather + '\'' +
                ", description='" + description + '\'' +
                ", temperatur=" + temperatur +
                ", windspeed=" + windspeed +
                ", rainPerHour=" + rainPerHour +
                '}';
    }
}