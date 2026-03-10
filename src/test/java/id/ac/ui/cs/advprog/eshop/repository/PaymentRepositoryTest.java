package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setup() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSavePayment() {

        Payment payment = new Payment(
                "1",
                "VOUCHER",
                "SUCCESS",
                new HashMap<>()
        );

        Payment result = paymentRepository.save(payment);

        assertEquals(payment, result);
    }

    @Test
    void testFindByIdSuccess() {

        Payment payment = new Payment(
                "1",
                "VOUCHER",
                "SUCCESS",
                new HashMap<>()
        );

        paymentRepository.save(payment);

        Payment result = paymentRepository.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void testFindPaymentByIdNotFound() {

        PaymentRepository repository = new PaymentRepository();

        Payment result = repository.findById("999");

        assertNull(result);
    }

    @Test
    void testFindAllPayments() {

        Payment payment1 = new Payment(
                "1",
                "VOUCHER",
                "SUCCESS",
                new HashMap<>()
        );

        Payment payment2 = new Payment(
                "2",
                "COD",
                "SUCCESS",
                new HashMap<>()
        );

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> payments = paymentRepository.findAll();

        assertEquals(2, payments.size());
    }

    @Test
    void testFindByIdEmptyRepository(){

        Payment result = paymentRepository.findById("1");

        assertNull(result);
    }

    @Test
    void testFindByIdDifferentId(){

        Payment payment = new Payment(
                "1",
                "VOUCHER",
                "SUCCESS",
                new HashMap<>()
        );

        paymentRepository.save(payment);

        Payment result = paymentRepository.findById("2");

        assertNull(result);
    }



}