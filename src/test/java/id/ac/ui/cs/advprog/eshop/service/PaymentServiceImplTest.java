package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Test
    void testGetPayment() {

        Payment payment = new Payment(
                "1",
                "VOUCHER",
                "SUCCESS",
                new HashMap<>()
        );

        when(paymentRepository.findById("1")).thenReturn(payment);

        Payment result = paymentService.getPayment("1");

        assertEquals("1", result.getId());
    }

    @Test
    void testGetAllPayments() {

        when(paymentRepository.findAll()).thenReturn(List.of());

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
    }

    @Test
    void testSetStatus() {

        Payment payment = new Payment(
                "1",
                "VOUCHER",
                "WAITING",
                new HashMap<>()
        );

        Payment result = paymentService.setStatus(payment,"SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testVoucherNullCode(){

        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                new HashMap<>()
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCODRejectedNoDeliveryFee(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("address","Jakarta");
        paymentData.put("deliveryFee","");

        Payment payment = paymentService.addPayment(
                null,
                "COD",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherWrongPrefix(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("voucherCode","ABC1234ABC567890");
        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherWrongDigitCount(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("voucherCode","ESHOPABCDEFGH1234");

        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherDigitNotEight(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("voucherCode","ESHOPABCDEFGH12");

        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCODAddressNull(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("deliveryFee","20000");

        Payment payment = paymentService.addPayment(
                null,
                "COD",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCODDeliveryFeeNull(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("address","Jakarta");

        Payment payment = paymentService.addPayment(
                null,
                "COD",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testVoucherDigitNotEight2(){

        Map<String,String> paymentData = new HashMap<>();

        paymentData.put("voucherCode","ESHOPABCDEFGHIJK");

        Payment payment = paymentService.addPayment(
                null,
                "VOUCHER",
                paymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }





}