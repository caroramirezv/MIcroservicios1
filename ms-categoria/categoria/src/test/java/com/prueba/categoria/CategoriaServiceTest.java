package com.prueba.categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.prueba.categoria.Model.Categoria;
import com.prueba.categoria.Repository.CategoriaRepository;
import com.prueba.categoria.Service.CategoriaService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class CategoriaServiceTest {
@Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService categoriaService; // <-- Asegúrate de que coincida con el nombre de tu clase de servicio

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks correctamente
    }
    @Test
    void testSave() {
        // Arrange
        Categoria categoriaInput = new Categoria();
        categoriaInput.setNombre("Ropa");
        
        when(repository.save(any(Categoria.class))).thenReturn(categoriaInput);

        // Act
        Categoria resultado = categoriaService.save(categoriaInput);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ropa", resultado.getNombre());
        verify(repository, times(1)).save(categoriaInput);
    }

    @Test
    void testFindAll() {
        // Arrange
        Categoria cat1 = new Categoria();
        Categoria cat2 = new Categoria();
        List<Categoria> listaSimulada = Arrays.asList(cat1, cat2);

        when(repository.findAll()).thenReturn(listaSimulada);

        // Act
        List<Categoria> resultado = categoriaService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Integer idTest = 1;
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(idTest);

        when(repository.findById(idTest)).thenReturn(Optional.of(categoriaMock));

        // Act
        Optional<Categoria> resultado = categoriaService.findById(idTest);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(idTest, resultado.get().getId());
        verify(repository, times(1)).findById(idTest);
    }

    @Test
    void testUpdate() {
        // Arrange
        Integer idTest = 5;
        Categoria categoriaInput = new Categoria();
        categoriaInput.setNombre("Electrónica");

        // El método update le setea el ID internamente antes de guardar
        when(repository.save(any(Categoria.class))).thenAnswer(invocation -> {
            Categoria c = invocation.getArgument(0);
            return c; // Retorna el mismo objeto modificado
        });

        // Act
        Categoria resultado = categoriaService.update(idTest, categoriaInput);

        // Assert
        assertNotNull(resultado);
        assertEquals(idTest, resultado.getId()); // Verifica que se le asignó el ID correcto
        assertEquals("Electrónica", resultado.getNombre());
        verify(repository, times(1)).save(categoriaInput);
    }

    @Test
    void testDelete() {
        // Arrange
        Integer idTest = 10;
        
        // Al ser un método void (deleteById), solo le decimos a Mockito qué hacer (nada)
        doNothing().when(repository).deleteById(idTest);

        // Act
        categoriaService.delete(idTest);

        // Assert
        verify(repository, times(1)).deleteById(idTest);
    }
}
