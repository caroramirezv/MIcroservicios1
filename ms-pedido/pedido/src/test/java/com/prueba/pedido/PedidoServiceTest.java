package com.prueba.pedido;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.busqueda.Client.UsuarioFeignClient;
import com.prueba.busqueda.Model.Pedido;
import com.prueba.busqueda.Model.DTO.UsuarioDTO;
import com.prueba.busqueda.Repository.PedidoRepository;
import com.prueba.busqueda.Service.PedidoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository repository;

    @BeforeEach
    public void setUp() { 
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testSave() {
        Pedido pedido = new Pedido();
        
        when(repository.save(pedido)).thenReturn(pedido);

         Pedido saved = pedidoService.save(pedido); 

        assertNotNull(saved);
        verify(repository, times(1)).save(pedido);
    }

    @Test
    public void testFindAll() {
        Pedido pedido = new Pedido();
        
        when(repository.findAll()).thenReturn(List.of(pedido));

         List<Pedido> pedidos = pedidoService.findAll();

        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testBuscarPedidoCompleto() {
        Integer pedidoId = 1;
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setUsuarioId(1);
        pedido.setFecha("2024-01-15");
        pedido.setTotal(150.00);

        when(repository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.findById(pedidoId);

        assertTrue(resultado.isPresent());
        assertEquals(pedidoId, resultado.get().getId());
        verify(repository, times(1)).findById(pedidoId);
    }
}