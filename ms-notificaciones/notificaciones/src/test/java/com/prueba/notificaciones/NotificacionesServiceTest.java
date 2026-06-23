package com.prueba.notificaciones;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.notificaciones.Client.UsuarioFeignNotificaciones;
import com.prueba.notificaciones.Model.Notificaciones;
import com.prueba.notificaciones.Model.DTO.UsuarioDTO;
import com.prueba.notificaciones.Repository.NotificacionesRepository;
import com.prueba.notificaciones.Service.NotificacionesService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotificacionesServiceTest {
    @InjectMocks
    private NotificacionesService notificacionesService;

    @Mock
    private NotificacionesRepository repository;

    @Mock
    private UsuarioFeignNotificaciones usuarioClient;

    @BeforeEach
    public void setUp() { 
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testSave() {
        Notificaciones notificacion = new Notificaciones();
        
        when(repository.save(notificacion)).thenReturn(notificacion);

        Notificaciones saved = notificacionesService.save(notificacion); 

        assertNotNull(saved);
        verify(repository, times(1)).save(notificacion);
    }

    @Test
    public void testFindAll() {
        Notificaciones notificacion = new Notificaciones();
        
        when(repository.findAll()).thenReturn(List.of(notificacion));

        List<Notificaciones> lista = notificacionesService.findAll();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testBuscarNotificacionesCompleto_Encontrado() {
        Integer id = 1;
        
        Notificaciones notificacion = new Notificaciones();
        notificacion.setId(id);
        notificacion.setNombre("Alerta de Stock");
        // Simulamos que la notificación tiene asociados los IDs de usuario 100 y 101
        notificacion.setUsuarioIds(List.of(100, 101)); 

        UsuarioDTO usuario1 = new UsuarioDTO();
        UsuarioDTO usuario2 = new UsuarioDTO();

        when(repository.findById(id)).thenReturn(Optional.of(notificacion));
        when(usuarioClient.obtenerUsuarioPorId(100)).thenReturn(usuario1);
        when(usuarioClient.obtenerUsuarioPorId(101)).thenReturn(usuario2);

        Map<String, Object> respuesta = notificacionesService.buscarNotificacionesCompleto(id);

        assertNotNull(respuesta);
        assertEquals(id, respuesta.get("id"));
        assertEquals("Alerta de Stock", respuesta.get("nombre"));
        
        List<UsuarioDTO> usuariosResultado = (List<UsuarioDTO>) respuesta.get("usuario");
        assertEquals(2, usuariosResultado.size());

        verify(repository, times(1)).findById(id);
        verify(usuarioClient, times(1)).obtenerUsuarioPorId(100);
        verify(usuarioClient, times(1)).obtenerUsuarioPorId(101);
    }

    @Test
    public void testBuscarNotificacionesCompleto_NoExiste() {
        Integer id = 99;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Map<String, Object> respuesta = notificacionesService.buscarNotificacionesCompleto(id);

        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());
        verify(repository, times(1)).findById(id);
        verify(usuarioClient, never()).obtenerUsuarioPorId(anyInt());
    }

    @Test
    public void testDelete() {
        Integer id = 1;

        doNothing().when(repository).deleteById(id);

        notificacionesService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdate_Exitoso() {
        Integer id = 1;
        Notificaciones notificacionExistente = new Notificaciones();
        Notificaciones nuevosDatos = new Notificaciones();
        nuevosDatos.setNombre("Nombre Actualizado");

        when(repository.findById(id)).thenReturn(Optional.of(notificacionExistente));
        when(repository.save(nuevosDatos)).thenReturn(nuevosDatos);

        Notificaciones result = notificacionesService.update(id, nuevosDatos);

        assertNotNull(result);
        assertEquals(id, nuevosDatos.getId());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(nuevosDatos);
    }

    @Test
    public void testUpdate_LanzaExcepcion() {
        Integer id = 99;
        Notificaciones nuevosDatos = new Notificaciones();

        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificacionesService.update(id, nuevosDatos);
        });

        assertEquals("No encontrado id: " + id, exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    public void testBuscarPorNombre() {
        String nombre = "Promo";
        Notificaciones notificacion = new Notificaciones();
        notificacion.setNombre("Promo de Invierno");

        when(repository.findByNombreContainingIgnoreCase(nombre)).thenReturn(List.of(notificacion));

        List<Notificaciones> resultado = notificacionesService.buscarPorNombre(nombre);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findByNombreContainingIgnoreCase(nombre);
    }

}
