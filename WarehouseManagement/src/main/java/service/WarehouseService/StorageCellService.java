package service.WarehouseService;

import config.DatabaseConfig;
import dao.WarehouseDAO.*;
import entity.Warehouse.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StorageCellService {
    private final StorageCellDao storageCellDao = new StorageCellDao();

    // Добавить ячейку
    public void addStorageCell(StorageCell cell) throws SQLException {
        WarehouseDao warehouseDao = new WarehouseDao();
        Warehouse warehouse = warehouseDao.getById(cell.getWarehouseId());

        // Проверка статуса склада
        if (!warehouse.isActive()) {
            throw new IllegalStateException("Склад закрыт. Добавление ячеек невозможно.");
        }

        // Проверка вместимости
        List<StorageCell> cells = storageCellDao.getCellsByWarehouse(cell.getWarehouseId());
        if (cells.size() + 1 > warehouse.getCapacity()) {
            throw new IllegalArgumentException("Превышена вместимость склада.");
        }

        storageCellDao.addCell(cell);
    }

    // Изменить ответственное лицо
    public void updateResponsibleEmployee(int cellId, int newEmployeeId) throws SQLException {
        storageCellDao.updateResponsibleEmployee(cellId, newEmployeeId);
    }

    // Переместить продукт
    public void moveProduct(int fromCellId, int toCellId, int quantity) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            StorageCell fromCell = storageCellDao.getCellById(fromCellId);
            if (fromCell.getQuantity() < quantity) {
                throw new IllegalArgumentException("Недостаточно товара в ячейке " + fromCellId);
            }

            storageCellDao.updateCellQuantity(fromCellId, -quantity, conn);
            storageCellDao.updateCellQuantity(toCellId, quantity, conn);

            conn.commit();
        }
    }

    // Изменить количество товара (Новое)
    public void updateCellQuantity(int cellId, int quantity) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            storageCellDao.updateCellQuantity(cellId, quantity, conn);
            conn.commit();
        }
    }
}