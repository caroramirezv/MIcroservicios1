package com.prueba.pagos;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.pagos.Client.PedidoFeignClient;
import com.prueba.pagos.Model.Pago;
import com.prueba.pagos.Model.DTO.PedidoDTO;
import com.prueba.pagos.Repository.PagoRepository;
import com.prueba.pagos.Service.PagoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PagosServiceTest {

    @InjectMocks
    private PagoService pagoService;

    @Mock
    private PagoRepository repository;

    @Mock
    private PedidoFeignClient pedidoClient;

    @BeforeEach
    public void setUp() { 
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testProcesarPago() {
        Pago pago = new Pago();
        
        when(repository.save(pago)).thenReturn(pago);

        Pago saved = pagoService.procesarPago(pago); 
        
        assertNotNull(saved);
        verify(repository, times(1)).save(pago);
    }

    @Test
    public void testFindAll() {
        Pago pago = new Pago();
        
        when(repository.findAll()).thenReturn(List.of(pago));

        List<Pago> pagos = pagoService.findAll();
        
        assertNotNull(pagos);
        assertEquals(1, pagos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testBuscarPagoCompleto_ConPagos() {
        Integer pedidoId = 1;
        Pago pago = new Pago();
        pago.setId(100);
        
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedidoId);


        when(repository.findByPedidoId(pedidoId)).thenReturn(List.of(pago));

        when(pedidoClient.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoDTO);

        Map<String, Object> resultado = pagoService.buscarPagoCompleto(pedidoId);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(pago, resultado.get("pago"));
        assertEquals(pedidoDTO, resultado.get("pedido"));
        
        verify(repository, times(1)).findByPedidoId(pedidoId);
        verify(pedidoClient, times(1)).obtenerPedidoPorId(pedidoId);
    }

    @Test
    public void testBuscarPagoCompleto_SinPagos() {
        Integer pedidoId = 1;


        when(repository.findByPedidoId(pedidoId)).thenReturn(List.of());

        Map<String, Object> resultado = pagoService.buscarPagoCompleto(pedidoId);


        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(repository, times(1)).findByPedidoId(pedidoId);
        verify(pedidoClient, never()).obtenerPedidoPorId(anyInt());
    }

    @Test
    public void testDelete_Exitoso() {
        Integer id = 1;
        Pago pago = new Pago();

        when(repository.findById(id)).thenReturn(Optional.of(pago));
        doNothing().when(repository).deleteById(id);

        pagoService.delete(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testDelete_LanzaExcepcion() {
        Integer id = 99;

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagoService.delete(id);
        });

        assertEquals("Pedido no encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).deleteById(anyInt());
    }

    @Test
    public void testUpdate_Exitoso() {
        Integer id = 1;
        Pago pago = new Pago();
        
        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(pago)).thenReturn(pago);

        Pago actualizado = pagoService.update(id, pago);

        assertNotNull(actualizado);
        assertEquals(id, pago.getId()); 
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).save(pago);
    }

    @Test
    public void testUpdate_LanzaExcepcion() {
        Integer id = 99;
        Pago pago = new Pago();


        when(repository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagoService.update(id, pago);
        });

        assertEquals("Ropa no encontrada con id: " + id, exception.getMessage());
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).save(any());
    }

    @Test
    public void testFiltrarPorEstado() {
        String estado = "APROBADO";
        Pago pago = new Pago();
        
        when(repository.findByEstado(estado)).thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.filtrarPorEstado(estado);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByEstado(estado);
    }

    @Test
    public void testFiltrarPorMetodPagos() {
        String metodo = "TARJETA";
        Pago pago = new Pago();
        
        when(repository.findByMetodoPago(metodo)).thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.filtrarPorMetodPagos(metodo);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByMetodoPago(metodo);
    }
}
