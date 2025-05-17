package dao.ProductDAO;

import config.DatabaseConfig;
import entity.Product.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для работы с товарами в базе данных
public class ProductDao {

    // Добавление нового товара
    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, purchase_price, selling_price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Заполняем параметры запроса из объекта Product
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPurchasePrice());
            stmt.setDouble(3, product.getSellingPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.executeUpdate(); // Выполняем запрос
        }
    }

    // Получение списка всех товаров
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Преобразуем ResultSet в объекты Product
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setPurchasePrice(rs.getDouble("purchase_price"));
                product.setSellingPrice(rs.getDouble("selling_price"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        }
        return products;
    }

    // Обновление количества товара
    public void updateProductQuantity(int productId, int quantity) throws SQLException {
        String sql = "UPDATE products SET quantity = quantity + ? WHERE product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    // Удаление товара по ID
    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }
}