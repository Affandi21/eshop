package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    Map<String,String> paymentData;

    @BeforeEach
    void setup(){

        paymentData = new HashMap<>();
    }

    @Test
    void testVoucherPaymentSuccess(){

        paymentData.put("voucherCode","ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                paymentData
        );

        assertEquals("SUCCESS",payment.getStatus());
    }

    @Test
    void testVoucherPaymentRejected(){

        paymentData.put("voucherCode","INVALID");

        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                paymentData
        );

        assertEquals("REJECTED",payment.getStatus());
    }

    @Test
    void testCODSuccess(){

        paymentData.put("address","Jakarta");
        paymentData.put("deliveryFee","20000");

        Payment payment = paymentService.addPayment(
                null,
                "COD",
                paymentData
        );

        assertEquals("SUCCESS",payment.getStatus());
    }

    @Test
    void testCODRejected(){

        paymentData.put("address","");
        paymentData.put("deliveryFee","20000");

        Payment payment = paymentService.addPayment(
                null,
                "COD",
                paymentData
        );

        assertEquals("REJECTED",payment.getStatus());
    }
}