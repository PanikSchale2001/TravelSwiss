package ninja.jfr.travelswiss;

import java.util.Date;

public class Connection {
    private int id;
    private String departureDestination;
    private Date departureDate;
    private Date departureTime;
    private String arrivalDestination;
    private Date arrivalDate;
    private Date arrivalTime;
    private City city;
    private boolean marked;
    private  Connection[] connections;
    private int position;

    public Connection(int id, String departureDestination, Date departureDate, Date departureTime, String arrivalDestination, Date arrivalDate, Date arrivalTime, boolean marked) {
        this.id = id;
        this.departureDestination = departureDestination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDestination = arrivalDestination;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.marked = marked;
    }

    public Connection(int id, String departureDestination, Date departureDate, Date departureTime, String arrivalDestination, Date arrivalDate, Date arrivalTime, boolean marked, Connection[] connections, int position) {
        this.id = id;
        this.departureDestination = departureDestination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDestination = arrivalDestination;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.marked = marked;
        this.connections = connections;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(String departureDestination) {
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

    public String getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(String arrivalDestination) {
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

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public Connection[] getConnections() {
        return connections;
    }

    public void setConnections(Connection[] connections) {
        this.connections = connections;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
