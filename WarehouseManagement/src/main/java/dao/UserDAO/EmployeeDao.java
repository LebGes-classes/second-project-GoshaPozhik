package dao.UserDAO;

import config.DatabaseConfig;
import entity.User.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс для работы с сотрудниками в базе данных
public class EmployeeDao {

    // Добавление нового сотрудника
    public void hireEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (full_name, position) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Установка параметров сотрудника
            stmt.setString(1, employee.getFullName());
            stmt.setString(2, employee.getPosition());
            stmt.executeUpdate();
        }
    }

    // Получение списка всех сотрудников
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Создание объектов Employee из данных БД
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setFullName(rs.getString("full_name"));
                employee.setPosition(rs.getString("position"));
                employee.setActive(rs.getBoolean("is_active"));
                employees.add(employee);
            }
        }
        return employees;
    }

    // Увольнение сотрудника по ID
    public void fireEmployee(int employeeId) throws SQLException {
        String sql = "UPDATE employees SET is_active = false WHERE employee_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.executeUpdate(); // Изменение статуса сотрудника
        }
    }
}