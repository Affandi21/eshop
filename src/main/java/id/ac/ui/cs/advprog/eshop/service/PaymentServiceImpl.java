package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        String status = "WAITING";

        if (method.equals("VOUCHER")) {

            String code = paymentData.get("voucherCode");

            if (isValidVoucher(code)) {
                status = "SUCCESS";
            } else {
                status = "REJECTED";
            }

        }

        if (method.equals("COD")) {

            if (isValidCOD(paymentData)) {
                status = "SUCCESS";
            } else {
                status = "REJECTED";
            }

        }

        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                method,
                status,
                paymentData
        );

        paymentRepository.save(payment);

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        paymentRepository.save(payment);

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {

        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    private boolean isValidVoucher(String code) {

        if (code == null) {
            return false;
        }

        if (code.length() != 16) {
            return false;
        }

        if (!code.startsWith("ESHOP")) {
            return false;
        }

        int digits = 0;

        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            }
        }

        return digits == 8;
    }

    private boolean isValidCOD(Map<String, String> data) {

        if (data.get("address") == null || data.get("address").isEmpty()) {
            return false;
        }

        if (data.get("deliveryFee") == null || data.get("deliveryFee").isEmpty()) {
            return false;
        }

        return true;
    }

}