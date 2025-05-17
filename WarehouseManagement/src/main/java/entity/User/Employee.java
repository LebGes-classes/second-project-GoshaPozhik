package entity.User;

public class Employee {
    private int employeeId;
    private String fullName;
    private String position;
    private boolean isActive;

    // Геттеры и сеттеры
    public int getEmployeeId() {return employeeId;}
    public void setEmployeeId(int employeeId) {this.employeeId = employeeId;}
    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public String getPosition() {return position;}
    public void setPosition(String position) {this.position = position;}
    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}
}