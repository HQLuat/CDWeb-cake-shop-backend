package vn.edu.hcmuaf.fit.cakeshop.modules.payment.service;

import java.math.BigDecimal;
import java.util.Map;

public interface VNPayService {
    String createPaymentUrl(String txnRef, BigDecimal amount, String ipAddress);
    boolean verifySignature(Map<String, String> params);
}
