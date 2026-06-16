CREATE TABLE notificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    detalle VARCHAR(255) NOT NULL
);

CREATE TABLE notificaciones_usuario_ids (
    notificaciones_id INT NOT NULL,
    usuario_id INT NOT NULL,
    CONSTRAINT fk_notificacion_usuarios FOREIGN KEY (notificaciones_id) REFERENCES notificaciones(id) ON DELETE CASCADE
);