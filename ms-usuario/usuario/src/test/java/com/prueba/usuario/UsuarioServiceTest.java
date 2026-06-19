package com.prueba.Usuario;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.busqueda.Client.PedidoFeignClient;
import com.prueba.busqueda.Model.Usuario;
import com.prueba.busqueda.Model.DTO.PedidoDTO;
import com.prueba.busqueda.Repository.UsuarioRepository;
import com.prueba.busqueda.Service.UsuarioService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository repository;

    @BeforeEachvoid setUp() { 
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();
        
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.save(usuario);

        assertNotNull(saved);
        verify(repository, times(1)).save(usuario);
    }

    @Test
    public void testFindAll() {
        Usuario usuario = new Usuario();
        
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.findAll();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testBuscarUsuarioCompleto() {
        Integer usuarioId = 1;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@correo.com");

        when(repository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        
        Map<String, Object> respuesta = usuarioService.buscarUsuarioCompleto(usuarioId);

        assertNotNull(respuesta);
        assertEquals(usuarioId, respuesta.get("id"));
        assertEquals("Juan Pérez", respuesta.get("nombre"));
        assertEquals("juan@correo.com", respuesta.get("email"));
        
        verify(repository, times(1)).findById(usuarioId);
    }

    @Test
    public void testDelete() {
        Integer id = 1;

        doNothing().when(repository).deleteById(id);

        usuarioService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdate() {
        Integer id = 1;
        
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        
        Usuario usuarioNuevosDatos = new Usuario();
        usuarioNuevosDatos.setNombre("Pedro Actualizado");

        when(repository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(repository.save(usuarioNuevosDatos)).thenReturn(usuarioNuevosDatos);

        Usuario result = usuarioService.update(id, usuarioNuevosDatos);

        assertNotNull(result);
        assertEquals(id, usuarioNuevosDatos.getId()); 
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(usuarioNuevosDatos);
    }

    @Test
    public void testBuscarPorEmail() {
        String email = "maria@correo.com";
        Usuario usuario = new Usuario();
        usuario.setId(2);
        usuario.setNombre("Maria");
        usuario.setEmail(email);
        usuario.setTelefono("555-1234");

        when(repository.findByEmail(email)).thenReturn(List.of(usuario));

        Map<String, Object> respuesta = usuarioService.buscarPorEmail(email);

        assertNotNull(respuesta);
        assertFalse(respuesta.isEmpty());
        assertEquals(2, respuesta.get("id"));
        assertEquals("Maria", respuesta.get("nombre"));
        assertEquals(email, respuesta.get("email"));
        assertEquals("555-1234", respuesta.get("telefono"));
        
        verify(repository, times(1)).findByEmail(email);
    }
    
    @Test
    public void testBuscarPorEmail_Vacio() {
        String email = "noexiste@correo.com";

        when(repository.findByEmail(email)).thenReturn(List.of());

        Map<String, Object> respuesta = usuarioService.buscarPorEmail(email);

        assertNotNull(respuesta);
        assertTrue(respuesta.isEmpty());
    }
}

