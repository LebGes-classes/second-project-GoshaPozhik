package service.UserService;

import dao.UserDAO.CustomerDao;
import entity.User.Customer;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao = new CustomerDao();

    // Добавление покупателя
    public void addCustomer(Customer customer) throws SQLException {
        customerDao.addCustomer(customer);
    }

    // Список всех покупателей
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDao.getAllCustomers();
    }
}