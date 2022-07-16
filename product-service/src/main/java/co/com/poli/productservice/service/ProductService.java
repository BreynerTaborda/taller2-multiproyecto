package co.com.poli.productservice.service;

import co.com.poli.productservice.persistence.entity.Category;
import co.com.poli.productservice.persistence.entity.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);

    void delete(Product product);

    List<Product> findAll();

    List<Product> findByCategory(Category category);

    Product findById(Long id);

}