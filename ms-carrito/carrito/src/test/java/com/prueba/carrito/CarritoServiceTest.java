package com.prueba.carrito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.prueba.carrito.Client.RopaFeignClient;
import com.prueba.carrito.Client.UsuarioFeignClient;
import com.prueba.carrito.Model.Carrito;
import com.prueba.carrito.Model.DTO.RopaDTO;
import com.prueba.carrito.Repository.CarritoRepository;
import com.prueba.carrito.Service.CarritoService;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
public class CarritoServiceTest {
@Mock
    private CarritoRepository repository;

    @Mock
    private RopaFeignClient ropaClient;

    @Mock
    private UsuarioFeignClient usuarioClient;


    @InjectMocks
    private CarritoService carritoService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSaveExitosamente() {

        Carrito carritoEntrada = new Carrito();
        Carrito carritoGuardado = new Carrito(); 
        
        when(repository.save(any(Carrito.class))).thenReturn(carritoGuardado);


        Carrito resultado = carritoService.save(carritoEntrada);

        assertNotNull(resultado);
        verify(repository, times(1)).save(carritoEntrada); 
    }

    @Test
    void testBuscarCarritoCompletoExitosamente() {
        // Arrange (Preparar el escenario simulando los datos de tu método)
        Integer carritoId = 1;
        
        Carrito carritoMock = new Carrito();
        carritoMock.setId(carritoId);
        carritoMock.setTotal_articulos(2);
        carritoMock.setTotal(50000.0);
        carritoMock.setUsuarioId(99);
        carritoMock.setRopaIds(Arrays.asList(10, 11)); // Dos IDs de ropa para probar tu Stream

        // Simulamos la respuesta del repositorio principal
        when(repository.findById(carritoId)).thenReturn(Optional.of(carritoMock));

        // Simulamos las respuestas de los clientes Feign
        // RopaFeignClient: Simulamos que devuelve un DTO genérico/ficticio para cada ID de ropa
        RopaDTO ropaMock = new RopaDTO(); // Usa tu clase real RopaDTO aquí
        when(ropaClient.obtenerRopaPorId(anyInt())).thenReturn(ropaMock);


        // Act (Ejecutar la lógica de tu método)
        Map<String, Object> respuesta = carritoService.buscarCarritoCompleto(carritoId);

        // Assert (Verificaciones finales)
        assertNotNull(respuesta);
        assertEquals(carritoId, respuesta.get("id"));
        assertEquals(99, respuesta.get("usuarioId"));
        
        // Verificamos que las listas de ropas se hayan procesado (el stream corrió 2 veces)
        List<?> ropasResultado = (List<?>) respuesta.get("ropas");
        assertEquals(2, ropasResultado.size());

        // Verificamos que se llamó a los clientes Feign las veces correctas
        verify(ropaClient, times(2)).obtenerRopaPorId(anyInt());
        verify(usuarioClient, times(1)).obtenerUsuarioId(99);
    }
}
