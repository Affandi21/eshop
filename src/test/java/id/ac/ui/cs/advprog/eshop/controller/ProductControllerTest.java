package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService service;
    private ProductController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        service = mock(ProductService.class);
        controller = new ProductController(service); // ✅ no reflection
        model = mock(Model.class);
    }

    @Test
    void testIndex() {
        assertEquals("index", controller.index());
    }

    @Test
    void testCreateProductPage() {
        String view = controller.createProductPage(model);

        assertEquals("createProduct", view);
        verify(model).addAttribute(eq("product"), any());
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();

        String view = controller.createProductPost(product, model);

        verify(service).create(product);
        assertEquals("redirect:/product/list", view);
    }

    @Test
    void testProductListPage() {
        when(service.findAll()).thenReturn(List.of(new Product()));

        String view = controller.productListPage(model);

        assertEquals("productList", view);
        verify(model).addAttribute(eq("products"), any());
    }

    @Test
    void testEditProductPage() {
        Product product = new Product();
        when(service.findById("1")).thenReturn(product);

        String view = controller.editProductPage("1", model);

        assertEquals("editProduct", view);
        verify(model).addAttribute("product", product);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();

        String view = controller.editProductPost(product);

        verify(service).update(product);
        assertEquals("redirect:/product/list", view);
    }

    @Test
    void testDeleteProduct() {
        String view = controller.deleteProduct("1");

        verify(service).delete("1");
        assertEquals("redirect:/product/list", view);
    }
}