package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository repository;
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        repository = mock(ProductRepository.class);
        service = new ProductServiceImpl();

        Field field = ProductServiceImpl.class.getDeclaredField("productRepository");
        field.setAccessible(true);
        field.set(service, repository);
    }

    @Test
    void testCreate() {
        Product product = new Product();
        when(repository.create(product)).thenReturn(product);

        Product result = service.create(product);

        assertEquals(product, result);
        verify(repository).create(product);
    }

    @Test
    void testFindAll() {
        Product p1 = new Product();
        Product p2 = new Product();

        when(repository.findAll()).thenReturn(List.of(p1, p2).iterator());

        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        when(repository.findById("1")).thenReturn(product);

        assertEquals(product, service.findById("1"));
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        when(repository.update(product)).thenReturn(product);

        assertEquals(product, service.update(product));
        verify(repository).update(product);
    }

    @Test
    void testDelete() {
        service.delete("1");
        verify(repository).delete("1");
    }
}