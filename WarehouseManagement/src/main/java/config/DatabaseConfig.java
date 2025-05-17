package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Подключение базы данных
public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";  // Ссылка
    private static final String USER = "postgres"; // Имя БД
    private static final String PASSWORD = "password"; // Пароль к БД

    // Получение соединения
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}