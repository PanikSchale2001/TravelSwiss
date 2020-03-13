package ninja.jfr.travelswiss;

import java.util.Date;

public class Connection {
    private City departureDestination;
    private String departureDate;
    private City arrivalDestination;
    private String arrivalDate;
    private String departurePlatform;
    private String arrivalPlatform;
    private boolean marked;
    private int position;

    public Connection() {
    }

    public City getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(City departureDestination) {
        this.departureDestination = departureDestination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public City getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(City arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDeparturePlatform() {
        return departurePlatform;
    }

    public void setDeparturePlatform(String departurePlatform) {
        this.departurePlatform = departurePlatform;
    }

    public String getArrivalPlatform() {
        return arrivalPlatform;
    }

    public void setArrivalPlatform(String arrivalPlatform) {
        this.arrivalPlatform = arrivalPlatform;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "departureDestination=" + departureDestination +
                ", departureDate=" + departureDate +
                ", arrivalDestination=" + arrivalDestination +
                ", arrivalDate=" + arrivalDate +
                ", departurePlatform='" + departurePlatform + '\'' +
                ", arrivalPlatform='" + arrivalPlatform + '\'' +
                ", marked=" + marked +
                ", position=" + position +
                '}';
    }
}
