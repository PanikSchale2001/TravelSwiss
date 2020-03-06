package ninja.jfr.travelswiss;

import java.util.Date;

public class Connection {
    private int id;
    private City departureDestination;
    private Date departureDate;
    private Date departureTime;
    private City arrivalDestination;
    private Date arrivalDate;
    private Date arrivalTime;
    private String departurePlatform;
    private String arrivalPlatform;
    private boolean marked;
    private int position;

    public Connection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(City departureDestination) {
        this.departureDestination = departureDestination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public City getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(City arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
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
}
