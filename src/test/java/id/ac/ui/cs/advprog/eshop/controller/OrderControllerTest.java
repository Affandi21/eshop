package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    PaymentService paymentService;

    @Test
    void testCreateOrderPage() throws Exception {

        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk());
    }

    @Test
    void testOrderHistoryPage() throws Exception {

        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk());
    }

    @Test
    void testOrderPayPage() throws Exception {

        Order order = mock(Order.class);

        when(orderService.findById("1")).thenReturn(order);

        mockMvc.perform(get("/order/pay/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateOrder() throws Exception {

        mockMvc.perform(post("/order/create")
                        .param("author","affandi"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testSearchOrder() throws Exception {

        when(orderService.findAllByAuthor("affandi")).thenReturn(java.util.List.of());

        mockMvc.perform(post("/order/history")
                        .param("author","affandi"))
                .andExpect(status().isOk());
    }

    @Test
    void testPayOrder() throws Exception {

        Order order = mock(Order.class);
        Payment payment = mock(Payment.class);

        when(orderService.findById("1")).thenReturn(order);
        when(paymentService.addPayment(any(), any(), any())).thenReturn(payment);
        when(payment.getId()).thenReturn("1");

        mockMvc.perform(post("/order/pay/1")
                        .param("method","COD")
                        .param("address","Depok")
                        .param("deliveryFee","10000"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    void testPayOrderVoucher() throws Exception {

        Order order = mock(Order.class);
        Payment payment = mock(Payment.class);

        when(orderService.findById("1")).thenReturn(order);
        when(paymentService.addPayment(any(), any(), any())).thenReturn(payment);
        when(payment.getId()).thenReturn("1");

        mockMvc.perform(post("/order/pay/1")
                        .param("method","VOUCHER")
                        .param("voucherCode","ESHOP1234ABC5678"))
                .andExpect(status().is3xxRedirection());
    }

}