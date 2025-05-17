package dao.WarehouseDAO;

import config.DatabaseConfig;
import entity.Warehouse.StorageCell;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для работы с ячейками хранения в базе данных
public class StorageCellDao {

    // Добавление новой ячейки хранения
    public void addCell(StorageCell cell) throws SQLException {
        String sql = "INSERT INTO storage_cells (warehouse_id, product_id, quantity, responsible_employee_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Заполнение параметров из объекта StorageCell
            stmt.setInt(1, cell.getWarehouseId());
            stmt.setInt(2, cell.getProductId());
            stmt.setInt(3, cell.getQuantity());
            stmt.setInt(4, cell.getResponsibleEmployeeId());
            stmt.executeUpdate();
        }
    }

    // Обновление ответственного сотрудника для ячейки
    public void updateResponsibleEmployee(int cellId, int newEmployeeId) throws SQLException {
        String sql = "UPDATE storage_cells SET responsible_employee_id = ? WHERE cell_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newEmployeeId);
            stmt.setInt(2, cellId);
            stmt.executeUpdate();
        }
    }

    // Получение всех ячеек определённого склада
    public List<StorageCell> getCellsByWarehouse(int warehouseId) throws SQLException {
        List<StorageCell> cells = new ArrayList<>();
        String sql = "SELECT * FROM storage_cells WHERE warehouse_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, warehouseId);
            try (ResultSet rs = stmt.executeQuery()) {

                // Преобразование ResultSet в объекты StorageCell
                while (rs.next()) {
                    StorageCell cell = new StorageCell();
                    cell.setCellId(rs.getInt("cell_id"));
                    cell.setWarehouseId(rs.getInt("warehouse_id"));
                    cell.setProductId(rs.getInt("product_id"));
                    cell.setQuantity(rs.getInt("quantity"));
                    cell.setResponsibleEmployeeId(rs.getInt("responsible_employee_id"));
                    cells.add(cell);
                }
            }
        }
        return cells;
    }

    // Получение ячейки по ID
    public StorageCell getCellById(int cellId) throws SQLException {
        String sql = "SELECT * FROM storage_cells WHERE cell_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cellId);
            try (ResultSet rs = stmt.executeQuery()) {

                // Проверка наличия записи в ResultSet
                if (rs.next()) {
                    StorageCell cell = new StorageCell();
                    cell.setCellId(rs.getInt("cell_id"));
                    cell.setWarehouseId(rs.getInt("warehouse_id"));
                    cell.setProductId(rs.getInt("product_id"));
                    cell.setQuantity(rs.getInt("quantity"));
                    cell.setResponsibleEmployeeId(rs.getInt("responsible_employee_id"));
                    return cell;
                }
            }
        }
        return null;
    }

    // Обновление количества товара в ячейке
    public void updateCellQuantity(int cellId, int quantityChange, Connection conn) throws SQLException {
        String sql = "UPDATE storage_cells SET quantity = quantity + ? WHERE cell_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantityChange);
            stmt.setInt(2, cellId);
            stmt.executeUpdate(); // Для работы в рамках транзакции
        }
    }
}