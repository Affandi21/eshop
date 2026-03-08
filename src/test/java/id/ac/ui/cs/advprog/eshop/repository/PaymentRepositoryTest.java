package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository repository;

    @BeforeEach
    void setup(){
        repository = new PaymentRepository();
    }

    @Test
    void testSavePayment(){

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP1234ABC5678");

        Payment payment = new Payment("1","VOUCHER","WAITING",data);

        repository.save(payment);

        Payment result = repository.findById("1");

        assertEquals("1",result.getId());
    }

}