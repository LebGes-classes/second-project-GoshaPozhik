package consoleApp;

import entity.Product.Product;
import entity.SalesPoint.SalesPoint;
import entity.User.Customer;
import entity.User.Employee;
import entity.Warehouse.StorageCell;
import entity.Warehouse.Warehouse;
import service.ProductService.ProductService;
import service.ReportService.ReportService;
import service.SalesPointService.SalesPointService;
import service.TransactionService.TransactionService;
import service.UserService.CustomerService;
import service.UserService.EmployeeService;
import service.WarehouseService.StorageCellService;
import service.WarehouseService.WarehouseService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); // Создаем сканер
    // Создаем все сервисы
    private static final ProductService productService = new ProductService();
    private static final EmployeeService employeeService = new EmployeeService();
    private static final WarehouseService warehouseService = new WarehouseService();
    private static final SalesPointService salesPointService = new SalesPointService();
    private static final TransactionService transactionService = new TransactionService();
    private static final ReportService reportService = new ReportService();
    private static final StorageCellService storageCellService = new StorageCellService();

    public static void main(String[] args) {
        // Работа консольного приложения
        boolean isRunning = true; // Флаг для избежания бесконечного цикла
        while (isRunning) {
            try {
                printMainMenu();  // Вывод меню
                int choice = readIntInput("Выберите категорию: ");
                // Выбор категории
                switch (choice) {
                    case 1 -> manageProducts();
                    case 2 -> manageEmployees();
                    case 3 -> manageWarehouses();
                    case 4 -> manageSalesPoints();
                    case 5 -> manageStorageCells();
                    case 6 -> manageCustomers();
                    case 7 -> manageTransactions();
                    case 8 -> showReports();
                    case 0 -> isRunning = false;
                    default -> showError("Неверный выбор!");
                }
            // Если вводим не цифру, то выбрасываем исключение
            } catch (NumberFormatException e) {
                showError("Некорректный ввод числа!");
            // Если ошибка с БД, то выбрасываем исключение
            } catch (Exception e) {
                showError("Произошла ошибка: " + e.getMessage());
                isRunning = false;
            }
        }
        // Выход из программы
        System.exit(0);
    }

    // Консольное меню
    private static void printMainMenu() {
        System.out.println("\n=== Управление компанией ===");
        System.out.println("1. Товары");
        System.out.println("2. Сотрудники");
        System.out.println("3. Склады");
        System.out.println("4. Пункты продаж");
        System.out.println("5. Ячейки хранения");
        System.out.println("6. Покупатели");
        System.out.println("7. Транзакции");
        System.out.println("8. Отчёты");
        System.out.println("0. Выход");
    }

    // Управление продуктами (основное меню)
    private static void manageProducts() throws SQLException {
        System.out.println("\n=== Управление товарами ===");
        System.out.println("1. Добавить товар");
        System.out.println("2. Список товаров");
        System.out.println("3. Обновить количество");
        System.out.println("4. Удалить товар");
        System.out.println("5. Товары для закупки");
        System.out.println("6. Перемещение товара");
        int choice = readIntInput("Выберите действие: ");

        // Выбор метода
        switch (choice) {
            case 1 -> addProduct();
            case 2 -> listProducts();
            case 3 -> updateProductStock();
            case 4 -> deleteProduct();
            case 5 -> showProductsForPurchase();
            case 6 -> moveProducts();
            default -> showError("Неверный выбор!");
        }
    }

    // Добавить товар
    private static void addProduct() throws SQLException {
        Product product = new Product();  // Создаем объект "Товар"
        product.setName(readStringInput("Название товара: "));
        product.setPurchasePrice(readDoubleInput("Закупочная цена: "));
        product.setSellingPrice(readDoubleInput("Розничная цена: "));
        product.setQuantity(readIntInput("Количество: "));
        // Заполняем данные через ввод с клавиатуры
        productService.addProduct(product); // Сохраняем в базу через сервис
        showSuccess("Товар добавлен!");  // Уведомляем об успехе
    }

    // Показать список всех товаров
    private static void listProducts() throws SQLException {
        // Получаем список товаров из базы через сервис
        List<Product> products = productService.getAllProducts();
        System.out.println("\nСписок товаров:");
        products.forEach(p -> System.out.printf(
                "%d. %s | Закупка: %.2f | Продажа: %.2f | Остаток: %d%n",
                p.getProductId(), p.getName(),
                p.getPurchasePrice(), p.getSellingPrice(), p.getQuantity()
        ));
    }

    // Обновить количество товара на складе
    private static void updateProductStock() throws SQLException {
        // Запрашиваем ID товара и изменение количества
        int productId = readIntInput("ID товара: ");
        int quantity = readIntInput("Изменение количества (+/-): ");
        // Обновляем количество через сервис
        productService.updateStock(productId, quantity);
        showSuccess("Количество обновлено!");
    }

    // Удалить товар из системы
    private static void deleteProduct() throws SQLException {
        // Запрашиваем ID товара для удаления
        int productId = readIntInput("ID товара для удаления: ");
        // Удаляем товар через сервис
        productService.removeProduct(productId);
        showSuccess("Товар удалён!");
    }

    // Показать товары, требующие закупки
    private static void showProductsForPurchase() throws SQLException {
        List<Product> products = productService.getProductsForPurchase();
        System.out.println("\n=== Товары для срочной закупки ===");
        System.out.printf("%-5s %-20s %-10s %-10s%n",
                "ID", "Название", "Остаток", "Мин. заказ");

        products.forEach(p -> System.out.printf("%-5d %-20s %-10d %-10d%n",
                p.getProductId(),
                p.getName(),
                p.getQuantity(),
                100 - p.getQuantity()
        ));

        // Если список пуст - выводим сообщение
        if (products.isEmpty()) {
            System.out.println("Нет товаров, требующих закупки");
        }
    }

    // Переместить товары между ячейками хранения
    private static void moveProducts() throws SQLException {
        System.out.println("\n=== Перемещение товаров ===");
        // Запрашиваем исходную и целевую ячейки + количество
        int fromCell = readIntInput("ID исходной ячейки: ");
        int toCell = readIntInput("ID целевой ячейки: ");
        int quantity = readIntInput("Количество для перемещения: ");

        try {
            // Выполняем перемещение через сервис
            storageCellService.moveProduct(fromCell, toCell, quantity);
            showSuccess("Товары успешно перемещены!");
        } catch (IllegalArgumentException e) {
            // Обработка ошибок (например, недостаточное количество)
            showError(e.getMessage());
        }
    }

    // Управление сотрудниками (основное меню)
    private static void manageEmployees() throws SQLException {
        System.out.println("\n=== Управление сотрудниками ===");
        System.out.println("1. Нанять сотрудника");
        System.out.println("2. Список сотрудников");
        System.out.println("3. Уволить сотрудника");
        // Запрашиваем выбор действия
        int choice = readIntInput("Выберите действие: ");

        switch (choice) {
            case 1 -> hireEmployee(); // Добавление сотрудника
            case 2 -> listEmployees(); // Просмотр списка
            case 3 -> fireEmployee(); // Увольнение
            default -> showError("Неверный выбор!");
        }
    }

    // Нанять нового сотрудника
    private static void hireEmployee() throws SQLException {
        // Создаем объект сотрудника и заполняем данные
        Employee employee = new Employee();
        employee.setFullName(readStringInput("ФИО сотрудника: "));
        employee.setPosition(readStringInput("Должность: "));
        // Сохраняем в базу через сервис
        employeeService.hireEmployee(employee);
        showSuccess("Сотрудник нанят!");
    }

    // Показать список всех сотрудников
    private static void listEmployees() throws SQLException {
        // Получаем список из базы данных
        List<Employee> employees = employeeService.getAllEmployees();
        System.out.println("\nСписок сотрудников:");
        employees.forEach(e -> System.out.printf(
                "%d. %s (%s) | Статус: %s%n",
                e.getEmployeeId(), e.getFullName(),
                e.getPosition(), e.isActive() ? "работает" : "уволен"
        ));
    }

    // Уволить сотрудника
    private static void fireEmployee() throws SQLException {
        // Запрашиваем ID сотрудника
        int employeeId = readIntInput("ID сотрудника для увольнения: ");
        // Помечаем сотрудника как уволенного через сервис
        employeeService.fireEmployee(employeeId);
        showSuccess("Сотрудник уволен!");
    }

    // Управление складами (основное меню)
    private static void manageWarehouses() throws SQLException {
        System.out.println("\n=== Управление складами ===");
        System.out.println("1. Добавить склад");
        System.out.println("2. Список складов");
        System.out.println("3. Изменить статус склада");
        System.out.println("4. Информация о складе");

        int choice = readIntInput("Выберите действие: ");

        switch (choice) {
            case 1 -> addWarehouse();     // Добавление нового склада
            case 2 -> listWarehouses();   // Просмотр всех складов
            case 3 -> toggleWarehouseStatus(); // Изменение статуса
            case 4 -> printWarehouseInfo();  // Информация о складе
            default -> showError("Неверный выбор!");
        }
    }

    // Добавить новый склад
    private static void addWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse(); // Создаем объект "Склад"
        // Заполняем данные через ввод:
        warehouse.setAddress(readStringInput("Адрес склада: "));
        warehouse.setCapacity(readIntInput("Вместимость: "));
        // Сохраняем склад через сервис
        warehouseService.addWarehouse(warehouse);
        showSuccess("Склад добавлен!");
    }

    // Показать список всех складов
    private static void listWarehouses() throws SQLException {
        // Получаем список из базы данных
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        System.out.println("\nСписок складов:");
        warehouses.forEach(w -> System.out.printf(
                "%d. %s | Вместимость: %d | Статус: %s%n",
                w.getWarehouseId(), w.getAddress(),
                w.getCapacity(), w.isActive() ? "открыт" : "закрыт"
        ));
    }

    // Изменить статус склада
    private static void toggleWarehouseStatus() throws SQLException {
        // Запрашиваем ID склада и новый статус
        int warehouseId = readIntInput("ID склада: ");
        boolean status = readIntInput("Новый статус (1-открыть, 0-закрыть): ") == 1;
        // Обновляем статус через сервис
        warehouseService.toggleWarehouseStatus(warehouseId, status);
        showSuccess("Статус склада обновлён!");
    }

    // Вывести информацию о складе
    private static void printWarehouseInfo () throws SQLException {
        // Запрашиваем ID склада
        int warehouseId = readIntInput("Введите ID склада: ");
        // Выводим информацию через сервис
        warehouseService.printWarehouseInfo(warehouseId);
    }

    // Вывод состояния склада
    private static boolean getWarehouseStatus(int warehouseId) throws SQLException {
        return warehouseService.getWarehouseStatus(warehouseId);
    }

    // Управление пунктами продаж (основное меню)
    private static void manageSalesPoints() throws SQLException {
        System.out.println("\n=== Управление пунктами продаж ===");
        System.out.println("1. Добавить пункт продаж");
        System.out.println("2. Список пунктов продаж");
        System.out.println("3. Изменить статус пункта");
        int choice = readIntInput("Выберите действие: ");

        switch (choice) {
            case 1 -> addSalesPoint();    // Добавление нового пункта
            case 2 -> listSalesPoints();  // Просмотр всех пунктов
            case 3 -> toggleSalesPointStatus(); // Изменение статуса
            default -> showError("Неверный выбор!");
        }
    }

    // Добавить новый пункт продаж
    private static void addSalesPoint() throws SQLException {
        SalesPoint point = new SalesPoint(); // Создаем объект "Пункт продаж"
        point.setAddress(readStringInput("Адрес пункта продаж: ")); // Заполняем адрес
        // Сохраняем через сервис
        salesPointService.addSalesPoint(point);
        showSuccess("Пункт продаж добавлен!");
    }

    // Показать список всех пунктов продаж
    private static void listSalesPoints() throws SQLException {
        // Получаем список из базы данных
        List<SalesPoint> points = salesPointService.getAllSalesPoints();
        System.out.println("\nСписок пунктов продаж:");
        points.forEach(p -> System.out.printf(
                "%d. %s | Статус: %s%n",
                p.getPointId(), p.getAddress(),
                p.isActive() ? "открыт" : "закрыт"
        ));
    }

    // Изменить статус пункта продаж
    private static void toggleSalesPointStatus() throws SQLException {
        // Запрашиваем ID пункта и новый статус
        int pointId = readIntInput("ID пункта продаж: ");
        boolean status = readIntInput("Новый статус (1-открыть, 0-закрыть): ") == 1;
        // Обновляем статус через сервис
        salesPointService.toggleSalesPointStatus(pointId, status);
        showSuccess("Статус пункта обновлён!");
    }

    // Управление ячейками хранения (основное меню)
    private static void manageStorageCells() throws SQLException {
        System.out.println("\n=== Управление ячейками хранения ===");
        System.out.println("1. Добавить ячейку");
        System.out.println("2. Сменить ответственного");
        int choice = readIntInput("Выберите действие: ");
        // Обработка выбора пользователя
        switch (choice) {
            case 1 -> addStorageCell();       // Создание новой ячейки
            case 2 -> changeCellResponsible(); // Изменение ответственного
            default -> showError("Неверный выбор!");
        }
    }

    // Добавление новой ячейки хранения
    private static void addStorageCell() throws SQLException {
        int warehouseId = readIntInput("ID склада: ");
        if (getWarehouseStatus(warehouseId)){
            StorageCell cell = new StorageCell(); // Создаем объект "Ячейка"
            // Заполняем данные через пользовательский ввод:
            cell.setWarehouseId(warehouseId);
            cell.setProductId(readIntInput("ID товара: "));
            cell.setQuantity(readIntInput("Количество: "));
            cell.setResponsibleEmployeeId(readIntInput("ID ответственного сотрудника: "));
            // Сохраняем ячейку через сервис
            storageCellService.addStorageCell(cell);
            showSuccess("Ячейка добавлена!");
        }
        else {
            showError("Склад с ID " + warehouseId + " закрыт. Используйте другой склад.");
        }
    }

    // Изменение ответственного сотрудника для ячейки
    private static void changeCellResponsible() throws SQLException {
        // Запрашиваем ID ячейки и нового сотрудника
        int cellId = readIntInput("ID ячейки: ");
        int newEmployeeId = readIntInput("ID нового сотрудника: ");
        // Обновляем данные через сервис
        storageCellService.updateResponsibleEmployee(cellId, newEmployeeId);
        showSuccess("Ответственный обновлён!");
    }

    // Управление покупателями (основное меню)
    private static void manageCustomers() throws SQLException {
        System.out.println("\n=== Управление покупателями ===");
        System.out.println("1. Добавить покупателя");
        System.out.println("2. Список покупателей");
        int choice = readIntInput("Выберите действие: ");
        // Обработка выбора пользователя
        switch (choice) {
            case 1 -> addCustomer();  // Добавление нового покупателя
            case 2 -> listCustomers(); // Просмотр списка
            default -> showError("Неверный выбор!");
        }
    }

    // Добавление нового покупателя
    private static void addCustomer() throws SQLException {
        Customer customer = new Customer(); // Создаем объект "Покупатель"
        // Заполняем данные через ввод:
        customer.setName(readStringInput("Имя покупателя: "));
        customer.setEmail(readStringInput("Email: "));
        // Сохраняем через сервис
        new CustomerService().addCustomer(customer);
        showSuccess("Покупатель добавлен!");
    }

    // Показать список всех покупателей
    private static void listCustomers() throws SQLException {
        // Получаем данные из базы через сервис
        List<Customer> customers = new CustomerService().getAllCustomers();
        System.out.println("\nСписок покупателей:");
        customers.forEach(c -> System.out.printf(
                "%d. %s | Email: %s%n",
                c.getCustomerId(), c.getName(), c.getEmail()
        ));
    }

    // Управление транзакциями (основное меню)
    private static void manageTransactions() throws SQLException {
        System.out.println("\n=== Управление транзакциями ===");
        System.out.println("1. Продажа товара");
        System.out.println("2. Возврат товара");
        System.out.println("3. Закупка товара");
        int choice = readIntInput("Выберите тип транзакции: ");
        // Запрашиваем общие данные для всех типов транзакций
        int cellId = readIntInput("ID ячейки: ");
        int productId = readIntInput("ID товара: ");
        int quantity = readIntInput("Количество: ");
        int employeeId = readIntInput("ID сотрудника: ");
        Integer salesPointId = readOptionalIntInput("ID пункта продажи (0 - пропустить): ");
        Integer customerId = readOptionalIntInput("ID покупателя (0 - пропустить): ");
        // Выбор типа операции
        switch (choice) {
            case 1 -> processTransaction("SALE", productId, quantity, employeeId, cellId, salesPointId, customerId);
            case 2 -> processTransaction("RETURN",  productId, quantity, employeeId, cellId, salesPointId, customerId);
            case 3 -> processTransaction("PURCHASE", productId, quantity, employeeId, cellId, salesPointId, customerId);
            default -> showError("Неверный выбор!");
        }
    }

    // Обработка транзакции
    private static void processTransaction(String type, int productId, int quantity,
                                           int employeeId, int cellId, Integer salesPointId, Integer customerId) throws SQLException {
        // Выполняем операцию через сервис
        transactionService.processTransaction(type, productId, quantity, employeeId, cellId, salesPointId, customerId);
        showSuccess("Транзакция успешно выполнена!");
    }

    // Управление отчетами
    private static void showReports() throws SQLException {
        System.out.println("\n=== Отчёты ===");
        System.out.println("1. Финансовый отчёт");
        int choice = readIntInput("Выберите отчёт: ");
        // Выбор типа отчета
        switch (choice) {
            case 1 -> reportService.showProfitReport(); // Формирование финансового отчета
            default -> showError("Неверный выбор!");
        }
    }

// Методы для ввода данных:

    // Чтение целого числа с консоли
    private static int readIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    // Чтение дробного числа с консоли
    private static double readDoubleInput(String prompt) {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine());
    }

    // Чтение строки с консоли
    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Чтение опционального ID (0 = null)
    private static Integer readOptionalIntInput(String prompt) {
        System.out.print(prompt);
        int value = Integer.parseInt(scanner.nextLine());
        return value == 0 ? null : value;
    }

    // Отображение успешных сообщений
    private static void showSuccess(String message) {
        System.out.println("\n✓ " + message); // Галочка в консоли
    }

    // Отображение ошибок
    private static void showError(String message) {
        System.out.println("\n✗ Ошибка: " + message); // Крестик в консоли
    }
}