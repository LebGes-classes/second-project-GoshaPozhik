package entity.Warehouse;

public class StorageCell {
    private int cellId;
    private int warehouseId;
    private int productId;
    private int quantity;
    private int responsibleEmployeeId;

    // Геттеры и сеттеры
    public int getCellId() { return cellId; }
    public void setCellId(int cellId) { this.cellId = cellId; }
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getResponsibleEmployeeId() { return responsibleEmployeeId; }
    public void setResponsibleEmployeeId(int responsibleEmployeeId) { this.responsibleEmployeeId = responsibleEmployeeId; }
}