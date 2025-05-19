package com.heechang.hcMan.domain.payment.application;

import com.heechang.hcMan.domain.payment.entity.Payment;
import com.heechang.hcMan.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }

    public List<Payment> getPayments(Long orderId) {
        if (orderId != null) {
            return paymentRepository.findByOrderId(orderId);
        } else {
            return paymentRepository.findAll();
        }
    }


    public Payment updatePayment(Long paymentId, Payment updateData) {
        Payment payment = getPayment(paymentId);
        if(updateData.getPaymentDate() != null) {
            payment.setPaymentDate(updateData.getPaymentDate());
        }
        if(updateData.getAmount() != null) {
            payment.setAmount(updateData.getAmount());
        }
        if(updateData.getPaymentStatus() != null) {
            payment.setPaymentStatus(updateData.getPaymentStatus());
        }
        return paymentRepository.save(payment);
    }
}
