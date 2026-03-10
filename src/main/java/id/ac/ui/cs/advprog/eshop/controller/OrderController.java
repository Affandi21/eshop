package id.ac.ui.cs.advprog.eshop.controller;


import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import java.util.HashMap;
import java.util.Map;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/order/create")
    public String createOrderPage() {
        return "orderCreate";
    }

    @PostMapping("/order/create")
    public String createOrder(@RequestParam String author) {

        List<Product> products = new ArrayList<>();

        Product dummyProduct = new Product();
        dummyProduct.setProductId(UUID.randomUUID().toString());
        dummyProduct.setProductName("Dummy Product");
        dummyProduct.setProductQuantity(1);

        products.add(dummyProduct);

        Order order = new Order(
                UUID.randomUUID().toString(),
                products,
                System.currentTimeMillis(),
                author
        );

        orderService.createOrder(order);

        return "redirect:/order/history";
    }

    @GetMapping("/order/history")
    public String orderHistoryPage(Model model) {

        List<Order> orders = new ArrayList<>();

        model.addAttribute("orders", orders);

        return "orderHistory";
    }

    @PostMapping("/order/history")
    public String searchOrder(
            @RequestParam String author,
            Model model
    ) {

        List<Order> orders = orderService.findAllByAuthor(author);

        model.addAttribute("orders", orders);

        return "orderHistory";
    }

    @GetMapping("/order/pay/{orderId}")
    public String payOrderPage(
            @PathVariable String orderId,
            Model model
    ) {

        Order order = orderService.findById(orderId);

        model.addAttribute("order", order);

        return "orderPay";
    }

    @PostMapping("/order/pay/{orderId}")
    public String payOrder(
            @PathVariable String orderId,
            @RequestParam String method,
            @RequestParam(required = false) String voucherCode
    ) {

        Map<String,String> paymentData = new HashMap<>();

        if(method.equals("VOUCHER")){
            paymentData.put("voucherCode", voucherCode);
        }

        if(method.equals("COD")){
            paymentData.put("address","Jakarta");
            paymentData.put("deliveryFee","20000");
        }

        Order order = orderService.findById(orderId);

        Payment payment = paymentService.addPayment(
                order,
                method,
                paymentData
        );

        return "redirect:/payment/detail/" + payment.getId();
    }

}