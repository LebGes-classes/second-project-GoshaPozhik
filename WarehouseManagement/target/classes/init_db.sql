CREATE TABLE IF NOT EXISTS products (
                                        product_id SERIAL PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
                                        purchase_price DECIMAL(10,2) NOT NULL,
                                        selling_price DECIMAL(10,2) NOT NULL,
                                        quantity INT NOT NULL CHECK (quantity >= 0)
);

CREATE TABLE IF NOT EXISTS employees (
                                         employee_id SERIAL PRIMARY KEY,
                                         full_name VARCHAR(100) NOT NULL,
                                         position VARCHAR(50) NOT NULL,
                                         hire_date DATE DEFAULT CURRENT_DATE,
                                         is_active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS warehouses (
                                          warehouse_id SERIAL PRIMARY KEY,
                                          address VARCHAR(200) UNIQUE NOT NULL,
                                          capacity INT NOT NULL,
                                          is_active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS storage_cells (
                                             cell_id SERIAL PRIMARY KEY,
                                             warehouse_id INT NOT NULL REFERENCES warehouses(warehouse_id),
                                             product_id INT NOT NULL REFERENCES products(product_id),
                                             quantity INT NOT NULL CHECK (quantity >= 0),
                                             responsible_employee_id INT NOT NULL REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS sales_points (
                                            point_id SERIAL PRIMARY KEY,
                                            address VARCHAR(200) UNIQUE NOT NULL,
                                            is_active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS customers (
                                         customer_id SERIAL PRIMARY KEY,
                                         name VARCHAR(100) NOT NULL,
                                         email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
                                            transaction_id SERIAL PRIMARY KEY,
                                            type VARCHAR(20) NOT NULL CHECK (type IN ('PURCHASE', 'SALE', 'RETURN')),
                                            product_id INT NOT NULL REFERENCES products(product_id),
                                            quantity INT NOT NULL,
                                            date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            employee_id INT NOT NULL REFERENCES employees(employee_id),
                                            storage_cell_id INT REFERENCES storage_cells(cell_id),
                                            sales_point_id INT REFERENCES sales_points(point_id),
                                            customer_id INT REFERENCES customers(customer_id)
);


