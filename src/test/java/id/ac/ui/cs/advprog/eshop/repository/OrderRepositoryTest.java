package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();

        products = new ArrayList<>();
        products.add(new Product());
    }

    @Test
    void testSaveNewOrder() {

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        Order result = orderRepository.save(order);

        assertEquals(order, result);
    }

    @Test
    void testSaveUpdateOrderSameId() {

        Order order1 = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        Order order2 = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        orderRepository.save(order1);
        orderRepository.save(order2);

        Order result = orderRepository.findById("1");

        assertEquals(order2, result);
    }

    @Test
    void testFindByIdSuccess() {

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        orderRepository.save(order);

        Order result = orderRepository.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void testFindOrderByIdNotFound() {

        OrderRepository repository = new OrderRepository();

        Order result = repository.findById("999");

        assertNull(result);
    }

    @Test
    void testFindAllByAuthorSuccess() {

        Order order1 = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        Order order2 = new Order(
                "2",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> result = orderRepository.findAllByAuthor("affandi");

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllByAuthorEmpty() {

        OrderRepository repository = new OrderRepository();

        List<Order> result = repository.findAllByAuthor("unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll() {

        Order order1 = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        Order order2 = new Order(
                "2",
                products,
                System.currentTimeMillis(),
                "dam"
        );

        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> result = orderRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllByAuthorNotMatch(){

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        orderRepository.save(order);

        List<Order> result = orderRepository.findAllByAuthor("dam");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdDifferentId(){

        Order order = new Order(
                "1",
                products,
                System.currentTimeMillis(),
                "affandi"
        );

        orderRepository.save(order);

        Order result = orderRepository.findById("999");

        assertNull(result);
    }


}