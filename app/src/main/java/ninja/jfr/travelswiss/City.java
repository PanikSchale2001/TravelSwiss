package ninja.jfr.travelswiss;

public class City {

    private int id;
    private String name;
    private double xPos;
    private double yPos;

    public City(String name, double xPos, double yPos, int id) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
