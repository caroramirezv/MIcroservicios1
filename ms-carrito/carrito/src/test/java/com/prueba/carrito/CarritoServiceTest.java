package com.prueba.carrito;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.prueba.carrito.Client.RopaFeignClient;
import com.prueba.carrito.Client.UsuarioFeignClient;
import com.prueba.carrito.Model.Carrito;
import com.prueba.carrito.Model.DTO.RopaDTO;
import com.prueba.carrito.Repository.CarritoRepository;
import com.prueba.carrito.Service.CarritoService;
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
        
        Integer carritoId = 1;
        
        Carrito carritoMock = new Carrito();
        carritoMock.setId(carritoId);
        carritoMock.setTotal_articulos(2);
        carritoMock.setTotal(50000.0);
        carritoMock.setUsuarioId(99);
        carritoMock.setRopaIds(Arrays.asList(10, 11)); 

        
        when(repository.findById(carritoId)).thenReturn(Optional.of(carritoMock));

        RopaDTO ropaMock = new RopaDTO(); 
        when(ropaClient.obtenerRopaPorId(anyInt())).thenReturn(ropaMock);


   
        Map<String, Object> respuesta = carritoService.buscarCarritoCompleto(carritoId);

       
        assertNotNull(respuesta);
        assertEquals(carritoId, respuesta.get("id"));
        assertEquals(99, respuesta.get("usuarioId"));
        
        
        List<?> ropasResultado = (List<?>) respuesta.get("ropas");
        assertEquals(2, ropasResultado.size());

        
        verify(ropaClient, times(2)).obtenerRopaPorId(anyInt());
        verify(usuarioClient, times(1)).obtenerUsuarioId(99);
    }
}
