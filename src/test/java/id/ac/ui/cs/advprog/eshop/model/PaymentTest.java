package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testSetStatus() {

        Payment payment = new Payment(
                "1",
                "VOUCHER",
                "WAITING",
                new HashMap<>()
        );

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

}