package com.prueba.ropa;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.ropa.Model.Ropa;
import com.prueba.ropa.Repository.RopaRepository;
import com.prueba.ropa.Service.RopaService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RopaServiceTest {

    @InjectMocks
    private RopaService ropaService;

    @Mock
    private RopaRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        Ropa ropa = new Ropa();
        
        when(repository.save(ropa)).thenReturn(ropa);

        Ropa saved = ropaService.save(ropa);

        assertNotNull(saved);
        verify(repository, times(1)).save(ropa);
    }

    @Test
    public void testFindAll() {
        Ropa ropa = new Ropa();
        
        when(repository.findAll()).thenReturn(List.of(ropa));

        List<Ropa> listaRopa = ropaService.findAll();

        assertNotNull(listaRopa);
        assertEquals(1, listaRopa.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testBuscarRopaCompleto() {
        Integer ropaId = 1;
        Ropa ropa = new Ropa();
        ropa.setId(ropaId);
        ropa.setNombre("Camisa de lino");
        ropa.setDetalle("Camisa manga larga, color blanco");
        ropa.setPrecio(25000); 

        when(repository.findById(ropaId)).thenReturn(Optional.of(ropa));
        
        Map<String, Object> respuesta = ropaService.buscarRopaCompleto(ropaId);

        assertNotNull(respuesta);
        assertEquals(ropaId, respuesta.get("id"));
        assertEquals("Camisa de lino", respuesta.get("nombre"));
        assertEquals("Camisa manga larga, color blanco", respuesta.get("detalle"));
        assertEquals(25000, respuesta.get("precio"));
        
        verify(repository, times(1)).findById(ropaId);
    }

    @Test
    public void testBuscarRopaCompleto_NoExiste() {
        Integer ropaId = 99;

        when(repository.findById(ropaId)).thenReturn(Optional.empty());
        
        Map<String, Object> respuesta = ropaService.buscarRopaCompleto(ropaId);

        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());
        
        verify(repository, times(1)).findById(ropaId);
    }

    @Test
    public void testDelete() {
        Integer id = 1;

        doNothing().when(repository).deleteById(id);

        ropaService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdate_Exitoso() {
        Integer id = 1;
        
        Ropa ropaNuevosDatos = new Ropa();
        ropaNuevosDatos.setNombre("Pantalón Jean");

        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(ropaNuevosDatos)).thenReturn(ropaNuevosDatos);

        Ropa result = ropaService.update(id, ropaNuevosDatos);

        assertNotNull(result);
        assertEquals(id, ropaNuevosDatos.getId()); 
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).save(ropaNuevosDatos);
    }

    @Test
    public void testUpdate_LanzaExcepcionCuandoNoExiste() {
        Integer id = 99;
        Ropa ropaNuevosDatos = new Ropa();

        when(repository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ropaService.update(id, ropaNuevosDatos);
        });

        assertEquals("Ropa no encontrada con id: " + id, exception.getMessage());
        
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).save(any());
    }

    @Test
    public void testBuscarPorNombre() {
        String nombre = "Camisa";
        Ropa ropa = new Ropa();
        ropa.setId(1);
        ropa.setNombre(nombre);

        when(repository.findByNombre(nombre)).thenReturn(List.of(ropa));

        List<Ropa> respuesta = ropaService.buscarPorNombre(nombre);

        assertNotNull(respuesta);
        assertFalse(respuesta.isEmpty());
        assertEquals(1, respuesta.size());
        assertEquals(nombre, respuesta.get(0).getNombre());
        
        verify(repository, times(1)).findByNombre(nombre);
    }
}
