package co.com.poli.productservice;

import co.com.poli.productservice.persistence.entity.Category;
import co.com.poli.productservice.persistence.entity.Product;
import co.com.poli.productservice.persistence.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void when_findByCategory_return_ListProduct(){
        Product product = Product.builder()
                .name("test")
                .stock(10D)
                .price(10000D)
                .category(Category.builder().id(1L).build()).build();
        productRepository.save(product);
        List<Product> products = productRepository.findByCategory(product.getCategory());
        Assertions.assertThat(products.size()).isEqualTo(2);
    }

}
