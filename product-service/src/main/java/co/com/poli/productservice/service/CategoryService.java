package co.com.poli.productservice.service;

import co.com.poli.productservice.persistence.entity.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);

    void delete(Category category);

    List<Category> findAll();

    Category findById(Long id);
}