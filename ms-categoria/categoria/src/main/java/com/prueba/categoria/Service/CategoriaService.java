package com.prueba.categoria.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.categoria.Model.Categoria;
import com.prueba.categoria.Repository.CategoriaRepository;

@Service
public class CategoriaService {
    @Autowired 
    private CategoriaRepository repository;

    public Categoria save(Categoria categoria) {
         return repository.save(categoria); 
    }

    public List<Categoria> findAll() {
         return repository.findAll(); 
    }
    public Optional<Categoria> findById(Integer id) {
         return repository.findById(id); 
    }
    public Categoria update(Integer id, Categoria categoria) {
        categoria.setId(id); 
        return repository.save(categoria); 
        }
    public void delete(Integer id) {
         repository.deleteById(id); 
        }
}
