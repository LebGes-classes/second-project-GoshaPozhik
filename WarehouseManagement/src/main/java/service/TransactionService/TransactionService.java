package service.TransactionService;

import dao.TransactionDAO.TransactionDao;
import service.ProductService.ProductService;
import service.WarehouseService.StorageCellService;

import java.sql.SQLException;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();
    private final ProductService productService = new ProductService();
    private final StorageCellService storageCellService = new StorageCellService(); // новое
    // Совершение транзакции
    public void processTransaction(String type,  int productId, int quantity,
                                   int employeeId,int cellId, Integer salesPointId, Integer customerId) throws SQLException {
        int quantityChange = switch (type) {
            case "PURCHASE" -> quantity;
            case "SALE", "RETURN" -> -quantity;
            default -> 0;
        };

        if ("RETURN".equals(type)) {
            quantityChange = quantity;
        }

        productService.updateStock(productId, quantityChange); // пока не нужно
        storageCellService.updateCellQuantity(cellId, quantityChange); // новое
        transactionDao.recordTransaction(type, productId, quantity, employeeId, cellId, salesPointId, customerId);
    }
}