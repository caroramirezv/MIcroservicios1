package com.prueba.pedido;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.pedido.Cliente.UsuarioFeignClient;
import com.prueba.pedido.Model.DTO.UsuarioDTO;
import com.prueba.pedido.Model.Pedido;
import com.prueba.pedido.Repository.PedidoRepository;
import com.prueba.pedido.Service.PedidoService;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository repository;

    @Mock
    private UsuarioFeignClient usuarioClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        // Given
        Pedido pedido = new Pedido();
        when(repository.save(pedido)).thenReturn(pedido);

        // When
        Pedido saved = pedidoService.save(pedido);

        // Then
        assertNotNull(saved);
        verify(repository, times(1)).save(pedido);
    }

    @Test
    public void testFindAll() {
        // Given
        Pedido pedido = new Pedido();
        when(repository.findAll()).thenReturn(List.of(pedido));

        // When
        List<Pedido> pedidos = pedidoService.findAll();

        // Then
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testBuscarPedidoCompleto_Encontrado() {
        // Given
        Integer pedidoId = 1;
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setUsuarioId(10);
        pedido.setFecha("2024-01-15");
        pedido.setEstado("Pendiente");
        pedido.setTotal(150);

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(10);
        usuario.setNombre("Carolina Ramirez");

        when(repository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(usuarioClient.obtenerUsuarioPorId(10)).thenReturn(usuario);

        // When
        Map<String, Object> respuesta = pedidoService.buscarPedidoCompleto(pedidoId);

        // Then
        assertNotNull(respuesta);
        assertEquals(pedidoId, respuesta.get("id"));
        assertEquals(usuario, respuesta.get("cliente"));
        assertEquals("2024-01-15", respuesta.get("fecha"));
        assertEquals("Pendiente", respuesta.get("estado"));
        assertEquals(150, respuesta.get("total"));

        verify(repository, times(1)).findById(pedidoId);
        verify(usuarioClient, times(1)).obtenerUsuarioPorId(10);
    }

    @Test
    public void testBuscarPedidoCompleto_NoExiste() {
        // Given
        Integer pedidoId = 99;
        when(repository.findById(pedidoId)).thenReturn(Optional.empty());

        // When
        Map<String, Object> respuesta = pedidoService.buscarPedidoCompleto(pedidoId);

        // Then
        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());
        verify(repository, times(1)).findById(pedidoId);
        verify(usuarioClient, never()).obtenerUsuarioPorId(any());
    }

    @Test
    public void testCambiarEstado_Exitoso() {
        // Given
        Integer id = 1;
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setEstado("Pendiente");

        when(repository.findById(id)).thenReturn(Optional.of(pedido));
        when(repository.save(pedido)).thenReturn(pedido);

        // When
        Pedido actualizado = pedidoService.cambiarEstado(id, "Enviado");

        // Then
        assertNotNull(actualizado);
        assertEquals("Enviado", actualizado.getEstado());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(pedido);
    }

    @Test
    public void testCambiarEstado_LanzaExcepcionCuandoNoExiste() {
        // Given
        Integer id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NoSuchElementException.class, () -> {
            pedidoService.cambiarEstado(id, "Enviado");
        });

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    public void testDelete_Exitoso() {
        // Given
        Integer id = 1;
        Pedido pedido = new Pedido();
        pedido.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(pedido));
        doNothing().when(repository).deleteById(id);

        // When
        pedidoService.delete(id);

        // Then
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testDelete_LanzaExcepcionCuandoNoExiste() {
        // Given
        Integer id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.delete(id);
        });

        assertEquals("Pedido no encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(any());
    }

    @Test
    public void testFindByUsuarioId() {
        // Given
        Integer usuarioId = 10;
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(usuarioId);

        when(repository.findByUsuarioId(usuarioId)).thenReturn(List.of(pedido));

        // When
        List<Pedido> resultado = pedidoService.findByUsuarioId(usuarioId);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByUsuarioId(usuarioId);
    }

    @Test
    public void testFindByEstado() {
        // Given
        String estado = "Pendiente";
        Pedido pedido = new Pedido();
        pedido.setEstado(estado);

        when(repository.findByEstado(estado)).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.findByEstado(estado);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(estado, resultado.get(0).getEstado());
        verify(repository, times(1)).findByEstado(estado);
    }
}
