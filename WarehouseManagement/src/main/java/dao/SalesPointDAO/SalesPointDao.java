package dao.SalesPointDAO;

import config.DatabaseConfig;
import entity.SalesPoint.SalesPoint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для работы с пунктами продаж в базе данных
public class SalesPointDao {

    // Добавление нового пункта продаж
    public void addSalesPoint(SalesPoint point) throws SQLException {
        String sql = "INSERT INTO sales_points (address) VALUES (?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, point.getAddress());
            stmt.executeUpdate();
        }
    }

    // Обновление статуса пункта продаж
    public void updateSalesPointStatus(int pointId, boolean isActive) throws SQLException {
        String sql = "UPDATE sales_points SET is_active = ? WHERE point_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isActive);
            stmt.setInt(2, pointId);
            stmt.executeUpdate();
        }
    }

    // Получение списка всех пунктов продаж
    public List<SalesPoint> getAllSalesPoints() throws SQLException {
        List<SalesPoint> points = new ArrayList<>();
        String sql = "SELECT * FROM sales_points";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Преобразуем ResultSet в объекты SalesPoint
            while (rs.next()) {
                SalesPoint point = new SalesPoint();
                point.setPointId(rs.getInt("point_id"));
                point.setAddress(rs.getString("address"));
                point.setActive(rs.getBoolean("is_active"));
                points.add(point);
            }
        }
        return points;
    }

    public boolean getStatusById(int pointId) throws SQLException {
        String sql = "SELECT is_active FROM sales_points WHERE point_id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pointId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getBoolean("is_active");
        }
    }
    public SalesPoint getById(int pointId) throws SQLException {
        String sql = "SELECT * FROM sales_points WHERE point_id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pointId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                SalesPoint p = new SalesPoint();
                p.setPointId(rs.getInt("point_id"));
                p.setAddress(rs.getString("address"));
                p.setActive(rs.getBoolean("is_active"));
                return p;
            }
            throw new SQLException("Пункт не найден");
        }
    }
}