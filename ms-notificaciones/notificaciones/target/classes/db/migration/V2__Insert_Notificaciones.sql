INSERT INTO notificaciones (id, nombre, detalle) 
VALUES (1, 'Alerta de Pago', 'Tu pedido #123 ha sido pagado exitosamente.');

INSERT INTO notificaciones_usuario_ids (notificaciones_id, usuario_id) VALUES (1, 10);
INSERT INTO notificaciones_usuario_ids (notificaciones_id, usuario_id) VALUES (1, 11);
INSERT INTO notificaciones_usuario_ids (notificaciones_id, usuario_id) VALUES (1, 12);