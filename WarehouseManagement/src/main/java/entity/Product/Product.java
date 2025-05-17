package entity.Product;

public class Product {
    private int productId;
    private String name;
    private double purchasePrice;
    private double sellingPrice;
    private int quantity;

    // Геттеры и сеттеры
    public int getProductId() {return productId;}
    public void setProductId(int productId) {this.productId = productId;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getPurchasePrice() {return purchasePrice;}
    public void setPurchasePrice(double purchasePrice) {this.purchasePrice = purchasePrice;}
    public double getSellingPrice() {return sellingPrice;}
    public void setSellingPrice(double sellingPrice) {this.sellingPrice = sellingPrice;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}