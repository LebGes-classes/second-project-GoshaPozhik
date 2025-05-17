package service.ProductService;

import config.DatabaseConfig;
import dao.ProductDAO.ProductDao;
import entity.Product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductDao productDao = new ProductDao();

    // Добавить продукт
    public void addProduct(Product product) throws SQLException {
        productDao.addProduct(product);
    }

    // Получить список продуктов
    public List<Product> getAllProducts() throws SQLException {
        return productDao.getAllProducts();
    }

    // Обновить количество товара
    public void updateStock(int productId, int quantity) throws SQLException {
        productDao.updateProductQuantity(productId, quantity);
    }

    // Удалить продукт по ID
    public void removeProduct(int productId) throws SQLException {
        productDao.deleteProduct(productId);
    }

    // Список продуктов к покупке
    public List<Product> getProductsForPurchase() throws SQLException {
        return productDao.getAllProducts().stream()
                .filter(p -> p.getQuantity() < 100)
                .collect(Collectors.toList());
    }

    // Получить продукт по ID
    public String getProductName(int productId) throws SQLException {
        String sql = "SELECT name FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getString("name") : "Неизвестный товар";
            }
        }
    }
}