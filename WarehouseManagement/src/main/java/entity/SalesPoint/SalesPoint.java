package entity.SalesPoint;

public class SalesPoint {
    private int pointId;
    private String address;
    private boolean isActive;

    // Геттеры и сеттеры
    public int getPointId() {return pointId;}
    public void setPointId(int pointId) {this.pointId = pointId;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}
}