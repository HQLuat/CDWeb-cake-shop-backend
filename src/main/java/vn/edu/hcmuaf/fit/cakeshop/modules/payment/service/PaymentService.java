package vn.edu.hcmuaf.fit.cakeshop.modules.payment.service;

import vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto.PaymentCreateRequest;
import vn.edu.hcmuaf.fit.cakeshop.modules.payment.dto.PaymentResponse;

import java.util.Map;

public interface PaymentService {
    PaymentResponse createVNPayPayment(PaymentCreateRequest request, String ipAddress);
    String handleVNPayCallback(Map<String, String> params);
}
