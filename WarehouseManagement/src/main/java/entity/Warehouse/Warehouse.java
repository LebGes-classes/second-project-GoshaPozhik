package entity.Warehouse;

public class Warehouse {
    private int warehouseId;
    private String address;
    private int capacity;
    private boolean isActive;

    // Геттеры и сеттеры
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}