CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    total INT NOT NULL
);