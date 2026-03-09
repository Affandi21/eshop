package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/order/create")
    public String createOrderPage(){

        return "orderCreate";
    }

    @GetMapping("/order/history")
    public String historyPage(){

        return "orderHistory";
    }

}