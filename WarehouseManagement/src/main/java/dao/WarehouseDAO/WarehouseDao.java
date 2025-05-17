package dao.WarehouseDAO;

import config.DatabaseConfig;
import entity.Warehouse.Warehouse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для работы со складами в базе данных
public class WarehouseDao {

    // Добавление нового склада
    public void addWarehouse(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO warehouses (address, capacity) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Заполнение параметров из объекта Warehouse
            stmt.setString(1, warehouse.getAddress());
            stmt.setInt(2, warehouse.getCapacity());
            stmt.executeUpdate();
        }
    }

    // Получение списка всех складов
    public List<Warehouse> getAllWarehouses() throws SQLException {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouses";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Преобразование ResultSet в объекты Warehouse
            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouse_id"));
                warehouse.setAddress(rs.getString("address"));
                warehouse.setCapacity(rs.getInt("capacity"));
                warehouse.setActive(rs.getBoolean("is_active"));
                warehouses.add(warehouse);
            }
        }
        return warehouses;
    }

    // Изменение статуса активности склада
    public void updateWarehouseStatus(int warehouseId, boolean isActive) throws SQLException {
        String sql = "UPDATE warehouses SET is_active = ? WHERE warehouse_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isActive);
            stmt.setInt(2, warehouseId);
            stmt.executeUpdate();
        }
    }

    // Получение статуса склада
    public boolean getWarehouseStatus(int warehouseId) throws SQLException {
        String sql = "SELECT is_active FROM warehouses WHERE warehouse_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, warehouseId);  // Устанавливаем параметр

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_active");  // Возвращаем статус, если запись найдена
                } else {
                    // Обработка случая, когда склад не найден
                    throw new SQLException("Склад с ID " + warehouseId + " не найден");
                }
            }
        }
    }

    // Получение склада по ID
    public Warehouse getById(int warehouseId) throws SQLException {
        String sql = "SELECT * FROM warehouses WHERE warehouse_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, warehouseId);
            try (ResultSet rs = stmt.executeQuery()) {

                // Проверка наличия записи в ResultSet
                if (rs.next()) {
                    Warehouse warehouse = new Warehouse();
                    warehouse.setWarehouseId(rs.getInt("warehouse_id"));
                    warehouse.setAddress(rs.getString("address"));
                    warehouse.setCapacity(rs.getInt("capacity"));
                    warehouse.setActive(rs.getBoolean("is_active"));
                    return warehouse;
                }
            }
        }
        return null;
    }
}