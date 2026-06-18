-- 1. Tabla principal de la búsqueda
CREATE TABLE busqueda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    categoria VARCHAR(100) NULL 
);

CREATE TABLE busqueda_resultados (
    busqueda_id INT NOT NULL,
    ropa_id INT NOT NULL,
    CONSTRAINT fk_busqueda_resultados FOREIGN KEY (busqueda_id) REFERENCES busqueda(id) ON DELETE CASCADE
);