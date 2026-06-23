package com.prueba.envio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prueba.envio.Client.PedidoFeignClient;
import com.prueba.envio.Model.Envio;
import com.prueba.envio.Model.DTO.PedidoDTO;
import com.prueba.envio.Repository.EnvioRepository;
import com.prueba.envio.Service.EnvioService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class EnvioServiceTest {
@Mock
    private EnvioRepository repository;

    @Mock
    private PedidoFeignClient pedidoClient;

    @InjectMocks
    private EnvioService envioService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Envio envioInput = new Envio();
        when(repository.save(any(Envio.class))).thenReturn(envioInput);


        Envio resultado = envioService.save(envioInput);

        assertNotNull(resultado);
        verify(repository, times(1)).save(envioInput);
    }

    @Test
    void testFindAll() {
        List<Envio> listaSimulada = Arrays.asList(new Envio(), new Envio());
        when(repository.findAll()).thenReturn(listaSimulada);

        List<Envio> resultado = envioService.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarEnvioCompletoExitoso() {
        Integer envioId = 1;
        Integer pedidoId = 500;

        Envio envioMock = new Envio();
        envioMock.setId(envioId);
        envioMock.setPedidoId(pedidoId);
        envioMock.setDireccion("Av. Vitacura 1234");
        envioMock.setEstado("DESPACHADO");
        envioMock.setTrackingCode("TRK-998877");
        envioMock.setFechaCreacion(new Date());

        PedidoDTO pedidoDTOMock = new PedidoDTO();

        when(repository.findById(envioId)).thenReturn(Optional.of(envioMock));
        when(pedidoClient.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoDTOMock);

        Map<String, Object> respuesta = envioService.buscarEnvioCompleto(envioId);

        assertNotNull(respuesta);
        assertEquals(envioId, respuesta.get("id"));
        assertEquals(pedidoId, respuesta.get("pedidoId"));
        assertEquals("DESPACHADO", respuesta.get("estado"));
        assertEquals(pedidoDTOMock, respuesta.get("pedido"));
        verify(pedidoClient, times(1)).obtenerPedidoPorId(pedidoId);
    }

    @Test
    void testBuscarEnvioCompletoCuandoFeignFalla() {
        Integer envioId = 1;
        Integer pedidoId = 500;

        Envio envioMock = new Envio();
        envioMock.setId(envioId);
        envioMock.setPedidoId(pedidoId);

        when(repository.findById(envioId)).thenReturn(Optional.of(envioMock));
        when(pedidoClient.obtenerPedidoPorId(pedidoId)).thenThrow(new RuntimeException("Error de conexión"));
        Map<String, Object> respuesta = envioService.buscarEnvioCompleto(envioId);

        assertNotNull(respuesta);
        assertEquals("No disponible", respuesta.get("pedido")); // Verifica que entró al catch con éxito
        verify(pedidoClient, times(1)).obtenerPedidoPorId(pedidoId);
    }

    @Test
    void testDelete() {
        Integer idTest = 10;
        doNothing().when(repository).deleteById(idTest);

        envioService.delete(idTest);

        verify(repository, times(1)).deleteById(idTest);
    }

    @Test
    void testFiltrarPorEstado() {

        String estadoTest = "ENTREGADO";
        List<Envio> listaFiltrada = Arrays.asList(new Envio());
        when(repository.findByEstado(estadoTest)).thenReturn(listaFiltrada);

        List<Envio> resultado = envioService.filtrarPorEstado(estadoTest);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByEstado(estadoTest);
    }

    @Test
    void testFiltrarPorPedido() {
        Integer pedidoIdTest = 200;
        List<Envio> listaFiltrada = Arrays.asList(new Envio());
        when(repository.findByPedidoId(pedidoIdTest)).thenReturn(listaFiltrada);

        List<Envio> resultado = envioService.filtrarPorPedido(pedidoIdTest);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByPedidoId(pedidoIdTest);
    }
}
