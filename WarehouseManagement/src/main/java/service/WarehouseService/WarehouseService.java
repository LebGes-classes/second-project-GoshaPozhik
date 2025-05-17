package service.WarehouseService;

import dao.WarehouseDAO.StorageCellDao;
import dao.WarehouseDAO.WarehouseDao;
import entity.Warehouse.StorageCell;
import entity.Warehouse.Warehouse;
import service.ProductService.ProductService;

import java.sql.SQLException;
import java.util.List;

public class WarehouseService {
    private final WarehouseDao warehouseDao = new WarehouseDao();
    private final StorageCellDao storageCellDao = new StorageCellDao();
    private final ProductService productService = new ProductService();

    // Добавить склад
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        warehouseDao.addWarehouse(warehouse);
    }

    // Список складов
    public List<Warehouse> getAllWarehouses() throws SQLException {
        return warehouseDao.getAllWarehouses();
    }

    // Открыть/закрыть склад
    public void toggleWarehouseStatus(int warehouseId, boolean isActive) throws SQLException {
        warehouseDao.updateWarehouseStatus(warehouseId, isActive);
    }

    // Получить статус склада
    public boolean getWarehouseStatus(int warehouseId) throws SQLException {
        return warehouseDao.getWarehouseStatus(warehouseId);
    }

    // Список ячеек склада
    public List<StorageCell> getWarehouseCells(int warehouseId) throws SQLException {
        return storageCellDao.getCellsByWarehouse(warehouseId);
    }

    // Информация о складе
    public void printWarehouseInfo(int warehouseId) throws SQLException {
        Warehouse warehouse = warehouseDao.getById(warehouseId);
        List<StorageCell> cells = getWarehouseCells(warehouseId);

        System.out.println("\n=== Информация о складе ===");
        System.out.println("Адрес: " + warehouse.getAddress());
        System.out.println("Вместимость: " + warehouse.getCapacity());
        System.out.println("Занято ячеек: " + cells.size());
        System.out.println("Товары на складе:");
        cells.forEach(c -> {
            try {
                System.out.printf(
                        " - %s: %d шт. (Ячейка %d)%n",
                        productService.getProductName(c.getProductId()),
                        c.getQuantity(),
                        c.getCellId()
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}