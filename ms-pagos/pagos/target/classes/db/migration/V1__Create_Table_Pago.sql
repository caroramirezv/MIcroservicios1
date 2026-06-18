CREATE TABLE pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL UNIQUE,
    monto INT NOT NULL,
    metodo_pago VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    fecha_pago DATETIME NOT NULL
);