package co.com.poli.productservice;

import co.com.poli.productservice.persistence.entity.Category;
import co.com.poli.productservice.persistence.entity.Product;
import co.com.poli.productservice.persistence.repository.ProductRepository;
import co.com.poli.productservice.service.ProductService;
import co.com.poli.productservice.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void begin() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);

        Product product = Product.builder()
                .id(4L)
                .name("balon")
                .price(10000D)
                .stock(10D)
                .category(Category.builder().id(1L).build()).build();
        Mockito.when(productRepository.findById(4L))
                .thenReturn(Optional.of(product));
    }

    @Test
    public void when_findById_return_Product() {
        Product product = productService.findById(4L);
        Assertions.assertThat(product.getName()).isEqualTo("balon");
    }

}
