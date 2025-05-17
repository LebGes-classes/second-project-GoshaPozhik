package dao.UserDAO;

import config.DatabaseConfig;
import entity.User.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для работы с клиентами в базе данных
public class CustomerDao {

    // Добавление нового клиента
    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Заполнение данных клиента
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.executeUpdate();
        }
    }

    // Получение всех клиентов из БД
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Преобразование результатов запроса в объекты
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customers.add(customer);
            }
        }
        return customers;
    }
}