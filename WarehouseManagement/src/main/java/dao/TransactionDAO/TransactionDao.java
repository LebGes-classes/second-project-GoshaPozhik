package dao.TransactionDAO;

import config.DatabaseConfig;
import java.sql.*;

// Класс для работы с транзакциями в базе данных
public class TransactionDao {

    // Запись транзакции в БД
    public void recordTransaction(String type, int productId, int quantity,
                                  int employeeId, int cellId, Integer salesPointId, Integer customerId) throws SQLException {
        String sql = "INSERT INTO transactions (type, product_id, quantity, employee_id, storage_cell_id, sales_point_id, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Установка параметров транзакции
            stmt.setString(1, type);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, employeeId);
            stmt.setInt(5, cellId);

            // Обработка необязательных полей (точка продажи и клиент)
            if (salesPointId != null) stmt.setInt(6, salesPointId);
            else stmt.setNull(6, Types.INTEGER);

            if (customerId != null) stmt.setInt(7, customerId);
            else stmt.setNull(7, Types.INTEGER);

            stmt.executeUpdate(); // Сохранение транзакции
        }
    }
}