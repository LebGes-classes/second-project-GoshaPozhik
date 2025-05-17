package service.UserService;

import dao.UserDAO.EmployeeDao;
import entity.User.Employee;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private final EmployeeDao employeeDao = new EmployeeDao();

    // Нанять сотрудника
    public void hireEmployee(Employee employee) throws SQLException {
        employeeDao.hireEmployee(employee);
    }

    // Список сотрудников
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDao.getAllEmployees();
    }

    // Уволить сотрудника
    public void fireEmployee(int employeeId) throws SQLException {
        employeeDao.fireEmployee(employeeId);
    }
}