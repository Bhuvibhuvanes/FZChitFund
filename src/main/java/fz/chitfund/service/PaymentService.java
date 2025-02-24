package fz.chitfund.service;

import fz.chitfund.entity.Payment;
import fz.chitfund.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment update(Long id, Payment payment) {
        Payment existingPayment = findById(id);
        // Update fields
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setDueDate(payment.getDueDate());
        existingPayment.setPaidDate(payment.getPaidDate());
        existingPayment.setStatus(payment.getStatus());
        return paymentRepository.save(existingPayment);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
} 