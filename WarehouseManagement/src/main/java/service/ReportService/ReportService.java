package service.ReportService;

import config.DatabaseConfig;
import java.sql.*;

public class ReportService {

    // Финансовый отчет
    public void showProfitReport() throws SQLException {
        String sql = """
            SELECT p.product_id, p.name,
                COALESCE(SUM(CASE WHEN t.type = 'SALE' THEN t.quantity ELSE 0 END), 0) AS sold,
                COALESCE(SUM(CASE WHEN t.type = 'PURCHASE' THEN t.quantity ELSE 0 END), 0) AS purchased,
                (COALESCE(SUM(CASE WHEN t.type = 'SALE' THEN t.quantity * p.selling_price ELSE 0 END), 0) -
                 COALESCE(SUM(CASE WHEN t.type = 'PURCHASE' THEN t.quantity * p.purchase_price ELSE 0 END), 0)) AS profit
            FROM products p
            LEFT JOIN transactions t ON p.product_id = t.product_id
            GROUP BY p.product_id, p.name
            """;

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Вывод отчета
            System.out.println("\n=== Отчёт о прибыли ===");
            System.out.printf("%-5s %-20s %-10s %-10s %-10s%n",
                    "ID", "Название", "Продано", "Закуплено", "Прибыль");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10d %-10d %-10.2f%n",
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getInt("sold"),
                        rs.getInt("purchased"),
                        rs.getDouble("profit"));
            }
        }
    }
}