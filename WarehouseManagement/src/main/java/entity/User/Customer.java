package entity.User;

public class Customer {
    private int customerId;
    private String name;
    private String email;

    // Геттеры и сеттеры
    public int getCustomerId() {return customerId;}
    public void setCustomerId(int customerId) {this.customerId = customerId;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}