package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    private List<Product> products;

    @BeforeEach
    void setup() {
        products = new ArrayList<>();
        products.add(new Product());
    }

    @Test
    void testCreateOrderSuccess() {

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        when(orderRepository.findById("1")).thenReturn(null);

        Order result = orderService.createOrder(order);

        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    void testCreateOrderDuplicate() {

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        when(orderRepository.findById("1")).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNull(result);
    }

    @Test
    void testUpdateStatusSuccess() {

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        when(orderRepository.findById("1")).thenReturn(order);

        Order updated = orderService.updateStatus("1","SUCCESS");

        assertEquals("SUCCESS", updated.getStatus());
    }

    @Test
    void testFindById() {

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        when(orderRepository.findById("1")).thenReturn(order);

        Order result = orderService.findById("1");

        assertEquals(order, result);
    }

    @Test
    void testFindAllByAuthor() {

        when(orderRepository.findAllByAuthor("affandi")).thenReturn(List.of());

        List<Order> result = orderService.findAllByAuthor("affandi");

        assertNotNull(result);
    }

    @Test
    void testFindAllOrders() {

        when(orderRepository.findAll()).thenReturn(List.of());

        List<Order> result = orderService.findAll();

        assertNotNull(result);
    }

}